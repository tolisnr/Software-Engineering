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

    public boolean deleteExpense(int expenseID) {
        boolean success = false;
        String deleteExpenseQuery = "DELETE FROM expense WHERE expenseID = ?";
        
        try (Connection conn = XAMPPConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteExpenseQuery)) {
            
            stmt.setInt(1, expenseID);
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.out.println("Error deleting expense: " + e.getMessage());
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
