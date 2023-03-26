package ui;

import model.League;
import model.Team;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

public class LoadUI extends AbstractAction {
    private ArrayList<Team> allTeams;     // list of all teams
    private ArrayList<League> allLeagues; // list of all leagues

    public LoadUI(ArrayList<Team> allTeams, ArrayList<League> allLeagues) {
        super("Load Files");
        this.allTeams = allTeams;
        this.allLeagues = allLeagues;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        int answer = JOptionPane.showConfirmDialog(null, "Load Save Files?",
                "Load", JOptionPane.YES_NO_OPTION);
        PopUpMessage message;

        if (answer == JOptionPane.YES_OPTION) {
            JsonReader reader = new JsonReader("./data/savedLeaguesGUI.json", "./data/savedTeamsGUI.json");
            try {
                allLeagues = reader.readLeagues();
                allTeams = reader.readTeams();
                message = new PopUpMessage("Load was successful.");
            } catch (IOException e) {
                message = new PopUpMessage("Exception was thrown. No save file was found. ");
            }
        } else {
            message = new PopUpMessage("No files were loaded.");
        }
    }
}
