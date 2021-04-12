package sum.ike.control.connector;


import sum.ike.control.dao.BookDao;
import sum.ike.model.Author;

import java.util.ArrayList;
import java.util.List;

public class AuthorConverter {

//    public List<AuthorX> convertX (List<Author> authorList) {
//        List<AuthorX> authorXList = new ArrayList<>();
//        for (Author author : authorList) {
//            authorXList.add(new AuthorX(
//                    author.getAuthorID(),
//                    author.getFirstName(),
//                    author.getLastName()));
//        }
//        return authorXList;
//    }

    protected AuthorX convertForBook (Author author) {
        return new AuthorX(
                author.getAuthorId(),
                author.getFirstName(),
                author.getLastName());
    }

    public List<AuthorY> convert (List<Author> authorList) {
        BookConverter bCon = new BookConverter();
        BookDao bDao = new BookDao();
        List<AuthorY> authorYList = new ArrayList<>();
        for (Author author : authorList) {
            authorYList.add(new AuthorY(
                    author.getAuthorId(),
                    author.getFirstName(),
                    author.getLastName(),
                    bCon.convertForAuthor(bDao.getListOfAuthor(author))));
        }
        return authorYList;
    }

    public AuthorY convert (Author author) {
        BookDao bDao = new BookDao();
        BookConverter bCon = new BookConverter();

        return new AuthorY(
                author.getAuthorId(),
                author.getFirstName(),
                author.getLastName(),
                bCon.convertForAuthor(bDao.getListOfAuthor(author)));
    }

}
