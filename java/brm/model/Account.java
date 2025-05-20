package brm.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

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
    public Account() {
    
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

    public boolean updateAccount(Connection conn) {
        AccountService accountService = new AccountService();

        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Novo nome da conta: ");
            String novoNome = scanner.nextLine();

            System.out.print("Novo saldo: ");
            double novoSaldo = scanner.nextDouble();
            scanner.nextLine(); 

            boolean updated = accountService.updateAccountById(conn, this.accountId, novoNome, novoSaldo);

            if (updated) {
                System.out.println("Conta atualizada com sucesso.");
            } else {
                System.out.println("Falha ao atualizar a conta.");
            }

            return updated;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAccount(Connection conn) {
        AccountService accountService = new AccountService();
        TransactionService transactionService = new TransactionService();

        try {
            Account conta = accountService.getAccountById(conn, this.accountId);
            if (conta == null) {
                System.out.println("Conta não encontrada.");
                return false;
            }

            transactionService.deleteAllTransactionsByAccountId(conn, this.accountId);

            boolean deleted = accountService.deleteAccountById(conn, this.accountId);
            if (deleted) {
                System.out.println("Conta e transações excluídas com sucesso.");
                return true;
            } else {
                System.out.println("Falha ao excluir a conta.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean allAccounts(Connection conn) {
        AccountService accountService = new AccountService();
        TransactionService transactionService = new TransactionService();

        try {
            List<Account> contas = accountService.getAllAccounts(conn);

            if (contas.isEmpty()) {
                System.out.println("Nenhuma conta cadastrada.");
                return false;
            }

            for (Account conta : contas) {
                System.out.println("----- Detalhes da Conta -----");
                System.out.println("ID: " + conta.getAccountId());
                System.out.println("Nome: " + conta.getAccountName());
                System.out.println("Saldo: " + conta.getBalance());

                List<Transaction> transacoes = transactionService.getTransactionsByAccountId(conn, conta.getAccountId());

                if (transacoes.isEmpty()) {
                    System.out.println("Nenhuma transação encontrada.");
                } else {
                    for (Transaction t : transacoes) {
                        System.out.println("ID Transação: " + t.getTransactionId());
                        System.out.println("Tipo: " + t.getTransactionType());
                        System.out.println("Valor: " + t.getAmount());
                        System.out.println("Data: " + t.getTransactionDate());
                        System.out.println("--------------------------");
                    }
                }
                System.out.println("=============================");
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