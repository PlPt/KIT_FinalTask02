package de.plpt.olympicgames.model;

import java.util.Objects;

public class AdminUser {

    //region varDef
    private String name;
    private String surname;
    private String userName;
    private String password;
    //endregion

    //region constructor
    public AdminUser(String name, String surname, String userName, String password) {
        this.name = name;
        this.surname = surname;
        this.userName = userName;
        this.password = password;
    }
    //endregion

    //region getters
    public String getPassword() {
        return password;
    }


    public String getUserName() {
        return userName;
    }


    public String getSurname() {
        return surname;
    }


    public String getName() {
        return name;
    }
    //endregion

    //region equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminUser adminUser = (AdminUser) o;
        return Objects.equals(userName, adminUser.userName);
    }
    //endregion

    //region hashCode
    @Override
    public int hashCode() {

        return Objects.hash(userName);
    }
    //endregion
}
