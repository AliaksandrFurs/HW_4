package Entities;

public class User {

    private int userId;
    private String name;
    private String address;

    public User (){};

    public User (int _userID, String _name, String _address){
        this.userId = _userID;
        this.name = _name;
        this.address = _address;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }


}
