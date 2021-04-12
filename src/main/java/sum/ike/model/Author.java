package sum.ike.model;

import sum.ike.control.utils.StringTrimmer;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

public class Author implements Serializable {


    //tag::Fields[]
    private static final long serialVersionUID = 2L;
    private int authorId;
    private  String firstName;
    private  String lastName;
    private static int authorIdCounter = 1;
    //end::Fields[]

    //tag::Constructor[]

    /**
     * author with self-generated authorId
     */
    public Author (String firstName, String lastName) {
        this.authorId = authorIdCounter++;
        this.firstName = StringTrimmer.trim(firstName);
        this.lastName = StringTrimmer.trim(lastName);
    }

    /**
     * author with assigned authorId (from db)
     */
    public Author (int authorId, String firstName, String lastName) {
        this.authorId = authorId;
        this.firstName = StringTrimmer.trim(firstName);
        this.lastName = StringTrimmer.trim(lastName);
    }
    //end::Constructor[]

    //tag::setAuthorIDCounter[]
    public static void setAuthorIdCounter (int authorIdCounter) {
        Author.authorIdCounter = authorIdCounter;
    }
    //end::setAuthorIDCounter[]

    //tag::hardSetAuthorID[]
    public void setAuthorId (int authorId) {
        this.authorId = authorId;
    }
    //end::hardSetAuthorID[]

    //tag::normalGetters[]
    public int getAuthorId () {
        return authorId;
    }

    public String getFirstName () {
        return firstName;
    }

    public String getLastName () {
        return lastName;
    }

    //end::normalGetters[]


    public void setFirstName (String firstName) {
        this.firstName = firstName.toUpperCase(Locale.ROOT);
    }

    public void setLastName (String lastName) {
        this.lastName = lastName.toUpperCase(Locale.ROOT);
    }


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
        return getAuthorId() + "," + getFirstName() + "," + getLastName();
    }

    public String toStringNoId () {
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

    public static class AuthorIdComparator implements Comparator<Author> {
        @Override
        public int compare (Author author1, Author author2) {
            return Integer.compare(author1.getAuthorId(), author2.getAuthorId());

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
        private int authorId;

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setAuthorId (int authorId) {
            this.authorId = authorId;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Author build() {
            return new Author(authorId, firstName, lastName);
        }
    }
    //end::BuilderClass[]
}
