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

import ca.ualberta.trinkettrader.Friends.TrackedFriends.TrackedFriendsList;
import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.NotificationManager;
import ca.ualberta.trinkettrader.Trades.TradeManager;
import ca.ualberta.trinkettrader.User.Friendable;
import ca.ualberta.trinkettrader.User.Profile.UserProfile;
import ca.ualberta.trinkettrader.User.User;

/**
 * Class representing a friend of the current user.  A friend represents another user of the app who the
 * current user can trade with and, optionally, track.  Each friend has an attribute specifying
 * whether or not they are tracked, and methods to view and modify their tracking status.  If a friend
 * is "tracked", they get added to the user's tracked friends list (in addition to their main friends
 * list), which is a shortlist of the friends the current user are most interested in trading with.
 * If a friend is "untracked" then they are only included in the main friends list.  By default a friend
 * is untracked.
 * <p/>
 * A friend represents another user of the app, and so in addition to their "track" status, they also have
 * the same attributes as the current user, including an inventory and a profile.  The user can view
 * a friend's profile through the FriendsProfileActivity, and view the friend's inventory through the
 * FriendsInventoryActivity.  The user can track or untrack a friend by viewing the friend's profile and
 * checking or unchecking the "Track" checkbox, respectively.  When viewing a friend's inventory you can
 * see all the trinkets the friend has added to their inventory and marked as public (any trinket added
 * to the friends inventory and marked as private will NOT be visible to the current user).
 * <p/>
 * A friend, like the current user, is identified on the system by their email address.  Friends are
 * searched for and listed by their emails, so the current user must know their friend's email address
 * in order to find them.
 *
 * Friending is a one-way opperation, so one user adding a second as a friend does not cause the first
 * user to become the second user's friend as well.  The second user would have to manually add the first
 * user as their friend to make the friending mutual.
 */
public class Friend implements Friendable {

    private Boolean isTracked;
    private String email;
    private transient User actualFriend;

    /**
     * Default constructor. Runs the default constructor of User, initializing all of the
     * friend's attributes to empty objects.  This constructor should only be used for testing purposes,
     * as during normal app operation all friends should be initialized with a valid email address.
     * By default, the tracked status is <code>False</code>, so the friend is only included in the
     * current user's friends list, and not their tracked friends list.
     */
    public Friend() {
        this.isTracked = Boolean.FALSE;
        this.actualFriend = new User();
    }

    /**
     * Constructor that sets all attributes of Friend from the inputs.  It sets the friend's inventory,
     * profile, friend information, and trade information.  This constructor would be called invoked
     * when friend data is being pulled off the network, either when initializing friend data when a user
     * logs in, or when an existing user or friend is searched-for when the app is online.  The data
     * found on the Elastic Search will be used to instantiate the Friend object.
     *
     * @param friendsList         the user's list of Friends
     * @param inventory           the user's inventory
     * @param notificationManager the user's notification manager
     * @param profile             the user's profile
     * @param trackedFriends      a list of the user's tracked friends
     * @param tradeManager        the user's trade manager
     * @param isTracked           Boolean representing the friend's tracking status
     */
    public Friend(FriendsList friendsList, Inventory inventory, NotificationManager notificationManager, UserProfile profile, TrackedFriendsList trackedFriends, TradeManager tradeManager, Boolean isTracked) {
        this.actualFriend = new User(friendsList, inventory, notificationManager, profile, trackedFriends, tradeManager);
        this.isTracked = isTracked;
    }

    /**
     * Constructor that sets only the email address of the Friend. All other attributes are initialized
     * using the default constructor of User (to empty objects), and the tracked status is initialized to
     * the default value of false (not tracked).  This constructor would be invoked if the current user searches
     * for a user whose email address is not on the network, or they search for a user, whose data they have
     * not cached, while the app is offline.  In this case, a friend object with only the email
     * address the current user searched for will be returned.
     *
     * @param email the friend's email address
     */
    public Friend(String email) {
        actualFriend = new User();
        this.actualFriend.getProfile().setEmail(email);
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
