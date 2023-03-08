package persistence;

import model.AmateurPlayer;
import model.League;
import model.ProPlayer;
import model.Team;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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

            assertTrue(allTeams.isEmpty());
            assertTrue(allLeagues.isEmpty());
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

            assertTrue(allTeams.isEmpty());
            assertEquals(1, allLeagues.size());
            checkLeague(allLeagues.get(0), "LCS");
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

            assertTrue(allLeagues.isEmpty());
            assertEquals(1, allTeams.size());
            checkTeam(allTeams.get(0), "100T", "LCS");
        } catch (IOException e) {
            fail("Couldn't readLeagues from file");
        }
    }

    @Test
    public void testReaderGeneralLeaguesGeneralTeams() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralLeagues.json", "./data/testReaderHasTeams.json");
        try {
            allLeagues = reader.readLeagues();
            allTeams = reader.readTeams();

            assertEquals(1, allLeagues.size());
            checkLeague(allLeagues.get(0), "LCS");
            assertEquals(1, allTeams.size());
            checkTeam(allTeams.get(0), "100T", "LCS");
        } catch (IOException e) {
            fail("Couldn't readLeagues from file");
        }
    }

    @Test
    public void testReaderHasTeamNotInLeague() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralLeagues.json","./data/testReaderExtraTeams.json");
        try {
            allLeagues = reader.readLeagues();
            allTeams = reader.readTeams();

            assertEquals(1, allLeagues.size());
            checkLeague(allLeagues.get(0), "LCS");
            assertEquals(2, allTeams.size());
            checkTeam(allTeams.get(0), "100T", "LCS");
            checkTeam(allTeams.get(1), "TSM", "No league");
        } catch (IOException e) {
            fail("Couldn't readLeagues from file");
        }
    }

    @Test
    public void testReaderHasSubs() {
        allLeagues = new ArrayList<>();
        allTeams = new ArrayList<>();
        Team team1 = new Team("100T", "LCS");
        team1.addStarter(new ProPlayer("Faker", "MID", "LCS"));
        team1.addSub(new AmateurPlayer("Bob", "TOP", "Silver"));
        team1.addStarter(new AmateurPlayer("Joe", "SUPP", "Gold"));
        team1.addSub(new ProPlayer("Canyon", "JG", "LCS"));
        Team team2 = new Team("TSM", "No league");
        allTeams.add(team1);
        allTeams.add(team2);
        League league1 = new League("LCS");
        League league2 = new League("LEC");
        league1.addTeam(team1);
        allLeagues.add(league1);
        allLeagues.add(league2);
        try {
            JsonWriter writer = new JsonWriter("./data/testReaderLeaguesWithSubs.json",
                    "./data/testReaderTeamsWithSubs.json");
            writer.open();
            writer.writeLeagues(allLeagues);
            writer.writeTeams(allTeams);
            writer.close();

            ArrayList<League> leaguesTest;
            ArrayList<Team> teamsTest;
            JsonReader reader = new JsonReader("./data/testReaderLeaguesWithSubs.json",
                    "./data/testReaderTeamsWithSubs.json");
            leaguesTest = reader.readLeagues();
            teamsTest = reader.readTeams();

            checkLeague(leaguesTest.get(0), "LCS");
            checkLeague(leaguesTest.get(1), "LEC");
            assertEquals(2, leaguesTest.size());
            checkPlayer(teamsTest.get(0).getStarters().get(0), "Faker", "MID");
            checkPlayer(teamsTest.get(0).getSubs().get(0),"Bob", "TOP" );
            checkPlayer(teamsTest.get(0).getStarters().get(1), "Joe", "SUPP");
            checkPlayer(teamsTest.get(0).getSubs().get(1),"Canyon", "JG" );
            checkTeam(teamsTest.get(0), "100T", "LCS");
            checkTeam(teamsTest.get(1), "TSM", "No league");
            assertEquals(2, teamsTest.size());
        } catch (IOException e) {
            fail("Couldn't readLeagues from file");
        }
    }
}
