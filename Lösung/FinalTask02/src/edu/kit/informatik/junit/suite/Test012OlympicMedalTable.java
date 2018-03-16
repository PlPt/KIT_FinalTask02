package edu.kit.informatik.junit.suite;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.junit.Wrapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static edu.kit.informatik.junit.TestHelper.expectError;
import static edu.kit.informatik.junit.TestHelper.expectOK;

/**
 * Tests olympic-medal-command
 */
public class Test012OlympicMedalTable {
    /**
     *
     * Here you have to set which parameters you want to use for your test
     */
    public static String[] testArgs = {}; // TODO Set arguments to test

    /**
     * Executes before ALL tests
     */
    @BeforeAll
    public static void enableTerminalTestingMode() {
        Terminal.enableTestingMode();
    }

    /**
     * Executes before EVERY test
     */
    @BeforeEach
    public void reset() {
        Terminal.reset();

    }

    /**
     * Executes after EVERY test. All @AfterEach methods are
     * guaranteed to run even if a Before or Test method
     * throws an exception. The @AfterEach methods declared
     * in superclasses will be run after those of the current class.
     */
    @AfterEach
    public void runTests() {

        // Run and terminate
        Terminal.addNoOutput("quit");
        Wrapper.main(testArgs);

        // Tell the Terminal to check the outputs and to cleanup
        Terminal.flush();
        Terminal.reset();
    }

    @Test
    public void addAdmin() {
        expectOK("add-admin Harry;Potter;harryp;12345678");
    }

    @Test
    public void addIocCode() {
        addAdmin();
        expectOK("login-admin harryp;12345678");

        expectOK("add-ioc-code 110;bul;bulgaria;1896");
        expectOK("add-ioc-code 111;arg;argentina;1920");
        expectOK("add-ioc-code 112;bhu;bhutan;1914");
        expectOK("add-ioc-code 113;ger;germany;1890");
        expectOK("add-ioc-code 114;can;canada;1870");
        expectOK("add-ioc-code 115;chn;china;1860");
        expectOK("add-ioc-code 116;fra;france;1910");
        expectOK("add-ioc-code 117;usa;usa;1903");
        expectOK("add-ioc-code 118;rus;russia;1840");
        expectOK("add-ioc-code 119;aut;austria;1901");
    }

    @Test
    public void addSport() {
        addIocCode();
        expectOK("add-olympic-sport biathlon;biathlon");
        expectOK("add-olympic-sport hockey;icehockey");
        expectOK("add-olympic-sport iceskating;shorttrack");
        expectOK("add-olympic-sport curling;curling");
        expectOK("add-olympic-sport iceskating;speedskating");
        expectOK("add-olympic-sport bobsport;bobsleigh");
        expectOK("add-olympic-sport snowboard;slalom");
        expectOK("add-olympic-sport ski jumping;individual");
        expectOK("add-olympic-sport ski jumping;team");

    }

    @Test
    public void addAthlete() {
        addSport();
        expectOK("add-athlete 0001;a;a;argentina;biathlon;biathlon");
        expectOK("add-athlete 0002;b;b;argentina;biathlon;biathlon");
        expectOK("add-athlete 0003;c;c;argentina;hockey;icehockey");
        expectOK("add-athlete 0004;d;d;argentina;iceskating;shorttrack");
        expectOK("add-athlete 0005;e;e;argentina;curling;curling");
        expectOK("add-athlete 0005;e;e;argentina;iceskating;speedskating");

        expectOK("add-athlete 0006;a;a;bhutan;hockey;icehockey");
        expectOK("add-athlete 0007;a;a;bhutan;ski jumping;individual");
        expectOK("add-athlete 0008;a;a;bhutan;hockey;icehockey");
        expectOK("add-athlete 0009;a;a;bhutan;bobsport;bobsleigh");
        expectOK("add-athlete 0010;a;a;bhutan;ski jumping;individual");

        expectOK("add-athlete 0011;b;b;germany;hockey;icehockey");
        expectOK("add-athlete 0011;b;b;germany;biathlon;biathlon");
        expectOK("add-athlete 0011;b;b;germany;iceskating;shorttrack");
        expectOK("add-athlete 0012;b;b;germany;bobsport;bobsleigh");
        expectOK("add-athlete 0013;b;b;germany;curling;curling");
        expectOK("add-athlete 0014;b;b;germany;hockey;icehockey");

        expectOK("add-athlete 0015;c;c;canada;hockey;icehockey");
        expectOK("add-athlete 0016;c;c;canada;biathlon;biathlon");
        expectOK("add-athlete 0017;c;c;canada;iceskating;shorttrack");
        expectOK("add-athlete 0018;c;c;canada;bobsport;bobsleigh");
        expectOK("add-athlete 0019;c;c;canada;curling;curling");
        expectOK("add-athlete 0020;c;c;canada;hockey;icehockey");

        expectOK("add-athlete 0021;d;d;china;ski jumping;team");
        expectOK("add-athlete 0022;d;d;china;ski jumping;team");
        expectOK("add-athlete 0023;d;d;china;ski jumping;team");
        expectOK("add-athlete 0024;d;d;china;ski jumping;team");
        expectOK("add-athlete 0025;d;d;china;ski jumping;team");
        expectOK("add-athlete 0026;d;d;china;ski jumping;team");

        expectOK("add-athlete 0027;e;e;france;ski jumping;team");
        expectOK("add-athlete 0027;e;e;france;curling;curling");
        expectOK("add-athlete 0027;e;e;france;bobsport;bobsleigh");
        expectOK("add-athlete 0027;e;e;france;snowboard;slalom");
        expectOK("add-athlete 0027;e;e;france;iceskating;speedskating");
        expectOK("add-athlete 0027;e;e;france;biathlon;biathlon");

        expectOK("add-athlete 0028;f;f;usa;snowboard;slalom");

        expectOK("add-athlete 0029;g;g;russia;snowboard;slalom");
        expectOK("add-athlete 0030;g;g;russia;curling;curling");
        expectOK("add-athlete 0030;g;g;russia;ski jumping;individual");
        expectOK("add-athlete 0030;g;g;russia;snowboard;slalom");
        expectOK("add-athlete 0030;g;g;russia;ski jumping;team");

    }

