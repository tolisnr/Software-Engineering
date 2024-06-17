import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Interface12EditExpense extends JFrame {

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

    public Interface12EditExpense(Team team, User user, expense existingExpense) {

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

        titleField = new JTextField(existingExpense.getTitle());
        customizeTextField(titleField);

        amountField = new JTextField(String.valueOf(existingExpense.getAmount()));
        customizeTextField(amountField);

        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setValue(existingExpense.getDate());
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

        // Preselect payer in the list if valid
        User payer = existingExpense.getPayer();
        if (payer != null) {
            int payerIndex = teamUsers.indexOf(payer);
            if (payerIndex != -1) {
                payerList.setSelectedIndex(payerIndex);
            }
        }

        // Preselect paidFor checkboxes based on existing expense
        List<User> paidForUsers = existingExpense.getPaidFor();
        for (User paidForUser : paidForUsers) {
            int index = teamUsers.indexOf(paidForUser);
            if (index != -1 && index < paidForCheckboxes.size()) {
                paidForCheckboxes.get(index).setSelected(true);
            }
        }

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

                    // Update existing expense object with new data
                    existingExpense.setTitle(title);
                    existingExpense.setAmount(amount);
                    existingExpense.setDate(date);
                    existingExpense.setPayer(payer);
                    existingExpense.setPaidFor(paidForUsers);

                    // Update expense in team
                    team.updateExpense(existingExpense, paidForUsersID);

                    // Display success message
                    JOptionPane.showMessageDialog(null, "Expense updated successfully.");

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
        setTitle("Edit Expense");
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

    public static void main(String[] args) {
        // Example usage
        Team team = new Team("TeamID", "Team Title", "Category", 1);
        User user = new User("username", "password", 1);
        List<User> users = new ArrayList<>();
        users.add(new User("User1", "password", 1));
        users.add(new User("User2", "password", 2));
        users.add(new User("User3", "password", 3));
        expense existingExpense = new expense(1, "Expense Title", 100.0, new Date(), users.get(0), users.subList(0, 2));

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Interface12EditExpense(team, user, existingExpense);
            }
        });
    }
}
