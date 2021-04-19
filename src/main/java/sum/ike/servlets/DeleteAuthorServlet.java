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

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            getServletContext().getRequestDispatcher("/search-author.jsp").forward(req, resp);


    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthorDao aDao = new AuthorDao();
        DbManager dbm = new DbManager();

        dbm.selectAll(DbManager.Table.AUTHOR);
        dbm.selectAll(DbManager.Table.BOOK);
        dbm.selectAll(DbManager.Table.USER);
        dbm.selectAll(DbManager.Table.LOAN);

        String selectedIndex = req.getParameter("selected");
        String name = req.getParameter("name");


        StringBuilder sb = new StringBuilder();


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
//                        StringBuilder htmlText = new StringBuilder();
                        //for-Schleife zum Erstellen der Liste (mit HTML tags) als String
//                        for (int i = 0; i < list.size(); i++) {
//                            htmlText.append("<tr><td><input type=\"radio\" name=\"filtered-author\" value=\"")
//                                    .append(i)
//                                    .append("\" size=\"100\" checked=\"checked\" />")
//                                    .append("</td> <td> <label> ")
//                                    .append(StringTrimmer.trim(list.get(i).toStringNoID()))
//                                    .append("</td></tr>");
//                        }
//                        req.setAttribute("htmltext", htmlText.toString());
//                        req.setAttribute("name", name);
                        getServletContext().getRequestDispatcher("/choose-an-author-to-delete.jsp").forward(req, resp);
                    } else {
                        req.setAttribute("message", "Es konnte kein Autor unter diesem Namen gefunden werden. ");
                        doGet(req, resp);
                    }

                } else {
                    sb.append("Du musst schon etwas eingeben. ");
                    req.setAttribute("message", sb.toString());
                    getServletContext().getRequestDispatcher("/search-author.jsp").forward(req, resp);
                }
            } else {
                sb.append("Ein unerwarteter Fehler ist aufgetreten. ");
                req.setAttribute("message", sb.toString());
                getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            }
        }

    }
}
