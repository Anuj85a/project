package repository;

import until.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepositoryImpl {

    public void addTransaction(int accountNo, String type, double amount) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO transactions (account_no, type, amount) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, accountNo);
            ps.setString(2, type);
            ps.setDouble(3, amount);

            ps.executeUpdate();
            conn.close();

        } catch (Exception e) {
            System.out.println("❌ Error adding transaction");
        }
    }

    public List<String> getTransactions(int accountNo) {
        List<String> list = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM transactions WHERE account_no=? ORDER BY id DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountNo);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String type = rs.getString("type");
                double amount = rs.getDouble("amount");
                String time = rs.getString("date_time");

                list.add(type + " | " + amount + " | " + time);
            }

            conn.close();

        } catch (Exception e) {
            System.out.println("❌ Error fetching transactions");
        }

        return list;
    }
}
