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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class ConfigurationTests extends ActivityInstrumentationTestCase2 {

    public ConfigurationTests(Class activityClass) {
        super(activityClass);
    }

    User user;
    UserProfile profile;
    Instrumentation instrumentation;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        user = new User();
        profile = user.getUserProfile();
        instrumentation = getInstrumentation();
    }

    public void testEditProfile() {
        profile.setName("Binky");
        profile.setPostalCode("T6W 1K8");
        assertTrue(profile.getName().equals("Binky"));
        assertTrue(profile.getPostalCode().equals("T6W 1K8"));

        //TODO: assert that we are dealing with Binky's homepage and therefore his profile
       //TODO: check preconditions

        //Set an activity monitor for DisplayFriendsActivity
        Instrumentation.ActivityMonitor homePageMonitor = instrumentation.addMonitor(HomePageActivity.class.getName(), null, false);

        //Start the activity that we set the monitor for
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(instrumentation.getTargetContext(), HomePageActivity.class.getName());
        instrumentation.startActivitySync(intent);

        //Wait for HomePageActivity to start
        HomePageActivity homePageActivity = (HomePageActivity) getInstrumentation().waitForMonitorWithTimeout(homePageMonitor, 5);
        assertNotNull(homePageActivity);

        //Set an activity monitor for ProfilePageActivity
        Instrumentation.ActivityMonitor profilePageMonitor = instrumentation.addMonitor(DisplayUserProfileActivity.class.getName(), null, false);

        //Click the 'My Profile' button
        Button myProfile = (Button) homePageActivity.findViewById(R.id.profile_button);
        assertNotNull(myProfile);
        assertEquals("View not a button", Button.class, myProfile.getClass());
        TouchUtils.clickView(this, myProfile);

        //Assert that DisplayUserProfileActivity starts up
        DisplayUserProfileActivity profileActivity = (DisplayUserProfileActivity) profilePageMonitor.waitForActivityWithTimeout(5);
        assertNotNull("Inventory Activity is null", profileActivity);
        assertEquals("Inventory activity has not been called", 1, profilePageMonitor.getHits());
        assertEquals("Activity of wrong type", DisplayUserProfileActivity.class, profileActivity.getClass());

        //Set an activity monitor for EditProfilePageActivity
        Instrumentation.ActivityMonitor editProfileMonitor = instrumentation.addMonitor(EditProfileActivity.class.getName(), null, false);

        //Click the 'My Profile' button
        Button editButton = (Button) profileActivity.findViewById(R.id.edit_button);
        assertNotNull(editButton);
        assertEquals("View not a button", Button.class, editButton.getClass());
        TouchUtils.clickView(this, editButton);

        //Assert that EditUserProfileActivity starts up
        EditProfileActivity editProfileActivity = (EditProfileActivity) editProfileMonitor.waitForActivityWithTimeout(5);
        assertNotNull("Inventory Activity is null", editProfileActivity);
        assertEquals("Inventory activity has not been called", 1, profilePageMonitor.getHits());
        assertEquals("Activity of wrong type", EditProfileActivity.class, editProfileActivity.getClass());

        //Fix the name
        EditText fixName = (EditText) editProfileActivity.findViewById(R.id.edit_name);
        fixName.setText("Bonky");

        //Fix the postal code
        EditText fixPC = (EditText) editProfileActivity.findViewById(R.id.edit_postal_code);
        fixPC.setText("T6W 1J5");

        //Set an activity monitor for ProfilePageActivity
        instrumentation.removeMonitor(profilePageMonitor);
        Instrumentation.ActivityMonitor refreshedProfileMonitor = instrumentation.addMonitor(DisplayUserProfileActivity.class.getName(), null, false);

        //Click 'Save' button
        Button save = (Button) editProfileActivity.findViewById(R.id.save_button);
        assertNotNull(save);
        assertEquals("View not a button", Button.class, save.getClass());
        TouchUtils.clickView(this, save);

        //Assert that DisplayUserProfileActivity starts up
        DisplayUserProfileActivity refreshedProfileActivity = (DisplayUserProfileActivity) refreshedProfileMonitor.waitForActivityWithTimeout(5);
        assertNotNull("Inventory Activity is null", refreshedProfileActivity);
        assertEquals("Inventory activity has not been called", 1, refreshedProfileMonitor.getHits());
        assertEquals("Activity of wrong type", DisplayUserProfileActivity.class, refreshedProfileActivity.getClass());

        TextView name = (TextView) refreshedProfileActivity.findViewById(R.id.name);
        assertEquals(name.getText(), "Bonky");

        TextView postalCode = (TextView) refreshedProfileActivity.findViewById(R.id.postal_code);
        assertEquals(postalCode.getText(), "T6W 1J5");

    }


    public void testEnablePhotoDownload() {
        profile.setArePhotosDownloadable(Boolean.FALSE);
        assertFalse(profile.getArePhotosDownloadable());

        //Set an activity monitor for DisplayFriendsActivity
        Instrumentation.ActivityMonitor homePageMonitor = instrumentation.addMonitor(HomePageActivity.class.getName(), null, false);

        //Start the activity that we set the monitor for
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(instrumentation.getTargetContext(), HomePageActivity.class.getName());
        instrumentation.startActivitySync(intent);

        //Wait for HomePageActivity to start
        HomePageActivity homePageActivity = (HomePageActivity) getInstrumentation().waitForMonitorWithTimeout(homePageMonitor, 5);
        assertNotNull(homePageActivity);

        //Set an activity monitor for ProfilePageActivity
        Instrumentation.ActivityMonitor profilePageMonitor = instrumentation.addMonitor(DisplayUserProfileActivity.class.getName(), null, false);

        //Click the 'My Profile' button
        Button myProfile = (Button) homePageActivity.findViewById(R.id.profile_button);
        assertNotNull(myProfile);
        assertEquals("View not a button", Button.class, myProfile.getClass());
        TouchUtils.clickView(this, myProfile);

        //Assert that DisplayUserProfileActivity starts up
        DisplayUserProfileActivity profileActivity = (DisplayUserProfileActivity) profilePageMonitor.waitForActivityWithTimeout(5);
        assertNotNull("Inventory Activity is null", profileActivity);
        assertEquals("Inventory activity has not been called", 1, profilePageMonitor.getHits());
        assertEquals("Activity of wrong type", DisplayUserProfileActivity.class, profileActivity.getClass());

        //Check that photo download is disabled
        TextView setting = (TextView) profileActivity.findViewById(R.id.photo_download_setting);
        assertEquals(setting.getText(), "Photo Download: Disabled");

        //Set an activity monitor for EditProfilePageActivity
        Instrumentation.ActivityMonitor editProfileMonitor = instrumentation.addMonitor(EditProfileActivity.class.getName(), null, false);

        //Click the 'Edit' button
        Button editButton = (Button) profileActivity.findViewById(R.id.edit_button);
        assertNotNull(editButton);
        assertEquals("View not a button", Button.class, editButton.getClass());
        TouchUtils.clickView(this, editButton);

        //Assert that EditUserProfileActivity starts up
        EditProfileActivity editProfileActivity = (EditProfileActivity) editProfileMonitor.waitForActivityWithTimeout(5);
        assertNotNull("Inventory Activity is null", editProfileActivity);
        assertEquals("Inventory activity has not been called", 1, profilePageMonitor.getHits());
        assertEquals("Activity of wrong type", EditProfileActivity.class, editProfileActivity.getClass());


        //Select photos as downloadable.
        CheckBox photoDownload = (CheckBox) editProfileActivity.findViewById(R.id.photo_download_checkbox);
        assertFalse(photoDownload.isChecked());
        photoDownload.setChecked(Boolean.TRUE);

        //Set an activity monitor for ProfilePageActivity
        instrumentation.removeMonitor(profilePageMonitor);
        Instrumentation.ActivityMonitor refreshedProfileMonitor = instrumentation.addMonitor(DisplayUserProfileActivity.class.getName(), null, false);

        //Click 'Save' button
        Button save = (Button) editProfileActivity.findViewById(R.id.save_button);
        assertNotNull(save);
        assertEquals("View not a button", Button.class, save.getClass());
        TouchUtils.clickView(this, save);

        //Assert that DisplayUserProfileActivity starts up
        DisplayUserProfileActivity refreshedProfileActivity = (DisplayUserProfileActivity) refreshedProfileMonitor.waitForActivityWithTimeout(5);
        assertNotNull("Inventory Activity is null", refreshedProfileActivity);
        assertEquals("Inventory activity has not been called", 1, refreshedProfileMonitor.getHits());
        assertEquals("Activity of wrong type", DisplayUserProfileActivity.class, refreshedProfileActivity.getClass());


        //Check that photo download is disabled
        TextView newSetting = (TextView) refreshedProfileActivity.findViewById(R.id.photo_download_setting);
        assertEquals(newSetting.getText(), "Photo Download: Enabled");
        assertTrue(profile.getArePhotosDownloadable());
    }

    public void testDisablePhotoDownload() {
        profile.setArePhotosDownloadable(Boolean.TRUE);
        assertTrue(profile.getArePhotosDownloadable());

        //Set an activity monitor for DisplayFriendsActivity
        Instrumentation.ActivityMonitor homePageMonitor = instrumentation.addMonitor(HomePageActivity.class.getName(), null, false);

        //Start the activity that we set the monitor for
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(instrumentation.getTargetContext(), HomePageActivity.class.getName());
        instrumentation.startActivitySync(intent);

        //Wait for HomePageActivity to start
        HomePageActivity homePageActivity = (HomePageActivity) getInstrumentation().waitForMonitorWithTimeout(homePageMonitor, 5);
        assertNotNull(homePageActivity);

        //Set an activity monitor for ProfilePageActivity
        Instrumentation.ActivityMonitor profilePageMonitor = instrumentation.addMonitor(DisplayUserProfileActivity.class.getName(), null, false);

        //Click the 'My Profile' button
        Button myProfile = (Button) homePageActivity.findViewById(R.id.profile_button);
        assertNotNull(myProfile);
        assertEquals("View not a button", Button.class, myProfile.getClass());
        TouchUtils.clickView(this, myProfile);

        //Assert that DisplayUserProfileActivity starts up
        DisplayUserProfileActivity profileActivity = (DisplayUserProfileActivity) profilePageMonitor.waitForActivityWithTimeout(5);
        assertNotNull("Inventory Activity is null", profileActivity);
        assertEquals("Inventory activity has not been called", 1, profilePageMonitor.getHits());
        assertEquals("Activity of wrong type", DisplayUserProfileActivity.class, profileActivity.getClass());

        //Check that photo download is disabled
        TextView setting = (TextView) profileActivity.findViewById(R.id.photo_download_setting);
        assertEquals(setting.getText(), "Photo Download: Enabled");

        //Set an activity monitor for EditProfilePageActivity
        Instrumentation.ActivityMonitor editProfileMonitor = instrumentation.addMonitor(EditProfileActivity.class.getName(), null, false);

        //Click the 'Edit' button
        Button editButton = (Button) profileActivity.findViewById(R.id.edit_button);
        assertNotNull(editButton);
        assertEquals("View not a button", Button.class, editButton.getClass());
        TouchUtils.clickView(this, editButton);

        //Assert that EditUserProfileActivity starts up
        EditProfileActivity editProfileActivity = (EditProfileActivity) editProfileMonitor.waitForActivityWithTimeout(5);
        assertNotNull("Inventory Activity is null", editProfileActivity);
        assertEquals("Inventory activity has not been called", 1, profilePageMonitor.getHits());
        assertEquals("Activity of wrong type", EditProfileActivity.class, editProfileActivity.getClass());


        //Select photos as downloadable.
        CheckBox photoDownload = (CheckBox) editProfileActivity.findViewById(R.id.photo_download_checkbox);
        assertTrue(photoDownload.isChecked());
        photoDownload.setChecked(Boolean.FALSE);

        //Set an activity monitor for ProfilePageActivity
        instrumentation.removeMonitor(profilePageMonitor);
        Instrumentation.ActivityMonitor refreshedProfileMonitor = instrumentation.addMonitor(DisplayUserProfileActivity.class.getName(), null, false);

        //Click 'Save' button
        Button save = (Button) editProfileActivity.findViewById(R.id.save_button);
        assertNotNull(save);
        assertEquals("View not a button", Button.class, save.getClass());
        TouchUtils.clickView(this, save);

        //Assert that DisplayUserProfileActivity starts up
        DisplayUserProfileActivity refreshedProfileActivity = (DisplayUserProfileActivity) refreshedProfileMonitor.waitForActivityWithTimeout(5);
        assertNotNull("Inventory Activity is null", refreshedProfileActivity);
        assertEquals("Inventory activity has not been called", 1, refreshedProfileMonitor.getHits());
        assertEquals("Activity of wrong type", DisplayUserProfileActivity.class, refreshedProfileActivity.getClass());


        //Check that photo download is disabled
        TextView newSetting = (TextView) refreshedProfileActivity.findViewById(R.id.photo_download_setting);
        assertEquals(newSetting.getText(), "Photo Download: Disabled");
        assertFalse(profile.getArePhotosDownloadable());

    }
}
