package NHL_Class; 
import java.util.ArrayList;
import java.util.List;
public class Goal{
    public int goal_id;
    public String team;
    public String period;
    public NHL_Class.Scorer scorer;
    public ArrayList<Assist> assists;
    public int min;
    public int sec;
    public boolean emptyNet;
    public String strength;
}
