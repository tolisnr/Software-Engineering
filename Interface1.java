import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Interface1 extends JFrame{

	private JButton CreateUser = new JButton("Create a new Account");
	private JButton Login = new JButton("Login");

	public Interface1() {
        // Create background panel
        BackgroundImageExample backgroundPanel = new BackgroundImageExample("background.jpg");

        //Create application icon
        ImageIcon icon = new ImageIcon("icon.jpeg");

        // Resize the image
        Image resizedImage = icon.getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Make button panel transparent
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 0)); // Center buttons with space between

        // Buttons Appearance
		customizeButton(CreateUser);
		customizeButton(Login);

        // Add buttons to the button panel
        buttonPanel.add(CreateUser);
        buttonPanel.add(Login);

        // Create a layered pane to layer the background and the button panel
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 600));

        // Add the background panel to the layered pane
        backgroundPanel.setBounds(0, 0, 800, 600);
        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

        // Add the button panel to the layered pane
        buttonPanel.setBounds(0, 300, 800, 600);
        layeredPane.add(buttonPanel, JLayeredPane.PALETTE_LAYER);

        // Set up the frame
        this.setContentPane(layeredPane);
        this.pack();
        this.setTitle("Budget Buddy");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setIconImage(resizedIcon.getImage());

        // Add action listeners
        CreateUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Interface3NewUser();
                dispose();
            }
        });

        Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Interface2Login();
                dispose();
            }
        });
    }

	private void customizeButton(JButton button) {
		Dimension buttonSize = new Dimension(200, 50);
		button.setPreferredSize(buttonSize);
        button.setBackground(Color.GRAY); // Set background color
        button.setForeground(Color.BLACK); // Set text color
        button.setFont(new Font("Arial", Font.BOLD, 16)); // Set font and size
        button.setPreferredSize(new Dimension(200, 50)); // Set button size
		Border border = new LineBorder(Color.BLACK, 2); // Black border with thickness 2
        button.setBorder(border);
		button.setFocusable(false);
    }
}
