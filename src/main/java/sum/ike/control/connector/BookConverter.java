package sum.ike.control.connector;

import sum.ike.control.AuthorDao;
import sum.ike.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookConverter {

    public List<BookX> convert (List<Book> bookList) {
        AuthorDao aDao = new AuthorDao();
        AuthorConverter aCon = new AuthorConverter();
        List<BookX> bookXList = new ArrayList<>();
        for (Book book : bookList) {
            bookXList.add(new BookX(
                    aCon.convertForBook(aDao.getAuthorByID(book.getAuthorID())),
                    book.getTitle(),
                    book.getIsbn(),
                    book.getPublisher(),
                    book.getPublishedYear()));

        }
        return bookXList;
    }
    public BookX convert (Book book){
        AuthorConverter aCon = new AuthorConverter();
        AuthorDao aDao = new AuthorDao();
        return new BookX(aCon.convertForBook(aDao.getAuthorByID(book.getAuthorID())),
                book.getTitle(),
                book.getIsbn(),
                book.getPublisher(),
                book.getPublishedYear());
    }

    protected List<BookY> convertForAuthor (List<Book> bookList) {
        List<BookY> bookYList = new ArrayList<>();
        for (Book book : bookList) {
            bookYList.add(new BookY(
                    book.getTitle(),
                    book.getIsbn(),
                    book.getPublisher(),
                    book.getPublishedYear()));
        }
        return bookYList;
    }

    public BookY convertForAuthor (Book book) {
        return new BookY(
                book.getTitle(),
                book.getIsbn(),
                book.getPublisher(),
                book.getPublishedYear());
    }
}
