package sum.ike.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private static final long serialVersionUID = 3L;
    private int userId;
    private String username;
    private String password;
    private String eMail;
    private int personId;
    private static int userIdCounter = 1;

    public User () { }

    public User (String username, String password, String eMail, int personId) {
        userId = userIdCounter++;
        this.personId = personId;
        this.username = username;
        this.password = password;
        this.eMail = eMail;
    }


    public User (int userId, int personId, String username, String password, String eMail) {
        this.userId = userId;
        this.personId = personId;
        this.username = username;
        this.password = password;
        this.eMail = eMail;
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

    public int getPersonId () {
        return personId;
    }

    public void setPersonId (int personId) {
        this.personId = personId;
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
        return userId + ","  + personId + "," + username + "," + password + "," + eMail;
    }
}
