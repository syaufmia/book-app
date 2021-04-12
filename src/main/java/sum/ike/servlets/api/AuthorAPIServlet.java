package sum.ike.servlets.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sum.ike.control.dao.AuthorDao;
import sum.ike.control.connector.AuthorConverter;
import sum.ike.control.db.DbManager;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


import java.io.IOException;


@WebServlet
public class AuthorAPIServlet extends HttpServlet {

    AuthorDao aDao = new AuthorDao();
    Gson gson = new Gson();
    AuthorConverter aCon = new AuthorConverter();
    DbManager dbManager = new DbManager();

    APIHelperServlet helper = new APIHelperServlet();

    @Override
    protected void doOptions (HttpServletRequest req, HttpServletResponse resp) {
        setAllowAccessHeader(resp);
    }

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        setAllowAccessHeader(resp);
        setContentTypeHeader(resp);

        dbManager.selectAll(DbManager.Table.AUTHOR);
        dbManager.selectAll(DbManager.Table.BOOK);

        String[] uri = helper.getSubURI(req);
        switch (uri.length) {
            case 4:
                resp.getWriter().println(gson.toJson(aCon.convert(aDao.getAuthorList())));
                resp.setStatus(HttpServletResponse.SC_OK);
                break;
            case 6:
                if (helper.compareSubURITo(req, 4, "id", "author_id")
                        && helper.subURIisInt(req, 5)
                        && (aDao.authorIdExists(Integer.parseInt(uri[5])))) {
                    resp.getWriter().println(gson.toJson(aCon.convert(aDao.getAuthorById(Integer.parseInt(uri[5])))));
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

        setAllowAccessHeader(resp);
        setContentTypeHeader(resp);

        dbManager.selectAll(DbManager.Table.AUTHOR);
        String[] uri = helper.getSubURI(req);
        String body;

        if ((uri.length == 4)
                && ((body = helper.getBody(req)) != null)
                && !body.isEmpty()) {
            JsonParser parser = new JsonParser();
            JsonObject json = parser.parse(body).getAsJsonObject();
            if (json.has("first_name") && json.has("last_name")) {
                String firstName = json.get("first_name").getAsString();
                String lastName = json.get("last_name").getAsString();
                if (!aDao.authorExists(firstName, lastName)) {
                    aDao.addNew(firstName, lastName);
                    dbManager.insertAuthor(aDao.getLastAuthor());
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                } else {
                    resp.sendError(HttpServletResponse.SC_CONFLICT);
                }
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }


    @Override
    protected void doPut (HttpServletRequest req, HttpServletResponse resp) throws IOException {

        setAllowAccessHeader(resp);
        setContentTypeHeader(resp);

        dbManager.selectAll(DbManager.Table.AUTHOR);
        String[] uri = helper.getSubURI(req);
        String body;

        if ((uri.length == 6)
                && (helper.compareSubURITo(req, 4, "id", "author_id")
                && (helper.subURIisInt(req, 5)
                && (aDao.authorIdExists(Integer.parseInt(uri[5])))
                && ((body = helper.getBody(req)) != null)
                && !body.isEmpty()))) {
            int ID = Integer.parseInt(uri[5]);
            JsonParser parser = new JsonParser();
            JsonObject json = parser.parse(body).getAsJsonObject();
            if (json.has("first_name") && json.has("last_name")) {
                String firstName = json.get("first_name").getAsString();
                String lastName = json.get("last_name").getAsString();
                if (aDao.authorExists(firstName, lastName)) {
                    resp.sendError(HttpServletResponse.SC_CONFLICT);
                }
                else {
                    aDao.changeAuthor(ID, firstName, lastName);
                    dbManager.updateAuthor(ID, firstName, lastName);
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            }
            else {
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
            if (helper.compareSubURITo(req, 4, "id", "author_id")
                    && helper.subURIisInt(req, 5)
                    && (aDao.authorIdExists(Integer.parseInt(uri[5])))) {
                int ID = Integer.parseInt(uri[5]);
                dbManager.deleteBook(ID);
                dbManager.deleteAuthor(ID);
                aDao.delete(ID);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void setAllowAccessHeader(HttpServletResponse resp) {
        resp.setHeader("Allow", "OPTIONS, GET, HEAD, POST, DELETE, PUT");
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        resp.setHeader("Access-Control-Allow-Methods", "OPTIONS, GET, HEAD, POST, DELETE, PUT");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    private void setContentTypeHeader(HttpServletResponse resp) {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
    }

}
