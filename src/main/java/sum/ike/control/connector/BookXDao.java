package sum.ike.control.connector;

import sum.ike.control.AuthorDao;
import sum.ike.control.BookDao;
import sum.ike.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookXDao {

    public List<BookX> convertBookList (List<Book> bookList) {
        AuthorDao aDao = new AuthorDao();
        AuthorXDao aXDao = new AuthorXDao();
        List<BookX> bookXList = new ArrayList<>();
        for (Book book : bookList) {
            bookXList.add(new BookX(
                    aXDao.convertAuthor(aDao.getAuthorByID(book.getAuthorID())),
                    book.getTitle(),
                    book.getIsbn(),
                    book.getPublisher(),
                    book.getPublishedYear()));

//
//                bookXList.add(new BookX(
//                        aXDao.convertAuthor(aDao.getAuthorByID(book.getAuthorID())),
//                        book.getTitle(),
//                        book.getIsbn(),
//                        book.getPublisher(),
//                        book.getPublishedYear()));
//

        }
        return bookXList;
    }
    public BookX convertBook (Book book){
        AuthorXDao aXDao = new AuthorXDao();
        AuthorDao aDao = new AuthorDao();
        return new BookX(aXDao.convertAuthor(aDao.getAuthorByID(book.getAuthorID())),
                book.getTitle(),
                book.getIsbn(),
                book.getPublisher(),
                book.getPublishedYear());
    }


}
