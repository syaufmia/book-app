package sum.ike.control;


import sum.ike.control.dao.BookDao;
import sum.ike.control.dao.LoanDao;
import sum.ike.control.dao.UserDao;
import sum.ike.control.db.DbManager;
import sum.ike.model.Loan;
import sum.ike.model.User;

import java.time.LocalDate;

public class TestMain {


    public static void main(String[] args){

        DbManager dbm = new DbManager();
        UserDao uDao = new UserDao();
        LoanDao lDao = new LoanDao();

        BookDao bDao = new BookDao();
//        dbm.selectAll(DbManager.Table.BOOK);

        dbm.selectAll(DbManager.Table.BOOK);
        dbm.selectAll(DbManager.Table.USER);
        dbm.selectAll(DbManager.Table.LOAN);



        System.out.println(lDao.getListOfBorrowedBooksOnDateByUser(2, LocalDate.now()));
//
        System.out.println(lDao.getLoanListByUser(2));
//        lDao.addLoan(22, 2);
//        dbm.insertLoan(lDao.getLastLoan());
//
//        System.out.println(lDao.getListOfBorrowedBooksOnDate(LocalDate.of(2021,4,12)));
//
//        System.out.println(uDao.getUserList());
//        System.out.println(lDao.getAll());

//
//        DbManager dbm = new DbManager();
//
//        dbm.selectAll(DbManager.Table.BOOK);
//        dbm.selectAll(DbManager.Table.AUTHOR);
//
//        BookDao bDao = new BookDao();
//
//        bDao.addNew("Sarah",
//                "Maas",
//                "978-1-5266-2064-4",
//                "A Court of Silver Flames",
//                "Bloomsbury",
//                2021);

//        System.out.println(bDao.getLastBook());
//
//
//        System.out.println(bDao.getBookList());
//
//        Loan loan = new Loan(bDao.getBook("978-3-15-000001-4"), uDao.getUser("Safi", "1234"));
//
//        System.out.println(loan.getStartDate());
//
//        System.out.println(loan.getEndDate());
//
//        loan.extendReturnDate(7);
//
//        System.out.println(loan.getEndDate());
//
//        loan.extendReturnDate(1);
//
//        System.out.println(loan.getEndDate());
//
//





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
