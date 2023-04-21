import java.sql.*;

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
    public static void createTableNHLScoreboard(){
        String sql = "CREATE TABLE IF NOT EXISTS NHL_SCOREBOARD " +
                "(id INTEGER not NULL AUTO_INCREMENT, " +
                " DATE VARCHAR(255), " +
                " ANA_M VARCHAR(255), " +
                " ANA_W VARCHAR(255), " +
                " ANA_T VARCHAR(255), " +
                " ANA_L VARCHAR(255), " +
                " ANA_GW VARCHAR(255), " +
                " ANA_GL VARCHAR(255), " +
                " ARI_M VARCHAR(255), " +
                " ARI_W VARCHAR(255), " +
                " ARI_T VARCHAR(255), " +
                " ARI_L VARCHAR(255), " +
                " ARI_GW VARCHAR(255), " +
                " ARI_GL VARCHAR(255), " +
                " BOS_M VARCHAR(255), " +
                " BOS_W VARCHAR(255), " +
                " BOS_T VARCHAR(255), " +
                " BOS_L VARCHAR(255), " +
                " BOS_GW VARCHAR(255), " +
                " BOS_GL VARCHAR(255), " +
                " BUF_M VARCHAR(255), " +
                " BUF_W VARCHAR(255), " +
                " BUF_T VARCHAR(255), " +
                " BUF_L VARCHAR(255), " +
                " BUF_GW VARCHAR(255), " +
                " BUF_GL VARCHAR(255), " +
                " CAR_M VARCHAR(255), " +
                " CAR_W VARCHAR(255), " +
                " CAR_T VARCHAR(255), " +
                " CAR_L VARCHAR(255), " +
                " CAR_GW VARCHAR(255), " +
                " CAR_GL VARCHAR(255), " +
                " CBJ_M VARCHAR(255), " +
                " CBJ_W VARCHAR(255), " +
                " CBJ_T VARCHAR(255), " +
                " CBJ_L VARCHAR(255), " +
                " CBJ_GW VARCHAR(255), " +
                " CBJ_GL VARCHAR(255), " +
                " CGY_M VARCHAR(255), " +
                " CGY_W VARCHAR(255), " +
                " CGY_T VARCHAR(255), " +
                " CGY_L VARCHAR(255), " +
                " CGY_GW VARCHAR(255), " +
                " CGY_GL VARCHAR(255), " +
                " CHI_M VARCHAR(255), " +
                " CHI_W VARCHAR(255), " +
                " CHI_T VARCHAR(255), " +
                " CHI_L VARCHAR(255), " +
                " CHI_GW VARCHAR(255), " +
                " CHI_GL VARCHAR(255), " +
                " COL_M VARCHAR(255), " +
                " COL_W VARCHAR(255), " +
                " COL_T VARCHAR(255), " +
                " COL_L VARCHAR(255), " +
                " COL_GW VARCHAR(255), " +
                " COL_GL VARCHAR(255), " +
                " DAL_M VARCHAR(255), " +
                " DAL_W VARCHAR(255), " +
                " DAL_T VARCHAR(255), " +
                " DAL_L VARCHAR(255), " +
                " DAL_GW VARCHAR(255), " +
                " DAL_GL VARCHAR(255), " +
                " DET_M VARCHAR(255), " +
                " DET_W VARCHAR(255), " +
                " DET_T VARCHAR(255), " +
                " DET_L VARCHAR(255), " +
                " DET_GW VARCHAR(255), " +
                " DET_GL VARCHAR(255), " +
                " EDM_M VARCHAR(255), " +
                " EDM_W VARCHAR(255), " +
                " EDM_T VARCHAR(255), " +
                " EDM_L VARCHAR(255), " +
                " EDM_GW VARCHAR(255), " +
                " EDM_GL VARCHAR(255), " +
                " FLA_M VARCHAR(255), " +
                " FLA_W VARCHAR(255), " +
                " FLA_T VARCHAR(255), " +
                " FLA_L VARCHAR(255), " +
                " FLA_GW VARCHAR(255), " +
                " FLA_GL VARCHAR(255), " +
                " LAK_M VARCHAR(255), " +
                " LAK_W VARCHAR(255), " +
                " LAK_T VARCHAR(255), " +
                " LAK_L VARCHAR(255), " +
                " LAK_GW VARCHAR(255), " +
                " LAK_GL VARCHAR(255), " +
                " MIN_M VARCHAR(255), " +
                " MIN_W VARCHAR(255), " +
                " MIN_T VARCHAR(255), " +
                " MIN_L VARCHAR(255), " +
                " MIN_GW VARCHAR(255), " +
                " MIN_GL VARCHAR(255), " +
                " MTL_M VARCHAR(255), " +
                " MTL_W VARCHAR(255), " +
                " MTL_T VARCHAR(255), " +
                " MTL_L VARCHAR(255), " +
                " MTL_GW VARCHAR(255), " +
                " MTL_GL VARCHAR(255), " +
                " NJD_M VARCHAR(255), " +
                " NJD_W VARCHAR(255), " +
                " NJD_T VARCHAR(255), " +
                " NJD_L VARCHAR(255), " +
                " NJD_GW VARCHAR(255), " +
                " NJD_GL VARCHAR(255), " +
                " NSH_M VARCHAR(255), " +
                " NSH_W VARCHAR(255), " +
                " NSH_T VARCHAR(255), " +
                " NSH_L VARCHAR(255), " +
                " NSH_GW VARCHAR(255), " +
                " NSH_GL VARCHAR(255), " +
                " NYI_M VARCHAR(255), " +
                " NYI_W VARCHAR(255), " +
                " NYI_T VARCHAR(255), " +
                " NYI_L VARCHAR(255), " +
                " NYI_GW VARCHAR(255), " +
                " NYI_GL VARCHAR(255), " +
                " NYR_M VARCHAR(255), " +
                " NYR_W VARCHAR(255), " +
                " NYR_T VARCHAR(255), " +
                " NYR_L VARCHAR(255), " +
                " NYR_GW VARCHAR(255), " +
                " NYR_GL VARCHAR(255), " +
                " OTT_M VARCHAR(255), " +
                " OTT_W VARCHAR(255), " +
                " OTT_T VARCHAR(255), " +
                " OTT_L VARCHAR(255), " +
                " OTT_GW VARCHAR(255), " +
                " OTT_GL VARCHAR(255), " +
                " PHI_M VARCHAR(255), " +
                " PHI_W VARCHAR(255), " +
                " PHI_T VARCHAR(255), " +
                " PHI_L VARCHAR(255), " +
                " PHI_GW VARCHAR(255), " +
                " PHI_GL VARCHAR(255), " +
                " PIT_M VARCHAR(255), " +
                " PIT_W VARCHAR(255), " +
                " PIT_T VARCHAR(255), " +
                " PIT_L VARCHAR(255), " +
                " PIT_GW VARCHAR(255), " +
                " PIT_GL VARCHAR(255), " +
                " SEA_M VARCHAR(255), " +
                " SEA_W VARCHAR(255), " +
                " SEA_T VARCHAR(255), " +
                " SEA_L VARCHAR(255), " +
                " SEA_GW VARCHAR(255), " +
                " SEA_GL VARCHAR(255), " +
                " SJS_M VARCHAR(255), " +
                " SJS_W VARCHAR(255), " +
                " SJS_T VARCHAR(255), " +
                " SJS_L VARCHAR(255), " +
                " SJS_GW VARCHAR(255), " +
                " SJS_GL VARCHAR(255), " +
                " STL_M VARCHAR(255), " +
                " STL_W VARCHAR(255), " +
                " STL_T VARCHAR(255), " +
                " STL_L VARCHAR(255), " +
                " STL_GW VARCHAR(255), " +
                " STL_GL VARCHAR(255), " +
                " TBL_M VARCHAR(255), " +
                " TBL_W VARCHAR(255), " +
                " TBL_T VARCHAR(255), " +
                " TBL_L VARCHAR(255), " +
                " TBL_GW VARCHAR(255), " +
                " TBL_GL VARCHAR(255), " +
                " TOR_M VARCHAR(255), " +
                " TOR_W VARCHAR(255), " +
                " TOR_T VARCHAR(255), " +
                " TOR_L VARCHAR(255), " +
                " TOR_GW VARCHAR(255), " +
                " TOR_GL VARCHAR(255), " +
                " VAN_M VARCHAR(255), " +
                " VAN_W VARCHAR(255), " +
                " VAN_T VARCHAR(255), " +
                " VAN_L VARCHAR(255), " +
                " VAN_GW VARCHAR(255), " +
                " VAN_GL VARCHAR(255), " +
                " VGK_M VARCHAR(255), " +
                " VGK_W VARCHAR(255), " +
                " VGK_T VARCHAR(255), " +
                " VGK_L VARCHAR(255), " +
                " VGK_GW VARCHAR(255), " +
                " VGK_GL VARCHAR(255), " +
                " WPG_M VARCHAR(255), " +
                " WPG_W VARCHAR(255), " +
                " WPG_T VARCHAR(255), " +
                " WPG_L VARCHAR(255), " +
                " WPG_GW VARCHAR(255), " +
                " WPG_GL VARCHAR(255), " +
                " WSH_M VARCHAR(255), " +
                " WSH_W VARCHAR(255), " +
                " WSH_T VARCHAR(255), " +
                " WSH_L VARCHAR(255), " +
                " WSH_GW VARCHAR(255), " +
                " WSH_GL VARCHAR(255), " +
                " PRIMARY KEY ( id ))";
        try{
            Statement stmt = MysqlCon.getConnection().createStatement();
            stmt.executeUpdate(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void insertRecordTableNHLScoreborad(String date){
        String sql = "INSERT INTO NHL_SCOREBOARD(DATE) VALUES (?)";
        try{
            PreparedStatement preparedStatement = MysqlCon.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, date);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}



