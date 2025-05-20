package brm;

import brm.util.DatabaseConnection;
import brm.util.DatabaseSetup;
import brm.model.Account;
import brm.service.AccountService;
import brm.service.TransactionService;
import brm.model.Transaction;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class BillingSystem {

    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             Scanner scanner = new Scanner(System.in)) {

            DatabaseSetup.initializeDatabase(conn);

            AccountService accountService = new AccountService();
            TransactionService transactionService = new TransactionService();

            System.out.println("Bem-vindo ao Sistema de Cobrança!");

            boolean running = true;
            while (running) {
                System.out.println("\nEscolha uma opção:");
                System.out.println("1 - Criar Conta");
                System.out.println("2 - Fazer Transação");
                System.out.println("3 - Ver Extrato da Conta");
                System.out.println("4 - Ver Todas as Contas");
                System.out.println("5 - Deleta Contas");
                System.out.println("6 - Deleta Transação");
                System.out.println("7 - Editar Conta");
                System.out.println("8 - Editar Transação");
                System.out.println("9 - Sair");
                System.out.print("Opção: ");
                int opcao = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcao) {
                    case 1:
                        System.out.print("Digite o ID da conta: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Digite o nome do titular: ");
                        String name = scanner.nextLine();

                        System.out.print("Digite o saldo inicial: ");
                        double saldo = scanner.nextDouble();
                        scanner.nextLine();

                        Account account = new Account(id, name, saldo);

                            if (account.createAccount(conn)) {
                                System.out.println("Conta criada com sucesso!");
                            } else {
                                System.out.println("Falha ao criar a conta.");
                            }

                        break;

                    case 2:
                        System.out.print("Digite o ID da transação: ");
                        int transId = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Digite o ID da conta: ");
                        int accId = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Digite o tipo de transação (ex: Pagamento, Depósito): ");
                        String tipo = scanner.nextLine();

                        System.out.print("Digite o valor da transação: ");
                        double valor = scanner.nextDouble();
                        scanner.nextLine();

                        Transaction transaction = new Transaction(transId, accId, tipo, valor, null);

                            if (transaction.createTransaction(conn)) {
                                System.out.println("Transação criada com sucesso!");
                            } else {
                                System.out.println("Falha ao criar a transação.");
                            }

                        break;

                    case 3:
                        System.out.print("Digite o ID da conta para ver o extrato: ");
                        int contaId = scanner.nextInt();
                        scanner.nextLine();

                        account = new Account(contaId);

                        if (account.searchAccount(conn)) {
                            System.out.println("Extrato exibido com sucesso.");
                        } else {
                            System.out.println("Conta não encontrada.");
                        }

                        break;
                    
                    case 4:
                        System.out.println("Ver Todas as Contas");
                            account = new Account(); 
                            if (account.allAccounts(conn)) {
                                System.out.println("Todas as contas exibidas com sucesso.");
                            } else {
                                System.out.println("Nenhuma conta encontrada.");
                            }

                        break;

                    case 5:
                        System.out.print("Digite o ID da conta: ");
                            contaId = scanner.nextInt(); 
                            scanner.nextLine();

                            account = new Account(contaId);

                            if (account.deleteAccount(conn)) {
                                System.out.println("Conta deletada com sucesso.");
                            } else {
                                System.out.println("Conta não encontrada.");
                            }

                        break;

                    case 6:
                        System.out.print("Digite o ID da transação: ");
                        int transactionId = scanner.nextInt();  
                        scanner.nextLine();

                        transaction = new Transaction(transactionId);  

                        if (transaction.deleteTransaction(conn)) {
                            System.out.println("Transação deletada com sucesso.");
                        } else {
                            System.out.println("Transação não encontrada.");
                        }

                        break;

                    case 7:
                        System.out.println("Editar Conta");

                        System.out.print("Digite o ID da conta: ");
                        contaId = scanner.nextInt();
                        scanner.nextLine(); 

                        account = new Account(contaId);

                        if (account.updateAccount(conn)) {
                            System.out.println("Conta atualizada com sucesso.");
                        } else {
                            System.out.println("Conta não encontrada ou erro ao atualizar.");
                        }

                        break;

                    case 8:
                        System.out.println("Editar Transação");

                        System.out.print("Digite o ID da Transação: ");
                        transactionId = scanner.nextInt();
                        scanner.nextLine(); 

                        transaction = new Transaction(transactionId);

                        if (transaction.updateTransaction(conn)) {
                            System.out.println("Transação atualizada com sucesso.");
                        } else {
                            System.out.println("Transação não encontrada ou erro ao atualizar.");
                        }

                        break;

                    case 9:
                        running = false;
                        System.out.println("Encerrando sistema. Até logo!");
                        break;

                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                        break;
                }
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC não encontrado!");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}