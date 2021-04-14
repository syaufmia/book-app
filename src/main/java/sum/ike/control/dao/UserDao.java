package sum.ike.control.dao;

import sum.ike.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private static final List<User> ul = new ArrayList<>();

    public void getData(ResultSet result) throws SQLException {
        if (!userIdExists(result.getInt("user_id"))) {
            addUser(result.getInt("user_id"),
                    result.getString("username"),
                    result.getString("password"),
                    result.getString("email"),
                    result.getString("first_name"),
                    result.getString("last_name")
            );
        }
        setUserIdCounterToMax();
    }

    public User getUser (String username, String password) {
        User us = null;
        for (User user : ul) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    us = user;
                    break;
                }
            }
        } return us;
    }

    public User getUser (int userId) {
        User user = null;
        for (User u : ul) {
            if (u.getUserId() == userId) {
                user = u;
                break;
            }
        }
        return user;
    }

    public int getMaxUserId () {
        int max = 0;
        for (User u : ul) {
            if (u.getUserId() > max) {
                max = u.getUserId();
            }
        }
        return max;
    }

    public void setUserIdCounterToMax () {
        User.setUserIdCounter(getMaxUserId() + 1);
    }




    public int getUserId (String username, String password) {
        int userId = 0;
        for (User user : ul) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    userId = user.getUserId();
                    break;
                }
            }
        } return userId;
    }

    public boolean userIdExists (int userId) {
        boolean exists = false;
        for (User user : ul) {
            if (user.getUserId() == userId) {
                exists = true;
                break;
            }
        } return exists;
    }

    public User getLastUser () {
        return ul.get(ul.size() - 1);
    }


    public boolean usernameExists (String username) {
        boolean exists = false;
        for (User user : ul) {
            if (user.getUsername().equals(username)) {
                exists = true;
                break;
            }
        } return exists;
    }

    public boolean userLoginCorrect (String username, String password) {
        boolean correct = false;
        for (User user : ul) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    correct = true;
                    break;
                }
            }
        }
        return correct;
    }

    public void addUser (User user) {
        if (!usernameExists(user.getUsername())) {
            ul.add(user);
        }
    }

    /**
     * adds a new User by creating new user
     */
    public void addUser (String username, String password, String eMail, String firstName, String lastName) {
        if (!usernameExists(username)) {
            User user = new User(username, password, eMail, firstName, lastName);
            ul.add(user);
        }

    }

    /**
     * adds user from db
     */
    public void addUser (int userId, String username, String password, String eMail, String firstName, String lastName) {
        User user = new User(userId, username, password, eMail, firstName, lastName);
        ul.add(user);
    }

    public List<User> getUserList () {
        return ul;
    }
}
