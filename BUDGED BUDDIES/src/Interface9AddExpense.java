import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
public class Interface9AddExpense extends JFrame {

    private JTextField titleField;
    private JTextField amountField;
    private JSpinner dateSpinner;
    private JList<String> payerList;
    private List<JCheckBox> paidForCheckboxes;
    private JButton BACK = new JButton("BACK");

    public Interface9AddExpense(Team team, User user) {

        setTitle("Add Expense");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        List<User> teamUsers = team.getUsers();
        List<String> userNames = new ArrayList<>();
        for (User u : teamUsers) {
            userNames.add(u.getUsername());
        }

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

        titleField = new JTextField();
        amountField = new JTextField();
        dateSpinner = new JSpinner(new SpinnerDateModel());

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

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(BACK, BorderLayout.WEST);

        add(topPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);
        setVisible(true);
    }
}
