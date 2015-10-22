package ca.ualberta.trinkettrader;

/**
 * Created by dashley on 2015-10-21.
 */
public class Friend {

    private Boolean isTracked;
    private User user;

    public Friend(User user) {
        this.user = user;
    }

    public Friend(Boolean isTracked, User user) {
        this.isTracked = isTracked;
        this.user = user;
    }

    public Boolean getIsTracked() {
        return isTracked;
    }

    public User getUser() {
        return user;
    }

    public void setIsTracked(Boolean isTracked) {
        this.isTracked = isTracked;
    }
}
