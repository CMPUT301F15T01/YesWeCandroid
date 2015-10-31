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
import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

// Running tests help: Janne Oksanen, http://stackoverflow.com/questions/19516289/exception-in-thread-main-java-lang-noclassdeffounderror-junit-textui-resultpr, 2015-10-31

public class InventoryTests extends ActivityInstrumentationTestCase2 {

    int clickCount;
    ListView list;
    Button addItemButton;
    Button inventoryButton;

    public InventoryTests() {
        super(MainActivity.class);
    }

    // Test method for checking if inventory contains a certain item
    public void testHasItem() {
        Inventory inventory = new Inventory();
        Trinket Trinket = new Trinket();
        assertFalse(inventory.contains(Trinket));
        inventory.add(Trinket);
        assertTrue(inventory.contains(Trinket));
    }

    public void testHasItemUI() {
        HomePageActivity activity = (HomePageActivity) getActivity();

        // Code from : https://developer.android.com/training/activity-testing/activity-functional-testing.html#keyinput, 2015-10-14
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor invActMon =
                getInstrumentation().addMonitor(DisplayInventoryActivity.class.getName(),
                        null, false);

        // Click the button
        inventoryButton = activity.getInventoryButton();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final DisplayInventoryActivity inventoryActivity = (DisplayInventoryActivity)
                invActMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", inventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, invActMon.getHits());
        assertEquals("Activity is of wrong type",
                DisplayInventoryActivity.class, inventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(invActMon);

        list = inventoryActivity.getInventoryItemsList();
        assertNull(list.getChildAt(0));

        // Move to add item activity
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditItemActivity.class.getName(),
                        null, false);

        // Click the button
        addItemButton = inventoryActivity.getAddItemButton();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                addItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditItemActivity nextReceiverActivity = (AddOrEditItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", nextReceiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, nextReceiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        AddOrEditItemActivity addItemtoDisplayInventoryActivity = (AddOrEditItemActivity) getActivity();

        // Click the button
        final EditText editName = addItemtoDisplayInventoryActivity.getItemName();
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editName.setText("test");
            }
        });
        getInstrumentation().waitForIdleSync();

        // Get resources
        Resources resources = addItemtoDisplayInventoryActivity.getResources();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_categories = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_categories)));
        final int ring = spinner_categories.indexOf("Ring");
        final Spinner category = addItemtoDisplayInventoryActivity.getItemCategory();
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                category.setSelection(ring);
            }
        });
        getInstrumentation().waitForIdleSync();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_qualities = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_qualities)));
        final int good = spinner_qualities.indexOf("Good");
        final Spinner quality = addItemtoDisplayInventoryActivity.getItemQuality();
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                quality.setSelection(good);
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the button
        final Button saveItemButton = addItemtoDisplayInventoryActivity.getSaveButton();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        assertNotNull(list.getChildAt(0));
        Iterator<Trinket> trinketIterator = inventoryActivity.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getName(), "test");
        }
    }

    // Test if a user has an inventory
    public void testHasInventory() {
        HomePageActivity activity = (HomePageActivity) getActivity();

        // Code from : https://developer.android.com/training/activity-testing/activity-functional-testing.html#keyinput, 2015-10-14
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor invActMon =
                getInstrumentation().addMonitor(DisplayInventoryActivity.class.getName(),
                        null, false);

        // Click the button
        inventoryButton = activity.getInventoryButton();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final DisplayInventoryActivity inventoryActivity = (DisplayInventoryActivity)
                invActMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", inventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, invActMon.getHits());
        assertEquals("Activity is of wrong type",
                DisplayInventoryActivity.class, inventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(invActMon);

        assertNotNull(inventoryActivity.getInventory());
    }

    // Test method for getting the number of items in an inventory
    public void testNumberOfItemsInInventory() {
        Inventory inventory = new Inventory();
        Trinket Trinket1 = new Trinket();
        inventory.add(Trinket1);
        Trinket Trinket2 = new Trinket();
        inventory.add(Trinket2);
        Trinket Trinket3 = new Trinket();
        inventory.add(Trinket3);
        assertTrue(inventory.size() == 3);

        inventory.remove(Trinket1);
        inventory.remove(Trinket2);
        assertTrue(inventory.size() == 1);
    }

    public void testNumberOfItemsInInventoryUI() {
        HomePageActivity activity = (HomePageActivity) getActivity();

        // Code from : https://developer.android.com/training/activity-testing/activity-functional-testing.html#keyinput, 2015-10-14
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor invActMon =
                getInstrumentation().addMonitor(DisplayInventoryActivity.class.getName(),
                        null, false);

        // Click the button
        inventoryButton = activity.getInventoryButton();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final DisplayInventoryActivity inventoryActivity = (DisplayInventoryActivity)
                invActMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", inventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, invActMon.getHits());
        assertEquals("Activity is of wrong type",
                DisplayInventoryActivity.class, inventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(invActMon);

        assertTrue(inventoryActivity.getInventory().size() == 0);

        // Move to add item activity
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditItemActivity.class.getName(),
                        null, false);

        // Click the button
        addItemButton = inventoryActivity.getAddItemButton();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                addItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditItemActivity nextReceiverActivity = (AddOrEditItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", nextReceiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, nextReceiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        AddOrEditItemActivity addItemtoDisplayInventoryActivity = (AddOrEditItemActivity) getActivity();

        // Click the button
        final EditText editName = addItemtoDisplayInventoryActivity.getItemName();
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editName.setText("test");
            }
        });
        getInstrumentation().waitForIdleSync();

        // Get resources
        Resources resources = addItemtoDisplayInventoryActivity.getResources();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_categories = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_categories)));
        final int ring = spinner_categories.indexOf("Ring");
        final Spinner category = addItemtoDisplayInventoryActivity.getItemCategory();
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                category.setSelection(ring);
            }
        });
        getInstrumentation().waitForIdleSync();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_qualities = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_qualities)));
        final int good = spinner_qualities.indexOf("Good");
        final Spinner quality = addItemtoDisplayInventoryActivity.getItemQuality();
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                quality.setSelection(good);
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the button
        final Button saveItemButton = addItemtoDisplayInventoryActivity.getSaveButton();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        assertTrue(inventoryActivity.getInventory().size() == 1);
    }

    // Test method to test if an inventory is empty
    public void testIsInventoryEmpty() {
        HomePageActivity activity = (HomePageActivity) getActivity();

        // Code from : https://developer.android.com/training/activity-testing/activity-functional-testing.html#keyinput, 2015-10-14
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor invActMon =
                getInstrumentation().addMonitor(DisplayInventoryActivity.class.getName(),
                        null, false);

        // Click the button
        inventoryButton = activity.getInventoryButton();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final DisplayInventoryActivity inventoryActivity = (DisplayInventoryActivity)
                invActMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", inventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, invActMon.getHits());
        assertEquals("Activity is of wrong type",
                DisplayInventoryActivity.class, inventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(invActMon);

        assertTrue(inventoryActivity.getInventory().isEmpty());

        // Move to add item activity
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditItemActivity.class.getName(),
                        null, false);

        // Click the button
        addItemButton = inventoryActivity.getAddItemButton();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                addItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditItemActivity nextReceiverActivity = (AddOrEditItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", nextReceiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, nextReceiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        AddOrEditItemActivity addItemtoDisplayInventoryActivity = (AddOrEditItemActivity) getActivity();

        // Click the button
        final EditText editName = addItemtoDisplayInventoryActivity.getItemName();
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editName.setText("test");
            }
        });
        getInstrumentation().waitForIdleSync();

        // Get resources
        Resources resources = addItemtoDisplayInventoryActivity.getResources();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_categories = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_categories)));
        final int ring = spinner_categories.indexOf("Ring");
        final Spinner category = addItemtoDisplayInventoryActivity.getItemCategory();
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                category.setSelection(ring);
            }
        });
        getInstrumentation().waitForIdleSync();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_qualities = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_qualities)));
        final int good = spinner_qualities.indexOf("Good");
        final Spinner quality = addItemtoDisplayInventoryActivity.getItemQuality();
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                quality.setSelection(good);
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the button
        final Button saveItemButton = addItemtoDisplayInventoryActivity.getSaveButton();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        assertFalse(inventoryActivity.getInventory().isEmpty());
    }

    // Test method for adding an item to your inventory
    public void testAddItem() {
        Inventory inventory = new Inventory();
        Trinket Trinket = new Trinket();
        inventory.add(Trinket);
        assertTrue(inventory.contains(Trinket));
    }

    // Test method for removing an item from your inventory
    public void testRemoveItem() {
        Inventory inventory = new Inventory();
        Trinket Trinket = new Trinket();
        inventory.add(Trinket);
        assertTrue(inventory.contains(Trinket));
        inventory.remove(Trinket);
        assertFalse(inventory.contains(Trinket));
    }

    // Test method for changing the share settings of an item
    public void testShareSettings() {
        Trinket Trinket = new Trinket();
        // Default share value is "public"
        assertTrue(Trinket.getAccessibility().equals("public"));
        // Change the item's share status to private
        Trinket.setAccessibility("private");
        assertTrue(Trinket.getAccessibility().equals("private"));
    }

    /* Test methods to edit an item's details */
    public void testChangeItemName() {
        Trinket Trinket = new Trinket();
        Trinket.setName("Test Name");
        assertTrue(Trinket.getName().equals("Test Name"));
        Trinket.setName("New Test Name");
        assertTrue(Trinket.getName().equals("New Test Name"));
    }
    public void testChangeItemQuantity() {
        Trinket Trinket = new Trinket();
        Trinket.setQuantity(5);
        assertTrue(Trinket.getQuantity().equals(5));
        Trinket.setQuantity(10);
        assertTrue(Trinket.getQuantity().equals(10));
    }
    public void testChangeItemQuality() {
        Trinket Trinket = new Trinket();
        Trinket.setQuality("poor");
        assertTrue(Trinket.getQuality().equals("poor"));
        Trinket.setQuality("good");
        assertTrue(Trinket.getQuality().equals("good"));
    }
    public void testChangeItemDescription() {
        Trinket Trinket = new Trinket();
        Trinket.setDescription("Test description.");
        assertTrue(Trinket.getDescription().equals("Test description."));
        Trinket.setDescription("New test description.");
        assertTrue(Trinket.getDescription().equals("New test description."));
    }
    // Test for changing item's category
    public void testChangeItemCategory() {
        Trinket Trinket = new Trinket();
        List<String> categories = new Inventory().getCategoriesList();
        assertTrue(categories.size() == 10);
        Trinket.setCategory("Bracelets");
        assertTrue(Trinket.getCategory().equals("Bracelets"));
        Trinket.setCategory("Rings");
        assertTrue(Trinket.getCategory().equals("Rings"));
    }

    // Test method for removing multiple items at once
    public void testBatchItemRemoval() {
        Inventory inventory = new Inventory();
        Trinket Trinket1 = new Trinket();
        inventory.add(Trinket1);
        Trinket Trinket2 = new Trinket();
        inventory.add(Trinket2);
        Trinket Trinket3 = new Trinket();
        inventory.add(Trinket3);
        Trinket Trinket4 = new Trinket();
        inventory.add(Trinket4);
        assertTrue(inventory.contains(Trinket1));
        assertTrue(inventory.contains(Trinket2));
        assertTrue(inventory.contains(Trinket3));
        assertTrue(inventory.contains(Trinket4));

        Collection<Trinket> items = new ArrayList<Trinket>();
        items.add(Trinket1);
        items.add(Trinket3);
        items.add(Trinket4);

        // Remove items 1, 3, and 4 from inventory
        inventory.removeAll(items);
        assertFalse(inventory.contains(Trinket1));
        assertTrue(inventory.contains(Trinket2));
        assertFalse(inventory.contains(Trinket3));
        assertFalse(inventory.contains(Trinket4));

    }

    // Test method for checking how quickly an item can be added to the inventory
    public void testQuickAdd() {
        clickCount = 0;
        HomePageActivity activity = (HomePageActivity) getActivity();

        // Code from : https://developer.android.com/training/activity-testing/activity-functional-testing.html#keyinput, 2015-10-14
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(DisplayInventoryActivity.class.getName(),
                        null, false);

        // Click the button
        inventoryButton = activity.getInventoryButton();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryButton.performClick();
                clickCount += 1;
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final DisplayInventoryActivity inventoryActivity = (DisplayInventoryActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", inventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                DisplayInventoryActivity.class, inventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        // Move to add item activity
        receiverActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditItemActivity.class.getName(),
                        null, false);

        // Click the button
        addItemButton = inventoryActivity.getAddItemButton();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                addItemButton.performClick();
                clickCount += 1;
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditItemActivity nextReceiverActivity = (AddOrEditItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", nextReceiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, nextReceiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        AddOrEditItemActivity addItemtoDisplayInventoryActivity = (AddOrEditItemActivity) getActivity();

        // Click the button
        final EditText editName = addItemtoDisplayInventoryActivity.getItemName();
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editName.setText("test");
            }
        });
        getInstrumentation().waitForIdleSync();

        // Get resources
        Resources resources = addItemtoDisplayInventoryActivity.getResources();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_categories = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_categories)));
        final int ring = spinner_categories.indexOf("Ring");
        final Spinner category = addItemtoDisplayInventoryActivity.getItemCategory();
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                category.setSelection(ring);
                clickCount += 1;
            }
        });
        getInstrumentation().waitForIdleSync();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_qualities = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_qualities)));
        final int good = spinner_qualities.indexOf("Good");
        final Spinner quality = addItemtoDisplayInventoryActivity.getItemQuality();
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                quality.setSelection(good);
                clickCount += 1;
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the button
        final Button saveItemButton = addItemtoDisplayInventoryActivity.getSaveButton();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
                clickCount += 1;
            }
        });
        getInstrumentation().waitForIdleSync();

        Iterator<Trinket> trinketIterator = inventoryActivity.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getName(), "test");
        }
        // 5 or less clicks
        assertTrue(clickCount <= 5);
    }
}
