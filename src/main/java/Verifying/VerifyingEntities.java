package Verifying;
import  Utils.DBConnection;
import Enums.CurrenciesEnum;

import Entities.User;
import jdk.jshell.execution.Util;
import org.postgresql.core.Utils;

import java.io.IOException;
import java.sql.*;
import java.util.Locale;

public class VerifyingEntities {

    // роверка наличия такого юзера по ID
    public static boolean checkUserId (User _user) {

        try{
            Class.forName("org.postgresql.Driver");
        } catch (Exception e){
            System.out.println("Нет драйвера");
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
            System.out.println("Нет такого ID");
        } finally {

        }
        return false;
    }

    //проверка правильности введения валюты
    public static Boolean checkCurrency (String _currency){
        switch (_currency.toUpperCase()){
            case "EUR":
                return true;
            case "USD":
                return true;
            case "BYN":
                return true;
            case "RUN":
                return true;
            default :
                return false;
        }
    }

    //проверка допустимости общей ссуммы на счёте с учётом трансакции Дописть на отрицательные числа
    public static Boolean checkAccountSum(int _sum, int _accountId) throws SQLException, IOException {
        try{
            Class.forName("org.postgresql.Driver");
        } catch (Exception e){
            System.out.println("Нет драйвера");
        }

        try(Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement()){
            int finalSum = _sum;
            ResultSet rs = statement.executeQuery("SELECT amount FROM Accounts where accountId=" + _accountId);
            while (rs.next()){
                finalSum += rs.getInt("accountId");
            }
            if(finalSum > 2000000000){
                return false;
            }else{
                return true;
            }
        } catch (SQLException e){
            System.out.println("Y");
        }
        return false;
    }

    public static Boolean checkUser (User _user)  {

        try{
            Class.forName("org.postgresql.Driver");
        } catch (Exception e){
            System.out.println("Нет драйвера");
        }

        try(Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT name FROM Users");
            while(rs.next()){
                if(_user.getName().equals(rs.getString("name"))){
                    return true;
                }
            }
        } catch (SQLException | IOException e){
            System.out.println("User with this name is already exist");
        }
        return false;
    }

    public static Boolean checkTransactionAmount (int _amount){
        if(_amount > 100000000 || _amount < 0){
            return false;
        }
        return true;
    }

    public static Boolean checkAccountBalance(int _currentBalance){
        if(_currentBalance == 0){
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
            System.out.println("Something wrong");
        }
        return false;
    }


}
