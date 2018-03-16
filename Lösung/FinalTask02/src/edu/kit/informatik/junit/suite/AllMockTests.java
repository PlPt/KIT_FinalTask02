package edu.kit.informatik.junit.suite;

import edu.kit.informatik.junit.mock.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Lucas Alber
 * @version 1.0
 */
//@RunWith(Suite.class)
@Suite.SuiteClasses({
    AddAthleteTest.class,
    AddCompetitionTest.class,
    AddIOCCodeTest.class,
    AddOlympicSportTest.class,
    AddSportsVenueTest.class,
    AdminTest.class,
    ListIOCCodesTest.class,
    ListOlympicSportsTest.class,
    ListSportsVenuesTest.class,
    MedalTableTest.class,
    ResetTest.class,
    SummaryAthletesTest.class
})
public class AllMockTests {

}
