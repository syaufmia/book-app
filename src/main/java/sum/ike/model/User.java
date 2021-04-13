package sum.ike.model;

import java.io.Serializable;


public class User implements Serializable {

    private static final long serialVersionUID = 3L;
    private int userId;
    private String username;
    private String password;
    private String eMail;
    private String firstName;
    private String lastName;
    private static int userIdCounter = 1;

    public User () { }

    public User (String username, String password, String eMail, String firstName, String lastName) {
        userId = userIdCounter++;
        this.username = username;
        this.password = password;
        this.eMail = eMail;
        this.firstName = firstName;
        this.lastName = lastName;

    }


    public User (int userId, String username, String password, String eMail, String firstName, String lastName) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.eMail = eMail;
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public static void setUserIdCounter (int userIdCounter) {
        User.userIdCounter = userIdCounter;
    }

    public int getUserId () {
        return userId;
    }

    public void setUserId (int userId) {
        this.userId = userId;
    }

    public String getEMail () {
        return eMail;
    }

    public void setEMail (String eMail) {
        this.eMail = eMail;
    }

    public String getFirstName () {
        return firstName;
    }

    public void setFirstName (String firstName) {
        this.firstName = firstName;
    }

    public String getLastName () {
        return lastName;
    }

    public void setLastName (String lastName) {
        this.lastName = lastName;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }


    @Override
    public String toString () {
        return userId + "," + username + "," + password + "," + eMail + "," + firstName + "," + lastName;
    }
}
