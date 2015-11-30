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

package ca.ualberta.trinkettrader.User;

import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observer;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.Elastic.ElasticStorable;
import ca.ualberta.trinkettrader.Elastic.SearchHit;
import ca.ualberta.trinkettrader.Friends.FriendsList;
import ca.ualberta.trinkettrader.Friends.TrackedFriends.TrackedFriendsList;
import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.Inventory.Trinket.Pictures.Picture;
import ca.ualberta.trinkettrader.Inventory.Trinket.Pictures.PictureDirectoryManager;
import ca.ualberta.trinkettrader.Inventory.Trinket.Trinket;
import ca.ualberta.trinkettrader.NotificationManager;
import ca.ualberta.trinkettrader.Trades.TradeManager;
import ca.ualberta.trinkettrader.User.Profile.UserProfile;

/**
 * Abstract class representing a user of the app. This class is implemented in the app by
 * {@link LoggedInUser LoggedInUser}, who represents the current user of the app on a particular device.
 * This class mainly acts as a container for all of the various classes that make up a user.
 * <p/>
 * In the system, a user has a list of {@link ca.ualberta.trinkettrader.Friends.Friend Friends} contained
 * in a {@link FriendsList FriendsList}, a {@link TrackedFriendsList TrackedFriendsList} for their
 * tracked friends, an {@link Inventory Inventory} which contains the
 * {@link ca.ualberta.trinkettrader.Inventory.Trinket.Trinket Trinkets} they own and want to be available
 * for trading, a {@link NotificationManager NotificationManager} to notify them if another user wishes to
 * trade with them, a {@link TradeManager TradeManager} to handle their trades with their friends, and
 * a user profile which stores and displays their information.
 */
public class User extends ElasticStorable implements ca.ualberta.trinkettrader.Observable {

    private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t01/user/";
    private static final String SEARCH_URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t01/user/_search";
    private static final String TAG = "User";
    protected Boolean needToSave;
    protected FriendsList friendsList;
    protected Inventory inventory;
    protected NotificationManager notificationManager;
    protected TrackedFriendsList trackedFriendsList;
    protected TradeManager tradeManager;
    protected UserProfile profile;
    private ArrayList<Observer> observers;

    /**
     * Public constructor for user: initializes all attribute classes as empty classes with no
     * active data.  This constructor is mainly used for testing purposes as it implies that no information is
     * available about the user, not even an email address.  By default a new user needs to be saved to
     * the network, so the needToSave attribute is set to true.
     */
    public User() {
        this.friendsList = new FriendsList();
        this.inventory = new Inventory();
        this.needToSave = Boolean.TRUE;
        this.notificationManager = new NotificationManager();
        this.profile = new UserProfile();
        this.trackedFriendsList = new TrackedFriendsList();
        this.tradeManager = new TradeManager();
    }

    /**
     * A public constructor for User in the case where the user's details are known for all its
     * attribute classes.  This constructor would be called for a user already on the system, whose
     * data can be pulled from Elastic Search or the phone's local data.
     *
     * @param friendsList         - a FriendsList of the user's friends
     * @param inventory           - the user's Inventory with their trinkets
     * @param notificationManager - a notification manager to notify the user if a new trade has been offered to them
     * @param profile             - a profile storing their information
     * @param trackedFriends      - a FriendsList which stores the Friends the user wishes to track
     * @param tradeManager        - a TradeManager for handling the user's trades
     */
    public User(FriendsList friendsList, Inventory inventory, NotificationManager notificationManager, UserProfile profile, TrackedFriendsList trackedFriends, TradeManager tradeManager) {
        this.friendsList = friendsList;
        this.inventory = inventory;
        this.needToSave = Boolean.TRUE;
        this.notificationManager = notificationManager;
        this.profile = profile;
        this.trackedFriendsList = trackedFriends;
        this.tradeManager = tradeManager;
        this.tradeManager.setUsername(this.profile.getUsername());
    }

