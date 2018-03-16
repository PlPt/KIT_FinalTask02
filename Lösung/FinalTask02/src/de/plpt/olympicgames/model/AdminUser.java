package de.plpt.olympicgames.model;

import java.util.Objects;

public class AdminUser extends Person {

    //region varDef
    private String userName;
    private String password;
    //endregion

    //region constructor

    /**
     * Instantiates a new AdminUser
     *
     * @param name     Name (first name) of Admin
     * @param surname  Surname (last name) of Admin
     * @param userName UserName of Admin
     * @param password Password to Login
     */
    public AdminUser(String name, String surname, String userName, String password) {
        super(name, surname);
        this.userName = userName;
        this.password = password;
    }
    //endregion

    //region getters

    /**
     * Determines  whether the given password is correct
     *
     * @param password Password to check equality
     * @return true if password is correct
     */
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    /**
     * Returns UserName of AdminUser
     *
     * @return UserName of Admin
     */
    public String getUserName() {
        return userName;
    }

    //endregion

    //region equals

    /**
     * Indicates whether two AdminUsers are equal
     *
     * @param o Object to check equality
     * @return true if the users are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminUser adminUser = (AdminUser) o;
        return Objects.equals(userName, adminUser.userName);
    }
    //endregion

    //region hashCode

    /**
     * Generates an integer hashCode for this Object
     *
     * @return hashCode of Object
     */
    @Override
    public int hashCode() {

        return Objects.hash(userName);
    }
    //endregion
}
