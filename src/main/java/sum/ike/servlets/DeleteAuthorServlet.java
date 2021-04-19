package sum.ike.servlets;

import sum.ike.control.dao.AuthorDao;
import sum.ike.control.db.DbManager;
import sum.ike.model.Author;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DeleteAuthorServlet extends HttpServlet {

    AuthorDao aDao = new AuthorDao();
    DbManager dbm = new DbManager();

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            getServletContext().getRequestDispatcher("/search-author.jsp").forward(req, resp);
    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        callDb();
        String selectedIndex = req.getParameter("selected");
        String name = req.getParameter("name");

        if (selectedIndex != null) {
            int ID = Integer.parseInt(selectedIndex);
            aDao.delete(ID);
            dbm.deleteBook(ID);
            dbm.deleteAuthor(ID);

            req.setAttribute("sentence", "Der Autor und alle dessen Bücher wurden entfernt.");
            doGet(req, resp);
        }
        else {
            if (name != null) { //if not null
                if (!name.isEmpty()) { //if not empty
                    List<Author> list = aDao.searchFor(name);
                    if (!list.isEmpty()) { //list not empty
                        req.setAttribute("authorSearchList", list);
                        getServletContext().getRequestDispatcher("/choose-an-author-to-delete.jsp").forward(req, resp);
                    } else {
                        req.setAttribute("message", "Es konnte kein Autor unter diesem Namen gefunden werden. ");
                        doGet(req, resp);
                    }
                } else {
                    req.setAttribute("message", "Du musst schon etwas eingeben. ");
                    getServletContext().getRequestDispatcher("/search-author.jsp").forward(req, resp);
                }
            } else {
                req.setAttribute("message", "Ein unerwarteter Fehler ist aufgetreten. ");
                getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            }
        }
    }

    protected void callDb () {
        dbm.selectAll(DbManager.Table.AUTHOR);
        dbm.selectAll(DbManager.Table.BOOK);
        dbm.selectAll(DbManager.Table.USER);
        dbm.selectAll(DbManager.Table.LOAN);
    }
}
