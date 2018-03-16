package de.plpt.olympicgames.model;

import java.util.Objects;

public class IocMember implements Comparable<IocMember> {

    //region varDef
    private int iocId;
    private String iocCode;
    private String countryName;
    private int year;
    //endregion

    //region constructor

    /**
     * Instantiates a new IocMember object
     *
     * @param iocId       unique id of Ioc Country
     * @param iocCode     iocCode of country
     * @param countryName Country name
     * @param year        year of appliance at IOC
     */
    public IocMember(int iocId, String iocCode, String countryName, int year) {
        this.iocId = iocId;
        this.iocCode = iocCode;
        this.countryName = countryName;
        this.year = year;
    }
    //endregion

    //region getters

    /**
     * Returns the year of appliance
     *
     * @return year of appliance
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns country name of IocMember
     *
     * @return country name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Returns IocCode of IocMember
     *
     * @return IOC code
     */
    public String getIocCode() {
        return iocCode;
    }

    /**
     * Returns unique IOC ID
     *
     * @return ioc id
     */
    public int getIocId() {
        return iocId;
    }
    //endregion

    //region equals

    /**
     * Determines whether two IocMember objects are ewual
     *
     * @param o Object to check ewuality
     * @return ture if the two IocMembers are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IocMember iocMember = (IocMember) o;
        return iocId == iocMember.iocId
                && Objects.equals(iocCode, iocMember.iocCode);
    }
    //endregion

    //region hashCode

    /**
     * Generates integer hashCode for IocMember object
     *
     * @return hashCode of object
     */
    @Override
    public int hashCode() {

        return Objects.hash(iocId, iocCode);
    }
    //endregion

    //region compareTo

    /**
     * Compares tow IocMember objects and orders by positive or negative integer result
     *
     * @param o IocMember object to compare with
     * @returna negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(IocMember o) {
        int yearDiff = year - o.getYear();

        if (yearDiff != 0) return yearDiff;

        return iocId - o.getIocId();
    }

    //endregion

    //region override toString

    /**
     * Returns a String representation of IOC Code
     *
     * @return string representation of IOC Code
     */
    @Override
    public String toString() {
        return String.format("%s %03d %s %s", year, iocId, iocCode, countryName);
    }
    //endregion
}
