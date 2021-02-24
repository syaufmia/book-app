package sum.ike.servlets.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sum.ike.control.AuthorDao;
import sum.ike.control.BookDao;
import sum.ike.control.FileManager;
import sum.ike.control.connector.BookConverter;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;


@WebServlet
public class BookAPIServlet extends HttpServlet {

    FileManager fm = new FileManager();
    AuthorDao aDao = new AuthorDao();
    BookDao bDao = new BookDao();
    Gson gson = new Gson();
    BookConverter bCon = new BookConverter();

    APIHelperServlet helper = new APIHelperServlet();

    @Override
    protected void doOptions (HttpServletRequest req, HttpServletResponse resp) {
        resp.setHeader("Allow", "OPTIONS, GET, HEAD, POST, DELETE");
    }

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        bDao.importData(fm.readCSVFileAsObjects("BookList.csv"));
        aDao.importData(fm.readCSVFileAsObjects("AuthorList.csv"));

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");

        String[] uri = helper.getSubURI(req);
        switch (uri.length) {
            case 4:
                resp.getWriter().println(gson.toJson(bCon.convert(bDao.getAll())));
                resp.setStatus(HttpServletResponse.SC_OK);
                break;
            case 6:
                if (helper.compareSubURITo(req, 4, "isbn") && bDao.containsIsbn(uri[5])) {
                    resp.getWriter().println(gson.toJson(bCon.convert(bDao.getBook(uri[5]))));
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                break;
            case 7:
                if (helper.compareSubURITo(req, 4, "author")
                    && helper.compareSubURITo(req, 5, "id", "author_id")
                    && helper.subURIisInt(req, 6)
                    && (aDao.idExists(Integer.parseInt(uri[6])))) {
                    resp.getWriter().println(gson.toJson(bCon.convert(bDao.getListOfAuthor(aDao.getAuthorByID(Integer.parseInt(uri[6]))))));
                    resp.setStatus(HttpServletResponse.SC_OK);
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

        aDao.importData(fm.readCSVFileAsObjects("AuthorList.csv"));
        bDao.importData(fm.readCSVFileAsObjects("BookList.csv"));

        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        String[] uri = helper.getSubURI(req);
        String body;


        if ((uri.length == 4)
                && ((body = helper.getBody(req)) != null)
                && !body.isEmpty()) {
            JsonParser parser = new JsonParser();
            JsonObject json = parser.parse(body).getAsJsonObject();
            if (json.has("first_name")
                    && json.has("last_name")
                    && json.has("title")
                    && json.has("isbn")
                    && json.has("publisher")
                    && json.has("year")
                    && json.get("year").getAsString().matches("\\d++")) {
                bDao.addNew(json.get("first_name").getAsString(),
                        json.get("last_name").getAsString(),
                        json.get("isbn").getAsString(),
                        json.get("title").getAsString(),
                        json.get("publisher").getAsString(),
                        Integer.parseInt(json.get("year").getAsString()));
                fm.writeObjectFileCSV(aDao.exportData(), "AuthorList.csv", FileManager.AUTHOR_TABLE_HEADER_ROW);
                fm.writeObjectFileCSV(bDao.exportData(), "BookList.csv", FileManager.BOOK_TABLE_HEADER_ROW);
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }


    @Override
    protected void doDelete (HttpServletRequest req, HttpServletResponse resp) throws IOException {

        aDao.importData(fm.readCSVFileAsObjects("AuthorList.csv"));
        bDao.importData(fm.readCSVFileAsObjects("BookList.csv"));

        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        String[] uri = helper.getSubURI(req);
        if (uri.length == 6) {
            if (helper.compareSubURITo(req, 4, "isbn")
                    && (bDao.containsIsbn(uri[5]))) {
                bDao.delete(bDao.getBook(uri[5]));
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                fm.writeObjectFileCSV(bDao.exportData(),"BookList.csv", FileManager.BOOK_TABLE_HEADER_ROW);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Allow", "OPTIONS, GET, HEAD, POST, DELETE");
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

}
