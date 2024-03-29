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
import java.time.LocalDateTime;

public class LoginServlet extends HttpServlet {

    DbManager dbm = new DbManager();
    UserDao uDao = new UserDao();
    LoanDao lDao = new LoanDao();


    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        callDb();
        HttpSession httpSession = req.getSession();
        req.setAttribute("dateTimeNow",LocalDateTime.now());

        if ((httpSession.getAttribute("user")) != null && (httpSession.getAttribute("user") instanceof User)) {
            User user = (User) httpSession.getAttribute("user");
            httpSession.setAttribute("loanList", lDao.getLoanListByUserOnDate(user.getUserId(), LocalDateTime.now()));

        }

        getServletContext().getRequestDispatcher("/login-page.jsp").forward(req, resp);
    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        callDb();

        HttpSession httpSession = req.getSession();

        if ((httpSession.getAttribute("user")) != null && (httpSession.getAttribute("user") instanceof User)) {
            String loanIdReturn = req.getParameter("loanIdReturn");
            String loanIdExtend = req.getParameter("loanIdExtend");
            String logout = req.getParameter("logout");

            if (loanIdReturn != null) {
                int loanId = Integer.parseInt(loanIdReturn);
                lDao.returnBook(loanId, LocalDateTime.now());
                if (!lDao.loanIsDelayed(loanId, LocalDateTime.now())) {
                    dbm.updateLoanEndDate(loanId, LocalDateTime.now());
                }
                dbm.updateLoanReturn(loanId, LocalDateTime.now());
            }
            else if (loanIdExtend != null) {
                int loanId = Integer.parseInt(loanIdExtend);
                if (lDao.loanExtendable(loanId, LocalDateTime.now())) {
                    lDao.extendEndDate(loanId, 7);
                    dbm.updateLoanEndDate(loanId, lDao.getLoan(loanId).getEndDate());
                }
            }
            else if (logout != null && logout.equalsIgnoreCase("true")) {
                httpSession.removeAttribute("user");
                httpSession.removeAttribute("loanList");
            }
        }
        else {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            if (username != null && password != null) {
                if (uDao.userLoginCorrect(username, password)) {
                    User user = uDao.getUser(username, password);
                    httpSession.setAttribute("user", user);
                    httpSession.setAttribute("loanList", lDao.getLoanListByUserOnDate(user.getUserId(), LocalDateTime.now()));
                }
                else {
                    req.setAttribute("message", "Benutzername oder Passwort ist falsch.");
                }
            }
        }
        doGet(req,resp);

    }

    protected void callDb () {
        dbm.selectAll(DbManager.Table.AUTHOR);
        dbm.selectAll(DbManager.Table.BOOK);
        dbm.selectAll(DbManager.Table.USER);
        dbm.selectAll(DbManager.Table.LOAN);
    }
}
