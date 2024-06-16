public class Owes {
    private String teamID;
    private int win;
    private int lose;
    private double amount;
    
    public Owes(String teamID, int win, int lose) {
        this.teamID = teamID;
        this.win = win;
        this.lose = lose;
        this.amount = 0;
    }

    public double getAmount() {
        return amount;
    }
}
