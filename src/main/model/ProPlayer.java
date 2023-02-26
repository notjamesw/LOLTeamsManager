package model;

// Represents a professional player
public class ProPlayer extends Player {
    private double kda;
    private double csm;
    private String region;

    // REQUIRES: role is a valid in-game role, region is a real in-game region.
    // EFFECTS: constructs a pro-player with the same fields as its superclass (player) but also with their region,
    // kda of 0, and csm of 0.
    public ProPlayer(String ign, String role, String region) {
        super(ign, role);
        this.region = region;
        this.kda = 0;
        this.csm = 0;
    }

    public double getKda() {
        return kda;
    }

    public double getCsm() {
        return csm;
    }

    public String getRegion() {
        return region;
    }

    public void setKda(double kda) {
        this.kda = kda;
    }

    public void setCsm(double csm) {
        this.csm = csm;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}