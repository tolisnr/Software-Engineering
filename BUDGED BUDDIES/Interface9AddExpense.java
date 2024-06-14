
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;


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
	private JList<String> payer = new JList<>();
	private JButton Save = new JButton("SAVE");
	private JList<String> posPayer = new JList<>();
	//Tha mpei to payer??


	public Interface9AddExpense(Team team, User user) {
		
		ArrayList<User> users = team.getUsers();
		ArrayList<String> Payer = new ArrayList<>();
		for (User u : users) {
			Payer.add(u.getUsername());
		}
		posPayer = new JList<>(Payer.toArray(new String[0]));

		panel.add(BACK);
		panel.add(titleText);
		titleField.setPreferredSize(new Dimension(200, 30));
		panel.add(titleField);
		panel.add(amountText);
		amountField.setPreferredSize(new Dimension(200, 30));
		panel.add(amountField);
		panel.add(dateText);
		dateField.setPreferredSize(new Dimension(200, 30));
		panel.add(dateField);
		//tha mpei to payer??
		panel.add(payerText);
		panel.add(new JScrollPane(posPayer));
		//payer
		panel.add(Save);
        this.setContentPane(panel);
        
		String title = titleField.getText();
		double amount = Double.parseDouble(amountField.getText());
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateField.getText());
		//User payer = (User)posPayer.getSelectedValue();

		//Edw emfanizei error logw tou payer, poy einai stirng anti gia User type
		expense aExpense = new expense(title, amount, date, payer);
        
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
				team.addExpense(aExpense);
			}
		});
        
        this.setVisible(true);
        this.setSize(400, 400);
        this.setTitle("Add Expense");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
