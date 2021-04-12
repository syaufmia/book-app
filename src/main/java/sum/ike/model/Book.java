package sum.ike.model;

import sum.ike.control.utils.StringTrimmer;

import java.io.Serializable;
import java.util.Comparator;

public class Book implements Serializable {

    //tag::Fields[]
    //TODO: AUTHOR author
    private int bookId;
    private int authorId; //by default = 0, unless it is inside a List | bookID == authorID when book written of author.
    private static final long serialVersionUID = 1L;
    private final String title;
    private final String isbn;
    private final String publisher;
    private final int publishedYear;
    private static int bookIdCounter = 1;
    //end::Fields[]

    //tag::bookConstructor[]

    /**
     * book with unknown Author (authorId = 0) and self-generated bookId
     */
    public Book (String isbn, String title, String publisher, int publishedYear) {
        this.bookId = bookIdCounter++;
        this.authorId = 0; //when a book is created, the AuthorID is 0 -> until the book is added to the list
        this.isbn = isbn;
        this.title = StringTrimmer.trim(title);
        this.publisher = StringTrimmer.trim(publisher);
        this.publishedYear = publishedYear;
    }

    /**
     * book with known Author and assigned bookId (from db)
     */
    public Book (int bookId, int authorId, String isbn, String title, String publisher, int publishedYear) {
        this.bookId = bookId;
        this.authorId = authorId;
        this.title = StringTrimmer.trim(title);
        this.isbn = isbn;
        this.publisher = StringTrimmer.trim(publisher);
        this.publishedYear = publishedYear;
    }

    /**
     * book with known author and self-generated bookId
     */
    public Book (int authorId, String isbn, String title, String publisher, int publishedYear) {
        this.authorId = authorId;
        this.title = StringTrimmer.trim(title);
        this.isbn = isbn;
        this.publisher = StringTrimmer.trim(publisher);
        this.publishedYear = publishedYear;
    }
    //end::bookConstructor[]

    public static void setBookIdCounter (int bookIdCounter) {
        Book.bookIdCounter = bookIdCounter;
    }

    public int getBookId () {
        return bookId;
    }

    public void setBookId (int bookId) {
        this.bookId = bookId;
    }

    public int getAuthorId () {
        return authorId;
    }

    public void setAuthorId (int authorId) {
        this.authorId = authorId;}

    public String getIsbn () {
        return isbn;
    }


    public String getTitle () {
        return title;
    }


    public String getPublisher () {
        return publisher;
    }


    //tag::getFieldValue[]
    public String getStringField (Attribute field) {
        switch (field) {
            case ISBN: return isbn;
            case TITLE: return title;
            case PUBLISHER: return publisher;
            default: return "";
        }
    }
    public int getIntField (Attribute field) {
        switch (field) {
            case AUTHOR_ID: return authorId;
            case YEAR: return publishedYear;
            default: return 0;
        }
    }
    //end::getFieldValue[]

    public int getPublishedYear () {
        return publishedYear;
    }

    /**
     * a Book equals another Book exactly when the isbn equal the isbn of the other book.
     */
    //tag::equalsMethod[]
    @Override
    public boolean equals(Object o) {
        if (o instanceof Book) {
            return (this.isbn.equalsIgnoreCase(((Book) o).isbn));
        }
        else {
            return false;
        }
    }
    //end::equalsMethod[]

    //tag::toStringMethod[]
    @Override
    public String toString() {
        return bookId + "," + authorId + "," + title + "," + isbn + "," + publisher + "," + publishedYear;
    }

    public String toStringNoID () {
        return title + "," + isbn + "," + publisher + "," + publishedYear;
    }
    //end::toStringMethod[]

    //tag::BookIsbnComparatorClass[]
    public static class BookIsbnComparator implements Comparator<Book> {

