package ca.ualberta.trinkettrader;

/**
 * Created by vamaraju on 11/5/2015.
 */
public class ApplicationState {

    private static ApplicationState ourInstance = new ApplicationState();
    private Friend clickedFriend;

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



}
