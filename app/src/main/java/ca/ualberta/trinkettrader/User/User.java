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

package ca.ualberta.trinkettrader.User;

import java.util.ArrayList;
import java.util.Observer;

import ca.ualberta.trinkettrader.ElasticStorable;
import ca.ualberta.trinkettrader.Friends.FriendsList;
import ca.ualberta.trinkettrader.Friends.TrackedFriends.TrackedFriendsList;
import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.NotificationManager;
import ca.ualberta.trinkettrader.Trades.TradeManager;
import ca.ualberta.trinkettrader.User.Profile.UserProfile;

/**
 * Abstract class representing a user of the app. This user may be either the
 * LoggedInUser or a Friend. This class mainly acts as a container for all of
 * the various classes that make up a user.
 */

public class User extends ElasticStorable implements ca.ualberta.trinkettrader.Observable {

    protected FriendsList friendsList;
    protected Inventory inventory;
    protected NotificationManager notificationManager;
    protected TrackedFriendsList trackedFriendsList;
    protected TradeManager tradeManager;
    protected UserProfile profile;
    protected Boolean needToSave;
    private ArrayList<Observer> observers;

    /**
     * Public constructor for user: initializes all attribute classes as empty classes with no
     * active data.
     *
     * This constructor is called when the application has no information about the user (i.e. a new
     * User) or when not all information about the user is available yet.
     */
    public User() {
        this.friendsList = new FriendsList();
        this.inventory = new Inventory();
        this.notificationManager = new NotificationManager();
        this.trackedFriendsList = new TrackedFriendsList();
        this.tradeManager = new TradeManager();
        this.profile = new UserProfile();
        this.needToSave = Boolean.TRUE;
    }

    /**
     * A public constructor for User in the case where the user's details are known for all its
     * attribute classes.
     */
    public User(FriendsList friendsList, Inventory inventory, NotificationManager notificationManager, UserProfile profile, TrackedFriendsList trackedFriends, TradeManager tradeManager) {
        this.friendsList = friendsList;
        this.inventory = inventory;
        this.notificationManager = notificationManager;
        this.profile = profile;
        this.trackedFriendsList = trackedFriends;
        this.tradeManager = tradeManager;
        this.needToSave = Boolean.TRUE;
    }

    public User(String email) {

    }

    protected String getJson() {
        return new String();
    }

    protected void queueUpdate() {

    }

    /**
     * Adds the specified observer to the list of observers. If it is already
     * registered, it is not added a second time.
     *
     * @param observer the Observer to add.
     */
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Removes the specified observer from the list of observers. Passing null
     * won't do anything.
     *
     * @param observer the observer to remove.
     */
    @Override
    public synchronized void deleteObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * If {@code hasChanged()} returns {@code true}, calls the {@code update()}
     * method for every observer in the list of observers using null as the
     * argument. Afterwards, calls {@code clearChanged()}.
     * <p/>
     * Equivalent to calling {@code notifyObservers(null)}.
     */
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.notify();
        }
    }

    /**
     * Returns whether User's data needs to be locally cached. This variable is set when a change is
     * made to the User's data.
     *
     * @return Boolean
     */
    public Boolean getNeedToSave() {
        //TODO: need to implement needToSave for friendslist as well...
        return this.needToSave | this.profile.getNeedToSave() | this.inventory.getNeedToSave();
    }

    /**
     * Sets whether User data needs to be locally cached. This Boolean should on be set only when
     * changes to the User's data is made.
     *
     * @param needToSave
     */
    protected void setNeedToSave(Boolean needToSave) {
        this.needToSave = needToSave;
    }

    /**
     * Returns User's friends
     *
     * @return FriendList
     */
    public FriendsList getFriendsList() {
        return friendsList;
    }

    /**
     * Sets user's list of friends
     *
     * @param friendsList
     */
    public void setFriendsList(FriendsList friendsList) {
        this.friendsList = friendsList;
        this.needToSave = Boolean.TRUE;
    }

    /**
     * Returns user's inventory
     *
     * @return Inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Sets user's inventory
     *
     * @param inventory
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
        this.needToSave = Boolean.TRUE;
    }

    /**
     * Returns user's notification manager
     *
     * @return NotificationManager
     */
    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    /**
     * Sets user's notification manager
     *
     * @param notificationManager
     */
    public void setNotificationManager(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }

    /**
     * Returns user's UserProfle
     *
     * @return UserProfile
     */
    public UserProfile getProfile() {
        return profile;
    }

    /**
     * Sets user's UserProfile
     *
     * @param profile
     */
    public void setProfile(UserProfile profile) {
        this.profile = profile;
        this.needToSave = Boolean.TRUE;
    }

    /**
     * Returns user's list of tracked friends
     *
     * @return TrackedFriendsList
     */
    public TrackedFriendsList getTrackedFriendsList() {
        return trackedFriendsList;
    }

    /**
     * Sets user's list of tracked friends
     *
     * @param trackedFriendsList
     */
    public void setTrackedFriends(TrackedFriendsList trackedFriendsList) {
        this.trackedFriendsList = trackedFriendsList;
    }

    /**
     * Returns user's trade manager
     *
     * @return TradeManager
     */
    public TradeManager getTradeManager() {
        return tradeManager;
    }

    /**
     * Sets user's trade manager
     *
     * @param tradeManager
     */
    public void setTradeManager(TradeManager tradeManager) {
        this.tradeManager = tradeManager;
    }
}
