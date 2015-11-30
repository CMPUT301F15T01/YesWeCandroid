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

import android.app.AlertDialog;
import android.app.Instrumentation;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import ca.ualberta.trinkettrader.Inventory.InventoryActivity;
import ca.ualberta.trinkettrader.Inventory.Trinket.AddOrEditTrinketActivity;
import ca.ualberta.trinkettrader.Inventory.Trinket.Trinket;
import ca.ualberta.trinkettrader.Inventory.Trinket.TrinketDetailsActivity;
import ca.ualberta.trinkettrader.User.LoggedInUser;

public class InventoryTests extends ActivityInstrumentationTestCase2 {

    // Janne Oksanen; http://stackoverflow.com/questions/19516289/exception-in-thread-main-java-lang-noclassdeffounderror-junit-textui-resultpr; 2015-10-31

    public ListView list;
    public Button addItemButton;
    int clickCount;

    public InventoryTests() {
        super(LoginActivity.class);
    }

    public void testHasItem() {
        // Get the current activity
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** HomePageActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor homePageActivityMonitor =
                getInstrumentation().addMonitor(HomePageActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final String test_email = loginActivity.getResources().getString(R.string.test_email);
        final AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                emailTextView.setText(test_email);
            }
        });
        final Button homePageButton = loginActivity.getLoginButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                homePageButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        HomePageActivity homePageActivity = (HomePageActivity)
                homePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", homePageActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                HomePageActivity.class, homePageActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(homePageActivityMonitor);

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
            LoggedInUser.getInstance().saveToNetwork();
        } catch (IOException e) {

        }

        /******** InventoryActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor displayInventoryActivityMonitor =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final Button inventoryButton = homePageActivity.getInventoryButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        InventoryActivity displayInventoryActivity = (InventoryActivity)
                displayInventoryActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", displayInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, displayInventoryActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, displayInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(displayInventoryActivityMonitor);

        list = displayInventoryActivity.getInventoryItemsList();
        assertNull(list.getChildAt(0));

        // Move to add item activity
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditTrinketActivity.class.getName(),
                        null, false);

        // Click the button
        addItemButton = displayInventoryActivity.getAddItemButton();
        displayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                addItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditTrinketActivity addItemtoInventoryActivity = (AddOrEditTrinketActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addItemtoInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditTrinketActivity.class, addItemtoInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        Instrumentation.ActivityMonitor displayInventoryActivityMonitorAgain =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        // Click the button
        final EditText editName = addItemtoInventoryActivity.getTrinketName();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editName.setText("test");
            }
        });
        getInstrumentation().waitForIdleSync();

        // Get resources
        Resources resources = addItemtoInventoryActivity.getResources();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_categories = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_categories)));
        final int ring = spinner_categories.indexOf("Ring");
        final Spinner category = addItemtoInventoryActivity.getTrinketCategory();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                category.setSelection(ring);
            }
        });
        getInstrumentation().waitForIdleSync();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_qualities = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_qualities)));
        final int good = spinner_qualities.indexOf("Good");
        final Spinner quality = addItemtoInventoryActivity.getTrinketQuality();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                quality.setSelection(good);
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the button
        final Button saveItemButton = addItemtoInventoryActivity.getSaveButton();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        InventoryActivity displayInventoryActivityAgain = (InventoryActivity)
                displayInventoryActivityMonitorAgain.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", displayInventoryActivityAgain);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, displayInventoryActivityMonitorAgain.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, displayInventoryActivityAgain.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(displayInventoryActivityMonitorAgain);

        list = displayInventoryActivityAgain.getInventoryItemsList();
        assertNotNull(list.getChildAt(0));
        Iterator<Trinket> trinketIterator = displayInventoryActivityAgain.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getName(), "test");
        }

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
        } catch (IOException e) {

        }
        loginActivity.finish();
        addItemtoInventoryActivity.finish();
        displayInventoryActivityAgain.finish();
        homePageActivity.finish();
        displayInventoryActivity.getInventory().clear();
        displayInventoryActivity.finish();

    }

    // Test if a user has an inventory
    // @UiThreadTest
    public void testHasInventory() {
        // Get the current activity
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** HomePageActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor homePageActivityMonitor =
                getInstrumentation().addMonitor(HomePageActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final String test_email = loginActivity.getResources().getString(R.string.test_email);
        final AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                emailTextView.setText(test_email);
            }
        });
        final Button homePageButton = loginActivity.getLoginButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                homePageButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        HomePageActivity homePageActivity = (HomePageActivity)
                homePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", homePageActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                HomePageActivity.class, homePageActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(homePageActivityMonitor);

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
            LoggedInUser.getInstance().saveToNetwork();
        } catch (IOException e) {

        }

        /******** InventoryActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor invActMon =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final Button inventoryButton = homePageActivity.getInventoryButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final InventoryActivity inventoryActivity = (InventoryActivity)
                invActMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", inventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, invActMon.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, inventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(invActMon);

        assertNotNull(inventoryActivity.getInventory());

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
        } catch (IOException e) {

        }

        loginActivity.finish();
        homePageActivity.finish();
        inventoryActivity.getInventory().clear();
        inventoryActivity.finish();
    }

    // Test method for getting the number of items in an inventory
    public void testNumberOfItemsInInventory() {
        // Get the current activity
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** HomePageActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor homePageActivityMonitor =
                getInstrumentation().addMonitor(HomePageActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final String test_email = loginActivity.getResources().getString(R.string.test_email);
        final AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                emailTextView.setText(test_email);
            }
        });
        final Button homePageButton = loginActivity.getLoginButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                homePageButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        HomePageActivity homePageActivity = (HomePageActivity)
                homePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", homePageActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                HomePageActivity.class, homePageActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(homePageActivityMonitor);

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
            LoggedInUser.getInstance().saveToNetwork();
        } catch (IOException e) {

        }

        /******** InventoryActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor invActMon =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final Button inventoryButton = homePageActivity.getInventoryButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final InventoryActivity inventoryActivity = (InventoryActivity)
                invActMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", inventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, invActMon.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, inventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(invActMon);

        assertTrue(inventoryActivity.getInventory().size() == 0);

        // Move to add item activity
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditTrinketActivity.class.getName(),
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
        AddOrEditTrinketActivity addItemtoInventoryActivity = (AddOrEditTrinketActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addItemtoInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditTrinketActivity.class, addItemtoInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        // Move to add item activity
        Instrumentation.ActivityMonitor displayInventoryActivityMonitorAgain =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        // Click the button
        final EditText editName = addItemtoInventoryActivity.getTrinketName();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editName.setText("test");
            }
        });
        getInstrumentation().waitForIdleSync();

        // Get resources
        Resources resources = addItemtoInventoryActivity.getResources();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_categories = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_categories)));
        final int ring = spinner_categories.indexOf("Ring");
        final Spinner category = addItemtoInventoryActivity.getTrinketCategory();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                category.setSelection(ring);
            }
        });
        getInstrumentation().waitForIdleSync();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_qualities = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_qualities)));
        final int good = spinner_qualities.indexOf("Good");
        final Spinner quality = addItemtoInventoryActivity.getTrinketQuality();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                quality.setSelection(good);
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the button
        final Button saveItemButton = addItemtoInventoryActivity.getSaveButton();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        InventoryActivity displayInventoryActivityAgain = (InventoryActivity)
                displayInventoryActivityMonitorAgain.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", displayInventoryActivityAgain);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, displayInventoryActivityMonitorAgain.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, displayInventoryActivityAgain.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(displayInventoryActivityMonitorAgain);

        list = displayInventoryActivityAgain.getInventoryItemsList();
        assertNotNull(list.getChildAt(0));
        Iterator<Trinket> trinketIterator = displayInventoryActivityAgain.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getName(), "test");
        }

        assertTrue(inventoryActivity.getInventory().size() == 1);

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
        } catch (IOException e) {

        }

        loginActivity.finish();
        addItemtoInventoryActivity.finish();
        displayInventoryActivityAgain.finish();
        homePageActivity.finish();
        inventoryActivity.finish();
        inventoryActivity.getInventory().clear();
    }

    // Test method to test if an inventory is empty
    public void testIsInventoryEmpty() {
        // Get the current activity
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** HomePageActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor homePageActivityMonitor =
                getInstrumentation().addMonitor(HomePageActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final String test_email = loginActivity.getResources().getString(R.string.test_email);
        final AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                emailTextView.setText(test_email);
            }
        });
        final Button homePageButton = loginActivity.getLoginButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                homePageButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        HomePageActivity homePageActivity = (HomePageActivity)
                homePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", homePageActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                HomePageActivity.class, homePageActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(homePageActivityMonitor);

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
            LoggedInUser.getInstance().saveToNetwork();
        } catch (IOException e) {

        }

        /******** InventoryActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor invActMon =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final Button inventoryButton = homePageActivity.getInventoryButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final InventoryActivity inventoryActivity = (InventoryActivity)
                invActMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", inventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, invActMon.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, inventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(invActMon);

        assertTrue(inventoryActivity.getInventory().isEmpty());

        // Move to add item activity
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditTrinketActivity.class.getName(),
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
        AddOrEditTrinketActivity addItemtoInventoryActivity = (AddOrEditTrinketActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addItemtoInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditTrinketActivity.class, addItemtoInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        Instrumentation.ActivityMonitor displayInventoryActivityMonitorAgain =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        // Click the button
        final EditText editName = addItemtoInventoryActivity.getTrinketName();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editName.setText("test");
            }
        });
        getInstrumentation().waitForIdleSync();

        // Get resources
        Resources resources = addItemtoInventoryActivity.getResources();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_categories = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_categories)));
        final int ring = spinner_categories.indexOf("Ring");
        final Spinner category = addItemtoInventoryActivity.getTrinketCategory();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                category.setSelection(ring);
            }
        });
        getInstrumentation().waitForIdleSync();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_qualities = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_qualities)));
        final int good = spinner_qualities.indexOf("Good");
        final Spinner quality = addItemtoInventoryActivity.getTrinketQuality();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                quality.setSelection(good);
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the button
        final Button saveItemButton = addItemtoInventoryActivity.getSaveButton();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        InventoryActivity displayInventoryActivityAgain = (InventoryActivity)
                displayInventoryActivityMonitorAgain.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", displayInventoryActivityAgain);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, displayInventoryActivityMonitorAgain.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, displayInventoryActivityAgain.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(displayInventoryActivityMonitorAgain);

        assertFalse(displayInventoryActivityAgain.getInventory().isEmpty());

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
        } catch (IOException e) {

        }

        loginActivity.finish();
        addItemtoInventoryActivity.finish();
        homePageActivity.finish();
        inventoryActivity.getInventory().clear();
        inventoryActivity.finish();
        displayInventoryActivityAgain.finish();
    }

    // Test method for adding an item to your inventory
    public void testAddItem() {
        // Get the current activity
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** HomePageActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor homePageActivityMonitor =
                getInstrumentation().addMonitor(HomePageActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final String test_email = loginActivity.getResources().getString(R.string.test_email);
        final AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                emailTextView.setText(test_email);
            }
        });
        final Button homePageButton = loginActivity.getLoginButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                homePageButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        HomePageActivity homePageActivity = (HomePageActivity)
                homePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", homePageActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                HomePageActivity.class, homePageActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(homePageActivityMonitor);

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
            LoggedInUser.getInstance().saveToNetwork();
        } catch (IOException e) {

        }

        /******** InventoryActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor invActMon =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final Button inventoryButton = homePageActivity.getInventoryButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final InventoryActivity inventoryActivity = (InventoryActivity)
                invActMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", inventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, invActMon.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, inventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(invActMon);

        assertTrue(inventoryActivity.getInventory().isEmpty());

        // Move to add item activity
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditTrinketActivity.class.getName(),
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
        AddOrEditTrinketActivity addItemtoInventoryActivity = (AddOrEditTrinketActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addItemtoInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditTrinketActivity.class, addItemtoInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        // Move to add item activity
        Instrumentation.ActivityMonitor displayInventoryActivityMonitorAgain =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        // Click the button
        final EditText editName = addItemtoInventoryActivity.getTrinketName();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editName.setText("test");
            }
        });
        getInstrumentation().waitForIdleSync();

        // Get resources
        Resources resources = addItemtoInventoryActivity.getResources();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_categories = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_categories)));
        final int ring = spinner_categories.indexOf("Ring");
        final Spinner category = addItemtoInventoryActivity.getTrinketCategory();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                category.setSelection(ring);
            }
        });
        getInstrumentation().waitForIdleSync();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_qualities = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_qualities)));
        final int good = spinner_qualities.indexOf("Good");
        final Spinner quality = addItemtoInventoryActivity.getTrinketQuality();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                quality.setSelection(good);
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the button
        final Button saveItemButton = addItemtoInventoryActivity.getSaveButton();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        InventoryActivity displayInventoryActivityAgain = (InventoryActivity)
                displayInventoryActivityMonitorAgain.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", displayInventoryActivityAgain);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, displayInventoryActivityMonitorAgain.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, displayInventoryActivityAgain.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(displayInventoryActivityMonitorAgain);

        list = displayInventoryActivityAgain.getInventoryItemsList();
        assertNotNull(list.getChildAt(0));
        Iterator<Trinket> trinketIterator = inventoryActivity.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getName(), "test");
        }

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
        } catch (IOException e) {

        }

        loginActivity.finish();
        addItemtoInventoryActivity.finish();
        displayInventoryActivityAgain.finish();
        homePageActivity.finish();
        inventoryActivity.getInventory().clear();
        inventoryActivity.finish();
    }

    // Test method for removing an item from your inventory
    public void testRemoveItem() {
        // Get the current activity
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** HomePageActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor homePageActivityMonitor =
                getInstrumentation().addMonitor(HomePageActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final String test_email = loginActivity.getResources().getString(R.string.test_email);
        final AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                emailTextView.setText(test_email);
            }
        });
        final Button homePageButton = loginActivity.getLoginButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                homePageButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        HomePageActivity homePageActivity = (HomePageActivity)
                homePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", homePageActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                HomePageActivity.class, homePageActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(homePageActivityMonitor);

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
            LoggedInUser.getInstance().saveToNetwork();
        } catch (IOException e) {

        }

        /******** InventoryActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor invActMon =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final Button inventoryButton = homePageActivity.getInventoryButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final InventoryActivity inventoryActivity = (InventoryActivity)
                invActMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", inventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, invActMon.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, inventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(invActMon);

        assertTrue(inventoryActivity.getInventory().size() == 0);

        // Move to add item activity
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditTrinketActivity.class.getName(),
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
        AddOrEditTrinketActivity addItemtoInventoryActivity = (AddOrEditTrinketActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addItemtoInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditTrinketActivity.class, addItemtoInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        // Move to add item activity
        Instrumentation.ActivityMonitor displayInventoryActivityMonitorAgain =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        // Click the button
        final EditText editName = addItemtoInventoryActivity.getTrinketName();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editName.setText("test");
            }
        });
        getInstrumentation().waitForIdleSync();

        // Get resources
        Resources resources = addItemtoInventoryActivity.getResources();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_categories = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_categories)));
        final int ring = spinner_categories.indexOf("Ring");
        final Spinner category = addItemtoInventoryActivity.getTrinketCategory();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                category.setSelection(ring);
            }
        });
        getInstrumentation().waitForIdleSync();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_qualities = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_qualities)));
        final int good = spinner_qualities.indexOf("Good");
        final Spinner quality = addItemtoInventoryActivity.getTrinketQuality();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                quality.setSelection(good);
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the button
        final Button saveItemButton = addItemtoInventoryActivity.getSaveButton();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        InventoryActivity displayInventoryActivityAgain = (InventoryActivity)
                displayInventoryActivityMonitorAgain.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", displayInventoryActivityAgain);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, displayInventoryActivityMonitorAgain.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, displayInventoryActivityAgain.getClass());

//        // Remove the ActivityMonitor
//        getInstrumentation().removeMonitor(displayInventoryActivityMonitorAgain);

        list = displayInventoryActivityAgain.getInventoryItemsList();
        assertNotNull(list.getChildAt(0));
        Iterator<Trinket> trinketIterator = displayInventoryActivityAgain.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getName(), "test");
        }

        // Move to add item activity
        Instrumentation.ActivityMonitor itemDetailsMonitor =
                getInstrumentation().addMonitor(TrinketDetailsActivity.class.getName(),
                        null, false);

        list = displayInventoryActivityAgain.getInventoryItemsList();
        displayInventoryActivityAgain.runOnUiThread(new Runnable() {
            public void run() {
                View firstItem = list.getChildAt(0);
                list.performItemClick(firstItem, 0, firstItem.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        TrinketDetailsActivity itemDetsAct = (TrinketDetailsActivity)
                itemDetailsMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", itemDetsAct);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, itemDetailsMonitor.getHits());
        assertEquals("Activity is of wrong type",
                TrinketDetailsActivity.class, itemDetsAct.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(itemDetailsMonitor);

        final Button deleteButton = itemDetsAct.getDeleteButton();
        itemDetsAct.runOnUiThread(new Runnable() {
            public void run() {
                deleteButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        final AlertDialog alert = itemDetsAct.getDialog();
        itemDetsAct.runOnUiThread(new Runnable() {
            public void run() {
                alert.getButton(DialogInterface.BUTTON_POSITIVE).performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        displayInventoryActivityAgain = (InventoryActivity)
                displayInventoryActivityMonitorAgain.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", displayInventoryActivityAgain);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, displayInventoryActivityMonitorAgain.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, displayInventoryActivityAgain.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(displayInventoryActivityMonitorAgain);

        list = displayInventoryActivityAgain.getInventoryItemsList();
        assertNull(list.getChildAt(0));

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
        } catch (IOException e) {

        }

        loginActivity.finish();
        addItemtoInventoryActivity.finish();
        displayInventoryActivityAgain.finish();
        homePageActivity.finish();
        inventoryActivity.finish();
        inventoryActivity.getInventory().clear();
        itemDetsAct.finish();
    }

    // Test method for changing the share settings of an item
    public void testShareSettings() {
        // Get the current activity
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** HomePageActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor homePageActivityMonitor =
                getInstrumentation().addMonitor(HomePageActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final String test_email = loginActivity.getResources().getString(R.string.test_email);
        final AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                emailTextView.setText(test_email);
            }
        });
        final Button homePageButton = loginActivity.getLoginButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                homePageButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        HomePageActivity homePageActivity = (HomePageActivity)
                homePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", homePageActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                HomePageActivity.class, homePageActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(homePageActivityMonitor);

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
            LoggedInUser.getInstance().saveToNetwork();
        } catch (IOException e) {

        }

        /******** InventoryActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor invActMon =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final Button inventoryButton = homePageActivity.getInventoryButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final InventoryActivity inventoryActivity = (InventoryActivity)
                invActMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", inventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, invActMon.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, inventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(invActMon);

        assertTrue(inventoryActivity.getInventory().size() == 0);

        // Move to add item activity
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditTrinketActivity.class.getName(),
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
        AddOrEditTrinketActivity addItemtoInventoryActivity = (AddOrEditTrinketActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addItemtoInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditTrinketActivity.class, addItemtoInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        // Move to add item activity
        Instrumentation.ActivityMonitor displayInventoryActivityMonitorAgain =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        // Click the button
        final EditText editName = addItemtoInventoryActivity.getTrinketName();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editName.setText("test");
            }
        });
        getInstrumentation().waitForIdleSync();

        // Get resources
        Resources resources = addItemtoInventoryActivity.getResources();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_categories = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_categories)));
        final int ring = spinner_categories.indexOf("Ring");
        final Spinner category = addItemtoInventoryActivity.getTrinketCategory();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                category.setSelection(ring);
            }
        });
        getInstrumentation().waitForIdleSync();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_qualities = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_qualities)));
        final int good = spinner_qualities.indexOf("Good");
        final Spinner quality = addItemtoInventoryActivity.getTrinketQuality();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                quality.setSelection(good);
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the button
        final Button saveItemButton = addItemtoInventoryActivity.getSaveButton();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        InventoryActivity displayInventoryActivityAgain = (InventoryActivity)
                displayInventoryActivityMonitorAgain.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", displayInventoryActivityAgain);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, displayInventoryActivityMonitorAgain.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, displayInventoryActivityAgain.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(displayInventoryActivityMonitorAgain);

        list = displayInventoryActivityAgain.getInventoryItemsList();
        assertNotNull(list.getChildAt(0));
        Iterator<Trinket> trinketIterator = displayInventoryActivityAgain.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getTrinketAccessibility(), "public");
        }

        // Move to add item activity
        Instrumentation.ActivityMonitor itemDetailsMonitor =
                getInstrumentation().addMonitor(TrinketDetailsActivity.class.getName(),
                        null, false);

        list = displayInventoryActivityAgain.getInventoryItemsList();
        displayInventoryActivityAgain.runOnUiThread(new Runnable() {
            public void run() {
                View firstItem = list.getChildAt(0);
                list.performItemClick(firstItem, 0, firstItem.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        TrinketDetailsActivity itemDetsAct = (TrinketDetailsActivity)
                itemDetailsMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", itemDetsAct);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, itemDetailsMonitor.getHits());
        assertEquals("Activity is of wrong type",
                TrinketDetailsActivity.class, itemDetsAct.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(itemDetailsMonitor);

        // Validate that ReceiverActivity is started
        Instrumentation.ActivityMonitor editItemMon =
                getInstrumentation().addMonitor(AddOrEditTrinketActivity.class.getName(),
                        null, false);

        final Button editButton = itemDetsAct.getEditButton();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditTrinketActivity editItemAct = (AddOrEditTrinketActivity)
                editItemMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", editItemAct);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, editItemMon.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditTrinketActivity.class, editItemAct.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(editItemMon);

        Instrumentation.ActivityMonitor itemDetsMon =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(), null, false);

        final CheckBox accessibility = editItemAct.getTrinketAccessibility();
        editItemAct.runOnUiThread(new Runnable() {
            public void run() {
                accessibility.setChecked(false);
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the button
        final Button saveEditButton = editItemAct.getSaveButton();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveEditButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        InventoryActivity editedInvAct = (InventoryActivity)
                itemDetsMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", editedInvAct);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, itemDetsMon.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, editedInvAct.getClass());

        getInstrumentation().removeMonitor(itemDetsMon);

        trinketIterator = editedInvAct.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getTrinketAccessibility(), "private");
        }

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
        } catch (IOException e) {

        }

        loginActivity.finish();
        addItemtoInventoryActivity.finish();
        displayInventoryActivityAgain.finish();
        homePageActivity.finish();
        inventoryActivity.finish();
        inventoryActivity.getInventory().clear();
        editedInvAct.finish();
        editItemAct.finish();
        itemDetsAct.finish();
    }

    /* Test methods to edit an item's details */
    public void testChangeItemName() {
        // Get the current activity
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** HomePageActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor homePageActivityMonitor =
                getInstrumentation().addMonitor(HomePageActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final String test_email = loginActivity.getResources().getString(R.string.test_email);
        final AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                emailTextView.setText(test_email);
            }
        });
        final Button homePageButton = loginActivity.getLoginButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                homePageButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        HomePageActivity homePageActivity = (HomePageActivity)
                homePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", homePageActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                HomePageActivity.class, homePageActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(homePageActivityMonitor);

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
            LoggedInUser.getInstance().saveToNetwork();
        } catch (IOException e) {

        }

        /******** InventoryActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor invActMon =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final Button inventoryButton = homePageActivity.getInventoryButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final InventoryActivity inventoryActivity = (InventoryActivity)
                invActMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", inventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, invActMon.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, inventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(invActMon);

        assertTrue(inventoryActivity.getInventory().size() == 0);

        // Move to add item activity
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditTrinketActivity.class.getName(),
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
        AddOrEditTrinketActivity addItemtoInventoryActivity = (AddOrEditTrinketActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addItemtoInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditTrinketActivity.class, addItemtoInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        // Move to add item activity
        Instrumentation.ActivityMonitor displayInventoryActivityMonitorAgain =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        // Click the button
        final EditText editName = addItemtoInventoryActivity.getTrinketName();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editName.setText("test");
            }
        });
        getInstrumentation().waitForIdleSync();

        // Get resources
        Resources resources = addItemtoInventoryActivity.getResources();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_categories = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_categories)));
        final int ring = spinner_categories.indexOf("Ring");
        final Spinner category = addItemtoInventoryActivity.getTrinketCategory();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                category.setSelection(ring);
            }
        });
        getInstrumentation().waitForIdleSync();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_qualities = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_qualities)));
        final int good = spinner_qualities.indexOf("Good");
        final Spinner quality = addItemtoInventoryActivity.getTrinketQuality();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                quality.setSelection(good);
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the button
        final Button saveItemButton = addItemtoInventoryActivity.getSaveButton();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        InventoryActivity displayInventoryActivityAgain = (InventoryActivity)
                displayInventoryActivityMonitorAgain.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", displayInventoryActivityAgain);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, displayInventoryActivityMonitorAgain.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, displayInventoryActivityAgain.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(displayInventoryActivityMonitorAgain);

        list = displayInventoryActivityAgain.getInventoryItemsList();
        assertNotNull(list.getChildAt(0));
        Iterator<Trinket> trinketIterator = displayInventoryActivityAgain.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getName(), "test");
        }

        // Move to add item activity
        Instrumentation.ActivityMonitor itemDetailsMonitor =
                getInstrumentation().addMonitor(TrinketDetailsActivity.class.getName(),
                        null, false);

        list = displayInventoryActivityAgain.getInventoryItemsList();
        displayInventoryActivityAgain.runOnUiThread(new Runnable() {
            public void run() {
                View firstItem = list.getChildAt(0);
                list.performItemClick(firstItem, 0, firstItem.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        TrinketDetailsActivity itemDetsAct = (TrinketDetailsActivity)
                itemDetailsMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", itemDetsAct);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, itemDetailsMonitor.getHits());
        assertEquals("Activity is of wrong type",
                TrinketDetailsActivity.class, itemDetsAct.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(itemDetailsMonitor);

        // Validate that ReceiverActivity is started
        Instrumentation.ActivityMonitor editItemMon =
                getInstrumentation().addMonitor(AddOrEditTrinketActivity.class.getName(),
                        null, false);

        final Button editButton = itemDetsAct.getEditButton();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditTrinketActivity editItemAct = (AddOrEditTrinketActivity)
                editItemMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", editItemAct);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, editItemMon.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditTrinketActivity.class, editItemAct.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(editItemMon);

        Instrumentation.ActivityMonitor itemDetsMon =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(), null, false);

        final EditText name = editItemAct.getTrinketName();
        editItemAct.runOnUiThread(new Runnable() {
            public void run() {
                name.setText("Test2");
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the button
        final Button saveEditButton = editItemAct.getSaveButton();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveEditButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        InventoryActivity editedInvAct = (InventoryActivity)
                itemDetsMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", editedInvAct);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, itemDetsMon.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, editedInvAct.getClass());

        getInstrumentation().removeMonitor(itemDetsMon);

        trinketIterator = editedInvAct.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getName(), "Test2");
        }

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
        } catch (IOException e) {

        }

        loginActivity.finish();
        addItemtoInventoryActivity.finish();
        displayInventoryActivityAgain.finish();
        homePageActivity.finish();
        inventoryActivity.getInventory().clear();
        inventoryActivity.finish();
        editedInvAct.finish();
        editItemAct.finish();
        itemDetsAct.finish();
    }

    public void testChangeItemQuantity() {
        // Get the current activity
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** HomePageActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor homePageActivityMonitor =
                getInstrumentation().addMonitor(HomePageActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final String test_email = loginActivity.getResources().getString(R.string.test_email);
        final AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                emailTextView.setText(test_email);
            }
        });
        final Button homePageButton = loginActivity.getLoginButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                homePageButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        HomePageActivity homePageActivity = (HomePageActivity)
                homePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", homePageActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                HomePageActivity.class, homePageActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(homePageActivityMonitor);

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
            LoggedInUser.getInstance().saveToNetwork();
        } catch (IOException e) {

        }

        /******** InventoryActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor invActMon =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final Button inventoryButton = homePageActivity.getInventoryButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final InventoryActivity inventoryActivity = (InventoryActivity)
                invActMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", inventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, invActMon.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, inventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(invActMon);

        assertTrue(inventoryActivity.getInventory().size() == 0);

        // Move to add item activity
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditTrinketActivity.class.getName(),
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
        AddOrEditTrinketActivity addItemtoInventoryActivity = (AddOrEditTrinketActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addItemtoInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditTrinketActivity.class, addItemtoInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        // Move to add item activity
        Instrumentation.ActivityMonitor displayInventoryActivityMonitorAgain =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        // Click the button
        final EditText editName = addItemtoInventoryActivity.getTrinketName();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editName.setText("test");
            }
        });
        getInstrumentation().waitForIdleSync();

        // Get resources
        Resources resources = addItemtoInventoryActivity.getResources();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_categories = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_categories)));
        final int ring = spinner_categories.indexOf("Ring");
        final Spinner category = addItemtoInventoryActivity.getTrinketCategory();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                category.setSelection(ring);
            }
        });
        getInstrumentation().waitForIdleSync();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_qualities = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_qualities)));
        final int good = spinner_qualities.indexOf("Good");
        final Spinner quality = addItemtoInventoryActivity.getTrinketQuality();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                quality.setSelection(good);
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the button
        final Button saveItemButton = addItemtoInventoryActivity.getSaveButton();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        InventoryActivity displayInventoryActivityAgain = (InventoryActivity)
                displayInventoryActivityMonitorAgain.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", displayInventoryActivityAgain);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, displayInventoryActivityMonitorAgain.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, displayInventoryActivityAgain.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(displayInventoryActivityMonitorAgain);

        list = displayInventoryActivityAgain.getInventoryItemsList();
        assertNotNull(list.getChildAt(0));
        Iterator<Trinket> trinketIterator = displayInventoryActivityAgain.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getQuantity(), "1");
        }

        // Move to add item activity
        Instrumentation.ActivityMonitor itemDetailsMonitor =
                getInstrumentation().addMonitor(TrinketDetailsActivity.class.getName(),
                        null, false);

        list = displayInventoryActivityAgain.getInventoryItemsList();
        displayInventoryActivityAgain.runOnUiThread(new Runnable() {
            public void run() {
                View firstItem = list.getChildAt(0);
                list.performItemClick(firstItem, 0, firstItem.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        TrinketDetailsActivity itemDetsAct = (TrinketDetailsActivity)
                itemDetailsMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", itemDetsAct);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, itemDetailsMonitor.getHits());
        assertEquals("Activity is of wrong type",
                TrinketDetailsActivity.class, itemDetsAct.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(itemDetailsMonitor);

        // Validate that ReceiverActivity is started
        Instrumentation.ActivityMonitor editItemMon =
                getInstrumentation().addMonitor(AddOrEditTrinketActivity.class.getName(),
                        null, false);

        final Button editButton = itemDetsAct.getEditButton();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditTrinketActivity editItemAct = (AddOrEditTrinketActivity)
                editItemMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", editItemAct);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, editItemMon.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditTrinketActivity.class, editItemAct.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(editItemMon);

        Instrumentation.ActivityMonitor itemDetsMon =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(), null, false);

        final EditText quan = editItemAct.getTrinketQuantity();
        editItemAct.runOnUiThread(new Runnable() {
            public void run() {
                quan.setText("4");
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the button
        final Button saveEditButton = editItemAct.getSaveButton();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveEditButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        InventoryActivity editedInvAct = (InventoryActivity)
                itemDetsMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", editedInvAct);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, itemDetsMon.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, editedInvAct.getClass());

        getInstrumentation().removeMonitor(itemDetsMon);

        trinketIterator = editedInvAct.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getQuantity(), "4");
        }

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
        } catch (IOException e) {

        }

        loginActivity.finish();
        addItemtoInventoryActivity.finish();
        displayInventoryActivityAgain.finish();
        homePageActivity.finish();
        inventoryActivity.getInventory().clear();
        inventoryActivity.finish();
        editedInvAct.finish();
        editItemAct.finish();
        itemDetsAct.finish();
    }

    public void testChangeItemQuality() {
        // Get the current activity
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** HomePageActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor homePageActivityMonitor =
                getInstrumentation().addMonitor(HomePageActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final String test_email = loginActivity.getResources().getString(R.string.test_email);
        final AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                emailTextView.setText(test_email);
            }
        });
        final Button homePageButton = loginActivity.getLoginButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                homePageButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        HomePageActivity homePageActivity = (HomePageActivity)
                homePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", homePageActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                HomePageActivity.class, homePageActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(homePageActivityMonitor);

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
            LoggedInUser.getInstance().saveToNetwork();
        } catch (IOException e) {

        }

        /******** InventoryActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor invActMon =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final Button inventoryButton = homePageActivity.getInventoryButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final InventoryActivity inventoryActivity = (InventoryActivity)
                invActMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", inventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, invActMon.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, inventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(invActMon);

        assertTrue(inventoryActivity.getInventory().size() == 0);

        // Move to add item activity
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditTrinketActivity.class.getName(),
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
        AddOrEditTrinketActivity addItemtoInventoryActivity = (AddOrEditTrinketActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addItemtoInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditTrinketActivity.class, addItemtoInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        // Move to add item activity
        Instrumentation.ActivityMonitor displayInventoryActivityMonitorAgain =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        // Click the button
        final EditText editName = addItemtoInventoryActivity.getTrinketName();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editName.setText("test");
            }
        });
        getInstrumentation().waitForIdleSync();

        // Get resources
        Resources resources = addItemtoInventoryActivity.getResources();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_categories = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_categories)));
        final int ring = spinner_categories.indexOf("Ring");
        final Spinner category = addItemtoInventoryActivity.getTrinketCategory();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                category.setSelection(ring);
            }
        });
        getInstrumentation().waitForIdleSync();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        final ArrayList<String> spinner_qualities = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_qualities)));
        final int good = spinner_qualities.indexOf("Good");
        final Spinner quality = addItemtoInventoryActivity.getTrinketQuality();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                quality.setSelection(good);
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the button
        final Button saveItemButton = addItemtoInventoryActivity.getSaveButton();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        InventoryActivity displayInventoryActivityAgain = (InventoryActivity)
                displayInventoryActivityMonitorAgain.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", displayInventoryActivityAgain);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, displayInventoryActivityMonitorAgain.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, displayInventoryActivityAgain.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(displayInventoryActivityMonitorAgain);

        list = displayInventoryActivityAgain.getInventoryItemsList();
        assertNotNull(list.getChildAt(0));
        Iterator<Trinket> trinketIterator = displayInventoryActivityAgain.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getQuality(), "Good");
        }

        // Move to add item activity
        Instrumentation.ActivityMonitor itemDetailsMonitor =
                getInstrumentation().addMonitor(TrinketDetailsActivity.class.getName(),
                        null, false);

        list = displayInventoryActivityAgain.getInventoryItemsList();
        displayInventoryActivityAgain.runOnUiThread(new Runnable() {
            public void run() {
                View firstItem = list.getChildAt(0);
                list.performItemClick(firstItem, 0, firstItem.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        TrinketDetailsActivity itemDetsAct = (TrinketDetailsActivity)
                itemDetailsMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", itemDetsAct);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, itemDetailsMonitor.getHits());
        assertEquals("Activity is of wrong type",
                TrinketDetailsActivity.class, itemDetsAct.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(itemDetailsMonitor);

        // Validate that ReceiverActivity is started
        Instrumentation.ActivityMonitor editItemMon =
                getInstrumentation().addMonitor(AddOrEditTrinketActivity.class.getName(),
                        null, false);

        final Button editButton = itemDetsAct.getEditButton();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditTrinketActivity editItemAct = (AddOrEditTrinketActivity)
                editItemMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", editItemAct);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, editItemMon.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditTrinketActivity.class, editItemAct.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(editItemMon);

        Instrumentation.ActivityMonitor itemDetsMon =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(), null, false);

        final Spinner qual = editItemAct.getTrinketQuality();
        editItemAct.runOnUiThread(new Runnable() {
            public void run() {
                qual.setSelection(spinner_qualities.indexOf("Poor"));
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the button
        final Button saveEditButton = editItemAct.getSaveButton();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveEditButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        InventoryActivity editedInvAct = (InventoryActivity)
                itemDetsMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", editedInvAct);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, itemDetsMon.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, editedInvAct.getClass());

        getInstrumentation().removeMonitor(itemDetsMon);

        trinketIterator = editedInvAct.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getQuality(), "Poor");
        }

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
        } catch (IOException e) {

        }

        loginActivity.finish();
        addItemtoInventoryActivity.finish();
        displayInventoryActivityAgain.finish();
        homePageActivity.finish();
        inventoryActivity.getInventory().clear();
        inventoryActivity.finish();
        editedInvAct.finish();
        editItemAct.finish();
        itemDetsAct.finish();
    }

    public void testChangeItemDescription() {
        // Get the current activity
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** HomePageActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor homePageActivityMonitor =
                getInstrumentation().addMonitor(HomePageActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final String test_email = loginActivity.getResources().getString(R.string.test_email);
        final AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                emailTextView.setText(test_email);
            }
        });
        final Button homePageButton = loginActivity.getLoginButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                homePageButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        HomePageActivity homePageActivity = (HomePageActivity)
                homePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", homePageActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                HomePageActivity.class, homePageActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(homePageActivityMonitor);

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
            LoggedInUser.getInstance().saveToNetwork();
        } catch (IOException e) {

        }

        /******** InventoryActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor invActMon =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final Button inventoryButton = homePageActivity.getInventoryButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final InventoryActivity inventoryActivity = (InventoryActivity)
                invActMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", inventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, invActMon.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, inventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(invActMon);

        assertTrue(inventoryActivity.getInventory().size() == 0);

        // Move to add item activity
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditTrinketActivity.class.getName(),
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
        AddOrEditTrinketActivity addItemtoInventoryActivity = (AddOrEditTrinketActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addItemtoInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditTrinketActivity.class, addItemtoInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        // Move to add item activity
        Instrumentation.ActivityMonitor displayInventoryActivityMonitorAgain =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        // Click the button
        final EditText editName = addItemtoInventoryActivity.getTrinketName();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editName.setText("test");
            }
        });
        getInstrumentation().waitForIdleSync();

        // Get resources
        Resources resources = addItemtoInventoryActivity.getResources();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_categories = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_categories)));
        final int ring = spinner_categories.indexOf("Ring");
        final Spinner category = addItemtoInventoryActivity.getTrinketCategory();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                category.setSelection(ring);
            }
        });
        getInstrumentation().waitForIdleSync();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_qualities = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_qualities)));
        final int good = spinner_qualities.indexOf("Good");
        final Spinner quality = addItemtoInventoryActivity.getTrinketQuality();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                quality.setSelection(good);
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the button
        final Button saveItemButton = addItemtoInventoryActivity.getSaveButton();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        InventoryActivity displayInventoryActivityAgain = (InventoryActivity)
                displayInventoryActivityMonitorAgain.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", displayInventoryActivityAgain);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, displayInventoryActivityMonitorAgain.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, displayInventoryActivityAgain.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(displayInventoryActivityMonitorAgain);

        list = displayInventoryActivityAgain.getInventoryItemsList();
        assertNotNull(list.getChildAt(0));
        Iterator<Trinket> trinketIterator = displayInventoryActivityAgain.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getDescription(), "");
        }

        // Move to add item activity
        Instrumentation.ActivityMonitor itemDetailsMonitor =
                getInstrumentation().addMonitor(TrinketDetailsActivity.class.getName(),
                        null, false);

        list = displayInventoryActivityAgain.getInventoryItemsList();
        displayInventoryActivityAgain.runOnUiThread(new Runnable() {
            public void run() {
                View firstItem = list.getChildAt(0);
                list.performItemClick(firstItem, 0, firstItem.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        TrinketDetailsActivity itemDetsAct = (TrinketDetailsActivity)
                itemDetailsMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", itemDetsAct);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, itemDetailsMonitor.getHits());
        assertEquals("Activity is of wrong type",
                TrinketDetailsActivity.class, itemDetsAct.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(itemDetailsMonitor);

        // Validate that ReceiverActivity is started
        Instrumentation.ActivityMonitor editItemMon =
                getInstrumentation().addMonitor(AddOrEditTrinketActivity.class.getName(),
                        null, false);

        final Button editButton = itemDetsAct.getEditButton();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditTrinketActivity editItemAct = (AddOrEditTrinketActivity)
                editItemMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", editItemAct);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, editItemMon.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditTrinketActivity.class, editItemAct.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(editItemMon);

        Instrumentation.ActivityMonitor itemDetsMon =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(), null, false);

        final EditText desc = editItemAct.getTrinketDescription();
        editItemAct.runOnUiThread(new Runnable() {
            public void run() {
                desc.setText("new desc");
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the button
        final Button saveEditButton = editItemAct.getSaveButton();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveEditButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        InventoryActivity editedInvAct = (InventoryActivity)
                itemDetsMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", editedInvAct);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, itemDetsMon.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, editedInvAct.getClass());

        getInstrumentation().removeMonitor(itemDetsMon);

        trinketIterator = editedInvAct.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getDescription(), "new desc");
        }
        try {
            LoggedInUser.getInstance().deleteFromNetwork();
        } catch (IOException e) {

        }

        loginActivity.finish();
        addItemtoInventoryActivity.finish();
        displayInventoryActivityAgain.finish();
        homePageActivity.finish();
        inventoryActivity.getInventory().clear();
        inventoryActivity.finish();
        editedInvAct.finish();
        editItemAct.finish();
        itemDetsAct.finish();
    }

    // Test for changing item's category
    public void testChangeItemCategory() {
        // Get the current activity
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** HomePageActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor homePageActivityMonitor =
                getInstrumentation().addMonitor(HomePageActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final String test_email = loginActivity.getResources().getString(R.string.test_email);
        final AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                emailTextView.setText(test_email);
            }
        });
        final Button homePageButton = loginActivity.getLoginButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                homePageButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        HomePageActivity homePageActivity = (HomePageActivity)
                homePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", homePageActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                HomePageActivity.class, homePageActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(homePageActivityMonitor);

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
            LoggedInUser.getInstance().saveToNetwork();
        } catch (IOException e) {

        }

        /******** InventoryActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor invActMon =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final Button inventoryButton = homePageActivity.getInventoryButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final InventoryActivity inventoryActivity = (InventoryActivity)
                invActMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", inventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, invActMon.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, inventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(invActMon);

        assertTrue(inventoryActivity.getInventory().size() == 0);

        // Move to add item activity
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditTrinketActivity.class.getName(),
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
        AddOrEditTrinketActivity addItemtoInventoryActivity = (AddOrEditTrinketActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addItemtoInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditTrinketActivity.class, addItemtoInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        // Move to add item activity
        Instrumentation.ActivityMonitor displayInventoryActivityMonitorAgain =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        // Click the button
        final EditText editName = addItemtoInventoryActivity.getTrinketName();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editName.setText("test");
            }
        });
        getInstrumentation().waitForIdleSync();

        // Get resources
        Resources resources = addItemtoInventoryActivity.getResources();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        final ArrayList<String> spinner_categories = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_categories)));
        final int ring = spinner_categories.indexOf("Ring");
        final Spinner category = addItemtoInventoryActivity.getTrinketCategory();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                category.setSelection(ring);
            }
        });
        getInstrumentation().waitForIdleSync();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        final ArrayList<String> spinner_qualities = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_qualities)));
        final int good = spinner_qualities.indexOf("Good");
        final Spinner quality = addItemtoInventoryActivity.getTrinketQuality();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                quality.setSelection(good);
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the button
        final Button saveItemButton = addItemtoInventoryActivity.getSaveButton();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        InventoryActivity displayInventoryActivityAgain = (InventoryActivity)
                displayInventoryActivityMonitorAgain.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", displayInventoryActivityAgain);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, displayInventoryActivityMonitorAgain.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, displayInventoryActivityAgain.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(displayInventoryActivityMonitorAgain);

        list = displayInventoryActivityAgain.getInventoryItemsList();
        assertNotNull(list.getChildAt(0));
        Iterator<Trinket> trinketIterator = displayInventoryActivityAgain.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getCategory(), "Ring");
        }

        // Move to add item activity
        Instrumentation.ActivityMonitor itemDetailsMonitor =
                getInstrumentation().addMonitor(TrinketDetailsActivity.class.getName(),
                        null, false);

        list = displayInventoryActivityAgain.getInventoryItemsList();
        displayInventoryActivityAgain.runOnUiThread(new Runnable() {
            public void run() {
                View firstItem = list.getChildAt(0);
                list.performItemClick(firstItem, 0, firstItem.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        TrinketDetailsActivity itemDetsAct = (TrinketDetailsActivity)
                itemDetailsMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", itemDetsAct);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, itemDetailsMonitor.getHits());
        assertEquals("Activity is of wrong type",
                TrinketDetailsActivity.class, itemDetsAct.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(itemDetailsMonitor);

        // Validate that ReceiverActivity is started
        Instrumentation.ActivityMonitor editItemMon =
                getInstrumentation().addMonitor(AddOrEditTrinketActivity.class.getName(),
                        null, false);

        final Button editButton = itemDetsAct.getEditButton();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditTrinketActivity editItemAct = (AddOrEditTrinketActivity)
                editItemMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", editItemAct);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, editItemMon.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditTrinketActivity.class, editItemAct.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(editItemMon);

        Instrumentation.ActivityMonitor itemDetsMon =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(), null, false);

        final Spinner cat = editItemAct.getTrinketCategory();
        editItemAct.runOnUiThread(new Runnable() {
            public void run() {
                cat.setSelection(spinner_categories.indexOf("Necklace"));
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the button
        final Button saveEditButton = editItemAct.getSaveButton();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveEditButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        InventoryActivity editedInvAct = (InventoryActivity)
                itemDetsMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", editedInvAct);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, itemDetsMon.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, editedInvAct.getClass());

        getInstrumentation().removeMonitor(itemDetsMon);

        trinketIterator = editedInvAct.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getCategory(), "Necklace");
        }

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
        } catch (IOException e) {

        }

        loginActivity.finish();
        addItemtoInventoryActivity.finish();
        displayInventoryActivityAgain.finish();
        homePageActivity.finish();
        inventoryActivity.getInventory().clear();
        inventoryActivity.finish();
        editedInvAct.finish();
        editItemAct.finish();
        itemDetsAct.finish();
    }

    // Test method for checking how quickly an item can be added to the inventory
    public void testQuickAdd() {
        clickCount = 0;
        // Get the current activity
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** HomePageActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor homePageActivityMonitor =
                getInstrumentation().addMonitor(HomePageActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final String test_email = loginActivity.getResources().getString(R.string.test_email);
        final AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                emailTextView.setText(test_email);
            }
        });
        final Button homePageButton = loginActivity.getLoginButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                homePageButton.performClick();
                clickCount += 1;
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        HomePageActivity homePageActivity = (HomePageActivity)
                homePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", homePageActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                HomePageActivity.class, homePageActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(homePageActivityMonitor);

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
            LoggedInUser.getInstance().saveToNetwork();
        } catch (IOException e) {

        }

        /******** InventoryActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor invActMon =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final Button inventoryButton = homePageActivity.getInventoryButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryButton.performClick();
                clickCount += 1;
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final InventoryActivity inventoryActivity = (InventoryActivity)
                invActMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", inventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, invActMon.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, inventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(invActMon);

        assertTrue(inventoryActivity.getInventory().isEmpty());

        // Move to add item activity
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditTrinketActivity.class.getName(),
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
        AddOrEditTrinketActivity addItemtoInventoryActivity = (AddOrEditTrinketActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addItemtoInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditTrinketActivity.class, addItemtoInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        // Move to add item activity
        Instrumentation.ActivityMonitor displayInventoryActivityMonitorAgain =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        // Get resources
        Resources resources = addItemtoInventoryActivity.getResources();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_categories = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_categories)));
        final int ring = spinner_categories.indexOf("Ring");
        final Spinner category = addItemtoInventoryActivity.getTrinketCategory();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                category.setSelection(ring);
                clickCount += 1;
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the button
        final Button saveItemButton = addItemtoInventoryActivity.getSaveButton();
        addItemtoInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
                clickCount += 1;
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        InventoryActivity displayInventoryActivityAgain = (InventoryActivity)
                displayInventoryActivityMonitorAgain.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", displayInventoryActivityAgain);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, displayInventoryActivityMonitorAgain.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, displayInventoryActivityAgain.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(displayInventoryActivityMonitorAgain);

        list = displayInventoryActivityAgain.getInventoryItemsList();
        assertNotNull(list.getChildAt(0));
        Iterator<Trinket> trinketIterator = inventoryActivity.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getName(), "");
        }

        // 5 or less clicks
        assertTrue(clickCount <= 5);

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
        } catch (IOException e) {

        }

        loginActivity.finish();
        addItemtoInventoryActivity.finish();
        displayInventoryActivityAgain.finish();
        homePageActivity.finish();
        inventoryActivity.finish();
        inventoryActivity.getInventory().clear();
    }
}
