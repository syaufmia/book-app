package sum.ike.control;

import sum.ike.model.Author;
import sum.ike.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookDao {

    private static final List<Book> bl = new ArrayList<>();
    //AuthorDao aDao = new AuthorDao();



    public List<Object> exportData () {
        List <Object> savingList = new ArrayList<>();
        if (!bl.contains(null)) {
            savingList.addAll(bl);
        }
        return savingList;
    }


    public void importData (List<String[]> list) {
        for (String[] s : list) {
            if (s.length == 5) {
                addNew(Integer.parseInt(s[0]), //each index is a column
                        s[2],
                        s[1],
                        s[3],
                        Integer.parseInt(s[4]));
            }
        }
    }
    public int getMaxID () {
        int max = 0;
        for (Book book : bl) {
            if (book.getAuthorID() > max) {
                max = book.getAuthorID();
            }
        } return max;
    }


    public List<Book> getAll () {
        return bl;
    }

    //default "add" method (for within the class)

    public void addNew (Book book) {
        if (!bl.contains(book)) {
            bl.add(book);
        }
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

    public Author getBookAuthor (Book book) {
        AuthorDao aDao = new AuthorDao();
        return (aDao.getAuthorByID(book.getAuthorID()));
    }

    public List<Book> getListOfAuthor (Author author) {
        List<Book> blOfAuthor = new ArrayList<>();
        for (Book book : bl) {
            if (author.getAuthorID() == book.getAuthorID()) {
                blOfAuthor.add(book);
            }
        }return blOfAuthor;
    }

    //"add"method for adding from the csv-read --> meaning: a book, of which we know, that it has an author!!
    public void addNew(int aID,String isbn, String title, String publisher, int year) {
        Book book = new Book.Builder()
                .setIsbn(isbn)
                .setTitle(title)
                .setPublisher(publisher)
                .setYear(year)
                .build();
        book.setAuthorID(aID);
        addNew(book);
        //set the counter to the recent ID from List
    }

    //"add"method for adding new book by user
    public boolean addNew (String firstName, String lastName, String isbn, String title, String publisher, int year) {
        AuthorDao aDao = new AuthorDao();
        Author author = new Author.Builder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .build();
        boolean added = aDao.addNew(author); //add new author to list (if not already exists) -> sets the ID of author.
        Book book = new Book.Builder()
                .setIsbn(isbn)
                .setTitle(title)
                .setPublisher(publisher)
                .setYear(year)
                .build();
        book.setAuthorID(author.getAuthorID()); //sets the ID of Autor to aID in book! same ID
        addNew(book);  //add the book to booklist (including the aID == ID of author)

        return added;
    }

    public void delete (Book book) {
        bl.remove(book);
    }

    public List<Book> getBookListFromAuthorList (List<Author> authorList) {
        List<Book> bookList = new ArrayList<>();
        for (Author author: authorList) {
            bookList.addAll(getListOfAuthor(author));
        }
        return bookList;
    }


    public void delete (int authorID) {
        AuthorDao aDao = new AuthorDao();
        bl.removeAll(getListOfAuthor(aDao.getAuthorByID(authorID)));
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
