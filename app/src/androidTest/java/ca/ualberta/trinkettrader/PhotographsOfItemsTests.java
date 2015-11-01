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
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ToggleButton;

public class PhotographsOfItemsTests extends ActivityInstrumentationTestCase2 {

    public PhotographsOfItemsTests(Class activityClass) {
        super(activityClass);
    }

    public void testAttachPhotograph(){
        // Get the current activity
        HomePageActivity homePageActivity = (HomePageActivity) getActivity();

        /******** DisplayInventoryActivity ********/
        {
            // Set up an ActivityMonitor
            Instrumentation.ActivityMonitor receiverActivityMonitor =
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

        // Get the new activity
        DisplayInventoryActivity displayInventoryActivity = (DisplayInventoryActivity) getActivity();

        /******** AddOrEditItemActivity ********/
        {
            // Set up an ActivityMonitor
            Instrumentation.ActivityMonitor receiverActivityMonitor =
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
            AddOrEditItemActivity receiverActivity = (AddOrEditItemActivity)
                    receiverActivityMonitor.waitForActivityWithTimeout(1000);
            assertNotNull("ReceiverActivity is null", receiverActivity);
            assertEquals("Monitor for ReceiverActivity has not been called",
                    1, receiverActivityMonitor.getHits());
            assertEquals("Activity is of wrong type",
                    AddOrEditItemActivity.class, receiverActivity.getClass());

            // Remove the ActivityMonitor
            getInstrumentation().removeMonitor(receiverActivityMonitor);
        }

        // Get the new activity
        AddOrEditItemActivity addOrEditItemActivity = (AddOrEditItemActivity) getActivity();

        /*
        Picture picture = new Picture("<path/to/photo>");
        Trinket trinket = new Trinket();
        trinket.attatchPhoto(photograph);
        assertTrue(trinket.photos.contains(photograph));
        */
    }

    public void testViewPhotograph(){
        /*
        // This looks like it has to be an activity test...do all use cases have to be in
        // 'model' tests when their functionality is better suited for the view?
        Trinket profile = new Trinket();
        Photograph photo = profile.getPhotographs("1");
        assertTrue(photo.isVisible());
        */
    }

    public void testConstrainPhotographSize(){
        /*
        // How do we validly test this? This use case is specific to a sysadmin.
        // Should a user extend that?
        Photograph photograph = new Photograph("<path/to/photo>");
        // How to check that photograph is within 65536?
        Trinket profile = new Trinket();
        trinket.attatchPhoto(photograph);
        assertTrue(trinket.photos.contains(photograph));
        assertTrue(photograph.getSize() <= 65536);
        */
    }

    public void testDeletePhotoGraph(){
        /*
        Photograph photograph = new Photograph("<path/to/photo>");
        Trinket trinket = new Trinket();
        trinket.attatchPhoto(photograph);
        assertTrue(trinket.photos.contains(photograph));
        trinket.deletePhoto(photograph);
        assertFalse(trinket.photos.contains(photograph));
        */
    }

    public void testManuallyChoosePhotosToDownloadIfPhotoDownloadDisabled(){
        /*
        //This is another test that looks like it should be an activity test.
        // Changes will be made once those tests are underway.
        //  Assert that default photo download is disabled
        User user = new User();
        UserSettings settings = user.getUserSettings();
        assertFalse(settings.arePhotosDownloadable);
        // Select photos to download
        */
    }

    public void testDisablePhotoDownload(){
        // Get the current activity
        HomePageActivity homePageActivity = (HomePageActivity) getActivity();


        /******** DisplayUserProfileActivity ********/
        {
            // Set up an ActivityMonitor
            Instrumentation.ActivityMonitor receiverActivityMonitor =
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
            DisplayUserProfileActivity receiverActivity = (DisplayUserProfileActivity)
                    receiverActivityMonitor.waitForActivityWithTimeout(1000);
            assertNotNull("ReceiverActivity is null", receiverActivity);
            assertEquals("Monitor for ReceiverActivity has not been called",
                    1, receiverActivityMonitor.getHits());
            assertEquals("Activity is of wrong type",
                    DisplayUserProfileActivity.class, receiverActivity.getClass());

            // Remove the ActivityMonitor
            getInstrumentation().removeMonitor(receiverActivityMonitor);
        }

        // Get the new activity
        DisplayUserProfileActivity displayUserProfileActivity = (DisplayUserProfileActivity) getActivity();

        /******** EditProfileActivity ********/
        {
            // Set up an ActivityMonitor
            Instrumentation.ActivityMonitor receiverActivityMonitor =
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
            EditProfileActivity receiverActivity = (EditProfileActivity)
                    receiverActivityMonitor.waitForActivityWithTimeout(1000);
            assertNotNull("ReceiverActivity is null", receiverActivity);
            assertEquals("Monitor for ReceiverActivity has not been called",
                    1, receiverActivityMonitor.getHits());
            assertEquals("Activity is of wrong type",
                    EditProfileActivity.class, receiverActivity.getClass());

            // Remove the ActivityMonitor
            getInstrumentation().removeMonitor(receiverActivityMonitor);
        }

        // Get the new activity
        EditProfileActivity editProfileActivity = (EditProfileActivity) getActivity();

        /******** Disable Photo Downloads ********/
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
    }
}
