package sum.ike.servlets;

import sum.ike.control.AuthorDao;
import sum.ike.control.BookDao;
import sum.ike.control.FileManager;
import sum.ike.control.StringTrimmer;
import sum.ike.model.Author;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddBookServlet extends HttpServlet {

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/add-book.jsp").forward(req, resp);

    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        FileManager fm = new FileManager();
        BookDao bDao = new BookDao();
        AuthorDao aDao = new AuthorDao();
        bDao.importData(fm.readCSVFileAsObjects("BookList.csv"));
        aDao.importData(fm.readCSVFileAsObjects("AuthorList.csv"));



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
            fm.writeObjectFileCSV(bDao.exportData(),"BookList.csv",FileManager.BOOK_TABLE_HEADER_ROW);
            req.setAttribute("sentence", "Du hast erfolgreich ein neues Buch von " +
                    StringTrimmer.trim(selectedAuthor.toStringNoID()) +
                    " hinzugefügt. ");
            req.setAttribute("actionURL", "added-book");
            getServletContext().getRequestDispatcher("/add-book.jsp").forward(req, resp);
        }
        StringBuilder displayText = new StringBuilder();

        /*
         * Dieser Fall tritt auf, wenn selectedIndex | name == null sowie titel, isbn & publisher != null
         * d.h. wenn der User zum ersten Mal nach dem POST-request in add-book.jsp weitergeleitet wird.
         */
        if ((titel != null) && (isbn != null) && (publisher != null)) {
            if (titel.isEmpty() || isbn.isEmpty() || publisher.isEmpty()) {
                displayText.append("Die Felder dürfen nicht leer sein. ");
                req.setAttribute("message", displayText.toString());
                doGet(req, resp);
            } else if (bDao.containsIsbn(isbn)) {
                displayText.append("Diese ISBN ist bereits in meiner Bibliothek enthalten. ");
                req.setAttribute("message", displayText.toString());
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
            displayText.append("Ein unerwarteter Fehler ist aufgetreten. ");
            req.setAttribute("message", displayText.toString());
            doGet(req, resp);
        }

    }
}
