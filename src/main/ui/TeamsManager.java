package ui;

import model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class TeamsManager {
    private ArrayList<Player> allPlayers; // list of all players
    private ArrayList<Team> allTeams;     // list of all teams
    private ArrayList<League> allLeagues; // list of all leagues
    private Scanner input;

    // EFFECTS: runs the teams manager application
    public TeamsManager() {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: initializes lists of all players, all teams and leagues
    public void init() {
        this.allPlayers = new ArrayList<>();
        this.allTeams = new ArrayList<>();
        this.allLeagues = new ArrayList<>();
        this.input = new Scanner(System.in);
    }

    // MODIFIES: this
    // EFFECTS: processes user input and determines whether to quit app
    public void runApp() {
        boolean quit = false;
        int command;

        init();

        while (!quit) {
            displayMainMenu();
            command = input.nextInt();
            if (command == 0) {
                quit = true;
            }
        }
        System.out.println("\nThank you for using LOL Teams Manager.");
    }

    public void displayMainMenu() {
        System.out.println("\nLOL TEAMS MANAGER");
        System.out.println("===========================");
        System.out.println("MAIN MENU");
        System.out.println("\nOptions:");
        System.out.println("        0. quit application");
        System.out.println("        1. View Leagues");
        System.out.println("        2. View all teams");
        System.out.println("        3. View all players");
    }

    //TODO: page to view leagues (with option to return to main menu) inserted at the front
    //TODO: page to view all players (with option to return to main menu) inserted at the front
    //TODO: page to view all teams (with option to return to main menu) inserted at the front
    //TODO: league viewing page (list of teams and players)
    //TODO: team viewing page (list of players)
    //TODO: player viewing page (stats)

    //TODO: find a way to add/remove players, teams, etc.

}
