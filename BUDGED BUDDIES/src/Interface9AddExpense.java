import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

public class Interface9AddExpense extends JFrame {

    private JPanel panel = new JPanel();
    private JButton BACK = new JButton("BACK");
    private JLabel titleText = new JLabel("Δώσε το τίτλο του Expense: ");
    private JTextField titleField = new JTextField();
    private JLabel amountText = new JLabel("Δώσε το ποσό που πλήρωσες: ");
    private JTextField amountField = new JTextField();
    private JLabel dateText = new JLabel("Επέλεξε την ημερομηνία: ");
    private JSpinner dateSpinner;
    private JLabel payerText = new JLabel("Επέλεξε ποιος πλήρωσε: ");
    private JList<String> posPayer;
    private JButton Save = new JButton("SAVE");
    private ArrayList<User> users;

    public Interface9AddExpense(Team team, User user) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        users = team.getUsers();
        ArrayList<String> Payer = new ArrayList<>();
        for (User u : users) {
            Payer.add(u.getUsername());
        }
        posPayer = new JList<>(Payer.toArray(new String[0]));

        // Initialize the date spinner with a SpinnerDateModel
        SpinnerDateModel spinnerModel = new SpinnerDateModel();
        dateSpinner = new JSpinner(spinnerModel);
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy"));

        dateSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                //Date selectedDate = (Date) spinnerModel.getValue();
            }
        });

        panel.add(BACK);
        panel.add(titleText);
        titleField.setPreferredSize(new Dimension(200, 30));
        panel.add(titleField);
        panel.add(amountText);
        amountField.setPreferredSize(new Dimension(200, 30));
        panel.add(amountField);
        panel.add(dateText);
        panel.add(dateSpinner);
        panel.add(payerText);
        panel.add(new JScrollPane(posPayer));
        panel.add(Save);

        this.setContentPane(panel);

        BACK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Interface8Expenses(team, user);
                dispose();
            }
        });

        Save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String title = titleField.getText();
                    double amount = Double.parseDouble(amountField.getText());
                    Date date = (Date) spinnerModel.getValue();
                    String payerUsername = posPayer.getSelectedValue();

                    // Find the selected payer User object
                    User payer = null;
                    for (User u : users) {
                        if (u.getUsername().equals(payerUsername)) {
                            payer = u;
                            break;
                        }
                    }

                    // Confirm selection with user
                    if (payer != null) {
                        int confirm = JOptionPane.showConfirmDialog(panel,
                                "Do you want " + payer.getUsername() + " to be the payer?", "Confirm Payer Selection",
                                JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
							int id = 0;
							expense aExpense = new expense(title, amount, date, payer, id);
                            team.addExpense(aExpense);
                            System.out.println("Expense added.");
                            new Interface8Expenses(team, user);
                            dispose();
                        } else {
                            System.out.println("Payer selection canceled.");
                        }
                    } else {
                        System.out.println("Payer not found.");
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.setVisible(true);
        this.setSize(400, 400);
        this.setTitle("Add Expense");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
