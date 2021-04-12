package sum.ike.model;

import java.io.Serializable;

public class Person implements Serializable {

    private static final long serialVersionUID = 4L;

    private int personId;
    private String firstName;
    private String lastName;
    private static int personIdCounter = 1;

    public Person () {}

    public Person (String firstName, String lastName) {
        this.personId = personIdCounter++;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person (int personId, String firstName, String lastName) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getPersonId () {
        return personId;
    }

    public void setPersonId (int personId) {
        this.personId = personId;
    }

    public String getFirstName () {
        return firstName;
    }

    public void setFirstName (String firstName) {
        this.firstName = firstName;
    }

    public String getLastName () {
        return lastName;
    }

    public void setLastName (String lastName) {
        this.lastName = lastName;
    }

    public static void setPersonIdCounter (int personIdCounter) {
        Person.personIdCounter = personIdCounter;
    }
}
