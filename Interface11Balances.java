import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Interface11Balances extends JFrame {

    private JButton BACK = new JButton("Back");
    private JTable balancesTable;
    private DefaultTableModel tableModel;
    private JLabel instructionLabel = new JLabel("If a balance is paid double click on it.");

    public Interface11Balances(Team team, User user) {

        // Create background panel
        TransparentBackgroundPanel backgroundPanel = new TransparentBackgroundPanel("background.jpg");

        // Create application icon
        ImageIcon icon = new ImageIcon("icon.jpeg");

        // Resize the image
        Image resizedImage = icon.getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        // Buttons Appearance
        customizeButton(BACK);

        // Create a layered pane to layer the background and the panels
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 600));

        // Add the background panel to the layered pane
        backgroundPanel.setBounds(0, 0, 800, 600);
        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

        // Set up the table and its model
        tableModel = new DefaultTableModel(new Object[]{"Loss Username", "Win Username", "Amount"}, 0);
        balancesTable = new JTable(tableModel);
        balancesTable.setOpaque(false); // Make table transparent

        // Custom cell renderer to set cell background to white and thick black border
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Set thick black border
                setBackground(Color.WHITE); // Set background color
                setOpaque(true);
                return this;
            }
        };

        // Apply the custom renderer to all columns
        for (int i = 0; i < balancesTable.getColumnCount(); i++) {
            balancesTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        // Set default cell margins to zero
        balancesTable.setIntercellSpacing(new Dimension(0, 0));

        // Set thicker grid lines
        balancesTable.setGridColor(Color.BLACK);
        balancesTable.setShowGrid(true);
        balancesTable.setRowHeight(30); // Adjust row height for better visibility

        JScrollPane tableScrollPane = new JScrollPane(balancesTable);
        tableScrollPane.setOpaque(false); // Make scroll pane transparent
        tableScrollPane.getViewport().setOpaque(false); // Make viewport transparent

        // Create a panel to hold the table
        JPanel tablePanel = new JPanel();
        tablePanel.setOpaque(false); // Make table panel transparent
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Add the table panel to the layered pane
        tablePanel.setBounds(100, 100, 600, 400);
        layeredPane.add(tablePanel, JLayeredPane.PALETTE_LAYER);

        // Customize and add the instruction label
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel instructionPanel = new JPanel();
        instructionPanel.setOpaque(false); // Make instruction panel transparent
        instructionPanel.add(instructionLabel);

        // Add the instruction panel below the table panel
        instructionPanel.setBounds(100, 500, 600, 30);
        layeredPane.add(instructionPanel, JLayeredPane.PALETTE_LAYER);

        // Add the BACK button to the top left corner of the layered pane
        BACK.setBounds(10, 10, 80, 30);
        layeredPane.add(BACK, JLayeredPane.PALETTE_LAYER);

        this.setContentPane(layeredPane);
        this.setIconImage(resizedIcon.getImage());
        this.setTitle("Balances");
        this.setSize(800, 600);
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

                System.out.println("Adding row: " + lossUsername + ", " + winUsername + ", " + amount); // Debugging

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

    private void customizeButton(JButton button) {
        Dimension buttonSize = new Dimension(200, 50);
        button.setPreferredSize(buttonSize);
        button.setBackground(Color.GRAY); // Set background color
        button.setForeground(Color.BLACK); // Set text color
        button.setFont(new Font("Arial", Font.BOLD, 16)); // Set font and size
        Border border = new LineBorder(Color.BLACK, 2); // Black border with thickness 2
        button.setBorder(border);
        button.setFocusable(false);
    }
    
}