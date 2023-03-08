package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class JsonReader {
    private String sourceFile1; //stores all leagues info
    private String sourceFile2; //stores all teams info
    private ArrayList<Team> allTeams;

    public JsonReader(String sourceFile1, String sourceFile2) {
        this.sourceFile1 = sourceFile1;
        this.sourceFile2 = sourceFile2;
        allTeams = new ArrayList<>();
    }

    public ArrayList<League> readLeagues() throws IOException {
        ArrayList<League> allLeagues = new ArrayList<>();
        String jsonData = readFile(sourceFile1);
        JSONArray jsonArray = new JSONArray(jsonData);
        for (Object json : jsonArray) {
            JSONObject nextLeague = (JSONObject) json;
            allLeagues.add(parseLeague(nextLeague));
        }
        return allLeagues;
    }

    public ArrayList<Team> readTeams() throws IOException {
        String jsonData = readFile(sourceFile2);
        JSONArray jsonArray = new JSONArray(jsonData);
        Team team1;
        for (Object json : jsonArray) {
            JSONObject nextTeam = (JSONObject) json;
            team1 = addTeam(nextTeam);
            if (allTeams.isEmpty()) {
                allTeams.add(team1);
            }
            for (Team team2 : allTeams) {
                if (!team2.getTeamName().equals(team1.getTeamName())) {
                    allTeams.add(team1);
                    break;
                }
            }
        }
        return allTeams;
    }

    private String readFile(String sourceFile) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(sourceFile), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    private League parseLeague(JSONObject jsonObjectLeague) {
        String name = jsonObjectLeague.getString("name");
        League league = new League(name);
        addTeams(league, jsonObjectLeague);
        return league;
    }

    private void addTeams(League league, JSONObject jsonObjectLeague) {
        JSONArray jsonArray = jsonObjectLeague.getJSONArray("teams");
        Team team;
        for (Object json : jsonArray) {
            JSONObject nextTeam = (JSONObject) json;
            team = addTeam(nextTeam);
            league.addTeam(team);
            allTeams.add(team);
        }
    }

    private Team addTeam(JSONObject jsonObjectTeam) {
        String teamName = jsonObjectTeam.getString("teamName");
        String teamLeague = jsonObjectTeam.getString("league");
        Team team = new Team(teamName, teamLeague);
        addStarters(team, jsonObjectTeam);
        addSubs(team, jsonObjectTeam);
        return team;
    }

    private void addStarters(Team team, JSONObject jsonObjectTeam) {
        JSONArray jsonArray = jsonObjectTeam.getJSONArray("starters");
        for (Object json : jsonArray) {
            JSONObject nextStarter = (JSONObject) json;
            addStarter(team, nextStarter);
        }
    }

    private void addStarter(Team team, JSONObject jsonObjectPlayer) {
        Player player;
        String ign = jsonObjectPlayer.getString("ign");
        String role = jsonObjectPlayer.getString("role");
        String description = jsonObjectPlayer.getString("description");
        int wins = jsonObjectPlayer.getInt("wins");
        int loses = jsonObjectPlayer.getInt("loses");
        try {
            String rank = jsonObjectPlayer.getString("rank");
            player = new AmateurPlayer(ign, role, rank);
        } catch (JSONException e) {
            player = addPro(ign, role, jsonObjectPlayer);
        }
        player.setDescription(description);
        player.setWins(wins);
        player.setLoses(loses);
        team.addStarter(player);
    }

    private void addSubs(Team team, JSONObject jsonObjectTeam) {
        JSONArray jsonArray = jsonObjectTeam.getJSONArray("subs");
        for (Object json : jsonArray) {
            JSONObject nextSub = (JSONObject) json;
            addSub(team, nextSub);
        }
    }

    private void addSub(Team team, JSONObject jsonObjectPlayer) {
        Player player;
        String ign = jsonObjectPlayer.getString("ign");
        String role = jsonObjectPlayer.getString("role");
        String description = jsonObjectPlayer.getString("description");
        int wins = jsonObjectPlayer.getInt("wins");
        int loses = jsonObjectPlayer.getInt("loses");
        try {
            String rank = jsonObjectPlayer.getString("rank");
            player = new AmateurPlayer(ign, role, rank);
        } catch (JSONException e) {
            player = addPro(ign, role, jsonObjectPlayer);
        }
        player.setDescription(description);
        player.setWins(wins);
        player.setLoses(loses);
        team.addSub(player);
    }

    private ProPlayer addPro(String ign, String role, JSONObject jsonObjectPlayer) {
        String region = jsonObjectPlayer.getString("region");
        double kda = jsonObjectPlayer.getDouble("kda");
        double csm = jsonObjectPlayer.getDouble("csm");
        ProPlayer player = new ProPlayer(ign, role, region);
        player.setKda(kda);
        player.setCsm(csm);
        return player;
    }


}
