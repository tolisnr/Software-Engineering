
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Interface5JoinTeam extends JFrame{

	private JPanel panel = new JPanel();
	private JLabel text = new JLabel("Δώσε τον κωδικό της ομάδας: ");
	private JTextField teamIDField = new JTextField();
	private JButton OK = new JButton("OK");
	private JButton BACK = new JButton("BACK");
	
	public Interface5JoinTeam(User user) {
		
		panel.add(text);
		teamIDField.setPreferredSize(new Dimension(200, 30));
		panel.add(teamIDField);
		panel.add(OK);
		panel.add(BACK);
		this.setContentPane(panel);
		
		OK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(TeamInDataBase(teamIDField.getText()) != null) {
					user.JoinTeam(TeamInDataBase(teamIDField.getText()));
					TeamInDataBase(teamIDField.getText()).addUser(user);
					new Interface4(user);
					dispose();
				}
					
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
        this.setTitle("Join Team");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public Team TeamInDataBase(String teamID) {
        try (Connection conn = XAMPPConnection.getConnection()) {
            String query = "SELECT * FROM team WHERE teamID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, teamID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String category = resultSet.getString("category");
                int admin = resultSet.getInt("admin");

                Team team = new Team(teamID, title, category, admin);
                System.out.println("Team with ID " + teamID + " exists in the database.");
                return team;
            } else {
                System.out.println("No team with ID " + teamID + " found in the database.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error while searching for team: " + e.getMessage());
            return null;
        }
    }}
