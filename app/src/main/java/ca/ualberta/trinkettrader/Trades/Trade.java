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

// TODO how are counter trades affected by 0 to many thing? are borrower and owner
// TODO roles reversed?
/**
 * Trade object which holds all relevant information for a trade. Each trade has
 * an inventory which is a list of the items involved in the trade, a unique TradeManager for the borrower
 * and the owner, and a status.  A borrower's inventory must contain 0 or more items. The owner's inventory will
 * contain one item.
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

    /**
     * public constructor
     * @param offeredItems
     * @param receiver
     * @param requestedItems
     * @param sender
     */
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

    /**
     *
     * @return
     */
    public Inventory getOfferedItems() {
        return offeredItems;
    }

    /**
     *
     * @return
     */
    public TradeManager getReceiver() {
        return receiver;
    }

    /**
     *
     * @return
     */
    public Inventory getRequestedItems() {
        return requestedItems;
    }

    /**
     *
     * @return
     */
    public TradeManager getSender() {
        return sender;
    }

    /**
     * Returns status of the trade. Can be pending, accepted, or declined.
     *
     * @return String
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status of a trade.  Can be pending, accepted, or declined.
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns number of items involved in a particular trade.
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

    // adarshr, accessed on 2015-11-16, http://stackoverflow.com/questions/10734106/how-to-override-tostring-properly-in-java

    // TODO unfinished. Everything mentioned in JavaDoc comment below will be implemented in next prototype.
    // TODO may remodel after profile page with updatable fields
    /**
     * This method override is responsible for determining how trades will be shown to the
     * user when they view their Current Trades list and PAst Trades list.
     *
     * For each trade in the list, it's number, the other person involved in the trade
     * (not LoggedInUser), it's status and the categories of the items involved will be
     * displayed.
     * @return String Text displayed for each trade in current trades list of
     * ActiveTradesActivity and in past trades list of PastTradesActivity.
     */
    @Override
    public String toString(){

        // need to display name of other person involved in trade
        // also need to find categories of items invloved
        return "Trade No. 1 " + "with status " + this.getStatus();
    }
}
