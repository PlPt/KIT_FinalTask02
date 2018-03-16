package edu.kit.informatik.junit.suite;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.junit.Wrapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static edu.kit.informatik.junit.TestHelper.expectError;
import static edu.kit.informatik.junit.TestHelper.expectOK;

public class Test010Athlete {
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

    /**
     * This is the actual test. It is possible to add more test methods.
     */
    @Test
    public void addAthleteError() {
        expectError("add-athlete 001;ger;germany;1900");
        new Test001Admin().loginLogoutAdminTest();

        expectOK("add-ioc-code 001;ger;germany;1900");
        expectOK("add-ioc-code 002;can;canada;1930");
        expectOK("add-ioc-code 003;fra;france;1920");
        expectOK("add-ioc-code 004;net;netherlands;1960");
        expectOK("add-ioc-code 005;usa;usa;1970");
        expectOK("add-ioc-code 006;rus;russia;2000");
        expectOK("add-ioc-code 010;chi;china;1963");
        expectOK("add-ioc-code 008;mex;mexico;1943");
        expectOK("add-ioc-code 009;aut;austria;1896");
        expectOK("add-ioc-code 007;bon;jamesbond;1963");

        expectOK("add-olympic-sport icehockey;icehockey");
        expectOK("add-olympic-sport biathlon;biathlon");
        expectOK("add-olympic-sport iceskating;shorttrack");
        expectOK("add-olympic-sport iceskating;speedskating");
        expectOK("add-olympic-sport iceskating;figureskating");
        expectOK("add-olympic-sport curling;curling");
        expectOK("add-olympic-sport bobsport;bobsleigh");
        expectOK("add-olympic-sport bobsport;skeleton");

        expectError("add-athlete 001;Felix;Loch;germany;bobsport;bobsleigh");
        expectError("add-athlete 0000;Felix;Loch;germany;bobsport;bobsleigh");
        expectError("add-athlete 0001;Felix;Loch;jamaika;bobsport;bobsleigh");
        expectError("add-athlete 0001;Felix;Loch;germany;bobsport;bob");
        expectError("add-athlete 0001;Felix;Loch;ger;bobsport;bobsleigh");
        expectError("add-athlete 00014;Felix;Loch;germany;bobsport;bobsleigh");
        expectError("add-athlete 0001;Fel;ix;Loch;germany;ski;bobsleigh");
        //expectError("add-athlete 0001;Felix;Loch12;germany;bobsport;bobsleigh");
        expectError("add-athlete 0001;Felix;Lo;ch;germany;bobsport;bobsleigh");
        expectError("add-athlete 0001;Felix;Loch;ger\nmany;bobsport;bobsleigh");
        expectError("add-athlete -0001;Felix;Loch;germany;bobsport;bobsleigh");
        expectError("addathlete 0001;Felix;Loch;germany;bobsport;bobsleigh");
    }

    /**
     * This is the actual test. It is possible to add more test methods.
     */
    @Test
    public void addAthleteOK() {
        new Test001Admin().loginLogoutAdminTest();

        expectOK("add-ioc-code 001;ger;germany;1900");
        expectOK("add-ioc-code 002;can;canada;1930");
        expectOK("add-ioc-code 003;fra;france;1920");
        expectOK("add-ioc-code 004;net;netherlands;1960");
        expectOK("add-ioc-code 005;usa;usa;1970");
        expectOK("add-ioc-code 006;rus;russia;2000");
        expectOK("add-ioc-code 010;chi;china;1963");
        expectOK("add-ioc-code 008;mex;mexico;1943");
        expectOK("add-ioc-code 009;aut;austria;1896");
        expectOK("add-ioc-code 007;bon;jamesbond;1963");

        expectOK("add-olympic-sport icehockey;icehockey");
        expectOK("add-olympic-sport biathlon;biathlon");
        expectOK("add-olympic-sport iceskating;shorttrack");
        expectOK("add-olympic-sport iceskating;speedskating");
        expectOK("add-olympic-sport iceskating;figureskating");
        expectOK("add-olympic-sport curling;curling");
        expectOK("add-olympic-sport bobsport;bobsleigh");
        expectOK("add-olympic-sport bobsport;skeleton");

        expectOK("add-athlete 0001;Felix;Loch;germany;bobsport;bobsleigh");
        expectOK("add-athlete 0001;Felix;Loch;germany;bobsport;skeleton");
        expectOK("add-athlete 0001;Felix;Loch;germany;curling;curling");
        expectOK("add-athlete 0002;Natalie;Geisenberger;germany;bobsport;bobsleigh");
        expectOK("add-athlete 0003;Agent;One;jamesbond;bobsport;bobsleigh");
        expectOK("add-athlete 0004;Andi;Langenhahn;germany;bobsport;bobsleigh");
        expectError("add-athlete 0001;Felix;Maier;germany;bobsport;bobsleigh");
        expectError("add-athlete 0001;Felix;Loch;germany;bobsport;bobsleigh");
        Terminal.addSingleLineOutputThatIsExactly("summary-athletes bobsport;bobsleigh",
                "0001 Felix Loch 0\n" +
                        "0002 Natalie Geisenberger 0\n" +
                        "0003 Agent One 0\n" +
                        "0004 Andi Langenhahn 0"
        );
    }
}
