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
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import java.io.IOException;

import ca.ualberta.trinkettrader.Friends.FriendsListActivity;
import ca.ualberta.trinkettrader.Friends.FriendsProfileActivity;
import ca.ualberta.trinkettrader.Friends.TrackedFriends.TrackedFriendsListActivity;
import ca.ualberta.trinkettrader.User.LoggedInUser;
import ca.ualberta.trinkettrader.User.Profile.UserProfileActivity;

public class FriendsTests extends ActivityInstrumentationTestCase2<LoginActivity> {

    // https://developer.android.com/training/activity-testing/activity-functional-testing.html; 2015-11-03

    private LoginActivity loginActivity;
    private HomePageActivity homePageActivity;
    private Instrumentation.ActivityMonitor homePageActivityMonitor;
    private AutoCompleteTextView loginEmailTextView;
    private Button loginButton;
    private ListView friendsList;
    private Button friendsButton;
    private Button findFriendsButton;
    private Button removeFriendButton;
    private Button backButton;
    private Button profileButton;
    private Button viewTrackedFriendsButton;
    private RadioButton trackFriendButton;
    private EditText findFriendTextField;


    public FriendsTests() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Start the UI tests from the login page (beginning of the app).
        loginActivity = getActivity();

        // On the login page: click the email input box and write an arbitrary email.
        // Test that the text was successfully written.
        loginEmailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                loginEmailTextView.performClick();
                loginEmailTextView.setText("user@gmail.com");
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(loginEmailTextView.getText().toString().equals("user@gmail.com"));

        // Click the login button to proceed to the home page.
        loginButton = loginActivity.getLoginButton();
        assertNotNull(loginButton);
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                loginButton.performClick();
            }
        });

        //try {
        //    LoggedInUser.getInstance().deleteFromNetwork();
        //    LoggedInUser.getInstance().saveToNetwork();
        //}
        //catch (IOException e) {
