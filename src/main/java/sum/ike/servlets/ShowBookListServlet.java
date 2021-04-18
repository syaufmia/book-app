package sum.ike.servlets;

import sum.ike.control.dao.AuthorDao;
import sum.ike.control.dao.BookDao;
import sum.ike.control.dao.LoanDao;
import sum.ike.control.dao.UserDao;
import sum.ike.control.db.DbManager;
import sum.ike.control.utils.StringTrimmer;
import sum.ike.model.Book;
import sum.ike.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;


public class ShowBookListServlet extends HttpServlet {

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        DbManager dbm = new DbManager();
        BookDao bDao = new BookDao();
        LoanDao lDao = new LoanDao();

        dbm.selectAll(DbManager.Table.AUTHOR);
        dbm.selectAll(DbManager.Table.BOOK);
        dbm.selectAll(DbManager.Table.LOAN);

        resp.setContentType("text/html;charset=UTF-8");
        req.setAttribute("bookList", bDao.getBookList());
        req.setAttribute("borrowedBookList", lDao.getListOfBorrowedBooksOnDate(LocalDate.now()));
        getServletContext().getRequestDispatcher("/show-books.jsp").forward(req, resp);
    }


    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        BookDao bDao = new BookDao();
        LoanDao lDao = new LoanDao();
        UserDao uDao = new UserDao();
        DbManager dbm = new DbManager();
        dbm.selectAll(DbManager.Table.AUTHOR);
        dbm.selectAll(DbManager.Table.BOOK);
        dbm.selectAll(DbManager.Table.USER);
        dbm.selectAll(DbManager.Table.LOAN);

        HttpSession httpSession = req.getSession();
        String bookId = req.getParameter("bookId");

        if (httpSession.getAttribute("user") != null
                && bookId != null) {
            User user = (User) httpSession.getAttribute("user");
            lDao.addLoan(Integer.parseInt(bookId), user.getUserId());
            dbm.insertLoan(lDao.getLastLoan());
        }

        req.setAttribute("sentence", "Du hast das Buch " + lDao.getLastLoan().getBook().getTitle() + " ausgeliehen");
        doGet(req, resp);

    }
}
