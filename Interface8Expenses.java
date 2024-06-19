import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;


public class Interface8Expenses extends JFrame {

    private JButton BACK = new JButton("BACK");
    private JButton addExpense = new JButton("ADD EXPENSE");
    private JList<String> expensesList;

    public Interface8Expenses(Team team, User user) {

        // Create background panel
        BackgroundImageExample backgroundPanel = new BackgroundImageExample("background.jpg");

        // Create application icon
        ImageIcon icon = new ImageIcon("icon.jpeg");

        // Resize the image
        Image resizedImage = icon.getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        // Buttons Appearance
        customizeButton(BACK);
        customizeButton(addExpense);

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Make button panel transparent
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center buttons
        buttonPanel.add(addExpense);

        // Create a layered pane to layer the background and the panels
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 600));

        // Add the background panel to the layered pane
        backgroundPanel.setBounds(0, 0, 800, 600);
        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

        // Add the panel with the buttons to the layered pane
        buttonPanel.setBounds(300, 500, 200, 50);
        layeredPane.add(buttonPanel, JLayeredPane.PALETTE_LAYER);

        // Add the BACK button to the top left corner of the layered pane
        BACK.setBounds(10, 10, 80, 30);
        layeredPane.add(BACK, JLayeredPane.PALETTE_LAYER);

        ArrayList<expense> expensesTeam = team.getExpenses();
        ArrayList<String> expensesTitles = new ArrayList<>();
        for (expense ex : expensesTeam) {
            expensesTitles.add(ex.getTitle());
        }
        expensesList = new JList<>(expensesTitles.toArray(new String[0]));

        expensesList.setFixedCellHeight(30); // Set the height of each cell
        expensesList.setFixedCellWidth(250); // Set the width of each cell
                                                                   
        //Create a panel to hold the list
        JPanel listPanel = new JPanel();
        listPanel.setOpaque(false); // Make list panel transparent
        listPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center list
        listPanel.add(new JScrollPane(expensesList));

        // Add the list panel to the layered pane
        listPanel.setBounds(200, 100, 400, 350);
        layeredPane.add(listPanel, JLayeredPane.PALETTE_LAYER);

        // Add a ListSelectionListener to handle double-click event
        expensesList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedIndex = expensesList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        expense selectedExpense = expensesTeam.get(selectedIndex);
                        new Interface10AExpense(team, user, selectedExpense).setVisible(true);
        			    dispose();
                    }
                }
            }
        });


        addExpense.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Interface9AddExpense(team, user);
                dispose();
            }
        });

        BACK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Interface7(team, user);
                dispose();
            }
        });

        this.setContentPane(layeredPane);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setIconImage(resizedIcon.getImage());
        this.setTitle("Expenses");
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