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
 * <p/>
 * Trinkets can be added to or deleted from the current user's inventory in the
 * {@link ca.ualberta.trinkettrader.Inventory.Trinket.AddOrEditTrinketActivity AddOrEditTrinketActivity}.
 * The trinkets in the current user's inventory can be viewed in the {@link InventoryActivity InventoryActivity},
 * and the details about the inventory, such as how many items are in it can be viewed in the
 * {@link InventoryDetailsActivity InventoryDetailsActivity}.
 */
public class Inventory extends ArrayList<Trinket> implements ca.ualberta.trinkettrader.Observable {

    private ArrayList<Observer> observers;
    private Boolean needToSave;

    /**
     * Constructs an inventory containing the Trinkets of the specified collection, in the order
     * they are returned by the collection's iterator.  Constructs an Inventory from
     * an arbitrary {@link Collection Collection} of Trinkets using the equivalent {@link ArrayList ArrayList}
     * constructor.  The given collection can be empty.  By default the inventory needs to be saved.
     * <p/>
     * This constructor should be called when a user with an existing account logs in.  The Inventory is
     * instantiated with the user's previous Inventory data.  This data is retrieved from the Elastic Search
     * server if the device is online, or from the user's local phone storage if it is offline.  If the
     * user has no trinkets in their Inventory, this constructor will still be called with an empty collection.
     *
     * @param collection - the collection whose elements are to be included in the inventory
     */
    public Inventory(Collection<? extends Trinket> collection) {
        super(collection);
        this.needToSave = Boolean.TRUE;
    }

    /**
     * Default constructor used when the current user logs in for the first time (thereby creating their
     * account).  A user begins with no friends, so an empty FriendsList is instantiated using the
     * default {@link ArrayList ArrayList} constructor.  By default the inventory needs to be saved.
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
     * Returns a boolean indicating if inventory needs to be saved due to a change to any of the Trinkets
     * in it.  If the inventory has been modified and needs to be saved, then this method returns <code>True</code>.
     * If there have been no changes to the inventory then it does not need to be saved and this method returns
     * <code>False</code>.  Actions that could cause the inventory to need to be saved are creating the
     * inventory; or adding, deleting, or editing a Trinket in the inventory using the
     * {@link ca.ualberta.trinkettrader.Inventory.Trinket.AddOrEditTrinketActivity AddOrEditTrinketActivity}.
     *
     * @return Boolean - true if inventory needs to be saved, false if not
     */
    public Boolean getNeedToSave() {
        return this.needToSave;
    }
}
