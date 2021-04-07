package sum.ike.control.connector.db;

import sum.ike.control.AuthorDao;
import sum.ike.model.Author;
import sum.ike.model.Book;

import java.sql.*;

public class StatementCreator {





    public void select(Table table) {

        AuthorDao aDao = new AuthorDao();
        DbConnector db = new DbConnector();
        Connection con = db.connect(DbConnector.BIB_URL, DbConnector.BIB_USER, DbConnector.BIB_PASS);
        Statement state;
        try {
            state = con.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM " + table);

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

    private String insertInto(Table table) {

        switch (table) {
            case AUTHOR:
                return "INSERT INTO " + table + " (author_id, first_name, last_name)";
            case BOOK:
                return "INSERT INTO " + table + " (author_id, title, isbn, publisher, year)";
            default:
                return null;
        }
    }

    public void insert(Author author) {

        DbConnector db = new DbConnector();
        Connection con = db.connect(DbConnector.BIB_URL, DbConnector.BIB_USER, DbConnector.BIB_PASS);
        Statement state;
        try {
            state = con.createStatement();
            state.executeUpdate(insertInto(Table.AUTHOR)
                    + " VALUES ("
                    + author.getAuthorID()
                    + ", '"
                    + author.getFirstName()
                    + "', '"
                    + author.getLastName()
                    + "');");
            state.close();
            con.close();
        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("Doppelter Eintrag wurde ignoriert. " + e.getMessage());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Book book) {

        DbConnector db = new DbConnector();
        Connection con = db.connect(DbConnector.BIB_URL, DbConnector.BIB_USER, DbConnector.BIB_PASS);
        Statement state;
        try {
            state = con.createStatement();
            state.executeUpdate(insertInto(Table.BOOK)
                    + " VALUES ("
                    + book.getAuthorID()
                    + ", '"
                    + book.getTitle()
                    + "', '"
                    + book.getIsbn()
                    + "', '"
                    + book.getPublisher()
                    + "', '"
                    + book.getPublishedYear()
                    + "');");
            state.close();
            con.close();

        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("Doppelter Eintrag wurde ignoriert. " + e.getMessage());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public enum Table {
        AUTHOR {
            public String toString() {
                return "author";
            }
        },
        BOOK {
            public String toString() {
                return "book";
            }
        }
    }
}
