package de.plpt.olympicgames.model;

import java.util.Objects;

public class Person {

    //region verDef
    private final String name;
    private final String surname;
    //endregion

    //region constructor

    /**
     * Instantiate a new Person object
     *
     * @param name    First Name of person
     * @param surname LastName of Person
     */
    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
    //endregion

    //region getSurname

    /**
     * Returns Surname (Lastname) of person
     *
     * @return last name of person
     */
    public String getSurname() {
        return surname;
    }
    //endregion

    //region getName

    /**
     * Returns Name (first name) of person
     *
     * @return first name of person
     */
    public String getName() {
        return name;
    }
    //endregion

    //region equals

    /**
     * Indicates wether two person objects are equal
     *
     * @param o Object to check equality
     * @return true if persons are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name)
                && Objects.equals(surname, person.surname);
    }
    //endregion

    //region hashCode

    /**
     * Generates an integer hashCode for the person object
     *
     * @return hashCode of person
     */
    @Override
    public int hashCode() {

        return Objects.hash(name, surname);
    }
    //endregion
}
