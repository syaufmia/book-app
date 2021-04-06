package sum.ike.control;


import java.sql.*;

public class DbConnector {

    public void connect(SqlQuery query) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bib", "root", "root");
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(query.toString() + " author");
            while (result.next()) {
                System.out.println(result.getString("last_name"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public enum SqlQuery {

        SELECT_ALL {
            public String toString() {
                return "SELECT * FROM";
            }
        },

    }

}
