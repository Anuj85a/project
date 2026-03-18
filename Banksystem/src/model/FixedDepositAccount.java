package model;

public class FixedDepositAccount extends Account {
    public FixedDepositAccount(int accountNo, String name, int pin, double balance) {
        super(accountNo, name, pin, balance);
    }

    @Override
    public String getAccountType() {
        return "FIXED DEPOSIT";
    }
}
