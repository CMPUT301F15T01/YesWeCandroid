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
 */
public class ContactInfo implements ca.ualberta.trinkettrader.Observable {

    private ArrayList<Observer> observers;
    private String name;
    private String address;
    private String city;
    private String phoneNumber;
    private String postalCode;
    private Boolean needToSave;

    public ContactInfo() {
        this.name = "";
        this.address = "";
        this.city = "";
        this.phoneNumber = "";
        this.postalCode = "";
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

    public Boolean getNeedToSave() {
        return this.needToSave;
    }

    /**
     * Returns name.
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
        this.needToSave = Boolean.TRUE;
    }

    /**
     * Gets address.
     *
     * @return String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
        this.needToSave = Boolean.TRUE;
    }

    /**
     * Gets city.
     *
     * @return String
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets city.
     *
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
        this.needToSave = Boolean.TRUE;
    }

    /**
     * Gets phone number.
     *
     * @return String
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets phone number.
     *
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.needToSave = Boolean.TRUE;
    }

    /**
     * Gets postal code.
     *
     * @return String
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets postal code.
     *
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        this.needToSave = Boolean.TRUE;
    }
}