    /**
     * Constructor that uses the email a user enters to register in the {@link ca.ualberta.trinkettrader.LoginActivity LoginActivity}.
     * This email is then used to initialize their TradeManager.  All other user attributes are initialized
     * as empty.  This constructor is used when a new user logs in for the first time, as there is no information
     * in the system about the user except the email they entered to register.  By default a new user needs to be saved to
     * the network, so the needToSave attribute is set to true.
     *
     * @param email - the user's email address.  Taken from the email they enter into the LoginActivity
     */
    public User(String email) {
        super();
        this.tradeManager = new TradeManager();
        this.tradeManager.setUsername(email);
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
     * Returns a boolean indicating if the {@link ca.ualberta.trinkettrader.User.User User} with this
     * contact information needs to be saved to the network.  If the contact information of the user has
     * been changed then needToSave is set to true and the user needs to be saved.  Otherwise, this should
     * return false indicating that the user does not need to be saved.
     *
     * @return Boolean - if true then the User with this contact information needs to be re-saved to
     * the network, if false then there is nothing new that needs to be sav
     */

    public Boolean getNeedToSave() {
        //TODO: need to implement needToSave for friendslist as well...
        return this.needToSave | this.profile.getNeedToSave() | this.inventory.getNeedToSave();
    }

    /**
     * Sets whether User data needs to be locally cached. This Boolean should on be set only when
     * changes to the User's data is made.
     *
     * @param needToSave
     */
    protected void setNeedToSave(Boolean needToSave) {
        this.needToSave = needToSave;
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
        // Vasyl Keretsman; http://stackoverflow.com/questions/15429257/how-to-convert-byte-array-to-hexstring-in-java; 2015-11-28
        final StringBuilder builder = new StringBuilder();
        for (byte b : this.profile.getEmail().getBytes()) {
            builder.append(String.format("%02x", b));
        }
        String uid = builder.toString();
        return uid;
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
        // Alexis C.; http://stackoverflow.com/questions/27253555/com-google-gson-internal-linkedtreemap-cannot-be-cast-to-my-class; 2015-11-28
        // Android-Droid; http://stackoverflow.com/questions/8120220/how-to-use-parameters-with-httppost; 2015-11-18
        final HttpGet getRequest = new HttpGet(this.getResourceUrl() + this.getUid());
        final HttpClient httpClient = new DefaultHttpClient();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpResponse response = httpClient.execute(getRequest);
                    Log.i("HttpResponse", response.getStatusLine().toString());
                    Type searchHitType = new TypeToken<SearchHit<LoggedInUser>>() {
                    }.getType();
                    SearchHit<LoggedInUser> returned = new Gson().fromJson(new InputStreamReader(response.getEntity().getContent()), searchHitType);
                    onGetResult(returned.getSource());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    /**
     * Method called after getFromNetwork gets a response. This method should
     * be overridden to do something with the result.
     *
     * @param result result of getFromNetwork
     */
    @Override
    public <T extends ElasticStorable> void onGetResult(T result) {
        if (result != null) {
            User user = (User) result;
            this.setFriendsList(user.getFriendsList());
            this.setInventory(user.getInventory());
            for (Trinket trinket: user.getInventory()) {
                ArrayList<Picture> pictures = new ArrayList<>();
                for (String filename: trinket.getPictureFileNames()) {
                    try {
                        pictures.add(new Picture(filename, new PictureDirectoryManager(ApplicationState.getInstance().getActivity()), ApplicationState.getInstance().getActivity()));
                    } catch (IOException | PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                trinket.setPictures(pictures);
            }
            this.setNotificationManager(user.getNotificationManager());
            this.setProfile(user.getProfile());
            this.setTrackedFriends(user.getTrackedFriendsList());
            this.setTradeManager(user.getTradeManager());
        } else {
            try {
                this.saveToNetwork();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    /**
     * Returns user's UserProfle
     *
     * @return UserProfile
     */
    public UserProfile getProfile() {
        return profile;
    }

    /**
     * Sets user's UserProfile
     *
     * @param profile
     */
    public void setProfile(UserProfile profile) {
        this.profile = profile;
        this.needToSave = Boolean.TRUE;
    }

    /**
     * Sets user's list of tracked friends
     *
     * @param trackedFriendsList
     */
    public void setTrackedFriends(TrackedFriendsList trackedFriendsList) {
        this.trackedFriendsList = trackedFriendsList;
    }

    /**
     * Returns user's list of tracked friends
     *
     * @return TrackedFriendsList
     */
    public TrackedFriendsList getTrackedFriendsList() {
        return trackedFriendsList;
    }

    /**
     * Returns User's friends
     *
     * @return FriendList
     */

    public FriendsList getFriendsList() {
        return friendsList;
    }

    /**
     * Sets user's list of friends
     *
     * @param friendsList
     */
    public void setFriendsList(FriendsList friendsList) {
        this.friendsList = friendsList;
        this.needToSave = Boolean.TRUE;
    }

    /**
     * Returns user's inventory
     *
     * @return Inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Sets user's inventory
     *
     * @param inventory
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
        this.needToSave = Boolean.TRUE;
    }

    /**
     * Returns user's notification manager
     *
     * @return NotificationManager
     */
    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    /**
     * Sets user's notification manager
     *
     * @param notificationManager
     */
    public void setNotificationManager(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }

    /**
     * Returns user's trade manager
     *
     * @return TradeManager
     */
    public TradeManager getTradeManager() {
        return tradeManager;
    }

    /**
     * Sets user's trade manager
     *
     * @param tradeManager
     */
    public void setTradeManager(TradeManager tradeManager) {
        this.tradeManager = tradeManager;
    }

    @Override
    public String getSearchUrl() {
        return SEARCH_URL;
    }

    public Location getDefaultLocation() {
        return this.profile.getDefaultLocation();
    }

    public void setDefaultLocation(Location defaultLocation) {
        this.profile.setDefaultLocation(defaultLocation);
    }

    public void setEmail(String email) {
        this.getProfile().setEmail(email);
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

    public void getFromNetwork() throws IOException {
        // Alexis C.; http://stackoverflow.com/questions/27253555/com-google-gson-internal-linkedtreemap-cannot-be-cast-to-my-class; 2015-11-28
        // Android-Droid; http://stackoverflow.com/questions/8120220/how-to-use-parameters-with-httppost; 2015-11-18
        final HttpGet getRequest = new HttpGet(this.getResourceUrl() + this.getUid());
        final HttpClient httpClient = new DefaultHttpClient();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpResponse response = httpClient.execute(getRequest);
                    Log.i("HttpResponse", response.getStatusLine().toString());
                    Type searchHitType = new TypeToken<SearchHit<User>>() {}.getType();
                    SearchHit<User> returned = new Gson().fromJson(new InputStreamReader(response.getEntity().getContent()), searchHitType);
                    onGetResult(returned.getSource());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
