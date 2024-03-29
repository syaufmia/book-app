package sum.ike.servlets;

import sum.ike.control.dao.AuthorDao;
import sum.ike.control.db.DbManager;
import sum.ike.control.utils.StringTrimmer;
import sum.ike.model.Author;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AddBookSearchAuthorServlet extends HttpServlet {

    AuthorDao aDao = new AuthorDao();
    DbManager dbm = new DbManager();

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String titel = req.getParameter("titel");
        String isbn = req.getParameter("isbn");
        String publisher = req.getParameter("publisher");
        String yearStr = req.getParameter("year");
        String name = req.getParameter("name");

        if ((boolean) req.getAttribute("list-empty")) {
            req.setAttribute("titel", titel);
            req.setAttribute("isbn",isbn);
            req.setAttribute("publisher",publisher);
            req.setAttribute("year",yearStr);
            req.setAttribute("message", "Deine Suche war leider nicht erfolgreich. ");
            getServletContext().getRequestDispatcher("/added-author").forward(req, resp);
        }

        if ((titel != null) && (isbn != null) && (publisher != null) && (yearStr != null) && (name != null)) {
            req.setAttribute("titel", titel);
            req.setAttribute("isbn",isbn);
            req.setAttribute("publisher",publisher);
            req.setAttribute("year",yearStr);
            req.setAttribute("name", name);


            req.setAttribute("filtered-author-list", req.getAttribute("filtered-author-list"));
            getServletContext().getRequestDispatcher("/choose-an-author.jsp").forward(req, resp);
        }
        else {
            req.setAttribute("message", "Tut mir Leid! Es ist ein Fehler aufgetreten. Bitte versuche es erneut. ");
            getServletContext().getRequestDispatcher(("/index.jsp")).forward(req, resp);
        }
    }
    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        callDb();
        String name = req.getParameter("name");
        StringBuilder sb = new StringBuilder();

        if (name != null) { //if not null
            if (!name.isEmpty()) { //if not empty
                List<Author> list = aDao.searchFor(name);
                if (!list.isEmpty()) { //list not empty
                    req.setAttribute("list-empty", false);
                    StringBuilder htmlText = new StringBuilder();
                    //for-Schleife zum Erstellen der Liste (mit HTML tags) als String
                    //TODO: use jstl
                    req.setAttribute("filteredAuthorList", list);
//                    for (int i = 0; i < list.size(); i++) {
//                        htmlText.append("<tr><td><input type=\"radio\" name=\"filtered-author\" value=\"")
//                                .append(i)
//                                .append("\" size=\"100\" checked=\"checked\" />")
//                                .append("</td> <td> <label> ")
//                                .append(StringTrimmer.trim(list.get(i).toStringNoId()))
//                                .append( "</td></tr>");
//                    }
//                    req.setAttribute("html-text", htmlText);

                    req.setAttribute("name", name);
                }
                else {
                    req.setAttribute("list-empty", true);
                }
                doGet(req, resp);
            } else {
                sb.append("Du musst schon etwas eingeben. ");
                req.setAttribute("message", sb.toString());
                getServletContext().getRequestDispatcher("/added-book").forward(req, resp);
            }
        }
        else {
            sb.append("Ein unerwarteter Fehler ist aufgetreten. ");
            req.setAttribute("message",sb.toString());
            getServletContext().getRequestDispatcher("/added-book").forward(req, resp);
        }
    }
    protected void callDb () {
        dbm.selectAll(DbManager.Table.AUTHOR);
        dbm.selectAll(DbManager.Table.BOOK);
        dbm.selectAll(DbManager.Table.USER);
        dbm.selectAll(DbManager.Table.LOAN);
    }
}
