import NBA_Class.Game;
import NBA_Class.Root;
import NHL_Class.Goal;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

public class Menu {

    public static void main(String[] args) {
        LocalDate todayDate = LocalDate.now();
        LocalDate startDateNBA = LocalDate.of(2021, 10, 19);
        LocalDate endDateNBA = LocalDate.of(2021, 10, 20);
        LocalDate startDateNHL = LocalDate.of(2021, 10, 19);
        LocalDate endDateNHL = LocalDate.of(2021, 10, 20);
        DateTimeFormatter dtfNBA = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter dtfNHL = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        MysqlHandler.createTableNBA();
        MysqlHandler.createTableNHL();

        fileNBA(dtfNBA.format(startDateNBA), dtfNHL.format(startDateNBA));
        fileNHL(dtfNHL.format(startDateNHL));


//      MysqlCon.getConnection(); // DAYS APPENDING
//      while(!dtfNBA.format(startDateNBA).equals(dtfNBA.format(startDateNBA))) {
//
//            File f = new File("src/NBA_files/" + dtfNBA.format(startDateNBA) + ".json");
//            if(!f.exists()){
//                fileNBA(dtfNBA.format(startDateNBA));
//                System.out.println(dtfNBA.format(startDateNBA));
//            }
//            else{
//                System.out.println("file already exists");
//            }
//
//           startDateNBA = startDateNBA.plusDays(1);
//        }
//        while(startDateNHL.isBefore(endDateNHL)) {
//            boolean dateCheck = startDateNHL.plusDays(7).isAfter(endDateNHL) || startDateNHL.plusDays(7).isEqual(endDateNHL);
//            File f = new File("src/NHL_files/" + dtfNHL.format(startDateNHL) + ".json");
//            if(!f.exists() && !dateCheck){
//                fileNHL(dtfNHL.format(startDateNHL), dtfNHL.format(startDateNHL.plusDays(7)));
//                System.out.println(dtfNHL.format(startDateNHL) + " - " + dtfNHL.format(startDateNHL.plusDays(7)));
//            }
//            else if(!f.exists() && dateCheck){
//                fileNHL(dtfNHL.format(startDateNHL), dtfNHL.format(startDateNHL.plusDays(1)));
//                System.out.println(dtfNHL.format(startDateNHL) + " - " + dtfNHL.format(startDateNHL.plusDays(1)));
//            }
//            else{
//                System.out.println("file already exists NHL");
//            }
//
//            startDateNHL = startDateNHL.plusDays(7);
//        }
    }

