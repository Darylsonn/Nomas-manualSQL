import java.sql.*;
import java.util.Arrays;

public class MysqlHandler {
    public static void createTableNBA(){
        String sql = "CREATE TABLE IF NOT EXISTS NBA_GAMES " +
                "(id INTEGER not NULL AUTO_INCREMENT, " +
                " GAME_ID VARCHAR(255), " +
                " DATE VARCHAR(255), " + //TODO date format, check date begin/finish
                " HTEAM VARCHAR(255), " +
                " VTEAM VARCHAR(255), " +
                " HTEAM_score VARCHAR(255), " +
                " VTEAM_score VARCHAR(255), " +
                " HTEAM_1Q VARCHAR(255), " +
                " HTEAM_2Q VARCHAR(255), " +
                " HTEAM_3Q VARCHAR(255), " +
                " HTEAM_4Q VARCHAR(255), " +
                " HTEAM_5Q VARCHAR(255), " +
                " VTEAM_1Q VARCHAR(255), " +
                " VTEAM_2Q VARCHAR(255), " +
                " VTEAM_3Q VARCHAR(255), " +
                " VTEAM_4Q VARCHAR(255), " +
                " VTEAM_5Q VARCHAR(255), " +
                " SEASON_YEAR VARCHAR(255), " +
                " PRIMARY KEY ( id ))";
        try(Statement stmt = MysqlCon.getConnection().createStatement()){
            stmt.executeUpdate(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void createTableNHL(){
        String sql = "CREATE TABLE IF NOT EXISTS NHL_GAMES " +
                "(id INTEGER not NULL AUTO_INCREMENT, " +
                " DATE VARCHAR(255), " + //TODO date format, check date begin/finish
                " HTEAM VARCHAR(255), " +
                " VTEAM VARCHAR(255), " +
                " HTEAM_score INTEGER, " +
                " VTEAM_score INTEGER, " +
                " HTEAM_1T INTEGER, " +
                " HTEAM_2T INTEGER, " +
                " HTEAM_3T INTEGER, " +
                " HTEAM_OT INTEGER, " +
                " HTEAM_SO INTEGER, " +
                " VTEAM_1T INTEGER, " +
                " VTEAM_2T INTEGER, " +
                " VTEAM_3T INTEGER, " +
                " VTEAM_OT INTEGER, " +
                " VTEAM_SO INTEGER, " +
                " HTEAM_SHOTS INTEGER, " +
                " VTEAM_SHOTS INTEGER, " +
                " IS_OT VARCHAR(255), " +
                " IS_SO VARCHAR(255), " +
                " PRIMARY KEY ( id ))";
        try(Statement stmt = MysqlCon.getConnection().createStatement()){
            stmt.executeUpdate(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void insertRecordNBA(String gameid, String date, String HTEAM, String VTEAM, String HTEAM_score, String VTEAM_score,
                                       String HTEAM_1Q, String HTEAM_2Q, String HTEAM_3Q, String HTEAM_4Q, String HTEAM_5Q,
                                       String VTEAM_1Q, String VTEAM_2Q, String VTEAM_3Q, String VTEAM_4Q, String VTEAM_5Q, String season_year){
        String insert = "INSERT INTO NBA_GAMES(GAME_ID, DATE, HTEAM, VTEAM, HTEAM_score, " +
                        "VTEAM_score, HTEAM_1Q, HTEAM_2Q, HTEAM_3Q, HTEAM_4Q, HTEAM_5Q, " +
                        "VTEAM_1Q, VTEAM_2Q, VTEAM_3Q, VTEAM_4Q, VTEAM_5Q, SEASON_YEAR) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement preparedStatement = MysqlCon.getConnection().prepareStatement(insert)){
            preparedStatement.setString(1, gameid);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, HTEAM);
            preparedStatement.setString(4, VTEAM);
            preparedStatement.setString(5, HTEAM_score);
            preparedStatement.setString(6, VTEAM_score);
            preparedStatement.setString(7, HTEAM_1Q);
            preparedStatement.setString(8, HTEAM_2Q);
            preparedStatement.setString(9, HTEAM_3Q);
            preparedStatement.setString(10, HTEAM_4Q);
            preparedStatement.setString(11, HTEAM_5Q);
            preparedStatement.setString(12, VTEAM_1Q);
            preparedStatement.setString(13, VTEAM_2Q);
            preparedStatement.setString(14, VTEAM_3Q);
            preparedStatement.setString(15, VTEAM_4Q);
            preparedStatement.setString(16, VTEAM_5Q);
            preparedStatement.setString(17, season_year);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public static boolean queryGameIdNBA(String gameID){
    String sql = "SELECT * from NBA_GAMES where GAME_ID = ?";
        try(PreparedStatement stmt = MysqlCon.getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            stmt.setString(1, gameID);
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insertRecordNHL(String date, String HTEAM, String VTEAM, int HTEAM_score, int VTEAM_score,
                                       int HTEAM_1T, int HTEAM_2T, int HTEAM_3T, int HTEAM_OT, int HTEAM_SO,
                                       int VTEAM_1T, int VTEAM_2T, int VTEAM_3T, int VTEAM_OT, int VTEAM_SO, int HTEAM_SHOTS, int VTEAM_SHOTS, String IS_OT, String IS_SO){
        String insert = "INSERT INTO NHL_GAMES(DATE, HTEAM, VTEAM, HTEAM_score, " +
                "VTEAM_score, HTEAM_1T, HTEAM_2T, HTEAM_3T, HTEAM_OT, HTEAM_SO, " +
                "VTEAM_1T, VTEAM_2T, VTEAM_3T, VTEAM_OT, VTEAM_SO, HTEAM_SHOTS, VTEAM_SHOTS, IS_OT, IS_SO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = MysqlCon.getConnection().prepareStatement(insert);
            preparedStatement.setString(1,date);
            preparedStatement.setString(2,HTEAM);
            preparedStatement.setString(3,VTEAM);
            preparedStatement.setInt(4,HTEAM_score);
            preparedStatement.setInt(5,VTEAM_score);
            preparedStatement.setInt(6,HTEAM_1T);
            preparedStatement.setInt(7,HTEAM_2T);
            preparedStatement.setInt(8,HTEAM_3T);
            preparedStatement.setInt(9, HTEAM_OT);
            preparedStatement.setInt(10, HTEAM_SO);
            preparedStatement.setInt(11, VTEAM_1T);
            preparedStatement.setInt(12, VTEAM_2T);
            preparedStatement.setInt(13, VTEAM_3T);
            preparedStatement.setInt(14, VTEAM_OT);
            preparedStatement.setInt(15, VTEAM_SO);
            preparedStatement.setInt(16, HTEAM_SHOTS);
            preparedStatement.setInt(17, VTEAM_SHOTS);
            preparedStatement.setString(18,IS_OT);
            preparedStatement.setString(19,IS_SO);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createTableHockeyGames(){

        String sql = "CREATE TABLE IF NOT EXISTS Games (\n" +
                "    game_id INT PRIMARY KEY,\n" +
                "    start_time DATETIME,\n" +
                "    away_team_abbreviation VARCHAR(255),\n" +
                "    away_team_location VARCHAR(255),\n" +
                "    away_team_short_name VARCHAR(255),\n" +
                "    away_team_name VARCHAR(255),\n" +
                "    home_team_abbreviation VARCHAR(255),\n" +
                "    home_team_location VARCHAR(255),\n" +
                "    home_team_short_name VARCHAR(255),\n" +
                "    home_team_name VARCHAR(255),\n" +
                "    away_team_goals INT,\n" +
                "    home_team_goals INT\n" +
                ");";
        try(Statement stmt = MysqlCon.getConnection().createStatement()){
            stmt.executeUpdate(sql);
        }
        catch (SQLException e){
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



