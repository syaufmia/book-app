package bk.set;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public class Author implements Serializable {

    //tag::AuthorFields[]
    private static final long serialVersionUID = 2L;
    private int authorID;
    private final String firstName;
    private final String lastName;
    private transient static int authorIDCounter = 1;
    //end::AuthorFields[]

    public Author (String firstName, String lastName) {
        this.authorID = authorIDCounter++;
        this.firstName = firstName.toUpperCase();
        this.lastName = lastName.toUpperCase();
    }

    //synchronize the Counter
    public static void setAuthorIDCounter (int authorIDCounter) {
        Author.authorIDCounter = authorIDCounter;
    }

    //for hard setting the ID (from given list etc)
    public void setAuthorID (int authorID) {
        this.authorID = authorID;
    }

    public int getAuthorID () {
        return authorID;
    }


    public String getFirstName () {
        return firstName;
    }


    public String getLastName () {
        return lastName;
    }


    public String getStringField (Attribute field) {
        switch (field) {
            case FIRST_NAME: return firstName;
            case LAST_NAME: return lastName;
            default: return "";
        }
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
        return getAuthorID() + "," + getFirstName() + "," + getLastName();
    }

    public String toStringNoID() {
        return getFirstName() + "," + getLastName();
    }

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

    public enum Attribute { FIRST_NAME, LAST_NAME, AUTHOR_ID }

    //tag::BuilderClass[]
    public static class Builder {
        private String firstName;
        private String lastName;

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
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

