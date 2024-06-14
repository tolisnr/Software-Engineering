
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Interface10AExpense extends JFrame{

	private JPanel panel = new JPanel();
	private JButton BACK = new JButton("BACK");
	private JLabel title = new JLabel();
	private JLabel amount = new JLabel();
	private JLabel date = new JLabel();
	private JLabel payer = new JLabel();
	private JButton EDIT = new JButton("EDIT");
	private JButton DELETE = new JButton("DELETE"); 
	
	public Interface10AExpense(Team team, User user, expense expense) { 

		title.setText(expense.getTitle());
		amount.setText(String.valueOf(expense.getAmount()));
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		date.setText(dateFormat.format(expense.getDate()));
		payer.setText(expense.getPayer().getUsername());
		
		panel.add(BACK);
		panel.add(new JLabel("Title:"));
		panel.add(title);
		panel.add(new JLabel("Amount:"));
		panel.add(amount);
		panel.add(new JLabel("Date:"));
		panel.add(date);
		panel.add(new JLabel("Payer:"));
		panel.add(payer);
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
				new Interface8Expenses(team, user);
				//thelei dispose??
			}
		});

		DELETE.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e){
				
			}
		});
        
        this.setVisible(true);
        this.setSize(300, 300);
        this.setTitle("");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
