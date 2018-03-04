package de.plpt.CommandProcessor;

import de.plpt.ArgumentParser.*;
import de.plpt.olympicgames.GameManagement;
import de.plpt.olympicgames.exception.AuthorizeException;
import de.plpt.olympicgames.exception.GameManagementException;
import de.plpt.olympicgames.model.AdminUser;
import de.plpt.olympicgames.model.Athlete;
import de.plpt.olympicgames.model.IocCode;
import de.plpt.olympicgames.model.SportVenue;
import edu.kit.informatik.Terminal;

public class CommandProcessor {

    //region varDef
    private boolean quit = false;
    private ArgumentParser parser;
    private GameManagement gameManagement;
    //endregion

    //region constructor
    public CommandProcessor(String[] cliArgs) {
        this.parser = new ArgumentParser(this);
        this.gameManagement = new GameManagement();

    }
    //endregion

    //region processCommands
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
                Terminal.printError(String.format("[EX][%s] :: %s", x.getClass().getName(), x.getMessage()));
            }
            catch (IntervalViolationException x) {
                Terminal.printError(String.format("[EX][%s] :: %s", x.getClass().getName(), x.getMessage()));
            }
        }
    }
    //endregion

    //region Methods

    //region AdminManagement

    //region addAdmin
    @CommandInfo(command = "add-admin ([^;\\n]+);([^;\\n]+);([^;\\n]{4,8});([^;\\n]{8,12})")
    public String addAdmin(String name, String surname, String userName, String password)
            throws GameManagementException {
        gameManagement.addAdmin(new AdminUser(name, surname, userName, password));
        return "OK";
    }
    //endregion


    //region loginAdmin
    @CommandInfo(command = "login-admin ([^;\\n]{4,8});([^;\\n]{8,12})")
    public String loginAdmin(String userName, String password) {

        boolean success = gameManagement.loginAdmin(userName, password);

        if (!success) throw new AuthorizeException("Username or passwort incorrect. Try again");

        return "OK";
    }
    //endregion

    //region logoutAdmin
    @CommandInfo(command = "logout-admin")
    public String logoutAdmin() {
        gameManagement.logout();
        return "OK";
    }
    //endregion

    //endregion

    //region SportManagement

    //region addSportsVenue
    @CommandInfo(command = "add-sports-venue (\\d+);([^;\\n]+);([^;\\n]+);([^;\\n]+);(\\d+);(\\d++)")
    public String addSportsVenue(@ParameterInfo(minValue = 1, maxValue = 999) int id, String countryName, String place
            , String name, int openingYear, int numberSeats) throws GameManagementException {
        gameManagement.checkLogin();
        gameManagement.addSportVenue(new SportVenue(id, countryName, place, name, openingYear, numberSeats));
        return "OK";
    }
    //endregion

    //region listSportsVenues
    @CommandInfo(command = "list-sports-venues ([^;\\n]+)")
    public String listSportsVenues(String country) throws GameManagementException {
        gameManagement.checkLogin();
        return gameManagement.listSportVenues(country);
    }
    //endregion

    //region addOlympicSport
    @CommandInfo(command = "add-olympic-sport ([^;\\n]+);([^;\\n]+)")
    public String addOlympicSport(String kindOfSport, String discipline) throws GameManagementException {
        gameManagement.checkLogin();

        boolean success = gameManagement.addOlympicSport(kindOfSport, discipline);

        if (!success) throw new GameManagementException("Olympic sport already exists");
        return "OK";
    }
    //endregion

    //region listOlympicSports
    @CommandInfo(command = "list-olympic-sports")
    public String listOlympicSports() {
        gameManagement.checkLogin();
        return gameManagement.listOlympicSports();
    }
    //endregion

    //region addIocCode
    @CommandInfo(command = "add-ioc-code (\\d{3});([^;\\n]{3});([^;\\n]+);(\\d{4})")
    public String addIocCode(int iocId, String iocCode, String country, int year) {
        gameManagement.checkLogin();
        gameManagement.addIocCode(new IocCode(iocId, iocCode, country, year));
        return "OK";
    }
    //endregion

    //region listIocCodes
    @CommandInfo(command = "list-ioc-codes")
    public String listIocCodes() {
        gameManagement.checkLogin();
        return gameManagement.listIocCodes();
    }
    //endregion

    //region addAthlete
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
    @CommandInfo(command = "summary-athletes ([^;\\n]+)")
    public String summaryAthletes(String discipline) throws GameManagementException {
        gameManagement.checkLogin();
        return gameManagement.summaryAthlete(discipline);
    }
    //endregion

    //region addCompetition
    @CommandInfo(command = "add-competition (\\d{4});(\\d{4});([^;\\n]+);([^;\\n]+);(\\d{1});(\\d{1});(\\d{1})")
    public String addCompetition(@ParameterInfo(minValue = 1, maxValue = 9999) int id,
                                 @ParameterInfo(minValue = 1926, maxValue = 2018) int year, String country,
                                 String kindOfSport, String discipline, boolean gold, boolean solver, boolean bronze)
            throws GameManagementException {
        gameManagement.checkLogin();

        if (year % 4 != 2) {
            throw new GameManagementException(
                    String.format("In Year %s there were no olympic games. Only every 4. year", year));
        }

        gameManagement.addCompetition(id, year, country, kindOfSport, discipline, gold, solver, bronze);
        return "OK";
    }
    //endregion

    //region olympicMedalTable
    @CommandInfo(command = "olympic-medal-table")
    public String olympicMedalTable() throws GameManagementException {
        gameManagement.checkLogin();
        return gameManagement.olympicMedalTable();
    }
    //endregion
    //endregion

    //region reset
    @CommandInfo(command = "reset")
    public String reset() {
        gameManagement.checkLogin();
        gameManagement.reset();
        return "OK";
    }
    //endregion

    //region quit
    @CommandInfo(command = "quit")
    public void quit() {
        this.quit = true;
    }
    //endregion

    //endregion
}
