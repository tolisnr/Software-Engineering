import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Interface11Balances extends JFrame {

    private JPanel panel = new JPanel();
    private JTextField text = new JTextField("If a balance is paid double click on it.");
    private JButton BACK = new JButton("Back");
    private JTable balancesTable;
    private DefaultTableModel tableModel;

    public Interface11Balances(Team team, User user) {
        panel.setLayout(new BorderLayout());

        // Set up the table and its model
        tableModel = new DefaultTableModel(new Object[]{"Loss Username", "Win Username", "Amount"}, 0);
        balancesTable = new JTable(tableModel);
        panel.add(new JScrollPane(balancesTable), BorderLayout.CENTER);
        panel.add(text);
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

        // Add mouse listener to handle row selection
        balancesTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int row = balancesTable.rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    handleRowSelection(row, team);
                }
            }
        });

        // Make sure the frame is visible
        this.setVisible(true);
    }

    private void displayOwes(Team team) {
        tableModel.setRowCount(0); // Clear existing rows

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

                tableModel.addRow(new Object[]{lossUsername, winUsername, amount});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching owes data: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

    private void handleRowSelection(int row, Team team) {
        String lossUsername = (String) balancesTable.getValueAt(row, 0);
        String winUsername = (String) balancesTable.getValueAt(row, 1);
        double amount = (double) balancesTable.getValueAt(row, 2);

        int dialogResult = JOptionPane.showConfirmDialog(this,
                String.format("Did %s pay %.2f to %s?", lossUsername, amount, winUsername),
                "Payment Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (dialogResult == JOptionPane.YES_OPTION) {
            updateOwesAmount(team, lossUsername, winUsername, amount);
        }
    }

    private void updateOwesAmount(Team team, String lossUsername, String winUsername, double amount) {
        try (Connection conn = XAMPPConnection.getConnection()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Database connection is null. Cannot update data.");
                return;
            }

            String updateOwesQuery = "UPDATE owes o " +
                    "JOIN users u1 ON o.winID = u1.userID " +
                    "JOIN users u2 ON o.lossID = u2.userID " +
                    "SET o.Amount = 0 " +
                    "WHERE o.teamID = ? AND u1.username = ? AND u2.username = ? AND o.Amount = ?";
            PreparedStatement updateOwesStatement = conn.prepareStatement(updateOwesQuery);
            updateOwesStatement.setString(1, team.getTeamID());
            updateOwesStatement.setString(2, winUsername);
            updateOwesStatement.setString(3, lossUsername);
            updateOwesStatement.setDouble(4, amount);

            int rowsAffected = updateOwesStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Payment confirmed and balances updated.");
                displayOwes(team); // Refresh the table
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update balances.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating owes data: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
    }
}