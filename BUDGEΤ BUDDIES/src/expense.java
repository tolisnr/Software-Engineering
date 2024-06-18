import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class expense {
    private int expenseID;
    private String title;
    private double amount;
    private Date date;
    private User payer;
    private List<User> paidFor;

    private static final String URL = "jdbc:mysql://localhost:3306/budgetbuddy";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public expense(int expenseID, String title, double amount, Date date, User payer, List<User> paidFor) {
        this.expenseID = expenseID;
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.payer = payer;
        this.paidFor = getPaidFor(expenseID);
    }

    public expense(int expenseID) {
        this.expenseID = expenseID;
    }

    public int getExpenseID() {
        return expenseID;
    }

    public String getTitle() {
        return title;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public User getPayer() {
        return payer;
    }

    public List<User> getPaidFor() {
        return paidFor;
    }

    public void setExpenseID(int expenseID) {
        this.expenseID = expenseID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }

    public void setPaidFor(List<User> paidFor) {
        this.paidFor = paidFor;
    }

    public boolean deleteExpense(int expenseID) {
        boolean success = false;
        List<Integer> usersInvolved = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            conn.setAutoCommit(false);

            usersInvolved = getUsersInvolvedInExpense(conn, expenseID);
            double amountPaid = getExpenseAmount(conn, expenseID);
            double sharedAmount = amountPaid / usersInvolved.size();
            int winID = payer.getUserID();

            updateOwesTable(conn, usersInvolved, sharedAmount, winID);
            updateUserTeamTotals(conn, usersInvolved, sharedAmount, expenseID);
            deleteExpenseRecord(conn, expenseID);

            conn.commit();
            success = true;
        } catch (SQLException e) {
            System.err.println("Error deleting expense: " + e.getMessage());
        }

        return success;
    }

    private List<Integer> getUsersInvolvedInExpense(Connection conn, int expenseID) throws SQLException {
        List<Integer> usersInvolved = new ArrayList<>();
        String query = "SELECT userID FROM `expense_users_paidfor` WHERE expenseID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, expenseID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    usersInvolved.add(rs.getInt("userID"));
                }
            }
        }

        return usersInvolved;
    }

    private double getExpenseAmount(Connection conn, int expenseID) throws SQLException {
        double amountPaid = 0.0;
        String query = "SELECT amount FROM expense WHERE expenseID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, expenseID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    amountPaid = rs.getDouble("amount");
                }
            }
        }

        return amountPaid;
    }

    private void updateOwesTable(Connection conn, List<Integer> usersInvolved, double sharedAmount, int winID) throws SQLException {
        String query = "UPDATE owes SET Amount = Amount - ? WHERE winID = ? AND lossID = ?";

        for (int userID : usersInvolved) {
            if (userID != winID) {
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setDouble(1, sharedAmount);
                    stmt.setInt(2, winID);
                    stmt.setInt(3, userID);
                    stmt.executeUpdate();
                }
            }
        }
    }

    private void updateUserTeamTotals(Connection conn, List<Integer> usersInvolved, double sharedAmount, int expenseID) throws SQLException {
        String query = "UPDATE user_team_totals SET mytotal = mytotal - ? WHERE userID = ? AND teamID = ?";
        String teamID = getTeamIDForExpense(conn, expenseID);

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int userID : usersInvolved) {
                stmt.setDouble(1, sharedAmount);
                stmt.setInt(2, userID);
                stmt.setString(3, teamID);
                stmt.executeUpdate();
            }
        }

        String query1 = "UPDATE team SET total = total - ? WHERE teamID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query1)) {
                stmt.setDouble(1, amount);
                stmt.setString(2, teamID);
                stmt.executeUpdate();
        }
    }

    private void deleteExpenseRecord(Connection conn, int expenseID) throws SQLException {
        String query = "DELETE FROM expense WHERE expenseID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, expenseID);
            stmt.executeUpdate();
        }
    }

    public List<User> getPaidFor(int expenseID) {
        List<User> paidForUsers = new ArrayList<>();
        String query = "SELECT u.userID, u.username, u.password FROM users u " +
                "INNER JOIN `expense_users_paidfor` eup ON u.userID = eup.userID " +
                "WHERE eup.expenseID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, expenseID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int userID = rs.getInt("userID");
                    String username = rs.getString("username");
                    String password = rs.getString("password");

                    User user = new User(username, password, userID);
                    paidForUsers.add(user);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching users for expense: " + e.getMessage());
        }

        return paidForUsers;
    }

    public String getTeamIDForExpense(Connection conn, int expenseID) {
        String query = "SELECT te.teamID FROM team_expenses te WHERE te.expenseID = ?";
        String teamID = null;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, expenseID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    teamID = rs.getString("teamID");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching teamID for expense: " + e.getMessage());
        }

        return teamID;
    }
}
