package BankingManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    private Connection connection;
    private Scanner scanner;

    public User(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void register(){
        scanner.nextLine();
        System.out.print("Enter the name: ");
        String name = scanner.nextLine();

        System.out.print("Enter the email: ");
        String email = scanner.nextLine();

        System.out.print("Enter the password: ");
        String pass = scanner.nextLine();

        if (userExists(email)){
            System.out.println("Already Exists!!");
            return;
        }

        String reg = "Insert into user(full_name, email, password) Values (?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(reg);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,pass);

            int rowsAffect = preparedStatement.executeUpdate();

            if (rowsAffect>0){
                System.out.println("Registered Success !!");
            }else {
                System.out.println("Failed !!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public String login(){
        scanner.nextLine();
        System.out.print("Enter the Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter the password: ");
        String pass = scanner.nextLine();

        String login_query = "Select * from user where email = ? AND password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(login_query);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,pass);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return email;
            }else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean userExists(String email){
        String user_query = "Select * from user where email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(user_query);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
