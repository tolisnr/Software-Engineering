
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Interface10AExpense extends JFrame{

	private JPanel panel = new JPanel();
	private JButton BACK = new JButton("BACK");
	
	public Interface10AExpense() {
		
		panel.add(BACK);
        this.setContentPane(panel);
        
        
        BACK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Interface1();
				dispose();
			}
			
		});
        
        this.setVisible(true);
        this.setSize(300, 300);
        this.setTitle("");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
