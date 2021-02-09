package sum.ike.control;

import sum.ike.model.Author;
import sum.ike.model.Book;

import java.util.ArrayList;
import java.util.List;

public class AuthorDao {
    private static final List<Author> al = new ArrayList<>();



    public List<Object> exportData () {
        List <Object> savingList = new ArrayList<>();
        if (!al.contains(null)) {
            savingList.addAll(al);
        }
        return savingList;
    }

    //importing from a CSV
    public void importData (List<String[]> list) {
        for (String[] element : list) {
            if (element.length == 3) {
                addNew(Integer.parseInt(element[0]), //each index is column
                        element[1],
                        element[2]);
            }
        }
    }


    public List<Author> getAll () {
        return al;
    }


    public Author searchForAndSelect (String name, int selection) {
        List <Author> searchedList = searchFor(name);
        if (!(searchedList.isEmpty()) && (selection <= searchedList.size()) ) {
            return searchedList.get(selection);

        }
        else return null;
    }

    public int getMaxID () {
        int max = 0;
        for (Author a : al) {
            if (a.getAuthorID() > max) {
                max = a.getAuthorID();
            }
        } return max;
    }

    /**
     * sets the authorIDCounter to the Max-ID + 1 that exists in the list
     */
    public void setAuthorCounterToMax () {
        Author.setAuthorIDCounter(getMaxID()+1);
    }

    /**
     *
     * @param index index of the the element, that should be returned
     * @param list any List of Author
     * @return returns an Author element from the list or NULL when idex out of bounds
     */
    public Author select (int index, List<Author> list) {
        if (!(list.isEmpty()) && (index > 0) && (index <= list.size()) ) {
            return list.get(index-1);
        }
        else return null;

    }

    /**
     * Compare a given Author object to all elements ins Authorlist. When found equivalence, set the
     * ID of given Author object to the same ID from element in list.
     *
     * @param author Author object, that is compared to all Author objects inside the AuthorList.
     */
    public boolean setAuthorIDToExistingID (Author author) {
        boolean unchanged = true;
        if (al.contains(author)) {
            for (Author a : al) {
                if (author.equals(a)) {
                    author.setAuthorID(a.getAuthorID());
                    unchanged = false;
                    break;
                }
            }
        } return unchanged;
    }

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
    /*
    //TODO:
            for (int i = 0; i < al.size(); i++) {
            if (!(author.getID() == al.get(i).getID())) {
                al.add(author);
            }
        }
    check other options: we have to iterate through whole list, even though we know that
    there is only ONE or NONE (not many etc). if ONE = don't add. if none = add. but if
    NONE, then why do we have to iterate to begin with? but if we choose al.contains() method,
    we cannot check if ID is contained, but actually check, if ID of author == al.get(i).getID
     */

    public Author getAuthorByID (int ID) {
        Author author = null;
        for (Author a : al) {
            if (ID == a.getAuthorID()) {
                author = a;
                break;
            }
        } return author;
    }

    public boolean idExists (int ID) {
        boolean exists = false;
        for (Author a: al) {
            if (ID == a.getAuthorID()) {
                exists = true;
                break;
            }
        }
        return exists;
    }


    public boolean addNew (Author author) {
        setAuthorIDToExistingID(author);
        if (!al.contains(author)) {
            al.add(author);
            return true;
        }
        else return false;
    }

    //this addNew() is only for reading from CSV!
    public void addNew(int id, String firstName, String lastName) {
        Author author = new Author.Builder()
                .setAuthorID(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .build();   //id created with counter++
        //author.setAuthorID(id);   //id overwritten with hard set from csv
        //addNew(author);
        al.add(author);
    }

    //this addNew() is only for input from User
    public boolean addNew (String firstName, String lastName) {
        Author author = new Author.Builder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .build(); //id created with counter++
        return (setAuthorIDToExistingID(author) & addNew(author));   //change id if in List available. Otherwise keep counter++
    }



    public boolean changeAuthor(int id, String firstName, String lastName) {
        BookDao bDao = new BookDao();
        boolean changed = false;
        if (!authorExists(firstName, lastName)) {
            bDao.delete(id);
            getAuthorByID(id).setFirstName(firstName);
            getAuthorByID(id).setLastName(lastName);
            changed = true;
        }
        return changed;
    }


    public void delete (Author author) {
        BookDao bDao = new BookDao();
        al.remove(author);
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < bDao.getAll().size(); i++) {
            if (author.getAuthorID() == bDao.getAll().get(i).getAuthorID()) {
                books.add(bDao.getAll().get(i));
            }
            for (Book book : books) {
                bDao.delete(book);
            }
        }
    }

    /**
     *
     * @param name String name, or many names (separated by space)
     * @return a filtered list of Author, that contain all entered String in Last name or first name.
     */
    public List<Author> searchFor (String name) {
        List<Author> searchForList = new ArrayList<>();
        for (String str : name.toUpperCase().split(" ")) {
            for (Author author : al) {
                if (author.getFirstName().contains(str) || author.getLastName().contains(str)) {
                    if (!(searchForList.contains(author))) {
                        searchForList.add(author);
                    }
                }
            }
        }
        return searchForList;
    }

    //TODO: ALTERNATIVE
//    public void delete (String input, Author.Attribute field) {
//        al.removeIf(author -> input.equalsIgnoreCase(author.getStringField(field)));
//    }
}
