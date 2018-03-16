package de.plpt.olympicgames.model;

import java.util.*;
import java.util.stream.Collectors;

public class Athlete extends Person implements Comparable<Athlete> {

    //region verDef
    private int id;
    private String name;
    private String surname;
    private String country;
    private Map<String, List<String>> sportType = new TreeMap<>();
    private List<Competition> competitions = new ArrayList<>();

    //endregion

    //region constructor

    /**
     * Instantiates a new Athlete object
     *
     * @param id      Uninque id of Athlete
     * @param name    Name (first name) of Athlete
     * @param surname Surname (last name) of Athlete
     * @param country origin country of Athlete
     */
    public Athlete(int id, String name, String surname, String country) {
        super(name, surname);
        this.id = id;
        this.country = country;
    }
    //endregion

    //region addCompetiton

    /**
     * Adds a new Competition to athlete's competition list
     *
     * @param competition Competition to add
     */
    public void addCompetition(Competition competition) {
        competitions.add(competition);
    }
    //endregion

    //region getCompetition

    /**
     * Returns a List of all competitions entered by Athlete
     *
     * @return List of competitions
     */
    public List<Competition> getCompetitions() {
        return competitions;
    }

    //endregion

    //region getMedalGoldCount

    /**
     * Returns number of all won gold medals
     *
     * @return number of won gold medals
     */
    public long getMedalGoldCount() {
        return competitions.stream().filter(c -> c.hasGold()).count();
    }
    //endregion

    //region getMedalSilverCount

    /**
     * Returns number of won silver medals
     *
     * @return number of silver medals
     */
    public long getMedalSilverCount() {
        return competitions.stream().filter(c -> c.hasSilver()).count();
    }
    //endregion

    //region getMedalBronzeCount

    /**
     * Returns number of won bronze medals
     *
     * @return number of bronze medals
     */
    public long getMedalBronzeCount() {
        return competitions.stream().filter(c -> c.hasBronze()).count();
    }
    //endregion

    //region getMedalCount

    /**
     * Retuns number of total medals won by Athlete
     *
     * @return number of total medals
     */
    public long getMedalCount() {
        return getMedalBronzeCount() + getMedalSilverCount() + getMedalGoldCount();
    }
    //endregion

    //region addSport

    /**
     * Attaches Athletes to a sport discipline
     *
     * @param kindOfSport KindOfSport perfomed by Athlete
     * @param discipline  Sport discipline performed by Athlete
     * @return ture if attach to sport type was successful
     */
    public boolean addSport(String kindOfSport, String discipline) {

        if (!sportType.containsKey(kindOfSport)) {
            sportType.put(kindOfSport, new ArrayList<>());
        }

        List<String> diciplines = sportType.get(kindOfSport);

        if (!diciplines.contains(discipline)) {
            diciplines.add(discipline);
            Collections.sort(diciplines);
        } else {
            return false;
        }


        return true;
    }
    //endregion

    //region getDisciplines

    /**
     * Returns a list of attached disciplines
     *
     * @return List of disciplines
     */
    public List<String> getDisciplines() {
        List<String> disciplines = new ArrayList<>();

        for (List<String> discs : sportType.values()) {
            disciplines.addAll(discs);
        }

        return disciplines;
    }
    //endregion

    //region getSportTypes

    /**
     * Returns a List of sport types attaches to athlete
     *
     * @return List of Sport type
     */
    public List<String> getSportTypes() {
        return sportType.keySet().stream().collect(Collectors.toList());
    }
    //endregion

    //region getCountry

    /**
     * Returns country of origin of Athlete
     *
     * @return country of origin
     */
    public String getCountry() {
        return country;
    }
    //endregion

    //region getter

    /**
     * Returns unique Athlete Id
     * @return id of athlete
     */
    public int getId() {
        return id;
    }
    //endregion

    //region equals

    /**
     * Indicates whether two Athletes are eqal
     *
     * @param o Object to check equality
     * @return true if the given Athletes are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Athlete athlete = (Athlete) o;
        return id == athlete.id;
    }
    //endregion

    //region hashCode

    /**
     * Generates integer hashCode for Athlete
     *
     * @return hashCode of Athlete
     */
    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
    //endregion

    //region override toString

    /**
     * Retuns a string representation of Athlete
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("%04d %s %s %s", id, name, surname, getMedalCount());
    }
    //endregion

    //region override compareTo

    /**
     * Compares an Athlete object to another
     *
     * @param a Athlete to compare
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Athlete a) {
        long diff1 = getMedalCount() - a.getMedalCount();

        if (diff1 != 0) return Long.compare(getMedalCount(), a.getMedalCount());

        return id - a.getId();
    }
    //endregion
}
