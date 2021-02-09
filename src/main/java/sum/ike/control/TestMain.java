package sum.ike.control;

import com.google.gson.Gson;
import sum.ike.control.connector.AuthorXDao;
import sum.ike.control.connector.BookXDao;
import sum.ike.model.Book;

public class TestMain {


    public static void main(String[] args) {
        BookDao bDao = new BookDao();
        AuthorDao aDao = new AuthorDao();
        Gson gson = new Gson();

        FileManager fm = new FileManager();

        aDao.importData(fm.readCSVFileAsObjects("AuthorList.csv"));
        bDao.importData(fm.readCSVFileAsObjects("BookList.csv"));

        AuthorXDao aXDao = new AuthorXDao();
        BookXDao bXDao = new BookXDao();

        aDao.delete(34);


        System.out.println(aXDao.convertAuthorList(aDao.getAll()));
        System.out.println();
        System.out.println(gson.toJson(bXDao.convertBookList(bDao.getFilteredList("das", Book.Attribute.TITLE))));

        System.out.println(gson.toJson(bXDao.convertBookList(bDao.getFilteredList("wur", Book.Attribute.TITLE))));




    }
}
