package edu.kit.informatik.junit.suite;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.junit.Wrapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static edu.kit.informatik.junit.TestHelper.expectError;
import static edu.kit.informatik.junit.TestHelper.expectOK;

public class Test006IOCCode {
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
    public void addIOCCodeError() {
        expectError("add-ioc-code 001;ger;germany;1900");
        new Test001Admin().loginLogoutAdminTest();
        expectError("addioc-code 001;ger;germany;1900");
        expectError("add-ioccode 001;ger;germany;1900");
        expectError("add-ioc_code 001;ger;germany;1900");
        expectError("add-ioc-code 000;ger;germany;1900");
        expectError("add-ioc-code 0001;ger;germany;1900");
        expectError("add-ioc-code 001;ge;germany;1900");
        expectError("add-ioc-code 001;germ;germany;1900");
        expectError("add-ioc-code 001;ger;germany;0");
        expectError("add-ioc-code 001;ger;germany;");
        expectError("add-ioc-code 01;ger;germany;1900");
        expectError("add-ioc-code 001t;ger;germany;1900");
        expectError("add-ioc-code 001;;ger;germany;1900");
        expectError("add-ioc-code 001;ge1;germany;1900");
        expectError("add-ioc-code 001;ger;germany;-1900");
        expectError("add-ioc-code 001;Ger;germany;1900");
    }

    /**
     * This is the actual test. It is possible to add more test methods.
     */
    @Test
    public void addIOCCodeOK() {
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
        expectError("add-ioc-code 006;slk;slowakia;1965");
        expectError("add-ioc-code 011;aut;australia;1850");
        Terminal.addSingleLineOutputThatIsExactly("list-ioc-codes",
                "1896 009 aut austria\n" +
                        "1900 001 ger germany\n" +
                        "1920 003 fra france\n" +
                        "1930 002 can canada\n" +
                        "1943 008 mex mexico\n" +
                        "1960 004 net netherlands\n" +
                        "1963 007 bon jamesbond\n" +
                        "1963 010 chi china\n" +
                        "1970 005 usa usa\n" +
                        "2000 006 rus russia"
        );
    }
}
