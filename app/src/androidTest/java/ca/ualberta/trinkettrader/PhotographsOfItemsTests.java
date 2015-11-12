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
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;

import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.Inventory.InventoryActivity;
import ca.ualberta.trinkettrader.Inventory.Trinket.AddOrEditTrinketActivity;
import ca.ualberta.trinkettrader.Inventory.Trinket.Pictures.Picture;
import ca.ualberta.trinkettrader.Inventory.Trinket.Trinket;
import ca.ualberta.trinkettrader.Inventory.Trinket.TrinketDetailsActivity;
import ca.ualberta.trinkettrader.User.Profile.EditUserProfileActivity;
import ca.ualberta.trinkettrader.User.Profile.UserProfile;
import ca.ualberta.trinkettrader.User.Profile.UserProfileActivity;

public class PhotographsOfItemsTests extends ActivityInstrumentationTestCase2 {

    // richq; http://stackoverflow.com/questions/3668528/access-image-in-my-android-tests; 2015-11-06
    private AssetManager assetManager;

    public PhotographsOfItemsTests() {
        super(LoginActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        assetManager = this.getActivity().getAssets();
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
        activity.finish();
    }

    public void testAttachPhotograph() {
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

        /******** AddOrEditTrinketActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor addOrEditItemActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditTrinketActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final Button addItemButton = displayInventoryActivity.getAddItemButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                addItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditTrinketActivity addOrEditItemActivity = (AddOrEditTrinketActivity)
                addOrEditItemActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addOrEditItemActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, addOrEditItemActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditTrinketActivity.class, addOrEditItemActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(addOrEditItemActivityMonitor);

        // Edit item name
        final EditText editName = addOrEditItemActivity.getTrinketName();
        final String itemName = "Test";
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                editName.setText(itemName);
            }
        });
        getInstrumentation().waitForIdleSync();

        // Get resources
        Resources resources = addOrEditItemActivity.getResources();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_categories = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_categories)));
        final int ring = spinner_categories.indexOf("Ring");
        final Spinner category = addOrEditItemActivity.getTrinketCategory();
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                category.setSelection(ring);
            }
        });
        getInstrumentation().waitForIdleSync();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_qualities = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_qualities)));
        final int good = spinner_qualities.indexOf("Good");
        final Spinner quality = addOrEditItemActivity.getTrinketQuality();
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                quality.setSelection(good);
            }
        });
        getInstrumentation().waitForIdleSync();

        // TODO Simulate selecting an image

        // Save the item
        final Button saveItemButton = addOrEditItemActivity.getSaveButton();
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Make sure the item has an image
        addOrEditItemActivity.finish();
        Inventory trinkets = displayInventoryActivity.getInventory();
        assertFalse(trinkets.isEmpty());
        for (Trinket trinket : trinkets) {
            assertFalse(trinket.getPictures().isEmpty());
        }

        // Close the activities
        addOrEditItemActivity.finish();
        displayInventoryActivity.finish();
        homePageActivity.finish();
    }

    public void testViewPhotograph() {
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

        /******** AddOrEditTrinketActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor addOrEditItemActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditTrinketActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final Button addItemButton = displayInventoryActivity.getAddItemButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                addItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditTrinketActivity addOrEditItemActivity = (AddOrEditTrinketActivity)
                addOrEditItemActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addOrEditItemActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, addOrEditItemActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditTrinketActivity.class, addOrEditItemActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(addOrEditItemActivityMonitor);

        // Edit item name
        final EditText editName = addOrEditItemActivity.getTrinketName();
        final String itemName = "Test";
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                editName.setText(itemName);
            }
        });
        getInstrumentation().waitForIdleSync();

        // Get resources
        Resources resources = addOrEditItemActivity.getResources();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_categories = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_categories)));
        final int ring = spinner_categories.indexOf("Ring");
        final Spinner category = addOrEditItemActivity.getTrinketCategory();
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                category.setSelection(ring);
            }
        });
        getInstrumentation().waitForIdleSync();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_qualities = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_qualities)));
        final int good = spinner_qualities.indexOf("Good");
        final Spinner quality = addOrEditItemActivity.getTrinketQuality();
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                quality.setSelection(good);
            }
        });
        getInstrumentation().waitForIdleSync();

        // TODO Simulate selecting an image

        // Save the item
        final Button saveItemButton = addOrEditItemActivity.getSaveButton();
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        /******** TrinketDetailsActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor itemDetailsActivityMonitor =
                getInstrumentation().addMonitor(TrinketDetailsActivity.class.getName(),
                        null, false);

        // Start TrinketDetailsActivity
        final ListView inventoryItemsList = displayInventoryActivity.getInventoryItemsList();
        displayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                View view = inventoryItemsList.getChildAt(0);
                inventoryItemsList.performItemClick(view, 0, view.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        TrinketDetailsActivity itemDetailsActivity = (TrinketDetailsActivity)
                itemDetailsActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", itemDetailsActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, itemDetailsActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                TrinketDetailsActivity.class, itemDetailsActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(itemDetailsActivityMonitor);

        // Check that the image is visible
        ArrayList<ImageView> imageViews = itemDetailsActivity.getImageViews();
        // PC.; http://stackoverflow.com/questions/9113895/how-to-check-if-an-imageview-is-attached-with-image-in-android; 2015-11-01
        assertFalse(imageViews.isEmpty());
        for (ImageView imageView : imageViews) {
            assertNotNull(imageView.getDrawable());
        }

        // Close the activities
        itemDetailsActivity.finish();
        addOrEditItemActivity.finish();
        displayInventoryActivity.finish();
        homePageActivity.finish();
        loginActivity.finish();
    }

    public void testConstrainPhotographSize() {
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

        /******** AddOrEditTrinketActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor addOrEditItemActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditTrinketActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final Button addItemButton = displayInventoryActivity.getAddItemButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                addItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditTrinketActivity addOrEditItemActivity = (AddOrEditTrinketActivity)
                addOrEditItemActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addOrEditItemActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, addOrEditItemActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditTrinketActivity.class, addOrEditItemActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(addOrEditItemActivityMonitor);

        // Edit item name
        final EditText editName = addOrEditItemActivity.getTrinketName();
        final String itemName = "Test";
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                editName.setText(itemName);
            }
        });
        getInstrumentation().waitForIdleSync();

        // Get resources
        Resources resources = addOrEditItemActivity.getResources();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_categories = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_categories)));
        final int ring = spinner_categories.indexOf("Ring");
        final Spinner category = addOrEditItemActivity.getTrinketCategory();
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                category.setSelection(ring);
            }
        });
        getInstrumentation().waitForIdleSync();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_qualities = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_qualities)));
        final int good = spinner_qualities.indexOf("Good");
        final Spinner quality = addOrEditItemActivity.getTrinketQuality();
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                quality.setSelection(good);
            }
        });
        getInstrumentation().waitForIdleSync();

        // TODO Simulate selecting an image

        // Save the item
        final Button saveItemButton = addOrEditItemActivity.getSaveButton();
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Make sure the item's image is less than the limit
        addOrEditItemActivity.finish();
        Inventory trinkets = displayInventoryActivity.getInventory();
        for (Trinket trinket : trinkets) {
            for (Picture picture : trinket.getPictures()) {
                assertTrue(picture.size() < 65536);
            }
        }

        // Close the activities
        addOrEditItemActivity.finish();
        displayInventoryActivity.finish();
        homePageActivity.finish();
    }

    public void testDeletePhotoGraph() {
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

        /******** AddOrEditTrinketActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor addOrEditItemActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditTrinketActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final Button addItemButton = displayInventoryActivity.getAddItemButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                addItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditTrinketActivity addOrEditItemActivity = (AddOrEditTrinketActivity)
                addOrEditItemActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addOrEditItemActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, addOrEditItemActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditTrinketActivity.class, addOrEditItemActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(addOrEditItemActivityMonitor);

        // Edit item name
        final EditText editName = addOrEditItemActivity.getTrinketName();
        final String itemName = "Test";
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                editName.setText(itemName);
            }
        });
        getInstrumentation().waitForIdleSync();

        // Get resources
        Resources resources = addOrEditItemActivity.getResources();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_categories = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_categories)));
        final int ring = spinner_categories.indexOf("Ring");
        final Spinner category = addOrEditItemActivity.getTrinketCategory();
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                category.setSelection(ring);
            }
        });
        getInstrumentation().waitForIdleSync();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_qualities = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_qualities)));
        final int good = spinner_qualities.indexOf("Good");
        final Spinner quality = addOrEditItemActivity.getTrinketQuality();
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                quality.setSelection(good);
            }
        });
        getInstrumentation().waitForIdleSync();

        // TODO Simulate selecting an image

        // Save the item
        final Button saveItemButton = addOrEditItemActivity.getSaveButton();
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        /******** TrinketDetailsActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor itemDetailsActivityMonitor =
                getInstrumentation().addMonitor(TrinketDetailsActivity.class.getName(),
                        null, false);

        // Start TrinketDetailsActivity
        final ListView inventoryItemsList = displayInventoryActivity.getInventoryItemsList();
        displayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                View view = inventoryItemsList.getChildAt(0);
                inventoryItemsList.performItemClick(view, 0, view.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        TrinketDetailsActivity itemDetailsActivity = (TrinketDetailsActivity)
                itemDetailsActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", itemDetailsActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, itemDetailsActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                TrinketDetailsActivity.class, itemDetailsActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(itemDetailsActivityMonitor);

        /******** AddOrEditTrinketActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor removeItemPictureActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditTrinketActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final Button editButton = itemDetailsActivity.getEditButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                editButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditTrinketActivity removeItemPictureActivity = (AddOrEditTrinketActivity)
                removeItemPictureActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", removeItemPictureActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, removeItemPictureActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditTrinketActivity.class, removeItemPictureActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(removeItemPictureActivityMonitor);

        // Delete the image
        final Button removeImageButton = removeItemPictureActivity.getRemovePictureButton();
        removeItemPictureActivity.runOnUiThread(new Runnable() {
            public void run() {
                removeImageButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Save the item
        final Button removeItemPictureSaveItemButton = removeItemPictureActivity.getSaveButton();
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                removeItemPictureSaveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        itemDetailsActivity.finish();

        // Make sure the item does not have an image
        Inventory inventory = displayInventoryActivity.getInventory();
        assertFalse(inventory.isEmpty());
        for (Trinket trinket : inventory) {
            assertTrue(trinket.getPictures().isEmpty());
        }

        // Close the activities
        removeItemPictureActivity.finish();
        itemDetailsActivity.finish();
        addOrEditItemActivity.finish();
        displayInventoryActivity.finish();
        homePageActivity.finish();
        loginActivity.finish();
    }

    public void testManuallyChoosePhotosToDownloadIfPhotoDownloadDisabled() {
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

        /******** UserProfileActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor displayUserProfileActivityMonitor =
                getInstrumentation().addMonitor(UserProfileActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final Button profileButton = homePageActivity.getProfileButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                profileButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        UserProfileActivity displayUserProfileActivity = (UserProfileActivity)
                displayUserProfileActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", displayUserProfileActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, displayUserProfileActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                UserProfileActivity.class, displayUserProfileActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(displayUserProfileActivityMonitor);

        /******** EditUserProfileActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor editProfileActivityMonitor =
                getInstrumentation().addMonitor(EditUserProfileActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final Button editUserProfileButton = displayUserProfileActivity.getEditUserProfileButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                editUserProfileButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        EditUserProfileActivity editUserProfileActivity = (EditUserProfileActivity)
                editProfileActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", editUserProfileActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, editProfileActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                EditUserProfileActivity.class, editUserProfileActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(editProfileActivityMonitor);

        UserProfile userProfile = editUserProfileActivity.getUserProfile();
        userProfile.setArePhotosDownloadable(Boolean.TRUE);
        assertTrue(userProfile.getArePhotosDownloadable());

        // Click the button
        final ToggleButton arePhotosDownloadableButton = editUserProfileActivity.getArePhotosDownloadableButton();
        assertTrue(arePhotosDownloadableButton.isChecked());
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                arePhotosDownloadableButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        assertFalse(arePhotosDownloadableButton.isChecked());
        assertFalse(userProfile.getArePhotosDownloadable());

        // Return to main menu
        editUserProfileActivity.finish();
        displayUserProfileActivity.finish();

        // TODO Need to try and manually download a photo here
        assertNotNull(null);

        // Close the activities
        editUserProfileActivity.finish();
        displayUserProfileActivity.finish();
        homePageActivity.finish();
        loginActivity.finish();
    }

    public void testDisablePhotoDownload() {
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

        /******** UserProfileActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor displayUserProfileActivityMonitor =
                getInstrumentation().addMonitor(UserProfileActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final Button profileButton = homePageActivity.getProfileButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                profileButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        UserProfileActivity displayUserProfileActivity = (UserProfileActivity)
                displayUserProfileActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", displayUserProfileActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, displayUserProfileActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                UserProfileActivity.class, displayUserProfileActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(displayUserProfileActivityMonitor);

        /******** EditUserProfileActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor editProfileActivityMonitor =
                getInstrumentation().addMonitor(EditUserProfileActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final Button editUserProfileButton = displayUserProfileActivity.getEditUserProfileButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                editUserProfileButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        EditUserProfileActivity editUserProfileActivity = (EditUserProfileActivity)
                editProfileActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", editUserProfileActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, editProfileActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                EditUserProfileActivity.class, editUserProfileActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(editProfileActivityMonitor);

        UserProfile userProfile = editUserProfileActivity.getUserProfile();
        userProfile.setArePhotosDownloadable(Boolean.TRUE);
        assertTrue(userProfile.getArePhotosDownloadable());

        // Click the button
        final ToggleButton arePhotosDownloadableButton = editUserProfileActivity.getArePhotosDownloadableButton();
        assertTrue(arePhotosDownloadableButton.isChecked());
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                arePhotosDownloadableButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        assertFalse(arePhotosDownloadableButton.isChecked());
        assertFalse(userProfile.getArePhotosDownloadable());

        // Return to main menu
        editUserProfileActivity.finish();
        displayUserProfileActivity.finish();

        // TODO Need to check here if the photos actually don't download
        assertNotNull(null);

        // Close the activities
        editUserProfileActivity.finish();
        displayUserProfileActivity.finish();
        homePageActivity.finish();
        loginActivity.finish();
    }
}
