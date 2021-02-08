package sum.ike.servlets;

import sum.ike.control.UserDao;
import sum.ike.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/login-page.jsp").forward(req, resp);

    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



        UserDao uDao = new UserDao();
        User user = new User();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        HttpSession httpSession = req.getSession();

        if (username.equals(uDao.getDefaultAdmin().getUsername()) && password.equals((uDao.getDefaultAdmin().getPassword()))) {
            req.setAttribute("sentence", "You successfully logged in.");
            httpSession.setAttribute("loggedIn", true);
            getServletContext().getRequestDispatcher("/index.jsp").forward(req,resp);
        }
        else if (uDao.containsUser(username, password)) {
            req.setAttribute("sentence", "You successfully logged in.");
            httpSession.setAttribute("loggedIn", true);
        }
        else {
            httpSession.setAttribute("loggedIn", false);
            req.setAttribute("message", "This username or password does not exist.");

        }
    }
}
