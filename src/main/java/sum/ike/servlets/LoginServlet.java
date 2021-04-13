package sum.ike.servlets;

import sum.ike.control.dao.UserDao;
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
        HttpSession httpSession = req.getSession();


        if ((httpSession.getAttribute("user")) != null && (httpSession.getAttribute("user") instanceof User)) {
            req.setAttribute("sentence", "Hallo " + user.getFirstName() + " " + user.getLastName() + ".");
        }
        else {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            if (username != null && password != null) {
                if (uDao.userLoginCorrect(username, password)) {
                    httpSession.setAttribute("UID",uDao.getUser(username, password));
                    req.setAttribute("sentence","Du bist jetzt eingeloggt.");
                }
                else {
                    req.setAttribute("message", "Benutzername oder Passwort ist falsch.");
                }
            }
        }
        doGet(req,resp);




    }
}
