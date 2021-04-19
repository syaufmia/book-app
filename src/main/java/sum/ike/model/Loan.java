package sum.ike.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Loan implements Serializable {

    private static final long serialVersionUID = 5L;
    private int loanId;
    private Book book;
    private User user;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime returnDate;
    private static int loanIdCounter = 1;

    /**
     * when borrowing a new book, self-created date
     */
    public Loan (Book book, User user) {
        this.loanId = loanIdCounter++;
        this.book = book;
        this.user = user;
        this.startDate = LocalDateTime.now();
        this.endDate = LocalDateTime.of(LocalDate.now().plusDays(14), LocalTime.of(14,0));
    }

    /**
     * when calling Loan-Data from db
     */
    public Loan (int loanId, Book book, User user, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime returnDate) {
        this.loanId = loanId;
        this.book = book;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.returnDate = returnDate;
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

    public LocalDateTime getReturnDate () {
        return returnDate;
    }

    public void setReturnDate (LocalDateTime returnDate) {
        this.returnDate = returnDate;
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

    public LocalDateTime getStartDate () {
        return startDate;
    }

    public void setStartDate (LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate () {
        return endDate;
    }

    public void setEndDate (LocalDateTime endDate) {
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
