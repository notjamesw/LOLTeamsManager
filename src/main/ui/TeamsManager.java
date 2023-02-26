package ui;

import model.*;

import java.util.ArrayList;
import java.util.Scanner;

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
    public void init() {
        this.allTeams = new ArrayList<>();
        this.allLeagues = new ArrayList<>();
        this.input = new Scanner(System.in);
    }

    // MODIFIES: this
    // EFFECTS: processes user input and determines whether to quit app
    public void runApp() {
        boolean quit = false;
        String command;

        init();

        while (!quit) {
            displayMainMenu();
            command = input.next();
            if (command.equals("q")) {
                quit = true;
            } else if (command.equals("1")) {
                allLeaguesMenu();
            } else if (command.equals("2")) {
                allTeamsMenu();
            } else {
                System.out.println("Please enter a valid command.");
            }
        }
        System.out.println("\nThank you for using LOL Teams Manager.");
    }

    // displays the title of the application
    public void displayTitle() {
        System.out.println("\nLOL TEAMS MANAGER");
        System.out.println("===========================");
    }

    // EFFECTS: display main menu with options to user
    public void displayMainMenu() {
        displayTitle();
        System.out.println("MAIN MENU");
        System.out.println("\nOptions:");
        System.out.println("        q. quit application");
        System.out.println("        1. View Leagues");
        System.out.println("        2. View all teams");
    }

    // MODIFIES: this
    // EFFECTS: processes user input, gives users the option to select a team from list of allTeams or create a new
    // team. 
    public void allTeamsMenu() {
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
                    //teamMenu(allTeams.get(index)); //TODO
                } else {
                    System.out.println("Please select a valid team.");
                }
            } else {
                System.out.println("Please enter a valid command.");
            }
        }
    }

    // EFFECTS: display a list of all teams
    public void displayAllTeamsMenu() {
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
    public void allLeaguesMenu() {
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
    public void displayAllLeaguesMenu() {
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
    public void createLeagueMenu() {
        displayTitle();
        System.out.println("LEAGUE CREATION");
        System.out.println("\nEnter a name for the new league:");
        String name = input.next();
        League league = new League(name);
        allLeagues.add(league);
    }

    // REQUIRES: league is a league in list of allLeagues
    // EFFECTS: processes user input and gives user the option to add a new team to the
    // league, or select a team to view the team.
    public void leagueMenu(League league) {
        boolean quit = false;
        String command;
        int index;

        while (!quit) {
            displayLeagueMenu(league);
            command = input.next();
            if (command.equals("q")) {
                quit = true;
            } else if (command.equals("c")) {
                createTeamMenu(league);
            } else if (!league.isEmpty()) {
                index = Integer.parseInt(command);
                if (index >= 0 && index < league.size()) {
                    //teamMenu(league.teamAtIndex(index)); //TODO
                } else {
                    System.out.println("Please select a valid team.");
                }
            } else {
                System.out.println("Please enter a valid command.");
            }
        }
    }

    // EFFECTS: displays a list of teams as well as their starters in a league, as well as options to the user
    public void displayLeagueMenu(League league) {
        displayTitle();
        System.out.println(league.getName());
        if (league.isEmpty()) {
            System.out.println("\nLeague has no teams yet.");
            System.out.println("To create a team and add it to the " + league.getName() + " enter 'c'");
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
        System.out.println("        c. create and add team to league");
    }

    // MODIFIES: this
    // EFFECTS: creates a new team with a user inputted name and adds it to a given league
    public void createTeamMenu(League league) {
        displayTitle();
        System.out.println("TEAM CREATION");
        System.out.println("Enter a team name:");
        String name = input.next();
        Team team = new Team(name);
        league.addTeam(team);
        allTeams.add(team);
    }

    // MODIFIES: this
    // EFFECTS: creates a new team with a user inputted name
    public void createTeamMenu() {
        displayTitle();
        System.out.println("TEAM CREATION");
        System.out.println("Enter a team name:");
        String name = input.next();
        Team team = new Team(name);
        allTeams.add(team);
    }

    //TODO: team viewing page (list of players), adding team to a league.
    //TODO: player viewing page (stats)
    //TODO: adding option to remove players, teams, etc.

}
