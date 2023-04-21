import NBA_Class.Game;
import NHL_Class.Goal;
import NHL_Class.Root;
import NHL_Class.Shots;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.naming.spi.NamingManager;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MenuTest {

    public static void main(String[] args) throws IOException, IllegalAccessException {

        try{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        File file = new File("src/NHL_files/2021-10-19.json");
        String date = "2022-10-19";
        MysqlHandler.createTableNHL();
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