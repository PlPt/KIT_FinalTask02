package edu.kit.informatik.junit.suite;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.junit.Wrapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static edu.kit.informatik.junit.TestHelper.expectError;
import static edu.kit.informatik.junit.TestHelper.expectOK;

/**
 * @author Pascal Petzoldt
 *
 * Complete indipendent test of program
 */

public class Test014CompleteIndipendentTest {
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

     @Test
     public void adminAccessDenied(){
        expectError("logout-admin");
         expectError("add-ioc-code 001;ger;germany;1996");
        expectError("add-sports-venue 001;germany;karlsruhe;Stadion;2000;255");
        expectError("list-sports-venues germany");
        expectError("add-olympic-sport Ski;Biathlon");
        expectError("list-olympic-sports");
        expectError("list-ioc-codes");
        expectError("add-athlete 0001;Markus;Taste;germany;Ski;Biathlon");
        expectError("summary-athletes Ski");
        expectError("add-competition 0001;2018;germany;Ski,Biathlon;1;0;0");
        expectError("olympic-medal-table");
        expectError("reset");
     }

     @Test
     public void addAdmins(){
        expectError("add-admin Hans;Wurst;Hans Wurst;12345678qsfjjjjj");
        expectError("add-admin Hans;Wurst;H;1234567890");
        expectError("add-admin Hans;Wurst;Hans;mypw");
        expectError("add-admin Hans;Wurst;Hans;123456789101112131415");
        expectOK("add-admin Hans;Wurst;Hans;1234567890");
        expectError("add-admin Hans;Wurst;Hans;4567890417");
        expectOK("add-admin Admin;Administrator;Admin;4QE$127u#77");
     }

     @Test
     public void loginLogout(){
        addAdmins();
        expectError("logout-admin");
        expectError("login-admin ;");
        expectError("login-admin Hans;1234567890;");
        expectOK("login-admin Hans;1234567890");
        expectError("login-admin Hans;1234567890");
        expectOK("logout-admin");
        expectError("logout-admin");
     }

     @Test
     public void login(){
        login(true);
     }

     public void login(boolean addUsers){
        if(addUsers) addAdmins();
        expectOK("login-admin Admin;4QE$127u#77");
     }

     @Test
     public void addIocCodes(){
         addIocCodes(true);
     }

     public void addIocCodes(boolean addUsers){
        login(addUsers);
         expectError("add-ioc-code 1;ger;germany;1995");
         expectError("add-ioc-code 01;ger;germany;1995");
         expectError("add-ioc-code 001;ger1;germany;1995");
         expectError("add-ioc-code 001;ger;germany;9995");
         expectOK("add-ioc-code 001;ger;germany;1995");
         expectError("add-ioc-code 001;ger;germany;1995");
         expectOK("add-ioc-code 002;fra;france;1989");
         expectError("add-ioc-code 002;fra;france;1989");
         expectError("add-ioc-code 001;chi;China;1956");
         expectOK("add-ioc-code 003;chi;China;1956");
         expectOK("add-ioc-code 004;aus;Austria;1967");
         expectError("add-ioc-code 005;pol;Polen;9967");
         expectOK("add-ioc-code 005;pol;Polen;1969");
         expectOK("add-ioc-code 006;swz;Schweiz;1977");

     }

     @Test
     public void summaryIocCodes(){
        login(true);
        Terminal.addNoOutput("list-ioc-codes"); // No output
         expectOK("logout-admin");
        addIocCodes(false);
         Terminal.addSingleLineOutputThatIsExactly("list-ioc-codes","1956 003 chi China\n" +
                 "1967 004 aus Austria\n" +
                 "1969 005 pol Polen\n" +
                 "1977 006 swz Schweiz\n" +
                 "1989 002 fra france\n" +
                 "1995 001 ger germany");

         expectOK("add-ioc-code 007;spa;Spanien;1995");

         Terminal.addSingleLineOutputThatIsExactly("list-ioc-codes","1956 003 chi China\n" +
                 "1967 004 aus Austria\n" +
                 "1969 005 pol Polen\n" +
                 "1977 006 swz Schweiz\n" +
                 "1989 002 fra france\n" +
                 "1995 001 ger germany\n" +
                 "1995 007 spa Spanien");
     }

