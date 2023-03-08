package persistence;

import model.League;
import model.ProPlayer;
import model.Team;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest{
    private ArrayList<League> allLeagues;
    private ArrayList<Team> allTeams;

    @Test
    public void testWriterInvalidFile() {
        try {
            allLeagues = new ArrayList<>();
            allTeams = new ArrayList<>();
            JsonWriter writer = new JsonWriter("./data/\0illegal:fileName.json", "./data/\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch(IOException e) {
            //test pass
        }

        try {
            allLeagues = new ArrayList<>();
            allTeams = new ArrayList<>();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLeagues.json", "./data/\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch(IOException e) {
            //test pass
        }
    }

    @Test
    public void testWriterEmptyLeagueEmptyTeams() {
        try {
            allLeagues = new ArrayList<>();
            allTeams = new ArrayList<>();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLeagues.json",
                    "./data/testWriterEmptyTeams.json");
            writer.open();
            writer.writeLeagues(allLeagues);
            writer.writeTeams(allTeams);
            writer.close();

            ArrayList<League> leaguesTest;
            ArrayList<Team> teamsTest;
            JsonReader reader = new JsonReader("./data/testWriterEmptyLeagues.json",
                    "./data/testWriterEmptyTeams.json");
            leaguesTest = reader.readLeagues();
            teamsTest = reader.readTeams();

            assertTrue(leaguesTest.isEmpty());
            assertTrue(teamsTest.isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterHasLeaguesEmptyTeams() {
        try {
            allLeagues = new ArrayList<>();
            allLeagues.add(new League("LCS"));
            allTeams = new ArrayList<>();
            JsonWriter writer = new JsonWriter("./data/testWriterHasLeagues.json",
                    "./data/testWriterEmptyTeams.json");
            writer.open();
            writer.writeLeagues(allLeagues);
            writer.writeTeams(allTeams);
            writer.close();

            ArrayList<League> leaguesTest;
            ArrayList<Team> teamsTest;
            JsonReader reader = new JsonReader("./data/testWriterHasLeagues.json",
                    "./data/testWriterEmptyTeams.json");
            leaguesTest = reader.readLeagues();
            teamsTest = reader.readTeams();

            checkLeague(leaguesTest.get(0), "LCS");
            assertEquals(1, leaguesTest.size());
            assertTrue(teamsTest.isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterEmptyLeaguesHasTeams() {
        try {
            allLeagues = new ArrayList<>();
            allTeams = new ArrayList<>();
            allTeams.add(new Team("100T", "LCS"));
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLeagues.json",
                    "./data/testWriterHasTeams.json");
            writer.open();
            writer.writeLeagues(allLeagues);
            writer.writeTeams(allTeams);
            writer.close();

            ArrayList<League> leaguesTest;
            ArrayList<Team> teamsTest;
            JsonReader reader = new JsonReader("./data/testWriterEmptyLeagues.json",
                    "./data/testWriterHasTeams.json");
            leaguesTest = reader.readLeagues();
            teamsTest = reader.readTeams();

            assertTrue(leaguesTest.isEmpty());
            assertEquals(1, teamsTest.size());
            checkTeam(teamsTest.get(0), "100T", "LCS");
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterHasLeaguesHasTeams() {
        try {
            allLeagues = new ArrayList<>();
            allTeams = new ArrayList<>();
            Team team1 = new Team("100T", "LCS");
            team1.addStarter(new ProPlayer("Faker", "MID", "LCS"));
            allTeams.add(team1);
            League league1 = new League("LCS");
            League league2 = new League("LEC");
            league1.addTeam(team1);
            allLeagues.add(league1);
            allLeagues.add(league2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralLeagues.json",
                    "./data/testWriterGeneralTeams.json");
            writer.open();
            writer.writeLeagues(allLeagues);
            writer.writeTeams(allTeams);
            writer.close();

            ArrayList<League> leaguesTest;
            ArrayList<Team> teamsTest;
            JsonReader reader = new JsonReader("./data/testWriterGeneralLeagues.json",
                    "./data/testWriterGeneralTeams.json");
            leaguesTest = reader.readLeagues();
            teamsTest = reader.readTeams();

            checkLeague(leaguesTest.get(0), "LCS");
            checkLeague(leaguesTest.get(1), "LEC");
            assertEquals(2, leaguesTest.size());
            checkPlayer(teamsTest.get(0).getStarters().get(0), "Faker", "MID");
            checkTeam(teamsTest.get(0), "100T", "LCS");
            assertEquals(1, teamsTest.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterExtraTeam() {
        try {
            allLeagues = new ArrayList<>();
            allTeams = new ArrayList<>();
            Team team1 = new Team("100T", "LCS");
            team1.addStarter(new ProPlayer("Faker", "MID", "LCS"));
            Team team2 = new Team("TSM", "No league");
            allTeams.add(team1);
            allTeams.add(team2);
            League league1 = new League("LCS");
            League league2 = new League("LEC");
            league1.addTeam(team1);
            allLeagues.add(league1);
            allLeagues.add(league2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralLeagues.json",
                    "./data/testWriterExtraTeams.json");
            writer.open();
            writer.writeLeagues(allLeagues);
            writer.writeTeams(allTeams);
            writer.close();

            ArrayList<League> leaguesTest;
            ArrayList<Team> teamsTest;
            JsonReader reader = new JsonReader("./data/testWriterGeneralLeagues.json",
                    "./data/testWriterExtraTeams.json");
            leaguesTest = reader.readLeagues();
            teamsTest = reader.readTeams();

            checkLeague(leaguesTest.get(0), "LCS");
            checkLeague(leaguesTest.get(1), "LEC");
            assertEquals(2, leaguesTest.size());
            checkPlayer(teamsTest.get(0).getStarters().get(0), "Faker", "MID");
            checkTeam(teamsTest.get(0), "100T", "LCS");
            checkTeam(teamsTest.get(1), "TSM", "No league");
            assertEquals(2, teamsTest.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
