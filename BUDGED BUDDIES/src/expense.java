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

    public expense(int expenseID, String title, double amount, Date date, User payer, List<User> paidFor) {
        this.expenseID = expenseID;
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.payer = payer;
        this.paidFor = getPaidFor(expenseID);
    }

    public expense(int expenseID) {
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

    public List<User> getPaidFor() {
        return paidFor;
    }

    public User getPayer() {
        return payer;
    }

    // Method to delete an expense and update related entries
    private static final String URL = "jdbc:mysql://localhost:3306/budgedbuddies";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    // Method to delete an expense and update related entries
    public boolean deleteExpense(int expenseID) {
        boolean success = false;
        Connection conn = null; // Initialize connection
    
        // Queries for updating balances and owes
        String selectPaidForQuery = "SELECT userID FROM `expense_users(paidfor)` WHERE expenseID = ?";
        String updateOwesQuery = "UPDATE owes " +
                "SET Amount = Amount - ? " +  // Subtract the amount owed
                "WHERE winID = ? AND lossID = ?";
    
        // Query to delete expense
        String deleteExpenseQuery = "DELETE FROM expense WHERE expenseID = ?";
        
        // Track users involved in paidfor list
        List<Integer> usersInvolved = new ArrayList<>();
        
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            conn.setAutoCommit(false); // Start transaction
    
            // Step 1: Fetch users involved in paidfor list
            try (PreparedStatement stmtPaidFor = conn.prepareStatement(selectPaidForQuery)) {
                stmtPaidFor.setInt(1, expenseID);
                try (ResultSet rsPaidFor = stmtPaidFor.executeQuery()) {
                    while (rsPaidFor.next()) {
                        int userID = rsPaidFor.getInt("userID");
                        usersInvolved.add(userID);
                    }
                }
            }
    
            // Step 2: Fetch the amount paid in the expense
            double amountPaid = 0.0;
            String selectAmountQuery = "SELECT amount FROM expense WHERE expenseID = ?";
            try (PreparedStatement stmtAmount = conn.prepareStatement(selectAmountQuery)) {
                stmtAmount.setInt(1, expenseID);
                try (ResultSet rsAmount = stmtAmount.executeQuery()) {
                    if (rsAmount.next()) {
                        amountPaid = rsAmount.getDouble("amount");
                    }
                }
            }
    
            // Step 3: Calculate the total expense amount
            double sharedAmount = amountPaid / usersInvolved.size();
    
            int winID = payer.getUserID();  // Assuming payer is already initialized
            
            // Step 4: Update owes table
            for (int userID : usersInvolved) {
                if (userID != winID) {
                    double totalOwed = sharedAmount;  // Initialize totalOwed with sharedAmount
                    
                    // Subtract the shared amount from the owes table
                    try (PreparedStatement stmtUpdateOwes = conn.prepareStatement(updateOwesQuery)) {
                        stmtUpdateOwes.setDouble(1, totalOwed);
                        stmtUpdateOwes.setInt(2, winID);
                        stmtUpdateOwes.setInt(3, userID);
                        stmtUpdateOwes.executeUpdate();  
                    }
                }
            }
    
            // Step 5: Delete expense
            try (PreparedStatement stmtDeleteExpense = conn.prepareStatement(deleteExpenseQuery)) {
                stmtDeleteExpense.setInt(1, expenseID);
                stmtDeleteExpense.executeUpdate();
            }
    
            conn.commit(); // Commit transaction if all steps succeed
            success = true;
    
        } catch (SQLException e) {
            System.out.println("Error deleting expense: " + e.getMessage());
            try {
                if (conn != null) {
                    conn.rollback(); // Rollback in case of any exception
                }
            } catch (SQLException rollbackEx) {
                System.out.println("Error rolling back transaction: " + rollbackEx.getMessage());
            }
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true); // Set auto-commit back to true
                    conn.close(); // Close connection
                }
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    
        return success;
    }
    
    public List<User> getPaidFor(int expenseID) {
        ArrayList<User> paidForUsers = new ArrayList<>();

        String query = "SELECT u.userID, u.username, u.password " +
                       "FROM users u " +
                       "INNER JOIN `expense_users(paidfor)` eup ON u.userID = eup.userID " +
                       "WHERE eup.expenseID = ?";

        try (Connection conn = XAMPPConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, expenseID);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt("userID");
                String username = rs.getString("username");
                String password = rs.getString("password");

                User user = new User(username, password, userID);
                paidForUsers.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching users for expense: " + e.getMessage());
        }

        return paidForUsers;
    }

    public String getTeamIDForExpense(int expenseID) {
        String query = "SELECT te.teamID " +
                       "FROM team_expenses te " +
                       "WHERE te.expenseID = ?";
        String teamID = null;

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, expenseID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                teamID = rs.getString("teamID");
            }

        } catch (SQLException e) {
            System.out.println("Error fetching teamID for expense: " + e.getMessage());
        }

        return teamID;
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

    public void setExpenseID(int expenseID) {
        this.expenseID = expenseID;
    }
}
