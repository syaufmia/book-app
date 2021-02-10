package sum.ike.control;

import sum.ike.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private final List<User> ul = new ArrayList<>();

    public User getDefaultAdmin () {
        User admin = new User("admin","admin","Safiye Uzun");
        admin.setAdmin(true);
        return admin;
    }

    public boolean isDefaultAdmin(String username, String password) {
        return (username.equals(getDefaultAdmin().getUsername()) && password.equals((getDefaultAdmin().getPassword())));
    }

    public String getDefaultAdminUID () {
        return getDefaultAdmin().getUID();
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



//
//    public boolean checkLogin (String username, String password) {
//        if (username.equals(getDefaultAdmin().getUsername()) && password.equals((getDefaultAdmin().getPassword()))) {
//            return true;
//        }
//        else return containsUser(username, password);
//    }


    public String getUID (String username, String password) {
        String UID = "NONE";

        for (User user : ul) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    UID = user.getUID();
                    break;
                }
            }
        } return UID;
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

    public boolean UserExists (String username, String password) {
        boolean exists = false;
        for (User user : ul) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    exists = true;
                    break;
                }
            }
        }
        return exists;
    }

    public void addUser (User user) {
        if (!ul.contains(user)) {
            ul.add(user);
        }
    }

    public boolean UIDExists (String UID) {
        boolean exists = false;
        for (User user : ul) {
            if (user.getUID().equals(UID)) {
                exists = true;
                break;
            }
        }return exists;
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
