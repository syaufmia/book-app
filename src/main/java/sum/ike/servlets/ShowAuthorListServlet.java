package sum.ike.servlets;

import sum.ike.control.AuthorDao;
import sum.ike.control.FileManager;
import sum.ike.control.StringTrimmer;
import sum.ike.model.Author;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ShowAuthorListServlet extends HttpServlet {

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        FileManager fm = new FileManager();
        AuthorDao aDao = new AuthorDao();
        aDao.importData(fm.readCSVFileAsObjects("AuthorList.csv"));


        StringBuilder htmlText = new StringBuilder();
        htmlText.append("<form name=\"sort-table\" action=\"\" method=\"POST\">\n")
                .append("<tr>\n<th> <input type=\"submit\" class=\"invisible-button\" value=\"Vorname\" name=\"by\" /></th>\n")
                .append("<th><input type=\"submit\" class=\"invisible-button\" value=\"Nachname\" name=\"by\" /></th>\n")
                .append("</tr>\n")
                .append("</form>");
        for (int i = 0; i < aDao.getAll().size(); i++) {
            htmlText.append("<tr>\n")
                    .append("<td>\n")
                    .append(StringTrimmer.trim(aDao.getAll().get(i).getFirstName()))
                    .append("\n</td>\n")
                    .append("<td>\n")
                    .append(StringTrimmer.trim(aDao.getAll().get(i).getLastName()))
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

        FileManager fm = new FileManager();
        AuthorDao aDao = new AuthorDao();


        resp.setContentType("text/html;charset=UTF-8");

        aDao.importData(fm.readCSVFileAsObjects("AuthorList.csv"));

        String sortBy = req.getParameter("by");

        if (sortBy != null) {
            if (sortBy.equalsIgnoreCase("vorname")) {
                aDao.getAll().sort(new Author.AuthorFirstNameComparator());
                doGet(req, resp);

            }
            if (sortBy.equalsIgnoreCase("nachname")) {
                aDao.getAll().sort(new Author.AuthorLastNameComparator());
                doGet(req, resp);

            }
        }
    }
}
