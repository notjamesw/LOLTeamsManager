package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a competitive region/league with a name and a list of teams
public class League implements Writable {
    private ArrayList<Team> teams;
    private String name;

    // REQUIRES: name of league must not be the same as a pre-existing league in allLeagues
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
        EventLog.getInstance().logEvent(new Event("Added team " + team.getTeamName() + " to " + this.name));
    }

    // REQUIRES: teams is not empty, teams contains team
    // MODIFIES: this
    // EFFECTS: remove a team from the league
    public void removeTeam(Team team) {
        teams.remove(team);
        EventLog.getInstance().logEvent(new Event("Removed team " + team.getTeamName() + " from " + this.name));
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

    @Override
    // EFFECTS: returns this league as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("teams", teamsToJson());
        return json;
    }

    // EFFECTS: returns teams in this league as a JSON array
    private JSONArray teamsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Team t : teams) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
