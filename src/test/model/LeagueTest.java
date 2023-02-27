package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LeagueTest {
    private League league1;
    private Team team1;
    private Team team2;
    private Team team3;

    @BeforeEach
    public void setup() {
        league1 = new League("LCS");
        team1 = new Team("TSM", "LCS");
        team2 = new Team("100T", "LCS");
        team3 = new Team("TL", "LCS");
    }

    @Test
    public void testConstructor() {
        assertEquals("LCS", league1.getName());
        assertTrue(league1.isEmpty());
    }

    @Test
    public void testAddTeam() {
        league1.addTeam(team1);
        assertEquals(1, league1.size());
        assertEquals(team1, league1.getTeams().get(0));

        league1.addTeam(team2);
        assertEquals(2, league1.size());
        assertEquals(team2, league1.getTeams().get(1));
    }

    @Test
    public void removeTeamAlmostEmpty() {
        league1.addTeam(team1);
        assertFalse(league1.isEmpty());
        league1.removeTeam(team1);
        assertTrue(league1.isEmpty());
    }

    @Test
    public void removeTeamStart() {
        league1.addTeam(team1);
        league1.addTeam(team2);
        league1.addTeam(team3);
        league1.removeTeam(team1);

        assertEquals(2, league1.size());
        assertEquals(team2, league1.teamAtIndex(0));

        league1.removeTeam(team2);
    }

    @Test
    public void removeTeamMiddle() {
        league1.addTeam(team1);
        league1.addTeam(team2);
        league1.addTeam(team3);
        league1.removeTeam(team2);

        assertEquals(2, league1.size());
        assertEquals(team3, league1.teamAtIndex(1));
    }

    @Test
    public void removeTeamEnd() {
        league1.addTeam(team1);
        league1.addTeam(team2);
        league1.addTeam(team3);
        league1.removeTeam(team3);

        assertEquals(2, league1.size());
        assertEquals(team2, league1.teamAtIndex(1));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(league1.isEmpty());
        league1.addTeam(team1);
        assertFalse(league1.isEmpty());
    }

    @Test
    public void testTeamAtIndexStartMiddleAndEnd() {
        league1.addTeam(team1);
        league1.addTeam(team3);
        league1.addTeam(team2);
        Team test1 = league1.teamAtIndex(0);
        Team test2 = league1.teamAtIndex(1);
        Team test3 = league1.teamAtIndex(2);

        assertEquals(team1, test1);
        assertEquals(team2, test3);
        assertEquals(team3, test2);
    }

    @Test
    public void testTeamAtIndexLeagueAlmostEmpty() {
        league1.addTeam(team1);
        Team test1 = league1.teamAtIndex(0);

        assertEquals(team1, test1);
    }

    @Test
    public void size() {
        assertEquals(0, league1.size());
        league1.addTeam(team1);
        league1.addTeam(team2);
        assertEquals(2, league1.size());
    }
}
