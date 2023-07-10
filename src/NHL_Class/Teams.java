package NHL_Class; 
public class Teams{
    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public int team_id;
    public Away getAway() {
        return away;
    }

    public Home getHome() {
        return home;
    }

    public Away away;
    public Home home;
}
