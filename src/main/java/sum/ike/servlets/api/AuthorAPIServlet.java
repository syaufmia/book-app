package sum.ike.servlets.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sum.ike.control.AuthorDao;
import sum.ike.control.BookDao;
import sum.ike.control.FileManager;
import sum.ike.model.Author;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;


@WebServlet
public class AuthorAPIServlet extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //TODO: Map<String,List<Object>> möglich? (Angabe von Gesamtanzahl & andere Infos

        //

        AuthorDao aDao = new AuthorDao();
        FileManager fm = new FileManager();
        aDao.importData(fm.readCSVFileAsObjects("AuthorList.csv"));

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Gson gson = new Gson();


        String uri = request.getRequestURI().substring(1);
        String[] splitURI = uri.split("/");


        switch (splitURI.length) {
            case 4:
                if (!aDao.getAll().isEmpty()) {
                    response.getWriter().println(gson.toJson(aDao.getAll()));
                    response.setStatus(HttpServletResponse.SC_OK);
                }
                else {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
                break;
            case 6:
                if (splitURI[4].equalsIgnoreCase("id")) {
                    if (splitURI[5].matches("\\d++")) {
                        if (aDao.idExists(Integer.parseInt(splitURI[5]))) {
                            List<Author> list = new ArrayList<>();
                            list.add(aDao.getAuthorByID(Integer.parseInt(splitURI[5])));
                            response.getWriter().println(gson.toJson(list));
                            response.setStatus(HttpServletResponse.SC_OK);
                        }
                        //when ID is not in the list --> NO CONTENT
                        else {
                            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                        }
                    }
                    //when not correct format of ID
                    else {
                        response.sendError(HttpServletResponse.SC_NO_CONTENT);
                    }
                }
                else if (splitURI[4].equalsIgnoreCase("lastName")) {
                    response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
                    // by last name???
                }
                else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    //return error code
                }
                break;
            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                // return error code
                break;
        }
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AuthorDao aDao = new AuthorDao();
        FileManager fm = new FileManager();
        aDao.importData(fm.readCSVFileAsObjects("AuthorList.csv"));


        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String uri = request.getRequestURI().substring(1);
        String[] splitURI = uri.split("/");


        if (splitURI.length == 4) {
            String body = getBody(request);
            if (body != null && !(body.isEmpty())) {
                JsonParser parser = new JsonParser();
                JsonObject json = parser.parse(body).getAsJsonObject();
                if (json.has("firstName") && json.has("lastName")) {
                    String firstName = json.get("firstName").getAsString();
                    String lastName = json.get("lastName").getAsString();
                    if (aDao.addNew(firstName, lastName)) {
                        fm.writeObjectFileCSV(aDao.exportData(), "AuthorList.csv", FileManager.AUTHOR_TABLE_HEADER_ROW);
                        response.setStatus(HttpServletResponse.SC_CREATED);
                    } else {
                        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "please add BODY with correct Key-Value pair in JSON");
                }
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "please add BODY in JSON format");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        AuthorDao aDao = new AuthorDao();
        FileManager fm = new FileManager();
        aDao.importData(fm.readCSVFileAsObjects("AuthorList.csv"));


        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String uri = request.getRequestURI().substring(1);
        String[] splitURI = uri.split("/");


        if (splitURI.length == 6) {
            if (splitURI[4].equalsIgnoreCase("id")) {
                if (splitURI[5].matches("\\d+")) {
                    if (aDao.idExists(Integer.parseInt(splitURI[5]))) {
                        String body = getBody(request);
                        if (body != null && !(body.isEmpty())) {
                            JsonParser parser = new JsonParser();
                            JsonObject json = parser.parse(body).getAsJsonObject();
                            if (json.has("firstName") && json.has("lastName")) {
                                String firstName = json.get("firstName").getAsString();
                                String lastName = json.get("lastName").getAsString();
                                if (aDao.changeAuthor(Integer.parseInt(splitURI[5]), firstName, lastName)) {
                                    fm.writeObjectFileCSV(aDao.exportData(),"AuthorList.csv",FileManager.AUTHOR_TABLE_HEADER_ROW);
                                    BookDao bDao = new BookDao();
                                    fm.writeObjectFileCSV(bDao.exportData(),"BookList.csv",FileManager.BOOK_TABLE_HEADER_ROW);
                                    response.setStatus(HttpServletResponse.SC_CREATED);
                                }
                                else {
                                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                                }
                            }
                            else{
                                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "please add BODY with correct Key-Value pair in JSON");
                            }
                        }
                        else{
                            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "please add BODY in JSON format");
                        }
                    }
                    else{
                        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    }
                }
                else{
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "please enter a valid id");
                }
            }
            else{
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        else{
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
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



}
