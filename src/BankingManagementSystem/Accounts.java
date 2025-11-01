package BankingManagementSystem;


import java.sql.*;
import java.util.Scanner;

public class Accounts {
    private Connection connection;
    private Scanner scanner;

    //constructor:
    public Accounts(Connection connection,Scanner scanner ){
        this.connection = connection;
        this.scanner = scanner;
    }

    //Methods:
    public long open_account(String email){
        if(!account_exist(email)){
            String open_acc = "Insert into account(account_no,full_name,email,balance,mpin) VALUES (?,?,?,?,?)";
            scanner.nextLine();

            System.out.print("Enter the name: ");
            String full_name = scanner.nextLine();

            System.out.print("Enter the amt of initial deposit: ");
            double bal = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Create the MPIN: ");
            String mpin = scanner.nextLine();

            try {
                long account_number = generate_acc_no();
                PreparedStatement preparedStatement = connection.prepareStatement(open_acc);
                preparedStatement.setLong(1,account_number);
                preparedStatement.setString(2,full_name);
                preparedStatement.setString(3,email);
                preparedStatement.setDouble(4,bal);
                preparedStatement.setString(5,mpin);

                int rows = preparedStatement.executeUpdate();

                if (rows>0){
                    return account_number;
                }else {
                    throw new RuntimeException("Account creation failed");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Account Exists!!");
    }

    public long get_acc_no(String email){
        String get_ac = "Select account_no from account where email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(get_ac);
            preparedStatement.setString(1,email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return resultSet.getLong("account_no");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        throw new RuntimeException("Account Does not exists !!");
    }

    public long generate_acc_no(){
        try {
            String gen = "Select account_no from account order by account_no desc Limit 1";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(gen);
            if (resultSet.next()){
                long last_digits = resultSet.getLong("account_no");
                return last_digits + 1;
            }else {
                return 10000100;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean account_exist(String email){
        String email_Exist = "Select account_no from account where email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(email_Exist);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
