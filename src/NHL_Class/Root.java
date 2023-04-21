package NHL_Class;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
public class Root{
    @JsonProperty("date")
    public Date getDate() {
        return this.date; }
    public void setDate(Date date) {
        this.date = date; }
    Date date;
    @JsonProperty("games")
    public ArrayList<Game> getGames() {
        return this.games; }
    public void setGames(ArrayList<Game> games) {
        this.games = games; }
    public ArrayList<Game> games;
}
