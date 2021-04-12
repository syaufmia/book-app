package sum.ike.servlets;

import sum.ike.control.dao.AuthorDao;
import sum.ike.control.dao.BookDao;
import sum.ike.control.db.DbManager;
import sum.ike.model.Author;
import sum.ike.model.Book;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet (name = "DeleteBookServlet", value = "/deleted-book/")
public class DeleteBookServlet extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        getServletContext().getRequestDispatcher("/search-book.jsp").forward(req,resp);
    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        BookDao bDao = new BookDao();
        AuthorDao aDao = new AuthorDao();
        DbManager dbm = new DbManager();
        dbm.selectAll(DbManager.Table.AUTHOR);
        dbm.selectAll(DbManager.Table.BOOK);

        String word = req.getParameter("word");
        String selected  = req.getParameter("filtered-book");

        if (selected ==null && word != null) {
            List<Book> filteredList = new ArrayList<>(bDao.getFilteredList(word, Book.Attribute.TITLE));

            for (Book b : bDao.getFilteredList(word, Book.Attribute.ISBN)) {
                if (!filteredList.contains(b)) {
                    filteredList.add(b);
                }
            }
            for (Book b : bDao.getFilteredList(word, Book.Attribute.PUBLISHER)) {
                if (!filteredList.contains(b)) {
                    filteredList.add(b);
                }
            }

            for (Author a : aDao.searchFor(word)) {
                for (Book b : bDao.getBookListOfAuthor(a)) {
                    if (!filteredList.contains(b)) {
                        filteredList.add(b);
                    }
                }
            }

            if (!filteredList.isEmpty()) {
                req.setAttribute("searchList", filteredList);
                getServletContext().getRequestDispatcher("/delete-book.jsp").forward(req, resp);
            }
            else {
                req.setAttribute("message", "Es gibt keine Ergebnisse für dieses Buch.");
                doGet(req, resp);
            }
        }
        else if (selected != null){

            bDao.delete(bDao.getBook(selected));
            dbm.deleteBook(selected);

            req.setAttribute("sentence", "Du hast das Buch erfolgreich gelöscht.");
            doGet(req,resp);
        }

        else {
            doGet(req,resp);
        }
//
//
//        if (selectedIndex != null && !name.isEmpty()) {
//            BookDao bDao = new BookDao();
//            bDao.importData(fm.readCSVFileAsObjects("BookList.csv"));
//            Author selectedAuthor = aDao.searchForAndSelect(name,Integer.parseInt(selectedIndex));
//
//            aDao.delete(Integer.parseInt(selectedIndex));
//
//
//            fm.writeObjectFileCSV(aDao.exportData(), "AuthorList.csv", FileManager.AUTHOR_TABLE_HEADER_ROW);
//            fm.writeObjectFileCSV(bDao.exportData(),"BookList.csv",FileManager.BOOK_TABLE_HEADER_ROW);
//            req.setAttribute("sentence", "Du hast " +
//                    StringTrimmer.trim(selectedAuthor.toStringNoID()) +
//                    " und dessen Bücher aus meiner Bibliothek entfernt. ");
//            getServletContext().getRequestDispatcher("/deleted-author").forward(req, resp);
//        }
//        else {
//            if (name != null) { //if not null
//                if (!name.isEmpty()) { //if not empty
//                    List<Author> list = aDao.searchFor(name);
//                    if (!list.isEmpty()) { //list not empty
//                        StringBuilder htmlText = new StringBuilder();
//                        //for-Schleife zum Erstellen der Liste (mit HTML tags) als String
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
//                        getServletContext().getRequestDispatcher("/choose-an-author-to-delete.jsp").forward(req, resp);
//                    } else {
//                        doGet(req, resp);
//                        req.setAttribute("message", "Es konnte kein Autor unter diesem Namen gefunden werden. ");
//                        getServletContext().getRequestDispatcher("/search-author.jsp").forward(req, resp);
//                    }
//
//                } else {
//                    sb.append("Du musst schon etwas eingeben. ");
//                    req.setAttribute("message", sb.toString());
//                    getServletContext().getRequestDispatcher("/search-author.jsp").forward(req, resp);
//                }
//            } else {
//                sb.append("Ein unerwarteter Fehler ist aufgetreten. ");
//                req.setAttribute("message", sb.toString());
//                getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
//            }
//        }

    }
}
