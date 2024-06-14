import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

public class User {
    public int userID;
    public String username;
    public String password;
    public ArrayList<Team> Teams = new ArrayList<>();

    public User(String name, String password, int userID) {
        this.username = name;
        this.password = password;
        this.userID = userID;
    }

    public void JoinTeam(Team team) {
        Teams.add(team);
        try (Connection conn = XAMPPConnection.getConnection()) {
            // Εισαγωγή στον πίνακα συσχέτισης teams_users
            String insertTeamUserQuery = "INSERT INTO teams_users (teamID, userID) VALUES (?, ?)";
            PreparedStatement teamUserStatement = conn.prepareStatement(insertTeamUserQuery);
            teamUserStatement.setString(1, team.getTeamID());
            teamUserStatement.setInt(2, this.userID);
            teamUserStatement.executeUpdate();

            System.out.println("Team and user added to the database.");
        } catch (SQLException e) {
            System.out.println("Error while adding team to the database: " + e.getMessage());
        }
    }

    public void CreateTeam(String title, String category) {
        String teamID = generateRandomCode();
        Team group = new Team(teamID, title, category, this.userID);

        try (Connection conn = XAMPPConnection.getConnection()) {
            
            // Εισάγετε τη νέα ομάδα στον πίνακα team
            String insertTeamQuery = "INSERT INTO team (teamID, title, category, admin) VALUES (?, ?, ?, ?)";
            PreparedStatement teamStatement = conn.prepareStatement(insertTeamQuery);
            teamStatement.setString(1, group.getTeamID());
            teamStatement.setString(2, title);
            teamStatement.setString(3, category);
            teamStatement.setInt(4, this.userID);
            teamStatement.executeUpdate();

            // Εισάγετε τον χρήστη στην ομάδα στον πίνακα teams_users
            String insertTeamUserQuery = "INSERT INTO teams_users (teamID, userID) VALUES (?, ?)";
            PreparedStatement teamUserStatement = conn.prepareStatement(insertTeamUserQuery);
            teamUserStatement.setString(1, group.getTeamID());
            teamUserStatement.setInt(2, this.userID);
            teamUserStatement.executeUpdate();

            System.out.println("Team created and added to the database.");
        } catch (SQLException e) {
            System.out.println("Error while creating and adding team to the database: " + e.getMessage());
        }

        JOptionPane.showMessageDialog(null, "Ο κωδικός της ομάδας είναι:\n" + group.getTeamID());
        
        group.addUser(this);
    }

    
    public void InsertUserinDataBase(User user) {
        String url = "jdbc:mysql://localhost:3306/budgedbuddies"; // Το URL της βάσης δεδομένων σας
        String username = "root"; // Το όνομα χρήστη της βάσης δεδομένων
        String password = ""; // Ο κωδικός πρόσβασης στη βάση δεδομένων

        // Προσθήκη νέου χρήστη στη βάση δεδομένων
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Ο νέος χρήστης προστέθηκε με επιτυχία!");

                // Λήψη του αυξανόμενου userID
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.userID = generatedKeys.getInt(1); // Ενημέρωση του userID του αντικειμένου χρήστη
                        System.out.println("Το αυξανόμενο userID είναι: " + user.userID);
                    } else {
                        System.out.println("Αποτυχία λήψης του αυξανόμενου userID.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Προέκυψε σφάλμα κατά την προσθήκη νέου χρήστη: " + e.getMessage());
        }
    }

    public boolean doesUserExist(String username) {
        String url = "jdbc:mysql://localhost:3306/budgedbuddies"; // Το URL της βάσης δεδομένων σας
        String dbUsername = "root"; // Το όνομα χρήστη της βάσης δεδομένων
        String dbPassword = ""; // Ο κωδικός πρόσβασης στη βάση δεδομένων

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "SELECT 1 FROM users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Προέκυψε σφάλμα κατά τον έλεγχο ύπαρξης χρήστη: " + e.getMessage());
            return false;
        }
    }
    
    private String generateRandomCode() {
        // Χαρακτήρες που θα μπορούν να περιλαμβάνονται στον κωδικό
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        // Δημιουργία αντικειμένου Random
        Random random = new Random();
        // Δημιουργία StringBuilder για την κατασκευή του κωδικού
        StringBuilder codeBuilder = new StringBuilder();
        // Δημιουργία κωδικού με 6 χαρακτήρες
        for (int i = 0; i < 6; i++) {
            // Επιλογή τυχαίου χαρακτήρα από το σύνολο των διαθέσιμων χαρακτήρων
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            // Προσθήκη του τυχαίου χαρακτήρα στον κωδικό
            codeBuilder.append(randomChar);
        }
        // Επιστροφή του τελικού κωδικού ως String
        return codeBuilder.toString();
    }

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

    

    public ArrayList<Team> getTeams() {
        ArrayList<Team> teams = new ArrayList<>();

        String query = "SELECT t.teamID, t.title, t.category, t.admin " +
                       "FROM team t " +
                       "INNER JOIN teams_users tu ON t.teamID = tu.teamID " +
                       "WHERE tu.userID = ?";
        
        try (Connection conn = XAMPPConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, this.userID);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String teamID = rs.getString("teamID");
                String title = rs.getString("title");
                String category = rs.getString("category");
                int adminID = rs.getInt("admin");

                // Create Team object and add to the list
                Team team = new Team(teamID, title, category, adminID);
                teams.add(team);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching teams for user: " + e.getMessage());
        }

        return teams;
    }

    public int getUserID() {
        return userID;
    }

}
