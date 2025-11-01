package BankingManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {
    private Connection connection;
    private Scanner scanner;

    public AccountManager(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void credit_money(long account_no){
        scanner.nextLine();
        System.out.print("Enter the Amount: ");
        double amt = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter the MPIN: ");
        String mpin = scanner.nextLine();

        try{
            connection.setAutoCommit(false);
            if (account_no != 0) {
                String query1 = "Select * from account where account_no = ? AND mpin = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query1);
                preparedStatement.setLong(1, account_no);
                preparedStatement.setString(2, mpin);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String credit_query = "Update account set balance = balance + ? Where account_no = ?";
                    PreparedStatement preparedStatement1 = connection.prepareStatement(credit_query);
                    preparedStatement1.setDouble(1, amt);
                    preparedStatement1.setLong(2, account_no);

                    int rowsAffected = preparedStatement1.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println(amt + "Credited Successfully !!");
                        connection.commit();
                        connection.setAutoCommit(true);
                    } else {
                        System.out.println("Transaction Failed");
                        connection.rollback();
                        connection.setAutoCommit(true);
                    }
                } else {
                    System.out.println("Invalid MPIN..!");
                }
            }connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void debit_money(long account_no){
        scanner.nextLine();
        System.out.print("Enter the amount: ");
        double amt = scanner.nextDouble();
        System.out.println("Enter the Mpin: ");
        String mpin = scanner.nextLine();

        try {
            connection.setAutoCommit(false);
            if (account_no != 0){
                String query2 = "Select * from account Where account_no = ? AND mpin = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query2);
                preparedStatement.setLong(1,account_no);
                preparedStatement.setString(2,mpin);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    double current_balance = resultSet.getDouble("balance");
                    if (amt<=current_balance){
                        String debit_query = "UPDATE Account SET balance = balance - ? WHERE account_no = ?";
                        PreparedStatement preparedStatement1 = connection.prepareStatement(debit_query);
                        preparedStatement1.setDouble(1, amt);
                        preparedStatement1.setLong(2, account_no);
                        int rowsAffected = preparedStatement1.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println(amt+" debited Successfully");
                            connection.commit();
                            connection.setAutoCommit(true);
                            return;
                        } else {
                            System.out.println("Transaction Failed!");
                            connection.rollback();
                            connection.setAutoCommit(true);
                        }
                    }else{
                        System.out.println("Insufficient Balance!");
                    }
                }else{
                    System.out.println("Invalid Pin!");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void transfer_money(long sender_ac){
        scanner.nextLine();
        System.out.print("Enter Receiver Account Number: ");
        long receiver_ac = scanner.nextLong();

        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter Security Pin: ");
        String mpin = scanner.nextLine();
        try{
            connection.setAutoCommit(false);
            if(sender_ac!=0 && receiver_ac!=0){
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Account WHERE account_no = ? AND mpin = ? ");
                preparedStatement.setLong(1, sender_ac);
                preparedStatement.setString(2, mpin);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    double current_balance = resultSet.getDouble("balance");
                    if (amount<=current_balance){

                        // Write debit and credit queries
                        String debit_query = "UPDATE Account SET balance = balance - ? WHERE account_no = ?";
                        String credit_query = "UPDATE Account SET balance = balance + ? WHERE account_no = ?";

                        // Debit and Credit prepared Statements
                        PreparedStatement creditPreparedStatement = connection.prepareStatement(credit_query);
                        PreparedStatement debitPreparedStatement = connection.prepareStatement(debit_query);

                        // Set Values for debit and credit prepared statements
                        creditPreparedStatement.setDouble(1, amount);
                        creditPreparedStatement.setLong(2, receiver_ac);
                        debitPreparedStatement.setDouble(1, amount);
                        debitPreparedStatement.setLong(2, sender_ac);

                        int rowsAffected1 = debitPreparedStatement.executeUpdate();
                        int rowsAffected2 = creditPreparedStatement.executeUpdate();

                        if (rowsAffected1 > 0 && rowsAffected2 > 0) {
                            System.out.println("Transaction Successful!");
                            System.out.println("Rs."+amount+" Transferred Successfully");
                            connection.commit();
                            connection.setAutoCommit(true);
                            return;
                        } else {
                            System.out.println("Transaction Failed");
                            connection.rollback();
                            connection.setAutoCommit(true);
                        }
                    }else{
                        System.out.println("Insufficient Balance!");
                    }
                }else{
                    System.out.println("Invalid Security Pin!");
                }
            }else{
                System.out.println("Invalid account number");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void check_bal(long account_no){
        scanner.nextLine();
        scanner.nextLine();
        System.out.print("Enter Security Pin: ");
        String mpin = scanner.nextLine();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT balance FROM Account WHERE account_no = ? AND mpin = ?");
            preparedStatement.setLong(1, account_no);
            preparedStatement.setString(2, mpin);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                double balance = resultSet.getDouble("balance");
                System.out.println("Balance: "+balance);
            }else{
                System.out.println("Invalid Pin!");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
