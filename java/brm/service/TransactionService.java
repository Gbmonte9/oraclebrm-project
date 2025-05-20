package brm.service;

import brm.model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    public void insertTransaction(Connection conn, int transactionId, int accountId, String type, double amount) throws SQLException {
        String sql = "INSERT INTO transactions (transaction_id, account_id, transaction_type, amount, transaction_date) " +
                     "VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transactionId);
            pstmt.setInt(2, accountId);
            pstmt.setString(3, type);
            pstmt.setDouble(4, amount);
            pstmt.executeUpdate();
        }
    }

    public List<Transaction> getTransactionsByAccountId(Connection conn, int accountId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Transaction t = new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getInt("account_id"),
                        rs.getString("transaction_type"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("transaction_date")
                    );
                    transactions.add(t);
                }
            }
        }
        return transactions;
    }

    public boolean deleteAllTransactionsByAccountId(Connection conn, int accountId) throws SQLException {
        String sql = "DELETE FROM transactions WHERE account_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; 
        }
    }

    public boolean deleteTransactionById(Connection conn, int transactionId) throws SQLException {
        String sql = "DELETE FROM transactions WHERE transaction_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transactionId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; 
        }
    }

    public boolean updateTransactionById(Connection conn, int transactionId, String newType, double newAmount) throws SQLException {
        String sql = "UPDATE transactions SET transaction_type = ?, amount = ? WHERE transaction_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newType);
            pstmt.setDouble(2, newAmount);
            pstmt.setInt(3, transactionId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; 
        }
    }


}