import NBA_Class.Game;
import NBA_Class.Root;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Basket {
    LocalDate todayDate = LocalDate.now();
    LocalDate startDateNBA = LocalDate.of(2021, 10, 19);
    LocalDate endDateNBA = LocalDate.of(2021, 10, 20);
    DateTimeFormatter dtfNBA = DateTimeFormatter.ofPattern("yyyyMMdd");


    //        fileNBA(dtfNBA.format(startDateNBA), dtfNHL.format(startDateNBA));

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

}