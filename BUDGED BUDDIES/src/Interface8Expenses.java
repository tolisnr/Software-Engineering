import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class Interface8Expenses extends JFrame {

    private JPanel panel = new JPanel();
    private JButton BACK = new JButton("BACK");
    private JButton addExpesnse = new JButton("ADD EXPENSE");
    private JList<String> expensesList;

    public Interface8Expenses(Team team, User user) {

        panel.add(BACK);
        panel.add(addExpesnse);

        ArrayList<expense> expensesTeam = team.getExpenses();
        ArrayList<String> expensesTitles = new ArrayList<>();
        for (expense ex : expensesTeam) {
            expensesTitles.add(ex.getTitle());
        }
        expensesList = new JList<>(expensesTitles.toArray(new String[0]));

        // Add a ListSelectionListener to handle double-click event
        expensesList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    new Interface10AExpense().setVisible(true);
        			dispose();
                }
            }
        });

        panel.add(new JScrollPane(expensesList));
        this.setContentPane(panel);

        addExpesnse.addActionListener(new ActionListener() {
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

        this.setVisible(true);
        this.setSize(300, 300);
        this.setTitle("Expenses");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
