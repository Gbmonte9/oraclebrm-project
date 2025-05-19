package brm.service;

import brm.model.Account;

import java.sql.*;

public class AccountService {

    public void insertAccount(Connection conn, int id, String name, double balance) throws SQLException {
        String sql = "INSERT INTO accounts (account_id, account_name, balance) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setDouble(3, balance);
            pstmt.executeUpdate();
        }
    }

    public Account getAccountById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Account(
                        rs.getInt("account_id"),
                        rs.getString("account_name"),
                        rs.getDouble("balance")
                    );
                } else {
                    return null; // conta não encontrada
                }
            }
        }
    }
}