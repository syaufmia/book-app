package bk.control.setcntrl;

import bk.control.Dao;
import bk.set.Author;
import bk.set.Book;

import java.util.ArrayList;
import java.util.List;

public class BookDao implements Dao<Book> {

    private static final List<Book> bl = new ArrayList<>();
    //AuthorDao aDao = new AuthorDao();


    @Override
    public List<Object> exportData () {
        List <Object> savingList = new ArrayList<>();
        if (!bl.contains(null)) {
            savingList.addAll(bl);
        }
        return savingList;
    }


    public void importData (List<String[]> list) {
        for (String[] element : list) {
            if (element.length == 5) {
                addNew(Integer.parseInt(element[0]), //each index is a column
                        element[2],
                        element[1],
                        element[3],
                        Integer.parseInt(element[4]));
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

    @Override
    public List<Book> getAll () {
        return bl;
    }

    //default "add" method (for within the class)
    @Override
    public void addNew (Book book) {
        if (!bl.contains(book)) {
            bl.add(book);
        }
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
    public void addNew (String firstName, String lastName, String isbn, String title, String publisher, int year) {
        AuthorDao aDao = new AuthorDao();
        Author author = new Author.Builder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .build();
        aDao.addNew(author); //add new author to list (if not already exists) -> sets the ID of author.
        Book book = new Book.Builder()
                .setIsbn(isbn)
                .setTitle(title)
                .setPublisher(publisher)
                .setYear(year)
                .build();
        book.setAuthorID(author.getAuthorID()); //sets the ID of Autor to aID in book! same ID
        addNew(book); //add the book to booklist (including the aID == ID of author)
    }

    @Override
    public void delete (Book book) {
        bl.remove(book);

    }

    //TODO: CHECK ALTERNATIVE! (deleting while iterating -- with Iterator??)
    public void delete (String input, Book.Attribute field) {
        bl.removeIf(book -> book.getStringField(field).equalsIgnoreCase(input));
    }

    public List <Book> getFilteredList (String input, Book.Attribute field) {
        List <Book> filteredBookList = new ArrayList<>();
        for (Book book : bl) {
            if (book.getStringField(field).equalsIgnoreCase(input)) {
                filteredBookList.add(book);
            }
        }
        return filteredBookList;
    }

}
