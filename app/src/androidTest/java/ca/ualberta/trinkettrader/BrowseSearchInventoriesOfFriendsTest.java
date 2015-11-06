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
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

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
    Friend friend1;
    Friend friend2;
    Trinket trinket1;
    Trinket trinket2;
    Trinket trinket3;
    Inventory friend1Inventory;
    Instrumentation instrumentation;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        borrower = LoggedInUser.getInstance();
        friend1 = new Friend();
        friend2 = new Friend();
        friend1Inventory = new Inventory();
        trinket1 = new Trinket();
        trinket2 = new Trinket();
        trinket3 = new Trinket();
        instrumentation  = getInstrumentation();




        Inventory inventory = borrower.getInventory();
        Iterator iterator = inventory.iterator();
        while (iterator.hasNext()) {
            assertTrue(iterator.next() == trinket1);
        }

    }

    public void testSearchInventoriesOfFriendsSingleFriend() {
        //set up model
        trinket1.setAccessibility("public");
        friend1Inventory.add(trinket1);

        trinket2.setAccessibility("private");
        friend1Inventory.add(trinket2);

        trinket3.setAccessibility("public");
        friend1Inventory.add(trinket3);

        borrower.getFriendsList().add(friend1);
        friend1.getFriendsList().add((Friend)borrower);

        //Set an activity monitor for DisplayFriendsActivity
        Instrumentation.ActivityMonitor displayFriendsMonitor = instrumentation.addMonitor(DisplayFriendsActivity.class.getName(), null, false);

        //Start the activity that we set the monitor for
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(instrumentation.getTargetContext(), DisplayFriendsActivity.class.getName());
        instrumentation.startActivitySync(intent);

        //Wait for DisplayFriendsActivity to start
        DisplayFriendsActivity displayFriendsActivity = (DisplayFriendsActivity) getInstrumentation().waitForMonitorWithTimeout(displayFriendsMonitor, 5);
        assertNotNull(displayFriendsActivity);

        //set up monitor for User profile activity that should appear with button click below
        Instrumentation.ActivityMonitor userProfileMonitor = instrumentation.addMonitor(DisplayUserProfileActivity.class.getName(), null, false);

        //Select friend1
        final ListView friendsListView = displayFriendsActivity.getFriendsListView();
        displayFriendsActivity.runOnUiThread(new Runnable() {
            public void run() {
                View v = friendsListView.getChildAt(0);
                friendsListView.performItemClick(v, 0, v.getId());
                //TODO: assert that friend1 was selected
            }
        });

        //Check that UserProfile Page started up
        DisplayUserProfileActivity profileActivity = (DisplayUserProfileActivity) userProfileMonitor.waitForActivityWithTimeout(5);
        assertNotNull("User profile activity for selected friend is Null", profileActivity);
        assertEquals("User profile activity has not been called", 1, userProfileMonitor.getHits());
        assertEquals("Activity of wrong type", DisplayUserProfileActivity.class, profileActivity.getClass());

        //Setup monitor for DisplayInventoryActivity before clicking button
        Instrumentation.ActivityMonitor inventoryMonitor = instrumentation.addMonitor(DisplayInventoryActivity.class.getName(), null, false);

        //Click the 'View Inventory' button
        Button inventoryButton = (Button) displayFriendsActivity.findViewById(R.id.view_inventory_button);
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

        //TODO: check that newly added classes fit with UML

        //Rmove all the monitors we used
        instrumentation.removeMonitor(inventoryMonitor);
        instrumentation.removeMonitor(displayFriendsMonitor);
        instrumentation.removeMonitor(userProfileMonitor);
    }

    public ArrayList<Trinket> getDisplayedItems(ListView view){
        int count = view.getAdapter().getCount();
        ArrayList<Trinket> listData = new ArrayList<Trinket>();
        for (int i = 0; i < count; i++) {
            listData.add((Trinket) view.getAdapter().getItem(i));
        }
        return listData;
    }

    public void testSearchInventoriesOfFriendsByCategorySingleFriend() {
        trinket1.setAccessibility("public");
        trinket1.setCategory("necklace");
        friend1Inventory.add(trinket1);

        trinket2.setAccessibility("public");
        trinket2.setCategory("ring");
        friend1Inventory.add(trinket2);

        trinket3.setAccessibility("public");
        trinket3.setAccessibility("necklace");
        friend1Inventory.add(trinket3);

        borrower.getFriendsList().add(friend1);
        friend1.getFriendsList().add((Friend)borrower);

        //Set an activity monitor for DisplayFriendsActivity
        Instrumentation.ActivityMonitor displayFriendsMonitor = instrumentation.addMonitor(DisplayFriendsActivity.class.getName(), null, false);

        //Start the activity that we set the monitor for
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(instrumentation.getTargetContext(), DisplayFriendsActivity.class.getName());
        instrumentation.startActivitySync(intent);

        //Wait for DisplayFriendsActivity to start
        DisplayFriendsActivity displayFriendsActivity = (DisplayFriendsActivity) getInstrumentation().waitForMonitorWithTimeout(displayFriendsMonitor, 5);
        assertNotNull(displayFriendsActivity);

        //set up monitor for User profile activity that should appear with button click below
        Instrumentation.ActivityMonitor userProfileMonitor = instrumentation.addMonitor(DisplayUserProfileActivity.class.getName(), null, false);

        //Select friend1
        final ListView friendsListView = displayFriendsActivity.getFriendsListView();
        displayFriendsActivity.runOnUiThread(new Runnable() {
            public void run() {
                View v = friendsListView.getChildAt(0);
                friendsListView.performItemClick(v, 0, v.getId());
                //TODO: assert that friend1 was selected
            }
        });

        //Check that UserProfile Page started up
        DisplayUserProfileActivity profileActivity = (DisplayUserProfileActivity) userProfileMonitor.waitForActivityWithTimeout(5);
        assertNotNull("User profile activity for selected friend is Null", profileActivity);
        assertEquals("User profile activity has not been called", 1, userProfileMonitor.getHits());
        assertEquals("Activity of wrong type", DisplayUserProfileActivity.class, profileActivity.getClass());

        //Setup monitor for DisplayInventoryActivity before clicking button
        Instrumentation.ActivityMonitor inventoryMonitor = instrumentation.addMonitor(DisplayInventoryActivity.class.getName(), null, false);

        //Click the 'View Inventory' button
        Button inventoryButton = (Button) displayFriendsActivity.findViewById(R.id.view_inventory_button);
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

        //Set the correct position on the spinner
        final Spinner catsSpinner = (Spinner) displayFriendsActivity.findViewById(R.id.accessibility_spinner);
        SpinnerAdapter categoryData = catsSpinner.getAdapter();

        assertNotNull(categoryData);
        assertEquals(categoryData.getCount(), 10);

        //Set the spinner to first item
        inventoryActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                catsSpinner.requestFocus();
                catsSpinner.setSelection(0);
            }
        });

        this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);

        //Move the spinner down 6 spots to get the 6th item - necklace
        for(int i = 0; i <= 6; i++){
            this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
        }
        Integer categoryPostion = catsSpinner.getSelectedItemPosition();
        String category = (String) catsSpinner.getItemAtPosition(categoryPostion);
        assertEquals("Selected category is not necklace", category, "necklace");

        //Setup monitor for DisplayInventoryActivity restarting before clicking filter button
        instrumentation.removeMonitor(inventoryMonitor);
        Instrumentation.ActivityMonitor refreshedInventoryMonitor = instrumentation.addMonitor(DisplayInventoryActivity.class.getName(), null, false);

        //Click the 'Filter' button
        Button filterButton = (Button) displayedTrinkets.findViewById(R.id.filterButtton);
        assertNotNull(filterButton);
        assertEquals("View not a button", Button.class, filterButton.getClass());
        TouchUtils.clickView(this, filterButton);

        //TODO: Do we have to test if the page refreshes after you select a filter? Not sure how it
        //TODO: works, and therefore how to test it.

        //Assert that DisplayInventoryActivity starts up
        DisplayInventoryActivity refreshedInventoryActivity = (DisplayInventoryActivity) refreshedInventoryMonitor.waitForActivityWithTimeout(5);
        assertNotNull("Inventory Activity is null", refreshedInventoryActivity);
        assertEquals("Inventory activity has not been called", 1, refreshedInventoryMonitor.getHits());
        assertEquals("Activity of wrong type", DisplayInventoryActivity.class, refreshedInventoryActivity.getClass());

        //Check that correct inventory items are displayed
        ListView refreshedInventory = (ListView)  refreshedInventoryActivity.findViewById(R.id.displayedTrinkets);
        ArrayList<Trinket> refreshedTrinkets = this.getDisplayedItems(refreshedInventory);
        assertTrue(refreshedTrinkets.contains(trinket1));
        assertTrue(refreshedTrinkets.contains(trinket3));

        instrumentation.removeMonitor(refreshedInventoryMonitor);
        instrumentation.removeMonitor(displayFriendsMonitor);
        instrumentation.removeMonitor(userProfileMonitor);
    }

    public void testSearchInventoriesOfFriendsByTextualQuery() {

        trinket1.setAccessibility("public");
        trinket1.setDescription("rose gold ring with a garnet");
        friend1Inventory.add(trinket1);

        trinket2.setDescription("rose gold ring with a ruby");
        friend1Inventory.add(trinket2);

        trinket3.setAccessibility("public");
        friend1Inventory.add(trinket3);

        borrower.getFriendsList().add(friend1);
        friend1.getFriendsList().add((Friend)borrower);

        //Set an activity monitor for DisplayFriendsActivity
        Instrumentation.ActivityMonitor displayFriendsMonitor = instrumentation.addMonitor(DisplayFriendsActivity.class.getName(), null, false);

        //Start the activity that we set the monitor for
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(instrumentation.getTargetContext(), DisplayFriendsActivity.class.getName());
        instrumentation.startActivitySync(intent);

        //Wait for DisplayFriendsActivity to start
        DisplayFriendsActivity displayFriendsActivity = (DisplayFriendsActivity) getInstrumentation().waitForMonitorWithTimeout(displayFriendsMonitor, 5);
        assertNotNull(displayFriendsActivity);

        //set up monitor for User profile activity that should appear with button click below
        Instrumentation.ActivityMonitor userProfileMonitor = instrumentation.addMonitor(DisplayUserProfileActivity.class.getName(), null, false);

        //Select friend1
        final ListView friendsListView = displayFriendsActivity.getFriendsListView();
        displayFriendsActivity.runOnUiThread(new Runnable() {
            public void run() {
                View v = friendsListView.getChildAt(0);
                friendsListView.performItemClick(v, 0, v.getId());
                //TODO: assert that friend1 was selected
            }
        });

        //Check that UserProfile Page started up
        DisplayUserProfileActivity profileActivity = (DisplayUserProfileActivity) userProfileMonitor.waitForActivityWithTimeout(5);
        assertNotNull("User profile activity for selected friend is Null", profileActivity);
        assertEquals("User profile activity has not been called", 1, userProfileMonitor.getHits());
        assertEquals("Activity of wrong type", DisplayUserProfileActivity.class, profileActivity.getClass());

        //Setup monitor for DisplayInventoryActivity before clicking button
        Instrumentation.ActivityMonitor inventoryMonitor = instrumentation.addMonitor(DisplayInventoryActivity.class.getName(), null, false);

        //Click the 'View Inventory' button
        Button inventoryButton = (Button) displayFriendsActivity.findViewById(R.id.view_inventory_button);
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

        //Enter Search string into search box
        EditText searchbox = (EditText) inventoryActivity.findViewById(R.id.searchByText);
        searchbox.setText("garnet");

        //Setup monitor for DisplayInventoryActivity restarting before clicking filter button
        instrumentation.removeMonitor(inventoryMonitor);
        Instrumentation.ActivityMonitor refreshedInventoryMonitor = instrumentation.addMonitor(
                DisplayInventoryActivity.class.getName(), null, false);

        //Click the 'Filter' button
        Button filterButton = (Button) displayedTrinkets.findViewById(R.id.filterButtton);
        assertNotNull(filterButton);
        assertEquals("View not a button", Button.class, filterButton.getClass());
        TouchUtils.clickView(this, filterButton);


        //Assert that DisplayInventoryActivity starts up
        DisplayInventoryActivity refreshedInventoryActivity = (DisplayInventoryActivity) refreshedInventoryMonitor.waitForActivityWithTimeout(5);
        assertNotNull("Inventory Activity is null", refreshedInventoryActivity);
        assertEquals("Inventory activity has not been called", 1, refreshedInventoryMonitor.getHits());
        assertEquals("Activity of wrong type", DisplayInventoryActivity.class, refreshedInventoryActivity.getClass());

        //Check that correct inventory items are displayed
        ListView refreshedInventory = (ListView)  refreshedInventoryActivity.findViewById(R.id.displayedTrinkets);
        ArrayList<Trinket> refreshedTrinkets = this.getDisplayedItems(refreshedInventory);
        assertTrue(refreshedTrinkets.contains(trinket1));

        instrumentation.removeMonitor(refreshedInventoryMonitor);
        instrumentation.removeMonitor(displayFriendsMonitor);
        instrumentation.removeMonitor(userProfileMonitor);

        Inventory inventory = Searcher.searchInventoryByDescription(borrower, "garnet");
        Iterator iterator = inventory.iterator();
        while (iterator.hasNext()) {
            assertTrue(iterator.next() == trinket1);
        }
    }

    public void testSharedTrinketsAreSearchableByFriends() {
        Inventory borrowerInventory = new Inventory();


        trinket1.setAccessibility("public");
        trinket2.setAccessibility("private");

        borrowerInventory.add(trinket1);
        borrowerInventory.add(trinket2);

        borrower.getFriendsList().add(friend1);
        friend1.getFriendsList().add((Friend)borrower);

        //TODO: check that we are viewing the borrower's inventory and not someone else's
        //Setup monitor for DisplayInventoryActivity for borrower
        Instrumentation.ActivityMonitor inventoryMonitor = instrumentation.addMonitor(
                DisplayInventoryActivity.class.getName(), null, false);

        //Start the activity that we set the monitor for
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(instrumentation.getTargetContext(), DisplayInventoryActivity.class.getName());
        instrumentation.startActivitySync(intent);

        //Assert that DisplayInventoryActivity starts up
        DisplayInventoryActivity inventoryActivity = (DisplayInventoryActivity) inventoryMonitor.waitForActivityWithTimeout(5);
        assertNotNull("Inventory Activity is null", inventoryActivity);
        assertEquals("Inventory activity has not been called", 1, inventoryMonitor.getHits());
        assertEquals("Activity of wrong type", DisplayInventoryActivity.class, inventoryActivity.getClass());

        //Check that correct inventory items are displayed
        ListView inventory = (ListView) inventoryActivity.findViewById(R.id.displayedTrinkets);
        ArrayList<Trinket> trinkets = this.getDisplayedItems(inventory);
        assertTrue(trinkets.contains(trinket1));
        assertTrue(trinkets.contains(trinket2));

        //Setup monitor for ItemDetailsActivity
        Instrumentation.ActivityMonitor itemDetailsMonitor = instrumentation.addMonitor(
                DisplayItemDetails.class.getName(), null, false);

        //Select an item borrower's inventory
        inventory.performItemClick(inventory.getAdapter().getView(1, null, null), 1,
                                   inventory.getAdapter().getItemId(1));

        //Assert that ItemDetailsActivity starts up
        ItemDetailsActivity itemDetailsActivity = (ItemDetailsActivity) itemDetailsMonitor.waitForActivityWithTimeout(5);
        assertNotNull("Inventory Activity is null", itemDetailsActivity);
        assertEquals("Inventory activity has not been called", 1, itemDetailsMonitor.getHits());
        assertEquals("Activity of wrong type", ItemDetailsActivity.class, itemDetailsActivity.getClass());

        //Setup monitor for AddEditItemActivity
        Instrumentation.ActivityMonitor editItemMonitor = instrumentation.addMonitor(
                AddOrEditItemActivity.class.getName(), null, false);

        //Click the 'Edit' button
        Button editButton = (Button) itemDetailsActivity.findViewById(R.id.edit_button);
        assertNotNull(editButton);
        assertEquals("View not a button", Button.class, editButton.getClass());
        TouchUtils.clickView(this, editButton);

        //Assert that AddOrEditItemActivity starts up
        AddOrEditItemActivity editItemActivity = (AddOrEditItemActivity) editItemMonitor.waitForActivityWithTimeout(5);
        assertNotNull("Inventory Activity is null", editItemActivity);
        assertEquals("Inventory activity has not been called", 1, editItemMonitor.getHits());
        assertEquals("Activity of wrong type", AddOrEditItemActivity.class, editItemActivity.getClass());

        //Set the correct position on the spinner
        final Spinner accessibiltySpinner = (Spinner) editItemActivity.findViewById(R.id.accessibility_spinner);
        SpinnerAdapter accessibilityOptions = accessibiltySpinner.getAdapter();

        assertNotNull(accessibilityOptions);
        assertEquals(accessibilityOptions.getCount(), 2);

        //Set the spinner to first item
        inventoryActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                accessibiltySpinner.requestFocus();
                accessibiltySpinner.setSelection(0);
            }
        });

        this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);

        //Move the spinner down 6 spots to get the 6th item - necklace
        for(int i = 0; i <= 2; i++){
            this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
        }
        Integer index = accessibiltySpinner.getSelectedItemPosition();
        String accessibility = (String) accessibiltySpinner.getItemAtPosition(index);
        assertEquals(accessibility, "public");

        instrumentation.removeMonitor(inventoryMonitor);
        instrumentation.removeMonitor(itemDetailsMonitor);
        instrumentation.removeMonitor(editItemMonitor);

    }
}