    @Test
    public void addCompetition() {
        addAthlete();
        expectOK("add-competition 0001;1926;argentina;biathlon;biathlon;1;0;0");
        expectOK("add-competition 0001;1930;argentina;biathlon;biathlon;0;1;0");
        expectOK("add-competition 0002;1930;argentina;biathlon;biathlon;0;1;0");
        expectOK("add-competition 0003;1930;argentina;hockey;icehockey;0;0;0");
        expectOK("add-competition 0004;1930;argentina;iceskating;shorttrack;0;0;1");
        expectOK("add-competition 0005;1930;argentina;iceskating;speedskating;1;0;0");
        expectOK("add-competition 0005;1930;argentina;curling;curling;0;1;0");
        expectOK("add-competition 0005;1934;argentina;curling;curling;0;0;1");

        expectOK("add-competition 0006;1930;bhutan;hockey;icehockey;1;0;0");
        expectOK("add-competition 0007;1930;bhutan;ski jumping;individual;1;0;0");
        expectOK("add-competition 0007;1934;bhutan;ski jumping;individual;0;1;0");
        expectOK("add-competition 0008;1930;bhutan;hockey;icehockey;0;1;0");
        expectOK("add-competition 0009;1930;bhutan;bobsport;bobsleigh;0;0;1");
        expectOK("add-competition 0009;1934;bhutan;bobsport;bobsleigh;0;0;1");
        expectOK("add-competition 0010;1930;bhutan;ski jumping;individual;0;0;1");
        expectOK("add-competition 0010;1934;bhutan;ski jumping;individual;0;0;1");
        expectOK("add-competition 0010;1938;bhutan;ski jumping;individual;0;0;0");
        expectOK("add-competition 0010;1942;bhutan;ski jumping;individual;0;0;1");
        expectOK("add-competition 0010;1946;bhutan;ski jumping;individual;0;1;0");

        expectOK("add-competition 0011;1930;germany;hockey;icehockey;1;0;0");
        expectOK("add-competition 0011;1930;germany;biathlon;biathlon;1;0;0");
        expectOK("add-competition 0011;1930;germany;iceskating;shorttrack;0;1;0");
        expectOK("add-competition 0011;1934;germany;iceskating;shorttrack;0;1;0");
        expectOK("add-competition 0012;1930;germany;bobsport;bobsleigh;0;0;1");
        expectOK("add-competition 0013;1930;germany;curling;curling;0;0;1");

        expectOK("add-competition 0015;1930;canada;hockey;icehockey;0;1;0");
        expectOK("add-competition 0016;1934;canada;biathlon;biathlon;0;1;0");
        expectOK("add-competition 0017;1938;canada;iceskating;shorttrack;0;0;1");
        expectOK("add-competition 0018;1942;canada;bobsport;bobsleigh;0;0;1");
        expectOK("add-competition 0018;1946;canada;bobsport;bobsleigh;0;0;0");
        expectOK("add-competition 0018;1950;canada;bobsport;bobsleigh;0;0;0");
        expectOK("add-competition 0018;1954;canada;bobsport;bobsleigh;0;0;0");

        expectOK("add-competition 0021;1930;china;ski jumping;team;1;0;0");
        expectOK("add-competition 0022;1930;china;ski jumping;team;1;0;0");
        expectOK("add-competition 0023;1930;china;ski jumping;team;0;1;0");
        expectOK("add-competition 0024;1930;china;ski jumping;team;0;1;0");
        expectOK("add-competition 0025;1930;china;ski jumping;team;0;0;1");
        expectOK("add-competition 0026;1930;china;ski jumping;team;0;0;1");

        expectOK("add-competition 0027;1930;france;ski jumping;team;1;0;0");
        expectOK("add-competition 0027;1930;france;iceskating;speedskating;1;0;0");
        expectOK("add-competition 0027;1934;france;iceskating;speedskating;1;0;0");

        expectOK("add-competition 0028;1930;usa;snowboard;slalom;0;1;0");
        expectOK("add-competition 0028;1934;usa;snowboard;slalom;0;1;0");
        expectOK("add-competition 0028;1942;usa;snowboard;slalom;1;0;0");
        expectOK("add-competition 0028;1938;usa;snowboard;slalom;1;0;0");
        expectOK("add-competition 0028;1946;usa;snowboard;slalom;0;0;1");
        expectOK("add-competition 0028;1950;usa;snowboard;slalom;0;0;1");

        expectOK("add-competition 0029;1930;russia;snowboard;slalom;1;0;0");
        expectOK("add-competition 0030;1934;russia;curling;curling;1;0;0");
        expectOK("add-competition 0030;1938;russia;curling;curling;1;0;0");
        expectOK("add-competition 0030;1934;russia;ski jumping;individual;0;0;1");

    }

