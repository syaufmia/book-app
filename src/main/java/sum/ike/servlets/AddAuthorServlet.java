package sum.ike.servlets;

import sum.ike.control.dao.AuthorDao;
import sum.ike.control.dao.BookDao;
import sum.ike.control.db.DbManager;
import sum.ike.control.utils.StringTrimmer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class AddAuthorServlet extends HttpServlet {

    DbManager dbm = new DbManager();
    AuthorDao aDao = new AuthorDao();
    BookDao bDao = new BookDao();

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/add-author.jsp").forward(req, resp);

    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        callDb();
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String titel = req.getParameter("titel");
        String isbn = req.getParameter("isbn");
        String publisher = req.getParameter("publisher");
        String yearStr = req.getParameter("year");

        StringBuilder displayText = new StringBuilder();

        //Buch sollte nie leer sein, sonst Fehler
        if ((titel != null && isbn != null && publisher != null && yearStr != null)) {
            //wenn Autor != d.h. wenn nicht zum ersten mal ein Autor eingetragen wird.
            if (firstName != null && lastName != null) {

                if ((titel.isEmpty()) || (isbn.isEmpty()) || (publisher.isEmpty()) || (yearStr.isEmpty())) {
                    /*
                     * Normales Autor-Hinzufügen (ohne Buch)
                     * d.h. wenn User Autor hinzufügen geklickt hat.
                     *  FirstName & LastName != null und Buch komplett leer ist (aber != null)
                     */
                    if ((titel.isEmpty()) && (isbn.isEmpty()) && (publisher.isEmpty()) && (yearStr.isEmpty())) {
                        if (firstName.isEmpty() || lastName.isEmpty()) {
                            displayText.append("Bitte fülle alle Felder aus. ");
                            req.setAttribute("message", displayText.toString());
                        }
                        else {
                            boolean exists = aDao.authorExists(firstName, lastName);
                            if (!exists) {
                                aDao.addNew(firstName, lastName);
                                dbm.insertAuthor(aDao.getLastAuthor());
                                req.setAttribute("firstName", StringTrimmer.trim(firstName));
                                req.setAttribute("lastName", StringTrimmer.trim(lastName));
                                req.setAttribute("sentence", "wurde erfolgreich hinzugefügt. ");
                            } else {
                                displayText.append("Dieser Author existiert schon in der Bibliothek. ");
                                req.setAttribute("message", displayText.toString());
                            }
                        }
                        req.setAttribute("actionURL", "added-author");
                        doGet(req, resp);
                    }
                    /*
                     * Dies entspricht dem Fall, dass book nur Teilweise empty ist,
                     * Was theoretisch nie passieren kann, da dieser Fall bereits bei AddBookServlet
                     * geprüft wird, aber sicherheitshalber nochmal abgedeckt.
                     */
                    else {
                        displayText.append("Irgendetwas ist schiefgelaufen. Deine Daten konnten nicht gespeichert werden. ");
                        req.setAttribute("message", displayText.toString());
                        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
                    }
                }
                else {
                    /*
                     * Dieser Fall trifft ein, wenn Buch-Angaben gegeben sind, aber Vorname ODER Nachname-Feld
                     * vom User leergelassen wurde. (d.h. wir kommen von Buch-eingeben und haben schon einmal Autor
                     * eingegeben
                     */
                    if (firstName.isEmpty() || lastName.isEmpty()) {
                        displayText.append("Felder dürfen nicht leer sein. ");
                        req.setAttribute("titel", titel);
                        req.setAttribute("isbn", isbn);
                        req.setAttribute("publisher", publisher);
                        req.setAttribute("year", yearStr);
                        req.setAttribute("message", displayText.toString());
                        req.setAttribute("actionURL", "added-author");
                        doGet(req, resp);
                    } else {
                        /*
                         * Dieser Fall trifft genau dann ein, wenn nichts null und nichts leer ist.
                         * Dann wird aus den aus den verfügbaren Daten ein Buch erstellt.
                         */
                        boolean authorExists = aDao.authorExists(firstName, lastName);
                        req.setAttribute("titel", titel);
                        req.setAttribute("isbn", isbn);
                        req.setAttribute("publisher", publisher);
                        req.setAttribute("year", yearStr);
                        req.setAttribute("firstName", firstName);
                        req.setAttribute("lastName", lastName);
                        if (authorExists) {
                            bDao.addNew(aDao.getAuthorId(firstName, lastName), isbn, titel, publisher, Integer.parseInt(yearStr));
                            //Buch aus existierendem Autor
                            dbm.insertBook(bDao.getLastBook());
                            displayText.append("Diesen Autor gab es schon. Dein neues Buch wurde dem vorhandenen Autor zugewiesen. ");
                        }
                        else {
                            bDao.addNew(firstName, lastName, isbn, titel, publisher, Integer.parseInt(yearStr));
                            //Buch aus neuem Autor
                            displayText.append("Du hast erfolgreich ein neues Buch mit einem neuen Autor in meiner Bibliothek gespeichert. ");
                            dbm.insertBook(bDao.getLastBook());
                            dbm.insertAuthor(aDao.getLastAuthor());
                        }
                        req.setAttribute("sentence", displayText.toString());
                        req.setAttribute("actionURL", "added-book");
                        getServletContext().getRequestDispatcher("/add-book.jsp").forward(req, resp);
                    }
                }
            }
         /*
          * Dieser Fall trifft ein, wenn FirstName & LastName == null, Aber alle anderen
          * Informationen (von Buch) =! null && notEmpty sind. D.h. dass der User von "Buch hinzufügen"
          * kommt und bereits Buch-Informationen hat, und jetzt zum ersten Mal Vor-und Nachname eingibt.
          * (Eingabe vom User wird erst nach dem Button Submit geprüft.)
          */
            else {
                if ((!titel.isEmpty()) && (!isbn.isEmpty()) && (!publisher.isEmpty()) && (!yearStr.isEmpty())) {
                    displayText.append("Bitte gib hier den Autor ein, der das Buch geschrieben hat. ");
                    req.setAttribute("sentence", displayText.toString());
                    req.setAttribute("titel", titel);
                    req.setAttribute("isbn", isbn);
                    req.setAttribute("publisher", publisher);
                    req.setAttribute("year", yearStr);
                    req.setAttribute("actionURL", "added-author");
                    doGet(req, resp);
                }
            }
        }
        /*
         * Dieser Fall trifft dann ein, wenn irgendetwas von Buch == null.
         * Eigentlich unmöglich, da input="hidden" immer leere String ausgibt, und nicht null.
         */
        else {
            displayText.append("Irgendetwas ist schiefgelaufen. Deine Daten konnten nicht gespeichert werden. ");
            req.setAttribute("message", displayText.toString());
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);

        }
    }
    public void callDb () {
        dbm.selectAll(DbManager.Table.AUTHOR);
        dbm.selectAll(DbManager.Table.BOOK);
        dbm.selectAll(DbManager.Table.USER);
        dbm.selectAll(DbManager.Table.LOAN);
    }
}
