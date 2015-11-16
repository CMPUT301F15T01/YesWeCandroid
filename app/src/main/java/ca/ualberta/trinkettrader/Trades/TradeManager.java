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

public class TradeManager {

    private NotificationManager notificationManager = new NotificationManager();
    private TradeArchiver tradeArchiver = new TradeArchiver();

    /**
     * Public Constructor
     */
    public TradeManager() {
    }

    /**
     * Propose trade
     *
     * @param trade
     */
    public void proposeTrade(Trade trade) {

    }

    /**
     * Propose counter trade
     *
     * @param trade
     * @param counterTrade
     */
    public void proposeCounterTrade(Trade trade, Trade counterTrade) {

    }

    /**
     * Accept trade
     *
     * @param trade
     * @param comments
     */
    public void acceptTrade(Trade trade, String comments) {

    }

    /**
     * Decline trade
     *
     * @param trade
     */
    public void declineTrade(Trade trade) {

    }

    /**
     * Delete trade
     *
     * @param trade
     */
    public void deleteTrade(Trade trade) {

    }

    /**
     * Return trade archiver
     *
     * @return TradeArchiver
     */
    public TradeArchiver getTradeArchiver() {
        return tradeArchiver;
    }

    /**
     * Return notification manager
     *
     * @return NotificationManager
     */
    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

}
