package sum.ike.control.dao;

import sum.ike.model.Book;
import sum.ike.model.Loan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanDao {

    private static final List<Loan> ll = new ArrayList<>();


    public void getData (ResultSet result) throws SQLException {
        if (!loanIdExists(result.getInt("loan_id"))) {
            if (result.getObject("return_date") != null) {
            addLoan(result.getInt("loan_id"),
                    result.getInt("book_id"),
                    result.getInt("user_id"),
                    result.getDate("start_date").toLocalDate(),
                    result.getDate("end_date").toLocalDate(),
                    result.getDate("return_date").toLocalDate());
            }
            else {
                addLoan(result.getInt("loan_id"),
                        result.getInt("book_id"),
                        result.getInt("user_id"),
                        result.getDate("start_date").toLocalDate(),
                        result.getDate("end_date").toLocalDate(),
                        null);
            }
        }
        setLoanIdCounterToMax();
    }

    /**
     * return a loan-object by id.
     * returns null, if id does not exists.
     */
    public Loan getLoan (int loanId) {
        Loan loan = null;
        for (Loan l : ll) {
            if (l.getLoanId() == loanId) {
                loan = l;
                break;
            }
        }
        return loan;
    }

    public Loan getLastLoan () {
        return ll.get(ll.size() - 1);
    }

    /**
     * returns book with a specific date between startDate and endDate.
     * Does not check, if book is already in booklist, since a book
     * should be only available once.
     */
    public List<Book> getListOfBorrowedBooks () {
        List<Book> borrowedBooks = new ArrayList<>();
        for (Loan l : ll) {
            if (l.getReturnDate() == null) {
                borrowedBooks.add(l.getBook());
            }
        }
        return borrowedBooks;
    }

    public List<Loan> getLoanListByUser (int userId) {
        List<Loan> loanListByUser = new ArrayList<>();
        UserDao uDao = new UserDao();
        for (Loan l : ll) {
            if (l.getUser().getUserId() == userId) {
                loanListByUser.add(l);
            }
        }
        return loanListByUser;
    }

    public List<Loan> getLoanListByUserOnDate (int userId, LocalDate date) {
        List<Loan> loanList = new ArrayList<>();
        for (Loan l : ll) {
            if (l.getUser().getUserId() == userId) {

                if (l.getEndDate() == null) {
                    loanList.add(l);

                }
                else if (date.isBefore(l.getEndDate())
                        && date.isAfter(l.getStartDate().minusDays(1))) {
                    loanList.add(l);
                }
            }
        }
        return loanList;
    }

    /**
     * gets a booklist with all borrowed books by a user on a specific date.
     * In order to work correctly, database for books and for users has to be called first!
     */
    public List<Book> getListOfBorrowedBooksOnDateByUser (int userId, LocalDate date) {
        List<Book> borrowedBooks = new ArrayList<>();
        for (Loan l : ll) {
            if (l.getUser().getUserId() == userId) {
                if (date.isBefore(l.getEndDate())
                        && date.isAfter(l.getStartDate().minusDays(1))) {
                    borrowedBooks.add(l.getBook());
                }
                else if (l.getEndDate() == null) {
                    borrowedBooks.add(l.getBook());

                }
            }
        }
        return borrowedBooks;
    }

    public boolean bookIsAvailable (Book book, LocalDate date) {
        boolean available = true;
        for (Loan l : ll) {
            if (date.isBefore(l.getEndDate())
                    && date.isAfter(l.getStartDate().minusDays(1))
                    && l.getBook().getBookId() == book.getBookId()) {
                available = false;
                break;
            }
        }
        return available;
    }

    public boolean loanIsDelayed (int loanId, LocalDate date) {
        boolean delayed = false;
        for (Loan l : ll) {
            if (loanId == l.getLoanId()) {
                if (date.isAfter(l.getEndDate())) {
                    delayed = true;
                }
            }
        }
        return delayed;
    }


    public List<Loan> getLoanListByEndDate (LocalDate endDate) {
        List<Loan> loanListByEndDate = new ArrayList<>();
        for (Loan l : ll) {
            if (l.getEndDate() == endDate) {
                loanListByEndDate.add(l);
            }
        }
        return loanListByEndDate;
    }
    //TODO: get list<loan> to be returned today

    public List<Loan> getAll () {
        return ll;
    }

    public void returnBook (int loanId, LocalDate date) {
        for (Loan l : ll) {
            if (l.getLoanId() == loanId
                    && l.getReturnDate() == null) {
                if (!date.isAfter(l.getEndDate())) {
                    l.setEndDate(date);
                }
                l.setReturnDate(date);
            }
        }
    }

    public boolean loanExtendable (int loanId, LocalDate date) {
        boolean extendable = true;
        for (Loan l : ll) {
            if (l.getLoanId() == loanId) {
                if (date.isAfter(l.getEndDate())) {
                    extendable = false;
                }
            }
        }
        return extendable;
    }


    public void changeEndDate (int loanId, LocalDate date) {
        for (Loan l : ll) {
            if (l.getLoanId() == loanId) {
                l.setEndDate(date);
            }
        }
    }

    public void extendEndDate (int loanId, int days) {
        for (Loan l : ll) {
            if (l.getLoanId() == loanId) {
                LocalDate date = l.getEndDate();
                l.setEndDate(date.plusDays(days));
            }
        }
    }

    public boolean loanIdExists (int loanId) {
        boolean exists = false;
        for (Loan l : ll) {
            if (l.getLoanId() == loanId) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public int getMaxLoanId () {
        int max = 0;
        for (Loan l : ll) {
            if (l.getLoanId() > max) {
                max = l.getLoanId();
            }
        }
        return max;
    }

    public void setLoanIdCounterToMax () {
        Loan.setLoanIdCounter(getMaxLoanId() + 1);
    }


    public void addLoan (int bookId, int userId) {
        BookDao bDao = new BookDao();
        UserDao uDao = new UserDao();
        Loan loan = new Loan(bDao.getBook(bookId),uDao.getUser(userId));
        ll.add(loan);
    }

    public void addLoan (int loanId, int bookId, int userId, LocalDate startDate, LocalDate endDate, LocalDate returnDate) {
        BookDao bDao = new BookDao();
        UserDao uDao = new UserDao();
        Loan loan = new Loan(
                loanId,
                bDao.getBook(bookId),
                uDao.getUser(userId),
                startDate,
                endDate,
                returnDate
        );
        ll.add(loan);
    }

}
