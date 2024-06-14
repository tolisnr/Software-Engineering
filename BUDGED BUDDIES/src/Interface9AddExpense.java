
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Interface9AddExpense extends JFrame{

	private JPanel panel = new JPanel();
	private JButton BACK = new JButton("BACK");
	private JLabel titleText = new JLabel("Δώσε το τίτλο του Expense: ");
	private JTextField titleField = new JTextField();
	private JLabel amountText = new JLabel("Δώσε το ποσό που πλήρωσες: ");
	private JTextField amountField = new JTextField();
	private JLabel dateText = new JLabel("Δώσε την ημερομηνία: ");
	private JTextField dateField = new JTextField();
	private JLabel payerText = new JLabel("Επέλεξε ποιος πλήρωσε: ");
	private JButton Save = new JButton("SAVE");


	public Interface9AddExpense(Team team, User user) {
		
		panel.add(BACK);
		panel.add(titleText);
		panel.add(titleField);
        this.setContentPane(panel);
        
        
        BACK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Interface8Expenses(team, user);
				dispose();
			}
			
		});
        
        this.setVisible(true);
        this.setSize(300, 300);
        this.setTitle("Add Expense");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
