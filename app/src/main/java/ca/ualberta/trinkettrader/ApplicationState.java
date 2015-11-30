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

import java.util.ArrayList;
import java.util.Observer;

import ca.ualberta.trinkettrader.Friends.Friend;
import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.Inventory.Trinket.Trinket;
import ca.ualberta.trinkettrader.Trades.Trade;

/**
 * Singleton utility class responsible for handling the clicking of friends in a user's friend's list,
 * trinkets in a user's inventory, and trades in the user's active and past trade lists.
 * <p/>
 * Each time a friend, trinket, or trade is clicked in a list, this class will be used in the
 * <b>onItemClick</b> method of the respective controller to set the object which was clicked.
 * <p/>
 * For more information, please see the respective controllers:
 * for clicked friends - FriendsListController
 * for clicked trinkets - InventoryController
 * for clicked trades - ActiveTradesController , PastTradesController
 */
public class ApplicationState implements ca.ualberta.trinkettrader.Observable {

    private static ApplicationState ourInstance = new ApplicationState();
    private ArrayList<Observer> observers;
    private Trinket clickedTrinket;
    private Friend clickedFriend;
    private Trade clickedTrade;
    private Boolean inCounterTrade = false;
    private Inventory friendsTradeTrinkets = new Inventory();
    private Inventory yourTradeTrinkets = new Inventory();



    private ApplicationState() {
    }

    /**
     * Returns the instance of the singleton
     *
     * @return ApplicationState
     */
    public static ApplicationState getInstance() {
        return ourInstance;
    }

    /**
     * Returns the friend most recently clicked on from the Friends List ListView.
     *
     * @return Friend
     */
    public Friend getClickedFriend() {
        return this.clickedFriend;
    }

    /**
     * Sets the friend most recently clicked on from the Friends List ListView.
     */
    public void setClickedFriend(Friend clickedFriend) {
        this.clickedFriend = clickedFriend;
    }

    /**
     * Returns the trinket most recently clicked on from the Inventory ListView.
     *
     * @return Trinket
     */
    public Trinket getClickedTrinket() {
        return this.clickedTrinket;
    }

    /**
     * Sets the trinket most recently clicked on from the Inventory ListView.
     */
    public void setClickedTrinket(Trinket clickedTrinket) {
        this.clickedTrinket = clickedTrinket;
    }


    /**
     * Gets the Inventory (list of Trinkets) that the user wants from the friend.
     *
     * @return the Inventory (list of Trinkets) representing what the user wants from the friend
     */
    public Inventory getFriendsTradeTrinkets() {
        return friendsTradeTrinkets;
    }


    /**
     * Sets the Inventory (list of Trinkets) that the user wants from the friend.
     */
    public void setFriendsTradeTrinkets(Inventory friendsTradeTrinkets) {
        this.friendsTradeTrinkets = friendsTradeTrinkets;
    }


    /**
     * Gets the Inventory (list of Trinkets) that the user wants to give to the friend.
     *
     * @return the Inventory (list of Trinkets) representing what the user wants to give to the friend
     */
    public Inventory getYourTradeTrinkets() {
        return yourTradeTrinkets;
    }

    /**
     * Sets the Inventory (list of Trinkets) that the user wants to give to the friend.
     */
    public void setYourTradeTrinkets(Inventory yourTradeTrinkets) {
        this.yourTradeTrinkets = yourTradeTrinkets;
    }

    /**
     * Returns the trade most recently clicked on from the Active Trades ListView (currentTradesListView)
     * or the Past Trades ListView (pastTradesListView).
     *
     * @return Trade returns clicked trade
     */
    public Trade getClickedTrade() {
        return this.clickedTrade;
    }

    /**
     * Sets the trinket most recently clicked on from the Active Trades ListView (currentTradesListView)
     * or the Past Trades ListView (pastTradesListView).
     *
     * @param clickedTrade trade that was clicked
     */
    public void setClickedTrade(Trade clickedTrade) {
        // trade was clicked at least once, so not new anymore
        clickedTrade.setNotNewOfferedTrade();  //TODO add to JavaDocs
        this.clickedTrade = clickedTrade;
    }


    public Boolean getInCounterTrade() {
        return inCounterTrade;
    }

    public void setInCounterTrade(Boolean inCounterTrade) {
        this.inCounterTrade = inCounterTrade;
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

}
