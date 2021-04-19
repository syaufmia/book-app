package sum.ike.servlets;

import sum.ike.control.dao.AuthorDao;
import sum.ike.control.db.DbManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ShowAuthorListServlet extends HttpServlet {

    AuthorDao aDao = new AuthorDao();
    DbManager dbm = new DbManager();

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        callDb();
        resp.setContentType("text/html;charset=UTF-8");

        req.setAttribute("authorList", aDao.getAuthorList());
        getServletContext().getRequestDispatcher("/show-authors.jsp").forward(req, resp);
    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        callDb();
//
//        String sortBy = req.getParameter("by");
//
//        if (sortBy != null) {
//            if (sortBy.equalsIgnoreCase("vorname")) {
//                aDao.getAuthorList().sort(new Author.AuthorFirstNameComparator());
//                doGet(req, resp);
//
//            }
//            if (sortBy.equalsIgnoreCase("nachname")) {
//                aDao.getAuthorList().sort(new Author.AuthorLastNameComparator());
//                doGet(req, resp);
//
//            }
//        }
    }

    protected void callDb () {
        dbm.selectAll(DbManager.Table.AUTHOR);
        dbm.selectAll(DbManager.Table.BOOK);
        dbm.selectAll(DbManager.Table.USER);
        dbm.selectAll(DbManager.Table.LOAN);
    }
}
