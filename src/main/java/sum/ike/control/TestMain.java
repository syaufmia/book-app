package sum.ike.control;

import com.google.gson.Gson;
import sum.ike.control.connector.AuthorConverter;
import sum.ike.control.connector.BookConverter;
import sum.ike.model.Book;

public class TestMain {


    public static void main(String[] args) {
        BookDao bDao = new BookDao();
        AuthorDao aDao = new AuthorDao();
        Gson gson = new Gson();

        FileManager fm = new FileManager();

        aDao.importData(fm.readCSVFileAsObjects("AuthorList.csv"));
        bDao.importData(fm.readCSVFileAsObjects("BookList.csv"));

        AuthorConverter aConverter = new AuthorConverter();
        BookConverter bConverter = new BookConverter();



        System.out.println(gson.toJson(aConverter.convert(aDao.getAll())));
        System.out.println();
        System.out.println(gson.toJson(aConverter.convert(aDao.searchFor("hdgdl"))));
        System.out.println();
        System.out.println(gson.toJson(bConverter.convert(bDao.getFilteredList("das", Book.Attribute.TITLE))));
        System.out.println();
        System.out.println(gson.toJson(bConverter.convert(bDao.getFilteredList("wur", Book.Attribute.TITLE))));


    }
}
