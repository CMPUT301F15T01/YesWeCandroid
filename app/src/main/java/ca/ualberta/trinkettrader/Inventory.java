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
    private ArrayList<String> categoriesList = new ArrayList<String>();
    private ArrayList<String> qualitiesList = new ArrayList<String>();


    public Inventory() {
        setListValues();
    }

    public Inventory(ArrayList<Trinket> trinkets) {
        this.trinkets = trinkets;
        setListValues();
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
    public Object[] toArray() {
        return trinkets.toArray();
    }

    @NonNull
    @Override
    public Trinket[] toArray(Object[] array) {
        return (Trinket[]) array;
    }


    public ArrayList<String> getCategoriesList() {
        return categoriesList;
    }

    public ArrayList<String> getQualitiesList() {
        return qualitiesList;
    }

    private void setListValues() {
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

        this.qualitiesList.add("Good");
        this.qualitiesList.add("Average");
        this.qualitiesList.add("Poor");
    }

}
