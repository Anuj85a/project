package model;

import java.util.ArrayList;

public abstract class Account {

    private int accountNo;
    private String name;
    private int pin;
    protected double balance;

    protected ArrayList<String> passbook = new ArrayList<>();

    public Account(int accountNo, String name, int pin, double balance) {
        this.accountNo = accountNo;
        this.name = name;
        this.pin = pin;
        this.balance = balance;
        passbook.add("✅ Account Opened | Balance: " + balance);
    }

    public int getAccountNo() {
        return accountNo;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public boolean authenticate(int inputPin) {
        return this.pin == inputPin;
    }

    public void deposit(double amount) {
        balance += amount;
        passbook.add("✅ Deposit: " + amount + " | Balance: " + balance);
    }

    public boolean withdraw(double amount) {
        if (amount > balance) return false;
        balance -= amount;
        passbook.add("✅ Withdraw: " + amount + " | Balance: " + balance);
        return true;
    }

    public ArrayList<String> getPassbook() {
        return passbook;
    }

    public abstract String getAccountType();
}
