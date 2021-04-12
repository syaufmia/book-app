package sum.ike.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Loan implements Serializable {

    private static final long serialVersionUID = 5L;
    private int loanId;
    private Book book;
    private User user;
    private LocalDate startDate;
    private LocalDate endDate;

    public Loan (Book book, User user) {
        this.book = book;
        this.user = user;
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now().plusDays(14);
    }

    public void extendReturnDate (int days) {
        endDate = endDate.plusDays(days);
    }

    public Book getBook () {
        return book;
    }

    public void setBook (Book book) {
        this.book = book;
    }

    public User getUser () {
        return user;
    }

    public void setUser (User user) {
        this.user = user;
    }

    public LocalDate getStartDate () {
        return startDate;
    }

    public void setStartDate (LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate () {
        return endDate;
    }

    public void setEndDate (LocalDate endDate) {
        this.endDate = endDate;
    }
}
