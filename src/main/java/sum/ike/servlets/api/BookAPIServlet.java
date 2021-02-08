package sum.ike.servlets.api;

import com.google.gson.Gson;
import sum.ike.control.AuthorDao;
import sum.ike.control.BookDao;
import sum.ike.control.FileManager;
import sum.ike.model.Author;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet
public class BookAPIServlet extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        BookDao bDao = new BookDao();
        FileManager fm = new FileManager();
        bDao.importData(fm.readCSVFileAsObjects("BookList.csv"));

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");


        Gson gson = new Gson();
        response.getWriter().println(gson.toJson(bDao.getAll()));

    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
