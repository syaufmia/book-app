package sum.ike.servlets.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sum.ike.control.dao.AuthorDao;
import sum.ike.control.dao.BookDao;
import sum.ike.control.connector.BookConverter;
import sum.ike.control.db.DbManager;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;


@WebServlet
public class BookAPIServlet extends HttpServlet {

    AuthorDao aDao = new AuthorDao();
    BookDao bDao = new BookDao();
    Gson gson = new Gson();
    BookConverter bCon = new BookConverter();
    DbManager dbManager = new DbManager();
    APIHelperServlet helper = new APIHelperServlet();

    @Override
    protected void doOptions (HttpServletRequest req, HttpServletResponse resp) {
        setAllowAccessHeader(resp);
    }

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        setContentTypeHeader(resp);
        setAllowAccessHeader(resp);

        dbManager.selectAll(DbManager.Table.AUTHOR);
        dbManager.selectAll(DbManager.Table.BOOK);

        String[] uri = helper.getSubURI(req);
        switch (uri.length) {
            case 4:
                resp.getWriter().println(gson.toJson(bCon.convert(bDao.getBookList())));
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
                    && (aDao.authorIdExists(Integer.parseInt(uri[6])))) {
                    resp.getWriter().println(gson.toJson(bCon.convert(bDao.getBookListOfAuthor(aDao.getAuthorById(Integer.parseInt(uri[6]))))));
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

        setContentTypeHeader(resp);
        setAllowAccessHeader(resp);

        dbManager.selectAll(DbManager.Table.AUTHOR);
        dbManager.selectAll(DbManager.Table.BOOK);

        String[] uri = helper.getSubURI(req);
        String body;

        if ((uri.length == 4)
                && ((body = helper.getBody(req)) != null)
                && !body.isEmpty()) {
            JsonParser parser = new JsonParser();
            JsonObject json = parser.parse(body).getAsJsonObject();
            if (json.has("title")
                    && json.has("isbn")
                    && json.has("publisher")
                    && json.has("year")
                    && json.get("year").getAsString().matches("\\d++")) {
                if (!bDao.containsIsbn(json.get("isbn").getAsString())) {
                    if (json.has("first_name")
                            && json.has("last_name")) {
                        bDao.addNew(json.get("first_name").getAsString(),
                                json.get("last_name").getAsString(),
                                json.get("isbn").getAsString(),
                                json.get("title").getAsString(),
                                json.get("publisher").getAsString(),
                                Integer.parseInt(json.get("year").getAsString()));
                        dbManager.insertBook(bDao.getLastBook());
                        dbManager.insertAuthor(aDao.getAuthorById(bDao.getLastBook().getAuthor().getAuthorId()));
                        resp.setStatus(HttpServletResponse.SC_CREATED);
                    }
                    else if (json.has("id")
                            && json.get("id").getAsString().matches("\\d++")
                            && aDao.authorIdExists(Integer.parseInt(json.get("id").getAsString()))) {
                        bDao.addNew(Integer.parseInt(json.get("id").getAsString()),
                                json.get("isbn").getAsString(),
                                json.get("title").getAsString(),
                                json.get("publisher").getAsString(),
                                Integer.parseInt(json.get("year").getAsString()));
                        dbManager.insertBook(bDao.getLastBook());
                        resp.setStatus(HttpServletResponse.SC_CREATED);
                    }
                    else {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    }
                }
                else {
                    resp.sendError(HttpServletResponse.SC_CONFLICT);
                }
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete (HttpServletRequest req, HttpServletResponse resp) throws IOException {

        setAllowAccessHeader(resp);

        dbManager.selectAll(DbManager.Table.AUTHOR);
        dbManager.selectAll(DbManager.Table.BOOK);


        String[] uri = helper.getSubURI(req);
        if (uri.length == 6) {
            if (helper.compareSubURITo(req, 4, "isbn")
                    && (bDao.containsIsbn(uri[5]))) {
                bDao.delete(bDao.getBook(uri[5]));
                dbManager.deleteBook(uri[5]);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
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
        setAllowAccessHeader(resp);
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    private void setAllowAccessHeader(HttpServletResponse resp) {
        resp.setHeader("Allow", "OPTIONS, GET, HEAD, POST, DELETE");
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        resp.setHeader("Access-Control-Allow-Methods", "OPTIONS, GET, HEAD, POST, DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    private void setContentTypeHeader(HttpServletResponse resp) {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
    }
}
