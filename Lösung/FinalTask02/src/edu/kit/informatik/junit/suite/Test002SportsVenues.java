package edu.kit.informatik.junit.suite;

import org.junit.jupiter.api.*;
import edu.kit.informatik.junit.Wrapper;
import edu.kit.informatik.Terminal;
import static edu.kit.informatik.junit.TestHelper.*;

/**
 * This is a JUnit test. 
 * Unofficial naming convention: TestXXXName[UpdatedXX].java Where XXX are the highest
 * number not used by an other author. Please check this before uploading. [] are optional if 
 * you want to provide an update. Do not delete the old version because maybe someone used it in a test suite.
 * 
 * @author Lucas Alber
 * @version 1.2
 */
class Test002SportsVenues {
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
    public void addSportsVenuesError() {
        expectError("add-sports-venue ");
        expectError("add-sports-venue ;;;;;");
        expectError("add-sports-venue ;");
        expectError("add-sports-venue id;country;location;name;year;places");
        expectError("add-sports-venue 123;country;location;name;year;places");
        expectError("add-sports-venue 123;country;location;name;1000;places");
        expectError("add-sports-venue 123;country;location;name;1000;50");
        new Test001Admin().loginLogoutAdminTest();
        expectOK("add-ioc-code 001;cou;country;1942");
        expectOK("add-sports-venue 123;country;location;name;1000;50");
        expectError("add-sports-venue 123;country;location;name;1000;50");
        expectError("add-sports-venue 123;country2;location;name;1000;50");
        expectOK("add-sports-venue 124;country;location;name;1000;50");
        expectError("add-sports-venue 000;country2;location;name;1000;50");
        expectError("add-sports-venue 0000;country3;location;name;1000;50");
        expectError("add-sports-venue 133;country2;location;name;100;50");
        //We don't really expect an error, but the program shouldn't crash
        expectError("add-sports-venue 139;country;blablaheyyowuzzup;yoyoheyyoblaja;"
                + "1348;500000000000000000000000000000000000000000000000000000000000000000");
    }
    
    /**
     * This is the actual test.
     */
    @Test
    public void addSportsVenuesOK() {
        new Test001Admin().loginLogoutAdminTest();
        expectOK("add-ioc-code 001;ger;germany;1992");
        expectOK("add-ioc-code 002;esp;spain;1930");
        
        expectOK("add-sports-venue 010;germany;karlsruhe;sportsarena10;1000;11");
        expectOK("add-sports-venue 011;germany;karlsruhe;sportsarena11;1002;12");
        expectOK("add-sports-venue 012;germany;karlsruhe;sportsarena12;1004;13");
        expectOK("add-sports-venue 013;germany;karlsruhe;sportsarena13;1006;14");
        expectOK("add-sports-venue 006;germany;karlsruhe;sportsarena6;1008;7");
        expectOK("add-sports-venue 007;germany;karlsruhe;sportsarena7;1010;8");
        expectOK("add-sports-venue 008;germany;karlsruhe;sportsarena8;1012;9");
        expectOK("add-sports-venue 009;germany;karlsruhe;sportsarena9;1014;10");
        expectOK("add-sports-venue 001;germany;karlsruhe;sportsarena1;1016;2");
        expectOK("add-sports-venue 002;germany;karlsruhe;sportsarena2;1018;3");
        expectOK("add-sports-venue 003;germany;karlsruhe;sportsarena3;1020;4");
        expectOK("add-sports-venue 004;germany;karlsruhe;sportsarena4;1022;5");
        expectOK("add-sports-venue 005;germany;karlsruhe;sportsarena5;1024;6");
        
        expectOK("add-sports-venue 110;spain;barcelona;sportsarena10;1000;11");
        expectOK("add-sports-venue 111;spain;barcelona;sportsarena11;1002;12");
        expectOK("add-sports-venue 112;spain;barcelona;sportsarena12;1004;13");
        expectOK("add-sports-venue 113;spain;barcelona;sportsarena13;1006;14");
        expectOK("add-sports-venue 106;spain;barcelona;sportsarena6;1008;7");
        expectOK("add-sports-venue 107;spain;barcelona;sportsarena7;1010;8");
        expectOK("add-sports-venue 108;spain;barcelona;sportsarena8;1012;9");
        expectOK("add-sports-venue 109;spain;barcelona;sportsarena9;1014;10");
        expectOK("add-sports-venue 101;spain;barcelona;sportsarena1;1016;2");
        expectOK("add-sports-venue 102;spain;barcelona;sportsarena2;1018;3");
        expectOK("add-sports-venue 103;spain;barcelona;sportsarena3;1020;4");
        expectOK("add-sports-venue 104;spain;barcelona;sportsarena4;1022;5");
        expectOK("add-sports-venue 105;spain;barcelona;sportsarena5;1024;6");
    }
    
    /**
     * This is the actual test.
     */
    @Test
    public void listSportsVenuesError() {
        expectError("list-sports-venues ");
        expectError("list-sports-venues");
        expectError("list-sports-venues spain");
        expectError("list-sports-venues germany");
        expectError("list-sports-venues notlogin");
        
        addSportsVenuesOK();
        expectError("list-sports-venues ");
        expectError("list-sports-venues");
        expectError("list-sports-venues spain ");
        expectError("list-sports-venues  germany");
        expectError("list-sports-venues notlogin");
        
        expectOK("logout-admin");
        expectError("list-sports-venues spain");
        expectError("list-sports-venues germany");
        
        expectOK("login-admin test;testtest");
        Terminal.addSingleLineOutputThatIsExactly("list-sports-venues germany",
                "(1 001 karlsruhe 2)\n"
                + "(2 002 karlsruhe 3)\n"
                + "(3 003 karlsruhe 4)\n"
                + "(4 004 karlsruhe 5)\n"
                + "(5 005 karlsruhe 6)\n"
                + "(6 006 karlsruhe 7)\n"
                + "(7 007 karlsruhe 8)\n"
                + "(8 008 karlsruhe 9)\n"
                + "(9 009 karlsruhe 10)\n"
                + "(10 010 karlsruhe 11)\n"
                + "(11 011 karlsruhe 12)\n"
                + "(12 012 karlsruhe 13)\n"
                + "(13 013 karlsruhe 14)");
    }
        
}
