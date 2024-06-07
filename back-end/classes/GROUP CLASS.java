import java.util.ArrayList;

public class Group {
	public String code;
	public ArrayList<User> Users = new ArrayList<>();
	public String title, password, category, economicActivity;
	
	public  Group(String code) {
		this.code=code;
	}

	public String getCode() {
		return code;
	}
	
	public void addUser(User u) {
		Users.add(u);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getEconomicActivity() {
		return economicActivity;
	}

	public void setEconomicActivity(String economicActivity) {
		this.economicActivity = economicActivity;
	}
	
}
