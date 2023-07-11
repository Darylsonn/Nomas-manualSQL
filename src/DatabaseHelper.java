import NHL_Class.Game;
import NHL_Class.Goal;

import java.sql.*;
import java.util.Date;

public class DatabaseHelper {
    // Function to create Files table
    public static void createFilesTable(){
        String sql = "CREATE TABLE IF NOT EXISTS files(" +
                "file_id INTEGER not NULL AUTO_INCREMENT, " +
                "file_name VARCHAR(255), " +
                "date DATETIME, " +
                "PRIMARY KEY (file_id))";
        try{
            Connection con = MysqlCon.getConnection();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet rs = dbm.getTables(null, null, "files",null);
            if(rs.next()) {
                System.out.println("Table 'files' already exists");
            }else{
                Statement stmt = con.createStatement();
                stmt.executeUpdate(sql);
                System.out.println("Table 'files' created successfully.");
            }
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
                    "game_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY," +
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
        try{
            Connection con = MysqlCon.getConnection();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet rs = dbm.getTables(null, null, "games",null);
            if(rs.next()) {
                System.out.println("Table 'games' already exists");
            }else{
                Statement stmt = con.createStatement();
                stmt.executeUpdate(sql);
                System.out.println("Table 'games' created successfully.");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Function to create the Goals table
    public static void createGoalsTable() {
            String sql = "CREATE TABLE goals (" +
                    "goal_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "game_id INTEGER NOT NULL," +
                    "team_abbreviation VARCHAR(255)," +
                    "period VARCHAR(255)," +
                    "scorer_player VARCHAR(255)," +
                    "scorer_season_total INT," +
                    "assists_players VARCHAR(255)," +
                    "assists_season_totals INT," +
                    "minute INT," +
                    "second INT," +
                    "empty_net BOOLEAN," +
                    "FOREIGN KEY (game_id) REFERENCES games(game_id)" +
                    ")";
        try{
            Connection con = MysqlCon.getConnection();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet rs = dbm.getTables(null, null, "goals",null);
            if(rs.next()) {
                System.out.println("Table 'goals' already exists");
            }else{
                Statement stmt = con.createStatement();
                stmt.executeUpdate(sql);
                System.out.println("Table 'goals' created successfully.");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    // Function to create the Teams table
    public static void createTeamsTable() {
            String sql = "CREATE TABLE teams (" +
                    "team_id VARCHAR(255) PRIMARY KEY," +
                    "abbreviation VARCHAR(255)," +
                    "location_name VARCHAR(255)," +
                    "short_name VARCHAR(255)," +
                    "team_name VARCHAR(255)" +
                    ")";
        try{
            Connection con = MysqlCon.getConnection();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet rs = dbm.getTables(null, null, "teams",null);
            if(rs.next()) {
                System.out.println("Table 'teams' already exists");
            }else{
                Statement stmt = con.createStatement();
                stmt.executeUpdate(sql);
                System.out.println("Table 'teams' created successfully.");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    // Function to create the GameStats table
    public static void createGameStatsTable() {
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
        try{
            Connection con = MysqlCon.getConnection();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet rs = dbm.getTables(null, null, "gamestats",null);
            if(rs.next()) {
                System.out.println("Table 'gamestats' already exists");
            }else{
                Statement stmt = con.createStatement();
                stmt.executeUpdate(sql);
                System.out.println("Table 'gamestats' created successfully.");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        }

    // Function to create and execute an SQL insert statement for the Games table
    public static void insertGame(Game game, Date date, int awayTeamGoals, int homeTeamGoals) {

            String sql = "INSERT INTO games (start_time, away_team_abbreviation, away_team_location, away_team_short_name, away_team_name, home_team_abbreviation, home_team_location, home_team_short_name, home_team_name, away_team_goals, home_team_goals)"
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            Connection con = MysqlCon.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(date.toString()));
            preparedStatement.setString(2, game.getTeams().getAway().getAbbreviation());
            preparedStatement.setString(3, game.getTeams().getAway().getLocationName());
            preparedStatement.setString(4, game.getTeams().getAway().getShortName());
            preparedStatement.setString(5, game.getTeams().getAway().getTeamName());
            preparedStatement.setString(6, game.getTeams().getHome().getAbbreviation());
            preparedStatement.setString(7, game.getTeams().getHome().getLocationName());
            preparedStatement.setString(8, game.getTeams().getHome().getShortName());
            preparedStatement.setString(9, game.getTeams().getHome().getTeamName());
            preparedStatement.setInt(10, awayTeamGoals);
            preparedStatement.setInt(11, homeTeamGoals);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0){
                throw new SQLException("Creating game record failed, now rows affected");
            }
            try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){
                if(generatedKeys.next()){
                    game.setGame_id(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Failed to get game's ID");
                }
            }
            con.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Function to create and execute an SQL insert statement for the Goals table
    public static void insertGoal(Goal goal, Game game) {
            String sql = "INSERT INTO goals (game_id, team_abbreviation, period, scorer_player, scorer_season_total, assists_players, assists_season_totals, minute, second, empty_net) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            Connection con = MysqlCon.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, game.getGame_id());
            preparedStatement.setString(2, goal.team);
            preparedStatement.setString(3, goal.period);
            preparedStatement.setString(4, goal.scorer.player);
            preparedStatement.setInt(5, goal.scorer.seasonTotal);
            if(goal.assists == null || goal.assists.isEmpty()){
                preparedStatement.setNull(6, Types.VARCHAR);
                preparedStatement.setNull(7, Types.INTEGER);
            }else {
                preparedStatement.setString(6, goal.assists.get(0).player);
                preparedStatement.setInt(7, goal.assists.get(0).seasonTotal);
            }
            preparedStatement.setInt(8, goal.min);
            preparedStatement.setInt(9, goal.sec);
            preparedStatement.setBoolean(10, goal.emptyNet);
            preparedStatement.executeUpdate();
            con.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Function to create and execute an SQL insert statement for the Teams table
    public static void insertTeam(int teamId, String abbreviation, String locationName, String shortName, String teamName) {
            String sql = "INSERT INTO teams (team_id, abbreviation, location_name, short_name, team_name) VALUES (?, ?, ?, ?, ?)";
            try{
                Connection con = MysqlCon.getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(sql);

                System.out.println("Team record inserted successfully.");
            }
            catch (SQLException e){
                e.printStackTrace();
            }
    }

    // Function to create and execute an SQL insert statement for the GameStats table
    public static void insertGameStats(int gameId, String teamAbbreviation, int blocked, String faceoffWinPercentage, int giveaways, int hits, int pim, int powerplayGoals, int powerplayOpportunities, String powerplayPercentage, int shots, int takeaways) {
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
