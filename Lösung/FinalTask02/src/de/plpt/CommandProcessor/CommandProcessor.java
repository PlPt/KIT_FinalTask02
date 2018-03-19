package de.plpt.CommandProcessor;

import de.plpt.ArgumentParser.*;
import de.plpt.olympicgames.GameManagement;
import de.plpt.olympicgames.exception.AuthorizeException;
import de.plpt.olympicgames.exception.GameManagementException;
import de.plpt.olympicgames.model.AdminUser;
import de.plpt.olympicgames.model.Athlete;
import de.plpt.olympicgames.model.IocMember;
import de.plpt.olympicgames.model.SportVenue;
import edu.kit.informatik.Terminal;

public class CommandProcessor {

    //region varDef
    private boolean quit = false;
    private ArgumentParser parser;
    private GameManagement gameManagement;
    //endregion

    //region constructor

    /**
     * Initializes a new CommandProcessor object
     */
    public CommandProcessor() {
        this.parser = new ArgumentParser(this);
        this.gameManagement = new GameManagement();

    }
    //endregion

    //region processCommands

    /**
     * Starts task which processes all input commands until quit
     */
    public void processCommands() {

        while (!quit) {
            String line = Terminal.readLine();
            try {
                String result = parser.parse(line);
                if (result != null) Terminal.printLine(result);
            } catch (ArgumentParserExecutionException apeex) {
                Exception clientException = apeex.getTypedCause();
                Terminal.printError(String.format("[ArgumentParserExecutionException][%s] :: %s",
                        clientException.getClass().getName(), clientException.getMessage()));

            } catch (ArgumentParserException apex) {
                Terminal.printError(String.format("[ArgumentParserException] :: %s", apex.getMessage()));
            } catch (IllegalArgumentException x) {
                Terminal.printError(String.format("[%s] :: %s", x.getClass().getName(), x.getMessage()));
            } catch (IntervalViolationException x) {
                Terminal.printError(String.format("[%s] :: %s", x.getClass().getName(), x.getMessage()));
            }
        }
    }
    //endregion

    //region Methods

    //region AdminManagement

    //region addAdmin

    /**
     * Adds an admin to the system
     *
     * @param name     Name of Admin
     * @param surname  Surname of Admin
     * @param userName UserName of Admin
     * @param password Password of Admin
     * @return Result of addAdmin
     * @throws GameManagementException Is thrown when user already exists or any user is logged in
     */
    @CommandInfo(command = "add-admin ([^;\\n]+);([^;\\n]+);([^;\\n]{4,8});([^;\\n]{8,12})")
    public String addAdmin(String name, String surname, String userName, String password)
            throws GameManagementException {
        gameManagement.addAdmin(new AdminUser(name, surname, userName, password));
        return "OK";
    }
    //endregion


    //region loginAdmin

    /**
     * Login an registered admin
     *
     * @param userName UserName of Admin
     * @param password Passowrd of Admin
     * @return OK if login was successful
     */
    @CommandInfo(command = "login-admin ([^;\\n]{4,8});([^;\\n]{8,12})")
    public String loginAdmin(String userName, String password) {

        boolean success = gameManagement.loginAdmin(userName, password);

        if (!success) throw new AuthorizeException("Username or passwort incorrect. Try again");

        return "OK";
    }
    //endregion

    //region logoutAdmin

    /**
     * Logged out admin
     *
     * @return OK if logout was successful
     */
    @CommandInfo(command = "logout-admin")
    public String logoutAdmin() {
        gameManagement.logout();
        return "OK";
    }
    //endregion

    //endregion

    //region SportManagement

    //region addSportsVenue

    /**
     * Adds a new SportVenue to system
     *
     * @param id          unique id of Sports venue
     * @param countryName countryName of venue
     * @param place       place of venue
     * @param name        name of venue
     * @param openingYear year of venue opening
     * @param numberSeats number of seats in venue
     * @return OK if add to system was successful
     * @throws GameManagementException is throw when Country don't exists or venue is already in system
     */
    @CommandInfo(command = "add-sports-venue (\\d{3});([^;\\n]+);([^;\\n]+);([^;\\n]+);(\\d+);(\\d++)")
    public String addSportsVenue(@ParameterInfo(minValue = 1, maxValue = 999) int id, String countryName, String place
            , String name, @ParameterInfo(minValue = 0, maxValue = 2999) int openingYear, int numberSeats)
            throws GameManagementException {
        gameManagement.checkLogin();
        gameManagement.addSportVenue(new SportVenue(id, countryName, place, name, openingYear, numberSeats));
        return "OK";
    }
    //endregion

    //region listSportsVenues

    /**
     * Lists all Sport venues in given country
     *
     * @param country country of venues
     * @return String list of sport venues
     * @throws GameManagementException is thrown when there is an error
     */
    @CommandInfo(command = "list-sports-venues ([^;\\n]+)")
    public String listSportsVenues(String country) throws GameManagementException {
        gameManagement.checkLogin();
        return gameManagement.listSportVenues(country);
    }
    //endregion

    //region addOlympicSport

    /**
     * Adds an olympic sport to system
     *
     * @param kindOfSport kind of sport to add
     * @param discipline  discipline to add
     * @return Ok if adding was successful
     * @throws GameManagementException is thrown when olympic sport already exists
     */
    @CommandInfo(command = "add-olympic-sport ([^;\\n]+);([^;\\n]+)")
    public String addOlympicSport(String kindOfSport, String discipline) throws GameManagementException {
        gameManagement.checkLogin();

        boolean success = gameManagement.addOlympicSport(kindOfSport, discipline);

        if (!success) throw new GameManagementException("Olympic sport already exists");
        return "OK";
    }
    //endregion

