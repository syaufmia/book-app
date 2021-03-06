package sum.ike.control.connector;

import java.io.Serializable;


public class AuthorX implements Serializable {


    private static final long serialVersionUID = 4L;
    private final int id;
    private final String first_name;
    private final String last_name;

    protected AuthorX (int author_id, String first_name, String last_name) {
        this.id = author_id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public int getAuthor_id () {
        return id;
    }

    public String getFirst_name () {
        return first_name;
    }

    public String getLast_name () {
        return last_name;
    }


}
