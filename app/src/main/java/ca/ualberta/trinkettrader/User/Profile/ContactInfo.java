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

import java.util.ArrayList;
import java.util.Observer;

/**
 * Class that serves as a container for contact information. This should
 * appear as an attribute of a user. Whenever this class is used it should
 * have been obtained by the getter contained in the user.
 *
 * All fields of contact information are optional attributes of a user, but can be set to help facilitate
 * trades by giving information about the location of the user and how they can be contacted outside of the
 * app.
 */
public class ContactInfo implements ca.ualberta.trinkettrader.Observable {

    private ArrayList<Observer> observers;
    private String name;
    private String address;
    private String city;
    private String phoneNumber;
    private String postalCode;
    private Boolean needToSave;

    /**
     * Default constructor.  Sets all contact information fields to empty strings.  Since there is no
     * meaningful information being set, by default the needToSave attribute is set to false, indicating
     * that this information does not need to be saved.  This constructor is called when a new
     * {@link ca.ualberta.trinkettrader.User.User User} is created for the first time.
     */
    public ContactInfo() {
        this.name = "";
        this.address = "";
        this.city = "";
        this.phoneNumber = "";
        this.postalCode = "";
        this.needToSave = false;
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
     * Returns a boolean indicating if the {@link ca.ualberta.trinkettrader.User.User User} with this
     * contact information needs to be saved to the network.  If the contact information of the user has
     * been changed then needToSave is set to true and the user needs to be saved.  Otherwise, this should
     * return false indicating that the user does not need to be saved.
     *
     * @return Boolean - if true then the User with this contact information needs to be re-saved to
     * the network, if false then there is nothing new that needs to be saved
     */
    public Boolean getNeedToSave() {
        return this.needToSave;
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
        return name;
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
        this.name = name;
        this.needToSave = Boolean.TRUE;
    }

    /**
     * Returns the user's address.  This is a String the user sets if they wish to provide an address so
     * that users can know their location for trading.  This field is optional.  If the user does not set this field then it will
     * return an empty string.
     *
     * This method is used to populate fields in the {@link EditUserProfileActivity EditUserProfileActivity}.
     *
     * @return String - the user's address.  Optional, so may be an empty string
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the user's address.  This is a String the user sets if they wish to provide an address so
     * that users can know their location for trading.  This field is optional.  If the user wishes to remove an address
     * they previously set then they can input an empty string to clear it.  After setting this field then needToSave is
     * set to true so that the change will be saved to the network.
     *
     * This method is used when a user enters information in the {@link EditUserProfileActivity EditUserProfileActivity}.
     *
     * @param address - the user's address.  Optional, so may be an empty string
     */
    public void setAddress(String address) {
        this.address = address;
        this.needToSave = Boolean.TRUE;
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
        return city;
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
        this.city = city;
        this.needToSave = Boolean.TRUE;
    }

    /**
     * Returns the user's phone number.  This is a String the user sets if they wish to provide some contact
     * information in addition to their email. This field is optional.  If the user does not set this field then it will
     * return an empty string.
     *
     * This method is used to populate fields in the {@link EditUserProfileActivity EditUserProfileActivity}.
     *
     * @return String - the user's phone number.  Optional, so may be an empty string
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the user's phone number.  This is a String the user sets if they wish to provide some contact
     * information in addition to their email.  This field is optional.  If the user wishes to remove a phone number
     * they previously set then they can input an empty string to clear it.  After setting this field then needToSave is
     * set to true so that the change will be saved to the network.
     *
     * This method is used when a user enters information in the {@link EditUserProfileActivity EditUserProfileActivity}.
     *
     * @param phoneNumber - the user's phone number.  Optional, so may be an empty string
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
        return postalCode;
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
        this.postalCode = postalCode;
        this.needToSave = Boolean.TRUE;
    }
}
