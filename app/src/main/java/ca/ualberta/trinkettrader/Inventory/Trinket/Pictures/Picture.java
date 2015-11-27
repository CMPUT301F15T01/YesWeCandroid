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

import org.apache.http.NameValuePair;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observer;

import ca.ualberta.trinkettrader.Elastic.ElasticStorable;
import ca.ualberta.trinkettrader.R;

/**
 * Represents a compressed picture. This is most likely attached to a trinket.
 */
public class Picture extends ElasticStorable implements ca.ualberta.trinkettrader.Observable {

    private transient static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t01/picture/";
    private transient static final String SEARCH_URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t01/picture/_search";
    private transient static final String TAG = "picture";
    private byte[] pictureByteArray;
    private File file;
    private transient ArrayList<Observer> observers;
    private transient PictureDirectoryManager directoryManager;
    private transient String filename;

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
        this.observers = new ArrayList<>();
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
        this.observers = new ArrayList<>();
    }

    /**
     * Loads this picture into the device's memory so that it can be later
     * viewed. Note that this method will attempt to load the picture even if
     * the picture has previously been loaded.
     */
    public void loadPicture() throws IOException, PackageManager.NameNotFoundException {
        ArrayList<NameValuePair> postParameters = new ArrayList<>();
        postParameters.add(new NameValuePair() {
            @Override
            public String getName() {
                return "picture";
            }

            @Override
            public String getValue() {
                return filename;
            }
        });
        this.searchOnNetwork(postParameters, this);
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
    public String getResourceUrl() {
        return RESOURCE_URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return file.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSearchUrl() {
        return SEARCH_URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTag() {
        return TAG;
    }

    /**
     * Method called after searchOnNetwork gets a response. This method should
     * be overridden to do something with the result.
     *
     * @param result result of searchOnNetwork
     */
    @Override
    public void onSearchResult(ArrayList<ElasticStorable> result) {
        Picture picture = (Picture) result.get(0);
        this.file.delete();
        try {
            this.file = directoryManager.compressPicture(this.filename, picture.getPictureByteArray());
            this.notifyObservers();
        } catch (IOException | PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the byte array containing the compressed picture.
     *
     * @return compressed picture in byte array
     */
    public byte[] getPictureByteArray() {
        return this.pictureByteArray;
    }
}
