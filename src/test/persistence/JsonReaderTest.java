package persistence;

import model.League;
import model.Team;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest{
    private ArrayList<League> allLeagues;
    private ArrayList<Team> allTeams;

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/nonExistentFile.json", "./data/nonExistentFile.json");
        try {
            allLeagues = reader.readLeagues();
            allTeams = reader.readTeams();
            fail("IO Exception not thrown but expected");
        } catch (IOException e) {
            // test pass
        }

        reader = new JsonReader("./data/testReaderNoLeagues.json", "./data/nonExistentFile.json");
        try {
            allLeagues = reader.readLeagues();
            allTeams = reader.readTeams();
            fail("IO Exception not thrown but expected");
        } catch (IOException e) {
            // test pass
        }
    }

    @Test
    public void testReaderNoLeaguesNoTeams() {
        JsonReader reader = new JsonReader("./data/testReaderNoLeagues.json", "./data/testReaderNoTeams.json");
        try {
            allLeagues = reader.readLeagues();
            allTeams = reader.readTeams();
        } catch (IOException e) {
            fail("Couldn't readLeagues from file");
        }
    }

    @Test
    public void testReaderHasLeaguesNoTeams() {
        JsonReader reader = new JsonReader("./data/testReaderHasLeagues.json", "./data/testReaderNoTeams.json");
        try {
            allLeagues = reader.readLeagues();
            allTeams = reader.readTeams();
        } catch (IOException e) {
            fail("Couldn't readLeagues from file");
        }
    }

    @Test
    public void testReaderNoLeaguesHasTeams() {
        JsonReader reader = new JsonReader("./data/testReaderNoLeagues.json", "./data/testReaderHasTeams.json");
        try {
            allLeagues = reader.readLeagues();
            allTeams = reader.readTeams();
        } catch (IOException e) {
            fail("Couldn't readLeagues from file");
        }
    }

    @Test
    public void testReaderGeneralLeaguesGeneralTeams() {
        JsonReader reader = new JsonReader("./data/testReaderHasLeagues.json", "./data/testReaderHasTeams.json");
        try {
            allLeagues = reader.readLeagues();
            allTeams = reader.readTeams();
        } catch (IOException e) {
            fail("Couldn't readLeagues from file");
        }
    }

    @Test
    public void testReaderHasTeamNotInLeague() {
        JsonReader reader = new JsonReader("./data/testReaderHasLeagues.json","./data/testReaderExtraTeams.json");
        try {
            allLeagues = reader.readLeagues();
            allTeams = reader.readTeams();
        } catch (IOException e) {
            fail("Couldn't readLeagues from file");
        }
    }
}
