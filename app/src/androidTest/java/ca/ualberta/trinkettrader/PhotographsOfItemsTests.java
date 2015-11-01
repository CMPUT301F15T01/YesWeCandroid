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
import android.view.View;
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

    public PhotographsOfItemsTests(Class activityClass) {
        super(activityClass);
    }

    public void testAttachPhotograph() {
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

        /** HERE BE DRAGONS **/

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
        displayInventoryActivity.finish();
        homePageActivity.finish();
    }

    public void testViewPhotograph() {
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

        /** HERE BE DRAGONS **/

        // Save the item
        final Button saveItemButton = addOrEditItemActivity.getSaveButton();
        addOrEditItemActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveItemButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();


        /******** ItemDetailsActivity ********/
        {
            // Set up an ActivityMonitor
            Instrumentation.ActivityMonitor receiverActivityMonitor =
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
            ItemDetailsActivity receiverActivity = (ItemDetailsActivity)
                    receiverActivityMonitor.waitForActivityWithTimeout(1000);
            assertNotNull("ReceiverActivity is null", receiverActivity);
            assertEquals("Monitor for ReceiverActivity has not been called",
                    1, receiverActivityMonitor.getHits());
            assertEquals("Activity is of wrong type",
                    ItemDetailsActivity.class, receiverActivity.getClass());

            // Remove the ActivityMonitor
            getInstrumentation().removeMonitor(receiverActivityMonitor);
        }

        // Get the new activity
        ItemDetailsActivity itemDetailsActivity = (ItemDetailsActivity) getActivity();


        // Check that the image is visible
        ImageView itemImage = itemDetailsActivity.getItemImage();
        // PC.; http://stackoverflow.com/questions/9113895/how-to-check-if-an-imageview-is-attached-with-image-in-android; 2015-11-01
        assertNotNull(itemImage.getDrawable());

        // Close the activities
        itemDetailsActivity.finish();
        displayInventoryActivity.finish();
        homePageActivity.finish();
    }

    public void testConstrainPhotographSize() {
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

    public void testDeletePhotoGraph() {
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

        /** HERE BE DRAGONS **/

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
        {
            // Set up an ActivityMonitor
            Instrumentation.ActivityMonitor receiverActivityMonitor =
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
            ItemDetailsActivity receiverActivity = (ItemDetailsActivity)
                    receiverActivityMonitor.waitForActivityWithTimeout(1000);
            assertNotNull("ReceiverActivity is null", receiverActivity);
            assertEquals("Monitor for ReceiverActivity has not been called",
                    1, receiverActivityMonitor.getHits());
            assertEquals("Activity is of wrong type",
                    ItemDetailsActivity.class, receiverActivity.getClass());

            // Remove the ActivityMonitor
            getInstrumentation().removeMonitor(receiverActivityMonitor);
        }

        // Get the new activity
        ItemDetailsActivity itemDetailsActivity = (ItemDetailsActivity) getActivity();


        /******** AddOrEditItemActivity ********/
        {
            // Set up an ActivityMonitor
            Instrumentation.ActivityMonitor receiverActivityMonitor =
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
        AddOrEditItemActivity removeItemPictureActivity = (AddOrEditItemActivity) getActivity();


        /******** TestDeleteImage ********/
        {
            // Delete the image
            final Button removeImageButton = removeItemPictureActivity.getRemoveImageButton();
            removeItemPictureActivity.runOnUiThread(new Runnable() {
                public void run() {
                    removeImageButton.performClick();
                }
            });
            getInstrumentation().waitForIdleSync();

            // Save the item
            final Button saveItemButton = addOrEditItemActivity.getSaveButton();
            addOrEditItemActivity.runOnUiThread(new Runnable() {
                public void run() {
                    saveItemButton.performClick();
                }
            });
            getInstrumentation().waitForIdleSync();
            itemDetailsActivity.finish();

            // Make sure the item does not have an image
            Iterator<Trinket> trinketIterator = displayInventoryActivity.getInventory().iterator();
            while (trinketIterator.hasNext()) {
                ArrayList<Picture> pictures = trinketIterator.next().getPictures();
                assertEquals(trinketIterator.next().getPictures().size(), 0);
            }
        }

        // Close the activities
        displayInventoryActivity.finish();
        homePageActivity.finish();
    }

    public void testManuallyChoosePhotosToDownloadIfPhotoDownloadDisabled() {
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

    public void testDisablePhotoDownload() {
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

        // Return to main menu
        editProfileActivity.finish();
        displayUserProfileActivity.finish();
    }
}
