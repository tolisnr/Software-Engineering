import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Interface4 extends JFrame{

	private JPanel panel = new JPanel();
	private JButton JoinTeam = new JButton("JOIN TEAM");
	private JButton CreateTeam = new JButton("CREATE TEAM");
	private JButton BACK = new JButton("BACK");
	private JList<String> teams = new JList<>();
	
	public Interface4(User user) {

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        ArrayList<Team> userTeams = user.getTeams();
        ArrayList<String> teamTitles = new ArrayList<>();
        for (Team team : userTeams) {
            teamTitles.add(team.getTitle());
        }
        teams = new JList<>(teamTitles.toArray(new String[0]));

		panel.add(new JScrollPane(teams));
		panel.add(CreateTeam);
		panel.add(JoinTeam);
		panel.add(BACK);
		this.setContentPane(panel);
		
		CreateTeam.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Interface6CreateTeam(user);
				dispose();
			}
			
		});
		
		JoinTeam.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Interface5JoinTeam(user);
				dispose();
			}
			
		});
		
		BACK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Interface1();
				dispose();
			}
			
		});
		
		teams.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = teams.locationToIndex(e.getPoint());
                    String selectedTitle = teamTitles.get(index);
                    Team selectedTeam = Team.getTeamByTitle(userTeams, selectedTitle);
                    if (selectedTeam != null) {
                        new Interface7(selectedTeam, user); // Navigate to Interface7 with selected team
                        dispose();
                    }
                }
            }
        });

		this.setVisible(true);
		this.setSize(300,300);
		this.setTitle("Budget Buddies");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
