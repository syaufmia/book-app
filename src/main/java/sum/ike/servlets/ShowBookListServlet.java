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

        BookDao bDao = new BookDao();
        AuthorDao aDao = new AuthorDao();
        DbManager dbm = new DbManager();
        UserDao uDao = new UserDao();
        LoanDao lDao = new LoanDao();

        dbm.selectAll(DbManager.Table.AUTHOR);
        dbm.selectAll(DbManager.Table.BOOK);
        dbm.selectAll(DbManager.Table.LOAN);


        resp.setContentType("text/html;charset=UTF-8");

        StringBuilder htmlText = new StringBuilder();
        req.setAttribute("bookList", bDao.getBookList());
        req.setAttribute("borrowedBookList", lDao.getListOfBorrowedBooksOnDate(LocalDate.now()));

        htmlText.append("<form name=\"sort-table\" action=\"\" method=\"POST\">\n")
                .append("<tr>\n<th> <input type=\"submit\" class=\"invisible-button\" value=\"Titel\" name=\"by\" /></th>\n")
                .append("<th><input type=\"submit\" class=\"invisible-button\" value=\"ISBN\" name=\"by\" /></th>\n")
                .append("<th>Autor</th>\n")
                .append("<th><input type=\"submit\" class=\"invisible-button\" value=\"Verlag\" name=\"by\" /></th>\n")
                .append("<th>Jahr</th>\n</tr>\n")
                .append("</form>");
        for (int i = 0; i < bDao.getBookList().size(); i++) {
            htmlText.append("<tr>\n")
                    .append("<td>\n")
                    .append(StringTrimmer.trim(bDao.getBookList().get(i).getTitle()))
                    .append("\n</td>\n")
                    .append("<td>\n")
                    .append(StringTrimmer.trim(bDao.getBookList().get(i).getIsbn()))
                    .append("\n</td>\n")
                    .append("<td>\n")
                    .append(StringTrimmer.trim(aDao.getAuthorById(bDao.getBookList().get(i).getAuthorId()).toStringNoId()))
                    .append("\n</td>\n")
                    .append("<td>\n")
                    .append(StringTrimmer.trim(bDao.getBookList().get(i).getPublisher()))
                    .append("\n</td>\n")
                    .append("<td>\n")
                    .append(bDao.getBookList().get(i).getPublishedYear())
                    .append("\n</td>\n")
                    .append("</tr>\n");
        }
        req.setAttribute("htmlText",htmlText.toString());
        req.setAttribute("tableName", "Bücherliste");
        req.setAttribute("sentence", "hier findest du alle Bücher, die in meiner Bibliothek enthalten sind.");



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
