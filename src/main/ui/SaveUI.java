package ui;

import model.League;
import model.Team;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

public class SaveUI extends AbstractAction {
    private ArrayList<Team> allTeams;     // list of all teams
    private ArrayList<League> allLeagues; // list of all leagues

    public SaveUI(ArrayList<Team> allTeams, ArrayList<League> allLeagues) {
        super("Save Files");
        this.allTeams = allTeams;
        this.allLeagues = allLeagues;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        int answer = JOptionPane.showConfirmDialog(null, "Save to Files?",
                "Save", JOptionPane.YES_NO_OPTION);
        PopUpMessage message;

        if (answer == JOptionPane.YES_OPTION) {
            try {
                JsonWriter writer = new JsonWriter("./data/savedLeaguesGUI.json", "./data/savedTeamsGUI.json");
                writer.open();
                writer.writeLeagues(allLeagues);
                writer.writeTeams(allTeams);
                writer.close();
                message = new PopUpMessage("Save was successful.");
            } catch (IOException e) {
                message = new PopUpMessage("Exception was thrown. Save failed.");
            }
        } else {
            message = new PopUpMessage("No files were saved.");
        }
    }
}
