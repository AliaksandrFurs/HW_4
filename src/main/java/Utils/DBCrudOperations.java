package Utils;

import Entities.User;
import Verifying.VerifyingEntities;

import java.io.IOException;
import java.sql.*;

public class DBCrudOperations {

    public  static void addUserToDB (User _user)  {
        Boolean ifUserExist = VerifyingEntities.checkUser(_user);
        if (ifUserExist == true){
            try{
                Class.forName("org.postgresql.Driver");
            }catch (Exception e){
                System.out.println("Could not establish connection with DB");
            }

            String insertRequest = "INSERT INTO User (userId, name, address) VALUES (?,?,?)";
            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(insertRequest)){
                     statement.setInt(1, _user.getUserId());
                     statement.setString(2, _user.getName());
                     statement.setString(3, _user.getAddress());
                     statement.executeUpdate();
            } catch (SQLException | IOException e) {
                System.out.println("Connection failed");
            }
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

        if(ifAccountWithCurrencyExist == true && ifIdExist == true && ifCurrencyExist == true){
            try(Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(insertRequest)){
                statement.setInt(1, _accountId);
                statement.setInt(2, _user.getUserId());
                statement.setInt(3, _balance);
                statement.setString(4, _currency);
                statement.executeUpdate();
            }catch (SQLException | IOException e){
                System.out.println("Smth goes wrong");
            }
        }
    }

    public static void withdrow (User _user, String _currency, int _sum){

    }

    public static void accountUpdate(User _user, String _currency, int _sum){
        String insertRequest = "INSERT INTO Transactions (transactionId, accountId, amount) VALUES (null,?,?)";
        int accountId = 0;

        Boolean ifIdExist = VerifyingEntities.checkUserId(_user);
        Boolean ifCurrencyExist = VerifyingEntities.checkCurrency(_currency);
        Boolean isSumOk = VerifyingEntities.checkTransactionAmount(_sum);
        Boolean ifAccountWithCurrencyExist = VerifyingEntities.ifAccountWithCurrencyExist(_user, _currency);

        if(ifIdExist == true && ifCurrencyExist == true && ifAccountWithCurrencyExist == false){
            try(Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement()){
                ResultSet rs = statement.executeQuery("SELECT accountID FROM Accounts WHERE currency=" + "'" + _currency + "'");
                while(rs.next()){
                    accountId = rs.getInt(1);
                }

            }catch (SQLException | IOException e){
                System.out.println("Smth goes wrong");
            }
        }

        if(ifIdExist == true && ifCurrencyExist == true && isSumOk == true && ifAccountWithCurrencyExist == false){

            try(Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(insertRequest)){
                statement.setInt(2, accountId);
                statement.setInt(3,_sum);
                statement.executeUpdate();
             }catch (SQLException | IOException e){
                System.out.println("Smth goes wrong");
            }
        }
    }

}
