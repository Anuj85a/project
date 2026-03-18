package service;

import exception.InsufficientBalanceException;
import exception.InvalidAccountTypeException;
import exception.InvalidCredentialsException;
import model.*;
import repository.AccountRepositoryImpl;
import repository.TransactionRepositoryImpl;

import java.util.List;

public class BankService {

    private AccountRepositoryImpl repo = new AccountRepositoryImpl();
    private TransactionRepositoryImpl txRepo = new TransactionRepositoryImpl();

    public void createAccount(int accNo, String name, int pin, double balance, int type)
            throws InvalidAccountTypeException {

        Account acc;

        if (type == 1) {
            acc = new SavingsAccount(accNo, name, pin, balance);
        } else if (type == 2) {
            acc = new CurrentAccount(accNo, name, pin, balance);
        } else if (type == 3) {
            acc = new FixedDepositAccount(accNo, name, pin, balance);
        } else {
            throw new InvalidAccountTypeException("Invalid Account Type Selected!");
        }

        repo.save(acc);
        txRepo.addTransaction(accNo, "ACCOUNT_CREATED", balance);
    }

    public Account login(int accNo, int pin) throws InvalidCredentialsException {
        Account acc = repo.findByAccountNo(accNo);

        if (acc == null || !acc.authenticate(pin)) {
            throw new InvalidCredentialsException("Invalid Account Number or PIN!");
        }
        return acc;
    }

    public double checkBalance(Account acc) {
        return acc.getBalance();
    }

    public void deposit(Account acc, double amount) {
        if (amount <= 0) {
            System.out.println("❌ Invalid Amount!");
            return;
        }
        acc.deposit(amount);
        repo.updateBalance(acc);
        txRepo.addTransaction(acc.getAccountNo(), "DEPOSIT", amount);
        System.out.println("✅ Deposit Successful!");
    }

    public void withdraw(Account acc, double amount) throws InsufficientBalanceException {
        if (amount <= 0) {
            System.out.println("❌ Invalid Amount!");
            return;
        }
        boolean ok = acc.withdraw(amount);
        if (!ok) throw new InsufficientBalanceException("Not enough balance!");

        repo.updateBalance(acc);
        txRepo.addTransaction(acc.getAccountNo(), "WITHDRAW", amount);
        System.out.println("✅ Withdraw Successful!");
    }

    public void printPassbook(Account acc) {

        System.out.println("\n========= PASSBOOK =========");
        System.out.println("Account No : " + acc.getAccountNo());
        System.out.println("Name       : " + acc.getName());
        System.out.println("Type       : " + acc.getAccountType());
        System.out.println("----------------------------");

        List<String> list = txRepo.getTransactions(acc.getAccountNo());

        for (String s : list) {
            System.out.println(s);
        }

        System.out.println("----------------------------\n");
    }
}
