package sum.ike.servlets.api;

import com.google.gson.Gson;
import sum.ike.control.AuthorDao;
import sum.ike.control.BookDao;
import sum.ike.control.FileManager;
import sum.ike.control.connector.AuthorXDao;

import sum.ike.control.connector.BookXDao;
import sum.ike.model.Book;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet
public class SearchAPIServlet extends HttpServlet {


    FileManager fm = new FileManager();
    AuthorDao aDao = new AuthorDao();
    BookDao bDao = new BookDao();
    BookXDao bXDao = new BookXDao();
    Gson gson = new Gson();
    AuthorXDao aXDao = new AuthorXDao();
    APIHelperServlet helper = new APIHelperServlet();

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        aDao.importData(fm.readCSVFileAsObjects("AuthorList.csv"));
        bDao.importData(fm.readCSVFileAsObjects("BookList.csv"));


        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");


        String[] uri = helper.getSubURI(req);


        switch (uri.length) {
            case 6:
                if (helper.compareSubURITo(req, 4, "author")) {
                    resp.getWriter().println(gson.toJson(aXDao.convertAuthorList(aDao.searchFor(uri[5]))));
                    resp.setStatus(HttpServletResponse.SC_OK);
                }
                else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    getServletContext().getRequestDispatcher("error-page.jsp").forward(req, resp);
                }
                break;

            case 7:
                if (helper.compareSubURITo(req, 4, "book")) {
                    if (helper.compareSubURITo(req, 5, "title", "titel")) {
                        resp.getWriter().println(gson.toJson(bXDao.convertBookList(bDao.getFilteredList(uri[6], Book.Attribute.TITLE))));
                        resp.setStatus(HttpServletResponse.SC_OK);
                    } else if (helper.compareSubURITo(req, 5, "isbn")) {
                        resp.getWriter().println(gson.toJson(bXDao.convertBookList(bDao.getFilteredList(uri[6], Book.Attribute.ISBN))));
                        resp.setStatus(HttpServletResponse.SC_OK);
                    } else if (helper.compareSubURITo(req, 5, "publisher")) {
                        resp.getWriter().println(gson.toJson(bXDao.convertBookList(bDao.getFilteredList(uri[6], Book.Attribute.PUBLISHER))));
                        resp.setStatus(HttpServletResponse.SC_OK);
                    } else if (helper.compareSubURITo(req, 5, "author")) {
                        resp.getWriter().println(gson.toJson(bXDao.convertBookList(bDao.getBookListFromAuthorList(aDao.searchFor(uri[6])))));
                        resp.setStatus(HttpServletResponse.SC_OK);
                    } else {
                        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        getServletContext().getRequestDispatcher("/error-page").forward(req, resp);
                    }
                }
                else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    getServletContext().getRequestDispatcher("/error-page").forward(req, resp);
                }
                break;
            default:
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                getServletContext().getRequestDispatcher("/error-page").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        response.setHeader("Access-Control-Allow-Methods", "GET");

    }
}
