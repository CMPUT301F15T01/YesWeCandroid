package ca.ualberta.trinkettrader;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Observable;

/**
 * Created by dashley on 2015-10-21.
 */
public class Inventory extends Observable implements Collection {

    private ArrayList<Trinket> trinkets;
    private ArrayList<String> categoriesList;

    public Inventory() {
    }

    public Inventory(ArrayList<Trinket> trinkets) {
        this.trinkets = trinkets;

        // Create the 10 categories
        this.categoriesList.add("ring");
        this.categoriesList.add("earrings");
        this.categoriesList.add("bracelet");
        this.categoriesList.add("necklace");
        this.categoriesList.add("anklet");
        this.categoriesList.add("belt");
        this.categoriesList.add("pendant");
        this.categoriesList.add("headband");
        this.categoriesList.add("barrett");
        this.categoriesList.add("tiara");
    }

    @Override
    public boolean add(Object object) {
        return trinkets.add((Trinket) object);
    }

    @Override
    public boolean addAll(Collection collection) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean contains(Object object) {
        return false;
    }

    @Override
    public boolean containsAll(Collection collection) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @NonNull
    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public boolean remove(Object object) {
        return false;
    }

    @Override
    public boolean removeAll(Collection collection) {
        return false;
    }

    @Override
    public boolean retainAll(Collection collection) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NonNull
    @Override
    public Trinket[] toArray(Object[] array) {
        return new Trinket[0];
    }


    public ArrayList<String> getCategoriesList() {
        return categoriesList;
    }

}
