package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class JsonWriter {
    private static final int TAB = 4;
    private String destinationFile1;
    private String destinationFile2;
    private PrintWriter printWriter1;
    private PrintWriter printWriter2;

    public JsonWriter(String destinationFile1, String destinationFile2) {
        this.destinationFile1 = destinationFile1;
        this.destinationFile2 = destinationFile2;
    }

    public void open() throws FileNotFoundException {
        printWriter1 = new PrintWriter(destinationFile1);
        printWriter2 = new PrintWriter(destinationFile2);
    }

    public void writeLeagues(ArrayList<League> allLeagues) {
        JSONArray jsonArray = new JSONArray();
        for (League league : allLeagues) {
            JSONObject json = league.toJson();
        }
        saveToFileLeague(jsonArray.toString(TAB));
    }

    public void writeTeams(ArrayList<Team> allTeams) {
        JSONArray jsonArray = new JSONArray();
        for (Team team : allTeams) {
            JSONObject json = team.toJson();
        }
        saveToFileTeam(jsonArray.toString(TAB));
    }

    public void close() {
        printWriter1.close();
        printWriter2.close();
    }

    public void saveToFileLeague(String json) {
        printWriter1.print(json);
    }

    public void saveToFileTeam(String json) {
        printWriter2.print(json);
    }
}
