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

    /**
     * Instantiates a new Competition object
     *
     * @param year        Year of competition
     * @param hasGold     Gold medal was won
     * @param hasSilver   Silver medal was won
     * @param hasBronze   Bronze medal was won
     * @param kindOfSport KindOfSport of Competition
     * @param discipline  discipline of competition
     */
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

    /***
     * Returns discipline of Competition
     * @return discipline of competition
     */
    public String getDiscipline() {
        return discipline;
    }


    /**
     * Returns kind of sport of Competition
     *
     * @return kind of sport of competition
     */
    public String getKindOfSport() {
        return kindOfSport;
    }


    /**
     * Indicates whether competition ended in a bronze medal
     *
     * @return true if a bronze medal was won
     */
    public boolean hasBronze() {
        return hasBronze;
    }

    /**
     * Indicates whether competition ended in a silver medal
     *
     * @return true if a silver medal was won
     */
    public boolean hasSilver() {
        return hasSilver;
    }

    /**
     * Indicates whether competition ended in a gold medal
     *
     * @return true if a gold medal was won
     */
    public boolean hasGold() {
        return hasGold;
    }

    /**
     * Returns year of competition
     *
     * @return year of competition
     */
    public int getYear() {
        return year;
    }

    /**
     * Indcates whether a competition results in a medal
     *
     * @return true if any medal was won
     */
    public boolean hasMedal() {
        return hasBronze || hasSilver || hasGold;
    }

    //endregion

    //region equals

    /**
     * Determines whether two Competition objects are equal
     *
     * @param o object to check equality
     * @return true if the given object are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Competition that = (Competition) o;
        return year == that.year
                &&    Objects.equals(kindOfSport, that.kindOfSport)
                &&    Objects.equals(discipline, that.discipline);
    }
    //endregion

    //region hashCode

    /**
     * Generates an integer hasCode of object
     *
     * @return hashCode of object
     */
    @Override
    public int hashCode() {

        return Objects.hash(year, kindOfSport, discipline);
    }
    //endregion
}
