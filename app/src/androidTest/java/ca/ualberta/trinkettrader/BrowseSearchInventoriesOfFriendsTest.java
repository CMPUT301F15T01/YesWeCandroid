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

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Testing file for "Browse Search Inventories Of Friends" use cases.
 * Multiple-activity testing tutorial taken from:
 * http://stackoverflow.com/questions/1759626/how-do-you-test-an-android-application-across-multiple-activities
 */
public class BrowseSearchInventoriesOfFriendsTest extends ActivityInstrumentationTestCase2 {

    public BrowseSearchInventoriesOfFriendsTest(Class activityClass) {
        super(activityClass);
    }

    User borrower;
    User friend1;
    User friend2;
    Inventory friend1Inventory;
    Trinket trinket1;
    Trinket trinket2;
    Trinket trinket3;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        borrower = new User();
        friend1 = new User();
        friend2 = new User();
        friend1Inventory = new Inventory();
        trinket1 = new Trinket();
        trinket2 = new Trinket();
        trinket3 = new Trinket();

        trinket1.setAccessibility("public");
        friend1Inventory.add(trinket1);

        trinket2.setAccessibility("private");
        friend1Inventory.add(trinket2);

        trinket3.setAccessibility("public");
        friend1Inventory.add(trinket3);

        borrower.getFriendsList().add(friend1);
        friend1.getFriendsList().add(borrower);


