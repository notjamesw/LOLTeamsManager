package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a team of players with a team name, list of starters and a list of subs
public class Team implements Writable {
    private ArrayList<Player> starters;
    private ArrayList<Player> subs;
    private String teamName;
    private String league;
    public static final int  MAX_STARTER_SIZE = 5; // max amount of starters on a team.

    // REQUIRES: name of team must not be the same as a pre-existing team
    // EFFECTS: constructs a team with a name, an empty list of starting players and an empty list of subs.
    public Team(String name, String league) {
        this.teamName = name;
        this.starters = new ArrayList<>();
        this.subs = new ArrayList<>();
        this.league = league;
    }

    public String getLeague() {
        return league;
    }

    public ArrayList<Player> getStarters() {
        return starters;
    }

    public ArrayList<Player> getSubs() {
        return subs;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    // REQUIRES: starters is not full
    // MODIFIES: this
    // EFFECTS: adds a player to the starting roster
    public void addStarter(Player player) {
        starters.add(player);
    }

    // REQUIRES: starters is not empty
    // MODIFIES: this
    // EFFECTS: removes the player at index in starters from the team.
    public void removeStarter(int index) {
        starters.remove(index);
    }

    // MODIFIES: this
    // EFFECTS: adds a player to subs
    public void addSub(Player sub) {
        subs.add(sub);
    }

    // REQUIRES: subs is not empty
    // MODIFIES: this
    // EFFECTS: removes the player at index from subs
    public void removeSub(int index) {
        subs.remove(index);
    }

    // EFFECTS: returns number of subs
    public int numberOfSubs() {
        return subs.size();
    }

    // EFFECTS: returns true if starters are full
    public boolean startersIsFull() {
        return starters.size() == MAX_STARTER_SIZE;
    }

    // EFFECTS: returns true if starters are empty
    public boolean startersIsEmpty() {
        return starters.size() == 0;
    }

    // EFFECTS: returns true if subs are empty
    public boolean subsIsEmpty() {
        return subs.size() == 0;
    }

    // MODIFIES: this
    // EFFECTS: removes all players, both starters and subs, if any, from the team
    public void clearAllPlayers() {
        starters.clear();
        subs.clear();
    }

    // REQUIRES: starters is full, players have unique positions
    // MODIFIES: this
    // EFFECTS: sorts starters by position
    public void sortStarters() {
        Player player1 = starters.get(0);
        Player player2 = starters.get(0);
        Player player3 = starters.get(0);
        Player player4 = starters.get(0);
        Player player5 = starters.get(0);
        for (Player player : this.starters) {
            if (player.getRole().equalsIgnoreCase("TOP")) {
                player1 = player;
            } else if (player.getRole().equalsIgnoreCase("JG")
                    || player.getRole().equalsIgnoreCase("JUNGLE")) {
                player2 = player;
            } else if (player.getRole().equalsIgnoreCase("MID")) {
                player3 = player;
            } else if (player.getRole().equalsIgnoreCase("BOT")
                    || player.getRole().equalsIgnoreCase("ADC")) {
                player4 = player;
            } else {
                player5 = player;
            }
        }
        rearrangeStarters(player1, player2, player3, player4, player5);
    }

    // MODIFIES: this
    // EFFECTS: helper method for sortStarters, clears starters and rearranges the players according to role.
    private void rearrangeStarters(Player p1, Player p2, Player p3, Player p4, Player p5) {
        ArrayList<Player> copy = new ArrayList<>();
        copy.add(p1);
        copy.add(p2);
        copy.add(p3);
        copy.add(p4);
        copy.add(p5);

        this.starters = copy;
    }

    @Override
    // EFFECTS: returns this team as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("teamName", teamName);
        json.put("league", league);
        json.put("starters", startersToJson());
        json.put("subs", subsToJson());

        return json;
    }

    // EFFECTS: returns starters in this team as a JSON array
    private JSONArray startersToJson() {
        return listToJson(starters);
    }

    // EFFECTS: returns subs in this team as a JSON array
    private JSONArray subsToJson() {
        return listToJson(subs);
    }

    // EFFECTS: returns list of players as a JSON array
    private JSONArray listToJson(ArrayList<Player> listOfPlayers) {
        JSONArray jsonArray = new JSONArray();

        for (Player player : listOfPlayers) {
            jsonArray.put(player.toJson());
        }
        return jsonArray;
    }
}
