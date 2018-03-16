package edu.kit.informatik.junit.suite;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.junit.Wrapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static edu.kit.informatik.junit.TestHelper.expectError;
import static edu.kit.informatik.junit.TestHelper.expectOK;

public class Test013SummaryAthletes {
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
    public void summaryAthletesEmpty() {
        new Test012OlympicMedalTable().addAthlete();
        Terminal.addMultipleLinesOutputThatIsExactly("summary-athletes hockey;icehockey",
                "0003 c c 0\n" +
                        "0006 a a 0\n" +
                        "0008 a a 0\n" +
                        "0011 b b 0\n" +
                        "0014 b b 0\n" +
                        "0015 c c 0\n" +
                        "0020 c c 0");
    }


    @Test
    public void summaryAthletes() {
        new Test012OlympicMedalTable().addCompetition();
        Terminal.addMultipleLinesOutputThatIsExactly("summary-athletes biathlon;biathlon",
                "0001 a a 2\n" +
                        "0002 b b 1\n" +
                        "0011 b b 1\n" +
                        "0016 c c 1\n" +
                        "0027 e e 0");
        Terminal.addMultipleLinesOutputThatIsExactly("summary-athletes hockey;icehockey",
                "0006 a a 1\n" +
                        "0008 a a 1\n" +
                        "0011 b b 1\n" +
                        "0015 c c 1\n" +
                        "0003 c c 0\n" +
                        "0014 b b 0\n" +
                        "0020 c c 0");
        Terminal.addMultipleLinesOutputThatIsExactly("summary-athletes iceskating;shorttrack",
                "0011 b b 2\n" +
                        "0004 d d 1\n" +
                        "0017 c c 1");
        Terminal.addMultipleLinesOutputThatIsExactly("summary-athletes iceskating;speedskating",
                "0027 e e 2\n" +
                        "0005 e e 1");
        Terminal.addMultipleLinesOutputThatIsExactly("summary-athletes curling;curling",
                "0005 e e 2\n" +
                        "0030 g g 2\n" +
                        "0013 b b 1\n" +
                        "0019 c c 0\n" +
                        "0027 e e 0");
        Terminal.addMultipleLinesOutputThatIsExactly("summary-athletes ski jumping;individual",
                "0010 a a 4\n" +
                        "0007 a a 2\n" +
                        "0030 g g 1");
        Terminal.addMultipleLinesOutputThatIsExactly("summary-athletes bobsport;bobsleigh",
                "0009 a a 2\n" +
                        "0012 b b 1\n" +
                        "0018 c c 1\n" +
                        "0027 e e 0");
        Terminal.addMultipleLinesOutputThatIsExactly("summary-athletes ski jumping;team",
                "0021 d d 1\n" +
                        "0022 d d 1\n" +
                        "0023 d d 1\n" +
                        "0024 d d 1\n" +
                        "0025 d d 1\n" +
                        "0026 d d 1\n" +
                        "0027 e e 1\n" +
                        "0030 g g 0");
        Terminal.addMultipleLinesOutputThatIsExactly("summary-athletes snowboard;slalom",
                "0028 f f 6\n" +
                        "0029 g g 1\n" +
                        "0027 e e 0\n" +
                        "0030 g g 0");

        expectOK("add-competition 0027;1970;france;snowboard;slalom;1;0;0");

        Terminal.addMultipleLinesOutputThatIsExactly("summary-athletes snowboard;slalom",
                "0028 f f 6\n" +
                        "0027 e e 1\n" +
                        "0029 g g 1\n" +
                        "0030 g g 0");

        expectOK("add-olympic-sport some;sport");
        Terminal.addNoOutput("summary-athletes some;sport");

    }
}
