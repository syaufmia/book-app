package sum.ike.control.connector.db;

import sum.ike.control.AuthorDao;
import sum.ike.control.connector.db.DbConnector;
import sum.ike.model.Author;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementCreator {

    public void select() {

        AuthorDao aDao = new AuthorDao();
        DbConnector db = new DbConnector();
        Connection con = db.connect(DbConnector.BIB_URL, DbConnector.BIB_USER, DbConnector.BIB_PASS);
        Statement state = null;
        try {
            state = con.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM author");

            while (result.next()) {
                aDao.getData(result);
            }

            result.close();
            state.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Author author : aDao.getAll()) {
            System.out.println(author);
        }
    }

    public void insert(Author author) {

        AuthorDao aDao = new AuthorDao();
        DbConnector db = new DbConnector();
        Connection con = db.connect(DbConnector.BIB_URL, DbConnector.BIB_USER, DbConnector.BIB_PASS);
        Statement state = null;
        try {
            state = con.createStatement();
            state.executeUpdate("INSERT INTO author (author_id, first_name, last_name)\n" +
                    "VALUES ("+author.getAuthorID()+", '"+ author.getFirstName() +"', '" +author.getLastName()+ "');");

            state.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
