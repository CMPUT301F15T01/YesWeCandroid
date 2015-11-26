package ca.ualberta.trinkettrader;

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
 * Methods using HTTPRequest in this class are taken from AndroidElasticSearch //TODO: add github link
 */
public abstract class ElasticStorable {

    /**
     * Save this object to the elasticsearch server.
     */
    public void saveToNetwork() {
        final HttpClient httpClient = new DefaultHttpClient();
        final HttpPost addRequest = new HttpPost(this.getResourceUrl() + this.getId());
        try {
            final StringEntity stringEntity = new StringEntity(new Gson().toJson(this));
            addRequest.setEntity(stringEntity);
            addRequest.setHeader("Accept", "application/json");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     * Search for ElasticStorable objects on the network by matching the attribute and attribute
     * value pairs.
     *
     * @param postParameters Pairs of attributes and their values to equate to.
     * @return response of the search
     * @throws IOException
     */
    public ArrayList<ElasticStorable> searchOnNetwork(ArrayList<NameValuePair> postParameters) throws IOException {
        // Android-Droid; http://stackoverflow.com/questions/8120220/how-to-use-parameters-with-httppost; 2015-11-18
        ArrayList<ElasticStorable> result = new ArrayList<>();

        final HttpPost searchRequest = new HttpPost(this.getSearchUrl());
        searchRequest.setEntity(new UrlEncodedFormEntity(postParameters));
        searchRequest.setHeader("Accept", "application/json");

        String query = new Gson().toJson(postParameters);
        Log.i(this.getTag(), "Json command: " + query);
        StringEntity stringEntity = new StringEntity(query);

        final HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(searchRequest);
        Type searchResponseType = new TypeToken<SearchResponse<ElasticStorable>>() {
        }.getType();
        SearchResponse<ElasticStorable> esResponse = new Gson().fromJson(new InputStreamReader(response.getEntity().getContent()), searchResponseType);
        for (SearchHit<ElasticStorable> hit : esResponse.getHits().getHits()) {
            result.add(hit.getSource());
        }

        return result;
    }

    public abstract String getSearchUrl();

    public abstract String getTag();

    /***
     * This is an accessory method when removing an ElasticStorable. To be called before clearing
     * the local version of the same data.
     *
     * @throws IOException
     */
    public void deleteFromNetwork() throws IOException {
        HttpClient httpClient = new DefaultHttpClient();

        HttpDelete deleteRequest = new HttpDelete(this.getResourceUrl() + this.getId());
        deleteRequest.setHeader("Accept", "application/json");

        HttpResponse response = httpClient.execute(deleteRequest);
        String status = response.getStatusLine().toString();
        Log.i(this.getTag(), status);
    }
}
