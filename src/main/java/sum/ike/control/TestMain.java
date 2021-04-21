package sum.ike.control;

import sum.ike.control.dao.LoanDao;
import sum.ike.control.db.DbConnector;
import sum.ike.control.db.DbManager;
import sum.ike.model.Author;
import sum.ike.model.Book;
import sum.ike.model.Loan;
import sum.ike.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class TestMain {


    public static void main(String[] args){

        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        Author author = new Author(1,"Jen", "Man");
        Book book = new Book(2,author,"111", "title", "verlag", 2001);
        User user = new User(3,"safi", "1234","s@gmail.com","safiye", "uzun");
        Loan loan = new Loan(book, user);
        System.out.println(loan);

    }

}
