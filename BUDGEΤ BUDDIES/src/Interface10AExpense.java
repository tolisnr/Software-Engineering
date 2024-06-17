import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class Interface10AExpense extends JFrame {

        private JLabel title = new JLabel();
        private JLabel amount = new JLabel();
        private JLabel date = new JLabel();
        private JLabel payer = new JLabel();
        private JLabel paidForLabel = new JLabel("Paid for:");
        private JList<String> paidForList;
        private JPanel panel = new JPanel();
        private JButton BACK = new JButton("Back");
        private JButton EDIT = new JButton("Edit");
        private JButton DELETE = new JButton("Delete");
    
        public Interface10AExpense(Team team, User user, expense expense) {

            title.setText("Title: " + expense.getTitle());
            amount.setText("Amount: " + String.valueOf(expense.getAmount()));
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            date.setText("Date: " + dateFormat.format(expense.getDate()));
            payer.setText("Payer: " + expense.getPayer().getUsername());
    
            // Collect usernames of the paidFor users
            List<User> paidForUsers = expense.getPaidFor();
            String[] paidForUsernames = new String[paidForUsers.size()];
            for (int i = 0; i < paidForUsers.size(); i++) {
                paidForUsernames[i] = paidForUsers.get(i).getUsername();
            }
    
            // Create JList and JScrollPane
            paidForList = new JList<>(paidForUsernames);
            paidForList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Adjust selection mode as needed
            JScrollPane scrollPane = new JScrollPane(paidForList);
    
            panel.add(title);
            panel.add(amount);
            panel.add(date);
            panel.add(payer);
            panel.add(paidForLabel);
            panel.add(scrollPane);
            panel.add(BACK);
            panel.add(EDIT);
            panel.add(DELETE);
            this.setContentPane(panel);
    
            BACK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new Interface8Expenses(team, user);
                    dispose();
                }
            });
    
            EDIT.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new Interface12EditExpense(team, user, expense);
                    dispose();
                }
            });
    
            DELETE.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean deleted = expense.deleteExpense(expense.getExpenseID());
                    if (deleted) {
                        JOptionPane.showMessageDialog(null, "Expense deleted successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to delete expense");
                    }
                    new Interface8Expenses(team, user);  // Refresh expense list or navigate back
                    dispose();
                }
        
            });
    
            this.setVisible(true);
            this.setSize(300, 400);
            this.setTitle("Expense Details");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }

