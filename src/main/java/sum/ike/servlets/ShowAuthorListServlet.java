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


public class ShowAuthorListServlet extends HttpServlet {

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        AuthorDao aDao = new AuthorDao();
        DbManager dbm = new DbManager();
        dbm.selectAll(DbManager.Table.AUTHOR);

        StringBuilder htmlText = new StringBuilder();
        htmlText.append("<form name=\"sort-table\" action=\"\" method=\"POST\">\n")
                .append("<tr>\n<th> <input type=\"submit\" class=\"invisible-button\" value=\"Vorname\" name=\"by\" /></th>\n")
                .append("<th><input type=\"submit\" class=\"invisible-button\" value=\"Nachname\" name=\"by\" /></th>\n")
                .append("</tr>\n")
                .append("</form>");
        for (int i = 0; i < aDao.getAuthorList().size(); i++) {
            htmlText.append("<tr>\n")
                    .append("<td>\n")
                    .append(StringTrimmer.trim(aDao.getAuthorList().get(i).getFirstName()))
                    .append("\n</td>\n")
                    .append("<td>\n")
                    .append(StringTrimmer.trim(aDao.getAuthorList().get(i).getLastName()))
                    .append("\n</td>\n")
                .append("</tr>\n");
        }


        req.setAttribute("tableName", "Autorenliste");
        req.setAttribute("sentence", "hier findest du alle Autoren, die in meiner Bibliothek gespeichert sind.");
        req.setAttribute("htmlText",htmlText.toString());
        getServletContext().getRequestDispatcher("/show-list.jsp").forward(req, resp);
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