        Inventory inventory = borrower.getInventory();
        Iterator iterator = inventory.iterator();
        while (iterator.hasNext()) {
            assertTrue(iterator.next() == trinket1);
        }

    }

    public void testSearchInventoriesOfFriendsSingleFriend() {
        Instrumentation instrumentation  = getInstrumentation();

        //Set an activity monitor for DisplayFriendsActivity
        Instrumentation.ActivityMonitor displayFriendsMonitor = instrumentation.addMonitor(DisplayFriendsActivity.class.getName(), null, false);

        //Start the activity that we set the monitor for
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(instrumentation.getTargetContext(), DisplayFriendsActivity.class.getName());
        instrumentation.startActivitySync(intent);

        //Wait for DisplayFriendsActivity to start
        DisplayFriendsActivity currentActivity = (DisplayFriendsActivity) getInstrumentation().waitForMonitorWithTimeout(displayFriendsMonitor, 5);
        assertNotNull(currentActivity);

        //set up monitor for User profile activity that should appear with button click below
        Instrumentation.ActivityMonitor userProfileMonitor = instrumentation.addMonitor(DisplayUserProfileActivity.class.getName(), null, false);

        //Select friend1
        final ListView friendsListView = currentActivity.getFriendsListView();
        currentActivity.runOnUiThread(new Runnable() {
            public void run() {
                View v = friendsListView.getChildAt(0);
                friendsListView.performItemClick(v, 0, v.getId());
                //TODO: assert that friend1 was selected
            }
        });

        //Remove monitor for activity we no longer care about
        instrumentation.removeMonitor(displayFriendsMonitor);

        //Check that UserProfile Page started up
        DisplayUserProfileActivity profileActivity = (DisplayUserProfileActivity) userProfileMonitor.waitForActivityWithTimeout(5);
        assertNotNull("User profile activity for selected friend is Null", profileActivity);
        assertEquals("User profile activity has not been called", 1, userProfileMonitor.getHits());
        assertEquals("Activity of wrong type", DisplayUserProfileActivity.class, profileActivity.getClass());

        //Setup monitor for DisplayInventoryActivity before clicking button
        Instrumentation.ActivityMonitor inventoryMonitor = instrumentation.addMonitor(DisplayInventoryActivity.class.getName(), null, false);

        //Click the 'View Inventory' button
        Button inventoryButton = (Button) currentActivity.findViewById(R.id.view_inventory_button);
        assertNotNull(inventoryButton);
        assertEquals("View not a button", Button.class, inventoryButton.getClass());
        TouchUtils.clickView(this, inventoryButton);

        //Assert that DisplayInventoryActivity starts up
        DisplayInventoryActivity inventoryActivity = (DisplayInventoryActivity) inventoryMonitor.waitForActivityWithTimeout(5);
        assertNotNull("Inventory Activity is null", inventoryActivity);
        assertEquals("Inventory activity has not been called", 1, inventoryMonitor.getHits());
        assertEquals("Activity of wrong type", DisplayInventoryActivity.class, inventoryActivity.getClass());

        //Check that correct inventory items are displayed
        ListView displayedTrinkets = (ListView)  inventoryActivity.findViewById(R.id.displayedTrinkets);
        ArrayList<Trinket> visibleTrinkets = this.getDisplayedItems(displayedTrinkets);
        assertTrue(visibleTrinkets.contains(trinket1));
        assertTrue(visibleTrinkets.contains(trinket3));
        assertFalse(visibleTrinkets.contains(trinket2));
    }

    public ArrayList<Trinket> getDisplayedItems(ListView view){
        int count = view.getAdapter().getCount();
        ArrayList<Trinket> listData = new ArrayList<Trinket>();
        for (int i = 0; i < count; i++) {
            listData.add((Trinket) view.getAdapter().getItem(i));
        }
        return listData;
    }

    public void testSearchInventoriesOfFriendsByCategory() {
        User user1 = new User();

        User user2 = new User();
        Inventory user2Inventory = new Inventory();
        Trinket trinket1 = new Trinket();
        trinket1.setAccessibility("public");
        trinket1.setCategory("necklace");
        user2Inventory.add(trinket1);
        Trinket trinket2 = new Trinket();
        trinket2.setAccessibility("public");
        trinket2.setCategory("ring");
        user2Inventory.add(trinket2);

        User user3 = new User();
        Trinket trinket3 = new Trinket();
        trinket3.setAccessibility("public");
        trinket3.setAccessibility("necklace");
        user2Inventory.add(trinket3);

        user1.getFriendsList().add(user2);
        user2.getFriendsList().add(user1);

        Inventory inventory = Searcher.searchInventoryByCategory(user1, "necklace");
        Iterator iterator = inventory.iterator();
        while (iterator.hasNext()) {
            assertTrue(iterator.next() == trinket1);
        }
    }

    public void testSearchInventoriesOfFriendsByTextualQuery() {
        User user1 = new User();

        User user2 = new User();
        Inventory user2Inventory = new Inventory();
        Trinket trinket1 = new Trinket();
        trinket1.setAccessibility("public");
        trinket1.setDescription("rose gold ring with a garnet");
        user2Inventory.add(trinket1);
        Trinket trinket2 = new Trinket();
        trinket2.setDescription("rose gold ring with a ruby");
        user2Inventory.add(trinket2);

        User user3 = new User();
        Trinket trinket3 = new Trinket();
        trinket3.setAccessibility("public");
        user2Inventory.add(trinket3);

        user1.getFriendsList().add(user2);
        user2.getFriendsList().add(user1);

        Inventory inventory = Searcher.searchInventoryByDescription(user1, "garnet");
        Iterator iterator = inventory.iterator();
        while (iterator.hasNext()) {
            assertTrue(iterator.next() == trinket1);
        }
    }

    public void testSharedtrinketsAreSearchableByFriends() {
        User user1 = new User();

        User user2 = new User();
        Inventory user2Inventory = new Inventory();
        Trinket trinket1 = new Trinket();
        trinket1.setAccessibility("public");
        user2Inventory.add(trinket1);
        Trinket trinket2 = new Trinket();
        trinket2.setAccessibility("private");
        user2Inventory.add(trinket2);

        User user3 = new User();
        Trinket trinket3 = new Trinket();
        trinket3.setAccessibility("public");
        user2Inventory.add(trinket3);

        user1.getFriendsList().add(user2);
        user2.getFriendsList().add(user1);

        Inventory inventory = user1.getInventory();
        Iterator iterator = inventory.iterator();
        while (iterator.hasNext()) {
            assertTrue(iterator.next() == trinket1);
        }
    }
}
