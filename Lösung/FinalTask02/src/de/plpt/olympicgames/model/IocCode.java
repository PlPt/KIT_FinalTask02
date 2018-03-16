package de.plpt.olympicgames.model;

import java.util.Objects;

public class IocCode implements Comparable<IocCode> {

    //region varDef
    private int iocId;
    private String iocCode;
    private String countryName;
    private int year;
    //endregion

    //region constructor
    public IocCode(int iocId, String iocCode, String countryName, int year) {
        this.iocId = iocId;
        this.iocCode = iocCode;
        this.countryName = countryName;
        this.year = year;
    }
    //endregion

    //region getters
    public int getYear() {
        return year;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getIocCode() {
        return iocCode;
    }

    public int getIocId() {
        return iocId;
    }
    //endregion

    //region equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IocCode iocCode1 = (IocCode) o;
        return iocId == iocCode1.iocId
                && Objects.equals(iocCode, iocCode1.iocCode);
    }
    //endregion

    //region hashCode
    @Override
    public int hashCode() {

        return Objects.hash(iocId, iocCode);
    }
    //endregion

    //region compareTo
    @Override
    public int compareTo(IocCode o) {
        int yearDiff = year - o.getYear();

        if(yearDiff != 0 ) return yearDiff;

        return iocId - o.getIocId();
    }

    //endregion

    //region override toString
    @Override
    public String toString() {
        return String.format("%s %03d %s %s", year, iocId, iocCode, countryName);
    }
    //endregion
}
