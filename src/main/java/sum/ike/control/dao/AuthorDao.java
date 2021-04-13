package sum.ike.control.dao;

import sum.ike.model.Author;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AuthorDao {
    private final static List<Author> al = new ArrayList<>();

    /**
     * gets Data from a DB ResultSet and sets the authorCounter to
     * the highest authorId from Database.
     * Doesn't add author if authorId already in list.
     */
    public void getData(ResultSet result) throws SQLException {
        if (!authorIdExists(result.getInt("author_id"))) {
            addNew(result.getInt("author_id"),
                    result.getString("first_name"),
                    result.getString("last_name")
            );
        }
        setAuthorIdCounterToMax();
    }

    public List<Author> getAuthorList () {
        return al;
    }

    public Author getLastAuthor () {
        return al.get(al.size()-1);
    }


    public Author searchForAndSelect (String name, int selection) {
        List <Author> searchedList = searchFor(name);
        if (!(searchedList.isEmpty()) && (selection <= searchedList.size()) ) {
            return searchedList.get(selection);

        }
        else return null;
    }

    public int getMaxAuthorId () {
        int max = 0;
        for (Author a : al) {
            if (a.getAuthorId() > max) {
                max = a.getAuthorId();
            }
        } return max;
    }

    /**
     * sets the authorIDCounter to the max id + 1 that exists in the list
     */
    public void setAuthorIdCounterToMax () {
        Author.setAuthorIdCounter(getMaxAuthorId()+1);
    }

    /**
     * returns an Author element at a given index from a given autorList
     * or returns NULL when index out of bounds
     */
    public Author select (int index, List<Author> list) {
        if (!(list.isEmpty()) && (index > 0) && (index <= list.size()) ) {
            return list.get(index-1);
        }
        else return null;

    }

    /**
     * checks if author with same firstName and lastName already exists.
     */
    public boolean authorExists (String firstName, String lastName) {
        boolean exists = false;
        for (Author author : al) {
            if (author.getFirstName().equalsIgnoreCase(firstName)) {
                if (author.getLastName().equalsIgnoreCase(lastName)) {
                    exists = true;
                    break;
                }
            }
        }
        return exists;
    }

    /**
     * returns author by given authorId.
     * returns NULL if id doesn't exist
     */
    public Author getAuthorById (int id) {
        Author author = null;
        for (Author a : al) {
            if (id == a.getAuthorId()) {
                author = a;
                break;
            }
        }
        return author;
    }

    /**
     * returns authorId of a combination of firstName and lastName.
     * returns 0 if such person does not exist.
     */
    public int getAuthorId (String firstName, String lastName) {
        int id = 0;
        for (Author author : al) {
            if (author.getFirstName().equalsIgnoreCase(firstName)) {
                if (author.getLastName().equalsIgnoreCase(lastName)) {
                    id = author.getAuthorId();
                    break;
                }
            }
        }
        return id;
    }


    public boolean authorIdExists (int id) {
        boolean exists = false;
        for (Author a: al) {
            if (id == a.getAuthorId()) {
                exists = true;
                break;
            }
        }
        return exists;
    }


    /**
     * adds author from db with existing authorId.
     * used only within the class.
     */
    private void addNew(int id, String firstName, String lastName) {
        Author author = new Author.Builder()
                .setAuthorId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .build();   //id created with counter++
        al.add(author);
    }

    /**
     * adds a new author if author with this name does not exists in list.
     * If already exists, then returns false and does not add author.
     */
    public boolean addNew (String firstName, String lastName) {
        Author author = new Author(firstName, lastName);//id created with counter++
        if (!authorExists(firstName, lastName)) { //if author doesnt exists yet
            al.add(author);// then add author
            return true;
        }
        else return false;
    }


    public void changeAuthor (int id, String firstName, String lastName) {
            getAuthorById(id).setFirstName(firstName);
            getAuthorById(id).setLastName(lastName);
    }

    /**
     * deletes an author by authorId and all books of this author.
     * Does nothing if authorId does not exist.
     */
    public void delete (int authorId) {
        if (authorIdExists(authorId)) {
            BookDao bDao = new BookDao();
            bDao.delete(authorId);
            al.remove(getAuthorById(authorId));
        }
    }

    /**
     * searches for authors by any name. Multiple names separated by space.
     * returns a list of Author, that contain all names (if entered many).
     */
    public List<Author> searchFor (String name) {
        List<Author> searchForList = new ArrayList<>();
        for (String str : name.toUpperCase().split(" ")) {
            for (Author author : al) {
                if (author.getFirstName().toUpperCase(Locale.ROOT).contains(str)
                        || author.getLastName().toUpperCase(Locale.ROOT).contains(str)) {
                    if (!(searchForList.contains(author))) {
                        searchForList.add(author);
                    }
                }
            }
        }
        return searchForList;
    }

}
