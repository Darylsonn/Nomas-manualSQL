import java.sql.*;
import java.util.Arrays;
import java.util.Date;

public class DatabaseHelper {
    // Function to create Files table
    public static void createFilesTable(){
        String sql = "CREATE TABLE IF NOT EXISTS files(" +
                "file_id INTEGER not NULL AUTO_INCREMENT, " +
                "file_name VARCHAR(255), " +
                "date DATETIME, " +
                "PRIMARY KEY (file_id))";
        try(Statement stmt = MysqlCon.getConnection().createStatement()){
            stmt.executeUpdate(sql);
            System.out.println("Files table created successfully.");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    // Function to query file name
    public static boolean queryFileName(String string){
        String sql = "SELECT file_name FROM files WHERE file_name = ?";
        boolean result = false;
        try{
            Connection con = MysqlCon.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, string);
            ResultSet rs = preparedStatement.executeQuery();
            result = rs.next();
            con.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    // Function to insert file name
    public static void insertFileName(String string, Date date){
        String sql = "INSERT INTO files (file_name, date) VALUES (?, ?)";
        try {
            Connection con = MysqlCon.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, string);
            preparedStatement.setDate(2, (java.sql.Date) date);
            preparedStatement.executeUpdate();
            con.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    // Function to create the Games table
    public static void createGamesTable() {
            String sql = "CREATE TABLE IF NOT EXISTS games (" +
                    "game_id INTEGER not null AUTO_INCREMENT PRIMARY KEY," +
                    "start_time TIMESTAMP," +
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
        try(Statement stmt = MysqlCon.getConnection().createStatement()){
            stmt.executeUpdate(sql);
            System.out.println("Games table created successfully.");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Function to create the Goals table
    public static void createGoalsTable(Connection connection) {
            String sql = "CREATE TABLE goals (" +
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
            try(Statement stmt = MysqlCon.getConnection().createStatement()){
                stmt.executeUpdate(sql);
                System.out.println("Goals table created successfully.");
            }
            catch (SQLException e){
                e.printStackTrace();
            }
    }
    // Function to create the Teams table
    public static void createTeamsTable(Connection connection) {
            String sql = "CREATE TABLE teams (" +
                    "team_id VARCHAR(255) PRIMARY KEY," +
                    "abbreviation VARCHAR(255)," +
                    "location_name VARCHAR(255)," +
                    "short_name VARCHAR(255)," +
                    "team_name VARCHAR(255)" +
                    ")";
            try(Statement stmt = MysqlCon.getConnection().createStatement()){
                stmt.executeUpdate(sql);
                System.out.println("Teams table created successfully.");
            }
            catch (SQLException e){
                e.printStackTrace();
            }
    }
    // Function to create the GameStats table
    public static void createGameStatsTable(Connection connection) {
            String sql = "CREATE TABLE gamestats (" +
                    "game_id VARCHAR(255) REFERENCES games(game_id)," +
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
            try(Statement stmt = MysqlCon.getConnection().createStatement()){
                stmt.executeUpdate(sql);
                System.out.println("GameStats table created successfully.");
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }

    // Function to create and execute an SQL insert statement for the Games table
    public static void insertGame(java.sql.Timestamp startTime, String awayTeamAbbreviation, String awayTeamLocation, String awayTeamShortName, String awayTeamName, String homeTeamAbbreviation,
                                  String homeTeamLocation, String homeTeamShortName, String homeTeamName, int awayTeamGoals, int homeTeamGoals) {

            String sql = "INSERT INTO games (start_time, away_team_abbreviation, away_team_location, away_team_short_name, away_team_name, home_team_abbreviation, home_team_location, home_team_short_name, home_team_name, away_team_goals, home_team_goals)"
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            Connection con = MysqlCon.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setTimestamp(1, startTime);
            preparedStatement.setString(2, awayTeamAbbreviation);
            preparedStatement.setString(3, awayTeamLocation);
            preparedStatement.setString(4, awayTeamShortName);
            preparedStatement.setString(5, awayTeamName);
            preparedStatement.setString(6, homeTeamAbbreviation);
            preparedStatement.setString(7, homeTeamLocation);
            preparedStatement.setString(8, homeTeamShortName);
            preparedStatement.setString(9, homeTeamName);
            preparedStatement.setInt(10, awayTeamGoals);
            preparedStatement.setInt(11, homeTeamGoals);
            preparedStatement.executeUpdate();
            con.close();
            System.out.println("Game record inserted successfully.");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Function to create and execute an SQL insert statement for the Goals table
    public static void insertGoal(Connection connection, int goalId, int gameId, String teamAbbreviation, String period, String scorerPlayer, int scorerSeasonTotal, String assistsPlayers, String assistsSeasonTotals, int minute, int second, boolean emptyNet) {
            String sql = "INSERT INTO goals (goal_id, game_id, team_abbreviation, period, scorer_player, scorer_season_total, assists_players, assists_season_totals, minute, second, empty_net) VALUES ("
                    + goalId + ", "
                    + gameId + ", '"
                    + teamAbbreviation + "', '"
                    + period + "', '"
                    + scorerPlayer + "', "
                    + scorerSeasonTotal + ", '{"
                    + assistsPlayers + "}', '{"
                    + assistsSeasonTotals + "}', "
                    + minute + ", "
                    + second + ", "
                    + (emptyNet ? 1 : 0) + ")";
            try(Statement stmt = MysqlCon.getConnection().createStatement()){
                stmt.executeUpdate(sql);
                System.out.println("Goal record inserted successfully.");
            }
            catch (SQLException e){
                e.printStackTrace();
            }
    }

    // Function to create and execute an SQL insert statement for the Teams table
    public static void insertTeam(Connection connection, int teamId, String abbreviation, String locationName, String shortName, String teamName) {
            String sql = "INSERT INTO teams (team_id, abbreviation, location_name, short_name, team_name) VALUES ("
                    + teamId + ", '"
                    + abbreviation + "', '"
                    + locationName + "', '"
                    + shortName + "', '"
                    + teamName + "')";
            try(Statement stmt = MysqlCon.getConnection().createStatement()){
                stmt.executeUpdate(sql);
                System.out.println("Team record inserted successfully.");
            }
            catch (SQLException e){
                e.printStackTrace();
            }
    }

    // Function to create and execute an SQL insert statement for the GameStats table
    public static void insertGameStats(Connection connection, int gameId, String teamAbbreviation, int blocked, String faceoffWinPercentage, int giveaways, int hits, int pim, int powerplayGoals, int powerplayOpportunities, String powerplayPercentage, int shots, int takeaways) {
            String sql = "INSERT INTO gamestats (game_id, team_abbreviation, blocked, faceoff_win_percentage, giveaways, hits, pim, powerplay_goals, powerplay_opportunities, powerplay_percentage, shots, takeaways) VALUES ("
                    + gameId + ", '"
                    + teamAbbreviation + "', "
                    + blocked + ", '"
                    + faceoffWinPercentage + "', "
                    + giveaways + ", "
                    + hits + ", "
                    + pim + ", "
                    + powerplayGoals + ", "
                    + powerplayOpportunities + ", '"
                    + powerplayPercentage + "', "
                    + shots + ", "
                    + takeaways + ")";
            try(Statement stmt = MysqlCon.getConnection().createStatement()){
                stmt.executeUpdate(sql);
                System.out.println("GameStats record inserted successfully.");
            }
            catch (SQLException e){
                e.printStackTrace();
            }
    }
}