        @Override
        public int compare (Book book1, Book book2) {
            int isbnCompare = book1.getIsbn().compareToIgnoreCase(book2.getIsbn());
            int yearCompare = Integer.compare(book1.getPublishedYear(), book2.getPublishedYear());

            if (isbnCompare == 0) {
                return yearCompare;
            } else {
                return isbnCompare;
            }
        }
    }
    //end::BookComparatorClass[]

    //tag::BookTitleComparatorClass[]
    public static class BookTitleComparator implements Comparator<Book> {

        @Override
        public int compare (Book book1, Book book2) {
            int titleCompare = book1.getTitle().compareToIgnoreCase(book2.getTitle());
            int yearCompare = Integer.compare(book1.getPublishedYear(), book2.getPublishedYear());

            if (titleCompare == 0) {
                return yearCompare;
            } else {
                return titleCompare;
            }
        }
    }
    //end::BookTitleComparatorClass[]

    //tag::BookPublisherComparatorClass[]
    public static class BookPublisherComparator implements Comparator<Book> {

        @Override
        public int compare (Book book1, Book book2) {
            int publisherCompare = book1.getPublisher().compareToIgnoreCase(book2.getPublisher());
            int yearCompare = Integer.compare(book1.getPublishedYear(), book2.getPublishedYear());

            if (publisherCompare == 0) {
                return yearCompare;
            } else {
                return publisherCompare;
            }
        }
    }


    //end::BookPublisherComparatorClass[]

    public static class BookIDComparator implements Comparator<Book> {

        @Override
        public int compare (Book book1, Book book2) {
            int IDCompare = Integer.compare(book1.getAuthorId(), book2.getAuthorId());
            int yearCompare = Integer.compare(book1.getPublishedYear(), book2.getPublishedYear());

            if (IDCompare == 0) {
                return yearCompare;
            } else {
                return IDCompare;
            }
        }
    }

    //tag::BookAuthorComparatorClass[]
//    public static class BookAuthorLastNameComparator implements Comparator<Book> {
//
//        @Override
//        public int compare (Book book1, Book book2) {
//            int lastThenFirstNameCompare = new Author.AuthorLastNameComparator().compare(book1.getAuthor(), book2.getAuthor());
//            int yearCompare = Integer.compare(book1.getPublishedYear(), book2.getPublishedYear());
//
//            if (lastThenFirstNameCompare == 0) {
//                return yearCompare;
//            } else {
//                return lastThenFirstNameCompare;
//            }
//        }
//    }
//
//    public static class BookAuthorFirstNameComparator implements Comparator<Book> {
//
//        @Override
//        public int compare (Book book1, Book book2) {
//            int firstThenLastNameCompare = new Author.AuthorFirstNameComparator().compare(book1.getAuthor(), book2.getAuthor());
//            int yearCompare = Integer.compare(book1.getPublishedYear(), book2.getPublishedYear());
//
//            if (firstThenLastNameCompare == 0) {
//                return yearCompare;
//            } else {
//                return firstThenLastNameCompare;
//            }
//        }
//    }
    //end::BookAuthorComparatorClass[]

    //tag::BookFieldEnum[]
    public enum Attribute { ISBN, TITLE, PUBLISHER, AUTHOR_ID, YEAR }
    //end::BookFieldEnum[]

    //tag:BuilderClass[]
    public static class Builder {

        private int bookId;
        private int authorId;
        private String isbn;
        private String title;
        private String publisher;
        private  int publishedYear;

        public Builder () {}

        public Builder setBookId (int bookId) {
            this.bookId = bookId;
            return this;
        }

        public Builder setAuthorId (int authorId) {
            this.authorId = authorId;
            return this;
        }

        public Builder setIsbn (String isbn) {
            this.isbn = isbn;
            return this;
        }

        public Builder setTitle (String title) {
            this.title = title;
            return this;
        }

        public Builder setPublisher (String publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder setYear (int publishedYear) {
            this.publishedYear = publishedYear;
            return this;
        }

        public Book build() {
            return new Book(bookId, authorId, isbn, title, publisher, publishedYear);
        }
    }
    //end::BuilderClass[]

}
