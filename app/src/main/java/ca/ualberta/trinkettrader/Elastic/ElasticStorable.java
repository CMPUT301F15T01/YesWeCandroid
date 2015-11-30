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
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
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
import java.util.Collection;

/**
 * Methods using HTTPRequest in this class are taken from AndroidElasticSearch
 */
public abstract class ElasticStorable {

    // emmby; http://stackoverflow.com/questions/1626667/how-to-use-parcel-in-android; 2015-11-26
    // joshua2ua; https://github.com/joshua2ua/AndroidElasticSearch; 2015-11-26

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

    public abstract String getResourceUrl();

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
     * cannot be found then pushes the current version to the server.
     *
     * @param type class of this object
     * @param <T>  class of this object
     * @throws IOException
     */
    public abstract  <T extends ElasticStorable> void getFromNetwork(final Class<T> type) throws IOException;

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
