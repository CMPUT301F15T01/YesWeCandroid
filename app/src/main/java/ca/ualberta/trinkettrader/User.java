package ca.ualberta.trinkettrader;

/**
 * Created by dashley on 2015-10-21.
 */
public class User {

    private FriendsList friendsList;
    private Inventory inventory;
    private NotificationManager notificationManager;
    private TrackedFriendsList trackedFriends;
    private TradeManager tradeManager;
    private UserProfile profile;
    private UserSettings userSettings;

    public User() {
    }

    public FriendsList getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(FriendsList friendsList) {
        this.friendsList = friendsList;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public void setNotificationManager(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public TrackedFriendsList getTrackedFriends() {
        return trackedFriends;
    }

    public void setTrackedFriends(TrackedFriendsList trackedFriends) {
        this.trackedFriends = trackedFriends;
    }

    public TradeManager getTradeManager() {
        return tradeManager;
    }

    public void setTradeManager(TradeManager tradeManager) {
        this.tradeManager = tradeManager;
    }

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(UserSettings userSettings) {
        this.userSettings = userSettings;
    }
}
