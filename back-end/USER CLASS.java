import java.util.ArrayList;
import java.util.Scanner;

public class User {
	public String name, password;
	public ArrayList<Group> Groups = new ArrayList<Group>();
	
	
	public User(String name, String  password) {
		this.name = name;
		this.password = password;
	}
	
	public void JoinGroup() {
		Scanner keyboard = new Scanner(System.in);
		String code = keyboard.next();
		for (Group group : Groups) {
			if(code == group.getCode()) {
				group.addUser(this);
			}
		}
	}
	
	public void CreateGroup() {
		Scanner keyboard = new Scanner(System.in);
		
		String title = keyboard.next();
		String password = keyboard.next();
		String category = keyboard.next();
		String economicActivity = keyboard.next();
		
		Group group = new Group(password);
		group.setTitle(title);
		group.setPassword(password);
		group.setCategory(category);
		group.setEconomicActivity(economicActivity);
	
	}
}
