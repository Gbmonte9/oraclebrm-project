package brm.service;

import brm.model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                    return null; 
                }
            }
        }
    }

    public List<Account> getAllAccounts(Connection conn) throws SQLException {
        List<Account> contas = new ArrayList<>();
        String sql = "SELECT * FROM accounts";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Account conta = new Account(
                    rs.getInt("account_id"),
                    rs.getString("account_name"),
                    rs.getDouble("balance")
                );
                contas.add(conta);
            }
        }

        return contas;
    }

    public boolean deleteAccountById(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM accounts WHERE account_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; 
        }
    }

    public boolean updateAccountById(Connection conn, int id, String newName, double newBalance) throws SQLException {
        String sql = "UPDATE accounts SET account_name = ?, balance = ? WHERE account_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setDouble(2, newBalance);
            pstmt.setInt(3, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

}