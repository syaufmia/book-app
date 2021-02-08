package sum.ike.servlets.api;

import com.google.gson.Gson;
import sum.ike.control.AuthorDao;
import sum.ike.control.BookDao;
import sum.ike.control.FileManager;
import sum.ike.model.Author;
import sum.ike.model.Book;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet
public class SearchAPIServlet extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        FileManager fm = new FileManager();
        AuthorDao aDao = new AuthorDao();
        aDao.importData(fm.readCSVFileAsObjects("AuthorList.csv"));


        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Gson gson = new Gson();

        String uri = request.getRequestURI().substring(1);
        String[] splitURI = uri.split("/");

        switch (splitURI.length) {
            // --[0]/[1]/[2]/[3]/[4]/[5]
            case 6:


                // --/book_manager_war_exploded/api/v1/search/author/{--} [ENG]
                // --/book_manager_war_exploded/api/v1/suche/autor/{--}   [DEU]
                if (splitURI[4].equalsIgnoreCase("author") || splitURI[4].equalsIgnoreCase("autor")) {
                    List<Author> searchList = aDao.searchFor(splitURI[5]);
                    if (!searchList.isEmpty()) {
                        response.getWriter().println(gson.toJson(searchList));
                        response.setStatus(HttpServletResponse.SC_OK);
                    }
                    else {
                        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    }
                }
                // --/book_manager_war_exploded/api/v1/search/{--}/{--} [ENG]
                // --/book_manager_war_exploded/api/v1/suche/{--}/{--}  [DEU]
                else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
                break;

            // --[0]/[1]/[2]/[3]/[4]/[5]/[6]
            case 7:
                BookDao bDao = new BookDao();
                bDao.importData(fm.readCSVFileAsObjects("BookList.csv"));

                if (splitURI[4].equalsIgnoreCase("book") || splitURI[4].equalsIgnoreCase("buch")) {
                    // --/book_manager_war_exploded/api/v1/search/book/title/{--} [ENG]
                    // --/book_manager_war_exploded/api/v1/suche/buch/titel/{--}  [DEU]
                    if (splitURI[5].equalsIgnoreCase("title") || splitURI[5].equalsIgnoreCase("titel")){
                        List<Book> searchList = bDao.getFilteredList(splitURI[6], Book.Attribute.TITLE);
                        if (!searchList.isEmpty()) {
                            response.getWriter().println(gson.toJson(searchList));
                            response.setStatus(HttpServletResponse.SC_OK);
                        }
                        else {
                            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                        }
                    }
                    // --/book_manager_war_exploded/api/v1/search/book/isbn/{--} [ENG]
                    // --/book_manager_war_exploded/api/v1/suche/buch/isbn/{--}  [DEU]
                    else if (splitURI[5].equalsIgnoreCase("isbn"))  {
                        List<Book> searchList = bDao.getFilteredList(splitURI[6], Book.Attribute.ISBN);
                        if (!searchList.isEmpty()) {
                            response.getWriter().println(gson.toJson(searchList));
                            response.setStatus(HttpServletResponse.SC_OK);
                        }
                        else {
                            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                        }
                    }
                    // --/book_manager_war_exploded/api/v1/search/book/publisher/{--} [ENG]
                    // --/book_manager_war_exploded/api/v1/suche/buch/verlag/{--}    [DEU]
                    else if (splitURI[5].equalsIgnoreCase("publisher") || splitURI[5].equalsIgnoreCase("verlag")) {
                        List<Book> searchList = bDao.getFilteredList(splitURI[6], Book.Attribute.PUBLISHER);
                        if (!searchList.isEmpty()) {
                            response.getWriter().println(gson.toJson(searchList));
                            response.setStatus(HttpServletResponse.SC_OK);
                        }
                        else {
                            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                        }
                    }
                    // --/book_manager_war_exploded/api/v1/search/book/author/{--} [ENG]
                    // --/book_manager_war_exploded/api/v1/suche/buch/autor/{--}  [DEU]
                    else if (splitURI[5].equalsIgnoreCase("author")||(splitURI[5].equalsIgnoreCase("autor"))) {
                        List<Book> searchList = bDao.getBookListFromAuthorList(aDao.searchFor(splitURI[6]));
                        if (!searchList.isEmpty()) {
                            response.getWriter().println(gson.toJson(searchList));
                            response.setStatus(HttpServletResponse.SC_OK);
                        }
                        else {
                            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                        }

                    }
                    // --/book_manager_war_exploded/api/v1/search/book/{--}/{--} [ENG]
                    // --/book_manager_war_exploded/api/v1/suche/buch/{--}/{--}  [DEU]
                    else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    }
                }
                break;
            // --[0]/[1]/[2]/[3]/[4]
            // --[0]/[1]/[2]/[3]/[4]/[5]/[6]/[7]
            // --[0]/[1]/[2]/[3]/[4]/[5]/[6]/[7]/[...]
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST); // --> 400 wrong syntax
                break;
        }
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD");

    }
}
