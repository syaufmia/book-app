package sum.ike.control.dao;

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
            addLoan(result.getInt("loan_id"),
                    result.getInt("book_id"),
                    result.getInt("user_id"),
                    result.getDate("start_date").toLocalDate(),
                    result.getDate("end_date").toLocalDate());
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

    //TODO: get list<loan> to be returned today

    public List<Loan> getAll () {
        return ll;
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

    public void addLoan (int loanId, int bookId, int userId, LocalDate startDate, LocalDate endDate) {
        BookDao bDao = new BookDao();
        UserDao uDao = new UserDao();
        Loan loan = new Loan(
                loanId,
                bDao.getBook(bookId),
                uDao.getUser(userId),
                startDate,
                endDate
        );
        ll.add(loan);
    }

}
