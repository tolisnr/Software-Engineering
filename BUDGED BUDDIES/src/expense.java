
import java.util.Date;

public class expense {

	private String expenseID;
	private String title;
	private double amount;
	private Date date;
	private User payer;
	
	public String getExpenseID() {
        return expenseID;
    }

    public String getTitle() {
        return title;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public User getPayer() {
        return payer;
    }
}
