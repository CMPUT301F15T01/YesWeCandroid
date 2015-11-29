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

package ca.ualberta.trinkettrader.User.Profile;

import android.location.Location;

import java.util.ArrayList;
import java.util.Observer;

/**
 * A profile class containing the User's personal information and whether photos will be
 * automatically downloaded to their device.
 */
public class UserProfile implements ca.ualberta.trinkettrader.Observable {

    private ArrayList<Observer> observers;
    private Boolean arePhotosDownloadable;
    private Boolean needToSave;
    private ContactInfo contactInfo;
    private Location defaultLocation;
    private String email;
    private String username;


    /**
     * UserProfile Constructor for generating new attribute classes
     */
    public UserProfile() {
        this.arePhotosDownloadable = Boolean.FALSE;
        this.contactInfo = new ContactInfo();
        this.defaultLocation = new Location("this");
        this.email = "";
        this.needToSave = Boolean.TRUE;
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
     * Returns Boolean on whether photo download setting is set. This setting determines
     * whether the photos of foreign trinkets are automatically downloaded to the device.
     * If false, the user must manually select which photos to download.
     *
     * @return Boolean
     */
    public Boolean getArePhotosDownloadable() {
        return arePhotosDownloadable;
    }

    /**
     * Set whether photos are downloadable
     *
     * @param arePhotosDownloadable
     */
    public void setArePhotosDownloadable(Boolean arePhotosDownloadable) {
        this.arePhotosDownloadable = arePhotosDownloadable;
    }

    /**
     * Returns user's city
     *
     * @return String
     */
    public String getCity() {
        return this.contactInfo.getCity();
    }

    /**
     * Sets user's city
     *
     * @param city
     */
    public void setCity(String city) {

        this.contactInfo.setCity(city);
        this.needToSave = Boolean.TRUE;
    }

    /**
     * Returns User's ContactInfo instance.
     *
     * @return ContactInfo
     */
    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    /**
     * Sets user's ContactInfo instance
     *
     * @param contactInfo
     */
    public void setContactInfo(ContactInfo contactInfo) {

        this.contactInfo = contactInfo;
        this.needToSave = Boolean.TRUE;
    }

    /**
     * Returns the user's email
     *
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
        this.username = email;
        this.needToSave = Boolean.TRUE;
    }

    /**
     * Returns the user's name
     *
     * @return String
     */
    public String getName() {
        return this.contactInfo.getName();
    }

    /**
     * Set's the user's name
     *
     * @param name
     */
    public void setName(String name) {
        this.contactInfo.setName(name);
        this.needToSave = Boolean.TRUE;
    }

    /**
     * Returns the user's postal code
     *
     * @return String
     */

    public String getPostalCode() {
        return contactInfo.getPostalCode();
    }

    /**
     * Sets the user's postal code
     *
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        contactInfo.setPostalCode(postalCode);
        this.needToSave = Boolean.TRUE;
    }

    /**
     * Returns user's username
     *
     * @return
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets user's username
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
        this.email = username;
        this.needToSave = Boolean.TRUE;
    }

    /**
     * Returns whether the user's profile data needs to be saved. This variable is set when there
     * are changes to any of the User's personal information.
     *
     * @return
     */
    public Boolean getNeedToSave() {
        return needToSave | this.contactInfo.getNeedToSave();
    }

    public Location getDefaultLocation() {
        return defaultLocation;
    }

    public void setDefaultLocation(Location defaultLocation) {
        this.defaultLocation = defaultLocation;
    }
}
