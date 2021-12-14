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

    public static boolean checkUserId (User _user) throws SQLException, IOException {

        try{
            Class.forName("org.postgresql.Driver");
        } catch (Exception e){
            System.out.println("Нет драйвера");
        }

        try(Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery("SELECT userID from USERS WHERE userID=" + _user.getUserId());
            while(rs.next()){
                int userID = rs.getInt("userID");
                if(userID == _user.getUserId()){
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Нет такого ID");
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
            case "RUN":
                return true;
            default :
                return false;
        }
    }

    public static Boolean checkAccountCum(int _sum) throws SQLException, IOException {
        try{
            Class.forName("org.postgresql.Driver");
        } catch (Exception e){
            System.out.println("Нет драйвера");
        }

        try(Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement()){


    } catch (SQLException e){
            System.out.println("Y");
        }

    }
}
