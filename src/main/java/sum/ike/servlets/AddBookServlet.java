package sum.ike.servlets;

import sum.ike.control.dao.AuthorDao;
import sum.ike.control.dao.BookDao;
import sum.ike.control.db.DbManager;
import sum.ike.control.utils.StringTrimmer;
import sum.ike.model.Author;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddBookServlet extends HttpServlet {
    BookDao bDao = new BookDao();
    AuthorDao aDao = new AuthorDao();
    DbManager dbm = new DbManager();

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/add-book.jsp").forward(req, resp);
    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        callDb();

        String selectedIndex = req.getParameter("filtered-author");
        String titel = req.getParameter("titel");
        String isbn = req.getParameter("isbn");
        String publisher = req.getParameter("publisher");
        String yearStr = req.getParameter("year");
        String name = req.getParameter("name");
        int year = 2000;
        if ((yearStr != null) && yearStr.matches("\\d{4}")) {
            year = Integer.parseInt(yearStr);
        }

        /*
         * Wenn der index und der name nicht Null ist, bedeutet das, dass wir vom Selection-Menü kommen.
         * Aus diesem SelectedAuthor kann nun ein neues Buch erzeugt und in die Liste hinzugefügt werden.
         * Danach wird man auf das "add-book"-menu weitergeleitet.
         */
        if ((selectedIndex != null) && (name != null)) {
            Author selectedAuthor = aDao.searchForAndSelect(name,Integer.parseInt(selectedIndex));
            bDao.addNew(selectedAuthor.getFirstName(), selectedAuthor.getLastName(), isbn, titel, publisher, year);
            dbm.insertBook(bDao.getLastBook());
            req.setAttribute("sentence", "Du hast erfolgreich ein neues Buch von " +
                    StringTrimmer.trim(selectedAuthor.toStringNoId()) +
                    " hinzugefügt. ");
            req.setAttribute("actionURL", "added-book");
            getServletContext().getRequestDispatcher("/add-book.jsp").forward(req, resp);
        }

        /*
         * Dieser Fall tritt auf, wenn selectedIndex | name == null sowie titel, isbn & publisher != null
         * d.h. wenn der User zum ersten Mal nach dem POST-request in add-book.jsp weitergeleitet wird.
         */
        else if ((titel != null) && (isbn != null) && (publisher != null)) {
            if (titel.isEmpty() || isbn.isEmpty() || publisher.isEmpty()) {
                req.setAttribute("message", "Die Felder dürfen nicht leer sein. ");
                doGet(req, resp);
            } else if (bDao.containsIsbn(isbn)) {
                req.setAttribute("message", "Diese ISBN ist bereits in meiner Bibliothek enthalten. ");
                doGet(req, resp);
            }
            else {
                req.setAttribute("titel", StringTrimmer.trim(titel));
                req.setAttribute("isbn", isbn);
                req.setAttribute("publisher", StringTrimmer.trim(publisher));
                req.setAttribute("year", year);
                getServletContext().getRequestDispatcher("/add-author-after-book.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("message", "Ein unerwarteter Fehler ist aufgetreten. ");
            doGet(req, resp);
        }
    }

    protected void callDb () {
        dbm.selectAll(DbManager.Table.AUTHOR);
        dbm.selectAll(DbManager.Table.BOOK);
        dbm.selectAll(DbManager.Table.USER);
        dbm.selectAll(DbManager.Table.LOAN);
    }
}
