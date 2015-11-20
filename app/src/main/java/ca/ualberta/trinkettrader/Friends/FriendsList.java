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

import ca.ualberta.trinkettrader.User.User;

/**
 * An extension of the built-in Java ArrayList representing a list of Friends.
 * In addition to the functionality of ArrayList, a FriendsList can be searched by username.
 * Also, duplicate Friends (Friends with the same username) cannot be added to the same
 * FriendsList (the ArrayList add() method is overriden).
 */
public class FriendsList extends ArrayList<Friend> implements ca.ualberta.trinkettrader.Observable {

    private ArrayList<Observer> observers;

    /**
     * Default constructor. Uses the ArrayList default constructor; creates an empty list.
     */
    public FriendsList() {
        super();
    }


    /**
     * Constructs a FriendsList using an arbitrary Collection of Users or Friends (subclasses of
     * User). Runs the equivalent constructor in ArrayList.
     * In other words, creates a FriendsList using the specified Collection argument.
     *
     * @param c a Collection of Friend objects; a list of the user's Friends
     */
    public FriendsList(Collection<? extends Friend> c) {
        super(c);
    }


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


    /**
     * Searches for a Friend in the FriendsList using the specified username.
     * Returns the Friend if the username is found; otherwise, returns <code>null</code>
     *
     * @param username the username of the friend to be searched
     * @return the Friend with the specified username if found; otherwise, null
     */
    public Friend getFriendByUsername(String username) {
        for (Friend f : this) {
            if (f.getActualFriend().getProfile().getUsername().equals(username)) {
                return f;
            }
        }
        return null;
    }


    /**
     * Searches for the index of the Friend in the FriendsList using the specified username.
     * Returns the index if the username is found; otherwise, returns <code>null</code>
     *
     * @param username the username of the friend to be searched
     * @return the Friend with the specified username if found; otherwise, null
     */
    public Integer getFriendIndexByUsername(String username) {
        for (int i=0; i<this.size(); i++) {
            if (this.get(i).getActualFriend().getProfile().getUsername().equals(username)) {
                return i;
            }
        }
        return null;
    }


    /**
     * Add friend to friends list.  If that friend is already in the FriendsList it will not be added again.
     *
     * @param friend the Friend to be added to the FriendsList
     * @return True if the Friend is successfully added; otherwise, False
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
