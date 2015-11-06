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

package ca.ualberta.trinkettrader;

import java.util.ArrayList;
import java.util.Observer;

public class UserProfile implements ca.ualberta.trinkettrader.Observable {

    private ArrayList<Observer> observers;

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
        for (Observer observer: observers) {
            observer.notify();
        }
    }

    private Boolean arePhotosDownloadable;
    private ContactInfo contactInfo;
    private String email;
    private String username;
    private Boolean needToSave;

    public UserProfile() {
        this.email = "";
        this.contactInfo = new ContactInfo();
        this.needToSave = Boolean.TRUE;
        this.arePhotosDownloadable = Boolean.FALSE;
    }

    public Boolean getArePhotosDownloadable() {
        return arePhotosDownloadable;
    }

    public void setArePhotosDownloadable(Boolean arePhotosDownloadable) {
        this.arePhotosDownloadable = arePhotosDownloadable;
    }

    public String getCity(){
        return this.contactInfo.getCity();
    }

    public void setCity(String city) {

        this.contactInfo.setCity(city);
        this.needToSave = Boolean.TRUE;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {

        this.contactInfo = contactInfo;
        this.needToSave = Boolean.TRUE;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.needToSave = Boolean.TRUE;
    }

    public String getName() {
        return this.contactInfo.getName();
    }

    public void setName(String name) {
        this.contactInfo.setName(name);
        this.needToSave = Boolean.TRUE;
    }

    public String getPostalCode() {
        return contactInfo.getPostalCode();
    }


    public void setPostalCode(String postalCode) {
        contactInfo.setPostalCode(postalCode);
        this.needToSave = Boolean.TRUE;
    }

    public String getUsername() {
        return this.username;
    }

    protected void setUsername(String username) {
        this.username = username;
    }

    public Boolean getNeedToSave() {
        return needToSave | this.contactInfo.getNeedToSave();
    }
}
