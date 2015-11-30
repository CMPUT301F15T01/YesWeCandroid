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

import org.apache.http.NameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import ca.ualberta.trinkettrader.Elastic.ElasticStorable;
import ca.ualberta.trinkettrader.NotificationManager;
import ca.ualberta.trinkettrader.User.LoggedInUser;

/**
 * Class for managing a user's trades.
 * Each user will have a TradeManager. The TradeManager will be used when
 * a user creates a new trade, deletes a trade, proposes a counter trade,
 * or changes the status of a trade (accepts or declines the trade).
 * Trade manager works with the TradeArchiver class to store and access a user's trades,
 * and with the NotificationManager class to notify the user of a new trade that another user has proposed
 * to them.
 */
public class TradeManager extends ElasticStorable {

    private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t01/trade/";
    private static final String SEARCH_URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t01/trade/_search";
    private static final String TAG = "trade";

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

    /**
     * Constructor that initializes the username (email address) of the {@link ca.ualberta.trinkettrader.Friends.Friend Friend}
     * the user is trading with.  Creates an empty {@link TradeArchiver TradeArchiver} to archive this trade
     * in the user's lists of current/past trades, and an empty NotificationManager to handle notifications
     * to and from the other user in the trade.  This TradeManager is initialized without a
     * {@link ca.ualberta.trinkettrader.Friends.Friend Friend}  to trade with.
     *
     * @param username (email address) of the Friend this TradeManager is handling a trade with
     */
    public TradeManager(String username) {
        this.username = username;
        notificationManager = new NotificationManager();
        tradeArchiver = new TradeArchiver();
    }

    /**
     * Propose trade.
     * Adds created trade to the currentTrades ArrayLists of both the borrower
     * and the owner.  Notifies the user who did not instantiate the trade that
     * they have been made a new offer.
     *
     * @param trade Trinket exchange between a borrower and an owner
     */
    public void proposeTrade(Trade trade) {
        // TODO delete? but move JavaDoc
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
        // TODO delete?
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
        //TODO delete? but move JavaDoc
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
        // TODO delete? but move JavaDoc
    }

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
        // TODO delete? but move JavaDoc?
    }

    /**
     * Deletes specified trade from currentTrades ArrayList. Trades will <b>never</b> be
     * deleted from pastTrades ArrayList.  This method will only be used to update the currentTrades
     * ArrayList when a trade is no longer active (ie. trade has been accepted, declined or deleted).
     *
     * @param trade Trinket exchange between a borrower and an owner
     */
    public void deleteTrade(Trade trade) {
        // TODO delete? but move JavaDoc
    }

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
    // TODO think that one is already instantiated in User class.  need to check.

    @Override
    public String getUid() {
        return LoggedInUser.getInstance().getProfile().getUsername();
    }

    /**
     * Searches for ElasticStorable objects on the network matching the attribute and attribute
     * value pairs. Calls onSearchResult with the results when the search completes.
     *
     * @param postParameters pairs of attributes to use when searching
     * @param type
     * @throws IOException
     */
    @Override
    public <T extends ElasticStorable> void searchOnNetwork(ArrayList<NameValuePair> postParameters, Class<T> type) throws IOException {
    }

    /**
     * Attempts to find this object on the elasticsearch server. If the object
     * cannot be found then pushes the current version to the server.
     *
     * @param type class of this object
     * @throws IOException
     */
    @Override
    public <T extends ElasticStorable> void getFromNetwork(Class<T> type) throws IOException {

    }

    /**
     * Default constructor.  Creates an empty {@link TradeArchiver TradeArchiver} to archive this trade
     * in the user's lists of current/past trades, and an empty NotificationManager to handle notifications
     * to and from the other user in the trade.  This TradeManager is initialized without a
     * {@link ca.ualberta.trinkettrader.Friends.Friend Friend}  to trade with.
     */
    @Override
    public <T extends ElasticStorable> void onGetResult(T result) {

    }

    /**
     * Method called after searchOnNetwork gets a response. This method should
     * be overridden to do something with the result.
     *
     * @param result result of searchOnNetwork
     */
    @Override
    public <T extends ElasticStorable> void onSearchResult(Collection<T> result) {
    }

    @Override
    public String getSearchUrl() {
        return SEARCH_URL;
    }

    /**
     * Returns the username (email) of the user the TradeManager belongs to.
     * This method is used by the Trade class to display to display the opposite
     * user involved in a trade when a trade is viewed in the current or past trades
     * list.
     * <p/>
     * Upon trade acceptance, this method will be used to send a user
     * the trade transaction completion email.
     *
     * @return String email of user who TradeManager belongs to
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username (email) of the user the TradeManager belongs to,
     * so the email of the user can be accessed from within a trade object.
     * It is very important that this method be called when a user logs in to
     * the application for the first time (when their account is created).
     * If this method is not called, a user's email will not be accessible within
     * a trade, so trades listed in the current and past trades lists will not
     * display correctly.  Upon trade acceptance, a user will not be able to receive
     * the trade transaction completion email if their email has not been set in their
     * TradeManager.
     *
     * @param username email of user who TradeManager belongs to
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
