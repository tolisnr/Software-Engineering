import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Team {

    public String teamID;
    public ArrayList<User> Users = new ArrayList<>();
    public String title, category;
    public int userIdAdmin;
    public ArrayList<expense> expenses = new ArrayList<>();

    public Team(String code, String title, String category, int userIdAdmin) {
        super();
        this.teamID = code;
        this.title = title;
        this.category = category;
        this.userIdAdmin = userIdAdmin;
    }

    public static Team getTeamByTitle(ArrayList<Team> teams, String title) {
        for (Team team : teams) {
            if (team.getTitle().equalsIgnoreCase(title)) {
                return team;
            }
        }
        return null;
    }

	public void addExpense(expense aExpense, List<Integer> paidForUserIDs) {
        expenses.add(aExpense);
        try (Connection conn = XAMPPConnection.getConnection()) {
            // Insert the expense into the expense table
            String insertExpenseQuery = "INSERT INTO expense (title, amount, date, payer) VALUES (?, ?, ?, ?)";
            PreparedStatement expenseStatement = conn.prepareStatement(insertExpenseQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            expenseStatement.setString(1, aExpense.getTitle());
            expenseStatement.setDouble(2, aExpense.getAmount());
            expenseStatement.setDate(3, new java.sql.Date(aExpense.getDate().getTime()));
            expenseStatement.setInt(4, aExpense.getPayer().getUserID());
    
            int rowsInserted = expenseStatement.executeUpdate();
    
            if (rowsInserted > 0) {
                // Retrieve the generated expenseID
                try (ResultSet generatedKeys = expenseStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        aExpense.setExpenseID(generatedKeys.getInt(1));
                        System.out.println("Expense added to the database with expenseID: " + aExpense.getExpenseID());
    
                        // Insert users for whom the expense was paid into expense_users(paidfor)
                        String insertPaidForQuery = "INSERT INTO `expense_users(paidfor)` (userID, expenseID) VALUES (?, ?)";
                        PreparedStatement paidForStatement = conn.prepareStatement(insertPaidForQuery);
    
                        for (Integer userID : paidForUserIDs) {
                            paidForStatement.setInt(1, userID);
                            paidForStatement.setInt(2, aExpense.getExpenseID());
                            paidForStatement.executeUpdate();
                        }
    
                        System.out.println("Users associated with expenseID " + aExpense.getExpenseID());
    
                        // Update the team_expenses table to associate this expense with the team
                        String insertTeamExpenseQuery = "INSERT INTO team_expenses (teamID, expenseID) VALUES (?, ?)";
                        PreparedStatement teamExpenseStatement = conn.prepareStatement(insertTeamExpenseQuery);
                        teamExpenseStatement.setString(1, this.teamID); // Assuming teamID is a member variable of the Team class
                        teamExpenseStatement.setInt(2, aExpense.getExpenseID());
    
                        int rowsUpdated = teamExpenseStatement.executeUpdate();
                        if (rowsUpdated > 0) {
                            System.out.println("Expense associated with teamID " + this.teamID);
                        } else {
                            System.out.println("Failed to associate expense with team.");
                        }
                    } else {
                        System.out.println("Failed to retrieve the generated expenseID.");
                    }
                }
            } else {
                System.out.println("No rows inserted into expense table.");
            }
        } catch (SQLException e) {
            System.out.println("Error while adding expense to the database: " + e.getMessage());
        }
    }
    
    
    
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        String query = "SELECT u.userID, u.username, u.password " +
                       "FROM users u " +
                       "INNER JOIN teams_users tu ON u.userID = tu.userID " +
                       "WHERE tu.teamID = ?";
        
        try (Connection conn = XAMPPConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, this.teamID);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt("userID");
                String username = rs.getString("username");
                String password = rs.getString("password");

                User user = new User(username, password, userID);
                users.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching users for team: " + e.getMessage());
        }

        return users;
    }

	public ArrayList<expense> getExpenses() {
        ArrayList<expense> teamExpenses = new ArrayList<>();

        String query = "SELECT e.expenseID, e.title, e.amount, e.date, e.payer " +
                       "FROM expense e " +
                       "INNER JOIN team_expenses te ON e.expenseID = te.expenseID " +
                       "WHERE te.teamID = ?";

        try (Connection conn = XAMPPConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, this.teamID);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int expenseID = rs.getInt("expenseID");
                String title = rs.getString("title");
                double amount = rs.getDouble("amount");
                Date date = rs.getDate("date");
                int payerID = rs.getInt("payer");

                // Fetch payer details from users table
                User payer = getUserByID(payerID);

                // Fetch the list of users for whom the expense is paid
                List<User> paidForUsers = getPaidForUsers(expenseID);

                // Create Expense object
                expense expense = new expense(expenseID, title, amount, date, payer, paidForUsers);
                teamExpenses.add(expense);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching expenses for team: " + e.getMessage());
        }

        for (expense exp : teamExpenses) {
            System.out.println("Expense Title: " + exp.getTitle());
            System.out.println("Amount: " + exp.getAmount());
            System.out.println("Date: " + exp.getDate());
            System.out.println("Payer: " + exp.getPayer().getUsername()); // Example assuming User has getUsername() method
            System.out.println("Paid For: " + exp.getPaidFor().stream().map(User::getUsername).toArray(String[]::new)); // Assuming User has getUsername() method
            System.out.println("---");
        }

        return teamExpenses;
    }

	public static User getUserByID(int userID) {
        User user = null;
        String query = "SELECT username, password FROM users WHERE userID = ?";

        try (Connection conn = XAMPPConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userID);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");

                user = new User(username, password, userID);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching user details: " + e.getMessage());
        }

        return user;
    }

    private List<User> getPaidForUsers(int expenseID) {
        List<User> paidForUsers = new ArrayList<>();
        String query = "SELECT ue.userID, u.username, u.password " +
                       "FROM user_expense ue " +
                       "INNER JOIN users u ON ue.userID = u.userID " +
                       "WHERE ue.expenseID = ?";

        try (Connection conn = XAMPPConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, expenseID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int userID = rs.getInt("userID");
                String username = rs.getString("username");
                String password = rs.getString("password");
                paidForUsers.add(new User(username, password, userID));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching paid for users: " + e.getMessage());
        }

        return paidForUsers;
    }


    public String getTeamID() {
        return teamID;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public int getUserIdAdmin() {
        return userIdAdmin;
    }

	public void addUser(User user){
		Users.add(user);
	}
}
