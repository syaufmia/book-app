package sum.ike.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

public class Author implements Serializable {


    //tag::Fields[]
    private static final long serialVersionUID = 2L;
    private int authorID;
    private  String firstName;
    private  String lastName;
    private transient static int authorIDCounter = 1;
    //end::Fields[]

    //tag::Constructor[]
    public Author (String firstName, String lastName) {
        this.authorID = authorIDCounter++;
        this.firstName = firstName.toUpperCase(Locale.ROOT);
        this.lastName = lastName.toUpperCase(Locale.ROOT);
    }
    //end::Constructor[]

    public Author (int authorID, String firstName, String lastName) {
        this.authorID = authorID;
        this.firstName = firstName.toUpperCase(Locale.ROOT);
        this.lastName = lastName.toUpperCase(Locale.ROOT);
    }

    //tag::setAuthorIDCounter[]
    public static void setAuthorIDCounter (int authorIDCounter) {
        Author.authorIDCounter = authorIDCounter;
    }
    //end::setAuthorIDCounter[]

    //tag::hardSetAuthorID[]
    public void setAuthorID (int authorID) {
        this.authorID = authorID;
    }
    //end::hardSetAuthorID[]

    //tag::normalGetters[]
    public int getAuthorID () {
        return authorID;
    }

    public String getFirstName () {
        return firstName;
    }

    public String getLastName () {
        return lastName;
    }

//    public List<Book> getBookList () {
//        return bookList;
//    }
    //end::normalGetters[]


    public void setFirstName (String firstName) {
        this.firstName = firstName.toUpperCase(Locale.ROOT);
    }

    public void setLastName (String lastName) {
        this.lastName = lastName.toUpperCase(Locale.ROOT);
    }

//    public void setBookList(List<Book> bookList) {
//        this.bookList = bookList;
//    }

    @Override
    public int hashCode () { //TODO: maybe change the hashCode to ID?
        return Objects.hash(firstName, lastName);
    }

    @Override
    public boolean equals (Object o) {
        if (o instanceof Author && (((Author) o).getFirstName() != null) && ((Author) o).getLastName() != null) {
            return (((Author) o).getFirstName().equalsIgnoreCase(firstName)) &&
                    (((Author) o).getLastName().equalsIgnoreCase(lastName));
        }
        else return false;
    }


    @Override
    public String toString() {
        return getAuthorID() + "," + getFirstName() + "," + getLastName();
    }

    public String toStringNoID() {
        return getFirstName() + " " + getLastName();
    }

    //tag::AuthorFirstNameComparator[]
    public static class AuthorFirstNameComparator implements Comparator<Author> {

        @Override
        public int compare (Author author1, Author author2) {
            int firstNameCompare = author1.getFirstName().compareToIgnoreCase(author2.getFirstName());
            int lastNameCompare = author1.getLastName().compareToIgnoreCase(author2.getLastName());

            if (firstNameCompare == 0) {
                return lastNameCompare;
            } else {
                return firstNameCompare;
            }
        }
    }
    //end::AuthorFirstNameComparator[]

    public static class AuthorIDComparator implements Comparator<Author> {
        @Override
        public int compare (Author author1, Author author2) {
            return Integer.compare(author1.getAuthorID(), author2.getAuthorID());

        }
    }

    public static class AuthorLastNameComparator implements Comparator<Author>{

        @Override
        public int compare (Author author1, Author author2) {
            int firstNameCompare = author1.getFirstName().compareToIgnoreCase(author2.getFirstName());
            int lastNameCompare = author1.getLastName().compareToIgnoreCase(author2.getLastName());

            if (lastNameCompare == 0) {
                return firstNameCompare;
            } else {
                return lastNameCompare;
            }
        }
    }


    //tag::BuilderClass[]
    public static class Builder {
        private String firstName;
        private String lastName;
        private int authorID;

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setAuthorID(int authorID) {
            this.authorID = authorID;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Author build() {
            return new Author(firstName, lastName);
        }
    }
    //end::BuilderClass[]
}