    @Test
    public void olympicMedalTableEmpty() {
        expectError("olympic-medal-table");
        addIocCode();
        Terminal.addMultipleLinesOutputThatIsExactly("olympic-medal-table",
                "(1,110,bul,bulgaria,0,0,0,0)\n" +
                        "(2,111,arg,argentina,0,0,0,0)\n" +
                        "(3,112,bhu,bhutan,0,0,0,0)\n" +
                        "(4,113,ger,germany,0,0,0,0)\n" +
                        "(5,114,can,canada,0,0,0,0)\n" +
                        "(6,115,chn,china,0,0,0,0)\n" +
                        "(7,116,fra,france,0,0,0,0)\n" +
                        "(8,117,usa,usa,0,0,0,0)\n" +
                        "(9,118,rus,russia,0,0,0,0)\n" +
                        "(10,119,aut,austria,0,0,0,0)");
    }

    @Test
    public void olympicMedalTable() {
        addCompetition();
        Terminal.addMultipleLinesOutputThatIsExactly("olympic-medal-table",
                "(1,118,rus,russia,3,0,1,4)\n" +
                "(2,116,fra,france,3,0,0,3)\n" +
                "(3,112,bhu,bhutan,2,3,5,10)\n" +
                "(4,111,arg,argentina,2,3,2,7)\n" +
                "(5,113,ger,germany,2,2,2,6)\n" +
                "(6,115,chn,china,2,2,2,6)\n" +
                "(7,117,usa,usa,2,2,2,6)\n" +
                "(8,114,can,canada,0,2,2,4)\n" +
                "(9,110,bul,bulgaria,0,0,0,0)\n" +
                "(10,119,aut,austria,0,0,0,0)");

        expectOK("add-athlete 0031;h;h;austria;biathlon;biathlon");
        expectOK("add-competition 0031;1930;austria;biathlon;biathlon;0;0;1");
        expectOK("add-competition 0028;1962;usa;snowboard;slalom;0;1;0");

        Terminal.addMultipleLinesOutputThatIsExactly("olympic-medal-table",
                "(1,118,rus,russia,3,0,1,4)\n" +
                        "(2,116,fra,france,3,0,0,3)\n" +
                        "(3,112,bhu,bhutan,2,3,5,10)\n" +
                        "(4,111,arg,argentina,2,3,2,7)\n" +
                        "(5,117,usa,usa,2,3,2,7)\n" +
                        "(6,113,ger,germany,2,2,2,6)\n" +
                        "(7,115,chn,china,2,2,2,6)\n" +
                        "(8,114,can,canada,0,2,2,4)\n" +
                        "(9,119,aut,austria,0,0,1,1)\n" +
                        "(10,110,bul,bulgaria,0,0,0,0)");
    }

}
