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
    private static int loanIdCounter = 1;

    /**
     * when borrowing a new book, self-created date
     */
    public Loan (Book book, User user) {
        this.loanId = loanIdCounter++;
        this.book = book;
        this.user = user;
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now().plusDays(14);
    }

    /**
     * when calling Loan-Data from db
     */
    public Loan (int loanId, Book book, User user, LocalDate startDate, LocalDate endDate) {
        this.loanId = loanId;
        this.book = book;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public static void setLoanIdCounter (int loanIdCounter) {
        Loan.loanIdCounter = loanIdCounter;
    }

    public int getLoanId () {
        return loanId;
    }

    public void setLoanId (int loanId) {
        this.loanId = loanId;
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

    @Override
    public String toString () {
        return "Loan{" +
                "loanId=" + loanId +
                ", book=" + book +
                ", user=" + user +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
