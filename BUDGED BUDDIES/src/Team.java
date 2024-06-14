import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

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

	public void addExpense(expense aExpense){
		
		expenses.add(aExpense);

		try (Connection conn = XAMPPConnection.getConnection()) {
            String insertExpenseQuery = "INSERT INTO expense (expenseID, title, amount, date, payer) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement expenseStatement = conn.prepareStatement(insertExpenseQuery);
            expenseStatement.setString(1, aExpense.getExpenseID());
            expenseStatement.setString(2, aExpense.getTitle());
            expenseStatement.setDouble(3, aExpense.getAmount());
            expenseStatement.setDate(4, new java.sql.Date(aExpense.getDate().getTime()));
            expenseStatement.setInt(5, aExpense.getPayer().getUserID());
            expenseStatement.executeUpdate();

            System.out.println("Expense added to the database.");
        } catch (SQLException e) {
            System.out.println("Error while adding expense to the database: " + e.getMessage());
        }
	}

	public String getTeamID() {
		return teamID;
	}
	
	public void addUser(User user) {
		
		Users.add(user);
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

	public ArrayList<expense> getExpenses() {
		return expenses;
	}

	
}
