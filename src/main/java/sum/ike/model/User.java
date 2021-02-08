package sum.ike.model;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 3L;
    String UID;
    private String username;
    private String password;
    private String fullName;
    private boolean admin;

    public User () { }


    public User (String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.admin = false;
        this.UID = "" +createUID();
    }

    public User (String username, String password, String fullName, String UID) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.UID = UID;
        this.admin = UID.charAt(UID.length() - 1) == 'A';

    }

    public String getUID () {
        return UID;
    }

    public void setUID (String UID) {
        this.UID = UID;
    }

    private int createUID () {
        char[] s = new char[username.length()];
        int code = 0;
        for (int i = 0; i < username.length(); i++) {
            s[i] = username.charAt(i);
            code += (s[i]*Math.pow(3,(username.length()-1)-i));
        }
        return code;
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

    public String getFullName () {
        return fullName;
    }

    public void setFullName (String fullName) {
        this.fullName = fullName;
    }

    public boolean isAdmin () {
        return admin;
    }

    public void setAdmin (boolean admin) {
        this.admin = admin;
        if ((admin) && (UID.charAt(UID.length() -1) != 'A'))  {
            UID += "A";
        }
    }


    @Override
    public boolean equals (Object obj) {
        if (obj instanceof User) {
            return ((User) obj).getUID().equalsIgnoreCase(this.UID);
        }
        else return false;
    }

    @Override
    public String toString () {
        return username + "," + password + "," + fullName+ "," + UID;
    }
}
