package Tests;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Tests {
    public static class TeamStats {
        public int wins;
        public int losses;
        public int ot;
        public int wins2;
        public int losses2;
        public int ot2;
    }

    public static class RecordTest {
        public Map<String, TeamStats> records;
    }
    public static class RecordTest2 {
        public Map<String, TeamStats> records2;
    }

    public static class Stats {
        @JsonProperty("recordtest")
        public RecordTest recordTest;
    }

    public static class Stats2 {
        @JsonProperty("recordtest2")
        public RecordTest2 recordTest2;
    }
    public static class Game{
        @JsonProperty("stats")
        public Stats stats;
        @JsonProperty("stats2")
        public Stats2 stats2;
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        String jsonString = "{\"game\": {\"stats\": {\"recordtest\": {\"ONE\": {\"wins\": 1, \"losses\": 2, \"ot\": 1},\"TWO\": {\"wins\": 3, \"losses\": 0, \"ot\": 0}}}}, \"stats2\": {\"recordtest2\": {\"ONE\": {\"wins2\": 6, \"losses2\": 6, \"ot2\": 6},\"TWO\": {\"wins2\": 6, \"losses2\": 7, \"ot2\": 8}}}}";

        try {
            // Parse the JSON string into a Java object
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Game game = mapper.readValue(jsonString, Game.class);
            Stats stats = game.stats;
            Stats2 stats2 = game.stats2;
            RecordTest recordTest = stats.recordTest;
            RecordTest2 recordTest2 = stats2.recordTest2;

            // Access the properties dynamically
            for (Map.Entry<String, TeamStats> entry : recordTest.records.entrySet()) {
                String team = entry.getKey();
                TeamStats teamStats = entry.getValue();

                System.out.println("Properties of " + team + ":");
                System.out.println("wins: " + teamStats.wins);
                System.out.println("losses: " + teamStats.losses);
                System.out.println("ot: " + teamStats.ot);
                System.out.println();
            }
            for (Map.Entry<String, TeamStats> entry : recordTest2.records2.entrySet()) {
                String team = entry.getKey();
                TeamStats teamStats = entry.getValue();

                System.out.println("Properties of " + team + ":");
                System.out.println("wins: " + teamStats.wins2);
                System.out.println("losses: " + teamStats.losses2);
                System.out.println("ot: " + teamStats.ot2);
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

