package edu.kit.informatik.junit.suite;

import org.junit.jupiter.api.*;
import edu.kit.informatik.junit.Wrapper;
import edu.kit.informatik.Terminal;
import static edu.kit.informatik.junit.TestHelper.*;

/**
 * Tests the add-competition, summary-athletes and olympic-medal-table commands.
 * @author Dominik Bez
 */
public class Test005Competitions {

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
   public void addCompetitionInvalid() {
       new Test004Athletes().addAthletesValid();
       expectError("add-competition 001;2018;germany;ski;schnell;0;0;1");
       expectError("add-competition 000;2018;germany;ski;schnell;0;0;1");
       expectError("add-competition 0001;208;germany;ski;schnell;0;0;1");
       expectError("add-competition 0001;2018;gerany;ski;schnell;0;0;1");
       expectError("add-competition 0001;2018;germany;si;schnell;0;0;1");
       expectError("add-competition 0001;2018;germany;ski;schell;0;0;1");
       expectError("add-competition 0001;2018;germany;ski;schnell;;0;1");
       expectError("add-competition 0001;2018;germany;ski;schnell;0;1");
       expectError("add-competition 0001;2018;germany;ski;schnell;0;0;");
       expectError("add-competition 0001;2018;spain;ski;schnell;0;0;1");
       expectError("add-competition 0001;2018;germany;ski;langsam;0;0;1");
       expectError("add-competition 0001;2018;germany;ski;schnell;0;1;1");
       expectError("add-competition 0001;2018;germany;ski;schnell;1;0;1");
       expectError("add-competition 0001;2018;germany;ski;schnell;1;1;1");
       expectError("add-competition 0001;2018;germany;ski;schnell;0;0;2");
       expectError("add-competition 0001;2017;germany;ski;schnell;0;0;1");
       expectError("add-competition 0001;1922;germany;ski;schnell;0;0;1");
       expectError("add-competition 0001;2022;germany;ski;schnell;0;0;1");
       
       expectOK("add-competition 0001;2018;germany;ski;schnell;0;0;1");
       expectError("add-competition 0001;2018;germany;ski;schnell;0;0;1");
       expectError("add-competition 0001;2018;germany;ski;schnell;0;1;0");
       expectError("add-competition 0001;2018;germany;ski;schnell;1;0;0");
       expectError("add-competition 0001;2018;germany;ski;schnell;0;0;0");
       expectOK("add-competition 0002;2018;spain;ski;schnell;0;0;0");
       
       Terminal.addSingleLineOutputThatIsExactly("summary-athletes ski;schnell",
               "0001 Michael Jordan 1\n"
             + "0002 Michael Jackson 0\n"
             + "0010 Robin Sparkles 0\n"
             + "0011 Duncan Campbell Scott 0");
   }
   
   /**
    * Test valid input. You can use this method to easily add admins, ioc codes, sports, athletes and competitions.
    */
   @Test
   public void addCompetitionValid() {
       new Test004Athletes().addAthletesValid();
       //germany
       expectOK("add-competition 0001;2018;germany;ski;schnell;0;0;1");
       expectOK("add-competition 0001;2014;germany;ski;schnell;0;0;1");
       expectOK("add-competition 0001;1994;germany;ski;schnell;0;1;0");
       expectOK("add-competition 0001;2018;germany;bob;schnell;1;0;0");
       expectOK("add-competition 0001;2002;germany;curling;curling;0;0;1");
       expectOK("add-competition 1000;2018;germany;bob;schnell;1;0;0");
       
       //spain
       expectOK("add-competition 0002;2018;spain;ski;schnell;0;0;1");
       expectOK("add-competition 0002;2014;spain;ski;schnell;1;0;0");
       expectOK("add-competition 0002;1930;spain;ski;schnell;0;1;0");
       expectOK("add-competition 0003;2018;spain;ski;langsam;0;0;1");
       expectError("add-competition 0003;2018;spain;ski;langsam;0;0;0");
       expectOK("add-competition 0003;2014;spain;ski;langsam;0;1;0");
       
       //canada
       expectOK("add-competition 0010;2018;canada;ski;schnell;1;0;0");
       expectOK("add-competition 0010;2014;canada;ski;schnell;1;0;0");
       expectOK("add-competition 0010;2010;canada;ski;schnell;1;0;0");
       expectOK("add-competition 0010;2006;canada;ski;schnell;1;0;0");
       expectOK("add-competition 0010;2002;canada;ski;schnell;1;0;0");
       expectOK("add-competition 0010;2002;canada;curling;curling;1;0;0");
       expectOK("add-competition 0011;2018;canada;ski;schnell;0;1;0");
       expectOK("add-competition 0011;2014;canada;ski;schnell;0;0;1");
       expectOK("add-competition 0011;2010;canada;ski;schnell;0;0;0");
       
       //summary ski schnell
       Terminal.addSingleLineOutputThatIsExactly("summary-athletes ski;schnell",
               "0010 Robin Sparkles 5\n"
             + "0001 Michael Jordan 3\n"
             + "0002 Michael Jackson 3\n"
             + "0011 Duncan Campbell Scott 2");
       
       //summary bob schnell
       Terminal.addSingleLineOutputThatIsExactly("summary-athletes bob;schnell",
               "0001 Michael Jordan 1\n"
             + "1000 Jude Law 1");
       
       //summary ski langsam
       Terminal.addSingleLineOutputThatIsExactly("summary-athletes ski;langsam", "0003 Michael Cera 2");
       
       //summary curling curling
       Terminal.addSingleLineOutputThatIsExactly("summary-athletes curling;curling",
               "0001 Michael Jordan 1\n"
             + "0010 Robin Sparkles 1\n"
             + "0004 Celine Dion 0");
       
       //summary biathlon biathlon
       Terminal.addNoOutput("summary-athletes biathlon;biathlon");
       
       //olympic medal table
       Terminal.addSingleLineOutputThatIsExactly("olympic-medal-table",
               "(1,003,can,canada,6,1,1,8)\n"
             + "(2,001,ger,germany,2,1,3,6)\n"
             + "(3,002,esp,spain,1,2,2,5)");
   }
    
}
