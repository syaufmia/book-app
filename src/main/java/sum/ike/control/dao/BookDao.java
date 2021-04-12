package sum.ike.control.dao;

import sum.ike.model.Author;
import sum.ike.model.Book;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookDao {

    private static final List<Book> bl = new ArrayList<>();

    /**
     * gets the Data from a Database ResultSet and sets the bookCounter to the
     * highest bookId from Database.
     * Doesn't add book if bookId already in list.
     */
    public void readDataToList (ResultSet result) throws SQLException {
        if (!bookIdExists(result.getInt("book_id"))) {
            addNew(result.getInt("book_id"),
                    result.getInt("author_id"),
                    result.getString("isbn"),
                    result.getString("title"),
                    result.getString("publisher"),
                    result.getInt("year"));
            setBookCounterToMax();
        }
    }

    public List<Book> getBookList () {
        return bl;
    }

    public int getMaxBookId () {
        int max = 0;
        for (Book b : bl) {
            if (b.getBookId() > max) {
                max = b.getBookId();
            }
        } return max;
    }

    public boolean bookIdExists (int id) {
        boolean exists = false;
        for (Book b: bl) {
            if (id == b.getBookId()) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public void setBookCounterToMax () {
        Book.setBookIdCounter(getMaxBookId()+1);
    }

    public boolean containsIsbn (String isbn) {
        boolean exists = false;
        for (Book book: bl) {
            if (book.getIsbn().equalsIgnoreCase(isbn)) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public Book getBook (String isbn) {
        Book b = null;
        for (Book book : bl) {
            if (book.getIsbn().equalsIgnoreCase(isbn)) {
                b = book;
                break;
            }
        }
        return b;
    }

    public Book getBook (int bookId) {
        Book b = null;
        for (Book book : bl) {
            if (book.getBookId() == bookId) {
                b = book;
                break;
            }
        }
        return b;
    }


    public Book getLastBook () {
        return bl.get(bl.size()-1);
    }

    public Author getBookAuthor (Book book) {
        AuthorDao aDao = new AuthorDao();
        return (aDao.getAuthorById(book.getAuthorId()));
    }

    public List<Book> getBookListOfAuthor (int authorId) {
        List<Book> blOfAuthor = new ArrayList<>();
        for (Book book : bl) {
            if (authorId == book.getAuthorId()) {
                blOfAuthor.add(book);
            }
        } return blOfAuthor;
    }

    public List<Book> getBookListOfAuthor (Author author) {
        List<Book> blOfAuthor = new ArrayList<>();
        for (Book book : bl) {
            if (author.getAuthorId() == book.getAuthorId()) {
                blOfAuthor.add(book);
            }
        }return blOfAuthor;
    }

    /**
     * adds a new book from already existing author with given authorId.
     * Returns false, if ISBN already existed, therefore not added
     */
    public boolean addNew(int authorId, String isbn, String title, String publisher, int year) {
        boolean added = false;
        if (!containsIsbn(isbn)) {
            Book book = new Book.Builder()
                    .setAuthorId(authorId)
                    .setIsbn(isbn)
                    .setTitle(title)
                    .setPublisher(publisher)
                    .setYear(year)
                    .build();
            bl.add(book);
            added = true;
        }
        return added;
    }

    /**
     * adds book from db (existing author and existing bookId).
     * No boolean return value because no check required.
     * Only used within the class.
     */
    private void addNew(int bookId, int authorId, String isbn, String title, String publisher, int year) {
        Book book = new Book.Builder()
                .setBookId(bookId)
                .setAuthorId(authorId)
                .setIsbn(isbn)
                .setTitle(title)
                .setPublisher(publisher)
                .setYear(year)
                .buildWithId();
        bl.add(book);
    }

    /**
     * adds new book with new author. If author existed already,
     * no new author will be added.
     * Returns true if ISBN did not exist yet. Else returns false and does not add book.
     */
    public boolean addNew (String firstName, String lastName, String isbn, String title, String publisher, int year) {
        AuthorDao aDao = new AuthorDao();
        aDao.addNew(firstName, lastName); //checks if author exists, if yes, does not add
        return addNew(aDao.getAuthorId(firstName, lastName),
               isbn,
               title,
               publisher,
               year);
    }

    public void delete (Book book) {
        bl.remove(book);
    }

    public List<Book> getBookListFromAuthorList (List<Author> authorList) {
        List<Book> bookList = new ArrayList<>();
        for (Author author: authorList) {
            bookList.addAll(getBookListOfAuthor(author));
        }
        return bookList;
    }

    public void delete (int authorId) {
        bl.removeAll(getBookListOfAuthor(authorId));
    }

    //TODO: CHECK ALTERNATIVE! (deleting while iterating -- with Iterator??)
    public void delete (String input, Book.Attribute field) {
        bl.removeIf(book -> book.getStringField(field).equalsIgnoreCase(input));
    }

    public List <Book> getFilteredList (String input, Book.Attribute field) {
        List <Book> filteredBookList = new ArrayList<>();
        for (Book book : bl) {
            if (book.getStringField(field).toUpperCase(Locale.ROOT).contains(input.toUpperCase(Locale.ROOT))) {
                filteredBookList.add(book);
            }
        }
        return filteredBookList;
    }

}
