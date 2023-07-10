package NHL_Class;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Tests {
    public static class TeamStats {
        public int wins;
        public int losses;
        public int ot;
    }

    public static class RecordTest {
        @JsonProperty("recordtest")
        public Map<String, TeamStats> records;
    }

    public static class Stats {
        @JsonProperty("stats")
        public RecordTest recordTest;
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        String jsonString = "{\"stats\": {\"recordtest\": {\"ONE\": {\"wins\": 1, \"losses\": 2, \"ot\": 1},\"TWO\": {\"wins\": 3, \"losses\": 0, \"ot\": 0}}}}";

        try {
            // Parse the JSON string into a Java object
            ObjectMapper mapper = new ObjectMapper();
            Stats stats = mapper.readValue(jsonString, Stats.class);
            RecordTest recordTest = stats.recordTest;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

