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

;

import java.util.ArrayList;
import java.util.Observer;

import ca.ualberta.trinkettrader.Elastic.ElasticStorable;
import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.Inventory.Trinket.Trinket;
import ca.ualberta.trinkettrader.User.LoggedInUser;

// TODO how are counter trades affected by 0 to many thing? are borrower and owner
// TODO roles reversed?

/**
 * Trade object which holds all relevant information for a trade. Each trade has
 * an inventory which is a list of the items involved in the trade, a unique TradeManager for the borrower
 * and the owner, and a status.  A borrower's inventory must contain 0 or more items. The owner's inventory will
 * contain one item.
 */
public class Trade extends ElasticStorable implements ca.ualberta.trinkettrader.Observable {

    private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t01/trade/";
    private static final String SEARCH_URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t01/trade/_search";
    private static final String TAG = "Trade";

    private ArrayList<Observer> observers;
    private Inventory offeredTrinkets;
    private Inventory requestedTrinkets;

    private String status;
    private transient TradeManager receiver;
    private transient TradeManager sender;
    private Integer numberOfTrinkets;

    private Boolean isNewOfferedTrade;

    /**
     * Constructor that initializes the sender and receiver sides of the trade.  Both parties are initialized
     * with their own {@link Inventory Inventories} (holding the trinkets they are bartering in this trade) and their own
     * {@link TradeManager TradeManagers}.  When a new trade is initialized, the current user who is
     * requesting the trade is the "sender" and the friend who the request is being sent to is the "receiver".
     * By default the trade's status is set to "pending", meaning that the negotiation is still in progress and
     * the trade has neither been accepted nor rejected yet.
     *
     * @param offeredTrinkets   inventory containing offered trinkets
     * @param receiver          TradeManager of user who was offered the trade
     * @param requestedTrinkets inventory containing requested trinkets
     * @param sender            TradeManager of user who instantiated trade
     */
    public Trade(Inventory offeredTrinkets, TradeManager receiver, Inventory requestedTrinkets, TradeManager sender) {
        this.offeredTrinkets = offeredTrinkets;
        this.receiver = receiver;
        this.requestedTrinkets = requestedTrinkets;
        this.sender = sender;
        this.status = "pending"; // TODO need to clarify what status names will be
        this.isNewOfferedTrade = Boolean.TRUE;  // TODO add comments to JavaDocs
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
     *
     * Equivalent to calling {@code notifyObservers(null)}.
     */
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.notify();
        }
    }

    /**
     * Returns the trinket offered by trade sender.  The current user who is proposing the trade is the
     * sender.  They will offer 1 trinket in the trade, which will be returned in an inventory.
     *
     * @return Inventory - Inventory containing offered trinkets
     */
    public Inventory getOfferedTrinkets() {
        return offeredTrinkets;
    }

    /**
     * Returns {@link TradeManager TradeManager} of receiver of trade (user who receives trade offer).
     *
     * @return TradeManager - TradeManager of user who was offered the trade
     */
    public TradeManager getReceiver() {
        return receiver;
    }

    /**
     * Return trinket(s) requested by trade sender.  The current user who is proposing the trade is the
     * sender.  They may request 0 or more trinkets in the trade they are proposing.  All items they are
     * requesting will be returned.  If they are requesting no items then an empty {@link Inventory Inventory}
     * is returned.
     *
     * @return Inventory - Inventory containing requested trinkets
     */
    public Inventory getRequestedTrinkets() {
        return requestedTrinkets;
    }

    /**
     * Returns {@link TradeManager TradeManager} of sender of trade (user who proposes trade offer).
     *
     * @return TradeManager - TradeManager of user who instantiated trade
     */
    public TradeManager getSender() {
        return sender;
    }

    /**
     * Returns number of trinkets involved in a particular trade.  Depending on who this method is called on
     * it will return the number of trinkets that either the sender or receiver has involved in the trade.
     *
     * @return Integer Number of offered or requested trinkets in trade
     */
    public Integer getNumberOfTrinkets() {
        return numberOfTrinkets;
    }

    /**
     * Sets number of trinkets in a particular trade.  Depending on who this method is called on
     * it will set the number of trinkets that either the sender or receiver has involved in the trade.
     *
     * @param numberOfTrinkets Number of offered or requested trinkets in a trade
     */
    public void setNumberOfTrinkets(Integer numberOfTrinkets) {
        this.numberOfTrinkets = numberOfTrinkets;
    }

    // adarshr; http://stackoverflow.com/questions/10734106/how-to-override-tostring-properly-in-java; 2015-11-16
    /**
     * This method override is responsible for determining how trades will be shown to the
     * user when they view their Current Trades list and Past Trades list.
     *
     * For each trade in the list, it's number, the other person involved in the trade
     * (not LoggedInUser) and it's status will be displayed.
     *
     * @return String Text displayed for each trade in current trades list of
     * ActiveTradesActivity and in past trades list of PastTradesActivity
     */
    @Override
    public String toString() {

        String otherUser;
        String status = this.getStatus();
        int tNo;
        // determine name of other user involved in trade
        if(LoggedInUser.getInstance().getProfile().getEmail().equals(receiver.getUsername())){
            otherUser = sender.getUsername();
        }else{
            otherUser = receiver.getUsername();
        }

        // use status to determine which list the trade is in (past trades or current trades)
        // to determine it's number in the list
        if(status.equals("pending")){
            tNo = LoggedInUser.getInstance().getTradeManager().getTradeArchiver().getCurrentTrades().indexOf(this) + 1;
        }else{
            tNo = LoggedInUser.getInstance().getTradeManager().getTradeArchiver().getPastTrades().indexOf(this) + 1;
        }

        // if trade hasn't been clicked (viewed) by user, display NEW! beside it
        if(isNewOfferedTrade) {
            return "NEW! Trade No. " + tNo + " with " + otherUser + "\nStatus: " + status;
        }else{
            return "Trade No. " + tNo + " with " + otherUser + "\nStatus: " + status;
        }
    }

    public String toEmailString() {
        String message = "New Trade!\n";
        message = message + this.getSender().getUsername() + " offers: \n";
        for (Trinket t : this.getOfferedTrinkets()) {
            message = message + t.getName() + "\n";
        }

        message = message + this.getReceiver().getUsername() + " offers: \n";
        for (Trinket t : this.getRequestedTrinkets()) {
            message = message + t.getName() + "\n";
        }

        return message;
    }

    /**
     * Returns status of the trade. Can be pending, accepted, or declined.
     * Current(active) trades have a status of pending.  Past (inactive)
     * trades have a status of accepted or declined.
     *
     * @return String - Status of trade (pending, accepted or declined)
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status of a trade.  Can be pending, accepted, or declined.
     * Current(active) trades have a status of pending.  Past (inactive)
     * trades have a status of accepted or declined.
     *
     * @param status - String representing status of trade (pending, accepted or declined)
     */
    public void setStatus(String status) {
        this.status = status;
    }

    // TODO add JavaDocs after this works
    /**
     *
     *
     */
    public void setNotNewOfferedTrade(){
        this.isNewOfferedTrade = Boolean.FALSE;
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
    public String getUid() {
        //TODO: confirm this is the key we want for trades
        return String.valueOf(this.hashCode());
    }

    @Override
    public String getSearchUrl() {
        return SEARCH_URL;
    }

    /**
     * Method called after searchOnNetwork gets a response. This method should
     * be overridden to do something with the result.
     *
     * @param <T extends ElasticStorable> - result result of searchOnNetwork
     */
    @Override
    public <T extends ElasticStorable> void onSearchResult(T result) {

    }

     /** Method called after getFromNetwork gets a response. This method should
     * be overridden to do something with the result.
     *
     * @param result - result of getFromNetwork
     */
    @Override
    public <T extends ElasticStorable> void onGetResult(T result) {

    }
}
