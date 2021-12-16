package entities;

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

    public User (int _userID, String _name){
        this.userId = _userID;
        this.name = _name;
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
