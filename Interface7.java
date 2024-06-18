import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;



public class Interface7 extends JFrame {

    private JButton Balances = new JButton("SHOW BALANCES");
    private JButton Expenses = new JButton("SHOW EXPENSES");
    private JButton BACK = new JButton("BACK");
    private JButton DeleteTeam = new JButton("DELETE TEAM");

    public Interface7(Team team, User user) {
        // Create a panel with transparent background image
        TransparentBackgroundPanel backgroundPanel = new TransparentBackgroundPanel("background.jpg");

        // Create application icon
        ImageIcon icon = new ImageIcon("icon.jpeg");

        // Resize the image
        Image resizedImage = icon.getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        // Create a panel for the back button and add it to the north
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setOpaque(false); // Make back button panel transparent
        backButtonPanel.setLayout(new BorderLayout());
        customizeButton(BACK);
        Dimension buttonSize = new Dimension(70, 30);
        BACK.setPreferredSize(buttonSize);

        JPanel backButtonWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonWrapper.setOpaque(false); // Make wrapper panel transparent
        backButtonWrapper.add(BACK);
        backButtonPanel.add(backButtonWrapper, BorderLayout.WEST);

        // Create and customize the JLabel and JTextField for the team ID
        JLabel teamIDLabel = new JLabel("TEAM ID:");
        JTextField teamIDField = new JTextField(team.getTeamID());
        teamIDField.setEditable(false); // Make the text field non-editable
        customizeLabel(teamIDLabel);
        customizeTextField(teamIDField);

        JPanel teamIDPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        teamIDPanel.setOpaque(false); // Make the team ID panel transparent
        teamIDPanel.add(teamIDLabel);
        teamIDPanel.add(teamIDField);
        backButtonPanel.add(teamIDPanel, BorderLayout.EAST);

        // Create a panel to hold the center buttons
        JPanel centerButtonPanel = new JPanel(new GridBagLayout());
        centerButtonPanel.setOpaque(false); // Make center button panel transparent
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 100, 10, 100);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel teamTotalLabel = new JLabel("TEAM TOTAL:");
        JLabel myTotalLabel = new JLabel("MY TOTAL:");
        JTextField teamTotalField = new JTextField(10);
        JTextField myTotalField = new JTextField(10);
        teamTotalField.setEditable(false); // Make the text field non-editable
        myTotalField.setEditable(false); // Make the text field non-editable
        customizeLabel(teamTotalLabel);
        customizeTextField(teamTotalField);
        customizeLabel(myTotalLabel);
        customizeTextField(myTotalField);

        // Add the teamTotalLabel and teamTotalField to the centerButtonPanel
        JPanel totalPanel = new JPanel(new FlowLayout());
        totalPanel.setOpaque(false);
        totalPanel.add(teamTotalLabel);
        totalPanel.add(teamTotalField);
        totalPanel.add(myTotalLabel);
        totalPanel.add(myTotalField);
        centerButtonPanel.add(totalPanel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        customizeButton(Expenses);
        centerButtonPanel.add(Expenses, gbc);

        gbc.gridx++;
        customizeButton(Balances);
        centerButtonPanel.add(Balances, gbc);

        // Create a panel for the delete team button and add it to the south
        JPanel deleteButtonPanel = new JPanel();
        deleteButtonPanel.setOpaque(false);
        customizeButton(DeleteTeam);
        deleteButtonPanel.add(DeleteTeam);

        // Create a main panel to hold everything together
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false); // Make main panel transparent
        mainPanel.add(backButtonPanel, BorderLayout.NORTH);
        mainPanel.add(centerButtonPanel, BorderLayout.CENTER);
        mainPanel.add(deleteButtonPanel, BorderLayout.SOUTH);

        // Create a layered pane to layer the background and the main panel
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 600));

        // Add the background panel to the layered pane
        backgroundPanel.setBounds(0, 0, 800, 600);
        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

        // Add the main panel to the layered pane
        mainPanel.setBounds(0, 0, 800, 600);
        layeredPane.add(mainPanel, JLayeredPane.PALETTE_LAYER);

        // Set up the frame
        this.setContentPane(layeredPane);
        this.pack();
        this.setTitle(team.getTitle());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setIconImage(resizedIcon.getImage());

        // Add action listeners
        Expenses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Interface8Expenses(team, user);
            }
        });

        Balances.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean hasNonZeroOwes = checkNonZeroOwes(team);

                if (team.getUsers().size() == 1 || !hasNonZeroOwes) {
                    JOptionPane.showMessageDialog(null, "There are no Balances.");
                } else {
                    new Interface11Balances(team, user);
                    dispose(); // Close Interface7 when opening Interface11Balances
                }
            }
        });

        BACK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Interface4(user);
                dispose();
            }
        });

        DeleteTeam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this team?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    // Assuming Team class has a deleteTeam method
                    boolean deleted = team.deleteTeam();
                    if (deleted) {
                        JOptionPane.showMessageDialog(null, "Team deleted successfully.");
                        new Interface4(user);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to delete team. Please try again.");
                    }
                }
            }
        });
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

    private void customizeLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 16)); // Set font and size
        label.setForeground(Color.BLACK); // Set text color
    }

    private void customizeTextField(JTextField textField) {
        textField.setPreferredSize(new Dimension(100, 40)); // Set preferred size
        textField.setFont(new Font("Arial", Font.BOLD, 16)); // Set font and size
        textField.setForeground(Color.BLACK); // Set text color
        textField.setBorder(new LineBorder(Color.BLACK, 2)); // Set border
    }

    private boolean checkNonZeroOwes(Team team) {
        boolean hasNonZeroOwes = false;

        try (Connection conn = XAMPPConnection.getConnection()) {
            String selectOwesQuery = "SELECT COUNT(*) AS nonZeroCount FROM owes WHERE teamID = ? AND Amount <> 0";
            PreparedStatement selectOwesStatement = conn.prepareStatement(selectOwesQuery);
            selectOwesStatement.setString(1, team.getTeamID()); // Assuming getTeamID() retrieves the team's ID

            ResultSet owesResultSet = selectOwesStatement.executeQuery();
            if (owesResultSet.next()) {
                int nonZeroCount = owesResultSet.getInt("nonZeroCount");
                hasNonZeroOwes = nonZeroCount > 0;
            }
        } catch (SQLException ex) {
            System.out.println("Error checking non-zero owes: " + ex.getMessage());
        }

        return hasNonZeroOwes;
    }
}