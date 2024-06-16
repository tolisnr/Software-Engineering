import java.util.Map;
import java.util.HashMap;

public class balance {

    private String balanceID;
    private Team team;
    private Map<User, Double> amountOwed;

    public balance(String balanceID, Team team) {
        this.balanceID = balanceID;
        this.team = team;
        this.amountOwed = new HashMap<>();
    }

    public String getBalanceID() {
        return balanceID;
    }

    public void setBalanceID(String balanceID) {
        this.balanceID = balanceID;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Map<User, Double> getAmountOwed() {
        return amountOwed;
    }

    public void addAmountOwed(User user, double amount) {
        this.amountOwed.merge(user, amount, Double::sum);
    }

    public void setAmountOwed(Map<User, Double> amountOwed) {
        this.amountOwed = amountOwed;
    }
}
