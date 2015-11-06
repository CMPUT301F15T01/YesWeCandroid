package ca.ualberta.trinkettrader;

/**
 * Created by vamaraju on 11/5/2015.
 */
public class ApplicationState {

    private static ApplicationState ourInstance = new ApplicationState();
    private Trinket clickedTrinket;
    private Friend clickedFriend;
    private Trade clickedTrade;

    private ApplicationState() {

    }

    public static ApplicationState getInstance() {
        return ourInstance;
    }

    public Friend getClickedFriend() {
        return this.clickedFriend;
    }

    public void setClickedFriend(Friend clickedFriend) {
        this.clickedFriend = clickedFriend;
    }

    public Trinket getClickedTrinket() {
        return this.clickedTrinket;
    }

    public void setClickedTrinket(Trinket clickedTrinket) {
        this.clickedTrinket = clickedTrinket;
    }

    public Trade getClickedTrade() {return this.clickedTrade; }

    public void setClickedTrade(Trade clickedTrade){ this.clickedTrade = clickedTrade; }
}
