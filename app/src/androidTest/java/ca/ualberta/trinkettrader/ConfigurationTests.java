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
import android.support.v7.widget.AppCompatButton;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class ConfigurationTests extends ActivityInstrumentationTestCase2 {

    public ConfigurationTests() {
        super(LoginActivity.class);
    }

    User user;
    UserProfile profile;
    Instrumentation instrumentation;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        user = LoggedInUser.getInstance();

        profile = user.getProfile();
        instrumentation = getInstrumentation();
        setActivityInitialTouchMode(true);
    }

    public void testEditProfile() {
        profile.setName("Binky");
        profile.setPostalCode("T6W 1K8");
        assertTrue(profile.getName().equals("Binky"));
        assertTrue(profile.getPostalCode().equals("T6W 1K8"));

        //TODO: assert that we are dealing with Binky's homepage and therefore his profile
       //TODO: check preconditions

        // Start the UI test from the login page (beginning of the app).
        LoginActivity loginActivity = (LoginActivity) getActivity();

        // On the login page: click the email input box and write an arbitrary email.
        // Test that the text was successfully written.
        final AutoCompleteTextView loginEmailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                loginEmailTextView.performClick();
                loginEmailTextView.setText("user@gmail.com");
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(loginEmailTextView.getText().toString().equals("user@gmail.com"));

        // Click the login button to proceed to the home page.
        final Button loginButton = loginActivity.getLoginButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                loginButton.performClick();
            }
        });

        // Test that the HomePageActivity started correctly after the clicking the login button.
        Instrumentation.ActivityMonitor homePageActivityMonitor = getInstrumentation().addMonitor(HomePageActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        HomePageActivity homePageActivity = (HomePageActivity) homePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("HomePageActivity is null", homePageActivity);
        assertEquals("Activity is of wrong type; expected HomePageActivity", HomePageActivity.class, homePageActivity.getClass());


        //Click the 'My Profile' button
        final Button myProfile = (Button) homePageActivity.findViewById(R.id.profile_button);
        assertNotNull(myProfile);
        assertEquals("View not a button", AppCompatButton.class, myProfile.getClass());
        homePageActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myProfile.performClick();
            }
        });
        //Set an activity monitor for ProfilePageActivity
        Instrumentation.ActivityMonitor profilePageMonitor = instrumentation.addMonitor(DisplayUserProfileActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();


        //Assert that DisplayUserProfileActivity starts up
        DisplayUserProfileActivity profileActivity = (DisplayUserProfileActivity) profilePageMonitor.waitForActivityWithTimeout(5);
        assertNotNull("Profile Activity is null", profileActivity);
        assertEquals("Activity of wrong type", DisplayUserProfileActivity.class, profileActivity.getClass());

        //Click the 'Edit' button
        final Button editButton = (Button) profileActivity.findViewById(R.id.edit_button);
        assertNotNull(editButton);
        assertEquals("View not a button", AppCompatButton.class, editButton.getClass());
        profileActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                editButton.performClick();
            }
        });

        //Set an activity monitor for EditProfilePageActivity
        Instrumentation.ActivityMonitor editProfileMonitor = instrumentation.addMonitor(EditProfileActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();

        //Assert that EditUserProfileActivity starts up
        EditProfileActivity editProfileActivity = (EditProfileActivity) editProfileMonitor.waitForActivityWithTimeout(5);
        assertNotNull("Inventory Activity is null", editProfileActivity);
        assertEquals("Activity of wrong type", EditProfileActivity.class, editProfileActivity.getClass());

        //Fix the name
        final EditText fixName = (EditText) editProfileActivity.findViewById(R.id.edit_name);
        editProfileActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fixName.setText("Bonky");
            }
        });

        //Fix the postal code
        final EditText fixPC = (EditText) editProfileActivity.findViewById(R.id.edit_postal_code);
        editProfileActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fixPC.setText("T6W 1J5");
            }
        });


        //Click 'Save' button
        final Button save = (Button) editProfileActivity.findViewById(R.id.save_button);
        assertNotNull(save);
        assertEquals("View not a button", AppCompatButton.class, save.getClass());
        editProfileActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                save.performClick();
            }
        });


        //Set an activity monitor for ProfilePageActivity
        instrumentation.removeMonitor(profilePageMonitor);
        Instrumentation.ActivityMonitor refreshedProfileMonitor = instrumentation.addMonitor(DisplayUserProfileActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();

        //Assert that DisplayUserProfileActivity starts up
        DisplayUserProfileActivity refreshedProfileActivity = (DisplayUserProfileActivity) refreshedProfileMonitor.waitForActivityWithTimeout(5);
        assertNotNull("Profile Activity is null", refreshedProfileActivity);
        assertEquals("Activity of wrong type", DisplayUserProfileActivity.class, refreshedProfileActivity.getClass());

        assertTrue(LoggedInUser.getInstance().getNeedToSave());

        TextView name = (TextView) refreshedProfileActivity.findViewById(R.id.name);
        assertEquals(name.getText().toString(), "Bonky");

        TextView postalCode = (TextView) refreshedProfileActivity.findViewById(R.id.postal_code);
        assertEquals(postalCode.getText().toString(), "T6W 1J5");


    }

    public void testEnablePhotoDownload() {
        profile.setArePhotosDownloadable(Boolean.FALSE);
        assertFalse(profile.getArePhotosDownloadable());

        // Start the UI test from the login page (beginning of the app).
        LoginActivity loginActivity = (LoginActivity) getActivity();

        // On the login page: click the email input box and write an arbitrary email.
        // Test that the text was successfully written.
        final AutoCompleteTextView loginEmailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                loginEmailTextView.performClick();
                loginEmailTextView.setText("user@gmail.com");
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(loginEmailTextView.getText().toString().equals("user@gmail.com"));

        // Click the login button to proceed to the home page.
        final Button loginButton = loginActivity.getLoginButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                loginButton.performClick();
            }
        });

        // Test that the HomePageActivity started correctly after the clicking the login button.
        Instrumentation.ActivityMonitor homePageActivityMonitor = getInstrumentation().addMonitor(HomePageActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();

        HomePageActivity homePageActivity = (HomePageActivity) homePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("HomePageActivity is null", homePageActivity);
        assertEquals("Activity is of wrong type; expected HomePageActivity", HomePageActivity.class, homePageActivity.getClass());

        //Click the 'My Profile' button
        final Button myProfile = (Button) homePageActivity.findViewById(R.id.profile_button);
        assertNotNull(myProfile);
        assertEquals("View not a button", AppCompatButton.class, myProfile.getClass());
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                myProfile.performClick();
            }
        });

        //Set an activity monitor for ProfilePageActivity
        Instrumentation.ActivityMonitor profilePageMonitor = instrumentation.addMonitor(DisplayUserProfileActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();

        //Assert that DisplayUserProfileActivity starts up
        DisplayUserProfileActivity profileActivity = (DisplayUserProfileActivity) profilePageMonitor.waitForActivityWithTimeout(5);
        assertNotNull("Profile Activity is null", profileActivity);
        assertEquals("Activity of wrong type", DisplayUserProfileActivity.class, profileActivity.getClass());

        //Check that photo download is disabled
        TextView setting = (TextView) profileActivity.findViewById(R.id.photo_download_setting);
        assertEquals(setting.getText(), "Photo Download: Disabled");

        //Click the 'Edit' button
        final Button editButton = (Button) profileActivity.findViewById(R.id.edit_button);
        assertNotNull(editButton);
        assertEquals("View not a button", AppCompatButton.class, editButton.getClass());
        profileActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                editButton.performClick();
            }
        });

        //Set an activity monitor for EditProfilePageActivity
        Instrumentation.ActivityMonitor editProfileMonitor = instrumentation.addMonitor(EditProfileActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();

        //Assert that EditUserProfileActivity starts up
        EditProfileActivity editProfileActivity = (EditProfileActivity) editProfileMonitor.waitForActivityWithTimeout(5);
        assertNotNull("EditProfile Activity is null", editProfileActivity);
        assertEquals("Activity of wrong type", EditProfileActivity.class, editProfileActivity.getClass());


        //Select photos as downloadable.
        final CheckBox photoDownload = (CheckBox) editProfileActivity.findViewById(R.id.photo_download_checkbox);
        assertFalse(photoDownload.isChecked());
        editProfileActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                photoDownload.setChecked(Boolean.TRUE);
            }
        });

        //Click 'Save' button
        final Button save = (Button) editProfileActivity.findViewById(R.id.save_button);
        assertNotNull(save);
        assertEquals("View not a button", AppCompatButton.class, save.getClass());
        editProfileActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                save.performClick();
            }
        });

        //Set an activity monitor for ProfilePageActivity
        instrumentation.removeMonitor(profilePageMonitor);
        Instrumentation.ActivityMonitor refreshedProfileMonitor = instrumentation.addMonitor(DisplayUserProfileActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();

        //Assert that DisplayUserProfileActivity starts up
        DisplayUserProfileActivity refreshedProfileActivity = (DisplayUserProfileActivity) refreshedProfileMonitor.waitForActivityWithTimeout(5);
        assertNotNull("Profile Activity is null", refreshedProfileActivity);
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
        assertEquals("View not a button", AppCompatButton.class, myProfile.getClass());
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
        assertEquals("View not a button", AppCompatButton.class, editButton.getClass());
        TouchUtils.clickView(this, editButton);

        //Assert that EditUserProfileActivity starts up
        EditProfileActivity editProfileActivity = (EditProfileActivity) editProfileMonitor.waitForActivityWithTimeout(5);
        assertNotNull("Inventory Activity is null", editProfileActivity);
        assertEquals("Inventory activity has not been called", 1, profilePageMonitor.getHits());
        assertEquals("Activity of wrong type", EditProfileActivity.class, editProfileActivity.getClass());


        //Select photos as downloadable.
        final CheckBox photoDownload = (CheckBox) editProfileActivity.findViewById(R.id.photo_download_checkbox);
        editProfileActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                photoDownload.setChecked(Boolean.FALSE);
            }
        });
        assertFalse("Photo download should not be checked", photoDownload.isChecked());

        //Set an activity monitor for ProfilePageActivity
        instrumentation.removeMonitor(profilePageMonitor);
        Instrumentation.ActivityMonitor refreshedProfileMonitor = instrumentation.addMonitor(DisplayUserProfileActivity.class.getName(), null, false);

        //Click 'Save' button
        Button save = (Button) editProfileActivity.findViewById(R.id.save_button);
        assertNotNull(save);
        assertEquals("View not a button", AppCompatButton.class, save.getClass());
        TouchUtils.clickView(this, save);

        //Assert that DisplayUserProfileActivity starts up
        DisplayUserProfileActivity refreshedProfileActivity = (DisplayUserProfileActivity) refreshedProfileMonitor.waitForActivityWithTimeout(5);
        assertNotNull("Inventory Activity is null", refreshedProfileActivity);
        assertEquals("Activity of wrong type", DisplayUserProfileActivity.class, refreshedProfileActivity.getClass());


        //Check that photo download is disabled
        TextView newSetting = (TextView) refreshedProfileActivity.findViewById(R.id.photo_download_setting);
        assertEquals(newSetting.getText(), "Photo Download: Disabled");
        assertFalse(profile.getArePhotosDownloadable());

    }
}
