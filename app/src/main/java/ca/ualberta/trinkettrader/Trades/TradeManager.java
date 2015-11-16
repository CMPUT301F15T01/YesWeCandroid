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

import ca.ualberta.trinkettrader.Friends.Friend;
import ca.ualberta.trinkettrader.NotificationManager;
import ca.ualberta.trinkettrader.Trades.Trade;
import ca.ualberta.trinkettrader.Trades.TradeArchiver;
import ca.ualberta.trinkettrader.User.User;

/**
 * Manages a user's trades.
 * Each user will have a TradeManager. The TradeManager will be used when
 * a user creates a new trade, deletes a trade, proposes a counter trade,
 * or changes the status of a trade (accepts or declines the trade).
 * Trade manager works with the TradeArchiver class to store and access a user's trades
 * and with the NotificationManager class to notify
 */
public class TradeManager {

    private NotificationManager notificationManager;
    private TradeArchiver tradeArchiver;

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
     * Propose trade.
     *
     * @param trade
     */
    public void proposeTrade(Trade trade) {
        //TODO add trade to currentTrades lists of borrower and owner
        // TODO notify trade receiver (person who did not instantiate trade)
    }

    // TODO pulling trades once the phone has connectivity.
    // TODO do <> break javaDocs? if not used for bold, italics,....
    /**
     * Pulls offered trades when user is online.
     *
     * When a user is offline, they will not be able to receive trade offers from other users.
     * When the user is online, this method will be called to update the user's currentTrades ArrayList.
     *
     * @return ArrayList<Trade> ArrayList of trades that were offered to the user when they were offline
     */
    public ArrayList<Trade> pullTrades(){
        return new ArrayList<Trade>();
    }


    /**
     * Propose counter trade
     *
     * @param trade
     * @param counterTrade
     */
    public void proposeCounterTrade(Trade trade, Trade counterTrade) {
        //TODO replace trade in currentTrades list (find old trade and delete it, add new trade)
    }

    /**
     * Accept trade
     *
     * @param trade
     * @param comments
     */
    public void acceptTrade(Trade trade, String comments) {
        // TODO delete trade from currentTrades list, and use archive method to add it to pastTrades and change status
        // TODO send notification emails to both parties
    }

    /**
     * Decline trade
     *
     * @param trade
     */
    public void declineTrade(Trade trade) {
        // TODO delete trade from current trades list, change trade status and add to past trades list (archive it)
    }

    // TODO this could be deleted, because there is already a TradeArchiver deleteTrade method.
    // TODO we would be calling deleteTrade.deleteTrade
    /**
     * Delete trade
     *
     * @param trade
     */
    public void deleteTrade(Trade trade) {

    }

    /**
     * Returns user's trade archiver.
     *
     * Please see TradeArchiver for more information.
     * @return TradeArchiver
     */
    public TradeArchiver getTradeArchiver() {
        return tradeArchiver;
    }
    // TODO think that one is already instantiated in User class.  need to check.
    /**
     * Returns  user's notification manager.
     *
     * Please see NotificationManager class for more information.
     * @return NotificationManager
     */
    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

}
