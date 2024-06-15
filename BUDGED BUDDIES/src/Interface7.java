import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        //Create application icon
        ImageIcon icon = new ImageIcon("icon.jpeg");

        // Resize the image
        Image resizedImage = icon.getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        // Create a panel for the back button and add it to the north
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setOpaque(false); // Make back button panel transparent
        backButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        customizeButton(BACK);
        Dimension buttonSize = new Dimension(70, 30);
        BACK.setPreferredSize(buttonSize);
        backButtonPanel.add(BACK);

        // Create a panel to hold the center buttons
        JPanel centerButtonPanel = new JPanel(new GridBagLayout());
        centerButtonPanel.setOpaque(false); // Make center button panel transparent
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(150, 100, 0, 100);
        customizeButton(Expenses);
        centerButtonPanel.add(Expenses, gbc);
        customizeButton(Balances);
        centerButtonPanel.add(Balances, gbc);

        //Create a panel for the delete team button and add it to the south
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
                new Interface11Balances();
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


}