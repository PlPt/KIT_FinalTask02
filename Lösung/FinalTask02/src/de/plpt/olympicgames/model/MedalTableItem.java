package de.plpt.olympicgames.model;

import java.util.List;

public class MedalTableItem implements Comparable<MedalTableItem> {


    //region varDef
    private IocMember iocMember;
    private List<Athlete> athletes;

    //endregion

    //region constructor

    /**
     * Instantiates a new MedalTableItem
     *
     * @param iocMember IOC Member of medal
     * @param athletes  List of athletes for IocMember
     */
    public MedalTableItem(IocMember iocMember, List<Athlete> athletes) {
        this.iocMember = iocMember;
        this.athletes = athletes;
    }
    //endregion

    //region compareTo

    /**
     * Compares two MedalTableItems and determnies which of them must be rated higher.
     *
     * @param o medalTableItem object to compare with
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(MedalTableItem o) {

        if (getNumberGoldMedals() - o.getNumberGoldMedals() != 0) {
            return Long.compare(o.getNumberGoldMedals(), getNumberGoldMedals());
        }

        if (getNumberSilverMedals() - o.getNumberSilverMedals() != 0) {
            return Long.compare(o.getNumberSilverMedals(), getNumberSilverMedals());
        }

        if (getNumberBronzeMedals() - o.getNumberBronzeMedals() != 0) {
            return Long.compare(o.getNumberBronzeMedals(), getNumberBronzeMedals());
        }

        return iocMember.getIocId() - o.iocMember.getIocId();
    }
    //endregion

    //region getNumberGoldMedals

    /**
     * Returns number of total gold medals, all athletes won
     *
     * @return number of total gold medals
     */
    private long getNumberGoldMedals() {
        return athletes.stream().mapToLong(a -> a.getMedalGoldCount()).sum();
    }
    //endregion

    //region getNumberSilverMedals

    /**
     * Returns number of total silver medals, all athletes won
     *
     * @return number of total silver medals
     */
    private long getNumberSilverMedals() {
        return athletes.stream().mapToLong(a -> a.getMedalSilverCount()).sum();
    }
    //endregion

    //region getNumberBronzeMedals

    /**
     * Returns number of total bronze medals, all athletes won
     *
     * @return number of total bronze medals
     */
    private long getNumberBronzeMedals() {
        return athletes.stream().mapToLong(a -> a.getMedalBronzeCount()).sum();
    }
    //endregion

    //region getTotalNumberMedals

    /**
     * Returns number of total  medals, all athletes won
     *
     * @return number of total  medals
     */
    private long getTotalNumberMedals() {
        return athletes.stream().mapToLong(a -> a.getMedalCount()).sum();
    }
    //endregion

    //region toString(int place)

    /**
     * Returns a string representation of this MedalTableItem
     *
     * @param place Number of Place
     * @return representation of MedalTableItem
     */
    public String toString(int place) {
        return String.format("(%s,%03d,%s,%s,%s,%s,%s,%s)", place, iocMember.getIocId(), iocMember.getIocCode(),
                iocMember.getCountryName(), getNumberGoldMedals(), getNumberSilverMedals(), getNumberBronzeMedals()
                , getTotalNumberMedals());
    }
    //endregion
}
