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

package ca.ualberta.trinkettrader.Inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observer;

import ca.ualberta.trinkettrader.Inventory.Trinket.Trinket;

/**
 * Specialized extension of the ArrayList class, which holds {@code Trinket Trinkets} to represent
 * the inventory of a user.  An inventory can only contain Trinkets.  In addition to all basic ArrayList
 * functionality and attributes, an inventory also has an attribute specifying if it needs to be saved to
 * the device for offline viewing.  Trinkets are ordered in the Inventory in the order in which they were
 * added, so the first Trinket added to the inventory is at position 0.
 *
 * Trinkets can be added to or deleted from the current user's inventory in the
 * {@link ca.ualberta.trinkettrader.Inventory.Trinket.AddOrEditTrinketActivity AddOrEditTrinketActivity}.
 * The
 */
public class Inventory extends ArrayList<Trinket> implements ca.ualberta.trinkettrader.Observable {

    private ArrayList<Observer> observers;
    private Boolean needToSave;

    /**
     * Constructs an inventory containing the Trinkets of the specified collection, in the order
     * they are returned by the collection's iterator.  By default the inventory needs to be saved.
     *
     * @param collection - the collection whose elements are to be included in the inventory
     */
    public Inventory(Collection<? extends Trinket> collection) {
        super(collection);
        this.needToSave = Boolean.TRUE;
    }

    /**
     * Constructs an empty inventory.  By default the inventory needs to be saved.
     */
    public Inventory() {
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
     * @param observer the Observer to remove.
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
     * Returns a boolean indicating if inventory needs to be saved.
     *
     * @return Boolean - true if inventory needs to be saved, false if not
     */
    public Boolean getNeedToSave() {
        return this.needToSave;
    }
}
