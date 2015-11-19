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

package ca.ualberta.trinkettrader.Friends;

import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.NotificationManager;
import ca.ualberta.trinkettrader.Friends.TrackedFriends.TrackedFriendsList;
import ca.ualberta.trinkettrader.Trades.TradeManager;
import ca.ualberta.trinkettrader.User.Friendable;
import ca.ualberta.trinkettrader.User.User;
import ca.ualberta.trinkettrader.User.Profile.UserProfile;

/**
 * Class representing a friend of a user. All friends are users and therefore have all attributes
 * and methods of a user. Friends have an additional attribute to specify if they are tracked, and
 * they have methods to view and modify their tracking status.
 */
public class Friend implements Friendable{

    private Boolean isTracked;
    private String email;
    private transient User actualFriend;

    /**
     * Default constructor. Runs the default constructor of User, initializing all of the
     * friend's attributes to empty objects.
     * By default, the tracked status is <code>False</code>.
     */
    public Friend() {
        this.isTracked = Boolean.FALSE;
        actualFriend = new User();
    }


    /**
     * Constructor that uses all the attributes of User.
     * Initializes the Friend using these attributes (using User's constructor), then sets the
     * tracked status.
     *
     * @param friendsList the user's list of Friends
     * @param inventory the user's inventory
     * @param notificationManager the user's notification manager
     * @param profile the user's profile
     * @param trackedFriends a list of the user's tracked friends
     * @param tradeManager the user's trade manager
     * @param isTracked Boolean representing the friend's tracking status
     */
    public Friend(FriendsList friendsList, Inventory inventory, NotificationManager notificationManager, UserProfile profile, TrackedFriendsList trackedFriends, TradeManager tradeManager, Boolean isTracked) {
        actualFriend = new User(friendsList, inventory, notificationManager, profile, trackedFriends, tradeManager);
        this.isTracked = isTracked;
    }


    /**
     * Constructor that sets only the username of the Friend. All other attributes are initialized
     * using the default constructor of User (to empty objects).
     *
     * @param username the friend's username
     */
    public Friend(String username) {
        actualFriend = new User();
        this.actualFriend.getProfile().setEmail(username);
        this.isTracked = Boolean.FALSE;
    }


    public User getActualFriend() {
        return actualFriend;
    }

    public void setActualFriend(User actualFriend) {
        this.actualFriend = actualFriend;
    }

    /**
     * Returns the tracked status of the friend as a Boolean.
     *

     * @return <code>True</code> if the friend is tracked; <code>False</code> otherwise
     */
    public Boolean isTracked() {
        return isTracked;
    }


    /**
     * Sets the tracked status of the friend to the specified Boolean value.
     * This method is used when adding and removing a friend from a <code>TrackedFriendsList</code>.
     *
     * @param isTracked Boolean representing the tracking status of the friend
     */
    public void setIsTracked(Boolean isTracked) {
        this.isTracked = isTracked;
    }


    @Override
    public String toString() {
        return this.actualFriend.getProfile().getUsername();
    }
}