    //region listOlympicSports

    /**
     * List all registered olympic sports
     *
     * @return List of olympic sports
     */
    @CommandInfo(command = "list-olympic-sports")
    public String listOlympicSports() {
        gameManagement.checkLogin();
        return gameManagement.listOlympicSports();
    }
    //endregion

    //region addIocCode

    /**
     * Adds a new Ioc Code to system
     *
     * @param iocId   unique ioc id
     * @param iocCode unique ioc code
     * @param country country of ioc membership
     * @param year    year of applience
     * @return OK if adding wass successful
     */
    @CommandInfo(command = "add-ioc-code (\\d{3});([a-z]{3});([^;\\n]+);(\\d{4})")
    public String addIocCode(
            @ParameterInfo(minValue = 1, maxValue = 999) int iocId, String iocCode, String country
            , @ParameterInfo(minValue = 0, maxValue = 2999) int year) {
        gameManagement.checkLogin();
        gameManagement.addIocCode(new IocMember(iocId, iocCode, country, year));
        return "OK";
    }
    //endregion

    //region listIocCodes

    /**
     * Lists all ioc codes
     *
     * @return list of all ioc codes
     */
    @CommandInfo(command = "list-ioc-codes")
    public String listIocCodes() {
        gameManagement.checkLogin();
        return gameManagement.listIocCodes();
    }
    //endregion

    //region addAthlete

    /**
     * Adds a new Athlete to system
     *
     * @param id          unique ID of Athlete
     * @param name        Name of athlete
     * @param surname     surname of athlete
     * @param country     country of origin
     * @param kindOfSport kind of sport Athlete applies
     * @param discipline  discipline which Athlete does
     * @return OK if adding was successful
     * @throws GameManagementException is thrown when
     *                                 1. the country does not exits in IOC Members
     *                                 2. Athlete already exists in system
     *                                 3. kindOfSport or descipline is not known in system
     */
    @CommandInfo(command = "add-athlete (\\d{4});([^;\\n]+);([^;\\n]+);([^;\\n]+);([^;\\n]+);([^;\\n]+)")
    public String addAthlete(@ParameterInfo(minValue = 1, maxValue = 9999) int id, String name, String surname
            , String country, String kindOfSport, String discipline) throws GameManagementException {
        gameManagement.checkLogin();
        boolean success = gameManagement.addAthlete(new Athlete(id, name, surname, country), kindOfSport, discipline);

        if (!success) throw new GameManagementException("Athlete is already connected to this discipline");
        return "OK";
    }
    //endregion

    //region summaryAthletes

    /**
     * Summary of all atlethes applied at giben kindOfSport and discipline
     *
     * @param kindOfSport kindOfSport to look for athletes
     * @param discipline  discipline of athletes
     * @return List of Athletes doing this sport
     * @throws GameManagementException is thrown when kindOfSport or discipline is not known to system
     */
    @CommandInfo(command = "summary-athletes ([^;\\n]+);([^;\\n]+)")
    public String summaryAthletes(String kindOfSport, String discipline) throws GameManagementException {
        gameManagement.checkLogin();
        return gameManagement.summaryAthlete(kindOfSport, discipline);
    }
    //endregion

    //region addCompetition

    /**
     * Adds a new competition
     *
     * @param id          unique Athlete id
     * @param year        year of competition
     * @param country     country of origin athlete
     * @param kindOfSport kindOfSport for Competition
     * @param discipline  discipline of competition
     * @param medals      medals won in competition
     * @return OK if adding was successful
     * @throws GameManagementException is thrown when there is any error with input data like unknown country etc.
     */
    @CommandInfo(
            command = "add-competition (\\d{4});(\\d{4});([^;\\n]+);([^;\\n]+);([^;\\n]+);(\\d{1});(\\d{1});(\\d{1})")
    public String addCompetition(@ParameterInfo(minValue = 1, maxValue = 9999) int id,
                                 @ParameterInfo(minValue = 1926, maxValue = 2018) int year, String country,
                                 String kindOfSport, String discipline
            , @ParameterInfo(arrayLenght = 3) Boolean[] medals)
            throws GameManagementException {
        gameManagement.checkLogin();

        if (year % 4 != 2) {
            throw new GameManagementException(
                    String.format("In Year %s there were no olympic games. Only every 4. year", year));
        }

        gameManagement.addCompetition(id, year, country, kindOfSport, discipline, medals);
        return "OK";
    }
    //endregion

    //region olympicMedalTable

    /**
     * Lists all olypmic medals for every IOC Member
     *
     * @return list of all medals and rankings
     */
    @CommandInfo(command = "olympic-medal-table")
    public String olympicMedalTable() {
        gameManagement.checkLogin();
        return gameManagement.olympicMedalTable();
    }
    //endregion
    //endregion

    //region reset

    /**
     * Resets all saved data exept AdminUsers and login status
     *
     * @return OK if reset was successful
     */
    @CommandInfo(command = "reset")
    public String reset() {
        gameManagement.checkLogin();
        gameManagement.reset();
        return "OK";
    }
    //endregion

    //region quit

    /**
     * Command for exiting process loop and leave program
     */
    @CommandInfo(command = "quit")
    public void quit() {
        this.quit = true;
    }
    //endregion

    //endregion
}
