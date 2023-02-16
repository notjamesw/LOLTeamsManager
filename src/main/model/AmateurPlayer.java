package model;

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
}
