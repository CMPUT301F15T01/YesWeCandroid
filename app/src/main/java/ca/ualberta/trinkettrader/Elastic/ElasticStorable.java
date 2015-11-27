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
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Methods using HTTPRequest in this class are taken from AndroidElasticSearch
 */
public abstract class ElasticStorable {

    // emmby; http://stackoverflow.com/questions/1626667/how-to-use-parcel-in-android; 2015-11-26
    // joshua2ua; https://github.com/joshua2ua/AndroidElasticSearch; 2015-11-26

    /**
     * Save this object on the elasticsearch server.
     */
    public void saveToNetwork() throws IOException {
        final HttpClient httpClient = new DefaultHttpClient();
        final HttpPost addRequest = new HttpPost(this.getResourceUrl() + this.getId());
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

    public abstract String getId();

    /**
     * Searches for ElasticStorable objects on the network matching the attribute and attribute
     * value pairs. Calls onSearchResult with the results when the search completes.
     *
     * @param postParameters pairs of attributes to use when searching
     * @throws IOException
     */
    public void searchOnNetwork(ArrayList<NameValuePair> postParameters) throws IOException {
        // Android-Droid; http://stackoverflow.com/questions/8120220/how-to-use-parameters-with-httppost; 2015-11-18
        final HttpPost searchRequest = new HttpPost(this.getSearchUrl());
        searchRequest.setEntity(new UrlEncodedFormEntity(postParameters));
        searchRequest.setHeader("Accept", "application/json");

        //Useless, really
        //String query = new Gson().toJson(postParameters);
        //Log.i(this.getTag(), "Json command: " + query);

        final HttpClient httpClient = new DefaultHttpClient();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<ElasticStorable> result = new ArrayList<>();
                    HttpResponse response = httpClient.execute(searchRequest);
                    Log.i("HttpResponse", response.getStatusLine().toString());

                    Type searchResponseType = new TypeToken<SearchResponse<ElasticStorable>>() {}.getType();
                    InputStreamReader streamReader = new InputStreamReader(response.getEntity().getContent());
                    SearchResponse<ElasticStorable> esResponse = new Gson().fromJson(streamReader, searchResponseType);
                   /* for (SearchHit<ElasticStorable> hit : esResponse.getHits().getHits()) {
                        result.add(hit.getSource());
                    }*/
                    onSearchResult(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public abstract String getSearchUrl();

    public abstract String getTag();

    /**
     * Method called after searchOnNetwork gets a response. This method should
     * be overridden to do something with the result.
     *
     * @param result result of searchOnNetwork
     */
    public abstract void onSearchResult(ArrayList<ElasticStorable> result);

    /**
     * This method deletes this object from the elasticsearch server. This
     * should be called when the object is no longer needed anywhere.
     *
     * @throws IOException
     */
    public void deleteFromNetwork() throws IOException {
        final HttpClient httpClient = new DefaultHttpClient();
        final HttpDelete deleteRequest = new HttpDelete(this.getResourceUrl() + this.getId());
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