     @Test
     public void addSportsVenue(){

        login();
        expectError("add-sports-venue 001;germany;Karlsruhe;Stadion;1949;255");


        expectOK("logout-admin");
        addIocCodes(false);

         expectError("add-sports-venue 1;germany;Karlsruhe;Stadion;1949;255");
         expectError("add-sports-venue 01;germany;Karlsruhe;Stadion;1949;255");
         expectError("add-sports-venue 001;germany;Karlsruhe;Stadion;9949;255");
         expectError("add-sports-venue 001;Holland;Amdsterdam;Stadion;1949;255");


         expectOK("add-sports-venue 001;germany;Karlsruhe;Stadion;1949;255");
         expectError("add-sports-venue 001;germany;Stuttgart;MB Arena;1955;2155");

        expectOK("add-sports-venue 002;germany;Karlsruhe;Schlossgarten;1978;512");
        expectError("add-sports-venue 002;Schweiz;Karlsruhe;Schlossgarten;1978;512");

         expectOK("add-sports-venue 003;Schweiz;Graz;Flughafen;1978;522");

     }

    @Test
    public void summarySportVenues() {
        addSportsVenue();
        Terminal.addSingleLineOutputThatIsExactly("list-sports-venues germany","(1 001 Karlsruhe 255)\n" +
                "(2 002 Karlsruhe 512)");
        Terminal.addSingleLineOutputThatIsExactly("list-sports-venues Schweiz","(1 003 Graz 522)");


    }


    @Test
    public void addOlympicSport() {
      login();
        expectOK("add-olympic-sport Ski;Biathlon");
        expectError("add-olympic-sport Ski;Biathlon");

        expectOK("add-olympic-sport Ski;Langlauf");
        expectOK("add-olympic-sport Ski;springen");

        expectOK("add-olympic-sport Snowboard;springen");
        expectOK("add-olympic-sport Snowboard;fahren");
        expectOK("add-olympic-sport Ski;abfahrt");

        expectOK("add-olympic-sport Hockey;IceHockey");
        expectError("add-olympic-sport Hockey;IceHockey");
        expectError("add-olympic-sport ;IceHockey");
    }

    @Test
    public void listOlympicSport() {
        addOlympicSport();

        Terminal.addSingleLineOutputThatIsExactly("list-olympic-sports","Hockey IceHockey\n" +
                "Ski Biathlon\n" +
                "Ski Langlauf\n" +
                "Ski abfahrt\n" +
                "Ski springen\n" +
                "Snowboard fahren\n" +
                "Snowboard springen");
    }

    @Test
    public void addAthlete(){

     addOlympicSport();
        expectError("add-athlete 0001;Markus;Reich;germany;Snowboard;fahren");
        expectOK("logout-admin");
        addIocCodes(false);
     expectError("add-athlete 0001;Markus;Reich;germany;Ski;IceHockey");
        expectError("add-athlete 1;Markus;Reich;germany;Ski;springen");
        expectError("add-athlete 01;Markus;Reich;germany;Ski;springen");
        expectError("add-athlete 001;Markus;Reich;germany;Ski;springen");
        expectError("add-athlete 0001;Markus;Reich;germany;Snowboard;abfahrt");

        expectOK("add-athlete 0001;Markus;Reich;germany;Snowboard;fahren");
        expectOK("add-athlete 0001;Markus;Reich;germany;Ski;springen");
        expectError("add-athlete 0001;Markus;Reich;germany;Ski;springen");

        expectOK("add-athlete 0002;Matt;Deamon;Schweiz;Ski;springen");
        expectError("add-athlete 0002;Matt;Deamon;Schweiz;Ski;springen");
        expectOK("add-athlete 0002;Matt;Deamon;Schweiz;Ski;Biathlon");

        expectOK("add-athlete 0003;Thomas;Anders;germany;Ski;Langlauf");
        expectOK("add-athlete 0003;Thomas;Anders;germany;Snowboard;springen");

    }

