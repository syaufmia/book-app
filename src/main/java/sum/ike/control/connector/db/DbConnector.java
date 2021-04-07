package sum.ike.control.connector.db;


import java.sql.*;

public class DbConnector {

    public static final String BIB_URL = "jdbc:mysql://localhost:3306/bib";
    public static final String BIB_USER = "root";
    public static final String BIB_PASS = "root";

    public Connection connect(String url, String user, String pass) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
