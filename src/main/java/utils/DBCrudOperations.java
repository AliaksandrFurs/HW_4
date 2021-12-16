package utils;

import entities.User;
import verifying.VerifyingEntities;

import java.io.IOException;
import java.sql.*;

public class DBCrudOperations {

    public  static void addUserToDB (User _user)  {
        Boolean ifUserExist = VerifyingEntities.checkUser(_user);
        if (ifUserExist == false){
            try{
                Class.forName("org.postgresql.Driver");
            }catch (Exception e){
                System.out.println("Could not establish connection with DB");
            }

            String insertRequest = "INSERT INTO Users (userId, name, address) VALUES (?,?,?)";
            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(insertRequest)){
                     statement.setInt(1, _user.getUserId());
                     statement.setString(2, _user.getName());
                     statement.setString(3, _user.getAddress());
                     statement.executeUpdate();
                System.out.println("User added");
            } catch (SQLException | IOException e) {
                System.out.println("Connection failed");
            }
        }else{
            System.out.println("User not added");
        }
    }

    public static void addUserAccount (User _user, int _accountId, int _balance, String _currency) {

        try{
            Class.forName("org.postgresql.Driver");
        }catch (Exception e){
            System.out.println("Could not establish connection with DB");
        }
        String insertRequest = "INSERT INTO Accounts (accountId, userId, balance, currency) VALUES (?,?,?,?)";
        Boolean ifIdExist = VerifyingEntities.checkUserId(_user);
        Boolean ifCurrencyExist = VerifyingEntities.checkCurrency(_currency);
        Boolean ifAccountWithCurrencyExist = VerifyingEntities.ifAccountWithCurrencyExist(_user, _currency);

        if(ifAccountWithCurrencyExist == false && ifIdExist == true && ifCurrencyExist == true){
            try(Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(insertRequest)){
                statement.setInt(1, _accountId);
                statement.setInt(2, _user.getUserId());
                statement.setInt(3, _balance);
                statement.setString(4, _currency);
                statement.executeUpdate();
                System.out.println("Account successfully added");
            }catch (SQLException | IOException e){
                System.out.println("Smth goes wrong");
            }
        }else{
            System.out.println("Account has not been added");
        }
    }

    public static void withdrowAccount (User _user, int _transactionId, String _currency, int _sum){

        String insertRequest = "INSERT INTO Transactions (transactionId, accountId, amount) VALUES (?,?,?)";
        String updateRequest = "UPDATE Accounts SET balance=? WHERE accountId=?";
        int accountId = 0;
        int finalSum = 0;
        Boolean ifFinalSumOk = false;
        Boolean transactionSuccesfull = false;

        Boolean ifIdExist = VerifyingEntities.checkUserId(_user);
        Boolean ifCurrencyExist = VerifyingEntities.checkCurrency(_currency);
        Boolean isSumOk = VerifyingEntities.checkTransactionAmount(_sum);
        Boolean ifAccountWithCurrencyExist = VerifyingEntities.ifAccountWithCurrencyExist(_user, _currency);

        if(ifIdExist == false || ifCurrencyExist == false || isSumOk == false || ifAccountWithCurrencyExist == false){
            System.out.println("Check your input data");
        }else {
            try (Connection connection = DBConnection.getConnection();
                 Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery("SELECT accountID, balance FROM Accounts WHERE currency=" + "'" + _currency + "'");
                while (rs.next()) {
                    accountId = rs.getInt(1);
                    finalSum = rs.getInt("balance") - _sum;
                }

            } catch (SQLException | IOException e) {
                System.out.println("Smth goes wrong");
            }

            if(finalSum < 0 || finalSum > 2000000000) {
                transactionSuccesfull = false;
                System.out.println("Transaction declined");
            }else{
                transactionSuccesfull = true;
                try (Connection connection = DBConnection.getConnection();
                     PreparedStatement statement = connection.prepareStatement(insertRequest)) {
                    statement.setInt(1, _transactionId);
                    statement.setInt(2, accountId);
                    statement.setInt(3, _sum-(_sum * 2));
                    statement.executeUpdate();
                } catch (SQLException | IOException e) {
                    System.out.println("Smth goes wrong");
                    transactionSuccesfull = false;
                }
            }

            if (transactionSuccesfull == true) {
                try (Connection connection = DBConnection.getConnection();
                     PreparedStatement statement = connection.prepareStatement(updateRequest)) {
                    statement.setInt(1, finalSum);
                    statement.setInt(2, accountId);
                    statement.executeUpdate();
                    System.out.println("Account withdrowed successfully");
                } catch (SQLException | IOException e) {
                    System.out.println("Smth goes wrong");
                }
            }
        }
    }

    public static void accountUpdate(User _user, int _transactionId, String _currency, int _sum){
        String insertRequest = "INSERT INTO Transactions (transactionId, accountId, amount) VALUES (?,?,?)";
        String updateRequest = "UPDATE Accounts SET balance=? WHERE accountId=?";
        int accountId = 0;
        int finalSum = 0;
        Boolean transactionSuccesfull = false;

        Boolean ifIdExist = VerifyingEntities.checkUserId(_user);
        Boolean ifCurrencyExist = VerifyingEntities.checkCurrency(_currency);
        Boolean isSumOk = VerifyingEntities.checkTransactionAmount(_sum);
        Boolean ifAccountWithCurrencyExist = VerifyingEntities.ifAccountWithCurrencyExist(_user, _currency);

        if(ifIdExist == false || ifCurrencyExist == false || isSumOk == false || ifAccountWithCurrencyExist == false){
            System.out.println("Check your input data");
        }else {
            try (Connection connection = DBConnection.getConnection();
                 Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery("SELECT accountID, balance FROM Accounts WHERE currency=" + "'" + _currency + "'");
                while (rs.next()) {
                    accountId = rs.getInt(1);
                    finalSum = rs.getInt("balance") + _sum;
                }

            } catch (SQLException | IOException e) {
                System.out.println("Smth goes wrong");
            }

            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(insertRequest)) {
                statement.setInt(1, _transactionId);
                statement.setInt(2, accountId);
                statement.setInt(3, _sum);
                statement.executeUpdate();
                transactionSuccesfull = true;
            } catch (SQLException | IOException e) {
                System.out.println("Smth goes wrong");
                transactionSuccesfull = false;
            }

            if (transactionSuccesfull == true) {
                try (Connection connection = DBConnection.getConnection();
                     PreparedStatement statement = connection.prepareStatement(updateRequest)) {
                    statement.setInt(1, finalSum);
                    statement.setInt(2, accountId);
                    System.out.println("Account updated successfully");
                    statement.executeUpdate();
                } catch (SQLException | IOException e) {
                    System.out.println("Smth goes wrong");
                }
            }else {
                System.out.println("Transaction declined");
            }
        }
    }

}
