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

package ca.ualberta.trinkettrader.Trades;

import java.util.ArrayList;

import ca.ualberta.trinkettrader.Elastic.ElasticStorable;
import ca.ualberta.trinkettrader.NotificationManager;
import ca.ualberta.trinkettrader.User.LoggedInUser;

/**
 * Manages a user's trades.
 * Each user will have a TradeManager. The TradeManager will be used when
 * a user creates a new trade, deletes a trade, proposes a counter trade,
 * or changes the status of a trade (accepts or declines the trade).
 * Trade manager works with the TradeArchiver class to store and access a user's trades
 * and with the NotificationManager class to notify
 */
public class TradeManager extends ElasticStorable {

    private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t01/trade/";
    private static final String SEARCH_URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t01/trade/_search";
    private static final String TAG = "trade";
    /**
     * Method called after searchOnNetwork gets a response. This method should
     * be overridden to do something with the result.
     *
     * @param result result of searchOnNetwork
     */
    @Override
    public <T extends ElasticStorable> void onSearchRe

    @Override
    public String
    retur
            EARCH_URL;
    private NotificationManager notificationManager;
    private TradeArchiver tradeArchiver;
    private String username;

    /**
     * Public Constructor
     */
    public TradeManager() {
        notificationManager = new NotificationManager();
        tradeArchiver = new TradeArchiver();
    }

    // TODO think that receiver of trade proposal should be added to input. will
    // TODO make it easier to send notifications.

    /**
     * Public Constructor that takes in the friend's username.
     */
    public TradeManager(String username) {
        this.username = username;
        notificationManager = new NotificationManager();
        tradeArchiver = new TradeArchiver();
    }

    // TODO pulling trades once the phone has connectivity.

    /**
     * Propose trade.
     * Adds created trade to the currentTrades ArrayLists of both the borrower
     * and the owner.  Notifies the user who did not instantiate the trade that
     * they have been made a new offer.
     *
     * @param trade Trinket exchange between a borrower and an owner
     */
    public void proposeTrade(Trade trade) {
        //TODO add trade to currentTrades lists of borrower and owner
        // TODO notify trade receiver (person who did not instantiate trade)
    }

    /**
     * Pulls offered trades when user is online.
     * <p/>
     * When a user is offline, they will not be able to receive trade offers from other users.
     * When the user is online, this method will be called to update the user's currentTrades ArrayList.
     *
     * @return ArrayList&lt;Trade&gt; ArrayList of trades that were offered to the user when they were offline
     */
    public ArrayList<Trade> pullTrades() {
        return new ArrayList<Trade>();
    }

    /**
     * Propose counter trade.
     * Replaces previously offered trade in the currentTrades ArrayList of both
     * the borrower and the owner with updated trade. Previous trade will be deleted
     * and updated trade will be added.  Counter trade details will be determined by
     * the user who was last offered the trade.
     *
     * @param trade        previous trade to delete
     * @param counterTrade updated counter trade to add
     */
    public void proposeCounterTrade(Trade trade, Trade counterTrade) {
        //TODO replace trade in currentTrades list (find old trade and delete it, add new trade)
    }

    /**
     * Accept trade
     * Sets status of trade to "accepted", deletes trade in borrower's and
     * owner's currentTrades ArrayLists, and adds trade to borrower's and
     * owner's pastTrades ArrayLists. Calls method which will send a notification email
     * to both borrower and owner with relevant trade information and comments.  Comments will be determined
     * by owner.
     * currentTrades and pastTrades ArrayLists
     * are accessed through TradeArchiver.
     *
     * @param trade    Trinket exchange between a borrower and an owner
     * @param comments Important trade related comments determined by owner
     */
    public void acceptTrade(Trade trade, String comments) {
        // TODO delete trade from currentTrades list, and use archive method to add it to pastTrades and change status
        // TODO send notification emails to both parties
    }

    // TODO this could be deleted, because there is already a TradeArchiver deleteTrade method.
    // TODO we would be calling deleteTrade.deleteTrade

    /**
     * Decline trade.
     * Sets status of trade to "declined", deletes trade in borrower's and
     * owner's currentTrades ArrayLists, and adds trade to borrower's and
     * owner's pastTrades ArrayLists. currentTrades and pastTrades ArrayLists
     * are accessed through TradeArchiver.
     *
     * @param trade Trinket exchange between a borrower and an owner
     */
    public void declineTrade(Trade trade) {
        // TODO delete trade from current trades list, change trade status and add to past trades list (archive it)
    }

    /**
     * Deletes specified trade from currentTrades ArrayList. Trades will <b>never</b> be
     * deleted from pastTrades ArrayList.  This method will only be used to update the currentTrades
     * ArrayList when a trade is no longer active (ie. trade has been accepted, declined or deleted).
     *
     * @param trade Trinket exchange between a borrower and an owner
     */
    public void deleteTrade(Trade trade) {

    }
    // TODO think that one is already instantiated in User class.  need to check.

    /**
     * Returns user's trade archiver. Trade archiver is needed to access
     * and manipulate a user's current and past trades.
     * <p/>
     * Please see TradeArchiver for more information.
     *
     * @return TradeArchiver Responsible for the storage and manipulation of a user's current (active) and
     * past (inactive) trades
     */
    public TradeArchiver getTradeArchiver() {
        return tradeArchiver;
    }

    /**
     * Returns  user's notification manager. Notification manager is responsible for
     * notifying a user of a new trades (new offer) and a completed trades.
     * <p/>
     * Please see NotificationManager class for more information.
     *
     * @return NotificationManager Manages receiving, sending and displaying of notifications for user
     */
    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public String getResourceUrl() {
        return RESOURCE_URL;
    }

    @Override
    public String getId() {
        return LoggedInUser.getInstance().getProfile().getUsername();
    } getSearchUrl() {
        return S
        username;
    }
}

    public String getUsername() {
        rname =
                n username;
    }

    public void setUsername(String username) {
        this.usesult(T result) {

        }

    }
