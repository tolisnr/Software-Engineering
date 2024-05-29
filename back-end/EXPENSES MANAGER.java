import java.util.ArrayList;
import java.util.Scanner;

public class ExpensesManager {
	private ArrayList<Expenses> expensesList = new ArrayList<>();

    public void addExpense(Expenses expense) {
        expensesList.add(expense);
    }

    public void deleteExpenses(Expenses exp) {
        expensesList.remove(exp);
    }
    
    public void editExpenses(Expenses exp) {
		if(exp.amount == 0){
			//Mporei na kanei add Expenses
			
				
	        }else{	
	    		//Mporei na epeksergastei ola ta expenses 
			Scanner keyboard = new Scanner(System.in);

		        exp.title  = keyboard.next();
			exp.date = keyboard.next();
			//Den mporw na epeksergastw ton xristi!!
			double temp = Double.parseDouble(keyboard.next());
			//if add expenses: 
			exp.amount += temp;
			//if subtract expenses:
			if(exp.amount - temp>= 0) exp.amount -= temp;
		}
	}
}
