
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Interface2Login extends JFrame {

    private JPanel panel = new JPanel();
    private JLabel text1 = new JLabel("Username:");
    private JTextField username = new JTextField();
    private JLabel text2 = new JLabel("Password");
    private JTextField password = new JTextField();
    private JButton login = new JButton("Login");
    private JButton BACK = new JButton("BACK");

    public Interface2Login() {

        panel.add(text1);
        username.setPreferredSize(new Dimension(200, 30));
        panel.add(username);
        panel.add(text2);
        password.setPreferredSize(new Dimension(200, 30));
        panel.add(password);
        panel.add(login);
        panel.add(BACK);
        this.setContentPane(panel);

        login.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Καλέστε την μέθοδο authenticateUser για να ελέγξετε τα στοιχεία σύνδεσης
                String name = username.getText();
                String pass = password.getText();

                if (authenticateUser(name, pass) != null) {
                    System.out.println("User authenticated successfully.");
                    new Interface4(authenticateUser(name, pass));
                    dispose();
                } else {
                	JOptionPane.showMessageDialog(null, "Κάτι πήγε στραβά! Δοκιμάστε ξανά! Έχετε λογαριασμο;");
                }
            }

        });

        BACK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Interface1();
				dispose();
			}
			
		});
        
        this.setVisible(true);
        this.setSize(300, 300);
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Μέθοδος για επαλήθευση των στοιχείων σύνδεσης στη βάση δεδομένων
    public static User authenticateUser(String username, String password) {
        try (Connection conn = XAMPPConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int userID = resultSet.getInt("userID");
                System.out.println("Login successful! User ID: " + userID);
                User user = new User(username, password, userID);
                return user;
            } else {
                System.out.println("Login failed. Incorrect username or password.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
            return null;
        }
    }

    
}

