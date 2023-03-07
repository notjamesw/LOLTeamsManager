package persistence;


import model.League;
import model.Player;
import model.Team;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkLeague(League league, String name) {
        assertEquals(name, league.getName());
    }

    protected void checkTeam(Team team, String name, String league) {
        assertEquals(name, team.getTeamName());
        assertEquals(league, team.getLeague());
    }

    protected void checkPlayer(Player player, String ign, String role) {
        assertEquals(ign, player.getIgn());
        assertEquals(role, player.getRole());
    }
}
