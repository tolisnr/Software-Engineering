import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Interface9AddExpense extends JFrame {

    // Buttons
    private JButton BACK = new JButton("Back");
    private JButton saveButton = new JButton("Save");
    // Text fields
    private JTextField titleField;
    private JTextField amountField;
    // Date field
    private JSpinner dateSpinner;
    // Lists
    private JList<String> payerList;
    private List<JCheckBox> paidForCheckboxes;
    // Labels
    private JLabel text1 = new JLabel("Title:");
    private JLabel text2 = new JLabel("Amount:");
    private JLabel text3 = new JLabel("Date:");
    private JLabel text4 = new JLabel("Payer:");
    private JLabel text5 = new JLabel("Paid For:");

    public Interface9AddExpense(Team team, User user) {

        // Create a panel with transparent background image
        TransparentBackgroundPanel backgroundPanel = new TransparentBackgroundPanel("background.jpg");
        backgroundPanel.setLayout(new BorderLayout());

        // Create application icon
        ImageIcon icon = new ImageIcon("icon.jpeg");

        // Resize the image
        Image resizedImage = icon.getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        List<User> teamUsers = team.getUsers();
        List<String> userNames = new ArrayList<>();
        for (User u : teamUsers) {
            userNames.add(u.getUsername());
        }

        payerList = new JList<>(userNames.toArray(new String[0]));
        payerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        payerList.setOpaque(false);
        payerList.setBackground(new Color(0, 0, 0, 0));
        customizeComponent(payerList);

        JPanel paidForPanel = new JPanel();
        paidForPanel.setLayout(new BoxLayout(paidForPanel, BoxLayout.Y_AXIS));
        paidForPanel.setOpaque(false);
        paidForCheckboxes = new ArrayList<>();
        for (String username : userNames) {
            JCheckBox checkBox = new JCheckBox(username);
            checkBox.setOpaque(false);
            customizeComponent(checkBox);
            paidForCheckboxes.add(checkBox);
            paidForPanel.add(checkBox);
        }

        // Buttons Appearance
        customizeButton(saveButton);
        customizeButton(BACK, new Dimension(100, 30));

        titleField = new JTextField();
        customizeTextField(titleField);

        amountField = new JTextField();
        customizeTextField(amountField);

        dateSpinner = new JSpinner(new SpinnerDateModel());
        customizeComponent(dateSpinner);

        // Customize font for labels and text fields
        Font labelFont = new Font("Arial", Font.BOLD, 18); // Adjust the font size here

        // Set up GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add components to the form panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(text1, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(text2, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(text3, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(dateSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(text4, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(new JScrollPane(payerList), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(text5, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(new JScrollPane(paidForPanel), gbc);

        text1.setFont(labelFont);
        text2.setFont(labelFont);
        text3.setFont(labelFont);
        text4.setFont(labelFont);
        text5.setFont(labelFont);
        titleField.setFont(new Font("Arial", Font.PLAIN, 16));
        amountField.setFont(new Font("Arial", Font.PLAIN, 16));
        dateSpinner.setFont(new Font("Arial", Font.PLAIN, 16));
        payerList.setFont(new Font("Arial", Font.PLAIN, 16));
        paidForPanel.setFont(new Font("Arial", Font.PLAIN, 16));

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String title = titleField.getText();
                    double amount = Double.parseDouble(amountField.getText());
                    Date date = (Date) dateSpinner.getValue();
                    int payerIndex = payerList.getSelectedIndex();

                    if (payerIndex == -1) {
                        JOptionPane.showMessageDialog(null, "Please select a payer.");
                        return;
                    }

                    User payer = teamUsers.get(payerIndex);

                    List<User> paidForUsers = new ArrayList<>();
                    for (int i = 0; i < paidForCheckboxes.size(); i++) {
                        if (paidForCheckboxes.get(i).isSelected()) {
                            paidForUsers.add(teamUsers.get(i));
                        }
                    }

                    List<Integer> paidForUsersID = new ArrayList<>();
                    for (User u : paidForUsers) {
                        paidForUsersID.add(u.getUserID());
                    }

                    if (paidForUsers.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please select at least one user for whom the expense is paid.");
                        return;
                    }

                    // Create new expense object
                    int expenseID = 0; // Assuming expenseID is auto-generated
                    expense newExpense = new expense(expenseID, title, amount, date, payer, paidForUsers);

                    // Add expense to team
                    team.addExpense(newExpense, paidForUsersID);

                    // Display success message
                    JOptionPane.showMessageDialog(null, "Expense added successfully.");

                    // Navigate back to the expenses interface
                    new Interface8Expenses(team, user);
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid amount format. Please enter a valid number.");
                }
            }
        });

        BACK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Interface8Expenses(team, user);
                dispose();
            }
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        topPanel.add(BACK);

        backgroundPanel.add(topPanel, BorderLayout.NORTH);
        backgroundPanel.add(formPanel, BorderLayout.CENTER);
        backgroundPanel.add(saveButton, BorderLayout.SOUTH);

        // Set up the frame
        setTitle("Add Expense");
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

    private void customizeTextField(JTextField textField) {
        textField.setOpaque(true); // Ensure the text field is opaque
        textField.setBackground(Color.WHITE); // Set background color
        textField.setForeground(Color.BLACK); // Set text color
        textField.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font and size
        Border border = new LineBorder(Color.BLACK, 2); // Black border with thickness 2
        textField.setBorder(border);
        textField.setCaretColor(Color.BLACK); // Set caret (cursor) color
    }
}
