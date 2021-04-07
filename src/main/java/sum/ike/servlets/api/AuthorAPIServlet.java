package sum.ike.servlets.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sum.ike.control.dao.AuthorDao;
import sum.ike.control.dao.BookDao;
import sum.ike.control.utils.FileManager;
import sum.ike.control.connector.AuthorConverter;
import sum.ike.control.db.DbManager;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


import java.io.IOException;


@WebServlet
public class AuthorAPIServlet extends HttpServlet {

    FileManager fm = new FileManager();
    AuthorDao aDao = new AuthorDao();
    BookDao bDao = new BookDao();
    Gson gson = new Gson();
    AuthorConverter aCon = new AuthorConverter();
    DbManager dbManager = new DbManager();

    APIHelperServlet helper = new APIHelperServlet();

    @Override
    protected void doOptions (HttpServletRequest req, HttpServletResponse resp) {
        resp.setHeader("Allow", "OPTIONS, GET, HEAD, POST, DELETE, PUT");
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        resp.setHeader("Access-Control-Allow-Methods", "OPTIONS, GET, POST, DELETE, HEAD, PUT");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



        dbManager.selectAll(DbManager.Table.AUTHOR);
        dbManager.selectAll(DbManager.Table.BOOK);

//        aDao.importData(fm.readCSVFileAsObjects("AuthorList.csv"));
//        bDao.importData(fm.readCSVFileAsObjects("BookList.csv"));

        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        String[] uri = helper.getSubURI(req);
        switch (uri.length) {
            case 4:
                resp.getWriter().println(gson.toJson(aCon.convert(aDao.getAll())));
                resp.setStatus(HttpServletResponse.SC_OK);
                break;
            case 6:
                if (helper.compareSubURITo(req, 4, "id", "author_id")
                        && helper.subURIisInt(req, 5)
                        && (aDao.idExists(Integer.parseInt(uri[5])))) {
                    resp.getWriter().println(gson.toJson(aCon.convert(aDao.getAuthorByID(Integer.parseInt(uri[5])))));
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
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

        dbManager.selectAll(DbManager.Table.AUTHOR);

        //aDao.importData(fm.readCSVFileAsObjects("AuthorList.csv"));
        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        resp.setCharacterEncoding("UTF-8");
//        resp.setContentType("application/json");
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
//                    fm.writeObjectFileCSV(aDao.exportData(), "AuthorList.csv", FileManager.AUTHOR_TABLE_HEADER_ROW);
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

    //TODO: integrate db
    @Override
    protected void doPut (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        dbManager.selectAll(DbManager.Table.AUTHOR);

//        aDao.importData(fm.readCSVFileAsObjects("AuthorList.csv"));
        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        resp.setCharacterEncoding("UTF-8");
//        resp.setContentType("application/json");
        String[] uri = helper.getSubURI(req);
        String body;

        if ((uri.length == 6)
                && (helper.compareSubURITo(req, 4, "id", "author_id")
                && (helper.subURIisInt(req, 5)
                && (aDao.idExists(Integer.parseInt(uri[5])))
                && ((body = helper.getBody(req)) != null)
                && !body.isEmpty()))) {
            int ID = Integer.parseInt(uri[5]);
            JsonParser parser = new JsonParser();
            JsonObject json = parser.parse(body).getAsJsonObject();
            if (json.has("first_name") && json.has("last_name")) {
                String firstName = json.get("first_name").getAsString();
                String lastName = json.get("last_name").getAsString();
                if (aDao.authorExists(firstName, lastName)) {
                    resp.setStatus(HttpServletResponse.SC_CONFLICT);
                    getServletContext().getRequestDispatcher("/error-page.jsp").forward(req, resp);
                }
                else {
                    aDao.changeAuthor(ID, firstName, lastName);
                    dbManager.updateAuthor(ID, firstName, lastName);
//                    fm.writeObjectFileCSV(aDao.exportData(), "AuthorList.csv",FileManager.AUTHOR_TABLE_HEADER_ROW);
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

        dbManager.selectAll(DbManager.Table.AUTHOR);
        dbManager.selectAll(DbManager.Table.BOOK);
//        aDao.importData(fm.readCSVFileAsObjects("AuthorList.csv"));
//        bDao.importData(fm.readCSVFileAsObjects("BookList.csv"));

        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");


        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        String[] uri = helper.getSubURI(req);
        if (uri.length == 6) {
            if (helper.compareSubURITo(req, 4, "id", "author_id")
                    && helper.subURIisInt(req, 5)
                    && (aDao.idExists(Integer.parseInt(uri[5])))) {
                int ID = Integer.parseInt(uri[5]);
                dbManager.deleteBook(ID);
                dbManager.deleteAuthor(ID);
                aDao.delete(ID);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
//                fm.writeObjectFileCSV(aDao.exportData(), "AuthorList.csv",FileManager.AUTHOR_TABLE_HEADER_ROW);
//                fm.writeObjectFileCSV(bDao.exportData(),"BookList.csv", FileManager.BOOK_TABLE_HEADER_ROW);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