    public static void fileNBA(String date, String date_dtf) {
        File file = new File("src/NBA_files/" + date + ".json");
        if (!file.exists()) {
            try {
                URL url = new URL("http://data.nba.net/10s/prod/v1/" + date + "/scoreboard.json");
                URLConnection urlc = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String inputline;
                while ((inputline = in.readLine()) != null) sb.append(inputline);
                in.close();
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(sb.toString());
                writer.close();
                System.out.println("File " + file + " created");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                Root root = objectMapper.readValue(file, Root.class);
                for (Game game : root.games) {
                    if (MysqlHandler.queryGameIdNBA(game.gameId)) {
                        continue;
                    }
                    String gameID = game.gameId;
                    String Hteam = game.hTeam.triCode;
                    String Vteam = game.vTeam.triCode;
                    String Hteam_score = game.hTeam.score;
                    String Vteam_score = game.vTeam.score;
                    String Hteam_1q = game.hTeam.linescore.get(0).score;
                    String Hteam_2q = game.hTeam.linescore.get(1).score;
                    String Hteam_3q = game.hTeam.linescore.get(2).score;
                    String Hteam_4q = game.hTeam.linescore.get(3).score;
                    String Hteam_5q = null;
                    String Vteam_1q = game.vTeam.linescore.get(0).score;
                    String Vteam_2q = game.vTeam.linescore.get(1).score;
                    String Vteam_3q = game.vTeam.linescore.get(2).score;
                    String Vteam_4q = game.vTeam.linescore.get(3).score;
                    String Vteam_5q = null;
                    String Season_year = game.seasonYear;

                    if (game.hTeam.linescore.size() == 5) {
                        Hteam_5q = game.hTeam.linescore.get(4).score;
                        Vteam_5q = game.vTeam.linescore.get(4).score;
                    }
                    MysqlHandler.insertRecordNBA(gameID, date, Hteam, Vteam, Hteam_score, Vteam_score,
                            Hteam_1q, Hteam_2q, Hteam_3q, Hteam_4q, Hteam_5q,
                            Vteam_1q, Vteam_2q, Vteam_3q, Vteam_4q, Vteam_5q, Season_year);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static void fileNHL(String date) {
        File file = new File("src/NHL_files/" + date + ".json");
        if (!file.exists()) {
            try {
                URL url = new URL("https://nhl-score-api.herokuapp.com/api/scores?startDate=" + date);
                URLConnection urlc = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String inputline;
                while ((inputline = in.readLine()) != null) sb.append(inputline);
                in.close();
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(sb.toString());
                writer.close();
            } catch (Exception e) {
                System.out.println("File " + file + " created");
            }
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            NHL_Class.Root root = objectMapper.readValue(file, NHL_Class.Root.class);
            for (NHL_Class.Game game : root.getGames()) {
                String Hteam = game.teams.home.abbreviation;
                String Vteam = game.teams.away.abbreviation;
                Map<String, Integer> map = game.gameStats.shots.getMap();
                int Hteam_score = 0;
                int Vteam_score = 0;
                int Hteam_1T = 0;
                int Hteam_2T = 0;
                int Hteam_3T = 0;
                int Hteam_OT = 0;
                int Hteam_SO = 0;
                int Vteam_1T = 0;
                int Vteam_2T = 0;
                int Vteam_3T = 0;
                int Vteam_OT = 0;
                int Vteam_SO = 0;
                String IS_OT = "N";
                String IS_SO = "N";
                for (Goal goals : game.goals) { //TODO change goal count for SO
                    if (Objects.equals(goals.team, Hteam)) {
                        if (Objects.equals(goals.period, "1")) {
                            Hteam_score++;
                            Hteam_1T++;
                        } else if (Objects.equals(goals.period, "2")) {
                            Hteam_score++;
                            Hteam_2T++;
                        } else if (Objects.equals(goals.period, "3")) {
                            Hteam_score++;
                            Hteam_3T++;
                        } else if (Objects.equals(goals.period, "OT")) {
                            Hteam_score++;
                            Hteam_OT++;
                            IS_OT = "Y";
                        } else if (Objects.equals(goals.period, "SO")) {
                            Hteam_score++;
                            Hteam_SO++;
                            IS_OT = "Y";
                            IS_SO = "Y";
                        }
                    }
                    if (Objects.equals(goals.team, Vteam)) {
                        if (Objects.equals(goals.period, "1")) {
                            Vteam_score++;
                            Vteam_1T++;
                        } else if (Objects.equals(goals.period, "2")) {
                            Vteam_score++;
                            Vteam_2T++;
                        } else if (Objects.equals(goals.period, "3")) {
                            Vteam_score++;
                            Vteam_3T++;
                        } else if (Objects.equals(goals.period, "OT")) {
                            Vteam_score++;
                            Vteam_OT++;
                        } else if (Objects.equals(goals.period, "SO")) {
                            Vteam_score++;
                            Vteam_SO++;
                        }
                    }
                }
                int Hteam_shots = map.get(Hteam);
                int Vteam_shots = map.get(Vteam);
                MysqlHandler.insertRecordNHL(date, Hteam, Vteam, Hteam_score, Vteam_score, Hteam_1T, Hteam_2T, Hteam_3T, Hteam_OT, Hteam_SO,
                        Vteam_1T, Vteam_2T, Vteam_3T, Vteam_OT, Vteam_SO, Hteam_shots, Vteam_shots, IS_OT, IS_SO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
