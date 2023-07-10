package NHL_Class; 
public class VAN implements Team{
    public int wins;
    public int losses;
    public int ot;
    public String type;
    public int count;
    public String divisionRank;
    public String leagueRank;
    public String pointsFromPlayoffSpot;
    public int goals;
    public int opportunities;
    public String percentage;

    @Override
    public int getWins() {
        return wins;
    }

    @Override
    public int getLosses() {
        return losses;
    }

    @Override
    public int getOt() {
        return ot;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public String getDivisionRank() {
        return divisionRank;
    }

    @Override
    public String getLeagueRank() {
        return leagueRank;
    }

    @Override
    public String getPointsFromPlayoffSpot() {
        return pointsFromPlayoffSpot;
    }

    @Override
    public int getGoals() {
        return goals;
    }

    @Override
    public int getOpportunities() {
        return opportunities;
    }

    @Override
    public String getPercentage() {
        return percentage;
    }
}
