package edu.kit.informatik.junit.suite;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.junit.Wrapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static edu.kit.informatik.junit.TestHelper.expectError;
import static edu.kit.informatik.junit.TestHelper.expectOK;

public class Test011Reset { /**
 *
 * Here you have to set which parameters you want to use for your test
 */
public static String[] testArgs = {};

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
    public void addAdmin() {
        expectOK("add-admin Harry;Potter;harryp;12345678");
        expectOK("add-admin Ron;Weasley;ronw;00000000");
        expectOK("add-admin Hermione;Granger;hermg;11111111");
    }

    @Test
    public void addIocCode() {
        addAdmin();
        expectOK("login-admin harryp;12345678");
        expectOK("add-ioc-code 111;arg;argentinien;1920");
        expectOK("add-ioc-code 112;bhu;bhutan;1984");
        expectOK("add-ioc-code 113;bul;bulgarien;1896");
    }

    @Test
    public void addSport() {
        addIocCode();
        expectOK("add-olympic-sport hockey;icehockey");
        expectOK("add-olympic-sport sport;other");
        expectOK("add-olympic-sport biathlon;biathlon");
    }

    @Test
    public void addAthlete() {
        addSport();
        expectOK("add-athlete 0001;lord;voldemort;argentinien;hockey;icehockey");
        expectOK("add-athlete 0002;dumble;dore;bhutan;biathlon;biathlon");
        expectOK("add-athlete 0003;mc;gonnagall;bulgarien;sport;other");
        expectOK("add-athlete 0003;mc;gonnagall;bulgarien;biathlon;biathlon");

    }

    @Test
    public void addCompetition() {
        addAthlete();
        expectOK("add-competition 0001;1926;argentinien;hockey;icehockey;1;0;0");
        expectOK("add-competition 0001;1930;argentinien;hockey;icehockey;0;0;1");
        expectOK("add-competition 0002;1994;bhutan;biathlon;biathlon;0;0;0");
        expectOK("add-competition 0003;1934;bulgarien;sport;other;0;0;1");
        expectOK("add-competition 0003;1934;bulgarien;biathlon;biathlon;0;1;0");

    }

    /**
     * This test an invalid reset input.
     */
    @Test
    public void resetError() {
        expectError("reset");
        addSport();
        expectError("reset ;");
        expectError("Reset");
        expectError("RESET");
        expectError("reset ");
        expectError("reset  ");
        expectError("reset;;");
        expectError("quit reset");
        expectError("reset quit");
        expectError("reset reset");
        expectError("reset--");
        expectError("\nreset");
        expectError(" reset");
        expectError("");
    }

    @Test
    public void resetAdmin() {
        expectError("reset");
        addCompetition();
        expectError("login-admin hermg;11111111");
        expectOK("reset");
        expectError("login-admin harryp;12345678");
        expectOK("logout-admin");
        expectError("add-admin H;P;harryp;12345678");
        expectError("reset");
        expectOK("login-admin harryp;12345678");
        expectOK("reset");
        expectOK("logout-admin");
    }

    @Test
    public void resetSystem() {
        addCompetition();
        Terminal.addMultipleLinesOutputThatIsExactly("list-ioc-codes", "1896 113 bul bulgarien\n" +
                "1920 111 arg argentinien\n" +
                "1984 112 bhu bhutan");
        Terminal.addMultipleLinesOutputThatIsExactly("summary-athletes biathlon;biathlon", "0003 mc gonnagall 1\n" +
                "0002 dumble dore 0");
        Terminal.addMultipleLinesOutputThatIsExactly("olympic-medal-table", "(1,111,arg,argentinien,1,0,1,2)\n" +
                "(2,113,bul,bulgarien,0,1,1,2)\n(3,112,bhu,bhutan,0,0,0,0)");
        expectError("add-ioc-code 112;bhu;bhutan;1984");
        expectError("add-athlete 0002;dumble;dore;bhutan;biathlon;biathlon");
        expectError("add-competition 0003;1934;bulgarien;sport;other;0;0;1");
        expectError("add-olympic-sport biathlon;biathlon");

        expectOK("reset");
        expectError("login-admin harryp;12345678");
        Terminal.addNoOutput("list-ioc-codes");
        expectError("summary-athletes biathlon;biathlon");
        Terminal.addNoOutput("olympic-medal-table");
        expectOK("add-ioc-code 001;bhu;bhutan;1984");
        Terminal.addSingleLineOutputThatIsExactly("list-ioc-codes", "1984 001 bhu bhutan");
        expectOK("logout-admin");
        expectError("reset");
        expectOK("login-admin harryp;12345678");
        expectOK("add-olympic-sport biathlon;biathlon");
        Terminal.addNoOutput("summary-athletes biathlon;biathlon");
        expectOK("add-athlete 0002;dumble;dore;bhutan;biathlon;biathlon");
        expectOK("add-competition 0002;2018;bhutan;biathlon;biathlon;0;0;1");
        Terminal.addMultipleLinesOutputThatIsExactly("olympic-medal-table", "(1,001,bhu,bhutan,0,0,1,1)");
        expectOK("reset");
    }

}
