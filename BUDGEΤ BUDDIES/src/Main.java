
import java.sql.Connection;
import java.sql.SQLException;



public class Main {
	public static void main(String[] args) {

		new Interface1();
		
		try {
            Connection conn = XAMPPConnection.getConnection();
            // Εδώ μπορείτε να κάνετε χρήση της σύνδεσης για να εκτελέσετε ερωτήματα SQL ή να επεξεργαστείτε τα δεδομένα σας.
            // Μην ξεχάσετε να κλείσετε τη σύνδεση μετά τη χρήση της!
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
