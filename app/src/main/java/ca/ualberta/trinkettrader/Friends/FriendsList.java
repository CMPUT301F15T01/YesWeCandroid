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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observer;

/**
 * An ArrayList of all the {@link Friend Friends} the user has.  This list includes all tracked and
 * untracked friends.  Friends are ordered in the arraylist by the order in which the user added them
 * as friends (first-added appears first in the list).  In addition to the basic functionality of
 * {@link ArrayList ArrayLists}, FriendsLists can be searched by the friend's email address.  Additionally,
 * the {@link ArrayList ArrayList's} add function is overridden so that the same Friend cannot be added
 * to the current user's FriendsList multiple times.
 *
 * The current user's FriendsList can be viewed, and new friends can be added to it, in the FriendsListActivity.
 */
public class FriendsList extends ArrayList<Friend> implements ca.ualberta.trinkettrader.Observable {

    private ArrayList<Observer> observers;

    /**
     * Default constructor used when the current user logs in for the first time (thereby creating their
     * account). A user begins with no friends, so an empty FriendsList is instantiated using the
     * default {@link ArrayList ArrayList} constructor.
     */
    public FriendsList() {
        super();
    }


    /**
     * Constructor for instantiating the FriendsList of an existing user.  Constructs a FriendsList from
     * an arbitrary {@link Collection Collection} of Friends using the equivalent {@link ArrayList ArrayList}
     * constructor.  The given collection can be empty.
     *
     * This constructor should be called when a user with an existing account logs in.  The FriendsList is
     * instantiated with the user's previous FriendsList data.  This data is retrieved from the Elastic Search
     * server if the device is online, or from the user's local phone storage if it is offline.  If the
     * user has no friends, this constructor will still be called with an empty collection.
     *
     * @param c a Collection of Friend objects; a list of the user's Friends
     */
    public FriendsList(Collection<? extends Friend> c) {
        super(c);
    }


    // TODO: Can this be removed?
    /**
     * Initializes a FriendsList to the specified capacity.
     * Useful if the user knows the number of Friends in advance.
     * Runs the equivalent constructor in ArrayList.
     *
     * @param initialCapacity capacity of the FriendsList
     */
    public FriendsList(int initialCapacity) {
        super();
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
     * Equivalent to calling {@code notifyObservers(null)}.
     */
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.notify();
        }
    }


    // TODO: When is this called?
    /**
     * Searches for a Friend in the FriendsList using the specified username (the friends email address).
     * Returns the Friend object if a friend with that email address is found in the FriendsList; otherwise,
     * returns <code>null</code>
     *
     * @param username - the username of the friend to be searched
     * @return Friend - the Friend with the specified username if found; otherwise, null
     */
    public Friend getFriendByUsername(String username) {
        for (Friend f : this) {
            if (f.getActualFriend().getProfile().getUsername().equals(username)) {
                return f;
            }
        }
        return null;
    }


    // TODO: When is this called?  Testing only?
    /**
     * Searches for the index of the Friend in the FriendsList using the specified username (the friend's
     * email address).  Returns the index of the Friend with that email address in the FriendsList if
     * a Friend with that email address is found in the FriendsList; otherwise, returns <code>null</code>.
     * Friends are ordered int the FriendsList in the order they are added, with the Friend that is added
     * first at index 0.
     *
     * @param username - the username of the friend to be searched
     * @return Integer - the index in the FriendsList of the Friend with the specified email address if found; otherwise, null
     */
    public Integer getFriendIndexByUsername(String username) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getActualFriend().getProfile().getUsername().equals(username)) {
                return i;
            }
        }
        return null;
    }


    /**
     * Add a new friend to FriendsList.  The method returns a Boolean to indicate if the friend was successfully
     * added to the list or not.  If the friend was successfully added then <code>add</code> returns True.
     * If not <code>add</code> returns False.  The most likely reason for <code>add</code> to fail is if
     * the user tries to add a friend that is already in their FriendsList.  Duplicates friends are not permitted,
     * so if the friend being added to the current user's FriendsList is already in their FriendsList then
     * that Friend will not be added again and <code>add</code> will return False.
     *
     * This method is invoked by the FriendsListController after a user tries to add a new Friend from the
     * FriendsListActivity.
     *
     * @param friend - the Friend to be added to the current user's FriendsList
     * @return Boolean - True if the Friend is successfully added; otherwise, False
     */
    @Override
    public boolean add(Friend friend) {
        for (Friend f : this) {
            if (f.getActualFriend().getProfile().getUsername().equals(friend.getActualFriend().getProfile().getUsername())) {
                return false;
            }
        }
        this.add(this.size(), friend);
        return true;
    }

}
