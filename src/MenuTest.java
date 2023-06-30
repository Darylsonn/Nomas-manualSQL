import NBA_Class.Game;
import NHL_Class.GameStats;
import NHL_Class.Goal;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;


public class MenuTest {

    public static void main(String[] args) {
        // Specify the path to the file containing the data
        String filePath = "data.json";

        // Create an instance of the ObjectMapper class
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Read the data from the file into the appropriate objects
            Data data = objectMapper.readValue(new File(filePath), Data.class);

            // Establish a database connection

            // Insert game records
            for (Game game : data.getGames()) {
                // Insert game data
                DatabaseHelper.insertGame(
                        connection,
                        game.getStartTime(),
                        game.getAwayTeam().getAbbreviation(),
                        game.getAwayTeam().getLocation(),
                        game.getAwayTeam().getShortName(),
                        game.getAwayTeam().getName(),
                        game.getHomeTeam().getAbbreviation(),
                        game.getHomeTeam().getLocation(),
                        game.getHomeTeam().getShortName(),
                        game.getHomeTeam().getName(),
                        game.getAwayTeamGoals(),
                        game.getHomeTeamGoals()
                );

                // Insert goal records for the game
                for (Goal goal : game.getGoals()) {
                    DatabaseHelper.insertGoal(
                            connection,
                            game.getId(),
                            goal.getTeam().getAbbreviation(),
                            goal.getPeriod(),
                            goal.getScorerPlayer(),
                            goal.getScorerSeasonTotal(),
                            goal.getAssistsPlayers(),
                            goal.getAssistsSeasonTotals(),
                            goal.getMinute(),
                            goal.getSecond(),
                            goal.isEmptyNet()
                    );
                }

                // Insert game stats record for the game
                GameStats gameStats = game.getGameStats();
                DatabaseHelper.insertGameStats(
                        connection,
                        game.getId(),
                        gameStats.getTeam().getAbbreviation(),
                        gameStats.getBlocked(),
                        gameStats.getFaceoffWinPercentage(),
                        gameStats.getGiveaways(),
                        gameStats.getHits(),
                        gameStats.getPim(),
                        gameStats.getPowerplayGoals(),
                        gameStats.getPowerplayOpportunities(),
                        gameStats.getPowerplayPercentage(),
                        gameStats.getShots(),
                        gameStats.getTakeaways()
                );
            }

            // Insert team records
            for (Team team : data.getTeams()) {
                DatabaseHelper.insertTeam(
                        connection,
                        team.getId(),
                        team.getAbbreviation(),
                        team.getLocation(),
                        team.getShortName(),
                        team.getName()
                );
            }

            // Close the database connection
            connection.close();

            System.out.println("Data insertion completed successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}