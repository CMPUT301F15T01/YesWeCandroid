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
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

public class FriendsTests extends ActivityInstrumentationTestCase2{
// Instrumentation code is from: https://developer.android.com/training/activity-testing/activity-functional-testing.html 2015-11-03

    AutoCompleteTextView loginEmailTextView;
    Button loginButton;
    ListView friendsList;
    Button friendsButton;
    Button findFriendsButton;
    Button removeFriendButton;
    Button backButton;
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

        // Test that the DisplayFriendsActivity started correctly after the clicking the friends button.
        Instrumentation.ActivityMonitor displayFriendsActivityMonitor = getInstrumentation().addMonitor(DisplayFriendsActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        DisplayFriendsActivity displayFriendsActivity = (DisplayFriendsActivity) displayFriendsActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("DisplayFriendsActivity is null", displayFriendsActivity);
        assertEquals("Monitor for DisplayFriendsActivity has not been called", 1, displayFriendsActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected DisplayFriendsActivity", DisplayFriendsActivity.class, displayFriendsActivity.getClass());
        getInstrumentation().removeMonitor(displayFriendsActivityMonitor);

        // On the friend-list page: click the Find Friend input box and write an arbitrary email.
        // Test that the text was successfully written.
        findFriendTextField = displayFriendsActivity.getFindFriendTextField();
        displayFriendsActivity.runOnUiThread(new Runnable() {
            public void run() {
                findFriendTextField.performClick();
                findFriendTextField.setText("test@gmail.com");
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(findFriendTextField.getText().toString().equals("test@gmail.com"));

        // Click the Find Friend button to add the friend to the friend list.
        // Test that the friend was successfully added.
        findFriendsButton = displayFriendsActivity.getFindFriendsButton();
        displayFriendsActivity.runOnUiThread(new Runnable() {
            public void run() {
                findFriendsButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(LoggedInUser.getInstance().getFriendsList().get(0).getProfile().getUsername().equals("test@gmail.com"));

        // Click the newly-added friend to proceed to the friend's profile, confirming that the friend was added.
        friendsList = displayFriendsActivity.getFriendsListView();
        displayFriendsActivity.runOnUiThread(new Runnable() {
            public void run() {
                View firstFriend = friendsList.getChildAt(0);
                friendsList.performItemClick(firstFriend, 0, firstFriend.getId());
            }
        });

        // Test that the DisplayFriendsProfileActivity started correctly after the clicking the Friend in the list view.
        Instrumentation.ActivityMonitor displayFriendsProfileActivityMonitor = getInstrumentation().addMonitor(DisplayFriendsProfileActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        DisplayFriendsProfileActivity displayFriendsProfileActivity = (DisplayFriendsProfileActivity) displayFriendsProfileActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("DisplayFriendsProfileActivity is null", displayFriendsProfileActivity);
        assertEquals("Monitor for DisplayFriendsProfileActivity has not been called", 1, displayFriendsProfileActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected DisplayFriendsProfileActivity", DisplayFriendsProfileActivity.class, displayFriendsProfileActivity.getClass());
        getInstrumentation().removeMonitor(displayFriendsProfileActivityMonitor);
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

        // Test that the DisplayFriendsActivity started correctly after the clicking the friends button.
        Instrumentation.ActivityMonitor displayFriendsActivityMonitor = getInstrumentation().addMonitor(DisplayFriendsActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        DisplayFriendsActivity displayFriendsActivity = (DisplayFriendsActivity) displayFriendsActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("DisplayFriendsActivity is null", displayFriendsActivity);
        assertEquals("Monitor for DisplayFriendsActivity has not been called", 1, displayFriendsActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected DisplayFriendsActivity", DisplayFriendsActivity.class, displayFriendsActivity.getClass());
        getInstrumentation().removeMonitor(displayFriendsActivityMonitor);

        // On the friend-list page: click the Find Friend input box and write an arbitrary email.
        // Test that the text was successfully written.
        findFriendTextField = displayFriendsActivity.getFindFriendTextField();
        displayFriendsActivity.runOnUiThread(new Runnable() {
            public void run() {
                findFriendTextField.performClick();
                findFriendTextField.setText("test@gmail.com");
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(findFriendTextField.getText().toString().equals("test@gmail.com"));

        // Click the Find Friend button to add the friend to the friend list.
        // Test that the friend was successfully added.
        findFriendsButton = displayFriendsActivity.getFindFriendsButton();
        displayFriendsActivity.runOnUiThread(new Runnable() {
            public void run() {
                findFriendsButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(LoggedInUser.getInstance().getFriendsList().get(0).getProfile().getUsername().equals("test@gmail.com"));

        // Click the newly-added friend to proceed to the friend's profile. From here, the friend can be removed.
        friendsList = displayFriendsActivity.getFriendsListView();
        displayFriendsActivity.runOnUiThread(new Runnable() {
            public void run() {
                View firstFriend = friendsList.getChildAt(0);
                friendsList.performItemClick(firstFriend, 0, firstFriend.getId());
            }
        });

        // Test that the DisplayFriendsProfileActivity started correctly after the clicking the Friend in the list view.
        Instrumentation.ActivityMonitor displayFriendsProfileActivityMonitor = getInstrumentation().addMonitor(DisplayFriendsProfileActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        DisplayFriendsProfileActivity displayFriendsProfileActivity = (DisplayFriendsProfileActivity) displayFriendsProfileActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("DisplayFriendsProfileActivity is null", displayFriendsProfileActivity);
        assertEquals("Monitor for DisplayFriendsProfileActivity has not been called", 1, displayFriendsProfileActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected DisplayFriendsProfileActivity", DisplayFriendsProfileActivity.class, displayFriendsProfileActivity.getClass());
        getInstrumentation().removeMonitor(displayFriendsProfileActivityMonitor);

        // Click the Remove Friend button to remove the friend from the friends list.
        removeFriendButton = displayFriendsProfileActivity.getRemoveFriendButton();
        displayFriendsProfileActivity.runOnUiThread(new Runnable() {
            public void run() {
                removeFriendButton.performClick();
            }
        });

        // Test that the DisplayFriendsActivity started correctly after the clicking the Remove button.
        displayFriendsActivityMonitor = getInstrumentation().addMonitor(DisplayFriendsActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        displayFriendsActivity = (DisplayFriendsActivity) displayFriendsActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("DisplayFriendsActivity is null", displayFriendsActivity);
        assertEquals("Monitor for DisplayFriendsActivity has not been called", 1, displayFriendsActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected DisplayFriendsActivity", DisplayFriendsActivity.class, displayFriendsActivity.getClass());
        getInstrumentation().removeMonitor(displayFriendsActivityMonitor);

        // Test that the friend was successfully removed.
        assertTrue(LoggedInUser.getInstance().getFriendsList().isEmpty());

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

        // Test that the DisplayFriendsActivity started correctly after the clicking the friends button.
        Instrumentation.ActivityMonitor displayFriendsActivityMonitor = getInstrumentation().addMonitor(DisplayFriendsActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        DisplayFriendsActivity displayFriendsActivity = (DisplayFriendsActivity) displayFriendsActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("DisplayFriendsActivity is null", displayFriendsActivity);
        assertEquals("Monitor for DisplayFriendsActivity has not been called", 1, displayFriendsActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected DisplayFriendsActivity", DisplayFriendsActivity.class, displayFriendsActivity.getClass());
        getInstrumentation().removeMonitor(displayFriendsActivityMonitor);

        // On the friend-list page: click the Find Friend input box and write an arbitrary email.
        // Test that the text was successfully written.
        findFriendTextField = displayFriendsActivity.getFindFriendTextField();
        displayFriendsActivity.runOnUiThread(new Runnable() {
            public void run() {
                findFriendTextField.performClick();
                findFriendTextField.setText("test@gmail.com");
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(findFriendTextField.getText().toString().equals("test@gmail.com"));

        // Click the Find Friend button to add the friend to the friend list.
        // Test that the friend was successfully added.
        findFriendsButton = displayFriendsActivity.getFindFriendsButton();
        displayFriendsActivity.runOnUiThread(new Runnable() {
            public void run() {
                findFriendsButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(LoggedInUser.getInstance().getFriendsList().get(0).getProfile().getUsername().equals("test@gmail.com"));

        // Click the newly-added friend to proceed to the friend's profile, confirming that the friend was added.
        friendsList = displayFriendsActivity.getFriendsListView();
        displayFriendsActivity.runOnUiThread(new Runnable() {
            public void run() {
                View firstFriend = friendsList.getChildAt(0);
                friendsList.performItemClick(firstFriend, 0, firstFriend.getId());
            }
        });

        // Test that the DisplayFriendsProfileActivity started correctly after the clicking the Friend in the list view.
        Instrumentation.ActivityMonitor displayFriendsProfileActivityMonitor = getInstrumentation().addMonitor(DisplayFriendsProfileActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        DisplayFriendsProfileActivity displayFriendsProfileActivity = (DisplayFriendsProfileActivity) displayFriendsProfileActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("DisplayFriendsProfileActivity is null", displayFriendsProfileActivity);
        assertEquals("Monitor for DisplayFriendsProfileActivity has not been called", 1, displayFriendsProfileActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected DisplayFriendsProfileActivity", DisplayFriendsProfileActivity.class, displayFriendsProfileActivity.getClass());
        getInstrumentation().removeMonitor(displayFriendsProfileActivityMonitor);

<<<<<<< HEAD


        // we are in the friend's page....now we have to click track and add the friend to the track friends.
=======
        //click the radio button to set the friend as tracked
        trackFriendButton = displayFriendsProfileActivity.getTrackedRadioButton();
        displayFriendsProfileActivity.runOnUiThread(new Runnable() {
            public void run() {
                trackFriendButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        //test that the friend has been tracked
        assertTrue(LoggedInUser.getInstance().getFriendsList().get(0).isTracked().equals(true));


        //click the back button to return to friends list
        backButton = displayFriendsProfileActivity.getBackButton();
        displayFriendsProfileActivity.runOnUiThread(new Runnable() {
            public void run() {
                backButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        /*

        THING IS SINCE THESE ARE NEW INTENTS OF IT STACKED ON TOP SOMETHING GOING WRONG

        // Test that the DisplayFriendsActivity started correctly after the clicking the friends button..
        displayFriendsActivityMonitor = getInstrumentation().addMonitor(DisplayFriendsActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        displayFriendsActivity = (DisplayFriendsActivity) displayFriendsActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("DisplayFriendsActivity is null", displayFriendsActivity);
        assertEquals("Monitor for DisplayFriendsActivity has not been called", 1, displayFriendsActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected DisplayFriendsActivity", DisplayFriendsActivity.class, displayFriendsActivity.getClass());
        getInstrumentation().removeMonitor(displayFriendsActivityMonitor);
        */
        //click the view tracked friends button to view the tracked friends list
        viewTrackedFriendsButton = displayFriendsActivity.getViewTrackedFriendsButton();
        displayFriendsActivity.runOnUiThread(new Runnable() {
            public void run() {
                viewTrackedFriendsButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        /*
        // Test that the DisplayTrackedFriendsProfileActivity started correctly after the clicking the view tracked friends button.
        Instrumentation.ActivityMonitor displayTrackedFriendsActivityMonitor = getInstrumentation().addMonitor(DisplayTrackedFriendsActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        DisplayTrackedFriendsActivity displayTrackedFriendsActivity = (DisplayTrackedFriendsActivity) displayTrackedFriendsActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("DisplayTrackedFriendsActivity is null", displayTrackedFriendsActivity);
        assertEquals("Monitor for DisplayTrackedFriendsActivity has not been called", 1, displayTrackedFriendsActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected DisplayTrackedFriendsActivity", DisplayTrackedFriendsActivity.class, displayTrackedFriendsActivity.getClass());
        getInstrumentation().removeMonitor(displayTrackedFriendsActivityMonitor);
        */
        // check that there is now a tracked friend in the tracked friends list
        assertFalse(LoggedInUser.getInstance().getTrackedFriends().isEmpty());
>>>>>>> 0768823ef11e38dbb4a98b3da486081f4b371795
        // we have to check that the friend was successfully added to the tracked friend list, and also check in the UI.
        // we have to make a tracked friends page or whatever, or somehow find a way to view tracked friends (or just leave it as is with this one button).
    }
}
