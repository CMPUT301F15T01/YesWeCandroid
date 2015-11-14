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

public class TradeArchiver implements ca.ualberta.trinkettrader.Observable {

    private ArrayList<Observer> observers;

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
        for (Observer observer: observers) {
            observer.notify();
        }
    }

    private ArrayList<Trade> currentTrades = new ArrayList<Trade>();
    private ArrayList<Trade> pastTrades = new ArrayList<Trade>();


    public TradeArchiver() {

    }

    /**
     * Add a trade to the archive.
     * @param trade
     */

    public void addTrade(Trade trade) {

    }

    public void archiveTrade(Trade trade) { pastTrades.add(trade); }

    // TODO implementation details: will only be used to update currentTrades
    public void deleteTrade(Trade trade) {
        // find trade in list and delete. need to delete anything anywhere else?
    }

    public ArrayList<Trade> getPastTrades() { return pastTrades; }

    public ArrayList<Trade> getCurrentTrades() { return currentTrades; }

    // will return trade from ArrayList of pastTrades

    /**
     * Returns a trade from list of past trades.
     * @param trade
     * @return Trade
     */
    public Trade getPastTrade(Trade trade){
        return trade;
    }

    /**
     * Returns boolean indicating if there is a pending trade.
     * @param trade
     * @return Boolean
     */
    public Boolean hasCurrentTrade(Trade trade){
        return Boolean.TRUE;
    }

    /**
     * Returns boolean indicating if there are any closed trades.
     * @param trade
     * @return Boolean
     */
    public Boolean hasPastTrade(Trade trade){
        return Boolean.TRUE;
    }

    public Boolean isCurrentTradesEmpty(){ return currentTrades.isEmpty(); }

    public Boolean isPastTradesEmpty(){ return pastTrades.isEmpty(); }
}
