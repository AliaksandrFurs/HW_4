package Main;

import Entities.User;
import Utils.DBCrudOperations;

public class Main {

    public static void main(String[] args)  {

        User firstUser = new User(1, "Alex", "Minsk");
        User secondUser = new User(2, "John", "Warshaw");
        User thirdUser = new User(3, "Mila", "Warshaw");
        User fourthUser = new User(4, "Sam", "Vilnius");
        User fifthUser = new User(5, "Inga", "Minsk");

            DBCrudOperations.addUserToDB(firstUser);
            DBCrudOperations.addUserToDB(secondUser);
            DBCrudOperations.addUserToDB(thirdUser);
            DBCrudOperations.addUserToDB(fourthUser);
            DBCrudOperations.addUserToDB(fifthUser);

            DBCrudOperations.addUserAccount(fifthUser, 1, 0, "EUR");
            DBCrudOperations.addUserAccount(fifthUser, 2, 0, "USD");
            DBCrudOperations.addUserAccount(fifthUser, 3, 20000, "BYN");
            DBCrudOperations.addUserAccount(firstUser, 1, 10000, "RUB");

            DBCrudOperations.accountUpdate(fifthUser, "BYN", 10000);
            DBCrudOperations.accountUpdate(fifthUser, "BYN", 200000000);
            //DBCrudOperations.accountUpdate(secondUser, "BYN", 25000);

    }
}
