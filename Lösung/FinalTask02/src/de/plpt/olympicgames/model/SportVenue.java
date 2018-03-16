package de.plpt.olympicgames.model;

import java.util.Objects;

public class SportVenue implements Comparable<SportVenue> {

    //region varDef
    private int id;
    private String countryName;
    private String place;
    private String name;
    private int openingYear;
    private int numberSeats;
    //endregion

    //region constructor

    /**
     * Instantiates a new SportVenue
     *
     * @param id          unique sport venue id
     * @param countryName Country name where venue is located in
     * @param place       Place name where venue is located
     * @param name        Name of venue
     * @param openingYear Venue openning year
     * @param numberSeats Number of seats in venue
     */
    public SportVenue(int id, String countryName, String place, String name, int openingYear, int numberSeats) {
        this.id = id;
        this.countryName = countryName;
        this.place = place;
        this.name = name;
        this.openingYear = openingYear;
        this.numberSeats = numberSeats;
    }
    //endregion

    //region getters

    /**
     * Returns number of seats in venue
     *
     * @return number of seates
     */
    public int getNumberSeats() {
        return numberSeats;
    }

    /**
     * Returns opening year of venue
     *
     * @return opening year
     */
    public int getOpeningYear() {
        return openingYear;
    }

    /**
     * Returns name of Sport venue
     *
     * @return name of sport venue
     */
    public String getName() {
        return name;
    }

    /**
     * Returns place of sport venue
     *
     * @return place of venue
     */
    public String getPlace() {
        return place;
    }

    /**
     * Returns country where venue is located
     *
     * @return country of venue
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Returns unique venue id
     *
     * @return venue id
     */
    public int getId() {
        return id;
    }
    //endregion

    //region compareTo

    /**
     * Compares this SportVenue to another and checks if it must be rated higher or lower
     *
     * @param sportVenue SportVenue to compare with
     * @return negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(SportVenue sportVenue) {
        return numberSeats - sportVenue.getNumberSeats();
    }
    //endregion

    //region equals

    /**
     * Determines whether two SportVenues are equal
     *
     * @param o Object to check equality
     * @return true if given object are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SportVenue that = (SportVenue) o;
        return id == that.id;
    }
    //endregion

    //region hashCode

    /**
     * Generates an integer hashCode for this SportVenue
     *
     * @return hashCode of venue
     */
    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
    //endregion


    //region  toString(placeNumber)

    /**
     * Returns a string representation of Sport Venue in deopendence of placeNumber
     *
     * @param placeNmber Number of palce of venue
     * @return string representation of venue
     */
    public String toString(int placeNmber) {
        return String.format("(%s %03d %s %s)", placeNmber, id, place, numberSeats);
    }
    //endregion
}
