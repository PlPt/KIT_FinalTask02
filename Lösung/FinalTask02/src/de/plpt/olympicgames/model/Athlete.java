package de.plpt.olympicgames.model;

import java.util.*;
import java.util.stream.Collectors;

public class Athlete implements Comparable<Athlete> {

    //region verDef
    private int id;
    private String name;
    private String surname;
    private String country;
    private Map<String, List<String>> sportType = new TreeMap<>();
    private List<Competition> competitions = new ArrayList<>();

    //endregion

    //region constructor
    public Athlete(int id, String name, String surname, String country) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.country = country;
    }
    //endregion

    //region addCompetiton
    public void addCompetition(Competition competition) {
        competitions.add(competition);
    }
    //endregion

    //region getCompetition

    public List<Competition> getCompetitions() {
        return competitions;
    }

    //endregion

    //region getMedalGoldCount
    public long getMedalGoldCount() {
        return competitions.stream().filter(c -> c.getHasGold()).count();
    }
    //endregion

    //region getMedalSilverCount
    public long getMedalSilverCount() {
        return competitions.stream().filter(c -> c.getHasSilver()).count();
    }
    //endregion

    //region getMedalBronzeCount
    public long getMedalBronzeCount() {
        return competitions.stream().filter(c -> c.getHasBronze()).count();
    }
    //endregion

    //region getMedalCount
    public long getMedalCount() {
        return getMedalBronzeCount() + getMedalSilverCount() + getMedalGoldCount();
    }
    //endregion

    //region addSport
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
    public List<String> getDisciplines() {
        List<String> disciplines = new ArrayList<>();

        for (List<String> discs : sportType.values()) {
            disciplines.addAll(discs);
        }

        return disciplines;
    }
    //endregion

    //region getSportTypes
    public List<String> getSportTypes() {
        return sportType.keySet().stream().collect(Collectors.toList());
    }
    //endregion

    //region getCountry
    public String getCountry() {
        return country;
    }
    //endregion

    //region getter
    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }


    public int getId() {
        return id;
    }
    //endregion

    //region equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Athlete athlete = (Athlete) o;
        return id == athlete.id;
    }
    //endregion

    //region hashCode
    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
    //endregion

    //region override toString
    @Override
    public String toString() {
        return String.format("%04d %s %s %s", id, name, surname, getMedalCount());
    }
    //endregion

    //region override compareTo
    @Override
    public int compareTo(Athlete o) {
        long diff1 = getMedalCount() - o.getMedalCount();

        if (diff1 != 0) return Long.compare(getMedalCount(), o.getMedalCount());

        return id - o.getId();
    }
    //endregion
}
