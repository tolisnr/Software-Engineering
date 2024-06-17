
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class XAMPPConnection {

	 private static final String URL = "jdbc:mysql://localhost:3306/budgetbuddies";
	    private static final String USERNAME = "root";
	    private static final String PASSWORD = "";
	    
	    // Μέθοδος για τη σύνδεση στη βάση δεδομένων
	    public static Connection getConnection() throws SQLException {
	        try {
	        	Class.forName("com.mysql.cj.jdbc.Driver");
	            return DriverManager.getConnection(URL, USERNAME, PASSWORD); // Σύνδεση στη βάση δεδομένων
	        } catch (ClassNotFoundException ex) {
	            ex.printStackTrace(); // Εκτύπωση σφάλματος, αν δεν μπορέσει να βρει τον Driver
	            throw new SQLException("Database Connection Error");
	        }
	    }
}
