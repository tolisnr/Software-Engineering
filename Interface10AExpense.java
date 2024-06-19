import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class Interface10AExpense extends JFrame {

        private JLabel title = new JLabel();
        private JLabel amount = new JLabel();
        private JLabel date = new JLabel();
        private JLabel payer = new JLabel();
        private JLabel paidForLabel = new JLabel("Paid for:");
        private JList<String> paidForList;
        private JButton BACK = new JButton("Back");
        private JButton EDIT = new JButton("Edit");
        private JButton DELETE = new JButton("Delete");
    
        public Interface10AExpense(Team team, User user, expense expense) {

            // Create background panel
            BackgroundImageExample backgroundPanel = new BackgroundImageExample("background.jpg");

            // Create application icon
            ImageIcon icon = new ImageIcon("icon.jpeg");

            // Resize the image
            Image resizedImage = icon.getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);

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


            // Buttons Appearance
            customizeButton(BACK);
            customizeButton(EDIT);
            customizeButton(DELETE);

            // Create a layered pane to layer the background and the panels
            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setPreferredSize(new Dimension(800, 600));

            // Add the background panel to the layered pane
            backgroundPanel.setBounds(0, 0, 800, 600);
            layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

            // Add the BACK button to the top left corner of the layered pane
            BACK.setBounds(10, 10, 80, 30);
            layeredPane.add(BACK, JLayeredPane.PALETTE_LAYER);

            // Create a panel to hold the expense details
            JPanel expensePanel = new JPanel();
            expensePanel.setOpaque(false); // Make panel transparent
            expensePanel.setLayout(new GridLayout(6, 1)); // 6 rows, 1 column
            expensePanel.add(title);
            expensePanel.add(amount);
            expensePanel.add(date);
            expensePanel.add(payer);
            expensePanel.add(paidForLabel);
            expensePanel.add(scrollPane);


            // Add the expense panel to the layered pane
            expensePanel.setBounds(300, 100, 200, 200);
            layeredPane.add(expensePanel, JLayeredPane.PALETTE_LAYER);

            // Create a panel to add the buttons
            JPanel buttonPanel = new JPanel();
            buttonPanel.setOpaque(false); // Make button panel transparent
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center buttons
            buttonPanel.add(EDIT);
            buttonPanel.add(DELETE);


            // Add the button panel to the layered pane
            buttonPanel.setBounds(300, 500, 200, 50);
            layeredPane.add(buttonPanel, JLayeredPane.PALETTE_LAYER);
            
    
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

