package sum.ike.control;

import sum.ike.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private final List<User> ul = new ArrayList<>();

    public User getDefaultAdmin() {
        User admin = new User("admin","admin","admin");
        admin.setAdmin(true);
        return admin;
    }


    public List<Object> exportData () {
        List <Object> savingList = new ArrayList<>();
        if (!ul.contains(null)) {
            savingList.addAll(ul);
        }
        return savingList;
    }

    //importing from a CSV
    public void importData (List<String[]> list) {
        for (String[] element : list) {
            if (element.length == 4) {
                User user = new User(element[0], element[1], element[2]);
                addUser(user);
                if (element[3].charAt(element[3].length() -1) == 'A') {
                    user.setAdmin(true);
                }
            }
        }
    }

    public boolean containsUser (String username, String password) {
        boolean contains = false;
        for (User user : ul) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    contains = true;
                    break;
                }
            }
        }
        return contains;
    }


    public void addUser (User user) {
        if (!ul.contains(user)) {
            ul.add(user);
        }
    }

    public void addUser (String username, String password, String fullName) {
        User user = new User(username, password, fullName);
        if (!ul.contains(user)) {
            ul.add(user);
        }
    }

    public void addUser (String username, String password, String fullName, String UID) {
        User user = new User(username, password, fullName);
    }

    public List<User> getUl () {
        return ul;
    }
}
