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

import ca.ualberta.trinkettrader.Friends.FriendsListActivity;
import ca.ualberta.trinkettrader.Friends.FriendsProfileActivity;
import ca.ualberta.trinkettrader.Friends.TrackedFriends.TrackedFriendsListActivity;
import ca.ualberta.trinkettrader.User.LoggedInUser;
import ca.ualberta.trinkettrader.User.Profile.UserProfileActivity;

public class FriendsTests extends ActivityInstrumentationTestCase2 {
// Instrumentation code is from: https://developer.android.com/training/activity-testing/activity-functional-testing.html 2015-11-03

    AutoCompleteTextView loginEmailTextView;
    Button loginButton;
    ListView friendsList;
    Button friendsButton;
    Button findFriendsButton;
    Button removeFriendButton;
    Button backButton;
    Button profileButton;
    Button viewTrackedFriendsButton;
    RadioButton trackFriendButton;
    EditText findFriendTextField;


    public FriendsTests() {
        super(LoginActivity.class);
    }

    // UI test for adding a friend. User Story: US02.02.01. Use Case: AddFriend.
    public void testAddFriendUI() {

        // Start the UI test from the login page (beginning of the app).
        LoginActivity loginActivity = (LoginActivity) getActivity();

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
        assertEquals("Monitor for HomePageActivity has not been called", 1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected HomePageActivity", HomePageActivity.class, homePageActivity.getClass());
        getInstrumentation().removeMonitor(homePageActivityMonitor);

        // Click the friends button to proceed to the friends page.
        friendsButton = homePageActivity.getFriendsButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                friendsButton.performClick();
            }
        });

        // Test that the FriendsListActivity started correctly after the clicking the friends button.
        Instrumentation.ActivityMonitor displayFriendsActivityMonitor = getInstrumentation().addMonitor(FriendsListActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        FriendsListActivity displayFriendsListActivity = (FriendsListActivity) displayFriendsActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("FriendsListActivity is null", displayFriendsListActivity);
        assertEquals("Monitor for FriendsListActivity has not been called", 1, displayFriendsActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected FriendsListActivity", FriendsListActivity.class, displayFriendsListActivity.getClass());
        getInstrumentation().removeMonitor(displayFriendsActivityMonitor);

        // On the friend-list page: click the Find Friend input box and write an arbitrary email.
        // Test that the text was successfully written.
        findFriendTextField = displayFriendsListActivity.getFindFriendTextField();
        displayFriendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                findFriendTextField.performClick();
                findFriendTextField.setText("test@gmail.com");
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(findFriendTextField.getText().toString().equals("test@gmail.com"));

        // Click the Find Friend button to add the friend to the friend list.
        // Test that the friend was successfully added.
        findFriendsButton = displayFriendsListActivity.getFindFriendsButton();
        displayFriendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                findFriendsButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(LoggedInUser.getInstance().getFriendsList().get(0).getProfile().getUsername().equals("test@gmail.com"));

        // Click the newly-added friend to proceed to the friend's profile, confirming that the friend was added.
        friendsList = displayFriendsListActivity.getFriendsListView();
        displayFriendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                View firstFriend = friendsList.getChildAt(0);
                friendsList.performItemClick(firstFriend, 0, firstFriend.getId());
            }
        });

        // Test that the FriendsProfileActivity started correctly after the clicking the Friend in the list view.
        Instrumentation.ActivityMonitor displayFriendsProfileActivityMonitor = getInstrumentation().addMonitor(FriendsProfileActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        FriendsProfileActivity displayFriendsProfileActivity = (FriendsProfileActivity) displayFriendsProfileActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("FriendsProfileActivity is null", displayFriendsProfileActivity);
        assertEquals("Monitor for FriendsProfileActivity has not been called", 1, displayFriendsProfileActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected FriendsProfileActivity", FriendsProfileActivity.class, displayFriendsProfileActivity.getClass());
        getInstrumentation().removeMonitor(displayFriendsProfileActivityMonitor);

        // The tests have completed; clear the friends lists and finish all activities (cleanup).
        LoggedInUser.getInstance().getFriendsList().clear();
        loginActivity.finish();
        homePageActivity.finish();
        displayFriendsListActivity.finish();
        displayFriendsProfileActivity.finish();
    }

    // UI test for removing a friend. User Story: US02.03.01. Use Case: RemoveFriend.
    public void testRemoveFriendUI() {

        // Start the UI test from the login page (beginning of the app).
        LoginActivity loginActivity = (LoginActivity) getActivity();

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
        assertEquals("Monitor for HomePageActivity has not been called", 1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected HomePageActivity", HomePageActivity.class, homePageActivity.getClass());
        getInstrumentation().removeMonitor(homePageActivityMonitor);

        // Click the friends button to proceed to the friends page.
        friendsButton = homePageActivity.getFriendsButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                friendsButton.performClick();
            }
        });

        // Test that the FriendsListActivity started correctly after the clicking the friends button.
        Instrumentation.ActivityMonitor displayFriendsActivityMonitor = getInstrumentation().addMonitor(FriendsListActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        FriendsListActivity displayFriendsListActivity = (FriendsListActivity) displayFriendsActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("FriendsListActivity is null", displayFriendsListActivity);
        assertEquals("Monitor for FriendsListActivity has not been called", 1, displayFriendsActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected FriendsListActivity", FriendsListActivity.class, displayFriendsListActivity.getClass());
        getInstrumentation().removeMonitor(displayFriendsActivityMonitor);

        // On the friend-list page: click the Find Friend input box and write an arbitrary email.
        // Test that the text was successfully written.
        findFriendTextField = displayFriendsListActivity.getFindFriendTextField();
        displayFriendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                findFriendTextField.performClick();
                findFriendTextField.setText("test@gmail.com");
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(findFriendTextField.getText().toString().equals("test@gmail.com"));

        // Click the Find Friend button to add the friend to the friend list.
        // Test that the friend was successfully added.
        findFriendsButton = displayFriendsListActivity.getFindFriendsButton();
        displayFriendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                findFriendsButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(LoggedInUser.getInstance().getFriendsList().get(0).getProfile().getUsername().equals("test@gmail.com"));

        // Click the newly-added friend to proceed to the friend's profile. From here, the friend can be removed.
        friendsList = displayFriendsListActivity.getFriendsListView();
        displayFriendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                View firstFriend = friendsList.getChildAt(0);
                friendsList.performItemClick(firstFriend, 0, firstFriend.getId());
            }
        });

        // Test that the FriendsProfileActivity started correctly after the clicking the Friend in the list view.
        Instrumentation.ActivityMonitor displayFriendsProfileActivityMonitor = getInstrumentation().addMonitor(FriendsProfileActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        FriendsProfileActivity displayFriendsProfileActivity = (FriendsProfileActivity) displayFriendsProfileActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("FriendsProfileActivity is null", displayFriendsProfileActivity);
        assertEquals("Monitor for FriendsProfileActivity has not been called", 1, displayFriendsProfileActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected FriendsProfileActivity", FriendsProfileActivity.class, displayFriendsProfileActivity.getClass());
        getInstrumentation().removeMonitor(displayFriendsProfileActivityMonitor);

        // Click the Remove Friend button to remove the friend from the friends list.
        removeFriendButton = displayFriendsProfileActivity.getRemoveFriendButton();
        displayFriendsProfileActivity.runOnUiThread(new Runnable() {
            public void run() {
                removeFriendButton.performClick();
            }
        });

        // Test that the FriendsListActivity started correctly after the clicking the Remove button.
        displayFriendsActivityMonitor = getInstrumentation().addMonitor(FriendsListActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        displayFriendsListActivity = (FriendsListActivity) displayFriendsActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("FriendsListActivity is null", displayFriendsListActivity);
        assertEquals("Monitor for FriendsListActivity has not been called", 1, displayFriendsActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected FriendsListActivity", FriendsListActivity.class, displayFriendsListActivity.getClass());
        getInstrumentation().removeMonitor(displayFriendsActivityMonitor);

        // Test that the friend was successfully removed.
        assertTrue(LoggedInUser.getInstance().getFriendsList().isEmpty());

        // The tests have completed; clear the friends lists and finish all activities (cleanup).
        LoggedInUser.getInstance().getFriendsList().clear();
        loginActivity.finish();
        homePageActivity.finish();
        displayFriendsListActivity.finish();
        displayFriendsProfileActivity.finish();
    }

    // UI test for tracking a friend. User Story: US02.01.01. Use Case: TrackFriend.
    public void testTrackFriendUI() {

        // Start the UI test from the login page (beginning of the app).
        LoginActivity loginActivity = (LoginActivity) getActivity();

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
        assertEquals("Monitor for HomePageActivity has not been called", 1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected HomePageActivity", HomePageActivity.class, homePageActivity.getClass());
        getInstrumentation().removeMonitor(homePageActivityMonitor);

        // Click the friends button to proceed to the friends page.
        friendsButton = homePageActivity.getFriendsButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                friendsButton.performClick();
            }
        });

        // Test that the FriendsListActivity started correctly after the clicking the friends button.
        Instrumentation.ActivityMonitor displayFriendsActivityMonitor = getInstrumentation().addMonitor(FriendsListActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        FriendsListActivity displayFriendsListActivity = (FriendsListActivity) displayFriendsActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("FriendsListActivity is null", displayFriendsListActivity);
        assertEquals("Monitor for FriendsListActivity has not been called", 1, displayFriendsActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected FriendsListActivity", FriendsListActivity.class, displayFriendsListActivity.getClass());

        // On the friend-list page: click the Find Friend input box and write an arbitrary email.
        // Test that the text was successfully written.
        findFriendTextField = displayFriendsListActivity.getFindFriendTextField();
        displayFriendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                findFriendTextField.performClick();
                findFriendTextField.setText("test@gmail.com");
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(findFriendTextField.getText().toString().equals("test@gmail.com"));

        // Click the Find Friend button to add the friend to the friend list.
        // Test that the friend was successfully added.
        findFriendsButton = displayFriendsListActivity.getFindFriendsButton();
        displayFriendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                findFriendsButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(LoggedInUser.getInstance().getFriendsList().get(0).getProfile().getUsername().equals("test@gmail.com"));

        // Click the newly-added friend to proceed to the friend's profile, confirming that the friend was added.
        friendsList = displayFriendsListActivity.getFriendsListView();
        displayFriendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                View firstFriend = friendsList.getChildAt(0);
                friendsList.performItemClick(firstFriend, 0, firstFriend.getId());
            }
        });

        // Test that the FriendsProfileActivity started correctly after the clicking the Friend in the list view.
        Instrumentation.ActivityMonitor displayFriendsProfileActivityMonitor = getInstrumentation().addMonitor(FriendsProfileActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        FriendsProfileActivity displayFriendsProfileActivity = (FriendsProfileActivity) displayFriendsProfileActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("FriendsProfileActivity is null", displayFriendsProfileActivity);
        assertEquals("Monitor for FriendsProfileActivity has not been called", 1, displayFriendsProfileActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected FriendsProfileActivity", FriendsProfileActivity.class, displayFriendsProfileActivity.getClass());
        getInstrumentation().removeMonitor(displayFriendsProfileActivityMonitor);

        // we are in the friend's page....now we have to click track and add the friend to the track friends.

        //click the radio button to set the friend as tracked

        // Click the Track radio button to track the friend.
        // Test that friend is now being tracked.
        trackFriendButton = displayFriendsProfileActivity.getTrackedRadioButton();
        displayFriendsProfileActivity.runOnUiThread(new Runnable() {
            public void run() {
                trackFriendButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(LoggedInUser.getInstance().getFriendsList().get(0).isTracked());

        // Click the back button to return to the friends list page.
        backButton = displayFriendsProfileActivity.getBackButton();
        displayFriendsProfileActivity.runOnUiThread(new Runnable() {
            public void run() {
                backButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Test that the FriendsListActivity started correctly after the clicking the back button.
        displayFriendsListActivity = (FriendsListActivity) displayFriendsActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("FriendsListActivity is null", displayFriendsListActivity);
        assertEquals("Monitor for FriendsListActivity has not been called", 1, displayFriendsActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected FriendsListActivity", FriendsListActivity.class, displayFriendsListActivity.getClass());
        getInstrumentation().removeMonitor(displayFriendsActivityMonitor);

        // Click the View Tracked Friends button to go to the tracked friends page and view newly-tracked friend.
        viewTrackedFriendsButton = displayFriendsListActivity.getViewTrackedFriendsButton();
        displayFriendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                viewTrackedFriendsButton.performClick();
            }
        });

        // Test that the DisplayTrackedFriendsProfileActivity started correctly after the clicking the View Tracked Friends button.
        Instrumentation.ActivityMonitor displayTrackedFriendsActivityMonitor = getInstrumentation().addMonitor(TrackedFriendsListActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        TrackedFriendsListActivity displayTrackedFriendsListActivity = (TrackedFriendsListActivity) displayTrackedFriendsActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("TrackedFriendsListActivity is null", displayTrackedFriendsListActivity);
        assertEquals("Monitor for TrackedFriendsListActivity has not been called", 1, displayTrackedFriendsActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected TrackedFriendsListActivity", TrackedFriendsListActivity.class, displayTrackedFriendsListActivity.getClass());
        getInstrumentation().removeMonitor(displayTrackedFriendsActivityMonitor);

        // Test that the friend was successfully added to the tracked friends list.
        assertFalse(LoggedInUser.getInstance().getTrackedFriends().isEmpty());

        // we have to check that the friend was successfully added to the tracked friend list, and also check in the UI.
        // we have to make a tracked friends page or whatever, or somehow find a way to view tracked friends (or just leave it as is with this one button).

        // The tests have completed; clear the friends lists and finish all activities (cleanup).
        LoggedInUser.getInstance().getFriendsList().clear();
        LoggedInUser.getInstance().getTrackedFriends().clear();
        loginActivity.finish();
        homePageActivity.finish();
        displayFriendsListActivity.finish();
        displayFriendsProfileActivity.finish();
        displayTrackedFriendsListActivity.finish();
    }

    // UI test for viewing a friend's profile. User Story: US02.05.01. Use Case: ViewFriendsProfile.
    public void testViewFriendProfileUI() {

        // Start the UI test from the login page (beginning of the app).
        LoginActivity loginActivity = (LoginActivity) getActivity();

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
        assertEquals("Monitor for HomePageActivity has not been called", 1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected HomePageActivity", HomePageActivity.class, homePageActivity.getClass());
        getInstrumentation().removeMonitor(homePageActivityMonitor);

        // Click the friends button to proceed to the friends page.
        friendsButton = homePageActivity.getFriendsButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                friendsButton.performClick();
            }
        });

        // Test that the FriendsListActivity started correctly after the clicking the friends button.
        Instrumentation.ActivityMonitor displayFriendsActivityMonitor = getInstrumentation().addMonitor(FriendsListActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        FriendsListActivity displayFriendsListActivity = (FriendsListActivity) displayFriendsActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("FriendsListActivity is null", displayFriendsListActivity);
        assertEquals("Monitor for FriendsListActivity has not been called", 1, displayFriendsActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected FriendsListActivity", FriendsListActivity.class, displayFriendsListActivity.getClass());
        getInstrumentation().removeMonitor(displayFriendsActivityMonitor);

        // On the friend-list page: click the Find Friend input box and write an arbitrary email.
        // Test that the text was successfully written.
        findFriendTextField = displayFriendsListActivity.getFindFriendTextField();
        displayFriendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                findFriendTextField.performClick();
                findFriendTextField.setText("test@gmail.com");
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(findFriendTextField.getText().toString().equals("test@gmail.com"));

        // Click the Find Friend button to add the friend to the friend list.
        // Test that the friend was successfully added.
        findFriendsButton = displayFriendsListActivity.getFindFriendsButton();
        displayFriendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                findFriendsButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(LoggedInUser.getInstance().getFriendsList().get(0).getProfile().getUsername().equals("test@gmail.com"));

        // Click the newly-added friend to proceed to the friend's profile.
        friendsList = displayFriendsListActivity.getFriendsListView();
        displayFriendsListActivity.runOnUiThread(new Runnable() {
            public void run() {
                View firstFriend = friendsList.getChildAt(0);
                friendsList.performItemClick(firstFriend, 0, firstFriend.getId());
            }
        });

        // Test that the FriendsProfileActivity started correctly after the clicking the Friend in the list view.
        Instrumentation.ActivityMonitor displayFriendsProfileActivityMonitor = getInstrumentation().addMonitor(FriendsProfileActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        FriendsProfileActivity displayFriendsProfileActivity = (FriendsProfileActivity) displayFriendsProfileActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("FriendsProfileActivity is null", displayFriendsProfileActivity);
        assertEquals("Monitor for FriendsProfileActivity has not been called", 1, displayFriendsProfileActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected FriendsProfileActivity", FriendsProfileActivity.class, displayFriendsProfileActivity.getClass());
        getInstrumentation().removeMonitor(displayFriendsProfileActivityMonitor);

        // Test that the friend being viewed is the same as the friend that was added earlier (compare the usernames).
        assertTrue(displayFriendsProfileActivity.getUsernameTextView().getText().toString().equals("test@gmail.com"));

        // The tests have completed; clear the friends lists and finish all activities (cleanup).
        LoggedInUser.getInstance().getFriendsList().clear();
        loginActivity.finish();
        homePageActivity.finish();
        displayFriendsListActivity.finish();
        displayFriendsProfileActivity.finish();
    }

    // UI test for viewing the user's own profile. User Story: US02.04.01. Use Case: ViewProfile.
    public void testViewProfileUI() {

        // Start the UI test from the login page (beginning of the app).
        LoginActivity loginActivity = (LoginActivity) getActivity();

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
        assertEquals("Monitor for HomePageActivity has not been called", 1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected HomePageActivity", HomePageActivity.class, homePageActivity.getClass());
        getInstrumentation().removeMonitor(homePageActivityMonitor);

        // Click the profile button to proceed to the profile page.
        profileButton = homePageActivity.getProfileButton();
        homePageActivity.runOnUiThread(new Runnable() {
            public void run() {
                profileButton.performClick();
            }
        });

        // Test that the UserProfileActivity started correctly after the clicking the profile button.
        Instrumentation.ActivityMonitor displayUserProfileActivityMonitor = getInstrumentation().addMonitor(UserProfileActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        UserProfileActivity displayUserProfileActivity = (UserProfileActivity) displayUserProfileActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("UserProfileActivity is null", displayUserProfileActivity);
        assertEquals("Monitor for UserProfileActivity has not been called", 1, displayUserProfileActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected UserProfileActivity", UserProfileActivity.class, displayUserProfileActivity.getClass());
        getInstrumentation().removeMonitor(displayUserProfileActivityMonitor);

        // The tests have completed; finish all activities (cleanup).
        loginActivity.finish();
        homePageActivity.finish();
        displayUserProfileActivity.finish();
    }
}
