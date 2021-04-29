package sum.ike.servlets;

import sum.ike.control.dao.LoanDao;
import sum.ike.control.dao.UserDao;
import sum.ike.control.db.DbManager;
import sum.ike.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet (name = "RegisterUserServlet", value = "/RegisterUserServlet")
public class RegisterUserServlet extends HttpServlet {

    DbManager dbm = new DbManager();
    UserDao uDao = new UserDao();
    LoanDao lDao = new LoanDao();

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/register-user.jsp").forward(req,resp);

    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        callDb();

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String password2 = req.getParameter("password2");
        String email = req.getParameter("email");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");

        if (username != null
                && password != null
                && password2 != null
                && email != null
                && firstName != null
                && lastName != null) {
            if (username.isEmpty()
                    || password.isEmpty()
                    || password2.isEmpty()
                    || email.isEmpty()
                    || firstName.isEmpty()
                    || lastName.isEmpty()) {
                req.setAttribute("message", "Du hast nicht alle Felder ausgefüllt.");
                doGet(req, resp);
            }
            else {
                if (uDao.usernameExists(username)) {
                    req.setAttribute("message", "Dieser Benutzername ist schon vergeben.");
                    doGet(req, resp);
                }
                else {
                    if (!password.equals(password2)) {
                        req.setAttribute("message", "Die Passwörter stimmen nicht überein.");
                        doGet(req, resp);
                    }
                    else {
                        uDao.addUser(username, password, email,firstName, lastName);
                        User user = uDao.getLastUser();
                        dbm.insertUser(user);
                        HttpSession httpSession = req.getSession();
                        httpSession.setAttribute("user", user);
                        httpSession.setAttribute("loanList", lDao.getLoanListByUserOnDate(user.getUserId(), LocalDateTime.now()));
                        getServletContext().getRequestDispatcher("/login/").forward(req, resp);
                    }
                }
            }
        }
        else {
            req.setAttribute("message", "Etwas ist schiefgelaufen. Bitte versuche es erneut.");
        }
    }

    protected void callDb () {
        dbm.selectAll(DbManager.Table.AUTHOR);
        dbm.selectAll(DbManager.Table.BOOK);
        dbm.selectAll(DbManager.Table.USER);
        dbm.selectAll(DbManager.Table.LOAN);
    }
}
