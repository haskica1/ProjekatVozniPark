package ba.unsa.etf.rpr.projekat;

public class User {
    private int userId;
    private String firstName, lastName;
    private String telephoneNumber;
    private String address;
    private LevelOfResponsibility levelOfResponsibility;
    private String username;
    private String password;


    public User() {
    }

    public User(String firstName, String lastName, String telephoneNumber, String address, LevelOfResponsibility levelOfResponsibility) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephoneNumber = telephoneNumber;
        this.address = address;
        this.levelOfResponsibility = levelOfResponsibility;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LevelOfResponsibility getLevelOfResponsibility() {
        return levelOfResponsibility;
    }

    public void setLevelOfResponsibility(LevelOfResponsibility levelOfResponsibility) {
        this.levelOfResponsibility = levelOfResponsibility;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }


    public boolean equals(User user) {
        if(firstName.equals(user.firstName) && lastName.equals(user.lastName) && telephoneNumber.equals(user.telephoneNumber) && address.equals(user.address) && username.equals(user.username) && password.equals(user.password))  return true;
        return false;
    }
}
