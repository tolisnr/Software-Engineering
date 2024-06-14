import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Interface3NewUser extends JFrame {

    // buttons
    private JButton save = new JButton("Save");
    private JButton BACK = new JButton("Back");

    // labels
    private JLabel text1 = new JLabel("Enter Username:");
    private JLabel text2 = new JLabel("Enter Password:");
    
    // textfields
    private JTextField username = new JTextField();
    private JPasswordField password = new JPasswordField();

    public Interface3NewUser() {
        // Create a panel with transparent background image
        TransparentBackgroundPanel backgroundPanel = new TransparentBackgroundPanel("background.jpg");

        // Create application icon
        ImageIcon icon = new ImageIcon("icon.jpeg");

        // Resize the image
        Image resizedImage = icon.getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        // Customize font for labels and text fields
        Font labelFont = new Font("Arial", Font.BOLD, 14); // Adjust the font size here
        Font textFieldFont = new Font("Arial", Font.PLAIN, 16);
        text1.setFont(labelFont);
        text2.setFont(labelFont);
        username.setFont(textFieldFont);
        password.setFont(textFieldFont);

        // Create a panel to hold the login components
        JPanel loginPanel = new JPanel();
        loginPanel.setOpaque(false); // Make login panel transparent
        loginPanel.setLayout(new GridLayout(3, 2, 10, 10)); // Grid layout for the labels and text fields

        // Buttons Appearance
        customizeButton(save);
        customizeButton(BACK);

        // Add components to the login panel
        loginPanel.add(text1);
        loginPanel.add(username);
        loginPanel.add(text2);
        loginPanel.add(password);

        // Create a panel to hold the button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Make button panel transparent
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center buttons

        // Add buttons to the button panel
        buttonPanel.add(save);

        // Create a layered pane to layer the background and the panels
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 600));

        // Add the background panel to the layered pane
        backgroundPanel.setBounds(0, 0, 800, 600);
        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

        // Add the login panel to the layered pane
        loginPanel.setBounds(250, 200, 300, 150);
        layeredPane.add(loginPanel, JLayeredPane.PALETTE_LAYER);

        // Add the button panel to the layered pane
        buttonPanel.setBounds(250, 370, 300, 50);
        layeredPane.add(buttonPanel, JLayeredPane.PALETTE_LAYER);

        // Add the BACK button to the top left corner of the layered pane
        BACK.setBounds(10, 10, 80, 30);
        layeredPane.add(BACK, JLayeredPane.PALETTE_LAYER);

        // Set up the frame
        this.setContentPane(layeredPane);
        this.pack();
        this.setTitle("Create Account");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setIconImage(resizedIcon.getImage());

        username.setPreferredSize(new Dimension(200, 30));
        password.setPreferredSize(new Dimension(200, 30));

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = 0;
                User user = new User(username.getText(), new String(password.getPassword()), id);

                if (!user.doesUserExist(user.getUsername())) {
                    user.InsertUserinDataBase(user);
                    JOptionPane.showMessageDialog(null, "Ο λογαριασμός δημιουργήθηκε επιτυχώς!");
                } else {
                    JOptionPane.showMessageDialog(null, "Ο χρήστης υπάρχει ήδη.");
                }

                new Interface2Login(); // Assuming this handles login interface
                dispose(); // Dispose the current frame (Interface3NewUser)
            }
        });

        BACK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Interface1();
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
        Border border = new LineBorder(Color.BLACK, 2); // Black border with thickness 2
        button.setBorder(border);
        button.setFocusable(false);
    }

    public static void main(String[] args) {
        new Interface3NewUser();
    }
}

class TransparentBackgroundPanel extends JPanel {
    private BufferedImage image;

    public TransparentBackgroundPanel(String fileName) {
        try {
            image = ImageIO.read(new File(fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // Set transparency level
            g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            g2d.dispose();
        }
    }
}

