package ca.ualberta.trinkettrader;

/**
 * Created by vamaraju on 11/5/2015.
 */
public class ApplicationState {

    private static ApplicationState ourInstance = new ApplicationState();
    private User clickedFriend;
    private Trinket clickedTrinket;

    private ApplicationState() {

    }

    public static ApplicationState getInstance() {
        return ourInstance;
    }

    public User getClickedFriend() {
        return this.clickedFriend;
    }

    public void setClickedFriend(User clickedFriend) {
        this.clickedFriend = clickedFriend;
    }

    public Trinket getClickedTrinket() {
        return this.clickedTrinket;
    }

    public void setClickedTrinket(Trinket clickedTrinket) {
        this.clickedTrinket = clickedTrinket;
    }
}
