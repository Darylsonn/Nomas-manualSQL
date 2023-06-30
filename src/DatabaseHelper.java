import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class DatabaseHelper {
    // Function to create the Games table
    public static void createGamesTable(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE Games (" +
                    "game_id VARCHAR(255) PRIMARY KEY," +
                    "start_time DATETIME," +
                    "away_team_abbreviation VARCHAR(255)," +
                    "away_team_location VARCHAR(255)," +
                    "away_team_short_name VARCHAR(255)," +
                    "away_team_name VARCHAR(255)," +
                    "home_team_abbreviation VARCHAR(255)," +
                    "home_team_location VARCHAR(255)," +
                    "home_team_short_name VARCHAR(255)," +
                    "home_team_name VARCHAR(255)," +
                    "away_team_goals INT," +
                    "home_team_goals INT" +
                    ")";
            statement.executeUpdate(sql);
            System.out.println("Games table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function to create the Goals table
    public static void createGoalsTable(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE Goals (" +
                    "goal_id VARCHAR(255) PRIMARY KEY," +
                    "game_id VARCHAR(255) REFERENCES Games(game_id)," +
                    "team_abbreviation VARCHAR(255)," +
                    "period VARCHAR(255)," +
                    "scorer_player VARCHAR(255)," +
                    "scorer_season_total INT," +
                    "assists_players VARCHAR(255)," +
                    "assists_season_totals VARCHAR(255)," +
                    "minute INT," +
                    "second INT," +
                    "empty_net BOOLEAN" +
                    ")";
            statement.executeUpdate(sql);
            System.out.println("Goals table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function to create the Teams table
    public static void createTeamsTable(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE Teams (" +
                    "team_id VARCHAR(255) PRIMARY KEY," +
                    "abbreviation VARCHAR(255)," +
                    "location_name VARCHAR(255)," +
                    "short_name VARCHAR(255)," +
                    "team_name VARCHAR(255)" +
                    ")";
            statement.executeUpdate(sql);
            System.out.println("Teams table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function to create the GameStats table
    public static void createGameStatsTable(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE GameStats (" +
                    "game_id VARCHAR(255) REFERENCES Games(game_id)," +
                    "team_abbreviation VARCHAR(255)," +
                    "blocked INT," +
                    "faceoff_win_percentage VARCHAR(255)," +
                    "giveaways INT," +
                    "hits INT," +
                    "pim INT," +
                    "powerplay_goals INT," +
                    "powerplay_opportunities INT," +
                    "powerplay_percentage VARCHAR(255)," +
                    "shots INT," +
                    "takeaways INT" +
                    ")";
            statement.executeUpdate(sql);
            System.out.println("GameStats table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Function to create and execute an SQL insert statement for the Games table
    public static void insertGame(Connection connection, String startTime, String awayTeamAbbreviation, String awayTeamLocation, String awayTeamShortName, String awayTeamName, String homeTeamAbbreviation, String homeTeamLocation, String homeTeamShortName, String homeTeamName, int awayTeamGoals, int homeTeamGoals) {
        try {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO Games (start_time, away_team_abbreviation, away_team_location, away_team_short_name, away_team_name, home_team_abbreviation, home_team_location, home_team_short_name, home_team_name, away_team_goals, home_team_goals) VALUES ('" + startTime + "', '" + awayTeamAbbreviation + "', '" + awayTeamLocation + "', '" + awayTeamShortName + "', '" + awayTeamName + "', '" + homeTeamAbbreviation + "', '" + homeTeamLocation + "', '" + homeTeamShortName + "', '" + homeTeamName + "', " + awayTeamGoals + ", " + homeTeamGoals + ")";
            statement.executeUpdate(sql);
            System.out.println("Game record inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function to create and execute an SQL insert statement for the Goals table
    public static void insertGoal(Connection connection, int gameId, String teamAbbreviation, String period, String scorerPlayer, int scorerSeasonTotal, String[] assistsPlayers, int[] assistsSeasonTotals, int minute, int second, boolean emptyNet) {
        try {
            Statement statement = connection.createStatement();
            String assistsPlayersStr = String.join(",", assistsPlayers);
            String assistsSeasonTotalsStr = Arrays.toString(assistsSeasonTotals).replace("[", "").replace("]", "");
            String sql = "INSERT INTO Goals (game_id, team_abbreviation, period, scorer_player, scorer_season_total, assists_players, assists_season_totals, minute, second, empty_net) VALUES (" + gameId + ", '" + teamAbbreviation + "', '" + period + "', '" + scorerPlayer + "', " + scorerSeasonTotal + ", '" + assistsPlayersStr + "', '" + assistsSeasonTotalsStr + "', " + minute + ", " + second + ", " + (emptyNet ? 1 : 0) + ")";
            statement.executeUpdate(sql);
            System.out.println("Goal record inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function to create and execute an SQL insert statement for the Teams table
    public static void insertTeam(Connection connection, int teamId, String abbreviation, String locationName, String shortName, String teamName) {
        try {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO Teams (team_id, abbreviation, location_name, short_name, team_name) VALUES (" + teamId + ", '" + abbreviation + "', '" + locationName + "', '" + shortName + "', '" + teamName + "')";
            statement.executeUpdate(sql);
            System.out.println("Team record inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function to create and execute an SQL insert statement for the GameStats table
    public static void insertGameStats(Connection connection, int gameId, String teamAbbreviation, int blocked, String faceoffWinPercentage, int giveaways, int hits, int pim, int powerplayGoals, int powerplayOpportunities, String powerplayPercentage, int shots, int takeaways) {
        try {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO GameStats (game_id, team_abbreviation, blocked, faceoff_win_percentage, giveaways, hits, pim, powerplay_goals, powerplay_opportunities, powerplay_percentage, shots, takeaways) VALUES (" + gameId + ", '" + teamAbbreviation + "', " + blocked + ", '" + faceoffWinPercentage + "', " + giveaways + ", " + hits + ", " + pim + ", " + powerplayGoals + ", " + powerplayOpportunities + ", '" + powerplayPercentage + "', " + shots + ", " + takeaways + ")";
            statement.executeUpdate(sql);
            System.out.println("GameStats record inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
