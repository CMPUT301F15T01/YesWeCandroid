package ca.ualberta.trinkettrader.Elastic;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import ca.ualberta.trinkettrader.User.User;

/**
 * Created by anju on 29/11/15.
 */
public class TestElasticStorable {
    public void loadFromNetwork() throws IOException {
        // Alexis C.; http://stackoverflow.com/questions/27253555/com-google-gson-internal-linkedtreemap-cannot-be-cast-to-my-class; 2015-11-28
        // Android-Droid; http://stackoverflow.com/questions/8120220/how-to-use-parameters-with-httppost; 2015-11-18
        String url = "http://cmput301.softwareprocess.es:8080/cmput301f15t01/user/_search?q=uid:7465737440746573742e6361";
        final HttpGet searchRequest = new HttpGet(url);
        searchRequest.setHeader("Accept", "application/json");
        final HttpClient httpClient = new DefaultHttpClient();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<User> users = new ArrayList<>();
                    HttpResponse response = httpClient.execute(searchRequest);
                    Log.d("HttpResponseTestLoad", response.getStatusLine().toString());
                    Type searchResponseType = new TypeToken<SearchResponse<User>>() {
                    }.getType();
                    InputStreamReader streamReader = new InputStreamReader(response.getEntity().getContent());
                    SearchResponse<User> esResponse = new Gson().fromJson(streamReader, searchResponseType);
                    for(SearchHit<User> hit: esResponse.getHits().getHits()){
                        User u = hit.getSource();
                        users.add(u);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    /*

       BufferedReader buffer =  new BufferedReader(streamReader);
                    String line;
                    while((line = buffer.readLine()) != null){
                        Log.i("LINE", line);
                    }
                    //                    String email = returned.getProfile().getEmail();
//                    Log.i("EMAIL", email);

    */
}
