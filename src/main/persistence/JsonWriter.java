package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

// Represents a writer that writes JSON representation of list of Leagues (allLeagues) and list of Teams (allTeams)
// to two files modelled based on CPSC210 JsonSerializationDemo project.
public class JsonWriter {
    private static final int TAB = 4;
    private String destinationFile1;
    private String destinationFile2;
    private PrintWriter printWriter1;
    private PrintWriter printWriter2;

    // EFFECTS: constructs writer to write to the destination files
    public JsonWriter(String destinationFile1, String destinationFile2) {
        this.destinationFile1 = destinationFile1;
        this.destinationFile2 = destinationFile2;
    }

    // MODIFIES: this
    // EFFECTS: opens the writer, throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        printWriter1 = new PrintWriter(destinationFile1);
        printWriter2 = new PrintWriter(destinationFile2);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of list of leagues to file1
    public void writeLeagues(ArrayList<League> allLeagues) {
        JSONArray jsonArray = new JSONArray();
        for (League league : allLeagues) {
            JSONObject json = league.toJson();
            jsonArray.put(json);
        }
        saveToFileLeague(jsonArray.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: write JSON representation of list of teams to file2
    public void writeTeams(ArrayList<Team> allTeams) {
        JSONArray jsonArray = new JSONArray();
        for (Team team : allTeams) {
            JSONObject json = team.toJson();
            jsonArray.put(json);
        }
        saveToFileTeam(jsonArray.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        printWriter1.close();
        printWriter2.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file1 for list of leagues
    public void saveToFileLeague(String json) {
        printWriter1.print(json);
    }

    // MODIFIES: this
    // EFFECTS: writes string to file2 for list of teams
    public void saveToFileTeam(String json) {
        printWriter2.print(json);
    }
}
