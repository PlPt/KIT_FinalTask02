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
 * This is a JUnit test. 
 * Unofficial naming convention: TestXXXName[UpdatedXX].java Where XXX are the highest
 * number not used by an other author. Please check this before uploading. [] are optional if 
 * you want to provide an update. Do not delete the old version because maybe someone used it in a test suite.
 *
 * -> Tests for Access control
 *
 * @author Pascal Petzoldt
 * @version 1.2
 */
class Test003OlympicSports {
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
    public void addOlympicSportsErrors() {
        //Check without login
       expectError("add-olympic-sport");
       expectError("add-olympic-sport ");
       expectError("add-olympic-sport ;");
       expectError("add-olympic-sport Leichtathletik\n;Staffellauf");
       expectError("add-olympic-sport ; ; ");


        new Test001Admin().loginLogoutAdminTest();

        expectError("add-olympic-sport");
        expectError("add-olympic-sport ");
        expectError("add-olympic-sport ;");
        expectError("add-olympic-sport Leichtathletik\n;Staffellauf");
        expectError("add-olympic-sport ; ; ");
        expectOK("add-olympic-sport Leichtathletik;Staffellauf");
        expectError("add-olympic-sport Leichtathletik;Staffellauf");
        expectError("add-olympic-sport Leichtathletik;Sprint".toUpperCase());
        expectOK("add-olympic-sport Leichtathletik;Sprint");




    }


    @Test
    public void listOlympicSportsErrors() {

        expectError("list-olympic-sports ");
        expectError("list-olympic-sports Leichtathletik");
        expectError("list-olympic-sports Leichtathletik;Sprint");
        expectError("list-olympic-sports".toUpperCase());

        addOlympicSportsErrors();

        //list sports
        expectError("list-olympic-sports ");
        expectError("list-olympic-sports Leichtathletik");
        expectError("list-olympic-sports Leichtathletik;Sprint");
        expectError("list-olympic-sports".toUpperCase());
        Terminal.addMultipleLinesOutputThatIsExactly("list-olympic-sports","Leichtathletik Sprint\n" +
                "Leichtathletik Staffellauf");
    }


}
