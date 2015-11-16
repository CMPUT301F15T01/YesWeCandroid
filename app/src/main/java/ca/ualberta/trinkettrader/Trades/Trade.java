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

import ca.ualberta.trinkettrader.Inventory.Inventory;

// TODO add info from specs about number of items (0 to many)
/**
 * Trade object which holds all relevant information for a trade. Each trade has
 * an inventory which is a list of the items involved in the trade, a unique TradeManager for the borrower
 * and the owner, and a status.
 *
 */
public class Trade implements ca.ualberta.trinkettrader.Observable {

    private ArrayList<Observer> observers;
    private Inventory offeredItems;
    private Inventory requestedItems;
    private String status;
    private TradeManager receiver;
    private TradeManager sender;
    private Integer numberOfItems;

    public Trade(Inventory offeredItems, TradeManager receiver, Inventory requestedItems, TradeManager sender) {
        this.offeredItems = offeredItems;
        this.receiver = receiver;
        this.requestedItems = requestedItems;
        this.sender = sender;
        this.status = "pending"; // TODO need to clarify what status names will be
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

    public Inventory getOfferedItems() {
        return offeredItems;
    }

    public TradeManager getReceiver() {
        return receiver;
    }

    public Inventory getRequestedItems() {
        return requestedItems;
    }

    public TradeManager getSender() {
        return sender;
    }

    /**
     * Returns status of the trade. Can be pending, accepted, or rejected.
     *
     * @return String
     */
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns number of items involved in the trades.
     *
     * @return Integer
     */
    public Integer getNumberOfItems() {
        return numberOfItems;
    }

    /**
     * Sets number of items in the trade.
     *
     * @param numberOfItems
     */
    public void setNumberOfItems(Integer numberOfItems) {
        this.numberOfItems = numberOfItems;
    }
}
