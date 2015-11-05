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

import android.app.Activity;
import android.app.Instrumentation;
import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


// Running tests help: Janne Oksanen, http://stackoverflow.com/questions/19516289/exception-in-thread-main-java-lang-noclassdeffounderror-junit-textui-resultpr, 2015-10-31

public class InventoryTests extends ActivityInstrumentationTestCase2 {

    int clickCount;
    public ListView list;
    public Button addItemButton;
    public Button inventoryButton;

    public InventoryTests() {
        super(LoginActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
        activity.finish();
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
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** LoginActivity ********/
        {
            // Set up an ActivityMonitor
            Instrumentation.ActivityMonitor receiverActivityMonitor =
                    getInstrumentation().addMonitor(LoginActivity.class.getName(),
                            null, false);

            // Start DisplayInventoryActivity
            AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
            emailTextView.setText("test@test.test");
            final Button homePageButton = loginActivity.getLoginButton();
            loginActivity.runOnUiThread(new Runnable() {
                public void run() {
                    homePageButton.performClick();
                }
            });
            getInstrumentation().waitForIdleSync();

            // Validate that ReceiverActivity is started
            DisplayInventoryActivity receiverActivity = (DisplayInventoryActivity)
                    receiverActivityMonitor.waitForActivityWithTimeout(1000);
            assertNotNull("ReceiverActivity is null", receiverActivity);
            assertEquals("Monitor for ReceiverActivity has not been called",
                    1, receiverActivityMonitor.getHits());
            assertEquals("Activity is of wrong type",
                    DisplayInventoryActivity.class, receiverActivity.getClass());

            // Remove the ActivityMonitor
            getInstrumentation().removeMonitor(receiverActivityMonitor);
        }

        // Get the current activity
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
        AddOrEditItemActivity addItemtoDisplayInventoryActivity = (AddOrEditItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addItemtoDisplayInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, addItemtoDisplayInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

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
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
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
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** LoginActivity ********/
        {
            // Set up an ActivityMonitor
            Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(LoginActivity.class.getName(),
                                    null, false);

            // Start DisplayInventoryActivity
            AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
            emailTextView.setText("test@test.test");
            final Button homePageButton = loginActivity.getLoginButton();
            loginActivity.runOnUiThread(new Runnable() {
                public void run() {
                    homePageButton.performClick();
                }
            });
            getInstrumentation().waitForIdleSync();

            // Validate that ReceiverActivity is started
            DisplayInventoryActivity receiverActivity = (DisplayInventoryActivity)
                    receiverActivityMonitor.waitForActivityWithTimeout(1000);
            assertNotNull("ReceiverActivity is null", receiverActivity);
            assertEquals("Monitor for ReceiverActivity has not been called",
                    1, receiverActivityMonitor.getHits());
            assertEquals("Activity is of wrong type",
                    DisplayInventoryActivity.class, receiverActivity.getClass());

            // Remove the ActivityMonitor
            getInstrumentation().removeMonitor(receiverActivityMonitor);
        }

        // Get the current activity
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
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** LoginActivity ********/
        {
            // Set up an ActivityMonitor
            Instrumentation.ActivityMonitor receiverActivityMonitor =
                    getInstrumentation().addMonitor(LoginActivity.class.getName(),
                            null, false);

            // Start DisplayInventoryActivity
            AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
            emailTextView.setText("test@test.test");
            final Button homePageButton = loginActivity.getLoginButton();
            loginActivity.runOnUiThread(new Runnable() {
                public void run() {
                    homePageButton.performClick();
                }
            });
            getInstrumentation().waitForIdleSync();

            // Validate that ReceiverActivity is started
            DisplayInventoryActivity receiverActivity = (DisplayInventoryActivity)
                    receiverActivityMonitor.waitForActivityWithTimeout(1000);
            assertNotNull("ReceiverActivity is null", receiverActivity);
            assertEquals("Monitor for ReceiverActivity has not been called",
                    1, receiverActivityMonitor.getHits());
            assertEquals("Activity is of wrong type",
                    DisplayInventoryActivity.class, receiverActivity.getClass());

            // Remove the ActivityMonitor
            getInstrumentation().removeMonitor(receiverActivityMonitor);
        }

        // Get the current activity
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
        AddOrEditItemActivity addItemtoDisplayInventoryActivity = (AddOrEditItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addItemtoDisplayInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, addItemtoDisplayInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

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
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        assertTrue(inventoryActivity.getInventory().size() == 1);
    }

    // Test method to test if an inventory is empty
    public void testIsInventoryEmpty() {
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** LoginActivity ********/
        {
            // Set up an ActivityMonitor
            Instrumentation.ActivityMonitor receiverActivityMonitor =
                    getInstrumentation().addMonitor(LoginActivity.class.getName(),
                            null, false);

            // Start DisplayInventoryActivity
            AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
            emailTextView.setText("test@test.test");
            final Button homePageButton = loginActivity.getLoginButton();
            loginActivity.runOnUiThread(new Runnable() {
                public void run() {
                    homePageButton.performClick();
                }
            });
            getInstrumentation().waitForIdleSync();

            // Validate that ReceiverActivity is started
            DisplayInventoryActivity receiverActivity = (DisplayInventoryActivity)
                    receiverActivityMonitor.waitForActivityWithTimeout(1000);
            assertNotNull("ReceiverActivity is null", receiverActivity);
            assertEquals("Monitor for ReceiverActivity has not been called",
                    1, receiverActivityMonitor.getHits());
            assertEquals("Activity is of wrong type",
                    DisplayInventoryActivity.class, receiverActivity.getClass());

            // Remove the ActivityMonitor
            getInstrumentation().removeMonitor(receiverActivityMonitor);
        }

        // Get the current activity
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
        AddOrEditItemActivity addItemtoDisplayInventoryActivity = (AddOrEditItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addItemtoDisplayInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, addItemtoDisplayInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

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
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        assertFalse(inventoryActivity.getInventory().isEmpty());
    }

    // Test method for adding an item to your inventory
    public void testAddItem() {
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** LoginActivity ********/
        {
            // Set up an ActivityMonitor
            Instrumentation.ActivityMonitor receiverActivityMonitor =
                    getInstrumentation().addMonitor(LoginActivity.class.getName(),
                            null, false);

            // Start DisplayInventoryActivity
            AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
            emailTextView.setText("test@test.test");
            final Button homePageButton = loginActivity.getLoginButton();
            loginActivity.runOnUiThread(new Runnable() {
                public void run() {
                    homePageButton.performClick();
                }
            });
            getInstrumentation().waitForIdleSync();

            // Validate that ReceiverActivity is started
            DisplayInventoryActivity receiverActivity = (DisplayInventoryActivity)
                    receiverActivityMonitor.waitForActivityWithTimeout(1000);
            assertNotNull("ReceiverActivity is null", receiverActivity);
            assertEquals("Monitor for ReceiverActivity has not been called",
                    1, receiverActivityMonitor.getHits());
            assertEquals("Activity is of wrong type",
                    DisplayInventoryActivity.class, receiverActivity.getClass());

            // Remove the ActivityMonitor
            getInstrumentation().removeMonitor(receiverActivityMonitor);
        }

        // Get the current activity
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
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditItemActivity addItemtoDisplayInventoryActivity = (AddOrEditItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addItemtoDisplayInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, addItemtoDisplayInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

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
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        Iterator<Trinket> trinketIterator = inventoryActivity.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getName(), "test");
        }
    }

    // Test method for removing an item from your inventory
    public void testRemoveItem() {
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** LoginActivity ********/
        {
            // Set up an ActivityMonitor
            Instrumentation.ActivityMonitor receiverActivityMonitor =
                    getInstrumentation().addMonitor(LoginActivity.class.getName(),
                            null, false);

            // Start DisplayInventoryActivity
            AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
            emailTextView.setText("test@test.test");
            final Button homePageButton = loginActivity.getLoginButton();
            loginActivity.runOnUiThread(new Runnable() {
                public void run() {
                    homePageButton.performClick();
                }
            });
            getInstrumentation().waitForIdleSync();

            // Validate that ReceiverActivity is started
            DisplayInventoryActivity receiverActivity = (DisplayInventoryActivity)
                    receiverActivityMonitor.waitForActivityWithTimeout(1000);
            assertNotNull("ReceiverActivity is null", receiverActivity);
            assertEquals("Monitor for ReceiverActivity has not been called",
                    1, receiverActivityMonitor.getHits());
            assertEquals("Activity is of wrong type",
                    DisplayInventoryActivity.class, receiverActivity.getClass());

            // Remove the ActivityMonitor
            getInstrumentation().removeMonitor(receiverActivityMonitor);
        }

        // Get the current activity
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
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        receiverActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditItemActivity.class.getName(),
                        null, false);
        AddOrEditItemActivity addItemtoDisplayInventoryActivity = (AddOrEditItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addItemtoDisplayInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, addItemtoDisplayInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

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
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        Iterator<Trinket> trinketIterator = inventoryActivity.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getName(), "test");
        }

        list = inventoryActivity.getInventoryItemsList();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                View firstItem = list.getChildAt(0);
                list.performItemClick(firstItem, 0, firstItem.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        receiverActivityMonitor =
                getInstrumentation().addMonitor(ItemDetailsActivity.class.getName(),
                        null, false);
        ItemDetailsActivity itemDetsAct = (ItemDetailsActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", itemDetsAct);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, itemDetsAct.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        final Button deleteButton = itemDetsAct.getDeleteButton();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                deleteButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        assertNull(list.getChildAt(0));
    }

    // Test method for changing the share settings of an item
    public void testShareSettings() {
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** LoginActivity ********/
        {
            // Set up an ActivityMonitor
            Instrumentation.ActivityMonitor receiverActivityMonitor =
                    getInstrumentation().addMonitor(LoginActivity.class.getName(),
                            null, false);

            // Start DisplayInventoryActivity
            AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
            emailTextView.setText("test@test.test");
            final Button homePageButton = loginActivity.getLoginButton();
            loginActivity.runOnUiThread(new Runnable() {
                public void run() {
                    homePageButton.performClick();
                }
            });
            getInstrumentation().waitForIdleSync();

            // Validate that ReceiverActivity is started
            DisplayInventoryActivity receiverActivity = (DisplayInventoryActivity)
                    receiverActivityMonitor.waitForActivityWithTimeout(1000);
            assertNotNull("ReceiverActivity is null", receiverActivity);
            assertEquals("Monitor for ReceiverActivity has not been called",
                    1, receiverActivityMonitor.getHits());
            assertEquals("Activity is of wrong type",
                    DisplayInventoryActivity.class, receiverActivity.getClass());

            // Remove the ActivityMonitor
            getInstrumentation().removeMonitor(receiverActivityMonitor);
        }

        // Get the current activity
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
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        Iterator<Trinket> trinketIterator = inventoryActivity.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getAccessibility(), "public");
        }

        list = inventoryActivity.getInventoryItemsList();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                View firstItem = list.getChildAt(0);
                list.performItemClick(firstItem, 0, firstItem.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        receiverActivityMonitor =
                getInstrumentation().addMonitor(ItemDetailsActivity.class.getName(),
                        null, false);
        ItemDetailsActivity itemDetsActivity = (ItemDetailsActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", itemDetsActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, itemDetsActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        final Button editButton = itemDetsActivity.getEditButton();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        receiverActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditItemActivity.class.getName(),
                        null, false);
        nextReceiverActivity = (AddOrEditItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", nextReceiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, nextReceiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        AddOrEditItemActivity editItemAct = (AddOrEditItemActivity) getActivity();

        final CheckBox accessibility = editItemAct.getAccessibility();
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                accessibility.setChecked(false);
            }
        });
        getInstrumentation().waitForIdleSync();

        trinketIterator = inventoryActivity.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getAccessibility(), "private");
        }
    }

    /* Test methods to edit an item's details */
    public void testChangeItemName() {
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** LoginActivity ********/
        {
            // Set up an ActivityMonitor
            Instrumentation.ActivityMonitor receiverActivityMonitor =
                    getInstrumentation().addMonitor(LoginActivity.class.getName(),
                            null, false);

            // Start DisplayInventoryActivity
            AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
            emailTextView.setText("test@test.test");
            final Button homePageButton = loginActivity.getLoginButton();
            loginActivity.runOnUiThread(new Runnable() {
                public void run() {
                    homePageButton.performClick();
                }
            });
            getInstrumentation().waitForIdleSync();

            // Validate that ReceiverActivity is started
            DisplayInventoryActivity receiverActivity = (DisplayInventoryActivity)
                    receiverActivityMonitor.waitForActivityWithTimeout(1000);
            assertNotNull("ReceiverActivity is null", receiverActivity);
            assertEquals("Monitor for ReceiverActivity has not been called",
                    1, receiverActivityMonitor.getHits());
            assertEquals("Activity is of wrong type",
                    DisplayInventoryActivity.class, receiverActivity.getClass());

            // Remove the ActivityMonitor
            getInstrumentation().removeMonitor(receiverActivityMonitor);
        }

        // Get the current activity
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
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditItemActivity addItemtoDisplayInventoryActivity = (AddOrEditItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addItemtoDisplayInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, addItemtoDisplayInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

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
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        Iterator<Trinket> trinketIterator = inventoryActivity.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getName(), "test");
        }

        list = inventoryActivity.getInventoryItemsList();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                View firstItem = list.getChildAt(0);
                list.performItemClick(firstItem, 0, firstItem.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        receiverActivityMonitor =
                getInstrumentation().addMonitor(ItemDetailsActivity.class.getName(),
                        null, false);
        ItemDetailsActivity itemDetsActivity = (ItemDetailsActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", itemDetsActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, itemDetsActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        final Button editButton = itemDetsActivity.getEditButton();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditItemActivity editItemAct = (AddOrEditItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", editItemAct);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, editItemAct.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        editItemAct.runOnUiThread(new Runnable() {
            public void run() {
                editName.setText("new name");
            }
        });
        getInstrumentation().waitForIdleSync();

        trinketIterator = inventoryActivity.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getName(), "new name");
        }
    }
    public void testChangeItemQuantity() {
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** LoginActivity ********/
        {
            // Set up an ActivityMonitor
            Instrumentation.ActivityMonitor receiverActivityMonitor =
                    getInstrumentation().addMonitor(LoginActivity.class.getName(),
                            null, false);

            // Start DisplayInventoryActivity
            AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
            emailTextView.setText("test@test.test");
            final Button homePageButton = loginActivity.getLoginButton();
            loginActivity.runOnUiThread(new Runnable() {
                public void run() {
                    homePageButton.performClick();
                }
            });
            getInstrumentation().waitForIdleSync();

            // Validate that ReceiverActivity is started
            DisplayInventoryActivity receiverActivity = (DisplayInventoryActivity)
                    receiverActivityMonitor.waitForActivityWithTimeout(1000);
            assertNotNull("ReceiverActivity is null", receiverActivity);
            assertEquals("Monitor for ReceiverActivity has not been called",
                    1, receiverActivityMonitor.getHits());
            assertEquals("Activity is of wrong type",
                    DisplayInventoryActivity.class, receiverActivity.getClass());

            // Remove the ActivityMonitor
            getInstrumentation().removeMonitor(receiverActivityMonitor);
        }

        // Get the current activity
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
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditItemActivity addItemtoDisplayInventoryActivity = (AddOrEditItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addItemtoDisplayInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, addItemtoDisplayInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

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
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        Iterator<Trinket> trinketIterator = inventoryActivity.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertTrue(trinketIterator.next().getQuantity() == "1");
        }

        list = inventoryActivity.getInventoryItemsList();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                View firstItem = list.getChildAt(0);
                list.performItemClick(firstItem, 0, firstItem.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        receiverActivityMonitor =
                getInstrumentation().addMonitor(ItemDetailsActivity.class.getName(),
                        null, false);
        ItemDetailsActivity itemDetsActivity = (ItemDetailsActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", itemDetsActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, itemDetsActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        final Button editButton = itemDetsActivity.getEditButton();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditItemActivity editItemAct = (AddOrEditItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", editItemAct);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, editItemAct.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        final EditText quantity = editItemAct.getItemQuantity();
        editItemAct.runOnUiThread(new Runnable() {
            public void run() {
                quantity.setText("5");
            }
        });
        getInstrumentation().waitForIdleSync();

        trinketIterator = inventoryActivity.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertTrue(trinketIterator.next().getQuantity() == "5");
        }
    }
    public void testChangeItemQuality() {
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** LoginActivity ********/
        {
            // Set up an ActivityMonitor
            Instrumentation.ActivityMonitor receiverActivityMonitor =
                    getInstrumentation().addMonitor(LoginActivity.class.getName(),
                            null, false);

            // Start DisplayInventoryActivity
            AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
            emailTextView.setText("test@test.test");
            final Button homePageButton = loginActivity.getLoginButton();
            loginActivity.runOnUiThread(new Runnable() {
                public void run() {
                    homePageButton.performClick();
                }
            });
            getInstrumentation().waitForIdleSync();

            // Validate that ReceiverActivity is started
            DisplayInventoryActivity receiverActivity = (DisplayInventoryActivity)
                    receiverActivityMonitor.waitForActivityWithTimeout(1000);
            assertNotNull("ReceiverActivity is null", receiverActivity);
            assertEquals("Monitor for ReceiverActivity has not been called",
                    1, receiverActivityMonitor.getHits());
            assertEquals("Activity is of wrong type",
                    DisplayInventoryActivity.class, receiverActivity.getClass());

            // Remove the ActivityMonitor
            getInstrumentation().removeMonitor(receiverActivityMonitor);
        }

        // Get the current activity
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
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditItemActivity addItemtoDisplayInventoryActivity = (AddOrEditItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addItemtoDisplayInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, addItemtoDisplayInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

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
        final ArrayList<String> spinner_qualities = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_qualities)));
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
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        Iterator<Trinket> trinketIterator = inventoryActivity.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getQuality(), "Good");
        }

        list = inventoryActivity.getInventoryItemsList();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                View firstItem = list.getChildAt(0);
                list.performItemClick(firstItem, 0, firstItem.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        receiverActivityMonitor =
                getInstrumentation().addMonitor(ItemDetailsActivity.class.getName(),
                        null, false);
        ItemDetailsActivity itemDetsActivity = (ItemDetailsActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", itemDetsActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, itemDetsActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        final Button editButton = itemDetsActivity.getEditButton();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditItemActivity editItemAct = (AddOrEditItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", editItemAct);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, editItemAct.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        editItemAct.runOnUiThread(new Runnable() {
            public void run() {
                quality.setSelection(spinner_qualities.indexOf("Poor"));
            }
        });
        getInstrumentation().waitForIdleSync();

        trinketIterator = inventoryActivity.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getQuality(), "Poor");
        }
    }
    public void testChangeItemDescription() {
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** LoginActivity ********/
        {
            // Set up an ActivityMonitor
            Instrumentation.ActivityMonitor receiverActivityMonitor =
                    getInstrumentation().addMonitor(LoginActivity.class.getName(),
                            null, false);

            // Start DisplayInventoryActivity
            AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
            emailTextView.setText("test@test.test");
            final Button homePageButton = loginActivity.getLoginButton();
            loginActivity.runOnUiThread(new Runnable() {
                public void run() {
                    homePageButton.performClick();
                }
            });
            getInstrumentation().waitForIdleSync();

            // Validate that ReceiverActivity is started
            DisplayInventoryActivity receiverActivity = (DisplayInventoryActivity)
                    receiverActivityMonitor.waitForActivityWithTimeout(1000);
            assertNotNull("ReceiverActivity is null", receiverActivity);
            assertEquals("Monitor for ReceiverActivity has not been called",
                    1, receiverActivityMonitor.getHits());
            assertEquals("Activity is of wrong type",
                    DisplayInventoryActivity.class, receiverActivity.getClass());

            // Remove the ActivityMonitor
            getInstrumentation().removeMonitor(receiverActivityMonitor);
        }

        // Get the current activity
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
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditItemActivity addItemtoDisplayInventoryActivity = (AddOrEditItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addItemtoDisplayInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, addItemtoDisplayInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

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
        final ArrayList<String> spinner_qualities = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_qualities)));
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
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        Iterator<Trinket> trinketIterator = inventoryActivity.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getDescription(), "");
        }

        list = inventoryActivity.getInventoryItemsList();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                View firstItem = list.getChildAt(0);
                list.performItemClick(firstItem, 0, firstItem.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        receiverActivityMonitor =
                getInstrumentation().addMonitor(ItemDetailsActivity.class.getName(),
                        null, false);
        ItemDetailsActivity itemDetsActivity = (ItemDetailsActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", itemDetsActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, itemDetsActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        final Button editButton = itemDetsActivity.getEditButton();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditItemActivity editItemAct = (AddOrEditItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", editItemAct);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, editItemAct.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        final EditText desc = editItemAct.getItemDescription();
        editItemAct.runOnUiThread(new Runnable() {
            public void run() {
                desc.setText("description");
            }
        });
        getInstrumentation().waitForIdleSync();

        trinketIterator = inventoryActivity.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getDescription(), "description");
        }
    }
    // Test for changing item's category
    public void testChangeItemCategory() {
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** LoginActivity ********/
        {
            // Set up an ActivityMonitor
            Instrumentation.ActivityMonitor receiverActivityMonitor =
                    getInstrumentation().addMonitor(LoginActivity.class.getName(),
                            null, false);

            // Start DisplayInventoryActivity
            AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
            emailTextView.setText("test@test.test");
            final Button homePageButton = loginActivity.getLoginButton();
            loginActivity.runOnUiThread(new Runnable() {
                public void run() {
                    homePageButton.performClick();
                }
            });
            getInstrumentation().waitForIdleSync();

            // Validate that ReceiverActivity is started
            DisplayInventoryActivity receiverActivity = (DisplayInventoryActivity)
                    receiverActivityMonitor.waitForActivityWithTimeout(1000);
            assertNotNull("ReceiverActivity is null", receiverActivity);
            assertEquals("Monitor for ReceiverActivity has not been called",
                    1, receiverActivityMonitor.getHits());
            assertEquals("Activity is of wrong type",
                    DisplayInventoryActivity.class, receiverActivity.getClass());

            // Remove the ActivityMonitor
            getInstrumentation().removeMonitor(receiverActivityMonitor);
        }

        // Get the current activity
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
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditItemActivity addItemtoDisplayInventoryActivity = (AddOrEditItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addItemtoDisplayInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, addItemtoDisplayInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

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
        final ArrayList<String> spinner_categories = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_categories)));
        final int ring = spinner_categories.indexOf("Ring");
        final Spinner category = addItemtoDisplayInventoryActivity.getItemCategory();
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                category.setSelection(ring);
            }
        });
        getInstrumentation().waitForIdleSync();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        final ArrayList<String> spinner_qualities = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_qualities)));
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
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        Iterator<Trinket> trinketIterator = inventoryActivity.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getCategory(), "Ring");
        }

        list = inventoryActivity.getInventoryItemsList();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                View firstItem = list.getChildAt(0);
                list.performItemClick(firstItem, 0, firstItem.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        receiverActivityMonitor =
                getInstrumentation().addMonitor(ItemDetailsActivity.class.getName(),
                        null, false);
        ItemDetailsActivity itemDetsActivity = (ItemDetailsActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", itemDetsActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, itemDetsActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        final Button editButton = itemDetsActivity.getEditButton();
        inventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditItemActivity editItemAct = (AddOrEditItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", editItemAct);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, editItemAct.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        editItemAct.runOnUiThread(new Runnable() {
            public void run() {
                category.setSelection(spinner_categories.indexOf("Necklace"));
            }
        });
        getInstrumentation().waitForIdleSync();

        trinketIterator = inventoryActivity.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            assertEquals(trinketIterator.next().getCategory(), "Necklace");
        }
    }

    // Test method for checking how quickly an item can be added to the inventory
    public void testQuickAdd() {
        clickCount = 0;
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** LoginActivity ********/
        {
            // Set up an ActivityMonitor
            Instrumentation.ActivityMonitor receiverActivityMonitor =
                    getInstrumentation().addMonitor(LoginActivity.class.getName(),
                            null, false);

            // Start DisplayInventoryActivity
            AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
            emailTextView.setText("test@test.test");
            final Button homePageButton = loginActivity.getLoginButton();
            loginActivity.runOnUiThread(new Runnable() {
                public void run() {
                    homePageButton.performClick();
                }
            });
            getInstrumentation().waitForIdleSync();

            // Validate that ReceiverActivity is started
            DisplayInventoryActivity receiverActivity = (DisplayInventoryActivity)
                    receiverActivityMonitor.waitForActivityWithTimeout(1000);
            assertNotNull("ReceiverActivity is null", receiverActivity);
            assertEquals("Monitor for ReceiverActivity has not been called",
                    1, receiverActivityMonitor.getHits());
            assertEquals("Activity is of wrong type",
                    DisplayInventoryActivity.class, receiverActivity.getClass());

            // Remove the ActivityMonitor
            getInstrumentation().removeMonitor(receiverActivityMonitor);
        }

        // Get the current activity
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
        AddOrEditItemActivity addItemtoDisplayInventoryActivity = (AddOrEditItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addItemtoDisplayInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, addItemtoDisplayInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

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
        addItemtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
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
