package verifying;
import constants.Constants;
import  utils.DBConnection;

import entities.User;

import java.io.IOException;
import java.sql.*;

public class VerifyingEntities {

    public static boolean checkUserId (User _user) {

        try{
            Class.forName("org.postgresql.Driver");
        } catch (Exception e){
            System.out.println(Constants.noDriver);
        }

        try(Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery("SELECT userID from USERS WHERE userID=" + _user.getUserId());
            while(rs.next()){
                if(_user.getUserId() == rs.getInt("userId")){
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException | IOException e) {
            System.out.println(Constants.noID);
        } finally {

        }
        return false;
    }

    public static Boolean checkCurrency (String _currency){
        switch (_currency.toUpperCase()){
            case "EUR":
                return true;
            case "USD":
                return true;
            case "BYN":
                return true;
            case "RUB":
                return true;
            default :
                return false;
        }
    }

    public static Boolean checkUser (User _user)  {

        try{
            Class.forName("org.postgresql.Driver");
        } catch (Exception e){
            System.out.println(Constants.noDriver);
        }

        try(Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT name FROM Users");
            while(rs.next()){
                if(_user.getName().equals(rs.getString("name"))){
                    System.out.println(Constants.idExists);
                    return true;
                }
            }
        } catch (SQLException | IOException e){
            System.out.println(Constants.noCOnnection);
        }
        return false;
    }

    public static Boolean checkAccountFinalBalance (int _amount, String _currency, char _operation){

        int finalSum = 0;

        switch (_operation){
            case '-':
                try (Connection connection = DBConnection.getConnection();
                     Statement statement = connection.createStatement()) {
                    ResultSet rs = statement.executeQuery("SELECT balance FROM Accounts WHERE currency=" + "'" + _currency + "'");
                    while (rs.next()) {
                        finalSum = rs.getInt("balance") - _amount;
                    }
                } catch (SQLException | IOException e) {
                    System.out.println(Constants.noCOnnection);
                }
                break;
            case '+':
                try (Connection connection = DBConnection.getConnection();
                     Statement statement = connection.createStatement()) {
                    ResultSet rs = statement.executeQuery("SELECT balance FROM Accounts WHERE currency=" + "'" + _currency + "'");
                    while (rs.next()) {
                        finalSum = rs.getInt("balance") + _amount;
                    }
                } catch (SQLException | IOException e) {
                    System.out.println(Constants.noCOnnection);
                }
                break;
        }

        if(finalSum > Constants.maxAccount || finalSum < Constants.minAccount || finalSum == 0){
            return false;
        }
        return true;
    }

    public static Boolean checkTransactionAmount (int _amount){
        if(_amount > Constants.maxTransaction || _amount == 0){
            return false;
        }
        return true;
    }

    public static Boolean checkBalanceAmount (int _amount){
        if (_amount > Constants.maxAccount || _amount < 0){
            return false;
        }
        return true;
    }

    public static Boolean ifAccountWithCurrencyExist (User _user, String _currency){

        try(Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery("SELECT currency FROM Accounts WHERE userId="+_user.getUserId());
            while (rs.next()){
                if(rs.getString("currency").equals(_currency)){
                    return true;
                }
            }
        } catch (SQLException | IOException e){
            System.out.println(Constants.noCOnnection);
        }
        return false;
    }
}
