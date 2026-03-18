package repository;

import model.*;
import until.DBConnection;

import java.sql.*;

public class AccountRepositoryImpl implements AccountRepository {

    @Override
    public void save(Account account) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO account VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, account.getAccountNo());
            ps.setString(2, account.getName());
            ps.setInt(3, getPin(account));
            ps.setDouble(4, account.getBalance());
            ps.setString(5, account.getAccountType());

            ps.executeUpdate();
            conn.close();

        } catch (Exception e) {
            System.out.println("❌ Error saving account: " + e.getMessage());
        }
    }

    @Override
    public Account findByAccountNo(int accountNo) {
        Account acc = null;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM account WHERE account_no=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountNo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                int pin = rs.getInt("pin");
                double balance = rs.getDouble("balance");
                String type = rs.getString("type");

                if (type.equals("SAVINGS"))
                    acc = new SavingsAccount(accountNo, name, pin, balance);
                else if (type.equals("CURRENT"))
                    acc = new CurrentAccount(accountNo, name, pin, balance);
                else
                    acc = new FixedDepositAccount(accountNo, name, pin, balance);
            }

            conn.close();

        } catch (Exception e) {
            System.out.println("❌ Error finding account: " + e.getMessage());
        }

        return acc;
    }

    // helper to access private pin
    private int getPin(Account acc) {
        try {
            java.lang.reflect.Field field = Account.class.getDeclaredField("pin");
            field.setAccessible(true);
            return (int) field.get(acc);
        } catch (Exception e) {
            return 0;
        }
    }

    // update balance
    public void updateBalance(Account acc) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "UPDATE account SET balance=? WHERE account_no=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setDouble(1, acc.getBalance());
            ps.setInt(2, acc.getAccountNo());

            ps.executeUpdate();
            conn.close();

        } catch (Exception e) {
            System.out.println("❌ Error updating balance");
        }
    }
}
