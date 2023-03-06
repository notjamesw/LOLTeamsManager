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
    private String sourceFile;
    private ArrayList<Team> allTeams;

    public JsonReader(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public League read() throws IOException {
        String jsonData = readFile(sourceFile);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLeague(jsonObject);
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
        for (Object json : jsonArray) {
            JSONObject nextTeam = (JSONObject) json;
            addTeam(league, nextTeam);
        }
    }

    private void addTeam(League league, JSONObject jsonObjectTeam) {
        String teamName = jsonObjectTeam.getString("teamName");
        String teamLeague = jsonObjectTeam.getString("league");
        Team team = new Team(teamName, teamLeague);
        addStarters(team, jsonObjectTeam);
        addSubs(team, jsonObjectTeam);
        league.addTeam(team);
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
        int wins = Integer.parseInt(jsonObjectPlayer.getString("wins"));
        int loses = Integer.parseInt(jsonObjectPlayer.getString("loses"));
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
        int wins = Integer.parseInt(jsonObjectPlayer.getString("wins"));
        int loses = Integer.parseInt(jsonObjectPlayer.getString("loses"));
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
        double kda = Double.parseDouble(jsonObjectPlayer.getString("kda"));
        double csm = Double.parseDouble(jsonObjectPlayer.getString("csm"));
        ProPlayer player = new ProPlayer(ign, role, region);
        player.setKda(kda);
        player.setCsm(csm);
        return player;
    }
}
