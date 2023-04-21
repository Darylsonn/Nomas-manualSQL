package NBA_Class;

import java.util.Date;

public class Game{
    public int seasonStageId;
    public String seasonYear;
    public String leagueName;
    public String gameId;
    public Arena arena;
    public boolean isGameActivated;
    public int statusNum;
    public int extendedStatusNum;
    public String startTimeEastern;
    public Date startTimeUTC;
    public Date endTimeUTC;
    public String startDateEastern;
    public String homeStartDate;
    public String homeStartTime;
    public String visitorStartDate;
    public String visitorStartTime;
    public String gameUrlCode;
    public String clock;
    public boolean isBuzzerBeater;
    public boolean isPreviewArticleAvail;
    public boolean isRecapArticleAvail;
    public Nugget nugget;
    public String attendance;
    public Tickets tickets;
    public boolean hasGameBookPdf;
    public boolean isStartTimeTBD;
    public boolean isNeutralVenue;
    public GameDuration gameDuration;
    public Period period;
    public VTeam vTeam;
    public HTeam hTeam;
    public Watch watch;
}
