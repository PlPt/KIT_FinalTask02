package de.plpt.olympicgames.model;

import java.util.Objects;

public class Competition {

    //region varDef
    private int year;
    private boolean hasGold;
    private boolean hasSilver;
    private boolean hasBronze;
    private String kindOfSport;
    private String discipline;
    //endregion

    //region constructor

    public Competition(int year, boolean hasGold, boolean hasSilver, boolean hasBronze, String kindOfSport
            , String discipline) {
        this.year = year;
        this.hasGold = hasGold;
        this.hasSilver = hasSilver;
        this.hasBronze = hasBronze;
        this.kindOfSport = kindOfSport;
        this.discipline = discipline;
    }

    //endregion

    //region getters

    public String getDiscipline() {
        return discipline;
    }


    public String getKindOfSport() {
        return kindOfSport;
    }


    public boolean getHasBronze() {
        return hasBronze;
    }

    public boolean getHasSilver() {
        return hasSilver;
    }

    public boolean getHasGold() {
        return hasGold;
    }

    public int getYear() {
        return year;
    }

    public boolean hasMedal(){
        return hasBronze || hasSilver || hasGold;
    }

    //endregion


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Competition that = (Competition) o;
        return year == that.year &&
                Objects.equals(kindOfSport, that.kindOfSport) &&
                Objects.equals(discipline, that.discipline);
    }

    @Override
    public int hashCode() {

        return Objects.hash(year, kindOfSport, discipline);
    }
}
