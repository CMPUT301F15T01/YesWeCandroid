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

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import ca.ualberta.trinkettrader.Elastic.ElasticStorable;
import ca.ualberta.trinkettrader.Elastic.SearchHit;
import ca.ualberta.trinkettrader.Friends.TrackedFriends.TrackedFriendsList;
import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.NotificationManager;
import ca.ualberta.trinkettrader.Trades.TradeManager;
import ca.ualberta.trinkettrader.User.LoggedInUser;
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
 * checking or unchecking the "Track" radio button, respectively.  When viewing a friend's inventory you can
 * see all the trinkets the friend has added to their inventory and marked as public (any trinket added
 * to the friends inventory and marked as private will NOT be visible to the current user).
 * <p/>
 * A friend, like the current user, is identified on the system by their email address.  Friends are
 * searched for and listed by their emails, so the current user must know their friend's email address
 * in order to find them.
 * <p/>
 * Friending is a one-way operation, so one user adding a second as a friend does not cause the first
 * user to become the second user's friend as well.  The second user would have to manually add the first
 * user as their friend to make the friending mutual.
 */
public class Friend {

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
        this.getActualFriend().getProfile().setUsername(email);
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
        this.actualFriend.setEmail(email);
        this.actualFriend.getTradeManager().setUsername(email);
        this.isTracked = Boolean.FALSE;
    }


    /**
     * Returns the  User object that Friend class delegate user-like operations to.  Friends represent
     * other users of the system and so are like users, but they do not require all properties of User as
     * Friends do not need to keep track of their own friends lists.  To represent these user-like qualities
     * each Friend stores a user object.  This User object is delegated to to manage the friend's
     * inventory and profile data.
     *
     * @return User - the User object that stores the Friend's profile and inventory, and performs operations
     * on them.
     */
    public User getActualFriend() {
        try {
            actualFriend.getFromNetwork();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return actualFriend;
    }

    public void setActualFriend(User actualFriend) {
        this.actualFriend = actualFriend;
    }

    /**
     * Returns the tracked status of the friend as a Boolean.  If the friend is tracked  isTracked()
     * returns <code>True</code>, and if it is not tracked the function returns <code>False</code>. This
     * method is called by the FriendsListActivity to determine whether a friend should be listed in
     * the tracked friends list or not, and by the FriendsProfileActivity to set whether or not the
     * "Tracked" radio button is checked or not when the friend's profile is viewed.
     *
     * @return Boolean - <code>True</code> if the friend is tracked; <code>False</code> otherwise
     */
    public Boolean isTracked() {
        return isTracked;
    }


    /**
     * Sets the tracked status of the friend to the specified Boolean value.  The friend's tracked status
     * can be changed by checking/unchecking the "Tracked" radio button.  Checking the radio button sets the
     * tracked status to tracked, represented by the <code>True</code>, and unchecking the radio button sets
     * the tracked status to untracked, or <code>False</code>.  Based on how tracked is set, the friend is
     * added or removed from the current user's <code>TrackedFriendsList</code>.
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
