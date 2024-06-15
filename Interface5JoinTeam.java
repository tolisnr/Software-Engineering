import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;

public class Interface5JoinTeam extends JFrame{

	private JLabel text = new JLabel("Δώστε τον κωδικό της ομάδας: ");
	private JTextField teamIDField = new JTextField();
	private JButton OK = new JButton("OK");
	private JButton BACK = new JButton("BACK");
	
	public Interface5JoinTeam(User user) {

		// Create background panel
		BackgroundImageExample backgroundPanel = new BackgroundImageExample("background.jpg");

		// Create application icon
		ImageIcon icon = new ImageIcon("icon.jpeg");

		// Resize the image
		Image resizedImage = icon.getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT);
		ImageIcon resizedIcon = new ImageIcon(resizedImage);

		// Create a panel to hold the buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false); // Make button panel transparent
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 0)); // Center buttons with space between

		// Buttons Appearance
		customizeButton(OK);
		customizeButton(BACK);

		// Add buttons to the button panel
		buttonPanel.add(OK);
		buttonPanel.add(BACK);

		// Add text field and label to a new panel
		JPanel textPanel = new JPanel();
		textPanel.setOpaque(false); // Make text panel transparent
		textPanel.add(text);
		teamIDField.setPreferredSize(new Dimension(200, 30)); // Set preferred size
		teamIDField.setMaximumSize(new Dimension(200, 30)); // Set maximum size
		textPanel.add(teamIDField);

		// Create a layered pane to layer the background and the button panel
		JLayeredPane layeredPane = new JLayeredPane();

		// Add the background panel to the layered pane
		backgroundPanel.setBounds(0, 0, 800, 600);
		layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

		// Add the button panel to the layered pane
		buttonPanel.setBounds(0, 300, 800, 600);
		layeredPane.add(buttonPanel, JLayeredPane.PALETTE_LAYER);

		// Add the text panel to the layered pane
		textPanel.setBounds(0, 200, 800, 600);
		layeredPane.add(textPanel, JLayeredPane.PALETTE_LAYER);

		layeredPane.setPreferredSize(new Dimension(800, 600));
		
		OK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(TeamInDataBase(teamIDField.getText()) != null) {
					user.JoinTeam(TeamInDataBase(teamIDField.getText()));
					TeamInDataBase(teamIDField.getText()).addUser(user);
					new Interface4(user);
					dispose();
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
		
		this.setContentPane(layeredPane);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setIconImage(resizedIcon.getImage());
        this.setTitle("Join Team");
		this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public Team TeamInDataBase(String teamID) {
        try (Connection conn = XAMPPConnection.getConnection()) {
            String query = "SELECT * FROM team WHERE teamID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, teamID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String category = resultSet.getString("category");
                int admin = resultSet.getInt("admin");

                Team team = new Team(teamID, title, category, admin);
                System.out.println("Team with ID " + teamID + " exists in the database.");
                return team;
            } else {
                System.out.println("No team with ID " + teamID + " found in the database.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error while searching for team: " + e.getMessage());
            return null;
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
