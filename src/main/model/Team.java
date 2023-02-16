package model;

import java.util.ArrayList;

// Represents a team of players with a team name, list of starters and a list of subs
public class Team {
    private ArrayList<Player> starters;
    private ArrayList<Player> subs;
    private String teamName;
    public static final int  MAX_STARTER_SIZE = 5; // max amount of starters on a team.

    // EFFECTS: constructs a team with a name, an empty list of starting players and an empty list of subs.
    public Team(String name) {
        this.teamName = name;
        this.starters = new ArrayList<>();
        this.subs = new ArrayList<>();
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

    // MODIFIES: this
    // EFFECTS: if starters are not full, returns true and adds a starter, else returns false
    public boolean addStarter(Player starter) {
        if (!this.startersIsFull()) {
            starters.add(starter);
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: starters is not empty
    // MODIFIES: this
    // EFFECTS: removes the player at playerIndex in starters from the team.
    public void removeStarter(Player player) {
        starters.remove(player);
    }

    // MODIFIES: this
    // EFFECTS: adds a player to subs
    public void addSub(Player sub) {
        subs.add(sub);
    }

    // REQUIRES: subs is not empty
    // MODIFIES: this
    // EFFECTS: removes the player from the team
    public void removeSub(Player player) {
        subs.remove(player);
    }

    // EFFECTS: returns number of subs
    public int subsLength() {
        return subs.size();
    }

    // EFFECTS: returns true if starters are full
    public boolean startersIsFull() {
        return starters.size() == MAX_STARTER_SIZE;
    }

    // MODIFIES: this
    // EFFECTS: removes all players, both starters and subs, if any, from the team
    public void clearAllPlayers() {
        starters.clear();
        subs.clear();
    }
}
