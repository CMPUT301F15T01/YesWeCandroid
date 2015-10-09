package ca.ualberta.trinkettrader;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by anju on 09/10/15.
 */
public class OfflineBehaviourTests extends ActivityInstrumentationTestCase2 {

    public void testAddInventoryOffline(){
        Inventory inventory =  new Inventory();
        SystemState state = getState();
        assertTrue(state.isffline());
        Item a = new Item();
        inventory.add(a);
        assertFalse(state.isffline());
        assertTrue(getDataBaseOFInvetory.hasItem(a));
    }

    public void testMakeTradeOffline(){
        SystemState state = getState();
        assertTrue(state.isOffline());
        Trade a = new Trade();
        assertFalse(state.isOffline());
        inventory.pushData();
        assertTrue(getDataBaseOfTrades.hasItem(a));

    }

    public void testViewPreviouslyViewedInventories(){
        User u = new User();
        Inventory inventory = new Inventory();
        ArrayList<Inventory> viewed = new ArrayList<Inventories>();
        displayInventory(inventory);
        assertTrue(viewed.contains(inventory));
    }
}

