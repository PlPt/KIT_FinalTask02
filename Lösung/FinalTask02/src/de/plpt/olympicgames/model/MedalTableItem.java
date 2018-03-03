package de.plpt.olympicgames.model;

import java.util.List;

public class MedalTableItem implements Comparable<MedalTableItem> {


    //region varDef
    private IocCode iocCode;
    private List<Athlete> athletes;

    //endregion

    //region csontructor
    public MedalTableItem(IocCode iocCode, List<Athlete> athletes) {
        this.iocCode = iocCode;
        this.athletes = athletes;
    }
    //endregion

    //region compareTo
    @Override
    public int compareTo(MedalTableItem o) {

        if (getNumberGoldMedals() - o.getNumberGoldMedals() != 0) {
            return Long.compare(getNumberGoldMedals(), o.getNumberGoldMedals());
        }

        if (getNumberSilverMedals() - o.getNumberSilverMedals() != 0) {
            return Long.compare(getNumberSilverMedals(), o.getNumberSilverMedals());
        }

        if (getNumberBronzeMedals() - o.getNumberBronzeMedals() != 0) {
            return Long.compare(getNumberBronzeMedals(), o.getNumberBronzeMedals());
        }

        return iocCode.getIocId() - o.iocCode.getIocId();
    }
    //endregion

    //region getNumberGoldMedals
    private long getNumberGoldMedals() {
        return athletes.stream().mapToLong(a -> a.getMedalGoldCount()).sum();
    }
    //endregion

    //region getNumberSilverMedals
    private long getNumberSilverMedals() {
        return athletes.stream().mapToLong(a -> a.getMedalSilverCount()).sum();
    }
    //endregion

    //region getNumberBronzeMedals
    private long getNumberBronzeMedals() {
        return athletes.stream().mapToLong(a -> a.getMedalBronzeCount()).sum();
    }
    //endregion

    //region getTotalNumberMedals
    private long getTotalNumberMedals() {
        return athletes.stream().mapToLong(a -> a.getMedalCount()).sum();
    }
    //endregion

    //region toString(int place)
    public String toString(int place) {
        return String.format("(%s,%03d,%s,%s,%s,%s,%s,%s)", place, iocCode.getIocId(), iocCode.getIocCode(),
                iocCode.getCountryName(), getNumberGoldMedals(), getNumberSilverMedals(), getNumberBronzeMedals()
                , getTotalNumberMedals());
    }
    //endregion
}
