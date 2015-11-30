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

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import ca.ualberta.trinkettrader.Elastic.ElasticStorable;
import ca.ualberta.trinkettrader.Elastic.SearchHit;
import ca.ualberta.trinkettrader.Elastic.SearchResponse;
import ca.ualberta.trinkettrader.Elastic.SimpleSearchCommand;
import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.User.LoggedInUser;
import ca.ualberta.trinkettrader.User.User;

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

    private Integer numberOfTrinkets;
    private String status;
    private transient TradeManager receiver;
    private transient TradeManager sender;
    private String recieverUsername;
    private String senderUsername;

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
        // Alexis C.; http://stackoverflow.com/questions/27253555/com-google-gson-internal-linkedtreemap-cannot-be-cast-to-my-class; 2015-11-28
        // Android-Droid; http://stackoverflow.com/questions/8120220/how-to-use-parameters-with-httppost; 2015-11-18
        String username = LoggedInUser.getInstance().getProfile().getEmail();
        final HttpGet searchRequest = new HttpGet(this.getSearchUrl() + "?q=\"recieverUsername:" + username + " OR senderUsername:" + username + "\"");
        searchRequest.setHeader("Accept", "application/json");

        final HttpClient httpClient = new DefaultHttpClient();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<Trade> result = new ArrayList<>();
                    HttpResponse response = httpClient.execute(searchRequest);
                    Log.i("HttpResponse", response.getStatusLine().toString());
                    Log.i("HttpResponse Body", EntityUtils.toString(response.getEntity(), "UTF-8"));

                    Type searchResponseType = new TypeToken<SearchHit<Trade>>() {}.getType();
                    InputStreamReader streamReader = new InputStreamReader(response.getEntity().getContent());
                    SearchResponse<Trade> esResponse = new Gson().fromJson(streamReader, searchResponseType);

                    for (SearchHit<Trade> hit: esResponse.getHits().getHits()) {
                        result.add(hit.getSource());
                    }

                    onSearchResult(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
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
        this.recieverUsername = receiver.getUsername();
        this.senderUsername = sender.getUsername();
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
     * Returns the trinket offered by trade sender.  The current user who is proposing the trade is the
     * sender.  They will offer 1 trinket in the trade, which will be returned in an inventory.
     *
     * @return Inventory - Inventory containing offered trinkets
     */
    public Inventory getOfferedTrinkets() {
        return offeredTrinkets;
    }

    /**
     * Returns TradeManager of receiver of trade (user who receives trade offer).
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
     * Returns TradeManager of sender of trade (user who proposes trade offer).
     *
     * @return TradeManager - TradeManager of user who instantiated trade
     */
    public TradeManager getSender() {
        return sender;
    }

    /**
     * Returns number of trinkets involved in a particular trade.
     *
     * @return Integer Number of offered and requested trinkets in trade
     */
    public Integer getNumberOfTrinkets() {
        return numberOfTrinkets;
    }

    /**
     * Sets number of trinkets in a particular trade.
     *
     * @param numberOfTrinkets Number of offered and requested trinkets in a trade
     */
    public void setNumberOfTrinkets(Integer numberOfTrinkets) {
        this.numberOfTrinkets = numberOfTrinkets;
    }

    /**
     * This method override is responsible for determining how trades will be shown to the
     * user when they view their Current Trades list and Past Trades list.
     * <p/>
     * For each trade in the list, it's number, the other person involved in the trade
     * (not LoggedInUser), it's status and the categories of the trinkets involved will be
     * displayed.
     *
     * @return String Text displayed for each trade in current trades list of
     * ActiveTradesActivity and in past trades list of PastTradesActivity
     */
    @Override
    public String toString() {

        // need to display name of other person involved in trade
        // also need to find categories of trinkets involved
        return "Trade No. 1 " + "with status " + LoggedInUser.getInstance().getProfile().getEmail() + " " + this.getStatus();
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

    // TODO useful?

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

    // adarshr; http://stackoverflow.com/questions/10734106/how-to-override-tostring-properly-in-java; 2015-11-16

    // TODO unfinished. Everything mentioned in JavaDoc comment below will be implemented in next prototype.
    // TODO may remodel after profile page with updatable fields

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
