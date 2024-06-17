import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Team {

    public String teamID;
    public ArrayList<User> Users = new ArrayList<>();
    public String title, category;
    public int userIdAdmin;
    public ArrayList<expense> expenses = new ArrayList<>();
    public double total;

    public Team(String code, String title, String category, int userIdAdmin) {
        super();
        this.teamID = code;
        this.title = title;
        this.category = category;
        this.userIdAdmin = userIdAdmin;
        this.total = 0;
    }

    public static Team getTeamByTitle(ArrayList<Team> teams, String title) {
        for (Team team : teams) {
            if (team.getTitle().equalsIgnoreCase(title)) {
                return team;
            }
        }
        return null;
    }

    public boolean deleteTeam() {
        boolean success = false;
    
        try (Connection conn = XAMPPConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction
    
            // Delete from teams_users table
            String deleteTeamsUsersQuery = "DELETE FROM teams_users WHERE teamID = ?";
            PreparedStatement teamsUsersStatement = conn.prepareStatement(deleteTeamsUsersQuery);
            teamsUsersStatement.setString(1, this.teamID);
            teamsUsersStatement.executeUpdate();
    
            // Delete expenses associated with the team, update total
            double totalExpenseAmount = 0;
            for (expense exp : expenses) {
                totalExpenseAmount += exp.getAmount();
                exp.deleteExpense(exp.getExpenseID()); // Assuming deleteExpense is implemented in the Expense class
            }
    
            // Delete from team_expenses table
            String deleteTeamExpensesQuery = "DELETE FROM team_expenses WHERE teamID = ?";
            PreparedStatement teamExpensesStatement = conn.prepareStatement(deleteTeamExpensesQuery);
            teamExpensesStatement.setString(1, this.teamID);
            teamExpensesStatement.executeUpdate();
            
            String deleteUserTotalsQuery = "DELETE FROM user_team_totals WHERE teamID = ?";
            PreparedStatement deleteUserTotalsStatement = conn.prepareStatement(deleteUserTotalsQuery);
            deleteUserTotalsStatement.setString(1, teamID);
            deleteUserTotalsStatement.executeUpdate();

            // Update team total in team table
            String updateTeamTotalQuery = "UPDATE team SET total = total - ? WHERE teamID = ?";
            PreparedStatement updateTeamStatement = conn.prepareStatement(updateTeamTotalQuery);
            updateTeamStatement.setDouble(1, totalExpenseAmount);
            updateTeamStatement.setString(2, this.teamID);
            updateTeamStatement.executeUpdate();
    
            // Delete from balance table (assuming balance table is related to team)
            String deleteBalanceQuery = "DELETE FROM balance WHERE teamID = ?";
            PreparedStatement balanceStatement = conn.prepareStatement(deleteBalanceQuery);
            balanceStatement.setString(1, this.teamID);
            balanceStatement.executeUpdate();
    
            // Delete from team table
            String deleteTeamQuery = "DELETE FROM team WHERE teamID = ?";
            PreparedStatement teamStatement = conn.prepareStatement(deleteTeamQuery);
            teamStatement.setString(1, this.teamID);
            int rowsDeleted = teamStatement.executeUpdate();
    
            if (rowsDeleted > 0) {
                success = true;
            }
    
            conn.commit(); // Commit transaction if successful
        } catch (SQLException e) {
            System.out.println("Error deleting team: " + e.getMessage());
            try (Connection conn = XAMPPConnection.getConnection()){
                conn.rollback(); // Rollback transaction on error
            } catch (SQLException e1) {
                System.out.println("Error rolling back transaction: " + e1.getMessage());
            }
        }
    
        return success;
    }


    public void addExpense(expense aExpense, List<Integer> paidForUserIDs) {
        expenses.add(aExpense); // Assuming this is updating an in-memory list
    
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
    
                            // Calculate the share of the expense for each user who owes the payer
                            double shareAmount = aExpense.getAmount() / paidForUserIDs.size();
    
                            // Update the balance table for each user who owes the payer
                            String updateBalanceQuery = "INSERT INTO balance (balanceID, teamID, win, loss, amount, expenseID) " +
                                    "VALUES (UUID(), ?, ?, ?, ?, ?) " +
                                    "ON DUPLICATE KEY UPDATE amount = amount + VALUES(amount)";
                            PreparedStatement balanceStatement = conn.prepareStatement(updateBalanceQuery);
    
                            // Update the owes table
                            String checkOwesQuery = "SELECT Amount FROM owes WHERE teamID = ? AND winID = ? AND lossID = ?";
                            String updateOwesQuery = "UPDATE owes SET Amount = Amount + ? WHERE teamID = ? AND winID = ? AND lossID = ?";
                            String insertOwesQuery = "INSERT INTO owes (teamID, winID, lossID, Amount) VALUES (?, ?, ?, ?)";
                            PreparedStatement checkOwesStatement = conn.prepareStatement(checkOwesQuery);
                            PreparedStatement updateOwesStatement = conn.prepareStatement(updateOwesQuery);
                            PreparedStatement insertOwesStatement = conn.prepareStatement(insertOwesQuery);
    
                            for (Integer lossUserID : paidForUserIDs) {
                                if (!lossUserID.equals(aExpense.getPayer().getUserID())) {
                                    // Update balance
                                    balanceStatement.setString(1, this.teamID);
                                    balanceStatement.setInt(2, aExpense.getPayer().getUserID());
                                    balanceStatement.setInt(3, lossUserID);
                                    balanceStatement.setDouble(4, -shareAmount);
                                    balanceStatement.setInt(5, aExpense.getExpenseID());
                                    balanceStatement.executeUpdate();
    
                                    // Check if entry exists in owes table
                                    checkOwesStatement.setString(1, this.teamID);
                                    checkOwesStatement.setInt(2, aExpense.getPayer().getUserID());
                                    checkOwesStatement.setInt(3, lossUserID);
                                    ResultSet owesResult = checkOwesStatement.executeQuery();
    
                                    if (owesResult.next()) {
                                        // Update existing owes entry
                                        updateOwesStatement.setDouble(1, shareAmount);
                                        updateOwesStatement.setString(2, this.teamID);
                                        updateOwesStatement.setInt(3, aExpense.getPayer().getUserID());
                                        updateOwesStatement.setInt(4, lossUserID);
                                        updateOwesStatement.executeUpdate();
                                    } else {
                                        // Insert new owes entry
                                        insertOwesStatement.setString(1, this.teamID);
                                        insertOwesStatement.setInt(2, aExpense.getPayer().getUserID());
                                        insertOwesStatement.setInt(3, lossUserID);
                                        insertOwesStatement.setDouble(4, shareAmount);
                                        insertOwesStatement.executeUpdate();
                                    }
                                }
                            }
    
                            // Calculate total amount for the team from the expenses table
                            String calculateTotalQuery = "SELECT SUM(amount) AS total FROM expense e " +
                                    "JOIN team_expenses te ON e.expenseID = te.expenseID " +
                                    "WHERE te.teamID = ?";
                            PreparedStatement totalStatement = conn.prepareStatement(calculateTotalQuery);
                            totalStatement.setString(1, this.teamID);
                            ResultSet totalResult = totalStatement.executeQuery();
    
                            if (totalResult.next()) {
                                double totalAmount = totalResult.getDouble("total");
    
                                // Update the team's total amount
                                String updateTotalQuery = "UPDATE team SET total = ? WHERE teamID = ?";
                                PreparedStatement updateTotalStatement = conn.prepareStatement(updateTotalQuery);
                                updateTotalStatement.setDouble(1, totalAmount);
                                updateTotalStatement.setString(2, this.teamID);
                                updateTotalStatement.executeUpdate();
    
                                System.out.println("Team total updated for teamID " + this.teamID + " to " + totalAmount);
                            }
                        }
                        for (Integer paidUserID : paidForUserIDs) {
                            double shareAmount = aExpense.getAmount() / paidForUserIDs.size();
                
                            // Check if user_team_totals entry exists for this user and team
                            String checkTotalsQuery = "SELECT mytotal FROM user_team_totals WHERE userID = ? AND teamID = ?";
                            PreparedStatement checkTotalsStatement = conn.prepareStatement(checkTotalsQuery);
                            checkTotalsStatement.setInt(1, paidUserID);
                            checkTotalsStatement.setString(2, this.teamID);
                
                            ResultSet totalsResult = checkTotalsStatement.executeQuery();
                
                            if (totalsResult.next()) {
                                // Update existing entry
                                double currentTotal = totalsResult.getDouble("mytotal");
                                double newTotal = currentTotal + shareAmount;
                
                                String updateTotalsQuery = "UPDATE user_team_totals SET mytotal = ? WHERE userID = ? AND teamID = ?";
                                PreparedStatement updateTotalsStatement = conn.prepareStatement(updateTotalsQuery);
                                updateTotalsStatement.setDouble(1, newTotal);
                                updateTotalsStatement.setInt(2, paidUserID);
                                updateTotalsStatement.setString(3, this.teamID);
                                updateTotalsStatement.executeUpdate();
                
                            } else {
                                // Insert new entry
                                String insertTotalsQuery = "INSERT INTO user_team_totals (userID, teamID, mytotal) VALUES (?, ?, ?)";
                                PreparedStatement insertTotalsStatement = conn.prepareStatement(insertTotalsQuery);
                                insertTotalsStatement.setInt(1, paidUserID);
                                insertTotalsStatement.setString(2, this.teamID);
                                insertTotalsStatement.setDouble(3, shareAmount);
                                insertTotalsStatement.executeUpdate();
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    


    public void updateExpense(expense updatedExpense, List<Integer> paidForUserIDs) {
        try (Connection conn = XAMPPConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction
    
            try {
                // Step 1: Get old expense data before updating
                String selectOldExpenseQuery = "SELECT amount, payer FROM expense WHERE expenseID = ?";
                PreparedStatement selectOldExpenseStatement = conn.prepareStatement(selectOldExpenseQuery);
                selectOldExpenseStatement.setInt(1, updatedExpense.getExpenseID());
                ResultSet oldExpenseResultSet = selectOldExpenseStatement.executeQuery();
    
                double oldAmount = 0;
                int oldPayerID = 0;
                if (oldExpenseResultSet.next()) {
                    oldAmount = oldExpenseResultSet.getDouble("amount");
                    oldPayerID = oldExpenseResultSet.getInt("payer");
                }
    
                // Step 2: Get old paidFor users
                String selectOldPaidForQuery = "SELECT userID FROM `expense_users(paidfor)` WHERE expenseID = ?";
                PreparedStatement selectOldPaidForStatement = conn.prepareStatement(selectOldPaidForQuery);
                selectOldPaidForStatement.setInt(1, updatedExpense.getExpenseID());
                ResultSet oldPaidForResultSet = selectOldPaidForStatement.executeQuery();
    
                List<Integer> oldPaidForUserIDs = new ArrayList<>();
                while (oldPaidForResultSet.next()) {
                    oldPaidForUserIDs.add(oldPaidForResultSet.getInt("userID"));
                }
    
                // Step 3: Calculate old and new share amounts
                double newShareAmount = updatedExpense.getAmount() / paidForUserIDs.size();
                double oldShareAmount = oldAmount / oldPaidForUserIDs.size();
    
                // Step 4: Adjust old payer's owes if the payer has changed
                if (oldPayerID != updatedExpense.getPayer().getUserID()) {
                    // Subtract old share amount from old payer's owes
                    for (Integer oldUserID : oldPaidForUserIDs) {
                        if (!oldUserID.equals(oldPayerID)) {
                            String adjustOldPayerOwesQuery = "UPDATE owes SET Amount = Amount - ? WHERE teamID = ? AND winID = ? AND lossID = ?";
                            try (PreparedStatement adjustOldPayerOwesStatement = conn.prepareStatement(adjustOldPayerOwesQuery)) {
                                adjustOldPayerOwesStatement.setDouble(1, oldShareAmount);
                                adjustOldPayerOwesStatement.setString(2, this.teamID);
                                adjustOldPayerOwesStatement.setInt(3, oldPayerID);
                                adjustOldPayerOwesStatement.setInt(4, oldUserID);
                                adjustOldPayerOwesStatement.executeUpdate();
                            }
                        }
                    }
                }
    
                // Step 5: Delete existing entries in expense_users(paidfor) table
                String deletePaidForQuery = "DELETE FROM `expense_users(paidfor)` WHERE expenseID = ?";
                PreparedStatement deletePaidForStatement = conn.prepareStatement(deletePaidForQuery);
                deletePaidForStatement.setInt(1, updatedExpense.getExpenseID());
                deletePaidForStatement.executeUpdate();
    
                // Step 6: Insert new entries in expense_users(paidfor) table
                String insertPaidForQuery = "INSERT INTO `expense_users(paidfor)` (userID, expenseID) VALUES (?, ?)";
                PreparedStatement insertPaidForStatement = conn.prepareStatement(insertPaidForQuery);
                for (Integer userID : paidForUserIDs) {
                    insertPaidForStatement.setInt(1, userID);
                    insertPaidForStatement.setInt(2, updatedExpense.getExpenseID());
                    insertPaidForStatement.executeUpdate();
                }
    
                // Step 7: Update expense table
                String updateExpenseQuery = "UPDATE expense SET title = ?, amount = ?, date = ?, payer = ? WHERE expenseID = ?";
                PreparedStatement updateExpenseStatement = conn.prepareStatement(updateExpenseQuery);
                updateExpenseStatement.setString(1, updatedExpense.getTitle());
                updateExpenseStatement.setDouble(2, updatedExpense.getAmount());
                updateExpenseStatement.setDate(3, new java.sql.Date(updatedExpense.getDate().getTime()));
                updateExpenseStatement.setInt(4, updatedExpense.getPayer().getUserID());
                updateExpenseStatement.setInt(5, updatedExpense.getExpenseID());
                updateExpenseStatement.executeUpdate();
    
                // Step 8: Update team's total amount
                String selectTeamTotalQuery = "SELECT total FROM team WHERE teamID = ?";
                double currentTeamTotal = 0.0;
                try (PreparedStatement selectTeamTotalStatement = conn.prepareStatement(selectTeamTotalQuery)) {
                    selectTeamTotalStatement.setString(1, this.teamID);
                    ResultSet teamTotalResultSet = selectTeamTotalStatement.executeQuery();
                    if (teamTotalResultSet.next()) {
                        currentTeamTotal = teamTotalResultSet.getDouble("total");
                    }
                }
    
                double expenseAmountDifference = updatedExpense.getAmount() - oldAmount;
                double newTeamTotal = currentTeamTotal + expenseAmountDifference;
    
                String updateTeamTotalQuery = "UPDATE team SET total = ? WHERE teamID = ?";
                try (PreparedStatement updateTeamTotalStatement = conn.prepareStatement(updateTeamTotalQuery)) {
                    updateTeamTotalStatement.setDouble(1, newTeamTotal);
                    updateTeamTotalStatement.setString(2, this.teamID);
                    updateTeamTotalStatement.executeUpdate();
                }
    
                // Step 9: Update or insert owes table for new expense
                String updateOwesQuery = "INSERT INTO owes (teamID, winID, lossID, Amount) VALUES (?, ?, ?, ?) " +
                                         "ON DUPLICATE KEY UPDATE Amount = Amount + VALUES(Amount)";
                PreparedStatement updateOwesStatement = conn.prepareStatement(updateOwesQuery);
                for (Integer lossUserID : paidForUserIDs) {
                    if (!lossUserID.equals(updatedExpense.getPayer().getUserID())) {
                        updateOwesStatement.setString(1, this.teamID);
                        updateOwesStatement.setInt(2, updatedExpense.getPayer().getUserID());
                        updateOwesStatement.setInt(3, lossUserID);
                        updateOwesStatement.setDouble(4, newShareAmount);
                        updateOwesStatement.executeUpdate();
                    }
                }
    
                // Step 10: Subtract old share amount for users no longer paid for this expense
                for (Integer oldUserID : oldPaidForUserIDs) {
                    if (!paidForUserIDs.contains(oldUserID)) {
                        String subtractOldShareQuery = "UPDATE owes SET Amount = Amount - ? WHERE teamID = ? AND winID = ? AND lossID = ?";
                        PreparedStatement subtractOldShareStatement = conn.prepareStatement(subtractOldShareQuery);
                        subtractOldShareStatement.setDouble(1, oldShareAmount);
                        subtractOldShareStatement.setString(2, this.teamID);
                        subtractOldShareStatement.setInt(3, oldPayerID);
                        subtractOldShareStatement.setInt(4, oldUserID);
                        subtractOldShareStatement.executeUpdate();
                    }
                }
    
                // New Step: Remove the previous share amount from `mytotal` for everyone in the old paid-for list
                String updateUserTotalRemoveOldQuery = "UPDATE user_team_totals SET mytotal = mytotal - ? WHERE teamID = ? AND userID = ?";
                try (PreparedStatement updateUserTotalRemoveOldStatement = conn.prepareStatement(updateUserTotalRemoveOldQuery)) {
                    for (Integer oldUserID : oldPaidForUserIDs) {
                        updateUserTotalRemoveOldStatement.setDouble(1, oldShareAmount);
                        updateUserTotalRemoveOldStatement.setString(2, this.teamID);
                        updateUserTotalRemoveOldStatement.setInt(3, oldUserID);
                        updateUserTotalRemoveOldStatement.executeUpdate();
                    }
                }
    
                // New Step: Add the new share amount to `mytotal` for everyone in the new paid-for list
                String updateUserTotalAddNewQuery = "UPDATE user_team_totals SET mytotal = mytotal + ? WHERE teamID = ? AND userID = ?";
                try (PreparedStatement updateUserTotalAddNewStatement = conn.prepareStatement(updateUserTotalAddNewQuery)) {
                    for (Integer newUserID : paidForUserIDs) {
                        updateUserTotalAddNewStatement.setDouble(1, newShareAmount);
                        updateUserTotalAddNewStatement.setString(2, this.teamID);
                        updateUserTotalAddNewStatement.setInt(3, newUserID);
                        updateUserTotalAddNewStatement.executeUpdate();
                    }
                }
    
                // Step 11: Commit transaction
                conn.commit();
                System.out.println("Expense and related balances updated successfully.");
    
            } catch (SQLException e) {
                conn.rollback(); // Rollback transaction on error
                System.out.println("Error while updating expense and balances: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
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
            expense exp = new expense(expenseID);
            List<User> paidForUsers = exp.getPaidFor(expenseID);

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
        System.out.println("Paid For: " + Arrays.toString(exp.getPaidFor().stream().map(User::getUsername).toArray(String[]::new))); // Assuming User has getUsername() method
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

    public double getTotal() {
        return total;
    }
}