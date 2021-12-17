package main;

import entities.*;
import utils.*;

public class Main {

    public static void main(String[] args)  {

        User firstUser = new User(1, "Alex", "Minsk");
        User secondUser = new User(2, "John", "Warshaw");
        User thirdUser = new User(3, "Mila", "Warshaw");
        User fourthUser = new User(4, "Sam", "Vilnius");
        User fifthUser = new User(5, "Inga", "Minsk");
        User sixthUser = new User(6, "Pit");

            //DBCrudOperations.addUserToDB(firstUser);
            //DBCrudOperations.addUserToDB(secondUser);
            //DBCrudOperations.addUserToDB(thirdUser);
            //DBCrudOperations.addUserToDB(fourthUser);
            //DBCrudOperations.addUserToDB(fifthUser);
            //DBCrudOperations.addUserToDB(sixthUser);

            //DBCrudOperations.addUserAccount(fifthUser, 1, 0, "EUR");
            //DBCrudOperations.addUserAccount(fifthUser, 2, 0, "USD");
            //DBCrudOperations.addUserAccount(fifthUser, 3, 20000, "BYN");
            //DBCrudOperations.addUserAccount(firstUser, 1, 10000, "RUB");
            //DBCrudOperations.addUserAccount(firstUser, 4, 10000, "RUB");
            //DBCrudOperations.addUserAccount(firstUser, 5, 10000, "USD");
            //DBCrudOperations.addUserAccount(secondUser, 6, -800, "USD");

            DBCrudOperations.accountUpdate(fifthUser, 1, "BYN", 10000);
            DBCrudOperations.accountUpdate(fifthUser, 2, "BYN", 200000000);
            DBCrudOperations.accountUpdate(firstUser, 3, "RUB", 25000);
            DBCrudOperations.accountUpdate(firstUser, 3, "RUB", 150000);

            DBCrudOperations.withdrowAccount(fifthUser, 4,"BYN", 100);
            DBCrudOperations.withdrowAccount(fifthUser, 5,"BYN", 10);
            DBCrudOperations.withdrowAccount(fifthUser, 5,"BYN", 80000);
            DBCrudOperations.withdrowAccount(fifthUser, 5,"BYN", 60);

    }
}
