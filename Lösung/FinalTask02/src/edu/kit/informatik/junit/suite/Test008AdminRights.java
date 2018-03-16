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
class Test008AdminRights {
    /**
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
        expectOK("add-admin Lucas;Alber;ldap;hallo-leute");
        expectOK("login-admin ldap;hallo-leute");
        expectError("add-admin Dominik;Bez;dodo;was-geht-ab");
    }
    
    @Test
    public void loginAdmin() {
        addAdmin();
        expectError("login-admin ldap;hallo-leute");
        expectOK("logout-admin");
        expectOK("add-admin Dominik;Bez;dodo;was-geht-ab");
        expectOK("login-admin ldap;hallo-leute");
        expectError("login-admin dodo;was-geht-ab");
    }
    
    @Test
    public void logoutAdmin() {
        expectError("logout-admin");
        addAdmin();
        expectOK("logout-admin");
        expectError("logout-admin");
    }
    
    @Test
    public void addIocCode() {
        expectError("add-ioc-code 001;ger;germany;1992");
        addAdmin();
        expectOK("add-ioc-code 001;ger;germany;1992");
        expectError("add-ioc-code 002;get;germany;1990");
        expectOK("logout-admin");
        expectError("add-ioc-code 001;ger;germany;1992");
        expectError("add-ioc-code 002;esp;spain;1900");
    }
    
    @Test
    public void listIocCodes() {
        expectError("list-ioc-codes");
        addIocCode();
        expectOK("login-admin ldap;hallo-leute");
        Terminal.addSingleLineOutputThatIsExactly("list-ioc-codes",
                "1992 001 ger germany");
        expectOK("logout-admin");
        expectError("list-ioc-codes");
    }
    
    @Test
    public void addSportsVenue() {
        expectError("add-sports-venue 001;germany;trofi;sportstätte1;2948;1000000");
        addIocCode();
        expectOK("login-admin ldap;hallo-leute");
        expectOK("add-sports-venue 001;germany;trofi;sportstätte1;2948;1000000");
        expectError("add-sports-venue 001;germany;trofi;sportstätte1;2948;1000000");
        expectOK("logout-admin");
        expectError("add-sports-venue 051;germany;hoid;sportstätte2;1648;100");
    }
    
    @Test
    public void listSportsVenues() {
        expectError("list-sports-venues germany");
        addSportsVenue();
        expectOK("login-admin ldap;hallo-leute");
        Terminal.addSingleLineOutputThatIsExactly("list-sports-venues germany", "(1 001 trofi 1000000)");
        expectOK("logout-admin");
        expectError("list-sports-venues germany");
    }
    
    @Test
    public void addOlympicSport() {
        expectError("add-olympic-sport ski;schnell");
        addSportsVenue();
        expectOK("login-admin ldap;hallo-leute");
        expectOK("add-olympic-sport ski;schnell");
        expectOK("logout-admin");
        expectError("add-olympic-sport bla;gealödf");
    }
    
    @Test
    public void listOlympicSports() {
        expectError("list-olympic-sports");
        addOlympicSport();
        expectError("list-olympic-sports");
        expectOK("login-admin ldap;hallo-leute");
        Terminal.addSingleLineOutputThatIsExactly("list-olympic-sports", "ski schnell");
        expectOK("logout-admin");
        expectError("list-olympic-sports");
    }
    
    @Test
    public void addAthlete() {
        expectError("add-athlete 0001;Michael;Jackson;germany;ski;schnell");
        addOlympicSport();
        expectError("add-athlete 0001;Michael;Jackson;germany;ski;schnell");
        expectOK("login-admin ldap;hallo-leute");
        expectOK("add-athlete 0001;Michael;Jackson;germany;ski;schnell");
        expectError("add-athlete 0001;Michael;Jackson;germany;ski;schnell");
        expectOK("logout-admin");
        expectError("add-athlete 0002;Hlasfd;!\"§$;germany;ski;schnell");
    }
    
    @Test
    public void addCompetition() {
        expectError("add-competition 0001;2018;germany;ski;schnell;0;0;1");
        addAthlete();
        expectError("add-competition 0001;2018;germany;ski;schnell;0;0;1");
        expectOK("login-admin ldap;hallo-leute");
        expectOK("add-competition 0001;2018;germany;ski;schnell;0;0;1");
        expectError("add-competition 0001;2018;germany;ski;schnell;0;0;1");
        expectOK("logout-admin");
        expectError("add-competition 0001;2014;germany;ski;schnell;0;0;1");
    }
    
    @Test
    public void summaryAthletes() {
        expectError("summary-athletes bob;schnell");
        addCompetition();
        expectError("summary-athletes bob;schnell");
        expectError("summary-athletes ski;schnell");
        expectOK("login-admin ldap;hallo-leute");
        expectError("summary-athletes bob;schnell");
        Terminal.addSingleLineOutputThatIsExactly("summary-athletes ski;schnell", 
                "0001 Michael Jackson 1");
        expectOK("logout-admin");
        expectError("summary-athletes ski;schnell");
    }
    
    @Test
    public void olympicMedalTable() {
        expectError("olympic-medal-table");
        addCompetition();
        expectError("olympic-medal-table");
        expectOK("login-admin ldap;hallo-leute");
        Terminal.addSingleLineOutputThatIsExactly("olympic-medal-table",
                "(1,001,ger,germany,0,0,1,1)");
        expectOK("logout-admin");
        expectError("olympic-medal-table");
    }
    
    @Test
    public void resetCommand() {
        expectError("reset");
        addCompetition();
        expectError("reset");
        expectOK("login-admin ldap;hallo-leute");
        expectOK("reset");
        expectOK("reset");
        expectOK("logout-admin");
        expectError("reset");
    }

}
