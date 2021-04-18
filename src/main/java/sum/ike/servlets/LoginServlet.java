package sum.ike.servlets;

import sum.ike.control.dao.LoanDao;
import sum.ike.control.dao.UserDao;
import sum.ike.control.db.DbManager;
import sum.ike.model.User;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserDao uDao = new UserDao();
        LoanDao lDao = new LoanDao();
        User user = null;
        DbManager dbm = new DbManager();
        dbm.selectAll(DbManager.Table.BOOK);
        dbm.selectAll(DbManager.Table.USER);
        dbm.selectAll(DbManager.Table.LOAN);

        HttpSession httpSession = req.getSession();


        if ((httpSession.getAttribute("user")) != null && (httpSession.getAttribute("user") instanceof User)) {
            user = (User) httpSession.getAttribute("user");
            httpSession.setAttribute("borrowedBooks", lDao.getListOfBorrowedBooksOnDateByUser(user.getUserId(), LocalDate.now()));
            httpSession.setAttribute("loanList", lDao.getLoanListByUserOnDate(user.getUserId(), LocalDate.now()));
        }
        getServletContext().getRequestDispatcher("/login-page.jsp").forward(req, resp);

    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        UserDao uDao = new UserDao();
        LoanDao lDao = new LoanDao();
        User user = null;
        DbManager dbm = new DbManager();
        dbm.selectAll(DbManager.Table.BOOK);
        dbm.selectAll(DbManager.Table.USER);
        dbm.selectAll(DbManager.Table.LOAN);

        HttpSession httpSession = req.getSession();


        if ((httpSession.getAttribute("user")) != null && (httpSession.getAttribute("user") instanceof User)) {
            user = (User) httpSession.getAttribute("user");
        }
        else {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            if (username != null && password != null) {
                if (uDao.userLoginCorrect(username, password)) {
                    user = uDao.getUser(username, password);
                    httpSession.setAttribute("user", user);
                    httpSession.setAttribute("borrowedBooks", lDao.getListOfBorrowedBooksOnDateByUser(user.getUserId(), LocalDate.now()));
                    httpSession.setAttribute("loanList", lDao.getLoanListByUserOnDate(user.getUserId(), LocalDate.now()));
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
