
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Interface3NewUser extends JFrame{

	private JPanel panel = new JPanel();
	private JLabel text1 = new JLabel("Δώσε όνομα χρήστη:");
	private JTextField username = new JTextField();
	private JLabel text2 = new JLabel("Δημιούργησε ένα κωδικό:");
	private JTextField password = new JTextField();
	private JButton save = new JButton("Save");
	private JButton BACK = new JButton("BACK");
	
	public Interface3NewUser(){
		
		panel.add(text1);
		username.setPreferredSize(new Dimension(200, 30));
		panel.add(username);
		panel.add(text2);
		password.setPreferredSize(new Dimension(200, 30));
		panel.add(password);
		panel.add(save);
		panel.add(BACK);
		this.setContentPane(panel);
		
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int id = 0;
				User user = new User(username.getText(), password.getText(), id);
				if (!user.doesUserExist(user.getUsername())) {
                    user.InsertUserinDataBase(user);
                    JOptionPane.showMessageDialog(null, "Ο λογαριασμός δημιουργήθηκε με επιτυχία!");
                    new Interface2Login();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Έχετε ήδη λογαριασμό.");
                    new Interface2Login();
                    dispose();
                }
				
			}
			
		});
		
		BACK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Interface1();
				dispose();
			}
			
		});
		
		this.setVisible(true);
		this.setSize(300,300);
		this.setTitle("Create Profil");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
