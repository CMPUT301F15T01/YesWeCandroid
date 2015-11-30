package ca.ualberta.trinkettrader;

import android.app.Instrumentation;
import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import ca.ualberta.trinkettrader.Friends.FriendsInventoryActivity;
import ca.ualberta.trinkettrader.Friends.FriendsListActivity;
import ca.ualberta.trinkettrader.Inventory.InventoryActivity;
import ca.ualberta.trinkettrader.Inventory.Trinket.AddOrEditTrinketActivity;
import ca.ualberta.trinkettrader.Inventory.Trinket.TrinketDetailsActivity;
import ca.ualberta.trinkettrader.User.LoggedInUser;
import ca.ualberta.trinkettrader.User.Profile.EditUserProfileActivity;
import ca.ualberta.trinkettrader.User.Profile.UserProfileActivity;

public class GeolocationTests extends ActivityInstrumentationTestCase2 {

    public GeolocationTests() {
        super(LoginActivity.class);
    }

    public void testSetDefaultTrinketGeolocation() {
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

        // Delete user from network
        try {
            LoggedInUser.getInstance().deleteFromNetwork();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        // Set new latitude and longitude
        final TextView latitude = editUserProfileActivity.getLatitude();
        final TextView longitude = editUserProfileActivity.getLongitude();
        editUserProfileActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                latitude.setText("55.5555");
                longitude.setText("25.2525");
            }
        });
        getInstrumentation().waitForIdleSync();

        // Start InventoryActivity
        final Button saveButton = editUserProfileActivity.getSaveButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

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
        final AddOrEditTrinketActivity addOrEditItemActivity = (AddOrEditTrinketActivity)
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

        /******** InventoryActivity ********/
        // Save the item
        final Button saveItemButton = addOrEditItemActivity.getSaveButton();
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        InventoryActivity inventoryActivity = (InventoryActivity)
                displayInventoryActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", inventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                2, displayInventoryActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, inventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(displayInventoryActivityMonitor);

        /******** TrinketDetailsActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor trinketDetailsActivityMonitor =
                getInstrumentation().addMonitor(TrinketDetailsActivity.class.getName(),
                        null, false);

        // Save the item
        final ListView listView = inventoryActivity.getInventoryItemsList();
        inventoryActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                assertNotSame(listView.getChildCount(), 0);
                listView.performItemClick(listView, 1, 0);
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        TrinketDetailsActivity trinketDetailsActivity = (TrinketDetailsActivity)
                trinketDetailsActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", trinketDetailsActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, trinketDetailsActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                TrinketDetailsActivity.class, trinketDetailsActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(trinketDetailsActivityMonitor);

        // Ensure that the default latitude and longitude are present
        assertTrue(trinketDetailsActivity.getLatitude().getText().toString().equals("55.5555"));
        assertTrue(trinketDetailsActivity.getLongitude().getText().toString().equals("25.2525"));

        // Delete user from network
        try {
            LoggedInUser.getInstance().deleteFromNetwork();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Close activities
        addOrEditItemActivity.finish();
        displayInventoryActivity.finish();
        displayUserProfileActivity.finish();
        editUserProfileActivity.finish();
        homePageActivity.finish();
        inventoryActivity.finish();
        loginActivity.finish();
        trinketDetailsActivity.finish();
    }

    public void testDetermineGeolocation() {
        /**
         * Prior to running this test you must run the emulator and provide it
         * provide it a gps location.
         *
         * telnet localhost 5554
         * geo fix 25.2525 55.5555
         */

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

        // Delete user from network
        try {
            LoggedInUser.getInstance().deleteFromNetwork();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        // Ensure the values are correct
        assertTrue(displayUserProfileActivity.getLatitude().getText().toString().equals("55.5555"));
        assertTrue(displayUserProfileActivity.getLongitude().getText().toString().equals("25.2525"));

        // Close activities
        displayUserProfileActivity.finish();
        homePageActivity.finish();
        loginActivity.finish();
    }

    public void testSearchByGeolocation() {
        /**
         * Prior to running this test you must run the emulator and provide it
         * provide it a gps location.
         *
         * telnet localhost 5554
         * geo fix 25.2525 55.5555
         */

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

        // Delete user from network
        try {
            LoggedInUser.getInstance().deleteFromNetwork();
            LoggedInUser.getInstance().saveToNetwork();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /******** UserProfileActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor displayFriendsMonitor =
                getInstrumentation().addMonitor(FriendsListActivity.class.getName(),
                        null, false);

        // Start InventoryActivity
        final Button friendsButton = homePageActivity.getFriendsButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                friendsButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        FriendsListActivity friendsListActivity = (FriendsListActivity)
                displayFriendsMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", friendsListActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, displayFriendsMonitor.getHits());
        assertEquals("Activity is of wrong type",
                UserProfileActivity.class, friendsListActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(displayFriendsMonitor);

        Instrumentation.ActivityMonitor friendsInventoryActivityMonitor =
                getInstrumentation().addMonitor(FriendsInventoryActivity.class.getName(),
                        null, false);

        final Button fInvButton = friendsListActivity.getAllInventoriesButton();
        friendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                fInvButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        FriendsInventoryActivity friendsInventoryActivity = (FriendsInventoryActivity)
                friendsInventoryActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", friendsInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, friendsInventoryActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                UserProfileActivity.class, friendsInventoryActivity.getClass());

        getInstrumentation().removeMonitor(friendsInventoryActivityMonitor);

        final Spinner radiusSpinner = friendsInventoryActivity.getLocationSpinner();
        Resources resources = friendsInventoryActivity.getResources();
        ArrayList<String> spinner_categories = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_location)));
        final int dist5 = spinner_categories.indexOf("5 km");
        friendsInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                radiusSpinner.setSelection(dist5);
            }
        });
        getInstrumentation().waitForIdleSync();

        final Button filterButton = friendsInventoryActivity.getFilterButton();
        friendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                filterButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        final ListView friendsInvList = friendsListActivity.getFriendsListView();
    }
}
