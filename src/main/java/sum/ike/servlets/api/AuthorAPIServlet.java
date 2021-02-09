package sum.ike.servlets.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sum.ike.control.AuthorDao;
import sum.ike.control.BookDao;
import sum.ike.control.FileManager;
import sum.ike.control.connector.AuthorXDao;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;


@WebServlet
public class AuthorAPIServlet extends HttpServlet {


    FileManager fm = new FileManager();
    AuthorDao aDao = new AuthorDao();
    BookDao bDao = new BookDao();
    Gson gson = new Gson();
    AuthorXDao aXDao = new AuthorXDao();

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        aDao.importData(fm.readCSVFileAsObjects("AuthorList.csv"));

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        String[] uri = getSubURI(req);
        switch (uri.length) {
            case 4:
                resp.getWriter().println(gson.toJson(aXDao.convertAuthorList(aDao.getAll())));
                resp.setStatus(HttpServletResponse.SC_OK);
                break;
            case 6:
                if (compareSubURITo(req, 4, "id", "author_id")
                        && subURIisInt(req, 5)
                        && (aDao.idExists(Integer.parseInt(uri[5])))) {
                    resp.getWriter().println(gson.toJson(aXDao.convertAuthor(aDao.getAuthorByID(Integer.parseInt(uri[5])))));
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    getServletContext().getRequestDispatcher("/error-page.jsp").forward(req, resp);
                }
                break;
            default:
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                getServletContext().getRequestDispatcher("/error-page.jsp").forward(req, resp);
                break;
        }
    }


    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        aDao.importData(fm.readCSVFileAsObjects("AuthorList.csv"));
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        String[] uri = getSubURI(req);
        String body;


        if ((uri.length == 4)
                && ((body = getBody(req)) != null)
                && !body.isEmpty()) {
            JsonParser parser = new JsonParser();
            JsonObject json = parser.parse(body).getAsJsonObject();
            if (json.has("first_name") && json.has("last_name")) {
                String firstName = json.get("first_name").getAsString();
                String lastName = json.get("last_name").getAsString();
                if (!aDao.authorExists(firstName, lastName)) {
                    aDao.addNew(firstName, lastName);
                    fm.writeObjectFileCSV(aDao.exportData(), "AuthorList.csv", FileManager.AUTHOR_TABLE_HEADER_ROW);
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                } else {
                    resp.setStatus(HttpServletResponse.SC_CONFLICT);
                    getServletContext().getRequestDispatcher("/error-page.jsp").forward(req, resp);
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                getServletContext().getRequestDispatcher("/error-page.jsp").forward(req, resp);
            }
        }
        else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            getServletContext().getRequestDispatcher("/error-page.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPut (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        aDao.importData(fm.readCSVFileAsObjects("AuthorList.csv"));
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        String[] uri = getSubURI(req);
        String body;

        if ((uri.length == 6)
                && (compareSubURITo(req, 4, "id", "author_id")
                && (subURIisInt(req, 5)
                && (aDao.idExists(Integer.parseInt(uri[5])))
                && ((body = getBody(req)) != null)
                && !body.isEmpty()))) {
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
                    aDao.changeAuthor(Integer.parseInt(uri[5]), firstName, lastName);
                    fm.writeObjectFileCSV(aDao.exportData(), "AuthorList.csv",FileManager.AUTHOR_TABLE_HEADER_ROW);
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                }
            }
            else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                getServletContext().getRequestDispatcher("/error-page.jsp").forward(req, resp);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            getServletContext().getRequestDispatcher("/error-page.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doDelete (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        aDao.importData(fm.readCSVFileAsObjects("AuthorList.csv"));
        bDao.importData(fm.readCSVFileAsObjects("BookList.csv"));


        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        String[] uri = getSubURI(req);
        if (uri.length == 6) {
            if (compareSubURITo(req, 4, "id", "author_id")
                    && subURIisInt(req, 5)
                    && (aDao.idExists(Integer.parseInt(uri[5])))) {
                aDao.delete(Integer.parseInt(uri[5]));
                resp.setStatus(HttpServletResponse.SC_OK);
                fm.writeObjectFileCSV(aDao.exportData(), "AuthorList.csv",FileManager.AUTHOR_TABLE_HEADER_ROW);
                fm.writeObjectFileCSV(bDao.exportData(),"BookList.csv", FileManager.BOOK_TABLE_HEADER_ROW);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                getServletContext().getRequestDispatcher("/error-page.jsp").forward(req, resp);
            }
        }
        else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            getServletContext().getRequestDispatcher("/error-page.jsp").forward(req, resp);
        }

    }

    public String getBody (HttpServletRequest request) throws IOException {
       StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    public String[] getSubURI (HttpServletRequest req) {
        return (req.getRequestURI().substring(1)).split("/");
    }

    public boolean compareSubURITo (HttpServletRequest req, int index, String check, String check2) {
        String[] uri = getSubURI(req);
        return uri[index].equalsIgnoreCase(check) || uri[index].equalsIgnoreCase(check2);
    }
    public boolean subURIisInt (HttpServletRequest req, int index) {
        return getSubURI(req)[index].matches("\\d++");
    }
}
