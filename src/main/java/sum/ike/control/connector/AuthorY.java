package sum.ike.control.connector;

import java.io.Serializable;
import java.util.List;

public class AuthorY implements Serializable {


    private static final long serialVersionUID = 6L;
    private final int id;
    private final String first_name;
    private final String last_name;
    private final List<BookY> books;

    protected AuthorY (int author_id, String first_name, String last_name, List<BookY> books) {
        this.id = author_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.books = books;
    }

    public int getId () {
        return id;
    }

    public String getFirst_name () {
        return first_name;
    }

    public String getLast_name () {
        return last_name;
    }

    public List<BookY> getBooks () {
        return books;
    }
}
