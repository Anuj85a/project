package Bank;

import exception.InvalidAccountTypeException;
import exception.InvalidCredentialsException;
import model.Account;
import service.BankService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BankService bankService = new BankService();

        while (true) {
            System.out.println("\n===== BANK APPLICATION =====");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    try {
                        System.out.print("Enter Account No: ");
                        int accNo = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();

                        System.out.print("Set 4-digit PIN: ");
                        int pin = sc.nextInt();

                        System.out.print("Enter Initial Balance: ");
                        double bal = sc.nextDouble();

                        System.out.println("Select Account Type: 1.Savings 2.Current 3.FixedDeposit");
                        int type = sc.nextInt();

                        bankService.createAccount(accNo, name, pin, bal, type);
                        System.out.println("✅ Account Created Successfully!");

                    } catch (InvalidAccountTypeException e) {
                        System.out.println("❌ " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("❌ Invalid Input!");
                        sc.nextLine();
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Enter Account No: ");
                        int accNo = sc.nextInt();

                        System.out.print("Enter PIN: ");
                        int pin = sc.nextInt();

                        Account user = bankService.login(accNo, pin);
                        System.out.println("✅ Login Success! Welcome " + user.getName());

                        // User Menu
                        while (true) {
                            System.out.println("\n===== USER MENU =====");
                            System.out.println("1. Check Balance");
                            System.out.println("2. Deposit");
                            System.out.println("3. Withdraw");
                            System.out.println("4. Print Passbook");
                            System.out.println("5. Logout");
                            System.out.print("Enter choice: ");

                            int userChoice = sc.nextInt();

                            if (userChoice == 1) {
                                System.out.println("✅ Balance: " + bankService.checkBalance(user));

                            } else if (userChoice == 2) {
                                System.out.print("Enter Amount: ");
                                double amt = sc.nextDouble();
                                bankService.deposit(user, amt);

                            } else if (userChoice == 3) {
                                System.out.print("Enter Amount: ");
                                double amt = sc.nextDouble();
                                bankService.withdraw(user, amt);

                            } else if (userChoice == 4) {
                                bankService.printPassbook(user);

                            } else if (userChoice == 5) {
                                System.out.println("✅ Logout Done!");
                                break;

                            } else {
                                System.out.println("❌ Invalid choice!");
                            }
                        }

                    } catch (InvalidCredentialsException e) {
                        System.out.println("❌ " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("❌ Invalid Input!");
                        sc.nextLine();
                    }
                    break;

                case 3:
                    System.out.println("✅ Thank You! Exiting...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }
}
