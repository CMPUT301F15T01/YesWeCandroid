package ca.ualberta.trinkettrader;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by anju on 18/11/15.
 */
public abstract class ElasticStorable {

    Gson gson = new Gson();

    public abstract String getResourceUrl();
    public abstract String getSearchUrl();
    public abstract String getTag();
    public abstract String getId();


    public void saveToNetwork(ElasticStorable item) {
        HttpClient httpClient = new DefaultHttpClient();

        try {
            HttpPost addRequest = new HttpPost(item.getResourceUrl() + item.getId());

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

    public ArrayList<ElasticStorable> searchMovies(String searchString, String field, ElasticStorable storable) {

        ArrayList<ElasticStorable> result = new ArrayList<ElasticStorable>();
        /**
         * Creates a search request from a search string and a field
         */

        HttpPost searchRequest = new HttpPost(storable.getSearchUrl());

        String[] fields = null;
        if (field != null) {
            throw new UnsupportedOperationException("Not implemented!");
        }

        SimpleSearchCommand command = new SimpleSearchCommand(searchString);

        String query = gson.toJson(command);
        Log.i(storable.getTag(), "Json command: " + query);

        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(query);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        searchRequest.setHeader("Accept", "application/json");
        searchRequest.setEntity(stringEntity);

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
}
