package sum.ike.control.connector;


import sum.ike.model.Author;

import java.util.ArrayList;
import java.util.List;

public class AuthorXDao {

    public List<AuthorX> convertAuthorList (List<Author> authorList) {
        List<AuthorX> authorXList = new ArrayList<>();
        for (Author author : authorList) {
            authorXList.add(new AuthorX(
                    author.getAuthorID(),
                    author.getFirstName(),
                    author.getLastName()));
        }
        return authorXList;
    }

    public AuthorX convertAuthor (Author author) {
        return new AuthorX(
                author.getAuthorID(),
                author.getFirstName(),
                author.getLastName());
    }

}
