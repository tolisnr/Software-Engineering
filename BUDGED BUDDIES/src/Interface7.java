import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interface7 extends JFrame {

    private JPanel panel = new JPanel();
    private JButton Balances = new JButton("SHOW BALANCES");
    private JButton Expenses = new JButton("SHOW EXPENSES");
    private JButton BACK = new JButton("BACK");

    public Interface7(Team team, User user) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Expenses);
        panel.add(Balances);
        panel.add(BACK);
        this.setContentPane(panel);

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

        this.setVisible(true);
        this.setSize(300, 300);
        this.setTitle(team.getTitle());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
