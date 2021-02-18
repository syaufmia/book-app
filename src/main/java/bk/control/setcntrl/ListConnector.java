package bk.control.setcntrl;

import bk.set.Author;
import bk.set.Book;

import java.util.*;

public class ListConnector {

    private static final Map <Integer, List<Book>> bookMap = new HashMap<>();
    private static final Map <Integer, Author> authorMap = new HashMap<>();


    public void setBookMap () {
        AuthorDao aDao = new AuthorDao();
        BookDao bDao = new BookDao();
        for (Author author : aDao.getAll()) {
            bookMap.put(author.getAuthorID(),bDao.getListOfAuthor(author));
        }
    }

    public Map<Integer, List<Book>> getBookMap () {
        return bookMap;
    }

    public void setAuthorMap () {
        AuthorDao aDao = new AuthorDao();
        for (Author author : aDao.getAll()) {
            authorMap.put(author.getAuthorID(),author);
        }
    }

    public Map<Integer, Author> getAuthorMap () {
        return authorMap;
    }






//    public Map<?, List<Book>> setMapFromList (List<Book> bl, Book.Attribute field) {
//        if (field == Book.Attribute.ISBN) {
//            Map<String, List<Book>> isbnBookMap = new HashMap<>();
//            for (Book book : bl) {
//                if (isbnBookMap.containsKey(book.getIsbn())) {
//                    isbnBookMap.get(book.getIsbn()).add(book);
//                } else {
//                    List<Book> bk = new ArrayList<>();
//                    bk.add(book);
//                    isbnBookMap.put(book.getIsbn(), bk);
//                }
//            }
//            return isbnBookMap;
//        } else if (field == Book.Attribute.PUBLISHER) {
//            Map<String, List<Book>> publisherBookMap = new HashMap<>();
//            for (Book book : bl) {
//                if (publisherBookMap.containsKey(book.getPublisher())) {
//                    publisherBookMap.get(book.getPublisher()).add(book);
//                } else {
//                    List<Book> bk = new ArrayList<>();
//                    bk.add(book);
//                    publisherBookMap.put(book.getPublisher(), bk);
//                }
//            }
//            return publisherBookMap;
////        } else if (field == Book.BookField.AUTHOR) {
////            Map<Author, List<Book>> authorBookMap = new HashMap<>();
////            for (Book book : bl) {
////                if (authorBookMap.containsKey(book.getAuthor())) {
////                    authorBookMap.get(book.getAuthor()).add(book);
////                } else {
////                    List<Book> bk = new ArrayList<>();
////                    bk.add(book);
////                    authorBookMap.put(book.getAuthor(), bk);
////                }
////            }
////            return authorBookMap;
//        } else {
//            return new HashMap<String, List<Book>>();
//        }
//    }

    //TODO: FILTERLIST FROM MAP
}


