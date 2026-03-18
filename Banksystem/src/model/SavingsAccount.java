package model;

public class SavingsAccount extends Account {
    public SavingsAccount(int accountNo, String name, int pin, double balance) {
        super(accountNo, name, pin, balance);
    }

    @Override
    public String getAccountType() {
        return "SAVINGS";
    }
}
