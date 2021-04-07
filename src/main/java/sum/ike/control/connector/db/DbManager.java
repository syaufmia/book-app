package sum.ike.control.connector.db;

import sum.ike.control.AuthorDao;
import sum.ike.control.BookDao;
import sum.ike.model.Author;
import sum.ike.model.Book;

import java.sql.*;
import java.util.Locale;

public class DbManager {


    public void selectAll (Table table) {

        DbConnector db = new DbConnector();
        Connection con = db.connect(DbConnector.BIB_URL, DbConnector.BIB_USER, DbConnector.BIB_PASS);
        Statement state;
        try {
            state = con.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM " + table);

            switch (table) {
                case AUTHOR:
                    AuthorDao aDao = new AuthorDao();
                    while (result.next()) {
                        aDao.getData(result);
                    }
                    break;
                case BOOK:
                    BookDao bookDao = new BookDao();
                    while (result.next()) {
                        bookDao.getData(result);
                    }
                    break;
            }
            result.close();
            state.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertAuthor (Author author) {

        DbConnector db = new DbConnector();
        Connection con = db.connect(DbConnector.BIB_URL, DbConnector.BIB_USER, DbConnector.BIB_PASS);
        Statement state;
        try {
            state = con.createStatement();
            state.executeUpdate(insertInto(Table.AUTHOR)
                    + " VALUES ("
                    + author.getAuthorID()
                    + ", '"
                    + author.getFirstName().toUpperCase(Locale.ROOT)
                    + "', '"
                    + author.getLastName().toUpperCase(Locale.ROOT)
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

    public void insertBook (Book book) {

        DbConnector db = new DbConnector();
        Connection con = db.connect(DbConnector.BIB_URL, DbConnector.BIB_USER, DbConnector.BIB_PASS);
        Statement state;
        try {
            state = con.createStatement();
            state.executeUpdate(insertInto(Table.BOOK)
                    + " VALUES ("
                    + book.getAuthorID()
                    + ", '"
                    + book.getTitle().toUpperCase(Locale.ROOT)
                    + "', '"
                    + book.getIsbn()
                    + "', '"
                    + book.getPublisher().toUpperCase(Locale.ROOT)
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

    public void updateAuthor (int ID, String firstName, String lastName) {
        DbConnector db = new DbConnector();
        Connection con = db.connect(DbConnector.BIB_URL, DbConnector.BIB_USER, DbConnector.BIB_PASS);
        Statement state;

        try {
            state = con.createStatement();
            state.executeUpdate("UPDATE author SET first_name = '"
                    + firstName.toUpperCase(Locale.ROOT)
                    + "', last_name = '"
                    + lastName.toUpperCase(Locale.ROOT)
                    + "' WHERE author_id = "
                    + ID + ";");
            state.close();
            con.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAuthor (int ID) {
        DbConnector db = new DbConnector();
        Connection con = db.connect(DbConnector.BIB_URL, DbConnector.BIB_USER, DbConnector.BIB_PASS);
        Statement state;

        try {
            state = con.createStatement();
            state.executeUpdate("DELETE FROM author WHERE author_id = "
                    + ID + ";");
            state.close();
            con.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public void deleteBook (String isbn) {
            DbConnector db = new DbConnector();
            Connection con = db.connect(DbConnector.BIB_URL, DbConnector.BIB_USER, DbConnector.BIB_PASS);
            Statement state;

            try {
                state = con.createStatement();
                state.executeUpdate("DELETE FROM book WHERE isbn = '"
                        + isbn +"';");
                state.close();
                con.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public void deleteBook (int ID) {
        DbConnector db = new DbConnector();
        Connection con = db.connect(DbConnector.BIB_URL, DbConnector.BIB_USER, DbConnector.BIB_PASS);
        Statement state;

        try {
            state = con.createStatement();
            state.executeUpdate("DELETE FROM author WHERE author_id = "
                    + ID +";");
            state.close();
            con.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
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
