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

import ca.ualberta.trinkettrader.NotificationManager;
import ca.ualberta.trinkettrader.Trades.Trade;
import ca.ualberta.trinkettrader.Trades.TradeArchiver;

/**
 * Manages a user's trades.
 * Each user will have a TradeManager which will interact with another user's
 * TradeManager throughout the lifecycle of the trade (trade creation to completion).
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

    /**
     * Propose trade.
     *
     * @param trade
     */
    public void proposeTrade(Trade trade) {
        //TODO add trade to currentTrades lists of borrower and owner
        // TODO notify trade receiver (person who did not instantiate trade)
    }

    /**
     * Propose counter trade
     *
     * @param trade
     * @param counterTrade
     */
    public void proposeCounterTrade(Trade trade, Trade counterTrade) {
        //TODO replace trade in currentTrades list (delete old, add new)
    }

    /**
     * Accept trade
     *
     * @param trade
     * @param comments
     */
    public void acceptTrade(Trade trade, String comments) {
        // TODO call appropriate
    }

    /**
     * Decline trade
     *
     * @param trade
     */
    public void declineTrade(Trade trade) {

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