//
//        }

        // Test that the HomePageActivity started correctly after the clicking the login button.
        homePageActivityMonitor = getInstrumentation().addMonitor(HomePageActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        homePageActivity = (HomePageActivity) homePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("HomePageActivity is null", homePageActivity);
        assertEquals("Monitor for HomePageActivity has not been called", 1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected HomePageActivity", HomePageActivity.class, homePageActivity.getClass());
        getInstrumentation().removeMonitor(homePageActivityMonitor);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        // De-initialize (delete) all the button, view, and activity variables for re-use in other tests.
        // The LoginActivity and HomePageActivity are explicitly finished in each test.
        loginActivity = null;
        homePageActivity = null;
        homePageActivityMonitor = null;
        loginEmailTextView = null;
        loginButton = null;
        friendsList = null;
        friendsButton = null;
        findFriendsButton = null;
        removeFriendButton = null;
        backButton = null;
        profileButton = null;
        viewTrackedFriendsButton = null;
        trackFriendButton = null;
        findFriendTextField = null;

        try {
            LoggedInUser.getInstance().deleteFromNetwork();
        }
        catch (IOException e) {

        }
    }


    // UI test for adding a friend. User Story: US02.02.01. Use Case: AddFriend.
    public void testAddFriendUI() {

        // The UI is currently on the Home Page. It has already logged in (see setUp() method).
        // Click the friends button to proceed to the friends page.
        friendsButton = homePageActivity.getFriendsButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                friendsButton.performClick();
            }
        });

        // Test that the FriendsListActivity started correctly after the clicking the friends button.
        Instrumentation.ActivityMonitor friendsListActivityMonitor = getInstrumentation().addMonitor(FriendsListActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        FriendsListActivity friendsListActivity = (FriendsListActivity) friendsListActivityMonitor.waitForActivityWithTimeout(5000);
        assertNotNull("FriendsListActivity is null", friendsListActivity);
        assertEquals("Monitor for FriendsListActivity has not been called", 1, friendsListActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected FriendsListActivity", FriendsListActivity.class, friendsListActivity.getClass());
        getInstrumentation().removeMonitor(friendsListActivityMonitor);

        // On the friend-list page: click the Find Friend input box and write an arbitrary email.
        // Test that the text was successfully written.
        findFriendTextField = friendsListActivity.getFindFriendTextField();
        friendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
        //        findFriendTextField.performClick();
                findFriendTextField.setText("test@gmail.com");
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(findFriendTextField.getText().toString().equals("test@gmail.com"));


        // Click the Find Friend button to add the friend to the friend list.
        // Test that the friend was successfully added.
        findFriendsButton = friendsListActivity.getFindFriendsButton();
        friendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                findFriendsButton.performClick();
                int i = 0;
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(LoggedInUser.getInstance().getFriendsList().get(0).getActualFriend().getProfile().getEmail().equals("test@gmail.com"));

        // Click the newly-added friend to proceed to the friend's profile, confirming that the friend was added.
        friendsList = friendsListActivity.getFriendsListView();
        friendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                View firstFriend = friendsList.getChildAt(0);
                friendsList.performItemClick(firstFriend, 0, firstFriend.getId());
            }
        });

        // Test that the FriendsProfileActivity started correctly after the clicking the Friend in the list view.
        Instrumentation.ActivityMonitor friendsProfileActivityMonitor = getInstrumentation().addMonitor(FriendsProfileActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        FriendsProfileActivity friendsProfileActivity = (FriendsProfileActivity) friendsProfileActivityMonitor.waitForActivityWithTimeout(5000);
        assertNotNull("FriendsProfileActivity is null", friendsProfileActivity);
        assertEquals("Monitor for FriendsProfileActivity has not been called", 1, friendsProfileActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected FriendsProfileActivity", FriendsProfileActivity.class, friendsProfileActivity.getClass());
        getInstrumentation().removeMonitor(friendsProfileActivityMonitor);

        // The tests have completed; clear the friends lists and finish all activities (cleanup).
        LoggedInUser.getInstance().getFriendsList().clear();
        loginActivity.finish();
        homePageActivity.finish();
        friendsListActivity.finish();
        friendsProfileActivity.finish();
    }


    // UI test for removing a friend. User Story: US02.03.01. Use Case: RemoveFriend.
    public void testRemoveFriendUI() {

        // The UI is currently on the Home Page. It has already logged in (see setUp() method).
        // Click the friends button to proceed to the friends page.
        friendsButton = homePageActivity.getFriendsButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                friendsButton.performClick();
            }
        });

        // Test that the FriendsListActivity started correctly after the clicking the friends button.
        Instrumentation.ActivityMonitor friendsListActivityMonitor = getInstrumentation().addMonitor(FriendsListActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        FriendsListActivity friendsListActivity = (FriendsListActivity) friendsListActivityMonitor.waitForActivityWithTimeout(5000);
        assertNotNull("FriendsListActivity is null", friendsListActivity);
        assertEquals("Monitor for FriendsListActivity has not been called", 1, friendsListActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected FriendsListActivity", FriendsListActivity.class, friendsListActivity.getClass());

        // On the friend-list page: click the Find Friend input box and write an arbitrary email.
        // Test that the text was successfully written.
        findFriendTextField = friendsListActivity.getFindFriendTextField();
        friendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                findFriendTextField.performClick();
                findFriendTextField.setText("test@gmail.com");
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(findFriendTextField.getText().toString().equals("test@gmail.com"));

        // Click the Find Friend button to add the friend to the friend list.
        // Test that the friend was successfully added.
        findFriendsButton = friendsListActivity.getFindFriendsButton();
        friendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                findFriendsButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(LoggedInUser.getInstance().getFriendsList().get(0).getActualFriend().getProfile().getUsername().equals("test@gmail.com"));

        // Click the newly-added friend to proceed to the friend's profile. From here, the friend can be removed.
        friendsList = friendsListActivity.getFriendsListView();
        friendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                View firstFriend = friendsList.getChildAt(0);
                friendsList.performItemClick(firstFriend, 0, firstFriend.getId());
            }
        });

        // Test that the FriendsProfileActivity started correctly after the clicking the Friend in the list view.
        Instrumentation.ActivityMonitor friendsProfileActivityMonitor = getInstrumentation().addMonitor(FriendsProfileActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        FriendsProfileActivity friendsProfileActivity = (FriendsProfileActivity) friendsProfileActivityMonitor.waitForActivityWithTimeout(5000);
        assertNotNull("FriendsProfileActivity is null", friendsProfileActivity);
        assertEquals("Monitor for FriendsProfileActivity has not been called", 1, friendsProfileActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected FriendsProfileActivity", FriendsProfileActivity.class, friendsProfileActivity.getClass());
        getInstrumentation().removeMonitor(friendsProfileActivityMonitor);

        // Click the Remove Friend button to remove the friend from the friends list.
        removeFriendButton = friendsProfileActivity.getRemoveFriendButton();
        friendsProfileActivity.runOnUiThread(new Runnable() {
            public void run() {
                removeFriendButton.performClick();
            }
        });

        // Test that the FriendsListActivity opened correctly after the clicking the Remove button.
        // It is already on the Activity stack, so it will not be re-created. Re-use the old ActivityMonitor.
        getInstrumentation().waitForIdleSync();
        friendsListActivity = (FriendsListActivity) friendsListActivityMonitor.getLastActivity();
        assertNotNull("FriendsListActivity is null", friendsListActivity);
        assertEquals("Monitor for FriendsListActivity has not been called", 1, friendsListActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected FriendsListActivity", FriendsListActivity.class, friendsListActivity.getClass());
        getInstrumentation().removeMonitor(friendsListActivityMonitor);

        // Test that the friend was successfully removed.
        assertTrue(LoggedInUser.getInstance().getFriendsList().isEmpty());
        friendsList = friendsListActivity.getFriendsListView();
        assertEquals(friendsList.getChildAt(0), null);

        // The tests have completed; clear the friends lists and finish all activities (cleanup).
        LoggedInUser.getInstance().getFriendsList().clear();
        loginActivity.finish();
        homePageActivity.finish();
        friendsListActivity.finish();
        friendsProfileActivity.finish();
    }


    // UI test for tracking a friend. User Story: US02.01.01. Use Case: TrackFriend.
    public void testTrackFriendUI() {

        // The UI is currently on the Home Page. It has already logged in (see setUp() method).
        // Click the friends button to proceed to the friends page.
        friendsButton = homePageActivity.getFriendsButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                friendsButton.performClick();
            }
        });

        // Test that the FriendsListActivity started correctly after the clicking the friends button.
        Instrumentation.ActivityMonitor friendsListActivityMonitor = getInstrumentation().addMonitor(FriendsListActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        FriendsListActivity friendsListActivity = (FriendsListActivity) friendsListActivityMonitor.waitForActivityWithTimeout(5000);
        assertNotNull("FriendsListActivity is null", friendsListActivity);
        assertEquals("Monitor for FriendsListActivity has not been called", 1, friendsListActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected FriendsListActivity", FriendsListActivity.class, friendsListActivity.getClass());

        // On the friend-list page: click the Find Friend input box and write an arbitrary email.
        // Test that the text was successfully written.
        findFriendTextField = friendsListActivity.getFindFriendTextField();
        friendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                findFriendTextField.performClick();
                findFriendTextField.setText("test@gmail.com");
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(findFriendTextField.getText().toString().equals("test@gmail.com"));

        // Click the Find Friend button to add the friend to the friend list.
        // Test that the friend was successfully added.
        findFriendsButton = friendsListActivity.getFindFriendsButton();
        friendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                findFriendsButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(LoggedInUser.getInstance().getFriendsList().get(0).getActualFriend().getProfile().getUsername().equals("test@gmail.com"));

        // Click the newly-added friend to proceed to the friend's profile, confirming that the friend was added.
        friendsList = friendsListActivity.getFriendsListView();
        friendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                View firstFriend = friendsList.getChildAt(0);
                friendsList.performItemClick(firstFriend, 0, firstFriend.getId());
            }
        });

        // Test that the FriendsProfileActivity started correctly after the clicking the Friend in the list view.
        Instrumentation.ActivityMonitor friendsProfileActivityMonitor = getInstrumentation().addMonitor(FriendsProfileActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        FriendsProfileActivity friendsProfileActivity = (FriendsProfileActivity) friendsProfileActivityMonitor.waitForActivityWithTimeout(5000);
        assertNotNull("FriendsProfileActivity is null", friendsProfileActivity);
        assertEquals("Monitor for FriendsProfileActivity has not been called", 1, friendsProfileActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected FriendsProfileActivity", FriendsProfileActivity.class, friendsProfileActivity.getClass());
        getInstrumentation().removeMonitor(friendsProfileActivityMonitor);

        // Click the Track radio button to track the friend.
        // Test that friend is now being tracked.
        trackFriendButton = friendsProfileActivity.getTrackedRadioButton();
        friendsProfileActivity.runOnUiThread(new Runnable() {
            public void run() {
                trackFriendButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(LoggedInUser.getInstance().getFriendsList().get(0).isTracked());

        // Click the back button to return to the friends list page.
        backButton = friendsProfileActivity.getBackButton();
        friendsProfileActivity.runOnUiThread(new Runnable() {
            public void run() {
                backButton.performClick();
            }
        });

        // Test that the FriendsListActivity opened correctly after the clicking the Back button.
        // It is already on the Activity stack, so it will not be re-created. Re-use the old ActivityMonitor.
        getInstrumentation().waitForIdleSync();
        friendsListActivity = (FriendsListActivity) friendsListActivityMonitor.getLastActivity();
        assertNotNull("FriendsListActivity is null", friendsListActivity);
        assertEquals("Monitor for FriendsListActivity has not been called", 1, friendsListActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected FriendsListActivity", FriendsListActivity.class, friendsListActivity.getClass());
        getInstrumentation().removeMonitor(friendsListActivityMonitor);

        // Click the View Tracked Friends button to go to the tracked friends page and view newly-tracked friend.
        viewTrackedFriendsButton = friendsListActivity.getViewTrackedFriendsButton();
        friendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                viewTrackedFriendsButton.performClick();
            }
        });

        // Test that the DisplayTrackedFriendsProfileActivity started correctly after the clicking the View Tracked Friends button.
        Instrumentation.ActivityMonitor trackedFriendsListActivityMonitor = getInstrumentation().addMonitor(TrackedFriendsListActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        TrackedFriendsListActivity trackedFriendsListActivity = (TrackedFriendsListActivity) trackedFriendsListActivityMonitor.waitForActivityWithTimeout(5000);
        assertNotNull("TrackedFriendsListActivity is null", trackedFriendsListActivity);
        assertEquals("Monitor for TrackedFriendsListActivity has not been called", 1, trackedFriendsListActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected TrackedFriendsListActivity", TrackedFriendsListActivity.class, trackedFriendsListActivity.getClass());
        getInstrumentation().removeMonitor(trackedFriendsListActivityMonitor);

        // Test that the friend was successfully added to the tracked friends list.
        assertFalse(LoggedInUser.getInstance().getTrackedFriendsList().isEmpty());

        // The tests have completed; clear the friends lists and finish all activities (cleanup).
        LoggedInUser.getInstance().getFriendsList().clear();
        LoggedInUser.getInstance().getTrackedFriendsList().clear();
        loginActivity.finish();
        homePageActivity.finish();
        friendsListActivity.finish();
        friendsProfileActivity.finish();
        trackedFriendsListActivity.finish();
    }


    // UI test for viewing a friend's profile. User Story: US02.05.01. Use Case: ViewFriendsProfile.
    public void testViewFriendProfileUI() {

        // The UI is currently on the Home Page. It has already logged in (see setUp() method).
        // Click the friends button to proceed to the friends page.
        friendsButton = homePageActivity.getFriendsButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                friendsButton.performClick();
            }
        });

        // Test that the FriendsListActivity started correctly after the clicking the friends button.
        Instrumentation.ActivityMonitor friendsListActivityMonitor = getInstrumentation().addMonitor(FriendsListActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        FriendsListActivity friendsListActivity = (FriendsListActivity) friendsListActivityMonitor.waitForActivityWithTimeout(5000);
        assertNotNull("FriendsListActivity is null", friendsListActivity);
        assertEquals("Monitor for FriendsListActivity has not been called", 1, friendsListActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected FriendsListActivity", FriendsListActivity.class, friendsListActivity.getClass());
        getInstrumentation().removeMonitor(friendsListActivityMonitor);

        // On the friend-list page: click the Find Friend input box and write an arbitrary email.
        // Test that the text was successfully written.
        findFriendTextField = friendsListActivity.getFindFriendTextField();
        friendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                findFriendTextField.performClick();
                findFriendTextField.setText("test@gmail.com");
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(findFriendTextField.getText().toString().equals("test@gmail.com"));

        // Click the Find Friend button to add the friend to the friend list.
        // Test that the friend was successfully added.
        findFriendsButton = friendsListActivity.getFindFriendsButton();
        friendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                findFriendsButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(LoggedInUser.getInstance().getFriendsList().get(0).getActualFriend().getProfile().getUsername().equals("test@gmail.com"));

        // Click the newly-added friend to proceed to the friend's profile, confirming that the friend was added.
        friendsList = friendsListActivity.getFriendsListView();
        friendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                View firstFriend = friendsList.getChildAt(0);
                friendsList.performItemClick(firstFriend, 0, firstFriend.getId());
            }
        });

        // Test that the FriendsProfileActivity started correctly after the clicking the Friend in the list view.
        Instrumentation.ActivityMonitor friendsProfileActivityMonitor = getInstrumentation().addMonitor(FriendsProfileActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        FriendsProfileActivity friendsProfileActivity = (FriendsProfileActivity) friendsProfileActivityMonitor.waitForActivityWithTimeout(5000);
        assertNotNull("FriendsProfileActivity is null", friendsProfileActivity);
        assertEquals("Monitor for FriendsProfileActivity has not been called", 1, friendsProfileActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected FriendsProfileActivity", FriendsProfileActivity.class, friendsProfileActivity.getClass());
        getInstrumentation().removeMonitor(friendsProfileActivityMonitor);

        // Test that the friend's profile being viewed corresponds to the friend that was added earlier (compare the usernames, names, and tracked status).
        assertTrue(friendsProfileActivity.getUsernameTextView().getText().toString().equals("test@gmail.com"));
        assertTrue(friendsProfileActivity.getNameTextView().getText().toString().equals("Name"));
        assertFalse(friendsProfileActivity.getTrackedRadioButton().isChecked());

        // The tests have completed; clear the friends lists and finish all activities (cleanup).
        LoggedInUser.getInstance().getFriendsList().clear();
        loginActivity.finish();
        homePageActivity.finish();
        friendsListActivity.finish();
        friendsProfileActivity.finish();
    }


    // UI test for viewing the user's own profile. User Story: US02.04.01. Use Case: ViewProfile.
    public void testViewProfileUI() {

        // The UI is currently on the Home Page. It has already logged in (see setUp() method).
        // Click the profile button to proceed to the profile page.
        profileButton = homePageActivity.getProfileButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                profileButton.performClick();
            }
        });

        // Test that the UserProfileActivity started correctly after the clicking the profile button.
        Instrumentation.ActivityMonitor userProfileActivityMonitor = getInstrumentation().addMonitor(UserProfileActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        UserProfileActivity userProfileActivity = (UserProfileActivity) userProfileActivityMonitor.waitForActivityWithTimeout(5000);
        assertNotNull("UserProfileActivity is null", userProfileActivity);
        assertEquals("Monitor for UserProfileActivity has not been called", 1, userProfileActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected UserProfileActivity", UserProfileActivity.class, userProfileActivity.getClass());
        getInstrumentation().removeMonitor(userProfileActivityMonitor);

        // The tests have completed; finish all activities (cleanup).
        loginActivity.finish();
        homePageActivity.finish();
        userProfileActivity.finish();
    }
}
