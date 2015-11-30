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
 * A profile class containing the information about a user and settings for their account.  The user can
 * add information about themselves such as contact information for other users to view.  They can also set
 * the default settings for their account, such as the default location for their trinkets and whether
 * they want photo downloads enabled.  A user's UserProfile is instantiated along with the user object.
 *
 * The UserProfile has a Boolean needToSave attribute to determine if there have been changes to the
 * user's profile that need to be saved to the network.  Whenever an attribute or setting in the user's
 * profile is changed then needToSave is set to true and the user will be re-saved to the network.
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
     * Default constructor.  Initializes all attributes to be blank.  By default photo downloads are
     * disabled and the default location for {@link ca.ualberta.trinkettrader.Inventory.Trinket.Trinket Trinkets}
     * is the phone's GPS location.  This constructor is called when a user logs in to the app.  As this
     * user should be saved so that their profile can be accessed from the network later, by default
     * needToSave is set to true, indicating that the user should be saved to the network.
     */
    public UserProfile() {
        this.arePhotosDownloadable = Boolean.FALSE;
        this.contactInfo = new ContactInfo();
        this.defaultLocation = new Location("this");
        this.email = "";
        this.username = "";
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
     * Set whether photos are downloadable.  This setting determines
     * whether the photos of foreign trinkets are automatically downloaded to the device.
     * If false, the user must manually select which photos to download.
     *
     * @param arePhotosDownloadable
     */
    public void setArePhotosDownloadable(Boolean arePhotosDownloadable) {
        this.arePhotosDownloadable = arePhotosDownloadable;
    }

    /**
     * Returns the user's city.  This is a String the user sets if they wish to provide a city so that
     * users can know their location for trading.  This field is optional.  If the user does not set this field then it will
     * return an empty string.
     *
     * This method is used to populate fields in the {@link EditUserProfileActivity EditUserProfileActivity}.
     *
     * @return String - the user's city.  Optional, so may be an empty string
     */
    public String getCity() {
        return this.contactInfo.getCity();
    }

    /**
     * Sets the user's city.  This is a String the user sets if they wish to provide a city so
     * that users can know their location for trading.  This field is optional.  If the user wishes to remove a city
     * they previously set then they can input an empty string to clear it.  After setting this field then needToSave is
     * set to true so that the change will be saved to the network.
     *
     * This method is used when a user enters information in the {@link EditUserProfileActivity EditUserProfileActivity}.
     *
     * @param city - the user's city.  Optional, so may be an empty string
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
        return this.email;
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
     * Returns the user's name.  This is a String the user sets if they wish to provide a name other than
     * their email address.  This field is optional.  If the user does not set this field then it will
     * return an empty string.
     *
     * This method is used to populate fields in the {@link EditUserProfileActivity EditUserProfileActivity}.
     *
     * @return String - the user's name.  Optional, so may be an empty string
     */
    public String getName() {
        return this.contactInfo.getName();
    }

    /**
     * Sets the user's name.  This is a String the user sets if they wish to provide a name other than
     * their email address.  This field is optional.  If the user wishes to remove a name they previously
     * set then they can input an empty string to clear it.  After setting this field then needToSave is
     * set to true so that the change will be saved to the network.
     *
     * This method is used when a user enters information in the {@link EditUserProfileActivity EditUserProfileActivity}.
     *
     * @param name - the user's name.  Optional, so may be an empty string
     */
    public void setName(String name) {
        this.contactInfo.setName(name);
        this.needToSave = Boolean.TRUE;
    }

    /**
     * Returns the user's postal code  This is a String the user sets if they wish to provide some contact
     * information in addition to their email. This field is optional.  If the user does not set this field then it will
     * return an empty string.
     *
     * This method is used to populate fields in the {@link EditUserProfileActivity EditUserProfileActivity}.
     *
     * @return String - the user's postal code.  Optional, so may be an empty string
     */

    public String getPostalCode() {
        return contactInfo.getPostalCode();
    }

    /**
     * Sets the user's postal code.  This is a String the user sets if they wish to provide some contact
     * information in addition to their email.  This field is optional.  If the user wishes to remove a potal code
     * they previously set then they can input an empty string to clear it.  After setting this field then needToSave is
     * set to true so that the change will be saved to the network.
     *
     * This method is used when a user enters information in the {@link EditUserProfileActivity EditUserProfileActivity}.
     *
     * @param postalCode - the user's postal code.  Optional, so may be an empty string
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
