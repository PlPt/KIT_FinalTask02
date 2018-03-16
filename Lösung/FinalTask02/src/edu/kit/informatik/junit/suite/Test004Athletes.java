package edu.kit.informatik.junit.suite;

import org.junit.jupiter.api.*;
import edu.kit.informatik.junit.Wrapper;
import edu.kit.informatik.Terminal;
import static edu.kit.informatik.junit.TestHelper.*;

/**
 * Tests the add-athlete command.
 * @author Dominik Bez
 */
public class Test004Athletes {
    
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
     * Test invalid input.
     */
    @Test
    public void addAthletesInvalid() {
        new Test003OlympicSport().addOlympicSportValid();
        //IDs
        expectError("add-athlete 1;Michael;Jordan;germany;ski;schnell");
        expectError("add-athlete 23;Michael;Jordan;germany;ski;schnell");
        expectError("add-athlete 001;Michael;Jordan;germany;ski;schnell");
        expectError("add-athlete 0000;Michael;Jordan;germany;ski;schnell");
        
        expectError("add-athletes 0001;Michael;Jordan;germany;ski;schnell");
        expectError("addathlete 0001;Michael;Jordan;germany;ski;schnell");
        expectError("add-athlete 0001;Michael;Jordan;germny;ski;schnell");
        expectError("add-athlete 0001;Michael;Jordan;germany;sk;schnell");
        expectError("add-athlete 0001;Michael;Jordan;germany;ski;schell");
        expectError("add-athlete 0001;Michael;Jordan;germany;ski;sc;hnell");
        expectError("add-athlete 0001;Mi\nchael;Jordan;germany;ski;schnell");
        expectError("add-athlete 0001;Michael;Jo;rdan;germany;ski;schnell");
        
        expectOK("add-athlete 0001;Michael;Jordan;germany;ski;schnell");
        expectError("add-athlete 0001;Michael;Jordan;germany;ski;schnell");
        expectError("add-athlete 0001;Sean;Michaels;germany;ski;schnell");
     //@ToDO   expectError("add-athlete 0001;Michael;Jordan;germany;bob;schnell");
        expectError("add-athlete 0001;Michael;Jordan;spain;ski;schnell");
        expectOK("add-athlete 0002;Michael;Jordan;germany;ski;schnell");
    }
    
    /**
     * Test valid input. You can use this method to easily add admins, ioc codes, sports and athletes.
     */
    @Test
    public void addAthletesValid() {
        new Test003OlympicSport().addOlympicSportValid();
        //germany
        expectOK("add-athlete 0001;Michael;Jordan;germany;ski;schnell");
        expectOK("add-athlete 0001;Michael;Jordan;germany;bob;schnell");
        expectOK("add-athlete 0001;Michael;Jordan;germany;curling;curling");
        expectOK("add-athlete 1000;Jude;Law;germany;bob;schnell");
        
        //spain
        expectOK("add-athlete 0002;Michael;Jackson;spain;ski;schnell");
        expectOK("add-athlete 0003;Michael;Cera;spain;ski;langsam");
        expectOK("add-athlete 0004;Celine;Dion;spain;curling;curling");
        
        //canada
        expectOK("add-ioc-code 003;can;canada;1900");
        expectOK("add-athlete 0010;Robin;Sparkles;canada;ski;schnell");
        expectOK("add-athlete 0010;Robin;Sparkles;canada;curling;curling");
        expectOK("add-athlete 0011;Duncan Campbell;Scott;canada;ski;schnell");
    }
    
}
