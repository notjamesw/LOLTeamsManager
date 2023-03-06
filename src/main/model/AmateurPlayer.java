package model;

import org.json.JSONObject;

// Represents an amateur player
public class AmateurPlayer extends Player {
    private String rank;

    // REQUIRES: role is a valid in-game role, rank is a valid in-game rank.
    // EFFECTS: constructs an amateur player with the same fields as its superclass (Player) but also with their
    // in-game rank.
    public AmateurPlayer(String ign, String role, String rank) {
        super(ign, role);
        this.rank = rank;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("ign", this.getIgn());
        json.put("role", this.getRole());
        json.put("description", this.getDescription());
        json.put("wins", this.getWins());
        json.put("loses", this.getLoses());
        json.put("rank", this.rank);
        return json;
    }
}
