package sum.ike.servlets;

import sum.ike.control.dao.AuthorDao;
import sum.ike.control.dao.BookDao;
import sum.ike.control.dao.LoanDao;
import sum.ike.control.dao.UserDao;
import sum.ike.control.db.DbManager;
import sum.ike.control.utils.StringTrimmer;
import sum.ike.model.Book;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        DbManager dbm = new DbManager();
        dbm.selectAll(DbManager.Table.BOOK);

        resp.setContentType("text/html;charset=UTF-8");

        String sortBy = req.getParameter("by");

        if (sortBy != null) {
            if (sortBy.equalsIgnoreCase("titel")) {
                bDao.getBookList().sort(new Book.BookTitleComparator());
                doGet(req, resp);

            }
            if (sortBy.equalsIgnoreCase("isbn")) {
                bDao.getBookList().sort(new Book.BookIsbnComparator());
                doGet(req, resp);

            }
            if (sortBy.equalsIgnoreCase("verlag")) {
                bDao.getBookList().sort(new Book.BookPublisherComparator());
                doGet(req, resp);
            }
        }
    }
}
