package sum.ike.servlets.api;

import com.google.gson.Gson;
import sum.ike.control.dao.AuthorDao;
import sum.ike.control.dao.BookDao;
import sum.ike.control.connector.AuthorConverter;
import sum.ike.control.connector.BookConverter;
import sum.ike.control.db.DbManager;
import sum.ike.model.Author;
import sum.ike.model.Book;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet
public class SearchAPIServlet extends HttpServlet {

    AuthorDao aDao = new AuthorDao();
    BookDao bDao = new BookDao();
    BookConverter bCon = new BookConverter();
    Gson gson = new Gson();
    AuthorConverter aCon = new AuthorConverter();
    APIHelperServlet helper = new APIHelperServlet();
    DbManager dbManager = new DbManager();

    @Override
    protected void doOptions (HttpServletRequest req, HttpServletResponse resp) {
        setAllowAccessHeader(resp);
    }


    //TODO: integrate db
    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        setAllowAccessHeader(resp);
        setContentTypeHeader(resp);

        dbManager.selectAll(DbManager.Table.AUTHOR);
        dbManager.selectAll(DbManager.Table.BOOK);
        String[] uri = helper.getSubURI(req);


        switch (uri.length) {
            case 6:
                if (helper.compareSubURITo(req, 4, "author")) {
                    resp.getWriter().println(gson.toJson(aCon.convert(aDao.searchFor(uri[5]))));
                    resp.setStatus(HttpServletResponse.SC_OK);
                }
                else if (helper.compareSubURITo(req, 4, "book")) {
                    List<Book> filteredList = new ArrayList<>(bDao.getFilteredList(uri[5], Book.Attribute.TITLE));

                    for (Book b : bDao.getFilteredList(uri[5], Book.Attribute.ISBN)) {
                        if (!filteredList.contains(b)) {
                            filteredList.add(b);
                        }
                    }
                    for (Book b : bDao.getFilteredList(uri[5], Book.Attribute.PUBLISHER)) {
                        if (!filteredList.contains(b)) {
                            filteredList.add(b);
                        }
                    }
                    for (Author a : aDao.searchFor(uri[5])) {
                        for (Book b : bDao.getListOfAuthor(a)) {
                            if (!filteredList.contains(b)) {
                                filteredList.add(b);
                            }
                        }
                    }
                    resp.getWriter().println(gson.toJson(bCon.convert(filteredList)));
                    resp.setStatus(HttpServletResponse.SC_OK);
                }
                else {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
                break;

            case 7:
                if (helper.compareSubURITo(req, 4, "book")) {
                    if (helper.compareSubURITo(req, 5, "title", "titel")) {
                        resp.getWriter().println(gson.toJson(bCon.convert(bDao.getFilteredList(uri[6], Book.Attribute.TITLE))));
                        resp.setStatus(HttpServletResponse.SC_OK);
                    } else if (helper.compareSubURITo(req, 5, "isbn")) {
                        resp.getWriter().println(gson.toJson(bCon.convert(bDao.getFilteredList(uri[6], Book.Attribute.ISBN))));
                        resp.setStatus(HttpServletResponse.SC_OK);
                    } else if (helper.compareSubURITo(req, 5, "publisher")) {
                        resp.getWriter().println(gson.toJson(bCon.convert(bDao.getFilteredList(uri[6], Book.Attribute.PUBLISHER))));
                        resp.setStatus(HttpServletResponse.SC_OK);
                    } else if (helper.compareSubURITo(req, 5, "author")) {
                        resp.getWriter().println(gson.toJson(bCon.convert(bDao.getBookListFromAuthorList(aDao.searchFor(uri[6])))));
                        resp.setStatus(HttpServletResponse.SC_OK);
                    } else {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    }
                }
                else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setAllowAccessHeader(resp);
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    @Override
    protected void doPut (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setAllowAccessHeader(resp);
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    @Override
    protected void doDelete (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setAllowAccessHeader(resp);
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    private void setAllowAccessHeader(HttpServletResponse resp) {
        resp.setHeader("Allow", "OPTIONS, GET, HEAD");
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        resp.setHeader("Access-Control-Allow-Methods", "OPTIONS, GET, HEAD");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    private void setContentTypeHeader(HttpServletResponse resp) {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
    }
}
