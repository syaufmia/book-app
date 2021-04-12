package sum.ike.control.dao;

import sum.ike.model.Person;
import sum.ike.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private final List<User> ul = new ArrayList<>();


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


    public boolean usernameExists (String username) {
        boolean exists = false;
        for (User user : ul) {
            if (user.getUsername().equals(username)) {
                exists = true;
                break;
            }
        } return exists;
    }

    public boolean UserLoginCorrect (String username, String password) {
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

    public void addUser (String username, String password, String eMail, String firstName, String lastName) {
        Person person = new Person (firstName, lastName);
        if (!usernameExists(username)) {
            User user = new User(username, password, eMail, person.getPersonId());
            ul.add(user);
        }

    }

    public void addUser (int userId, int personId, String username, String password, String eMail) {
        User user = new User(userId, personId, username, password, eMail);
        ul.add(user);
    }

    public List<User> getUl () {
        return ul;
    }
}
