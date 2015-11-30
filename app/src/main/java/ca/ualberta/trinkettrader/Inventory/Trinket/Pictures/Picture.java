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

package ca.ualberta.trinkettrader.Inventory.Trinket.Pictures;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Observer;
import java.util.Set;

import ca.ualberta.trinkettrader.Elastic.ElasticStorable;
import ca.ualberta.trinkettrader.Elastic.SearchHit;
import ca.ualberta.trinkettrader.R;

/**
 * A class representing a compressed picture.  Pictures can optionally be attached to a Trinket to show
 * what it looks like to other users.  Pictures can be added to a Trinket from either the phone's camera
 * or gallery.  These sources will provide images as uncompressed JPEGs, but the Trinket Trader app requires
 * the images be 65535 bytes in size or less.  Thus, the attached images need ot be compressed.
 */
public class Picture extends ElasticStorable implements ca.ualberta.trinkettrader.Observable {

    private transient static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t01/picture/";
    private transient static final String SEARCH_URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t01/picture/_search";
    private transient static final String TAG = "picture";

    private byte[] pictureByteArray;
    private File file;
    private String filename;
    private transient PictureDirectoryManager directoryManager;
    private transient Set<Observer> observers;

    /**
     * Creates a new picture from a file containing a compressed jpeg image.
     * The image is pushed to the server after it has been created.
     *
     * @param picture          file containing the compressed jpeg image
     * @param directoryManager directory manager that can be used to store the picture
     * @throws IOException
     * @throws PackageManager.NameNotFoundException
     */
    public Picture(File picture, PictureDirectoryManager directoryManager) throws IOException, PackageManager.NameNotFoundException {
        this.directoryManager = directoryManager;
        this.file = directoryManager.compressPicture(picture);
        this.filename = this.file.getName();
        // \u00d3scar L\u00f3pez; http://stackoverflow.com/questions/8721262/how-to-get-file-size-in-java; 2015-11-04
        pictureByteArray = new byte[(int) this.file.length()];
        int result = 0;
        while (result == 0) {
            result = new FileInputStream(this.file).read(pictureByteArray);
        }
        this.observers = new HashSet<>();
        this.saveToNetwork();
    }

    /**
     * Creates a new picture from a file stored on the elasticsearch server.
     *
     * @param filename         filename of the picture stored on the server
     * @param directoryManager directory manager that can be used to store the picture
     * @param activity         android activity used to get the application's context
     */
    public Picture(String filename, PictureDirectoryManager directoryManager, Activity activity) throws IOException, PackageManager.NameNotFoundException {
        this.directoryManager = directoryManager;
        this.filename = filename;
        // poitroae; http://stackoverflow.com/questions/8717333/converting-drawable-resource-image-into-bitmap; 2015-11-25
        Bitmap placeholder = BitmapFactory.decodeResource(activity.getResources(), R.drawable.placeholder);
        this.file = directoryManager.compressPicture(this.filename, placeholder);
        this.observers = new HashSet<>();
    }

    /**
     * Loads this picture into the device's memory so that it can be later
     * viewed. Note that this method will attempt to load the picture even if
     * the picture has previously been loaded.
     */
    public void loadPicture() throws IOException, PackageManager.NameNotFoundException {
        this.getFromNetwork(Picture.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void deleteObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.notify();
        }
    }

    /**
     * Deletes information related to the picture from the device's memory and
     * from the elasticsearch server. This method should be used if you want to
     * permanently delete a picture. Note that, as only a copy of the image is
     * stored in the applications' folder, the original image should still
     * exist outside of the app.
     */
    public void delete() throws IOException {
        boolean result = false;
        while (!result) {
            result = this.file.delete();
        }
        this.deleteFromNetwork();
        this.notifyObservers();
    }

    /**
     * Returns a bitmap representation of the picture. This should be used
     * anytime the image needs to be displayed. Note that a dummy bitmap will
     * be generated if the image is not yet loaded.
     *
     * @return bitmap representation of the picture
     */
    public Bitmap getBitmap() {
        return BitmapFactory.decodeByteArray(pictureByteArray, 0, pictureByteArray.length);
    }

    /**
     * Returns the file containing the compressed picture.
     *
     * @return file containing the compressed picture
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Returns the name of the file containing the compressed picture.
     *
     * @return name of the file containing the compressed picture
     */
    public String getFilename() {
        return this.filename;
    }

    /**
     * Returns the size of the picture in the device's memory.
     *
     * @return size of the picture in the device's memory
     */
    public Long size() {
        return file.length();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTag() {
        return TAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResourceUrl() {
        return RESOURCE_URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUid() {
        // Vasyl Keretsman; http://stackoverflow.com/questions/15429257/how-to-convert-byte-array-to-hexstring-in-java; 2015-11-28
        final StringBuilder builder = new StringBuilder();
        for (byte b : this.filename.getBytes()) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
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
                    Type searchHitType = new TypeToken<SearchHit<Picture>>() {
                    }.getType();
                    SearchHit<Picture> returned = new Gson().fromJson(new InputStreamReader(response.getEntity().getContent()), searchHitType);
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
        Picture picture = (Picture) result;
        this.file.delete();
        try {
            this.pictureByteArray = picture.getPictureByteArray();
            this.file = directoryManager.compressPicture(this.filename, this.pictureByteArray);
            this.notifyObservers();
        } catch (IOException | PackageManager.NameNotFoundException e) {
            e.printStackTrace();
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
     * {@inheritDoc}
     */
    @Override
    public String getSearchUrl() {
        return SEARCH_URL;
    }

    /**
     * Returns the byte array containing the compressed picture.
     *
     * @return compressed picture in byte array
     */
    public byte[] getPictureByteArray() {
        return this.pictureByteArray;
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
}
