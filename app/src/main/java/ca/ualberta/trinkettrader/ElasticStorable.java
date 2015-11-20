package ca.ualberta.trinkettrader;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Methods using HTTPRequest in this class are taken from AndroidElasticSearch //TODO: add github link
 */
public abstract class ElasticStorable {

    Gson gson = new Gson();

    public abstract String getResourceUrl();
    public abstract String getSearchUrl();
    public abstract String getTag();
    public abstract String getId();


    /**
     * Save an ElasticStorable object to the network
     * @param item ElasticStorable instance to add or update on the network
     */
    public void saveToNetwork(ElasticStorable item) {
        HttpClient httpClient = new DefaultHttpClient();

        try {
            HttpPost addRequest = new HttpPost(item.getResourceUrl() + item.getId());
            Log.d("SAVE URL", item.getResourceUrl()+item.getId());

            StringEntity stringEntity = new StringEntity(gson.toJson(item));
            addRequest.setEntity(stringEntity);
            addRequest.setHeader("Accept", "application/json");
            
            HttpResponse response = httpClient.execute(addRequest);
            String status = response.getStatusLine().toString();
            Log.i(item.getTag(), status);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Search for ElasticStorable objects on the network by matching the attribute and attribute
     * value pairs
     *
     * @param postParameters Pairs of attributes and their values to equate to.
     * @param storable An instance of the ElasticStorable subclass that we look for, specifically.
     * @return
     */
    //This method was modified under the guidance of http://stackoverflow.com/questions/8120220/how-to-use-parameters-with-httppost
    //by Android-Droid
    public ArrayList<ElasticStorable> searchOnNetwork(List<BasicNameValuePair> postParameters, ElasticStorable storable) {

        ArrayList<ElasticStorable> result = new ArrayList<ElasticStorable>();
        /**
         * Creates a search request from a search string and a field
         */

        HttpPost searchRequest = new HttpPost(storable.getSearchUrl());
        try{
            searchRequest.setEntity(new UrlEncodedFormEntity(postParameters));
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        String query = gson.toJson(postParameters);
        Log.i(storable.getTag(), "Json command: " + query);

        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(query);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        searchRequest.setHeader("Accept", "application/json");
        HttpClient httpClient = new DefaultHttpClient();

        HttpResponse response = null;
        try {
            response = httpClient.execute(searchRequest);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /**
         * Parses the response of a search
         */
        Type searchResponseType = new TypeToken<SearchResponse<ElasticStorable>>() {
        }.getType();

        SearchResponse<ElasticStorable> esResponse;

        try {
            esResponse = gson.fromJson(
                    new InputStreamReader(response.getEntity().getContent()),
                    searchResponseType);
        } catch (JsonIOException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        for (SearchHit<ElasticStorable> hit : esResponse.getHits().getHits()) {
            result.add(hit.getSource());
        }

        return result;
    }

    /***
     * This is an accessory method when removing an ElasticStorable. To be called before clearing
     * the local version of the same data.
     * @param storable ElasticStorable object that is on the network
     */
    public void deleteFromNetwork(ElasticStorable storable) {
        HttpClient httpClient = new DefaultHttpClient();

        try {
            HttpDelete deleteRequest = new HttpDelete(storable.getResourceUrl() + storable.getId());
            deleteRequest.setHeader("Accept", "application/json");

            HttpResponse response = httpClient.execute(deleteRequest);
            String status = response.getStatusLine().toString();
            Log.i(storable.getTag(), status);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
