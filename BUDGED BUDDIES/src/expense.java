import java.sql.Connection;
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

    private ArrayList<User> getPaidFor(int expenseID) {
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



    public void setPaidFor(List<User> paidFor) {
        this.paidFor = paidFor;
    }

    public void setExpenseID(int expenseID) {
        this.expenseID = expenseID;
    }
}
