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

        if(currentUser!=null){
            throw new AuthorizeException(String.format("User '%s' is logged in. Please log out first."));

        }

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

        if (currentUser != null)
            throw new AuthorizeException(String.format("The user '%s' is already logged in!", currentUser.getUserName()));

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

      boolean iocExists =  iocCodeList.stream().anyMatch(ioc -> code.getIocId() == ioc.getIocId() || code.getIocCode().equals(ioc.getIocCode()) || code.getCountryName().equals(ioc.getCountryName()));

        if (!iocCodeList.contains(code) && !iocExists ) {
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

        String result = builder.toString().trim();
        return result.length()>0 ? result : null;
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
        String result = builder.toString().trim();
        return result.length()>0 ? result : null;
    }
    //endregion

    //region addSportVenue
    public boolean addSportVenue(SportVenue venue) throws GameManagementException {

        if(!iocCodeList.stream().anyMatch(i -> i.getCountryName().equals(venue.getCountryName()))){
            throw new GameManagementException(String.format("Country '%s' is not an IOC known country",venue.getCountryName()));
        }

        if (!venues.contains(venue)) {
            venues.add(venue);
        } else {
            throw new GameManagementException("The given venue is already known in system");
        }
        Collections.sort(venues);
        return true;
    }
    //endregion

    //region listSportVenues
    public String listSportVenues(String country) throws GameManagementException {
        StringBuilder builder = new StringBuilder();
        int idx = 1;
        List<SportVenue> filteredVenues = venues.stream()
                .filter(v -> v.getCountryName().equals(country)).collect(Collectors.toList());

        if (filteredVenues.size() == 0) throw new GameManagementException(
                String.format("There are no venues belongs to country %s", country));

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

        if(!olympicSports.containsKey(kindOfSport)){
            throw new GameManagementException(String.format("Sport type '%s' is not exists"
                    , kindOfSport));
        }

        if(!olympicSports.get(kindOfSport).contains(discipline)){
            throw new GameManagementException(String.format("Sport type '%s' under '%s' does not exists"
                    , kindOfSport,discipline));
        }

        boolean exists = athletes.stream().anyMatch(a -> a.getId() == athlete.getId());

        Athlete currentAthlete = null;
        if (!athletes.contains(athlete) && !exists) {
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
    public String summaryAthlete(String kindOfSport,String discipline) throws GameManagementException {
        StringBuilder builder = new StringBuilder();

        if(!olympicSports.containsKey(kindOfSport)){
            throw new GameManagementException(String.format("Discipline '%s' is not registered in system", kindOfSport));
        }

        if (!olympicSports.get(kindOfSport).contains(discipline)) {
            throw new GameManagementException(String.format("Discipline '%s' is not registered in system", discipline));
        }

        List<Athlete> filteredList = athletes.stream().filter(a -> a.getDisciplines().contains(discipline) && a.getSportTypes().contains(kindOfSport))
                .sorted((Athlete o1, Athlete o2)->{ int diff = Long.compare(o2.getCompetitions().stream().filter(c -> c.getKindOfSport().equals(kindOfSport) && c.getDiscipline().equals(discipline) && c.hasMedal()).count(),o1.getCompetitions().stream().filter(c -> c.getKindOfSport().equals(kindOfSport) && c.getDiscipline().equals(discipline) && c.hasMedal()).count()); if(diff!=0)return diff; else return Integer.compare(o1.getId(),o2.getId());}).collect(Collectors.toList());

        for (Athlete a : filteredList) {
            builder.append(   String.format("%04d %s %s %s", a.getId(), a.getName(), a.getSurname(), a.getCompetitions().stream().filter(c -> c.hasMedal() && c.getKindOfSport().equals(kindOfSport) && c.getDiscipline().equals(discipline)).count()));
            builder.append("\n");
        }
        String result = builder.toString().trim();

        return result.length()>0  ? result : null;
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


        if((!((bronze^silver)^gold) || bronze&&silver&&gold ) && !(!bronze&&!silver&&!gold)){
            throw new GameManagementException(
                    String.format("Athlete with id '%s' caon only win one medal per Competition",id));
        }

        Competition comp = new Competition(year, gold, silver, bronze, kindOfSport, discipline);

        if(athlete.getCompetitions().contains(comp)){
            throw new GameManagementException(
                    String.format("Athlete with id '%s' has already a competition for year '%s'  in discipline '%s'", id,
                            year,kindOfSport + " " +  discipline));
        }

        athlete.addCompetition(comp);


        return true;
    }
    //endregion

    //region olympicMedalTable
    public String olympicMedalTable() throws GameManagementException {



        List<MedalTableItem> medalTableItemList = new ArrayList<>();

        for (IocCode ioc : iocCodeList) {

            List<Athlete> connectedAthletes = athletes.stream().filter(a -> a.getCountry()
                    .equals(ioc.getCountryName())).collect(Collectors.toList());

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
        String result = builder.toString().trim();
        return result.length()>0 ? result : null;
    }
    //endregion

    //region reset
    public void reset() {

        athletes.clear();
        iocCodeList.clear();
        this.olympicSports.clear();
        this.venues.clear();

    }
    //endregion
    //endregions

}
