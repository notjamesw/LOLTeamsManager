package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {
    private Team team1;
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;
    private Player p5;

    @BeforeEach
    public void setup() {
        team1 = new Team("TSM", "LCS");
        p1 = new ProPlayer("369", "TOP", "LCS");
        p2 = new ProPlayer("Oner", "JG", "LCS");
        p3 = new ProPlayer("Faker", "MID", "LCS");
        p4 = new ProPlayer("FBI", "BOT", "LCS");
        p5 = new ProPlayer("Busio", "SUPP", "LCS");
    }

    @Test
    public void testConstructor() {
        assertEquals("TSM", team1.getTeamName());
        assertEquals("LCS", team1.getLeague());
        assertEquals(0, team1.getStarters().size());
        assertEquals(0, team1.getSubs().size());
    }

    @Test
    public void testStartersIsEmpty() {
        assertTrue(team1.startersIsEmpty());

        team1.addStarter(p1);
        assertFalse(team1.startersIsEmpty());
    }

    @Test
    public void testSubsIsEmpty() {
        assertTrue(team1.subsIsEmpty());

        team1.addSub(p1);
        assertFalse(team1.subsIsEmpty());
    }

    @Test
    public void testStartersIsFullNotFull() {
        assertFalse(team1.startersIsFull());
        team1.addStarter(p1);
        team1.addStarter(p2);
        team1.addStarter(p3);
        team1.addStarter(p4);
        assertFalse(team1.startersIsFull());
    }

    @Test
    public void testStartersIsFull() {
        team1.addStarter(p1);
        team1.addStarter(p2);
        team1.addStarter(p3);
        team1.addStarter(p4);
        team1.addStarter(p5);
        assertTrue(team1.startersIsFull());
    }

    @Test
    public void testNumberOfSubs() {
        assertEquals(0, team1.numberOfSubs());
        team1.addSub(p1);
        assertEquals(1, team1.numberOfSubs());
    }

    @Test
    public void testAddStarterNotFull() {
        team1.addStarter(p1);
        assertFalse(team1.startersIsEmpty());
        assertEquals(1, team1.getStarters().size());
        assertEquals(p1, team1.getStarters().get(0));

    }

    @Test
    public void testAddStarterAlmostFull() {
        team1.addStarter(p1);
        team1.addStarter(p2);
        team1.addStarter(p3);
        team1.addStarter(p4);
        assertFalse(team1.startersIsFull());
        assertEquals(p4, team1.getStarters().get(3));
        assertEquals(4, team1.getStarters().size());

        team1.addStarter(p5);
        assertTrue(team1.startersIsFull());
        assertEquals(p5, team1.getStarters().get(4));
        assertEquals(5, team1.getStarters().size());
    }

    @Test
    public void testAddSubs() {
        team1.addSub(p1);
        assertFalse(team1.subsIsEmpty());
        assertEquals(1, team1.numberOfSubs());

        team1.addSub(p2);
        assertEquals(2, team1.numberOfSubs());
        assertEquals(p2, team1.getSubs().get(1));
    }

    @Test
    public void testRemoveStartersAlmostEmpty() {
        team1.addStarter(p1);
        team1.removeStarter(0);
        assertTrue(team1.startersIsEmpty());
    }

    @Test
    public void testRemoveStartersMiddle() {
        team1.addStarter(p1);
        team1.addStarter(p2);
        team1.addStarter(p3);
        team1.addStarter(p4);
        team1.removeStarter(1);

        assertEquals(3, team1.getStarters().size());
        assertEquals(p3, team1.getStarters().get(1));

        team1.removeStarter(1);

        assertEquals(2, team1.getStarters().size());
        assertEquals(p4, team1.getStarters().get(1));
    }

    @Test
    public void testRemoveStartersStartOrEnd() {
        team1.addStarter(p1);
        team1.addStarter(p2);
        team1.addStarter(p3);
        team1.addStarter(p4);
        team1.removeStarter(0);

        assertEquals(3, team1.getStarters().size());
        assertEquals(p2, team1.getStarters().get(0));

        team1.removeStarter(2);

        assertEquals(2, team1.getStarters().size());
        assertEquals(p3, team1.getStarters().get(1));
    }

    @Test
    public void testRemoveSubsAlmostEmpty() {
        team1.addSub(p1);
        team1.removeSub(0);
        assertTrue(team1.subsIsEmpty());
    }

    @Test
    public void testRemoveSubsMiddle() {
        team1.addSub(p1);
        team1.addSub(p2);
        team1.addSub(p3);
        team1.addSub(p4);
        team1.removeSub(1);

        assertEquals(3, team1.numberOfSubs());
        assertEquals(p3, team1.getSubs().get(1));

        team1.removeSub(1);

        assertEquals(2, team1.numberOfSubs());
        assertEquals(p4, team1.getSubs().get(1));
    }

    @Test
    public void testRemoveSubsStartOrEnd() {
        team1.addSub(p1);
        team1.addSub(p2);
        team1.addSub(p3);
        team1.addSub(p4);
        team1.removeSub(0);

        assertEquals(3, team1.numberOfSubs());
        assertEquals(p2, team1.getSubs().get(0));

        team1.removeSub(2);

        assertEquals(2, team1.numberOfSubs());
        assertEquals(p3, team1.getSubs().get(1));
    }

    @Test
    public void testClearAllPlayersEmpty() {
        team1.clearAllPlayers();
        assertTrue(team1.startersIsEmpty());
        assertTrue(team1.subsIsEmpty());
    }

    @Test
    public void testClearAllPlayersNotEmpty() {
        team1.addStarter(p1);
        team1.clearAllPlayers();
        assertTrue(team1.startersIsEmpty());
        assertTrue(team1.subsIsEmpty());

        team1.addSub(p1);
        team1.clearAllPlayers();
        assertTrue(team1.startersIsEmpty());
        assertTrue(team1.subsIsEmpty());

        team1.addStarter(p1);
        team1.addStarter(p3);
        team1.addSub(p2);
        team1.clearAllPlayers();
        assertTrue(team1.startersIsEmpty());
        assertTrue(team1.subsIsEmpty());
    }

    @Test
    public void testSortPlayersSorted() {
        ArrayList<Player> sortedStarters = new ArrayList<>();
        sortedStarters.add(p1);
        sortedStarters.add(p2);
        sortedStarters.add(p3);
        sortedStarters.add(p4);
        sortedStarters.add(p5);

        team1.addStarter(p1);
        team1.addStarter(p2);
        team1.addStarter(p3);
        team1.addStarter(p4);
        team1.addStarter(p5);
        team1.sortStarters();
        assertEquals(sortedStarters, team1.getStarters());
    }

    @Test
    public void testSortPlayersTwoDisplaced() {
        ArrayList<Player> sortedStarters = new ArrayList<>();
        sortedStarters.add(p1);
        sortedStarters.add(p2);
        sortedStarters.add(p3);
        sortedStarters.add(p4);
        sortedStarters.add(p5);

        team1.addStarter(p2);
        team1.addStarter(p1);
        team1.addStarter(p3);
        team1.addStarter(p4);
        team1.addStarter(p5);
        team1.sortStarters();
        assertEquals(sortedStarters, team1.getStarters());

        team1.clearAllPlayers();
        team1.addStarter(p5);
        team1.addStarter(p2);
        team1.addStarter(p3);
        team1.addStarter(p4);
        team1.addStarter(p1);
        team1.sortStarters();
        assertEquals(sortedStarters, team1.getStarters());
    }

    @Test
    public void testSortPlayersAllDisplaced() {
        ArrayList<Player> sortedStarters = new ArrayList<>();
        sortedStarters.add(p1);
        sortedStarters.add(p2);
        sortedStarters.add(p3);
        sortedStarters.add(p4);
        sortedStarters.add(p5);

        team1.addStarter(p2);
        team1.addStarter(p1);
        team1.addStarter(p5);
        team1.addStarter(p3);
        team1.addStarter(p4);
        team1.sortStarters();
        assertEquals(sortedStarters, team1.getStarters());
    }


}


