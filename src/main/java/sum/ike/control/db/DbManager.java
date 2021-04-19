package sum.ike.control.db;

import sum.ike.control.dao.AuthorDao;
import sum.ike.control.dao.BookDao;
import sum.ike.control.dao.LoanDao;
import sum.ike.control.dao.UserDao;
import sum.ike.model.Author;
import sum.ike.model.Book;
import sum.ike.model.Loan;
import sum.ike.model.User;

import java.sql.*;
import java.time.LocalDate;
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
                    BookDao bDao = new BookDao();
                    while (result.next()) {
                        bDao.readDataToList(result);
                    }
                    break;
                case USER:
                    UserDao uDao = new UserDao();
                    while (result.next()) {
                        uDao.getData(result);
                    }
                case LOAN:
                    LoanDao lDao = new LoanDao();
                    while (result.next()) {
                        lDao.getData(result);
                    }
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
                    + author.getAuthorId()
                    + ", '"
                    + author.getFirstName().toUpperCase(Locale.ROOT)
                    + "', '"
                    + author.getLastName().toUpperCase(Locale.ROOT)
                    + "');");
            state.close();
            con.close();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("Doppelter Eintrag wurde ignoriert. " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertUser (User user) {
        DbConnector db = new DbConnector();
        Connection con = db.connect(DbConnector.BIB_URL, DbConnector.BIB_USER, DbConnector.BIB_PASS);
        Statement state;
        try {
            state = con.createStatement();
            state.executeUpdate(insertInto(Table.USER)
                    + " VALUES ("
                    + user.getUserId()
                    + ", '"
                    + user.getPassword()
                    + "', '"
                    + user.getUsername()
                    + "', '"
                    + user.getEMail()
                    + "', '"
                    + user.getFirstName()
                    + "', '"
                    + user.getLastName()
                    + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertLoan (Loan loan) {
        DbConnector db = new DbConnector();
        Connection con = db.connect(DbConnector.BIB_URL, DbConnector.BIB_USER, DbConnector.BIB_PASS);
        Statement state;
        try {
            state = con.createStatement();
            state.executeUpdate(insertInto(Table.LOAN)
                    + " VALUES ("
                    + loan.getLoanId()
                    + ", "
                    + loan.getBook().getBookId()
                    + ", "
                    + loan.getUser().getUserId()
                    + ", '"
                    + loan.getStartDate()
                    + "', '"
                    + loan.getEndDate()
                    + "');");
            state.close();
            con.close();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("Doppelter Eintrag wurde ignoriert. " + e.getMessage());
        } catch (SQLException e) {
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
                    + book.getAuthor().getAuthorId()
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
        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("Doppelter Eintrag wurde ignoriert. " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAuthor (int authorId, String firstName, String lastName) {
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
                    + authorId + ";");
            state.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLoan (int loanId, LocalDate endDate) {
        DbConnector db = new DbConnector();
        Connection con = db.connect(DbConnector.BIB_URL, DbConnector.BIB_USER, DbConnector.BIB_PASS);
        Statement state;

        try {
            state = con.createStatement();
            state.executeUpdate("UPDATE loan SET end_date = '"
                    + endDate
                    + "' WHERE loan_id = "
                    + loanId + ";");
            state.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAuthor (int authorId) {
        DbConnector db = new DbConnector();
        Connection con = db.connect(DbConnector.BIB_URL, DbConnector.BIB_USER, DbConnector.BIB_PASS);
        Statement state;

        try {
            state = con.createStatement();
            state.executeUpdate("DELETE FROM author WHERE author_id = "
                    + authorId + ";");
            state.close();
            con.close();
        } catch (SQLException e) {
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
                    + isbn + "';");
            state.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook (int authorId) {
        DbConnector db = new DbConnector();
        Connection con = db.connect(DbConnector.BIB_URL, DbConnector.BIB_USER, DbConnector.BIB_PASS);
        Statement state;
        try {
            state = con.createStatement();
            state.executeUpdate("DELETE FROM book WHERE author_id = "
                    + authorId + ";");
            state.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String insertInto (Table table) {

        switch (table) {
            case AUTHOR:
                return "INSERT INTO " + table + " (author_id, first_name, last_name)";
            case BOOK:
                return "INSERT INTO " + table + " (author_id, title, isbn, publisher, year)";
            case USER:
                return "INSERT INTO " + table + " (user_id, password, username, email, first_name, last_name)";
            case LOAN:
                return "INSERT INTO " + table + " (loan_id, book_id, user_id, start_date, end_date)";
            default:
            return null;
        }
    }

    public enum Table {
        AUTHOR {
            public String toString () {
                return "author";
            }
        },
        BOOK {
            public String toString () {
                return "book";
            }
        },
        USER {
            public String toString () {
                return "user";
            }
        },
        LOAN {
            public String toString () {
                return "loan";
            }
        }
    }
}
