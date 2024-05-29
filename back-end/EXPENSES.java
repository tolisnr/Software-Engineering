import java.util.ArrayList;

public class Expenses {
	public ArrayList<User> Users = new ArrayList<>();
	public double amount; 
	public  String title, date;
	
	//o costructor einai to method add Expsenses 
	public Expenses(User u,double amount, String title, String date) {
		this.amount = amount; 
		this.Users.add(u);
		this.title = title;
		this.date = date;
	}
		
}
