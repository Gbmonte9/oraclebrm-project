package brm.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import brm.service.AccountService;
import brm.service.TransactionService;

public class Account {
    private int accountId;
    private String accountName;
    private double balance;

    public Account(int accountId, String accountName, double balance) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.balance = balance;
    }

    public Account(int accountId) {
        this.accountId = accountId;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public double getBalance() {
        return balance;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean createAccount(Connection conn) {
        if (!isValid()) {
            System.out.println("Dados inválidos para a conta!");
            return false;
        }

        AccountService service = new AccountService();
        try {
            service.insertAccount(conn, this.accountId, this.accountName, this.balance);
            return true;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1) {
                System.out.println("Conta já existe com esse ID!");
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }

    public boolean isValid() {
        if (accountId <= 0) return false;
        if (accountName == null || accountName.trim().isEmpty()) return false;
        if (balance < 0) return false;
        return true;
    }

    public boolean searchAccount(Connection conn) {
        AccountService accountService = new AccountService();
        TransactionService transactionService = new TransactionService();

        try {
            Account conta = accountService.getAccountById(conn, this.accountId);
            if (conta == null) {
                return false;
            }

            System.out.println("----- Detalhes da Conta -----");
            System.out.println("ID: " + conta.getAccountId());
            System.out.println("Nome: " + conta.getAccountName());
            System.out.println("Saldo: " + conta.getBalance());

            System.out.println("----- Transações -----");
            List<Transaction> transacoes = transactionService.getTransactionsByAccountId(conn, this.accountId);
            if (transacoes.isEmpty()) {
                System.out.println("Nenhuma transação encontrada.");
            } else {
                for (Transaction t : transacoes) {
                    System.out.println("ID: " + t.getTransactionId());
                    System.out.println("Tipo: " + t.getTransactionType());
                    System.out.println("Valor: " + t.getAmount());
                    System.out.println("Data: " + t.getTransactionDate());
                    System.out.println("--------------------------");
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public String toString() {
        return "Account ID: " + accountId + ", Name: " + accountName + ", Balance: " + balance;
    }
}