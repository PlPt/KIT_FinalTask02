package de.plpt.olympicgames.model;

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

    public int getNumberSeats() {
        return numberSeats;
    }

    public int getOpeningYear() {
        return openingYear;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public String getCountryName() {
        return countryName;
    }

    public int getId() {
        return id;
    }
    //endregion

    //region compareTo
    @Override
    public int compareTo(SportVenue o) {
        return numberSeats - o.getNumberSeats();
    }
    //endregion

    //region override toString
    public String toString(int placeNmber) {
        return String.format("(%s %03d %s %s)", placeNmber, id, place, numberSeats);
    }
    //endregion
}
