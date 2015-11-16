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
import java.util.Observer;

import ca.ualberta.trinkettrader.Trades.Trade;

/**
 * Responsible for the storage of a user's current (active) and
 * past (inactive) trades.
 */
public class TradeArchiver implements ca.ualberta.trinkettrader.Observable {

    private ArrayList<Observer> observers;
    private ArrayList<Trade> currentTrades;
    private ArrayList<Trade> pastTrades;

    public TradeArchiver() {
    }


    /**
     * Add a trade to currentTrades ArrayList.
     *
     * @param trade
     */
    public void addTrade(Trade trade) {

    }

    /**
     * Inserts specified trade into pastTrades ArrayList. Only trades that have been
     * accepted or declined will be archived.
     * A trade will <b>not</b> be archived if it is deleted.
     * @param trade
     */
    public void archiveTrade(Trade trade) {

    }

    // TODO implementation details: will only be used to update currentTrades
    /**
     * Deletes specified trade from currentTrades ArrayList. Trades will <b>never</b> be
     * deleted from pastTrades ArrayList.  This method will only be used to update the currentTrades
     * ArrayList when a trade is no longer active (ie. trade has been accepted, declined or deleted).
     * @param trade
     */
    public void deleteTrade(Trade trade) {

    }

    public ArrayList<Trade> getPastTrades() {
        return pastTrades;
    }


    public ArrayList<Trade> getCurrentTrades() {
        return currentTrades;
    }

    // TODO I can see how this would be useful, but currently it's useless.
    /**
     * Returns specified trade from list of past trades.
     *
     * @param trade
     * @return Trade
     */
    public Trade getPastTrade(Trade trade) { return trade; }

    /**
     * Returns boolean indicating if a trade is a current (active) trade.
     * Current trades are trades that <b>have not</b> been accepted or declined, and are stored
     * in the currentTrades ArrayList. A current trade's status is "pending".
     *
     * @param trade
     * @return Boolean
     */
    public Boolean hasCurrentTrade(Trade trade) {
        return Boolean.TRUE;
    }

    /**
     * Returns boolean indicating if a trade is a past (inactive) trade.
     * Past trades are trades that <b>have</b> been accepted or declined, and are stored
     * in the pastTrades ArrayList.  A past trade's status is either "accepted" or "declined".
     *
     * @param trade
     * @return Boolean
     */
    public Boolean hasPastTrade(Trade trade) {
        return Boolean.TRUE;
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
