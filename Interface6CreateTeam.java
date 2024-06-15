import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Interface6CreateTeam extends JFrame{

	private JLabel titleText = new JLabel("Δώστε το τίτλο της ομάδας: ");
	private JTextField titleField = new JTextField();
	private JLabel categoryText = new JLabel("Δώστε την κατηγορία: ");
	private JTextField categoryField = new JTextField();
	private JButton Save = new JButton("SAVE");
	private JButton BACK = new JButton("BACK");
	
	public Interface6CreateTeam(User user) {

		// Create background panel
		BackgroundImageExample backgroundPanel = new BackgroundImageExample("background.jpg");

		// Create application icon
		ImageIcon icon = new ImageIcon("icon.jpeg");

		// Resize the image
		Image resizedImage = icon.getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT);
		ImageIcon resizedIcon = new ImageIcon(resizedImage);

		// Create a panel to hold the components
		JPanel detailsPanel = new JPanel();
		detailsPanel.setOpaque(false); // Make details panel transparent
		detailsPanel.setLayout(new GridLayout(3, 2, 10, 10)); // Grid layout for the labels and text fields

		titleField.setPreferredSize(new Dimension(200, 30));
		categoryField.setPreferredSize(new Dimension(200, 30));

		detailsPanel.add(titleText);
		detailsPanel.add(titleField);
		detailsPanel.add(categoryText);
		detailsPanel.add(categoryField);

		// Buttons Appearance
		customizeButton(Save);
		customizeButton(BACK);

		// Create a panel to hold the buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false); // Make button panel transparent
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center buttons

		// Add buttons to the button panel
		buttonPanel.add(Save);

		// Create a layered pane to layer the background and the panels
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(800, 600));

		// Add the background panel to the layered pane
		backgroundPanel.setBounds(0, 0, 800, 600);
		layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

		// Add the details panel to the layered pane
		detailsPanel.setBounds(250, 200, 300, 150);
		layeredPane.add(detailsPanel, JLayeredPane.PALETTE_LAYER);

		// Add the button panel to the layered pane
		buttonPanel.setBounds(250, 370, 300, 50);
		layeredPane.add(buttonPanel, JLayeredPane.PALETTE_LAYER);

		// Add the BACK button to the top left corner of the layered pane
		BACK.setBounds(10, 10, 80, 30);
		layeredPane.add(BACK, JLayeredPane.PALETTE_LAYER);
		
		Save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				user.CreateTeam(titleField.getText(), categoryField.getText());
				new Interface4(user);
				dispose();
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
		this.setTitle("Create Team");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
