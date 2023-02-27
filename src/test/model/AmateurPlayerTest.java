package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmateurPlayerTest {
    private AmateurPlayer ap1;
    @BeforeEach
    public void setup() {
        ap1 = new AmateurPlayer("JOE", "JG", "Bronze");
    }

    @Test
    public void testConstructor() {
        assertEquals("JOE", ap1.getIgn());
        assertEquals("JG", ap1.getRole());
        assertEquals("Bronze", ap1.getRank());
        assertEquals(-1, ap1.getWinRate());
        assertEquals("", ap1.getDescription());
        assertEquals(0, ap1.getWins());
        assertEquals(0, ap1.getLoses());

        ap1.setRank("Silver");
        assertEquals("Silver", ap1.getRank());
    }

    @Test
    public void testSetWins() {
        ap1.setWins(0);
        assertEquals(0, ap1.getWins());
        assertEquals(-1, ap1.getWinRate());

        ap1.setWins(1);
        assertEquals(1,ap1.getWins());
        assertEquals(100, ap1.getWinRate());
    }

    @Test
    public void testSetLoses() {
        ap1.setLoses(0);
        assertEquals(0, ap1.getLoses());
        assertEquals(-1, ap1.getWinRate());

        ap1.setLoses(1);
        assertEquals(1,ap1.getLoses());
        assertEquals(0, ap1.getWinRate());
    }

    @Test
    public void testUpdateWinRate() {
        ap1.setWins(0);
        assertEquals(-1, ap1.getWinRate());

        ap1.setWins(1);
        assertEquals(100, ap1.getWinRate());

        ap1.setLoses(2);
        assertEquals(100 * (double) 1/3, ap1.getWinRate());
    }
}
