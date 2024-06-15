import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Interface12EditExpense extends JFrame {

    private JTextField titleField;
    private JTextField amountField;
    private JSpinner dateSpinner;
    private JList<String> payerList;
    private List<JCheckBox> paidForCheckboxes;
    private JButton BACK = new JButton("BACK");

    public Interface12EditExpense(Team team, User user, expense existingExpense) {

        setTitle("Edit Expense");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        List<User> teamUsers = team.getUsers();
        List<String> userNames = new ArrayList<>();
        for (User u : teamUsers) {
            userNames.add(u.getUsername());
        }

        // Initialize UI components
        titleField = new JTextField(existingExpense.getTitle());
        amountField = new JTextField(String.valueOf(existingExpense.getAmount()));
        dateSpinner = new JSpinner(new SpinnerDateModel(existingExpense.getDate(), null, null, Calendar.DAY_OF_MONTH));
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(dateEditor);

        payerList = new JList<>(userNames.toArray(new String[0]));
        payerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel paidForPanel = new JPanel();
        paidForPanel.setLayout(new BoxLayout(paidForPanel, BoxLayout.Y_AXIS));
        paidForCheckboxes = new ArrayList<>();
        for (String username : userNames) {
            JCheckBox checkBox = new JCheckBox(username);
            paidForCheckboxes.add(checkBox);
            paidForPanel.add(checkBox);
        }

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

        // Add components to formPanel after data initialization
        JPanel formPanel = new JPanel(new GridLayout(6, 2));
        formPanel.add(new JLabel("Title:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Amount:"));
        formPanel.add(amountField);
        formPanel.add(new JLabel("Date:"));
        formPanel.add(dateSpinner);
        formPanel.add(new JLabel("Payer:"));
        formPanel.add(new JScrollPane(payerList));
        formPanel.add(new JLabel("Paid For:"));
        formPanel.add(new JScrollPane(paidForPanel));

        JButton saveButton = new JButton("Save");
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

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(BACK, BorderLayout.WEST);

        add(topPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);
        setVisible(true);
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
