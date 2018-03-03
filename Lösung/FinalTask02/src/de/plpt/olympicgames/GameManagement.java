package de.plpt.olympicgames;

import de.plpt.olympicgames.exception.AuthorizeException;
import de.plpt.olympicgames.exception.GameManagementException;
import de.plpt.olympicgames.model.*;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameManagement {

    //region varDef
    private List<IocCode> iocCodeList = new ArrayList<IocCode>();
    private Map<String, List<String>> olympicSports = new TreeMap<>();
    private List<SportVenue> venues = new ArrayList<>();
    private AdminUser currentUser = null;
    private List<AdminUser> adminUsers = new ArrayList<>();
    private List<Athlete> athletes = new ArrayList<>();

    //endregion

    //region Methods
    //region AdminMethods

    //region addAdmin
    public void addAdmin(AdminUser adminUser) throws GameManagementException {
        if (!adminUsers.contains(adminUser)) {
            adminUsers.add(adminUser);
        } else {
            throw new GameManagementException(String.format("User with UserName %s already exists"
                    , adminUser.getUserName()));
        }
    }
    //endregion

    //region loginAdmin
    public boolean loginAdmin(String userName, String password) {


        Supplier<Stream<AdminUser>> userStream = () -> adminUsers.stream()
                .filter((a) -> a.getUserName().equals(userName) && a.getPassword().equals(password));

        if (userStream.get().count() <= 0) return false;
        else if (userStream.get().count() > 1) throw new AuthorizeException("More than one user found!");

        currentUser = userStream.get().findFirst().get();

        return true;
    }
    //endregion

    //region logout
    public boolean logout() {
        if (currentUser == null) throw new AuthorizeException("No user is logged in");
        currentUser = null;
        return true;
    }
    //endregion

    //region checkLogin
    public void checkLogin() {
        if (currentUser == null) {
            throw new AuthorizeException("Access denied, please login first");
        }
    }
    //endregion

    //endregion

    //region addIocCode
    public boolean addIocCode(IocCode code) {
        if (!iocCodeList.contains(code)) {
            iocCodeList.add(code);
            Collections.sort(iocCodeList);
        } else {
            throw new IllegalStateException("IOC Code %s is already known");
        }
        return true;
    }
    //endregion

    //region listIocCodes
    public String listIocCodes() {
        StringBuilder builder = new StringBuilder();

        for (IocCode code : iocCodeList) {
            builder.append(code.toString());
            builder.append("\n");
        }

        return builder.toString().trim();
    }
    //endregion

    //region addOlympicSport
    public boolean addOlympicSport(String kindOfSport, String discipline) {

        if (!olympicSports.containsKey(kindOfSport)) {
            olympicSports.put(kindOfSport, new ArrayList<>());
        }

        List<String> diciplines = olympicSports.get(kindOfSport);

        if (!diciplines.contains(discipline)) {
            diciplines.add(discipline);
            Collections.sort(diciplines);
        } else {
            return false;
        }


        return true;
    }
    //endregion

    //region listOlympicSports
    public String listOlympicSports() {
        StringBuilder builder = new StringBuilder();

        Iterator<Map.Entry<String, List<String>>> iterator = olympicSports.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, List<String>> sport = iterator.next();

            for (String discipline : sport.getValue()) {
                builder.append(String.format("%s %s", sport.getKey(), discipline));
                builder.append("\n");
            }

        }

        return builder.toString().trim();
    }
    //endregion

    //region addSportVenue
    public boolean addSportVenue(SportVenue venue) {
        venues.add(venue);
        Collections.sort(venues);
        return true;
    }
    //endregion

    //region listSportVenues
    public String listSportVenues(String country) {
        StringBuilder builder = new StringBuilder();
        int idx = 1;
        List<SportVenue> filteredVenues = venues.stream()
                .filter(v -> v.getCountryName().equals(country)).collect(Collectors.toList());

        for (SportVenue venue : filteredVenues) {
            builder.append(venue.toString(idx));
            idx++;
            builder.append("\n");
        }
        return builder.toString().trim();
    }
    //endregion

    //region addAthlete
    public boolean addAthlete(Athlete athlete, String kindOfSport, String discipline)
            throws GameManagementException {

        if (!iocCodeList.stream().anyMatch(ioc -> ioc.getCountryName().equals(athlete.getCountry()))) {
            throw new GameManagementException(String.format("Country '%s' is not registered from IOC"
                    , athlete.getCountry()));
        }

        Athlete currentAthlete = null;
        if (!athletes.contains(athlete)) {
            athletes.add(athlete);
            Collections.sort(athletes);
            currentAthlete = athlete;
        } else {
            currentAthlete = athletes.stream().filter(a -> a.equals(athlete)).findFirst().get();
        }

        boolean success = currentAthlete.addSport(kindOfSport, discipline);

        return success;
    }
    //endregion

    //region summaryAthlete
    public String summaryAthlete(String discipline) throws GameManagementException {
        StringBuilder builder = new StringBuilder();

        if (!olympicSports.values().stream().anyMatch(l -> l.contains(discipline))) {
            throw new GameManagementException(String.format("Discipline '%s' is not registered in system", discipline));
        }

        List<Athlete> filteredList = athletes.stream().filter(a -> a.getDisciplines().contains(discipline))
                .collect(Collectors.toList());

        for (Athlete a : filteredList) {
            builder.append(a.toString());
            builder.append("\n");
        }

        return builder.toString().trim();
    }
    //endregion

    //region addCompetition
    public boolean addCompetition(int id, int year, String countryName, String kindOfSport, String discipline
            , boolean gold, boolean silver, boolean bronze) throws GameManagementException {

        if (!olympicSports.containsKey(kindOfSport)) {
            throw new GameManagementException(
                    String.format("Type of sport '%s' is not registered in system", kindOfSport));
        }

        if (!olympicSports.values().stream().anyMatch(l -> l.contains(discipline))) {
            throw new GameManagementException(String.format("Discipline '%s' is not registered in system", discipline));
        }


        Optional<Athlete> optionalAthlete = athletes.stream().filter(a -> a.getId() == id).findFirst();
        if (!optionalAthlete.isPresent()) {
            throw new GameManagementException(String.format("No athlete with id '%s' found", id));
        }
        Athlete athlete = optionalAthlete.get();

        if (!athlete.getCountry().equals(countryName)) {
            throw new GameManagementException(
                    String.format("Athlete with id '%s' is not in country '%s' but in country '%s'", id, countryName
                            , athlete.getCountry()));
        }

        if (!athlete.getSportTypes().contains(kindOfSport)) {
            throw new GameManagementException(String.format("Athlete with id '%s' does not do '%s' but does '%s'"
                    , id, kindOfSport, String.join(";", athlete.getSportTypes())));
        }

        if (!athlete.getDisciplines().contains(discipline)) {
            throw new GameManagementException(
                    String.format("Athlete with id '%s' does not interact in discipline '%s' but in  '%s'", id
                            , discipline, String.join(",", athlete.getDisciplines())));
        }


        athlete.addCompetition(new Competition(year, gold, silver, bronze, kindOfSport, discipline));


        return true;
    }
    //endregion

    //region olympicMedalTable
    public String olympicMedalTable() throws GameManagementException {

        if (athletes.size() == 0) throw new GameManagementException("No athletes added to system");

        List<MedalTableItem> medalTableItemList = new ArrayList<>();

        for (IocCode ioc : iocCodeList) {

            List<Athlete> connectedAthletes = athletes.stream().filter(a -> a.getCountry()
                                                .equals(ioc.getCountryName())).collect(Collectors.toList());
            if (connectedAthletes.size() == 0) continue;
            MedalTableItem item = new MedalTableItem(ioc, connectedAthletes);
            medalTableItemList.add(item);
        }

        Collections.sort(medalTableItemList);

        int place = 1;
        StringBuilder builder = new StringBuilder();
        for (MedalTableItem item : medalTableItemList) {
            builder.append(item.toString(place));
            place++;
            builder.append("\n");
        }

        return builder.toString().trim();
    }
    //endregion

    //region reset
    public void reset() {

        athletes.clear();
        iocCodeList.clear();
        this.olympicSports.clear();
        this.venues.clear();
        adminUsers.clear();
        logout();
    }
    //endregion
    //endregions

}
