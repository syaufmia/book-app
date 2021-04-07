package sum.ike.servlets;

import sum.ike.control.dao.AuthorDao;
import sum.ike.control.dao.BookDao;
import sum.ike.control.utils.FileManager;
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
        FileManager fm = new FileManager();
        AuthorDao aDao = new AuthorDao();
        aDao.importData(fm.readCSVFileAsObjects("AuthorList.csv"));


        String selectedIndex = req.getParameter("selected");
        String name = req.getParameter("name");


        StringBuilder sb = new StringBuilder();


        if (selectedIndex != null) {
            BookDao bDao = new BookDao();
            Author selectedAuthor = aDao.getAuthorByID(Integer.parseInt(selectedIndex));
            //TODO: METHOD FOR DELETING BOOK BY ID!
            aDao.delete(Integer.parseInt(selectedIndex));
            System.out.println(selectedAuthor);


            fm.writeObjectFileCSV(aDao.exportData(), "AuthorList.csv", FileManager.AUTHOR_TABLE_HEADER_ROW);
            fm.writeObjectFileCSV(bDao.exportData(),"BookList.csv",FileManager.BOOK_TABLE_HEADER_ROW);
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
//                        getServletContext().getRequestDispatcher("/search-author.jsp").forward(req, resp);
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
