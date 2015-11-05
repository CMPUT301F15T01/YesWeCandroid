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
import java.util.Iterator;

public class PhotographsOfItemsTests extends ActivityInstrumentationTestCase2 {

    public PhotographsOfItemsTests() {
        super(LoginActivity.class);
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

        // Start DisplayInventoryActivity
        final AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                emailTextView.setText("test@test.test");
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

        /******** DisplayInventoryActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor displayInventoryActivityMonitor =
                getInstrumentation().addMonitor(DisplayInventoryActivity.class.getName(),
                        null, false);

        // Start DisplayInventoryActivity
        final Button inventoryButton = homePageActivity.getInventoryButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        DisplayInventoryActivity displayInventoryActivity = (DisplayInventoryActivity)
                displayInventoryActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", displayInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, displayInventoryActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                DisplayInventoryActivity.class, displayInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(displayInventoryActivityMonitor);

        /******** AddOrEditItemActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor addOrEditItemActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditItemActivity.class.getName(),
                        null, false);

        // Start DisplayInventoryActivity
        final Button addItemButton = displayInventoryActivity.getAddItemButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                addItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditItemActivity addOrEditItemActivity = (AddOrEditItemActivity)
                addOrEditItemActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addOrEditItemActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, addOrEditItemActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, addOrEditItemActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(addOrEditItemActivityMonitor);


        // Edit item name
        final EditText editName = addOrEditItemActivity.getItemName();
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
        final Spinner category = addOrEditItemActivity.getItemCategory();
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                category.setSelection(ring);
            }
        });
        getInstrumentation().waitForIdleSync();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_qualities = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_qualities)));
        final int good = spinner_qualities.indexOf("Good");
        final Spinner quality = addOrEditItemActivity.getItemQuality();
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                quality.setSelection(good);
            }
        });
        getInstrumentation().waitForIdleSync();

        // TODO UI test the image capturing with the camera

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
        Iterator<Trinket> trinketIterator = displayInventoryActivity.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            ArrayList<Picture> pictures = trinketIterator.next().getPictures();
            assertNotNull(trinketIterator.next().getPictures());
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

        // Start DisplayInventoryActivity
        final AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                emailTextView.setText("test@test.test");
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

        /******** DisplayInventoryActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor displayInventoryActivityMonitor =
                getInstrumentation().addMonitor(DisplayInventoryActivity.class.getName(),
                        null, false);

        // Start DisplayInventoryActivity
        final Button inventoryButton = homePageActivity.getInventoryButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        DisplayInventoryActivity displayInventoryActivity = (DisplayInventoryActivity)
                displayInventoryActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", displayInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, displayInventoryActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                DisplayInventoryActivity.class, displayInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(displayInventoryActivityMonitor);

        /******** AddOrEditItemActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor addOrEditItemActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditItemActivity.class.getName(),
                        null, false);

        // Start DisplayInventoryActivity
        final Button addItemButton = displayInventoryActivity.getAddItemButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                addItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditItemActivity addOrEditItemActivity = (AddOrEditItemActivity)
                addOrEditItemActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addOrEditItemActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, addOrEditItemActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, addOrEditItemActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(addOrEditItemActivityMonitor);

        // Edit item name
        final EditText editName = addOrEditItemActivity.getItemName();
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
        final Spinner category = addOrEditItemActivity.getItemCategory();
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                category.setSelection(ring);
            }
        });
        getInstrumentation().waitForIdleSync();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_qualities = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_qualities)));
        final int good = spinner_qualities.indexOf("Good");
        final Spinner quality = addOrEditItemActivity.getItemQuality();
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                quality.setSelection(good);
            }
        });
        getInstrumentation().waitForIdleSync();

        // TODO UI test the image capturing with the camera

        // Save the item
        final Button saveItemButton = addOrEditItemActivity.getSaveButton();
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        /******** ItemDetailsActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor itemDetailsActivityMonitor =
                getInstrumentation().addMonitor(ItemDetailsActivity.class.getName(),
                        null, false);

        // Start ItemDetailsActivity
        final ListView inventoryItemsList = displayInventoryActivity.getInventoryItemsList();
        Trinket trinket = (Trinket) inventoryItemsList.getItemAtPosition(0);
        displayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                View view = inventoryItemsList.getChildAt(0);
                inventoryItemsList.performItemClick(view, 0, view.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        ItemDetailsActivity itemDetailsActivity = (ItemDetailsActivity)
                itemDetailsActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", itemDetailsActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, itemDetailsActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                ItemDetailsActivity.class, itemDetailsActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(itemDetailsActivityMonitor);

        // Check that the image is visible
        ImageView itemImage = itemDetailsActivity.getItemPicture();
        // PC.; http://stackoverflow.com/questions/9113895/how-to-check-if-an-imageview-is-attached-with-image-in-android; 2015-11-01
        assertNotNull(itemImage.getDrawable());

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

        // TODO Need to check that all the photos are under 65536 bytes
        assertNotNull(null);

        // Close the activities
        loginActivity.finish();
    }

    public void testDeletePhotoGraph() {
        // Get the current activity
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** HomePageActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor homePageActivityMonitor =
                getInstrumentation().addMonitor(HomePageActivity.class.getName(),
                        null, false);

        // Start DisplayInventoryActivity
        final AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                emailTextView.setText("test@test.test");
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

        /******** DisplayInventoryActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor displayInventoryActivityMonitor =
                getInstrumentation().addMonitor(DisplayInventoryActivity.class.getName(),
                        null, false);

        // Start DisplayInventoryActivity
        final Button inventoryButton = homePageActivity.getInventoryButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                inventoryButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        DisplayInventoryActivity displayInventoryActivity = (DisplayInventoryActivity)
                displayInventoryActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", displayInventoryActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, displayInventoryActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                DisplayInventoryActivity.class, displayInventoryActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(displayInventoryActivityMonitor);

        /******** AddOrEditItemActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor addOrEditItemActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditItemActivity.class.getName(),
                        null, false);

        // Start DisplayInventoryActivity
        final Button addItemButton = displayInventoryActivity.getAddItemButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                addItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditItemActivity addOrEditItemActivity = (AddOrEditItemActivity)
                addOrEditItemActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", addOrEditItemActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, addOrEditItemActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, addOrEditItemActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(addOrEditItemActivityMonitor);

        // Edit item name
        final EditText editName = addOrEditItemActivity.getItemName();
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
        final Spinner category = addOrEditItemActivity.getItemCategory();
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                category.setSelection(ring);
            }
        });
        getInstrumentation().waitForIdleSync();

        // SLaks; http://stackoverflow.com/questions/3064423/in-java-how-to-easily-convert-an-array-to-a-set; 2015-10-30
        ArrayList<String> spinner_qualities = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.spinner_qualities)));
        final int good = spinner_qualities.indexOf("Good");
        final Spinner quality = addOrEditItemActivity.getItemQuality();
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                quality.setSelection(good);
            }
        });
        getInstrumentation().waitForIdleSync();

        // TODO UI test the image capturing with the camera

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
        Iterator<Trinket> trinketIterator = displayInventoryActivity.getInventory().iterator();
        while (trinketIterator.hasNext()) {
            ArrayList<Picture> pictures = trinketIterator.next().getPictures();
            assertNotNull(trinketIterator.next().getPictures());
        }

        /******** ItemDetailsActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor itemDetailsActivityMonitor =
                getInstrumentation().addMonitor(ItemDetailsActivity.class.getName(),
                        null, false);

        // Start ItemDetailsActivity
        final ListView inventoryItemsList = displayInventoryActivity.getInventoryItemsList();
        Trinket trinket = (Trinket) inventoryItemsList.getItemAtPosition(0);
        displayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                View view = inventoryItemsList.getChildAt(0);
                inventoryItemsList.performItemClick(view, 0, view.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        ItemDetailsActivity itemDetailsActivity = (ItemDetailsActivity)
                itemDetailsActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", itemDetailsActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, itemDetailsActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                ItemDetailsActivity.class, itemDetailsActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(itemDetailsActivityMonitor);

        /******** AddOrEditItemActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor removeItemPictureActivityMonitor =
                getInstrumentation().addMonitor(AddOrEditItemActivity.class.getName(),
                        null, false);

        // Start DisplayInventoryActivity
        final Button editButton = itemDetailsActivity.getEditButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                editButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddOrEditItemActivity removeItemPictureActivity = (AddOrEditItemActivity)
                removeItemPictureActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", removeItemPictureActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, removeItemPictureActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddOrEditItemActivity.class, removeItemPictureActivity.getClass());

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
        Iterator<Trinket> noPicturesTrinketIterator = displayInventoryActivity.getInventory().iterator();
        while (noPicturesTrinketIterator.hasNext()) {
            ArrayList<Picture> pictures = noPicturesTrinketIterator.next().getPictures();
            assertEquals(noPicturesTrinketIterator.next().getPictures().size(), 0);
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

        // Start DisplayInventoryActivity
        final AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                emailTextView.setText("test@test.test");
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

        /******** DisplayUserProfileActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor displayUserProfileActivityMonitor =
                getInstrumentation().addMonitor(DisplayUserProfileActivity.class.getName(),
                        null, false);

        // Start DisplayInventoryActivity
        final Button profileButton = homePageActivity.getProfileButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                profileButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        DisplayUserProfileActivity displayUserProfileActivity = (DisplayUserProfileActivity)
                displayUserProfileActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", displayUserProfileActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, displayUserProfileActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                DisplayUserProfileActivity.class, displayUserProfileActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(displayUserProfileActivityMonitor);

        /******** EditProfileActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor editProfileActivityMonitor =
                getInstrumentation().addMonitor(EditProfileActivity.class.getName(),
                        null, false);

        // Start DisplayInventoryActivity
        final Button editUserProfileButton = displayUserProfileActivity.getEditUserProfileButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                editUserProfileButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        EditProfileActivity editProfileActivity = (EditProfileActivity)
                editProfileActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", editProfileActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, editProfileActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                EditProfileActivity.class, editProfileActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(editProfileActivityMonitor);

        UserProfile userProfile = editProfileActivity.getUserProfile();
        userProfile.setArePhotosDownloadable(Boolean.TRUE);
        assertTrue(userProfile.getArePhotosDownloadable());

        // Click the button
        final ToggleButton arePhotosDownloadableButton = editProfileActivity.getArePhotosDownloadableButton();
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
        editProfileActivity.finish();
        displayUserProfileActivity.finish();

        // TODO Need to try and manually download a photo here
        assertNotNull(null);

        // Close the activities
        editProfileActivity.finish();
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

        // Start DisplayInventoryActivity
        final AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                emailTextView.setText("test@test.test");
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

        /******** DisplayUserProfileActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor displayUserProfileActivityMonitor =
                getInstrumentation().addMonitor(DisplayUserProfileActivity.class.getName(),
                        null, false);

        // Start DisplayInventoryActivity
        final Button profileButton = homePageActivity.getProfileButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                profileButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        DisplayUserProfileActivity displayUserProfileActivity = (DisplayUserProfileActivity)
                displayUserProfileActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", displayUserProfileActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, displayUserProfileActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                DisplayUserProfileActivity.class, displayUserProfileActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(displayUserProfileActivityMonitor);

        /******** EditProfileActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor editProfileActivityMonitor =
                getInstrumentation().addMonitor(EditProfileActivity.class.getName(),
                        null, false);

        // Start DisplayInventoryActivity
        final Button editUserProfileButton = displayUserProfileActivity.getEditUserProfileButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                editUserProfileButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        EditProfileActivity editProfileActivity = (EditProfileActivity)
                editProfileActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", editProfileActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, editProfileActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                EditProfileActivity.class, editProfileActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(editProfileActivityMonitor);

        UserProfile userProfile = editProfileActivity.getUserProfile();
        userProfile.setArePhotosDownloadable(Boolean.TRUE);
        assertTrue(userProfile.getArePhotosDownloadable());

        // Click the button
        final ToggleButton arePhotosDownloadableButton = editProfileActivity.getArePhotosDownloadableButton();
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
        editProfileActivity.finish();
        displayUserProfileActivity.finish();

        // TODO Need to check here if the photos actually don't download
        assertNotNull(null);

        // Close the activities
        editProfileActivity.finish();
        displayUserProfileActivity.finish();
        homePageActivity.finish();
        loginActivity.finish();
    }
}
