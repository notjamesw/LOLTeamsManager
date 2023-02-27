package model;

// Abstract class representing a player having a username/ign, in-game role, description, wins, losses and win rate.
public abstract class Player {
    private String ign;          // player's in game name.
    private String role;
    private String description;
    private int wins;
    private int losses;
    private double winRate;

    // REQUIRES: role is a valid in-game role
    // EFFECTS: constructs a player with an ign, a role, empty description, no wins, no losses and win rate of -1.
    protected Player(String ign, String role) {
        this.ign = ign;
        this.role = role;
        this.description = "";
        this.wins = 0;
        this.losses = 0;
        this.winRate = -1;
    }

    // MODIFIES: this
    // EFFECTS: updates player's win rate based on their current wins and loses. If the player has 0 games played,
    // win rate = -1.
    private void updateWinRate() {
        if (wins + losses == 0) {
            winRate = -1;
        } else {
            winRate = 100 * (double) wins / (wins + losses);
        }
    }

    public String getIgn() {
        return ign;
    }

    public String getRole() {
        return role;
    }

    public String getDescription() {
        return description;
    }

    public int getWins() {
        return wins;
    }

    public int getLoses() {
        return losses;
    }

    public double getWinRate() {
        return winRate;
    }

    public void setIgn(String ign) {
        this.ign = ign;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // REQUIRES: wins >= 0
    // MODIFIES: this
    // EFFECTS: sets the player's number of wins and updates player's win rate
    public void setWins(int wins) {
        this.wins = wins;
        updateWinRate();
    }

    // REQUIRES: losses >= 0
    // MODIFIES: this
    // EFFECTS: sets the player's number of losses and updates player's win rate
    public void setLoses(int losses) {
        this.losses = losses;
        updateWinRate();
    }

}
