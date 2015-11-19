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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observer;

import ca.ualberta.trinkettrader.ElasticStorable;

/**
 * Represents a compressed picture. This is most likely attached to a trinket.
 */
public class Picture extends ElasticStorable implements ca.ualberta.trinkettrader.Observable {

    private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t01/user/";
    private static final String SEARCH_URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t01/user/_search";
    private static final String TAG = "User";


    private ArrayList<Observer> observers;
    private byte[] image;
    private volatile File file;

    /**
     * Creates a new picture from a file containing a compressed jpeg image.
     *
     * @param file file containing the compressed jpeg image
     * @throws IOException
     */
    public Picture(File file) throws IOException {
        this.file = file;
        FileInputStream fileInputStream = new FileInputStream(file);
        // \u00d3scar L\u00f3pez; http://stackoverflow.com/questions/8721262/how-to-get-file-size-in-java; 2015-11-04
        image = new byte[(int) file.length()];
        fileInputStream.read(image);
        fileInputStream.close();
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
     * Deletes information related to the picture from the device's memory.
     */
    public void delete() {
        this.file.delete();
    }

    /**
     * Returns a bitmap representation of the picture. This should be used
     * anytime the image needs to be displayed.
     *
     * @return Bitmap
     */
    public Bitmap getBitmap() {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    /**
     * Returns the size of the picture in the device's memory.
     *
     * @return Long
     */
    public Long size() {
        return file.length();
    }
    @Override
    public String getResourceUrl() {
        return RESOURCE_URL;
    }

    @Override
    public String getSearchUrl() {
        return SEARCH_URL;
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public String getId() {
        return String.valueOf(this.image.hashCode());
    }
}
