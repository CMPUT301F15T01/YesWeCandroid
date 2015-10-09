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

import android.test.ActivityInstrumentationTestCase2;

public class OfflineBehaviourTests extends ActivityInstrumentationTestCase2 {

    public OfflineBehaviourTests(Class activityClass) {
        super(activityClass);
    }

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

