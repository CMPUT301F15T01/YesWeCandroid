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

public class Trinket implements ca.ualberta.trinkettrader.Observable {

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

    private ArrayList<Picture> pictures;
    private String accessibility;
    private String category;
    private String description;
    private String name;
    private String quality;
    private String quantity;

    /**
     * Public constructor
     */
    public Trinket() {
        accessibility = "public";
        pictures = new ArrayList<>();
        quantity = "1";
    }

    /**
     * Return trinket's accessibility
     * @return String
     */
    public String getAccessibility() {
        return accessibility;
    }

    /**
     * Sets trinket's accessibility
     * @param accessibility
     */
    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    /**
     * Return trinket's category
     * @return String
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets trinket's category
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Return trinket's description
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets trinket's description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Return trinket's name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Sets trinket's name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return trinket's pictures
     * @return ArrayList<Picture>
     */
    public ArrayList<Picture> getPictures() {
        return this.pictures;
    }

    /**
     * Set trinket's pictures
     * @param pictures
     */
    public void setPictures(ArrayList<Picture> pictures) {
        this.pictures = pictures;
    }

    /**
     * Return trinket's quality
     * @return String
     */
    public String getQuality() {
        return quality;
    }

    /**
     * Sets trinket's quality
     * @param quality
     */
    public void setQuality(String quality) {
        this.quality = quality;
    }

    /**
     * Return's quantity of Trinket
     * @return String
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * Sets trinkets quantity
     * @param quantity
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name;
    }
}
