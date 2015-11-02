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

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Observable;

public class Inventory extends Observable implements Collection {

    private ArrayList<Trinket> trinkets;

    public Inventory() {
        this(new ArrayList<Trinket>());
    }

    public Inventory(ArrayList<Trinket> trinkets) {
        this.trinkets = trinkets;
    }

    @Override
    public boolean add(Object object) {
        return trinkets.add((Trinket) object);
    }

    @Override
    public boolean addAll(Collection collection) {
        return trinkets.addAll(collection);
    }

    @Override
    public void clear() {
    }

    @Override
    public boolean contains(Object object) {
        return trinkets.contains((object));
    }

    @Override
    public boolean containsAll(Collection collection) {
        return trinkets.containsAll(collection);
    }

    @Override
    public boolean isEmpty() {
        return trinkets.isEmpty();
    }

    @NonNull
    @Override
    public Iterator<Trinket> iterator() {
        return null;
    }

    @Override
    public boolean remove(Object object) {
        return trinkets.remove(object);
    }

    @Override
    public boolean removeAll(Collection collection) {
        return trinkets.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection collection) {
        return trinkets.retainAll(collection);
    }

    @Override
    public int size() {
        return trinkets.size();
    }

    @NonNull
    @Override
    public Trinket[] toArray() {
        return new Trinket[0];
    }

    @NonNull
    @Override
    public Trinket[] toArray(Object[] array) {
        return new Trinket[0];
    }
}
