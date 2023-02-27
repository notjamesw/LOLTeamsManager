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
    }
}
