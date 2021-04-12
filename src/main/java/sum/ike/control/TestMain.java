package sum.ike.control;


import sum.ike.control.dao.AuthorDao;
import sum.ike.control.dao.BookDao;
import sum.ike.control.db.DbManager;
import sum.ike.model.Author;
import sum.ike.model.Loan;
import sum.ike.model.User;

public class TestMain {


    public static void main(String[] args){

        User user = new User("safi", "password", "Safiye");

        DbManager dbm = new DbManager();

        dbm.selectAll(DbManager.Table.BOOK);

        BookDao bDao = new BookDao();

        System.out.println(bDao.getAll());

        Loan loan = new Loan(bDao.getBook("978-3-15-000001-4"),user);

        System.out.println(loan.getStartDate());

        System.out.println(loan.getEndDate());

        loan.extendReturnDate(7);

        System.out.println(loan.getEndDate());

        loan.extendReturnDate(1);

        System.out.println(loan.getEndDate());







//        User user = new User();
//        UserDao uDao = new UserDao();
//
//        String username = "syaufmia";
//
//
//        char[] s = new char[username.length()];
//        int code = 0;
//        for (int i = 0; i < username.length(); i++) {
//            s[i] = username.charAt(i);
//            code += (s[i]*Math.pow(3,(username.length()-1)-i));
//        }
//
//        System.out.println(Integer.toHexString(code));
    }

}
