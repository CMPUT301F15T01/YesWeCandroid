package ca.ualberta.trinkettrader;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by dashley on 2015-10-21.
 */
public class FriendsList implements Collection {

    private ArrayList<Friend> friends;

    public FriendsList() {
    }

    public FriendsList(ArrayList<Friend> friends) {
        this.friends = friends;
    }

    @Override
    public boolean add(Object object) {
        return false;
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
    public Friend[] toArray(Object[] array) {
        return new Friend[0];
    }
}
