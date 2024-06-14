
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Interface6CreateTeam extends JFrame{

	private JPanel panel = new JPanel();
	private JLabel titleText = new JLabel("Δώσε το τίτλο της ομάδας: ");
	private JTextField titleField = new JTextField();
	private JLabel categoryText = new JLabel("Δώσε την κατηγορα: ");
	private JTextField categoryField = new JTextField();
	private JButton Save = new JButton("SAVE");
	private JButton BACK = new JButton("BACK");
	
	public Interface6CreateTeam(User user) {
		
		panel.add(titleText);
		titleField.setPreferredSize(new Dimension(200, 30));
		panel.add(titleField);
		panel.add(categoryText);
		categoryField.setPreferredSize(new Dimension(200, 30));
		panel.add(categoryField);
		panel.add(Save);
		panel.add(BACK);
		this.setContentPane(panel);
		
		Save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				user.CreateTeam(titleField.getText(), categoryField.getText());
				new Interface4(user);
				dispose();
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
		this.setSize(500,500);
		this.setTitle("Create Profil");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
