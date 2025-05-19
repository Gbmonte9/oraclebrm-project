package brm.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {

    public static void initializeDatabase(Connection conn) throws SQLException {
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

        stmt.close();
    }
}