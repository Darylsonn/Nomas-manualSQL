package NHL_Class; 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class Game{
    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public int game_id;
    public NHL_Class.Status status;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date startTime;
    public ArrayList<Goal> goals;
    public NHL_Class.Scores scores;

    public Status getStatus() {
        return status;
    }

    public ArrayList<Goal> getGoals() {
        return goals;
    }

    public Scores getScores() {
        return scores;
    }

    public Teams getTeams() {
        return teams;
    }

    public CurrentStats getCurrentStats() {
        return currentStats;
    }

    public GameStats getGameStats() {
        return gameStats;
    }

    public NHL_Class.Teams teams;
    public NHL_Class.CurrentStats currentStats;
    public NHL_Class.GameStats gameStats;
}