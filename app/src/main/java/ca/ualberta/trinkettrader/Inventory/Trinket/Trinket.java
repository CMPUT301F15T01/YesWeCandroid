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
 * may have photos attached to show what it looks like.  The possible categories of a Trinket can be found
 * in the app's arrays.xml resource and are: Anklet, Barrett, Belt, Bracelet, Brooch, Earrings, Headband,
 * Necklace, Ring, and Tiara.  The possible levels of quality a Trinket can assessed at can be found
 * in the app's arrays.xml resource and are: Average, Good, and Poor.  A user represents having
 * more than one of the same kind of trinket by setting the item's quantity (for example, if they
 * have three identical blue bracelets.  The properties of a trinket are set or edited from the
 * {@link AddOrEditTrinketActivity AddOrEditTrinketActivity}.
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
     * Creates a new trinket object.  By default the trinket's accessibility is public, meaning it can
     * be viewed by other users of Trinket Trader and its quantity is 1, meaning the user has only one
     * instance of this type of trinket. No other field is set by this constructor, and must be set by the user in the
     * {@link AddOrEditTrinketActivity AddOrEditTrinketActivity}.  This constructor is invoked by the
     * {@link AddOrEditTrinketActivity AddOrEditTrinketActivity}.
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

    /**
     * Returns the geographic location of approximately where the Trinket is currently located.  This
     * can inform other users of the app if it is feasible for them to trade for this Trinket, or if
     * it would be too far away to easily complete the trade.  The Location is optional and may not be set.
     *
     * This method is invoked to populate the Location field in the {@link AddOrEditTrinketActivity AddOrEditTrinketActivity}
     * when a Trinket is being edited.
     *
     * @return {@link Location Location} - Android location specifying the coordinates of approximately
     * where the Trinket is stored
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the geographic location of approximately where the Trinket is currently located.  This
     * can inform other users of the app if it is feasible for them to trade for this Trinket, or if
     * it would be too far away to easily complete the trade.  The Location is optional and does
     * not need to be set.
     *
     * This method is invoked when the user sets or changes a Trinket's location in the
     * {@link AddOrEditTrinketActivity AddOrEditTrinketActivity}.
     *
     * @param location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Return trinket's accessibility as a string.  Will be either "public" or "private".  By default
     * this value is "public".  If a Trinket's accessibility is public then it
     * can be viewed by other user's in the system when they view the current user's inventory
     * (with the {@link ca.ualberta.trinkettrader.Friends.FriendsInventoryActivity FriendsInventoryActivity}).
     * If the Trinket is "private", then other users cannot view it and it will only be viewable to the
     * current user.
     *
     * @return String - string describing trinket's accessibility as either "public" or "private"
     */
    public String getTrinketAccessibility() {
        return accessibility;
    }

    /**
     * Sets trinket's accessibility.  Must be either "public" or "private".  If a Trinket's accessibility
     * is set to public then it can be viewed by other user's in the system when they view the current
     * user's inventory (with the {@link ca.ualberta.trinkettrader.Friends.FriendsInventoryActivity FriendsInventoryActivity}).
     * If the Trinket is "private", then other users cannot view it and it will only be viewable to the
     * current user.  This method is invoked when the user toggles a Trinket's availability setting in the
     * {@link AddOrEditTrinketActivity AddOrEditTrinketActivity}.
     *
     * @param accessibility - string describing desired accessibility for the trinket, either "public"
     *                      or "private"
     */
    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    /**
     * Return trinket's category as a string.  The trinket can be classified as one of 10 categories.
     * The possible categories of a Trinket can be found in the app's arrays.xml resource
     * and are: Anklet, Barrett, Belt, Bracelet, Brooch, Earrings, Headband, Necklace, Ring, or Tiara.
     * This method is invoked to populate the Category field in the {@link AddOrEditTrinketActivity AddOrEditTrinketActivity}
     * when a Trinket is being edited.  It is also used to find Trinkets of the relevant category when
     * searching an inventory by category.
     *
     * @return String - string of which category the trinket is.  Either Anklet, Barrett, Belt, Bracelet,
     * Brooch, Earrings, Headband, Necklace, Ring, or Tiara.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets trinket's category as one of 10 categories.  The possible categories of a Trinket can be found
     * in the app's arrays.xml resource and are: Anklet, Barrett, Belt, Bracelet, Brooch, Earrings, Headband,
     * Necklace, Ring, or Tiara.  This method is invoked when the user sets or changes a Trinket's category in the
     * {@link AddOrEditTrinketActivity AddOrEditTrinketActivity}.
     *
     * @param category - string naming which category the trinket belongs to
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Returns trinket's description.  The description is free-form text that gives extra information about the trinket and
     * is set by the user.  For example, a user could set the description of a "Red Bracelet" Trinket as
     * "A deep crimson red bracelet.  The bracelet is made of bakelite and is rather heavy.  It was bequeathed to
     * me by my great-grandmother on her deathbed.  May be cursed."  The description is an optional field and may be left blank.
     *
     * This method is invoked to populate the Description field in the {@link AddOrEditTrinketActivity AddOrEditTrinketActivity}
     * when a Trinket is being edited.
     *
     * @return String - string describing the trinket
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets trinket's description.  The description gives extra information about the trinket and
     * is set by the user in a text field in the AddOrEditTrinketActivity.  For example, a user could
     * set the description of a "Red Bracelet" Trinket as "A deep crimson red bracelet.  The
     * bracelet is made of bakelite and is rather heavy.  It was bequeathed to me by my great-grandmother
     * on her deathbed.  May be cursed."
     *
     * There is no limit to the description's length.  The description is an optional field and can be left blank.
     * This method is invoked when the user sets or changes a Trinket's description in the
     * {@link AddOrEditTrinketActivity AddOrEditTrinketActivity}.
     *
     * @param description - string describing the trinket
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns trinket's name.  The name is set by the user to identify the trinket.  The Trinket' name
     * can be any text the user wishes, but should ideally be a concise summary of what the trinket is.
     * For example, a silver tiara with no jewels on it could be named "Plain Silver Tiara".
     *
     * This method is invoked to populate the Name field in the {@link AddOrEditTrinketActivity AddOrEditTrinketActivity}
     * when a Trinket is being edited.
     *
     * @return String - string gives the trinket's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets trinket's name.  The trinket's name is its main identifier, which is displayed to
     * represent the trinket in inventories, and by which the trinket can be searched.  The Trinket' name
     * can be any text the user wishes, but should ideally be a concise summary of what the trinket is.
     * For example, a silver tiara with no jewels on it could be named "Plain Silver Tiara".  There is
     * no limit to the name's length.
     *
     * This method is invoked when the user sets or changes a Trinket's name in the
     * {@link AddOrEditTrinketActivity AddOrEditTrinketActivity}.
     *
     * @param name - string to set as the trinket's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns all pictures that have been attached to the trinket.  If no pictures have been added
     * to the trinket then an empty ArrayList is returned.  Pictures are added to the Trinket by the
     * Trinket's owner in order to sow other users what the Trinket actually looks like.  The picture can
     * either be taken with the phone's camera or selected from the phone's gallery.  Adding photos is
     * optional, so there may be no photos attached to a Trinket.
     *
     * This method is invoked to populate the photos display in the {@link AddOrEditTrinketActivity AddOrEditTrinketActivity}
     * when a Trinket is being edited.
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
     * Adding photos is optional, so an empty array list can be input to this method to attach no photos.
     * Pictures are added to the Trinket by the Trinket's owner in order to sow other users what the Trinket
     * actually looks like.  The picture can either be taken with the phone's camera or selected from
     * the phone's gallery.
     *
     * This method is invoked in the {@link AddOrEditTrinketActivity AddOrEditTrinketActivity}
     * when a photo is attached or deleted from the Trinket being added or edited.
     *
     * @param pictures - list of pictures to attach to the trinket
     */
    public void setPictures(ArrayList<Picture> pictures) {
        this.pictures = pictures;
    }

    /**
     * Return trinket's quality.  The trinket's quality is represented by a string and can be either
     * "good", "average" or "poor".  The quality is determined and set by the user in the
     * {@link AddOrEditTrinketActivity AddOrEditTrinketActivity} with an Android Spinner.  The values for
     * this spinner are stored in the arrays.xml resource.
     *
     * This method is invoked to populate the Quality field in the {@link AddOrEditTrinketActivity AddOrEditTrinketActivity}
     * when a Trinket is being edited.
     *
     * @return String - string representing the trinket's quality, either "good", "average", or
     * "poor"
     */
    public String getQuality() {
        return quality;
    }

    /**
     * Sets trinket's quality.  The trinket's quality is represented by a string and can be either
     * "good", "average" or "poor".  The quality of the Trinket is determined and set by the user
     *  using a spinner in the AddOrEditTrinketActivity with an Android Spinner.  The values for
     * this spinner are stored in the arrays.xml resource.
     *
     * This method is invoked when the user sets or changes a Trinket's quality in the
     * {@link AddOrEditTrinketActivity AddOrEditTrinketActivity}.
     *
     * @param quality - string specifying the trinket's quality, either "good", "average", or
     *                "poor"
     */
    public void setQuality(String quality) {
        this.quality = quality;
    }

    /**
     * Returns the trinket's quantity as a string.  The quantity should be a positive integer greater than 0 and
     * is returned as a string.  By default the trinket's quantity is set to 1.  This value is meant to
     * represent how many Trinkets of a certain type a user has.  For example, a user may own only one
     * "Great Imperial Crown" and so they would leave the quantity of this Trinket as "1", but they may have
     * 5 identical "Bright Pink Plastic Bangle"s, in which case they would list the quantity of this item
     * as "5".
     *
     * This method is invoked to populate the Quantity field in the {@link AddOrEditTrinketActivity AddOrEditTrinketActivity}
     * when a Trinket is being edited.
     *
     * @return String - a string giving the trinket's quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * Sets the trinket's quantity with a string.  The quantity should be a positive number greater than 0 and
     * is set in a text field in the AddOrEditTrinketActivity.  By default the trinket's quantity is
     * set to 1.  This value is meant to represent how many Trinkets of a certain type a user has.
     * For example, a user may own only one "Great Imperial Crown" and so they would leave the quantity
     * of this Trinket as "1", but they may have 5 identical "Bright Pink Plastic Bangle"s, in which
     * case they would list the quantity of this item as "5".
     *
     * This method is invoked when the user sets or changes a Trinket's quantity in the
     * {@link AddOrEditTrinketActivity AddOrEditTrinketActivity}.
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
