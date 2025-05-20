package brm.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;

import brm.service.TransactionService;

public class Transaction {
    private int transactionId;
    private int accountId;
    private String transactionType;
    private double amount;
    private Timestamp transactionDate;

    public Transaction(int transactionId, int accountId, String transactionType, double amount, Timestamp transactionDate) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public Transaction(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    public boolean createTransaction(Connection conn) {
        if (!isValid()) {
            System.out.println("Dados inválidos para a transação!");
            return false;
        }

        TransactionService service = new TransactionService();
        try {
            service.insertTransaction(conn, this.transactionId, this.accountId, this.transactionType, this.amount);
            return true;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1) {
                System.out.println("Transação já existe com esse ID!");
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }

    public boolean updateTransaction(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Digite o novo tipo da transação: ");
            String newType = scanner.nextLine();

            System.out.print("Digite o novo valor da transação: ");
            double newAmount = scanner.nextDouble();
            scanner.nextLine(); 

            TransactionService transactionService = new TransactionService();
            boolean updated = transactionService.updateTransactionById(conn, this.transactionId, newType, newAmount);

            return updated;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTransaction(Connection conn) {
        TransactionService service = new TransactionService();
        try {
            boolean deleted = service.deleteTransactionById(conn, this.transactionId);
            return deleted;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isValid() {
        if (transactionId <= 0) return false;
        if (accountId <= 0) return false;
        if (transactionType == null || transactionType.trim().isEmpty()) return false;
        if (amount <= 0) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Transaction ID: " + transactionId + ", Account ID: " + accountId +
               ", Type: " + transactionType + ", Amount: " + amount +
               ", Date: " + transactionDate;
    }
}