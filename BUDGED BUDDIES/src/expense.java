import java.util.Date;

public class expense {

	private int expenseID;
	private String title;
	private double amount;
	private Date date;
	private User payer;

	public expense(String title, double amount, Date date, User payer, int expenseID) {
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.payer = payer;
        this.expenseID = expenseID;
    }

    public void setExpenseID(int expenseID) {
        this.expenseID = expenseID;
    }

    public int getExpenseID() {
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
