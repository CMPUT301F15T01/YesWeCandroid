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

public abstract class User {

    protected FriendsList friendsList;
    protected Inventory inventory;
    protected NotificationManager notificationManager;
    protected TrackedFriendsList trackedFriends;
    protected TradeManager tradeManager;
    protected UserProfile profile;

    public User() {
        this.friendsList = new FriendsList();
        this.inventory = new Inventory();
        this.notificationManager = new NotificationManager();
        this.trackedFriends = new TrackedFriendsList();
        this.tradeManager = new TradeManager();
        this.profile = new UserProfile();
    }

    public User(FriendsList friendsList, Inventory inventory, NotificationManager notificationManager, UserProfile profile, TrackedFriendsList trackedFriends, TradeManager tradeManager) {
        this.friendsList = friendsList;
        this.inventory = inventory;
        this.notificationManager = notificationManager;
        this.profile = profile;
        this.trackedFriends = trackedFriends;
        this.tradeManager = tradeManager;
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
}
