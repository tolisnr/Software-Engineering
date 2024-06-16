import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Interface11Balances extends JFrame {

    private JPanel panel = new JPanel();
    private JButton BACK = new JButton("Back");
    private JTextArea balancesTextArea = new JTextArea(20, 30);

    public Interface11Balances(Team team, User user) {
        panel.setLayout(new BorderLayout());

        panel.add(new JScrollPane(balancesTextArea), BorderLayout.CENTER);
        panel.add(BACK, BorderLayout.SOUTH);

        this.setContentPane(panel);
        this.setTitle("Balances");
        this.setSize(400, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up ActionListener for Back button
        BACK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Interface7(team, user); // Navigate back to Interface7
                dispose(); // Close current frame
            }
        });

        // Fetch and display owes data
        displayOwes(team);

        // Make sure the frame is visible
        this.setVisible(true);
    }

    private void displayOwes(Team team) {
        balancesTextArea.setText(""); // Clear existing text

        try (Connection conn = XAMPPConnection.getConnection()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Database connection is null. Cannot fetch data.");
                return;
            }

            String selectOwesQuery = "SELECT o.Amount, o.winID, o.lossID, u1.username AS winUsername, u2.username AS lossUsername " +
                    "FROM owes o " +
                    "JOIN users u1 ON o.winID = u1.userID " +
                    "JOIN users u2 ON o.lossID = u2.userID " +
                    "WHERE o.teamID = ? AND o.Amount <> 0";
            PreparedStatement selectOwesStatement = conn.prepareStatement(selectOwesQuery);
            selectOwesStatement.setString(1, team.getTeamID());

            ResultSet owesResultSet = selectOwesStatement.executeQuery();
            while (owesResultSet.next()) {
                double amount = owesResultSet.getDouble("Amount");
                String winUsername = owesResultSet.getString("winUsername");
                String lossUsername = owesResultSet.getString("lossUsername");

                String oweString = String.format("%s owes to %s: %.2f\n", lossUsername, winUsername, amount);
                balancesTextArea.append(oweString);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching owes data: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
    }
}
