package edu.kit.informatik.junit.suite;

import org.junit.jupiter.api.*;
import edu.kit.informatik.junit.Wrapper;
import static edu.kit.informatik.junit.TestHelper.*;
import edu.kit.informatik.Terminal;

/**
 * This is a JUnit test. 
 * Unofficial naming convention: TestXXXName[UpdatedXX].java Where XXX are the highest
 * number not used by an other author. Please check this before uploading. [] are optional if 
 * you want to provide an update. Do not delete the old version because maybe someone used it in a test suite.
 *
 * @author Lucas Alber
 * @version 1.2
 */
class Test007OlympicSports {
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
    public void addOlympicSportError() {
        expectError("add-olympic-sport icehockey;icehockey");
        new Test001Admin().loginLogoutAdminTest();
        expectError("add-olympicsport icehockey;icehockey");
        expectError("addolympicsport icehockey,icehockey");
        expectError("add olympic sport icehockey;icehockey");
        expectError("add_olympic-sport icehockey;icehockey");
        expectError("addolympicsport icehockey;icehockey");
        expectError("addolympicspor icehockey;icehockey");
        expectError("addolympicsport ice;hockey;icehockey");
        expectError("add-olympic-sport iceho\nckey;icehockey");
        expectError("add-olympic-sport ;;;");
        expectError("add-olympic-sport ;icehockey");
        expectOK("add-olympic-sport icehockey;icehockey");
        expectOK("add-olympic-sport iceskating;shorttrack");
        expectError("add-olympic-sport icehockey;icehockey");
        expectError("add-olympic-sport iceskating;shorttrack");
    }

    /**
     * This is the actual test. It is possible to add more test methods.
     */
    @Test
    public void addOlympicSportOK() {
        new Test001Admin().loginLogoutAdminTest();
        expectOK("add-olympic-sport icehockey;icehockey");
        expectOK("add-olympic-sport biathlon;biathlon");
        expectOK("add-olympic-sport iceskating;shorttrack");
        expectOK("add-olympic-sport iceskating;speedskating");
        expectOK("add-olympic-sport iceskating;figureskating");
        expectOK("add-olympic-sport curling;curling");
        expectOK("add-olympic-sport bobsport;bobsleigh");
        expectOK("add-olympic-sport bobsport;skeleton");
        expectError("add-olympic-sport curling;curling");
        expectError("add-olympic-sport bobsport;skeleton");
        expectError("add-olympic-sport iceskating;speedskating");
        Terminal.addSingleLineOutputThatIsExactly("list-olympic-sports",
                "biathlon biathlon\n" +
                        "bobsport bobsleigh\n" +
                        "bobsport skeleton\n" +
                        "curling curling\n" +
                        "icehockey icehockey\n" +
                        "iceskating figureskating\n" +
                        "iceskating shorttrack\n" +
                        "iceskating speedskating"
        );
    }


}
