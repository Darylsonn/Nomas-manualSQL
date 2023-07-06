import NHL_Class.Game;
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
                int teamAwayGoals = 0;
                int teamHomeGoals = 0;
//                LocalDate date = game.startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Timestamp timestamp = new Timestamp(game.startTime.getTime());
                for(Goal goal : game.getGoals()){
                    if(goal.team.equals(game.getTeams().away.getAbbreviation())) {
                        teamAwayGoals++;
                    }
                    else if(goal.team.equals(game.getTeams().home.getAbbreviation())){
                        teamHomeGoals++;
                    }
                }
                DatabaseHelper.insertGame(
                        game,
                        timestamp,
                        game.getTeams().getAway().getAbbreviation(),
                        game.getTeams().getAway().getLocationName(),
                        game.getTeams().getAway().getShortName(),
                        game.getTeams().getAway().getTeamName(),
                        game.getTeams().getHome().getAbbreviation(),
                        game.getTeams().getHome().getLocationName(),
                        game.getTeams().getHome().getShortName(),
                        game.getTeams().getHome().getTeamName(),
                        teamAwayGoals,
                        teamHomeGoals
                );
                for(Goal goal : game.getGoals()){
                    String assistPlayer = null;
                    Integer assistSeasonTotal = null;
                    if(goal.assists != null){
                        assistPlayer = goal.assists.get(0).player;
                    }
                    DatabaseHelper.insertGoal(
                            game.getGame_id(),
                            goal.team,
                            goal.period,
                            goal.scorer.player,
                            goal.scorer.seasonTotal,
                            assistPlayer, // TODO need Assists table
                            goal.assists.get(0).seasonTotal, // TODO change int to object in insert method
                            goal.min,
                            goal.sec,
                            goal.emptyNet
                    );
                }
            }
            System.out.println("Game records inserted successfully");
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
}
