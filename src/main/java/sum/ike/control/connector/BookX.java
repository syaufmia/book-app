package sum.ike.control.connector;

import sum.ike.model.Author;

import java.io.Serializable;

public class BookX implements Serializable {


    private static final long serialVersionUID = 5L;
    private AuthorX author;
    private String title;
    private String isbn;
    private String publisher;
    private int year;

    public BookX (AuthorX author, String title, String isbn, String publisher, int year) {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
        this.year = year;
    }

    public AuthorX getXAuthor () {
        return author;
    }

    public void setXAuthor (AuthorX author) {
        this.author = author;
    }

    public String getXTitle () {
        return title;
    }

    public void setXTitle (String title) {
        this.title = title;
    }

    public String getXIsbn () {
        return isbn;
    }

    public void setXIsbn (String isbn) {
        this.isbn = isbn;
    }

    public String getXPublisher () {
        return publisher;
    }

    public void setXPublisher (String publisher) {
        this.publisher = publisher;
    }

    public int getXYear () {
        return year;
    }

    public void setXYear (int year) {
        this.year = year;
    }

    @Override
    public String toString () {
        return "Book{" +
                "author=" + author +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publisher='" + publisher + '\'' +
                ", year=" + year +
                '}';
    }
}
