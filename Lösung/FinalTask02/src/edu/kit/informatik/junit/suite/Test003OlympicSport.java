package edu.kit.informatik.junit.suite;

import org.junit.jupiter.api.*;
import edu.kit.informatik.junit.Wrapper;
import edu.kit.informatik.Terminal;
import static edu.kit.informatik.junit.TestHelper.*;

/**
 * Tests the add-olympic-sport command.
 * @author Dominik Bez
 */
public class Test003OlympicSport {

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
    * Test invalid inputs.
    */
   @Test
   public void addOlympicSportInvalid() {
       new Test002SportsVenues().addSportsVenuesOK();
       expectError("add-olympic-sports ski;schnell");
       expectError("add-olympic-sport ski schnell");
       expectError("add-olympic-sport skischnell");
       expectError("addolympic-sport ski;schnell");
       
       expectError("add-olympic-sport ski\n;schnell");
       expectError("add-olympic-sport ski;;schnell");
       expectError("add-olympic-sport ski;sch;nell");
   }
   
   /**
    * Test valid inputs.
    */
   @Test
   public void addOlympicSportValid() {
       new Test002SportsVenues().addSportsVenuesOK();
       expectOK("add-olympic-sport ski;schnell");
       expectOK("add-olympic-sport bob;schnell");
       expectOK("add-olympic-sport ski;langsam");
       expectOK("add-olympic-sport curling;curling");
       expectOK("add-olympic-sport biathlon;biathlon");
   }
    
}
