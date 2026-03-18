package model;

public class CurrentAccount extends Account {
    public CurrentAccount(int accountNo, String name, int pin, double balance) {
        super(accountNo, name, pin, balance);
    }

    @Override
    public String getAccountType() {
        return "CURRENT";
    }
}
