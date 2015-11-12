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
import java.util.Observer;

import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.User.User;

public class Searcher implements ca.ualberta.trinkettrader.Observable {

    private ArrayList<Observer> observers;

    public static Inventory searchInventoryByCategory(User user, String category) {
        return new Inventory();
    }

    public static Inventory searchInventoryByDescription(User user, String description) {
        return new Inventory();
    }

    public static Inventory searchInventoryByCategory(ArrayList<User> users, String category) {
        return new Inventory();
    }

    public static Inventory searchInventoryByDescription(ArrayList<User> users, String description) {
        return new Inventory();
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
}
