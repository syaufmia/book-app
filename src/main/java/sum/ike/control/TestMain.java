package sum.ike.control;

import sum.ike.control.connector.AuthorXDao;
import sum.ike.control.connector.BookXDao;

public class TestMain {


    public static void main(String[] args) {
        BookDao bDao = new BookDao();
        AuthorDao aDao = new AuthorDao();

        FileManager fm = new FileManager();

        aDao.importData(fm.readCSVFileAsObjects("AuthorList.csv"));
        bDao.importData(fm.readCSVFileAsObjects("BookList.csv"));

        AuthorXDao aXDao = new AuthorXDao();
        BookXDao bXDao = new BookXDao();

        System.out.println(aXDao.convertAuthorList(aDao.getAll()));
        System.out.println();
        System.out.println(bXDao.convertBookList(bDao.getAll()));



    }
}
