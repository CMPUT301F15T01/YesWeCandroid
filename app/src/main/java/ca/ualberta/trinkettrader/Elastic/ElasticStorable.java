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

package ca.ualberta.trinkettrader.Elastic;

import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Methods using HTTPRequest in this class are taken from AndroidElasticSearch
 */
public abstract class ElasticStorable {

    // emmby; http://stackoverflow.com/questions/1626667/how-to-use-parcel-in-android; 2015-11-26
    // joshua2ua; https://github.com/joshua2ua/AndroidElasticSearch; 2015-11-26

    /**
     * Returns the tag section of the elastic search entry's URL.  Trades, photos, and users are all
     * saved separately in elastic search within their own tags.  This method will return "User" as the
     * tag for {@link ca.ualberta.trinkettrader.User.User User} objects, "Trade" for
     * {@link ca.ualberta.trinkettrader.Trades.Trade Trade} objects, and "picture" for
     * {@link ca.ualberta.trinkettrader.Inventory.Trinket.Pictures.Picture Picture} objects.
     *
     * @return String - the tag used to generate the url for each type of item stored in elastic search
     */
    public abstract String getTag();

    /**
     * Save this object on the elasticsearch server.
     */
    public void saveToNetwork() throws IOException {
        final HttpClient httpClient = new DefaultHttpClient();
        final HttpPost addRequest = new HttpPost(this.getResourceUrl() + this.getUid());
        final StringEntity stringEntity = new StringEntity(new Gson().toJson(this));
        addRequest.setEntity(stringEntity);
        addRequest.setHeader("Accept", "application/json");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpResponse response = httpClient.execute(addRequest);
                    Log.i("HttpResponse", response.getStatusLine().toString());
                    Log.i("HttpResponse Body", EntityUtils.toString(response.getEntity(), "UTF-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    /**
     * Returns the base URL each class of ElasticStorable object will be saved to.  Trades, photos, and users are all
     * saved separately in elastic search within their own tags.  Each individual object is then stored with
     * their own unique ids.  The resource URL specifies the base URL which is composed of the server
     * URL ("http://cmput301.softwareprocess.es:8080/cmput301f15t01/") followed by the tag of the particular class,
     * "User" as the tag for {@link ca.ualberta.trinkettrader.User.User User} objects, "Trade" for
     * {@link ca.ualberta.trinkettrader.Trades.Trade Trade} objects, and "picture" for
     * {@link ca.ualberta.trinkettrader.Inventory.Trinket.Pictures.Picture Picture} objects.
     *
     * @return String - base URL (no unique ID) for an object being stored to elastic search
     */
    public abstract String getResourceUrl();

    /**
     * Returns a unique ID for each user's objects being stored to Elastic Search.  This is appended to the
     * resource url to save each object to the elastic search server.  For a particular user of the
     * system associated with a unique email address, this method will always return the same UID.
     *
     * @return - an id unique to each user of the system for saving objects to elastic search
     */
    public abstract String getUid();

    /**
     * Searches for ElasticStorable objects on the network matching the attribute and attribute
     * value pairs. Calls onSearchResult with the results when the search completes.
     *
     * @param postParameters pairs of attributes to use when searching
     * @throws IOException
     */
    public abstract <T extends ElasticStorable> void searchOnNetwork(ArrayList<NameValuePair> postParameters, final Class<T> type) throws IOException;

    /**
     * Attempts to find this object on the elasticsearch server. If the object
     * cannot be found then pushes the current version to the server.  Each object is searched for using
     * its know URL, which is composed of its resource url ({@link ElasticStorable#getResourceUrl()}) and an ID
     * unique to the user associated with the object ({@link ElasticStorable#getUid()}).
     *
     * @param type - class of the object being saved
     * @param <T> - type may be of any class extending ElasticStorable
     * @throws IOException
     */
    public abstract <T extends ElasticStorable> void getFromNetwork(final Class<T> type) throws IOException;

    /**
     * Method called after getFromNetwork gets a response. This method should
     * be overridden to do something with the result.
     *
     * @param result result of getFromNetwork
     * @param <T>    type passed to getFromNetwork
     */
    public abstract <T extends ElasticStorable> void onGetResult(T result);

    /**
     * Method called after searchOnNetwork gets a response. This method should
     * be overridden to do something with the result.
     *
     * @param result result of searchOnNetwork
     */
    public abstract <T extends ElasticStorable> void onSearchResult(Collection<T> result);

    public abstract String getSearchUrl();

    /**
     * This method deletes this object from the elasticsearch server. This
     * should be called when the object is no longer needed anywhere.
     *
     * @throws IOException
     */
    public void deleteFromNetwork() throws IOException {
        final HttpClient httpClient = new DefaultHttpClient();
        final HttpDelete deleteRequest = new HttpDelete(this.getResourceUrl() + this.getUid());
        deleteRequest.setHeader("Accept", "application/json");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpResponse response = httpClient.execute(deleteRequest);
                    Log.i("HttpResponse", response.getStatusLine().toString());
                    Log.i("HttpResponse Body", EntityUtils.toString(response.getEntity(), "UTF-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
