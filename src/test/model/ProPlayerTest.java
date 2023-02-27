package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProPlayerTest {
    private ProPlayer pro1;

    @BeforeEach
    public void setup() {
        pro1 = new ProPlayer("Faker", "MID", "LCK");
    }

    @Test
    public void testConstructor() {
        assertEquals("Faker", pro1.getIgn());
        assertEquals("MID", pro1.getRole());
        assertEquals("LCK", pro1.getRegion());
        assertEquals(0, pro1.getCsm());
        assertEquals(0, pro1.getKda());
        assertEquals(-1, pro1.getWinRate());
        assertEquals("", pro1.getDescription());
        assertEquals(0, pro1.getWins());
        assertEquals(0, pro1.getLoses());
    }

    @Test
    public void testSetWins() {
        pro1.setWins(0);
        assertEquals(0, pro1.getWins());
        assertEquals(-1, pro1.getWinRate());

        pro1.setWins(1);
        assertEquals(1,pro1.getWins());
        assertEquals(100, pro1.getWinRate());
    }

    @Test
    public void testSetLoses() {
        pro1.setLoses(0);
        assertEquals(0, pro1.getLoses());
        assertEquals(-1, pro1.getWinRate());

        pro1.setLoses(1);
        assertEquals(1,pro1.getLoses());
        assertEquals(0, pro1.getWinRate());
    }

    @Test
    public void testUpdateWinRate() {
        pro1.setWins(0);
        assertEquals(-1, pro1.getWinRate());

        pro1.setWins(1);
        assertEquals(100, pro1.getWinRate());

        pro1.setLoses(2);
        assertEquals(100 * (double) 1/3, pro1.getWinRate());
    }
}
