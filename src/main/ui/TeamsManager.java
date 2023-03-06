package ui;

import model.*;

import java.util.ArrayList;
import java.util.Scanner;

// Represents an LOL team manager application
public class TeamsManager {
    private ArrayList<Team> allTeams;     // list of all teams
    private ArrayList<League> allLeagues; // list of all leagues
    private Scanner input;

    // EFFECTS: runs the teams manager application
    public TeamsManager() {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: initializes lists of all teams and leagues
    private void init() {
        this.allTeams = new ArrayList<>();
        this.allLeagues = new ArrayList<>();
        this.input = new Scanner(System.in);
    }

    // MODIFIES: this
    // EFFECTS: processes user input and determines whether to quit app
    private void runApp() {
        boolean quit = false;
        String command;

        init();

        while (!quit) {
            displayMainMenu();
            command = input.next();
            if (command.equals("q")) {
                quit = true;
            } else if (command.equals("a")) {
                allLeaguesMenu();
            } else if (command.equals("b")) {
                allTeamsMenu();
            } else {
                System.out.println("Please enter a valid command.");
            }
        }
        System.out.println("\nThank you for using LOL Teams Manager.");
    }

    // EFFECTS: displays the title of the application
    private void displayTitle() {
        System.out.println("\nLOL TEAMS MANAGER");
        System.out.println("===========================");
    }

    // EFFECTS: display main menu with options to user
    private void displayMainMenu() {
        displayTitle();
        System.out.println("MAIN MENU");
        System.out.println("\nOptions:");
        System.out.println("        q. quit application");
        System.out.println("        a. View Leagues");
        System.out.println("        b. View all teams");
    }

    // MODIFIES: this
    // EFFECTS: processes user input, gives users the option to select a team from list of allTeams or create a new
    // team. 
    private void allTeamsMenu() {
        boolean quit = false;
        String command;
        int index;

        while (!quit) {
            displayAllTeamsMenu();
            command = input.next();
            if (command.equals("q")) {
                quit = true;
            } else if (command.equals("c")) {
                createTeamMenu();
            } else if (!allTeams.isEmpty()) {
                index = Integer.parseInt(command);
                if (index >= 0 && index < allTeams.size()) {
                    accessTeamMenu(allTeams.get(index));
                } else {
                    System.out.println("Please select a valid team.");
                }
            } else {
                System.out.println("Please enter a valid command.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: allows teams with no league to access the team menu
    private void accessTeamMenu(Team team) {
        boolean noLeague = true;
        for (League league : allLeagues) {
            if (league.getName().equals(team.getLeague())) {
                teamMenu(team, league);
                noLeague = false;
            }
        }
        if (noLeague) {
            teamMenu(team, null);
        }
    }

    // EFFECTS: display a list of all teams
    private void displayAllTeamsMenu() {
        displayTitle();
        System.out.println("ALL TEAMS");
        if (allTeams.isEmpty()) {
            System.out.println("\nNo teams have been created yet.");
            System.out.println("To create a team enter 'c'");
        } else {
            int i = 0;
            System.out.println("\nTo select a team and view its information, enter its index.");
            for (Team team : allTeams) {
                System.out.println(i + ". " + team.getTeamName());
                i++;
            }
        }
        System.out.println("\nOptions:");
        System.out.println("        q. quit and return to Main Menu");
        System.out.println("        c. create team");
    }

    // MODIFIES: this
    // EFFECTS: processes user input, gives users the option to add a new league or select a league
    // to view the teams in the league
    private void allLeaguesMenu() {
        boolean quit = false;
        String command;
        int index;

        while (!quit) {
            displayAllLeaguesMenu();
            command = input.next();
            if (command.equals("q")) {
                quit = true;
            } else if (command.equals("c")) {
                createLeagueMenu();
            } else if (!allLeagues.isEmpty()) {
                index = Integer.parseInt(command);
                if (index >= 0 && index < allLeagues.size()) {
                    leagueMenu(allLeagues.get(index));
                } else {
                    System.out.println("Please select a valid league.");
                }
            } else {
                System.out.println("Please enter a valid command.");
            }
        }
    }

    // EFFECTS: displays menu with all the leagues and options to the user
    private void displayAllLeaguesMenu() {
        displayTitle();
        System.out.println("LEAGUES");
        if (allLeagues.isEmpty()) {
            System.out.println("\nNo leagues have been created yet.");
            System.out.println("To create a league, please enter 'c'.");
        } else {
            int i = 0;
            System.out.println("\nTo select a league and view its information, enter its index.");
            for (League league : allLeagues) {
                System.out.println(i + ". " + league.getName());
                i++;
            }
        }
        System.out.println("\nOptions:");
        System.out.println("        q. quit and return to Main Menu");
        System.out.println("        c. create a League");
    }

    // MODIFIES: this
    // EFFECTS: creates and adds a new league with a name and no teams to the list of all leagues
    private void createLeagueMenu() {
        displayTitle();
        System.out.println("LEAGUE CREATION");
        System.out.println("\nEnter a name for the new league:");
        String name = input.next();
        League league = new League(name);
        allLeagues.add(league);
    }

    // REQUIRES: league is a league in list of allLeagues
    // MODIFIES: this, league
    // EFFECTS: processes user input and gives user the option to add a new team to the
    // league, or select a team to view the team.
    private void leagueMenu(League league) {
        boolean quit = false;
        String command;
        int index;

        while (!quit) {
            displayLeagueMenu(league);
            command = input.next();
            if (command.equals("q")) {
                quit = true;
            } else if (command.equals("a")) {
                createTeamMenu(league);
            } else if (command.equals("b")) {
                renameLeague(league);
            } else if (command.equals("c")) {
                if (removeLeague(league)) {
                    quit = true;
                }
            } else if (!league.isEmpty()) {
                index = Integer.parseInt(command);
                if (index >= 0 && index < league.size()) {
                    teamMenu(league.teamAtIndex(index), league);
                }
            }
        }
    }

    // MODIFIES: this, league
    // EFFECTS: rename the given league
    private void renameLeague(League league) {
        System.out.println("Enter a new name for the league:");
        String name = input.next();
        league.setName(name);
    }

    // MODIFIES: this, league
    // EFFECTS: removes the league from allLeagues
    private boolean removeLeague(League league) {
        displayTitle();
        System.out.println("REMOVE LEAGUE");
        String teamName = league.getName();
        System.out.println(league.getName() + " will be removed.");
        System.out.println("To confirm, enter 'y'.");
        String confirm = input.next();
        if (confirm.equals("y")) {
            allLeagues.remove(league);
            return true;
        } else {
            System.out.println(teamName + " has not been disbanded.");
            return false;
        }
    }

    // EFFECTS: displays a list of teams as well as their starters in a league, as well as options to the user
    private void displayLeagueMenu(League league) {
        displayTitle();
        System.out.println(league.getName());
        if (league.isEmpty()) {
            System.out.println("\nLeague has no teams yet.");
            System.out.println("To create a team and add it to the " + league.getName() + " enter 'a'");
        } else {
            int i = 0;
            System.out.println("\nTo select a team and view its information, enter its index.");
            for (Team team : league.getTeams()) {
                System.out.println(i + ". " + team.getTeamName());
                for (Player player : team.getStarters()) {
                    System.out.println(player.getRole() + " " + player.getIgn());
                }
                i++;
            }
        }
        System.out.println("\nOptions:");
        System.out.println("        q. quit and return to Leagues Menu");
        System.out.println("        a. create and add team to league");
        System.out.println("        b. rename the league");
        System.out.println("        c. remove the league");
    }

    // MODIFIES: this, league
    // EFFECTS: creates a new team with a user inputted name and adds it to a given league
    private void createTeamMenu(League league) {
        displayTitle();
        System.out.println("TEAM CREATION");
        System.out.println("Enter a team name:");
        String name = input.next();
        Team team = new Team(name, league.getName());
        league.addTeam(team);
        allTeams.add(team);
    }

    // MODIFIES: this
    // EFFECTS: creates a new team with a user inputted name
    private void createTeamMenu() {
        displayTitle();
        System.out.println("TEAM CREATION");
        System.out.println("Enter a team name:");
        String name = input.next();
        Team team = new Team(name, "No league");
        allTeams.add(team);
    }

    // MODIFIES: this, team, league
    // EFFECTS: processes user input and gives user options to manage starters or subs on a team, or manage team
    // information.
    private void teamMenu(Team team, League league) {
        boolean quit = false;
        String command;

        while (!quit) {
            displayTeamMenu(team);
            command = input.next();
            if (command.equals("q")) {
                quit = true;
            } else if (command.equals("a")) {
                startersMenu(team);
            } else if (command.equals("b")) {
                subsMenu(team);
            } else if (command.equals("c")) {
                if (manageTeam(team, league)) {
                    quit = true;
                }
            } else {
                System.out.println("Please enter a valid command.");
            }
        }
    }

    // EFFECTS: displays the team menu
    private void displayTeamMenu(Team team) {
        displayTitle();
        System.out.println(team.getTeamName());
        System.out.println("Starters:");
        for (Player player : team.getStarters()) {
            System.out.println(player.getRole() + " " + player.getIgn());
        }
        System.out.print("Number of subs: " + team.numberOfSubs());
        System.out.println("\nOptions:");
        System.out.println("        q. quit and return to League Menu");
        System.out.println("        a. manage starters");
        System.out.println("        b. manage subs");
        System.out.println("        c. manage team");
    }

    // MODIFIES: this, team, league
    // EFFECTS: processes user input and gives user options to rename team, delete team, release all players from the
    // team, or change the team's current league.
    private boolean manageTeam(Team team, League league) {
        boolean quit = false;
        String command;

        while (!quit) {
            displayManageTeam();
            command = input.next();
            if (command.equals("q")) {
                quit = true;
            } else if (command.equals("a")) {
                renameTeam(team);
            } else if (command.equals("b")) {
                if (deleteTeam(team, league)) {
                    return true;
                }
            } else if (command.equals("c")) {
                clearTeam(team);
            } else if (command.equals("d")) {
                changeTeamLeague(team, league);
            } else {
                System.out.println("Please enter a valid command.");
            }
        }
        return false;
    }

    // EFFECTS: displays manage team menu
    private void displayManageTeam() {
        displayTitle();
        System.out.println("MANAGE TEAM MENU");
        System.out.println("\nOptions:");
        System.out.println("        q. quit and return to Team Menu");
        System.out.println("        a. rebrand team");
        System.out.println("        b. disband team");
        System.out.println("        c. release all players from the team");
        System.out.println("        d. add/change team's league");
    }

    // MODIFIES: this, team, league
    // EFFECTS: changes team's league to user inputted league if it's found in allLeagues.
    private void changeTeamLeague(Team team, League league) {
        boolean foundLeague = false;
        if (league != null) {
            league.removeTeam(team);
        }
        System.out.println("Enter a new league for the team: ");
        String newLeague = input.next();
        for (League leg : allLeagues) {
            if (leg.getName().equals(newLeague)) {
                foundLeague = true;
                leg.addTeam(team);
                team.setLeague(leg.getName());
            }
        }
        if (!foundLeague) {
            System.out.println("New league not found.");
        }
    }

    // MODIFIES: this, team
    // EFFECTS: processes user input and gives user options to manage starters.
    private void startersMenu(Team team) {
        boolean quit = false;
        String command;
        int index;

        while (!quit) {
            displayStarters(team);
            command = input.next();
            if (command.equals("q")) {
                quit = true;
            } else if (command.equals("a")) {
                addStarter(team);
            } else if (command.equals("b")) {
                removeStarter(team);
            } else if (!team.startersIsEmpty()) {
                index = Integer.parseInt(command);
                if (index >= 0 && index < team.getStarters().size()) {
                    playerMenu(team.getStarters().get(index));
                } else {
                    System.out.println("Please select a valid player.");
                }
            } else {
                System.out.println("Please enter a valid command.");
            }
        }
    }

    // EFFECTS: displays starters menu
    private void displayStarters(Team team) {
        displayTitle();
        System.out.println("MANAGE STARTERS");
        int i = 0;
        if (team.startersIsEmpty()) {
            System.out.println("No starters have been added.");
        } else {
            System.out.println("\nTo select a player and view their information, enter their index.");
            for (Player player : team.getStarters()) {
                System.out.println(i + ". " + player.getRole() + " " + player.getIgn());
                i++;
            }
        }
        System.out.println("\nOptions:");
        System.out.println("        q. quit and return to Team Menu");
        System.out.println("        a. add a starter");
        System.out.println("        b. remove a starter");
    }

    // MODIFIES: team
    // EFFECTS: adds a new starter to the to team if starting roster is not full.
    private void addStarter(Team team) {
        System.out.println("ADD STARTER");
        if (team.startersIsFull()) {
            System.out.println("Starting roster is full, cannot add a starter.");
        } else {
            team.addStarter(playerCreation(team));
            System.out.println("Starter has been added.");
            if (team.startersIsFull()) {
                team.sortStarters();
            }
        }
    }

    // MODIFIES: team
    // EFFECTS: removes a starter from the team if starters is not empty
    private void removeStarter(Team team) {
        if (team.startersIsEmpty()) {
            System.out.println("There are no starters to remove.");
        } else {
            System.out.println("To remove a starter, enter the index of the starter to be removed:");
            int index = input.nextInt();
            if (index >= 0 && index < team.getStarters().size()) {
                team.removeStarter(index);
            } else {
                System.out.println("Invalid index, no starter is removed.");
            }
        }
    }

    // MODIFIES: this, team
    // EFFECTS: processes user input and gives user options to manage subs.
    private void subsMenu(Team team) {
        boolean quit = false;
        String command;
        int index;

        while (!quit) {
            displaySubs(team);
            command = input.next();
            if (command.equals("q")) {
                quit = true;
            } else if (command.equals("a")) {
                addSub(team);
            } else if (command.equals("b")) {
                removeSub(team);
            } else if (!team.subsIsEmpty()) {
                index = Integer.parseInt(command);
                if (index >= 0 && index < team.numberOfSubs()) {
                    playerMenu(team.getSubs().get(index));
                } else {
                    System.out.println("Please select a valid player.");
                }
            } else {
                System.out.println("Please enter a valid command.");
            }
        }
    }

    // EFFECTS: displays subs menu
    private void displaySubs(Team team) {
        displayTitle();
        System.out.println("MANAGE SUBS");
        int i = 0;
        if (team.subsIsEmpty()) {
            System.out.println("No subs have been added.");
        } else {
            System.out.println("\nTo select a player and view their information, enter their index.");
            for (Player player : team.getSubs()) {
                System.out.println(i + ". " + player.getRole() + " " + player.getIgn());
                i++;
            }
        }
        System.out.println("\nOptions:");
        System.out.println("        q. quit and return to Team Menu");
        System.out.println("        a. add a sub");
        System.out.println("        b. remove a sub");
    }

    // MODIFIES: team
    // EFFECTS: adds a new sub to the team
    private void addSub(Team team) {
        System.out.println("ADD SUB");
        team.addSub(playerCreation(team));
        System.out.println("Sub has been added.");
    }

    // MODIFIES: team
    // EFFECTS: removes a sub from the team if subs is not empty.
    private void removeSub(Team team) {
        if (team.subsIsEmpty()) {
            System.out.println("There are no subs to remove.");
        } else {
            System.out.println("To remove a sub, enter the index of the sub to be removed:");
            int index = input.nextInt();
            if (index >= 0 && index < team.numberOfSubs()) {
                team.removeSub(index);
            } else {
                System.out.println("Invalid index, no sub is removed.");
            }
        }
    }

    // MODIFIES: this, team
    // EFFECTS: renames team to a new user inputted name
    private void renameTeam(Team team) {
        displayTitle();
        System.out.println("RENAME TEAM");
        System.out.println("Enter a new name for the team:");
        String oldName = team.getTeamName();
        String name = input.next();
        team.setTeamName(name);
        System.out.println(oldName + " has been rebranded to " + team.getTeamName());
    }

    // MODIFIES: this, team, league
    // EFFECTS: after confirmation, clears all players from the team and removes the team from the league
    // and list of allTeams
    private boolean deleteTeam(Team team, League league) {
        displayTitle();
        System.out.println("DISBAND TEAM");
        String teamName = team.getTeamName();
        System.out.println(team.getTeamName() + " will be disbanded.");
        System.out.println("To confirm, enter 'y'.");
        String confirm = input.next();
        if (confirm.equals("y")) {
            team.clearAllPlayers();
            allTeams.remove(team);
            if (league != null) {
                league.removeTeam(team);
            }
            System.out.println(teamName + " has been disbanded.");
            return true;
        } else {
            System.out.println(teamName + " has not been disbanded.");
            return false;
        }
    }

    // MODIFIES: this, team
    // EFFECTS: after confirmation, clears all players from the team
    private void clearTeam(Team team) {
        displayTitle();
        System.out.println("RELEASE ALL PLAYERS");
        System.out.println("All players will be released from " + team.getTeamName());
        System.out.println("To confirm, enter 'y'.");
        String confirm = input.next();
        if (confirm.equals("y")) {
            team.clearAllPlayers();
            System.out.println("All players have been released.");
        } else {
            System.out.println("Players have not been released.");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a player, either pro or amateur based on user input.
    private Player playerCreation(Team team) {
        Player player;
        String command;
        do {
            displayPlayerCreation();
            command = input.next();
        } while (!(command.equals("a") || command.equals("b")));
        System.out.println("Enter the player's ign:");
        String ign = input.next();
        System.out.println("Enter the player's role:");
        String role = input.next();
        if (command.equals("a")) {
            player = new ProPlayer(ign, role, team.getLeague());
        } else {
            System.out.println("Enter the player's rank:");
            String rank = input.next();
            player = new AmateurPlayer(ign, role, rank);
        }
        return player;
    }

    // EFFECTS: display player creation menu
    private void displayPlayerCreation() {
        displayTitle();
        System.out.println("PLAYER CREATION");
        System.out.println("\nOptions:");
        System.out.println("        a. create a Pro-player");
        System.out.println("        b. create an Amateur player");
    }

    // MODIFIES: this, player
    // EFFECTS: processes user input and gives user options to manage player information and stats.
    private void playerMenu(Player player) {
        boolean quit = false;
        String command;

        while (!quit) {
            displayPlayerInformation(player);
            displayPlayerOptions();
            command = input.next();
            if (command.equals("q")) {
                quit = true;
            } else if (command.equals("a")) {
                editDescription(player);
            } else if (command.equals("b")) {
                editStats(player);
            } else {
                System.out.println("Please enter a valid command.");
            }
        }
    }

    // EFFECTS: displays player information
    private void displayPlayerInformation(Player player) {
        displayTitle();
        System.out.println(player.getIgn());
        System.out.println(player.getRole());
        if (player instanceof ProPlayer) {
            System.out.println("PROFESSIONAL PLAYER");
            System.out.println("Statistics: ");
            System.out.println("CS per Min: " + ((ProPlayer) player).getCsm());
            System.out.println("KDA: " + ((ProPlayer) player).getKda());
        } else if (player instanceof AmateurPlayer) {
            System.out.println("CASUAL/AMATEUR PLAYER");
            System.out.println("Rank: " + ((AmateurPlayer) player).getRank());
            System.out.println("Statistics: ");
        }
        System.out.println("Wins: " + player.getWins());
        System.out.println("Loses: " + player.getLoses());
        if (player.getWinRate() == -1) {
            System.out.println("Cannot calculate win rate, no games played.");
        } else {
            System.out.printf("Win rate: %.2f", player.getWinRate());
        }
        System.out.println("\nAdditional information: " + player.getDescription());
    }

    // EFFECTS: display player options menu
    private void displayPlayerOptions() {
        System.out.println("\nOptions:");
        System.out.println("        q. quit and return to Roster Menu");
        System.out.println("        a. edit player description");
        System.out.println("        b. edit player stats");
    }

    // MODIFIES: this, player
    // EFFECTS: processes user input and gives user option to change player description, ign, role, or rank
    // if the player is an AmateurPlayer.
    private void editDescription(Player player) {
        boolean quit = false;
        String command;

        while (!quit) {
            displayDescriptionMenu(player);
            command = input.next();
            input.nextLine();
            if (command.equals("q")) {
                quit = true;
            } else if (command.equals("a")) {
                changeDescription(player);
            } else if (command.equals("b")) {
                changeIgn(player);
            } else if (command.equals("c")) {
                changeRole(player);
            } else if (player instanceof AmateurPlayer && command.equals("d")) {
                changeRank((AmateurPlayer) player);
            }
        }
    }

    // MODIFIES: this, player
    // EFFECTS: changes player's description.
    private void changeDescription(Player player) {
        System.out.println("Enter new player description:");
        String newInfo = input.nextLine();
        player.setDescription(newInfo);
    }

    // MODIFIES: this, player
    // EFFECTS: changes player's rank.
    private void changeRank(AmateurPlayer player) {
        System.out.println("Enter new player rank:");
        String rank = input.next();
        player.setRank(rank);
    }

    // MODIFIES: this, player
    // EFFECTS: changes player's ign to user input
    private void changeIgn(Player player) {
        System.out.println("Enter new player ign:");
        String ign = input.next();
        player.setIgn(ign);
    }

    // MODIFIES: this, player
    // EFFECTS: changes player's role to user input
    private void changeRole(Player player) {
        System.out.println("Enter new player role:");
        String role = input.next();
        player.setRole(role);
    }

    // EFFECTS: displays description menu
    private void displayDescriptionMenu(Player player) {
        displayTitle();
        System.out.println("\nOptions:");
        System.out.println("        q. quit and return to Player Menu");
        System.out.println("        a. edit player description");
        System.out.println("        b. change ign");
        System.out.println("        c. change role");
        if (player instanceof AmateurPlayer) {
            System.out.println("        d. edit player rank");
        }
    }

    // MODIFIES: this, player
    // EFFECTS: processes user input and gives user option to change player stats
    private void editStats(Player player) {
        boolean quit = false;
        String command;
        int newStat;

        while (!quit) {
            displayStatsMenu(player);
            command = input.next();
            if (command.equals("q")) {
                quit = true;
            } else if (command.equals("a")) {
                System.out.println("Enter updated wins:");
                newStat = input.nextInt();
                player.setWins(newStat);
            } else if (command.equals("b")) {
                System.out.println("Enter updated loses:");
                newStat = input.nextInt();
                player.setLoses(newStat);
            } else if (player instanceof ProPlayer) {
                proStatOptions((ProPlayer) player, command);
            } else {
                System.out.println("Please enter a valid command.");
            }
        }
    }

    // EFFECTS: display stats menu
    private void displayStatsMenu(Player player) {
        displayTitle();
        System.out.println("\nOptions:");
        System.out.println("        q. quit and return to Player Menu");
        System.out.println("        a. update wins");
        System.out.println("        b. update loses");
        if (player instanceof ProPlayer) {
            System.out.println("        c. update csm");
            System.out.println("        d. update KDA");
        }
    }

    // MODIFIES: this, player
    // EFFECTS: processes user input and gives user option to change pro player specific stats.
    private void proStatOptions(ProPlayer player, String command) {
        double csm;
        double kda;
        if (command.equals("c")) {
            System.out.println("Enter updated CS per Min: ");
            csm = input.nextDouble();
            player.setCsm(csm);
        } else if (command.equals("d")) {
            System.out.println("Enter updated KDA: ");
            kda = input.nextDouble();
            player.setKda(kda);
        } else {
            System.out.println("Please enter a valid command.");
        }
    }
}
