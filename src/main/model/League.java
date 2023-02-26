package model;

import java.util.ArrayList;

// Represents a competitive region/league with a name and a list of teams
public class League {
    private ArrayList<Team> teams;
    private String name;

    // EFFECTS: constructs a competitive region/league with a name and an empty list of teams
    public League(String name) {
        this.name = name;
        this.teams = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setName(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: adds a team to the league
    public void addTeam(Team team) {
        teams.add(team);
    }

    // REQUIRES: teams is not empty
    // MODIFIES: this
    // EFFECTS: remove a team from the league
    public void removeTeam(Team team) {
        teams.remove(team);
    }

    // EFFECTS: returns true if there are no teams in the league and false if otherwise
    public boolean isEmpty() {
        return teams.isEmpty();
    }

    // REQUIRES: teams is not empty and 0 <= index <= teams.size()
    // EFFECTS: returns the team at the given index in the league
    public Team teamAtIndex(int index) {
        return teams.get(index);
    }

    // EFFECTS: returns the number of teams in the league
    public int size() {
        return teams.size();
    }
}
