package sum.ike.servlets;

import sum.ike.control.dao.AuthorDao;
import sum.ike.control.db.DbManager;
import sum.ike.model.Author;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ShowAuthorListServlet extends HttpServlet {

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        AuthorDao aDao = new AuthorDao();
        DbManager dbm = new DbManager();
        dbm.selectAll(DbManager.Table.AUTHOR);

        resp.setContentType("text/html;charset=UTF-8");

        req.setAttribute("authorList", aDao.getAuthorList());
        getServletContext().getRequestDispatcher("/show-authors.jsp").forward(req, resp);
    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        AuthorDao aDao = new AuthorDao();
        DbManager dbm = new DbManager();

        resp.setContentType("text/html;charset=UTF-8");

        dbm.selectAll(DbManager.Table.AUTHOR);

        String sortBy = req.getParameter("by");

        if (sortBy != null) {
            if (sortBy.equalsIgnoreCase("vorname")) {
                aDao.getAuthorList().sort(new Author.AuthorFirstNameComparator());
                doGet(req, resp);

            }
            if (sortBy.equalsIgnoreCase("nachname")) {
                aDao.getAuthorList().sort(new Author.AuthorLastNameComparator());
                doGet(req, resp);

            }
        }
    }
}
