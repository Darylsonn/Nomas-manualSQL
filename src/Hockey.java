import NHL_Class.Game;
import NHL_Class.GameStats;
import NHL_Class.Goal;
import NHL_Class.Root;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Hockey {
    LocalDate todayDate = LocalDate.now();
    LocalDate startDateNHL = LocalDate.of(2021, 10, 19);
    LocalDate endDateNHL = LocalDate.of(2021, 10, 20);
    DateTimeFormatter dtfNHL = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Insert game records
    public static void uploadFileDataHockey(File file) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            Root root = objectMapper.readValue(file, Root.class);
            for (Game game : root.getGames()) {
                Timestamp timestamp = new Timestamp(game.startTime.getTime());
                DatabaseHelper.insertGame(
                        game,
                        timestamp
                );
                for(Goal goal : game.getGoals()){
                    DatabaseHelper.insertGoal(goal, game);
                }
                System.out.println("Game records inserted successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//    public static void uploadFileData(File file){
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//            NHL_Class.Root root = objectMapper.readValue(file, NHL_Class.Root.class);
//            for (NHL_Class.Game game : root.getGames()) {
//                String Hteam = game.teams.home.abbreviation;
//                String Vteam = game.teams.away.abbreviation;
//                Map<String, Integer> map = game.gameStats.shots.getMap();
//                int Hteam_score = 0;
//                int Vteam_score = 0;
//                int Hteam_1T = 0;
//                int Hteam_2T = 0;
//                int Hteam_3T = 0;
//                int Hteam_OT = 0;
//                int Hteam_SO = 0;
//                int Vteam_1T = 0;
//                int Vteam_2T = 0;
//                int Vteam_3T = 0;
//                int Vteam_OT = 0;
//                int Vteam_SO = 0;
//                String IS_OT = "N";
//                String IS_SO = "N";
//                for (Goal goals : game.goals) { //TODO change goal count for SO
//                    if (Objects.equals(goals.team, Hteam)) {
//                        if (Objects.equals(goals.period, "1")) {
//                            Hteam_score++;
//                            Hteam_1T++;
//                        } else if (Objects.equals(goals.period, "2")) {
//                            Hteam_score++;
//                            Hteam_2T++;
//                        } else if (Objects.equals(goals.period, "3")) {
//                            Hteam_score++;
//                            Hteam_3T++;
//                        } else if (Objects.equals(goals.period, "OT")) {
//                            Hteam_score++;
//                            Hteam_OT++;
//                            IS_OT = "Y";
//                        } else if (Objects.equals(goals.period, "SO")) {
//                            Hteam_score++;
//                            Hteam_SO++;
//                            IS_OT = "Y";
//                            IS_SO = "Y";
//                        }
//                    }
//                    if (Objects.equals(goals.team, Vteam)) {
//                        if (Objects.equals(goals.period, "1")) {
//                            Vteam_score++;
//                            Vteam_1T++;
//                        } else if (Objects.equals(goals.period, "2")) {
//                            Vteam_score++;
//                            Vteam_2T++;
//                        } else if (Objects.equals(goals.period, "3")) {
//                            Vteam_score++;
//                            Vteam_3T++;
//                        } else if (Objects.equals(goals.period, "OT")) {
//                            Vteam_score++;
//                            Vteam_OT++;
//                        } else if (Objects.equals(goals.period, "SO")) {
//                            Vteam_score++;
//                            Vteam_SO++;
//                        }
//                    }
//                }
//                int Hteam_shots = map.get(Hteam);
//                int Vteam_shots = map.get(Vteam);
//                MysqlHandler.insertRecordNHL(date, Hteam, Vteam, Hteam_score, Vteam_score, Hteam_1T, Hteam_2T, Hteam_3T, Hteam_OT, Hteam_SO,
//                        Vteam_1T, Vteam_2T, Vteam_3T, Vteam_OT, Vteam_SO, Hteam_shots, Vteam_shots, IS_OT, IS_SO);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
        /* OLD */
    }
    public static void processGameStats(File file) {
        // Deserialize JSON file into a Map
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> jsonMap = objectMapper.readValue(file, Map.class);

            // Access the values for each game
            List<Map<String, Object>> gamesList = (List<Map<String, Object>>) jsonMap.get("games");
            if (gamesList != null && !gamesList.isEmpty()) {
                for (Map<String, Object> game : gamesList) {
                    Map<String, Object> teams = (Map<String, Object>) game.get("teams");
                    Map<String, Object> awayTeam = (Map<String, Object>) teams.get("away");
                    Map<String, Object> homeTeam = (Map<String, Object>) teams.get("home");
                    String awayAbbreviation = (String) awayTeam.get("abbreviation");
                    String homeAbbreviation = (String) homeTeam.get("abbreviation");

                    // Retrieve streaks and standings based on team abbreviation
                    Map<String, Object> currentStats = (Map<String, Object>) game.get("currentStats");
                    Map<String, Object> streaks = objectMapper.convertValue(currentStats.get("streaks"), Map.class);
                    Map<String, Object> standings = (Map<String, Object>) currentStats.get("standings");

                    // Access the parameters using the team abbreviation
                    Map<String, Object> awayStreak = (Map<String, Object>) streaks.get(awayAbbreviation);
                    Map<String, Object> homeStreak = (Map<String, Object>) streaks.get(homeAbbreviation);

                    // Retrieve WINS and count values
                    String awayWins = (String) awayStreak.get("type");
                    int awayCount = (int) awayStreak.get("count");
                    String homeWins = (String) homeStreak.get("type");
                    int homeCount = (int) homeStreak.get("count");

                    // Print the values
                    System.out.println("Away Team Streaks - WINS: " + awayWins + ", Count: " + awayCount);
                    System.out.println("Home Team Streaks - WINS: " + homeWins + ", Count: " + homeCount);
                    System.out.println();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
