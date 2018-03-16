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
class Test001Admin {
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
    public void addAdminTestError() {
        expectError("add-admin Dominik;Bez;dodo;1234567");
        expectError("add-admin Julius;Häcker;yolo;1234567891011");
        expectError("add-admin Lukas;Seiz;not;abcdefgh");
        expectError("add-admin Moritz;Leitner;toomuch12;123456789");
        expectError("add-admin Pascal;Petzold;just;R;123456789");
        expectError("add-admin Titia;Gemeinhardt;userOK;zatdt;7676");
        expectOK("add-admin Titia;Gemeinhardt;userOK;zatdt7676");
        expectError("add-admin Wendy;Yi;userOK; nowthere");
        expectError("add-admin Wendy;Yi;userOK; nowthere\n");
        expectError("add-admin Lucas;Alber;lucas\n;goodPass%W.d");
        expectError("add-admin Lucas;Alber;");
        expectError("add-admin Lucas;Alber;;;");
        expectError("add-admin ");
        expectError("add-admin ;;test;testtest");
        
    }
    
    /**
     * This is the actual test. It is possible to add more test methods.
     */
    @Test
    public void addAdminTestOK() {
        expectOK("add-admin Dominik;Bez;dodo;12345678");
        expectOK("add-admin Julius;Häcker;yolo;123456789101");
        expectOK("add-admin Lukas;Seiz;justRiGT;abcdefgh");
        expectOK("add-admin Moritz;Leitner;toomuch1;123456789");
        expectOK("add-admin Pascal;Petzold;just;12345 67 89");
        expectOK("add-admin Titia;Gemeinhardt;userOK;zatdt7676");
        expectOK("add-admin Wendy;Yi;userOK2; nowthere");
        expectOK("add-admin Lucas;Alber;lucas;goodPass%W.d");
        expectOK("add-admin No;Names;test;testtest");
    }
    
    /**
     * This is the actual test. Lets "test" with password "testtest" logged in
     */
    @Test
    public void loginLogoutAdminTest() {
        expectError("login-admin nouser;nopassword");
        expectError("login-admin nouser;");
        expectError("login-admin ;");
        expectError("login-admin ;password");
        addAdminTestOK();
        expectError("login-admin nouser;nopassword");
        expectError("login-admin nouser;");
        expectError("login-admin ;");
        expectError("login-admin ;password");
        expectError("login-admin dodo;12345679");
        expectOK("login-admin dodo;12345678");
        expectError("login-admin dodo;12345678");
        expectError("login-admin justRiGT;abcdefgh");
        expectError("logout-admin dodo;12345678");
        expectOK("logout-admin");
        expectOK("login-admin justRiGT;abcdefgh");
        expectOK("logout-admin");
        expectOK("login-admin test;testtest");
    }
    

}
