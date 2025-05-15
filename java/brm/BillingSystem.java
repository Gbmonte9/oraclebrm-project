package brm;

import java.sql.*;

public class BillingSystem {
    public static void main(String[] args) {
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1", "system", "root");

            Statement stmt = conn.createStatement();

            try {
                stmt.executeUpdate(
                    "CREATE TABLE accounts (" +
                    "account_id NUMBER PRIMARY KEY, " +
                    "account_name VARCHAR2(100), " +
                    "balance NUMBER)"
                );
                System.out.println("Tabela 'accounts' criada.");
            } catch (SQLException e) {
                if (e.getErrorCode() == 955) { 
                    System.out.println("Tabela 'accounts' já existe.");
                } else {
                    throw e;
                }
            }

            try {
                stmt.executeUpdate(
                    "CREATE TABLE transactions (" +
                    "transaction_id NUMBER PRIMARY KEY, " +
                    "account_id NUMBER, " +
                    "transaction_type VARCHAR2(50), " +
                    "amount NUMBER, " +
                    "transaction_date TIMESTAMP, " +
                    "FOREIGN KEY (account_id) REFERENCES accounts(account_id))"
                );
                System.out.println("Tabela 'transactions' criada.");
            } catch (SQLException e) {
                if (e.getErrorCode() == 955) {
                    System.out.println("Tabela 'transactions' já existe.");
                } else {
                    throw e;
                }
            }

            try {
                stmt.executeUpdate("INSERT INTO accounts (account_id, account_name, balance) VALUES (1, 'Gabriel Monte', 1000.00)");
                stmt.executeUpdate("INSERT INTO transactions (transaction_id, account_id, transaction_type, amount, transaction_date) VALUES (1, 1, 'Pagamento', 150.00, CURRENT_TIMESTAMP)");
            } catch (SQLException e) {
                if (e.getErrorCode() == 1) { 
                    System.out.println("Dados já existem, pulando inserção.");
                } else {
                    throw e;
                }
            }

            ResultSet rs = stmt.executeQuery("SELECT account_name, balance FROM accounts WHERE account_id = 1");
            while (rs.next()) {
                System.out.println("Account Name: " + rs.getString("account_name"));
                System.out.println("Balance: " + rs.getDouble("balance"));
            }
            rs.close();

            rs = stmt.executeQuery("SELECT * FROM transactions WHERE account_id = 1");
            while (rs.next()) {
                System.out.println("Transaction Type: " + rs.getString("transaction_type"));
                System.out.println("Amount: " + rs.getDouble("amount"));
                System.out.println("Date: " + rs.getTimestamp("transaction_date"));
            }
            rs.close();

            int rowsUpdated = stmt.executeUpdate("UPDATE accounts SET balance = balance - 150 WHERE account_id = 1");
            System.out.println("Linhas atualizadas: " + rowsUpdated);

            rs = stmt.executeQuery("SELECT account_name, balance FROM accounts WHERE account_id = 1");
            while (rs.next()) {
                System.out.println("Account Name (atualizado): " + rs.getString("account_name"));
                System.out.println("Balance (atualizado): " + rs.getDouble("balance"));
            }
            rs.close();

            stmt.close();
            conn.close();

        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC não encontrado!");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}