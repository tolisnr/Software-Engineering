import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Interface4 extends JFrame {

    private JPanel panel = new JPanel();
    private JButton JoinTeam = new JButton("JOIN TEAM");
    private JButton CreateTeam = new JButton("CREATE TEAM");
    private JButton BACK = new JButton("BACK");
    private JList<String> teams = new JList<>();

    public Interface4(User user) {
        // Create a panel with transparent background image
        TransparentBackgroundPanel backgroundPanel = new TransparentBackgroundPanel("background.jpg");
        backgroundPanel.setLayout(new BorderLayout());

        // Create application icon
        ImageIcon icon = new ImageIcon("icon.jpeg");

        // Resize the image
        Image resizedImage = icon.getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        ArrayList<Team> userTeams = user.getTeams();
        ArrayList<String> teamTitles = new ArrayList<>();
        for (Team team : userTeams) {
            teamTitles.add(team.getTitle());
        }

        teams = new JList<>(teamTitles.toArray(new String[0]));
        teams.setOpaque(false);
        teams.setBackground(new Color(0, 0, 0, 0));
        customizeComponent(teams);

        JScrollPane scrollPane = new JScrollPane(teams);
        scrollPane.setPreferredSize(new Dimension(300, 150)); // Set preferred size for the list

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.add(scrollPane);

        backgroundPanel.add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(CreateTeam);
        buttonPanel.add(JoinTeam);

        customizeButton(CreateTeam);
        customizeButton(JoinTeam);
        customizeButton(BACK, new Dimension(100, 30));

        CreateTeam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Interface6CreateTeam(user);
                dispose();
            }
        });

        JoinTeam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Interface5JoinTeam(user);
                dispose();
            }
        });

        BACK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Interface1();
                dispose();
            }
        });

        teams.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = teams.locationToIndex(e.getPoint());
                    String selectedTitle = teamTitles.get(index);
                    Team selectedTeam = Team.getTeamByTitle(userTeams, selectedTitle);
                    if (selectedTeam != null) {
                        new Interface7(selectedTeam, user); // Navigate to Interface7 with selected team
                        dispose();
                    }
                }
            }
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        topPanel.add(BACK);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setOpaque(false);
        bottomPanel.add(CreateTeam);
        bottomPanel.add(JoinTeam);

        backgroundPanel.add(topPanel, BorderLayout.NORTH);
        backgroundPanel.add(panel, BorderLayout.CENTER);
        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Set up the frame
        setTitle("Budget Buddies");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        setContentPane(backgroundPanel);
        setVisible(true);
        this.setIconImage(resizedIcon.getImage());
    }

    private void customizeButton(JButton button) {
        customizeButton(button, new Dimension(200, 50));
    }

    private void customizeButton(JButton button, Dimension size) {
        button.setPreferredSize(size);
        button.setBackground(Color.GRAY); // Set background color
        button.setForeground(Color.BLACK); // Set text color
        button.setFont(new Font("Arial", Font.BOLD, 16)); // Set font and size
        Border border = new LineBorder(Color.BLACK, 2); // Black border with thickness 2
        button.setBorder(border);
        button.setFocusable(false);
    }

    private void customizeComponent(JComponent component) {
        component.setBackground(Color.WHITE); // Set background color
        component.setForeground(Color.BLACK); // Set text color
        component.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font and size
        Border border = new LineBorder(Color.BLACK, 2); // Black border with thickness 2
        component.setBorder(border);
    }
}