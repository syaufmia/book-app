package sum.ike.control.connector;

import java.io.Serializable;

public class BookY implements Serializable {

    private static final long serialVersionUID = 8L;
    private final String title;
    private final String isbn;
    private final String publisher;
    private final int year;

    protected BookY (String title, String isbn, String publisher, int year) {
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
        this.year = year;
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