    @Test
    public void summaryAthletes(){
        addAthlete();
        expectError("summary-athletes");
        expectError("summary-athletes springen");
        expectError("summary-athletes Ice");

        Terminal.addSingleLineOutputThatIsExactly("summary-athletes Ski;springen","0001 Markus Reich 0\n" +
                "0002 Matt Deamon 0"
               );

        Terminal.addSingleLineOutputThatIsExactly("summary-athletes Ski;Biathlon","0002 Matt Deamon 0");
    }


    @Test
    public void addCompetition(){
        addAthlete();

        expectError("add-competition 0045;2018;germany;Ski;springen;1;0;0");
        expectError("add-competition 0045;2018;germany;Ski;springen;1;0;0");
        expectError("add-competition 0001;2018;germany;Snowboard;springen;1;0;0");
        expectError("add-competition 0001;2017;germany;Snowboard;fahren;1;0;0");
        expectError("add-competition 0001;2016;germany;Snowboard;fahren;1;0;0");
        expectError("add-competition 0001;2018;germany;Snowboard;fahren;1;1;1");
        expectError("add-competition 0001;2018;germany;Snowboard;fahren;1;1;0");
        expectError("add-competition 0001;2018;germany;Snowboard;fahren;1;0;1");
        expectError("add-competition 0001;2018;germany;Snowboard;fahren;0;1;1");

        expectOK("add-competition 0001;2018;germany;Snowboard;fahren;1;0;0");
        expectError("add-competition 0001;2018;germany;Snowboard;fahren;1;0;0");
        expectError("add-competition 0001;2018;germany;Snowboard;fahren;0;0;1");
        expectOK("add-competition 0001;2018;germany;Ski;springen;0;1;0");
        expectOK("add-competition 0001;2014;germany;Ski;springen;1;0;0");

        expectError("add-competition 0002;2018;germany;Snowboard;springen;1;0;0");
        expectError("add-competition 0002;2018;Schweiz;Snowboard;springen;1;0;0");
        expectError("add-competition 0002;2018;germany;Ski;Biathlon;0;0;1");

        expectOK("add-competition 0002;2018;Schweiz;Ski;springen;1;0;0");
        expectOK("add-competition 0002;2018;Schweiz;Ski;Biathlon;0;0;1");
        expectError("add-competition 0002;2018;Schweiz;Ski;Biathlon;0;0;1");
        expectOK("add-competition 0002;2014;Schweiz;Ski;Biathlon;0;0;1");
        expectError("add-competition 0002;2014;Schweiz;Ski;Biathlon;0;0;1");

        expectOK("add-competition 0003;2014;germany;Ski;Langlauf;0;0;1");
        expectOK("add-competition 0003;2018;germany;Snowboard;springen;0;0;1");
        expectError("add-competition 003;2014;Schweiz;Snowboard;springen;0;0;1");
        expectError("add-competition 0003;2012;Schweiz;Snowboard;springen;0;0;1");
    }

    @Test
    public  void medalTable(){
        addCompetition();
        Terminal.addSingleLineOutputThatIsExactly("olympic-medal-table","(1,001,ger,germany,2,1,2,5)\n" +
                "(2,006,swz,Schweiz,1,0,2,3)\n" +
                "(3,002,fra,france,0,0,0,0)\n" +
                "(4,003,chi,China,0,0,0,0)\n" +
                "(5,004,aus,Austria,0,0,0,0)\n" +
                "(6,005,pol,Polen,0,0,0,0)");

    }

    @Test
    public void  resetGame(){
        addCompetition();
        Terminal.addSingleLineOutputThatIsExactly("olympic-medal-table","(1,001,ger,germany,2,1,2,5)\n" +
                "(2,006,swz,Schweiz,1,0,2,3)\n" +
                "(3,002,fra,france,0,0,0,0)\n" +
                "(4,003,chi,China,0,0,0,0)\n" +
                "(5,004,aus,Austria,0,0,0,0)\n" +
                "(6,005,pol,Polen,0,0,0,0)");

        expectOK("reset");

        Terminal.addNoOutput("olympic-medal-table");
        Terminal.addNoOutput("list-ioc-codes");
        Terminal.addNoOutput("list-olympic-sports");
        expectError("summary-athletes Ski;springen");

    }
}
