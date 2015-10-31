// Copyright 2015 Andrea McIntosh, Dylan Ashley, Anju Eappen, Jenna Hatchard, Kirsten Svidal, Raghav Vamaraju
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package ca.ualberta.trinkettrader;

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
