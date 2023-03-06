package persistence;

import model.*;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class JsonWriter {
    private static final int TAB = 4;
    private String destinationFile;
    private PrintWriter printWriter;

    public JsonWriter(String destinationFile) {
        this.destinationFile = destinationFile;
    }

    public void open() throws FileNotFoundException {
        printWriter = new PrintWriter(new File(destinationFile));
    }

    public void writeLeague(League league) {
        JSONObject json = league.toJson();
        saveToFile(json.toString(TAB));
    }

    public void writeTeam(Team team) {
        JSONObject json = team.toJson();
        saveToFile(json.toString(TAB));
    }

    public void close() {
        printWriter.close();
    }

    public void saveToFile(String json) {
        printWriter.print(json);
    }
}
