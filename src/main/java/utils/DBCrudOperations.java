package utils;

import constants.Constants;
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
                System.out.println(Constants.noDriver);
            }

            String insertRequest = "INSERT INTO Users (userId, name, address) VALUES (?,?,?)";
            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(insertRequest)){
                     statement.setInt(1, _user.getUserId());
                     statement.setString(2, _user.getName());
                     statement.setString(3, _user.getAddress());
                     statement.executeUpdate();
                System.out.println(Constants.userAdded);
            } catch (SQLException | IOException e) {
                System.out.println(Constants.noCOnnection);
            }
        }else{
            System.out.println(Constants.userNotAdded);
        }
    }

    public static void addUserAccount (User _user, int _accountId, int _balance, String _currency) {

        try{
            Class.forName("org.postgresql.Driver");
        }catch (Exception e){
            System.out.println(Constants.noDriver);
        }
        String insertRequest = "INSERT INTO Accounts (accountId, userId, balance, currency) VALUES (?,?,?,?)";
        Boolean ifIdExist = VerifyingEntities.checkUserId(_user);
        Boolean ifCurrencyExist = VerifyingEntities.checkCurrency(_currency);
        Boolean ifAccountWithCurrencyExist = VerifyingEntities.ifAccountWithCurrencyExist(_user, _currency);
        Boolean ifBalanceOk = VerifyingEntities.checkBalanceAmount(_balance);

        if(ifAccountWithCurrencyExist == false && ifIdExist == true && ifCurrencyExist == true && ifBalanceOk == true){
            try(Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(insertRequest)){
                statement.setInt(1, _accountId);
                statement.setInt(2, _user.getUserId());
                statement.setInt(3, _balance);
                statement.setString(4, _currency);
                statement.executeUpdate();
                System.out.println(Constants.accountAdded);
            }catch (SQLException | IOException e){
                System.out.println(Constants.noCOnnection);
            }
        }else{
            System.out.println(Constants.accountNotAdded);
        }
    }

    public static void withdrowAccount (User _user, int _transactionId, String _currency, int _sum){

        String insertRequest = "INSERT INTO Transactions (transactionId, accountId, amount) VALUES (?,?,?)";
        String updateRequest = "UPDATE Accounts SET balance=? WHERE accountId=?";
        int accountId = 0;
        int finalBalance = 0;

        Boolean ifIdExist = VerifyingEntities.checkUserId(_user);
        Boolean ifCurrencyExist = VerifyingEntities.checkCurrency(_currency);
        Boolean isSumOk = VerifyingEntities.checkTransactionAmount(_sum);
        Boolean ifAccountWithCurrencyExist = VerifyingEntities.ifAccountWithCurrencyExist(_user, _currency);
        Boolean ifTransactionAmountOk = VerifyingEntities.checkTransactionAmount(_sum);
        Boolean iffinalAccountBalanceOk = VerifyingEntities.checkAccountFinalBalance(_sum, _currency, '-');
        Boolean ifTransactionSuccesfull = false;


        if(ifIdExist == true && ifCurrencyExist == true && isSumOk == true && ifAccountWithCurrencyExist == true
        && ifTransactionAmountOk == true && iffinalAccountBalanceOk == true){

            try (Connection connection = DBConnection.getConnection();
                 Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery("SELECT accountID, balance FROM Accounts WHERE currency=" + "'" + _currency + "'");
                while (rs.next()) {
                    accountId = rs.getInt(1);
                    finalBalance = rs.getInt(2) - _sum;
                }
            } catch (SQLException | IOException e) {
                System.out.println(Constants.noCOnnection);
            }

            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(insertRequest)) {
                statement.setInt(1, _transactionId);
                statement.setInt(2, accountId);
                statement.setInt(3, _sum - (_sum * 2));
                statement.executeUpdate();
                ifTransactionSuccesfull = true;
            } catch (SQLException | IOException e) {
                System.out.println(Constants.transactionExist);
            }

            if(ifTransactionSuccesfull == true) {
                try (Connection connection = DBConnection.getConnection();
                     PreparedStatement statement = connection.prepareStatement(updateRequest)) {
                    statement.setInt(1, finalBalance);
                    statement.setInt(2, accountId);
                    statement.executeUpdate();
                    System.out.println(Constants.withdrawOk);
                } catch (SQLException | IOException e) {
                    System.out.println(Constants.error);
                }
            }
        }else {
            System.out.println(Constants.withdrawNotOk);
        }
    }

    public static void accountUpdate(User _user, int _transactionId, String _currency, int _sum) {

        String insertRequest = "INSERT INTO Transactions (transactionId, accountId, amount) VALUES (?,?,?)";
        String updateRequest = "UPDATE Accounts SET balance=? WHERE accountId=?";
        int accountId = 0;
        int finalBalance = 0;

        Boolean ifIdExist = VerifyingEntities.checkUserId(_user);
        Boolean ifCurrencyExist = VerifyingEntities.checkCurrency(_currency);
        Boolean isSumOk = VerifyingEntities.checkTransactionAmount(_sum);
        Boolean ifAccountWithCurrencyExist = VerifyingEntities.ifAccountWithCurrencyExist(_user, _currency);
        Boolean ifTransactionAmountOk = VerifyingEntities.checkTransactionAmount(_sum);
        Boolean iffinalAccountBalanceOk = VerifyingEntities.checkAccountFinalBalance(_sum, _currency, '+');
        Boolean ifTransactionSuccesfull = false;


        if (ifIdExist == true && ifCurrencyExist == true && isSumOk == true && ifAccountWithCurrencyExist == true
                && ifTransactionAmountOk == true && iffinalAccountBalanceOk == true) {

            try (Connection connection = DBConnection.getConnection();
                 Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery("SELECT accountID, balance FROM Accounts WHERE currency=" + "'" + _currency + "'");
                while (rs.next()) {
                    accountId = rs.getInt(1);
                    finalBalance = rs.getInt(2) + _sum;
                }
            } catch (SQLException | IOException e) {
                System.out.println(Constants.noCOnnection);
            }

            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(insertRequest)) {
                statement.setInt(1, _transactionId);
                statement.setInt(2, accountId);
                statement.setInt(3, _sum - (_sum * 2));
                statement.executeUpdate();
                ifTransactionSuccesfull = true;
            } catch (SQLException | IOException e) {
                System.out.println(Constants.transactionExist);
            }

            if (ifTransactionSuccesfull == true) {
                try (Connection connection = DBConnection.getConnection();
                     PreparedStatement statement = connection.prepareStatement(updateRequest)) {
                    statement.setInt(1, finalBalance);
                    statement.setInt(2, accountId);
                    statement.executeUpdate();
                    System.out.println(Constants.updateOk);
                } catch (SQLException | IOException e) {
                    System.out.println(Constants.error);
                }
            }
        } else {
            System.out.println(Constants.uodateNotOk);
        }
    }
}
