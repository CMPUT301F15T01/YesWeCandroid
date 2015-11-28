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

package ca.ualberta.trinkettrader.Inventory.Trinket;

import android.location.Location;

import java.util.ArrayList;
import java.util.Observer;

import ca.ualberta.trinkettrader.Inventory.Trinket.Pictures.Picture;

/**
 * Class for describing one kind of trinket that a user has.  A trinket is either public or private
 * (accessibility), has a name, description, quantity, quality, belongs to one of 10 categories, and
 * may have photos attached to show what it looks like.  A user represents having more than one of
 * the same kind of trinket by setting the item's quantity (for example, if they have three identical
 * blue bracelets.  The details of a trinket are set or edited from the AddOrEditTrinketActivity.
 */
public class Trinket implements ca.ualberta.trinkettrader.Observable {

    private ArrayList<Observer> observers;
    private ArrayList<Picture> pictures;
    private Location location;
    private String accessibility;
    private String category;
    private String description;
    private String name;
    private String quality;
    private String quantity;

    /**
     * Creates a new trinket object.  By default the trinket's accessibility is public and its
     * quantity is 1. No other field is set by this constructor, and must be set by the user in the
     * AddOrEditTrinketActivity.
     */
    public Trinket() {
        accessibility = "public";
        pictures = new ArrayList<>();
        quantity = "1";
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Return trinket's accessibility as a string.  Will be either "public" or "private".  By default
     * this value is "public".
     *
     * @return String - string describing trinket's accessibility as either "public" or "private"
     */
    public String getTrinketAccessibility() {
        return accessibility;
    }

    // TODO:  What do public and private mean

    /**
     * Sets trinket's accessibility,  Must be either "public" or "private".
     *
     * @param accessibility - string describing desired accessibility for the trinket, either "public"
     *                      or "private"
     */
    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    /**
     * Return trinket's category as a string.  The trinket can be classified as one of 10 categories.
     * The possible categories are defined in the arrays.xml resource.
     *
     * @return String - string of which category the trinket is
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets trinket's category as one of 10 categories.  The possible categories are defined in the
     * arrays.xml resource.
     *
     * @param category - string naming which category the trinket belongs to
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Returns trinket's description.  The description gives extra information about the trinket and
     * is set by the user.  There is no limit to the description's length.
     *
     * @return String - string describing the trinket
     */
    public String getDescription() {
        return description;
    }

    // TODO:  Give an example of a description

    /**
     * Sets trinket's description.  The description gives extra information about the trinket and
     * is set by the user in a text field in the AddOrEditTrinketActivity.  There is no limit to the
     * description's length.
     *
     * @param description - string describing the trinket
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns trinket's name.  The name is set by the user to identify the trinket.
     *
     * @return String - string gives the trinket's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets trinket's name.  The trinket's name is its main identifier, which is displayed to
     * represent the trinket in inventories, and by which the trinket can be searched.
     *
     * @param name - string to set as the trinket's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns all pictures that have been attached to the trinket.  If no pictures have been added
     * to the trinket then an empty ArrayList is returned.
     *
     * @return ArrayList<Picture> - a list of all the pictures that have been attached to the trinket,
     * empty if no pictures have been attached
     */
    public ArrayList<Picture> getPictures() {
        return this.pictures;
    }

    /**
     * Sets pictures that are attached to trinket.  If there are any pictures already attached to the
     * trinket they will be replaced (unless they are part of the list of photos being added).
     *
     * @param pictures - list of pictures to attach to the trinket
     */
    public void setPictures(ArrayList<Picture> pictures) {
        this.pictures = pictures;
    }

    /**
     * Return trinket's quality.  The trinket's quality is represented by a string and can be either
     * "good", "average" or "poor".  The quality is determined and set by the user in the
     * AddOrEditTrinketActivity.
     *
     * @return String - string representing the trinket's quality, either "good", "average", or
     * "poor"
     */
    public String getQuality() {
        return quality;
    }

    /**
     * Sets trinket's quality.  The trinket's quality is represented by a string and can be either
     * "good", "average" or "poor".  The quality is determined and set by the user using a spinner in the
     * AddOrEditTrinketActivity.
     *
     * @param quality - string specifying the trinket's quality, either "good", "average", or
     *                "poor"
     */
    public void setQuality(String quality) {
        this.quality = quality;
    }

    /**
     * Returns the trinket's quantity.  The quantity should be a positive integer greater than 0 and
     * is returned as a string.  By default the trinket's quantity is set to 1.
     *
     * @return String - a string giving the trinket's quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * Sets the trinket's quantity.  The quantity should be a positive number greater than 0 and
     * is set in a text field in the AddOrEditTrinketActivity.  By default the trinket's quantity is
     * set to 1.
     *
     * @param quantity - string giving the item's quantity as a positive integer greater than 0
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name;
    }
}
