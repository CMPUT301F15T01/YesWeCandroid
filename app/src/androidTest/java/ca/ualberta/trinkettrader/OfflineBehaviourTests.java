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
import java.util.ArrayList;

public class OfflineBehaviourTests extends ActivityInstrumentationTestCase2 {

    public OfflineBehaviourTests(Class activityClass) {
        super(activityClass);
    }

    public void testAddInventoryOffline(){

        Inventory inventory =  new Inventory();
        //UserSettings state = new UserSettings();
        //assertTrue(state.hasActiveInternetConnection());
        Trinket a = new Trinket();
        inventory.add(a);
        //assertFalse(state.isOffline());
        //assertTrue(getDataBaseOFInvetory.contains(a));
        assertNotNull(null);
    }

    public void testMakeTradeOffline(){

        /**
         * Is there a way to turn off internet connection in unit tests?
         */
        /*Assert no internet connection*/
        //UserSettings state = new UserSettings();
        //assertFalse(state.hasActiveInternetConnection());
        User user = LoggedInUser.getInstance();
        User user1 = new Friend();
        Trade trade = new Trade(user.getInventory(), user.getTradeManager(), user1.getInventory(), user1.getTradeManager());


        /*Assert internet connection*/
        //assertTrue(state.hasActiveInternetConnection());

        /*Push Data*/
        /**
         * We have to discuss what all the communication managers will do.
         */
        TradeCommunicationsManager manager = new TradeCommunicationsManager();
        //manager.pushData();
        //assertTrue(getDataBaseOfTrades.hasItem(a));
        assertNotNull(null);
    }

    public void testViewPreviouslyViewedInventories(){

        /**
         * Another UI test ..therefore incomplete*/
        User u = LoggedInUser.getInstance();
        Inventory inventory = new Inventory();
        //u.addToPreviouslyViewedInventories(inventory);
        ArrayList<Inventory> viewed = new ArrayList<Inventory>();
        //displayInventory(inventory);
        assertTrue(viewed.contains(inventory));
        assertNotNull(null);
    }
}

