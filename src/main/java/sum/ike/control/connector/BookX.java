package sum.ike.control.connector;

import java.io.Serializable;

public class BookX implements Serializable {

    private static final long serialVersionUID = 5L;

    private final String title;
    private final String isbn;
    private final AuthorX author;
    private final String publisher;
    private final int year;

    protected BookX (AuthorX author, String title, String isbn, String publisher, int year) {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
        this.year = year;
    }

    public AuthorX getXAuthor () {
        return author;
    }


    public String getXTitle () {
        return title;
    }


    public String getXIsbn () {
        return isbn;
    }


    public String getXPublisher () {
        return publisher;
    }


    public int getXYear () {
        return year;
    }

}
