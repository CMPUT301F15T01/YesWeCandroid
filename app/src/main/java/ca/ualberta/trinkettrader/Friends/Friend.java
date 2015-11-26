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
 * Class representing a friend of the current user.  A friend represents another user of the app who the
 * current user can trade with and, optionally, track.  Each friend has an attribute specifying
 * whether or not they are tracked, and methods to view and modify their tracking status.  If a friend
 * is "tracked", they get added to the user's tracked friends list (in addition to their main friends
 * list), which is a shortlist of the friends the current user are most interested in trading with.
 * If a friend is "untracked" then they are only included in the main friends list.  By default a friend
 * is untracked.
 *
 * A friend represents another user of the app, and so in addition to their "track" status, they also have
 * the same attributes as the current user, including an inventory and a profile.  The user can view
 * a friend's profile through the FriendsProfileActivity, and view the friend's inventory through the
 * FriendsInventoryActivity.  The user can track or untrack a friend by viewing the friend's profile and
 * checking or unchecking the "Track" checkbox, respectively.  When viewing a friend's inventory you can
 * see all the trinkets the friend has added to their inventory and marked as public (any trinket added
 * to the friends inventory and marked as private will NOT be visible to the current user).
 *
 * A friend, like the current user, is identified on the system by their email address.  Friends are
 * searched for and listed by their emails, so the current user must know their friend's email address
 * in order to find them.
 *
 *
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

// reader must be able to find whateach term means, when should they be using (5 w's).  what uses it, etc.
    // who calls it, what they are calling it with.  who loads it.  should be useable by another developer
    // structure, etc. can be described by uml.  need to know what stuff is actually useful for.
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
