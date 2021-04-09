package sum.ike.servlets;

import sum.ike.control.dao.AuthorDao;
import sum.ike.control.dao.BookDao;
import sum.ike.control.db.DbManager;
import sum.ike.control.utils.StringTrimmer;
import sum.ike.model.Book;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ShowBookListServlet extends HttpServlet {

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        BookDao bDao = new BookDao();
        AuthorDao aDao = new AuthorDao();

        DbManager dbm = new DbManager();

        dbm.selectAll(DbManager.Table.AUTHOR);
        dbm.selectAll(DbManager.Table.BOOK);

        resp.setContentType("text/html;charset=UTF-8");

        StringBuilder htmlText = new StringBuilder();

        htmlText.append("<form name=\"sort-table\" action=\"\" method=\"POST\">\n")
                .append("<tr>\n<th> <input type=\"submit\" class=\"invisible-button\" value=\"Titel\" name=\"by\" /></th>\n")
                .append("<th><input type=\"submit\" class=\"invisible-button\" value=\"ISBN\" name=\"by\" /></th>\n")
                .append("<th>Autor</th>\n")
                .append("<th><input type=\"submit\" class=\"invisible-button\" value=\"Verlag\" name=\"by\" /></th>\n")
                .append("<th>Jahr</th>\n</tr>\n")
                .append("</form>");
        for (int i = 0; i < bDao.getAll().size(); i++) {
            htmlText.append("<tr>\n")
                    .append("<td>\n")
                    .append(StringTrimmer.trim(bDao.getAll().get(i).getTitle()))
                    .append("\n</td>\n")
                    .append("<td>\n")
                    .append(StringTrimmer.trim(bDao.getAll().get(i).getIsbn()))
                    .append("\n</td>\n")
                    .append("<td>\n")
                    .append(StringTrimmer.trim(aDao.getAuthorByID(bDao.getAll().get(i).getAuthorID()).toStringNoID()))
                    .append("\n</td>\n")
                    .append("<td>\n")
                    .append(StringTrimmer.trim(bDao.getAll().get(i).getPublisher()))
                    .append("\n</td>\n")
                    .append("<td>\n")
                    .append(bDao.getAll().get(i).getPublishedYear())
                    .append("\n</td>\n")
                    .append("</tr>\n");
        }
        req.setAttribute("htmlText",htmlText.toString());
        req.setAttribute("tableName", "Bücherliste");
        req.setAttribute("sentence", "hier findest du alle Bücher, die in meiner Bibliothek enthalten sind.");



        getServletContext().getRequestDispatcher("/show-list.jsp").forward(req, resp);
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
                bDao.getAll().sort(new Book.BookTitleComparator());
                doGet(req, resp);

            }
            if (sortBy.equalsIgnoreCase("isbn")) {
                bDao.getAll().sort(new Book.BookIsbnComparator());
                doGet(req, resp);

            }
            if (sortBy.equalsIgnoreCase("verlag")) {
                bDao.getAll().sort(new Book.BookPublisherComparator());
                doGet(req, resp);
            }
        }
    }
}
