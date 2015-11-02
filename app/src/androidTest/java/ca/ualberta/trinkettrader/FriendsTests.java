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
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class FriendsTests extends ActivityInstrumentationTestCase2{

    int clickCount;
    ListView list;
    Button addFriendButton;
    Button friendsButton;

    public FriendsTests() {super(HomePageActivity.class);}

    // Test if friends list has a friend in it
    public void testHasFriends() {
        FriendsList friendsList = new FriendsList();
        Friend friend = new Friend();
        assertFalse(friendsList.contains(friend));
        friendsList.add(friend);
        assertTrue(friendsList.contains(friend));
    }

    // Test if a user has an inventory
    public void testHasFriendsList() {
        HomePageActivity activity = (HomePageActivity) getActivity();

        // Code from : https://developer.android.com/training/activity-testing/activity-functional-testing.html#keyinput, 2015-10-14
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor friendActMon =
                getInstrumentation().addMonitor(DisplayFriendsActivity.class.getName(),
                        null, false);

        // Click the button
        friendsButton = activity.getFriendsButton();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                friendsButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final DisplayFriendsActivity friendsActivity = (DisplayFriendsActivity)
                friendActMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", friendsActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, friendActMon.getHits());
        assertEquals("Activity is of wrong type",
                DisplayFriendsActivity.class, friendsActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(friendActMon);

        assertNotNull(friendsActivity.getFriends());
    }



    // Test method to test if an friends list has no friends in it
    public void testNoFriends() {
        FriendsList friendsList = new FriendsList();
        assertTrue(friendsList.isEmpty());
        Friend friend1 = new Friend();
        friendsList.add(friend1);
        assertFalse(friendsList.isEmpty());
    }

    public void testNumberOfFriendsUI(){
        HomePageActivity activity = (HomePageActivity) getActivity();

        // Code from : https://developer.android.com/training/activity-testing/activity-functional-testing.html#keyinput, 2015-10-14
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor friendActMon =
                getInstrumentation().addMonitor(DisplayFriendsActivity.class.getName(),
                        null, false);

        // Click the button
        friendsButton = activity.getFriendsButton();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                friendsButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final DisplayFriendsActivity friendsActivity = (DisplayFriendsActivity)
                friendActMon.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", friendsActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, friendActMon.getHits());
        assertEquals("Activity is of wrong type",
                DisplayFriendsActivity.class, friendsActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(friendActMon);

        assertTrue(friendsActivity.getFriends().size() == 0);

        // Move to add item activity
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(EditFriendsListActivity.class.getName(),
                        null, false);

        // Click the button
        addFriendButton = friendsActivity.getAddFriendButton();
        friendsActivity.runOnUiThread(new Runnable() {
            public void run() {
                addFriendButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        EditFriendsListActivity nextReceiverActivity = (EditFriendsListActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", nextReceiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                EditFriendsListActivity.class, nextReceiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        EditFriendsListActivity addFriendtoDisplayInventoryActivity = (EditFriendsListActivity) getActivity();

        // Click the button
        final EditText editName = addFriendtoDisplayInventoryActivity.getFriendName();
        addFriendtoDisplayInventoryActivity.runOnUiThread(new Runnable() {
            public void run() {
                editName.setText("testFriend");
            }
        });
        getInstrumentation().waitForIdleSync();

        assertTrue(friendsActivity.getFriends().size() == 1);
    }

    // Test method for adding a friend to your friendslist
    public void testAddFriendUI() {
        HomePageActivity activity = (HomePageActivity) getActivity();

        // Code from : https://developer.android.com/training/activity-testing/activity-functional-testing.html#keyinput, 2015-10-14
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(DisplayFriendsActivity.class.getName(),
                        null, false);

        // Click the button
        friendsButton = activity.getFriendsButton();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                friendsButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final DisplayFriendsActivity friendsActivity = (DisplayFriendsActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", friendsActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                DisplayFriendsActivity.class, friendsActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        // Move to add item activity
        receiverActivityMonitor =
                getInstrumentation().addMonitor(EditFriendsListActivity.class.getName(),
                        null, false);

        // Click the button
        addFriendButton = friendsActivity.getAddFriendButton();
        friendsActivity.runOnUiThread(new Runnable() {
            public void run() {
                addFriendButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        EditFriendsListActivity nextReceiverActivity = (EditFriendsListActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", nextReceiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                EditFriendsListActivity.class, nextReceiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        EditFriendsListActivity addFriendtoDisplayFriendsActivity = (EditFriendsListActivity) getActivity();

        // Click the button
        final EditText editName = addFriendtoDisplayFriendsActivity.getFriendName();
        addFriendtoDisplayFriendsActivity.runOnUiThread(new Runnable() {
            public void run() {
                editName.setText("test friend");
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the button
        final Button saveFriendButton = addFriendtoDisplayFriendsActivity.getSaveButton();
        addFriendtoDisplayFriendsActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveFriendButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        Iterator<Friend> friendIterator = friendsActivity.getFriends().iterator();
        while (friendIterator.hasNext()) {
            assertEquals(friendIterator.next().getName(), "test friend");
        }
    }
    // Test method for adding a friend to your friends list
    public void testAddFriend() {
        FriendsList friendsList = new FriendsList();
        Friend friend = new Friend();
        friendsList.add(friend);
        assertTrue(friendsList.
                contains(friend));
    }

    // Test method for removing an item from your inventory
    public void testRemoveFriendUI() {
        HomePageActivity activity = (HomePageActivity) getActivity();

        // Code from : https://developer.android.com/training/activity-testing/activity-functional-testing.html#keyinput, 2015-10-14
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(DisplayFriendsActivity.class.getName(),
                        null, false);

        // Click the button
        friendsButton = activity.getFriendsButton();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                friendsButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final DisplayFriendsActivity friendsActivity = (DisplayFriendsActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", friendsActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                DisplayFriendsActivity.class, friendsActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        // Move to add item activity
        receiverActivityMonitor =
                getInstrumentation().addMonitor(EditFriendsListActivity.class.getName(),
                        null, false);

        // Click the button
        addFriendButton = friendsActivity.getAddFriendButton();
        friendsActivity.runOnUiThread(new Runnable() {
            public void run() {
                addFriendButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        EditFriendsListActivity nextReceiverActivity = (EditFriendsListActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", nextReceiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                EditFriendsListActivity.class, nextReceiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        EditFriendsListActivity addFriendtoDisplayFriendsActivity = (EditFriendsListActivity) getActivity();

        // Click the button
        final EditText editName = addFriendtoDisplayFriendsActivity.getFriendName();
        addFriendtoDisplayFriendsActivity.runOnUiThread(new Runnable() {
            public void run() {
                editName.setText("test friend");
            }
        });
        getInstrumentation().waitForIdleSync();

        // Click the button
        final Button saveFriendButton = addFriendtoDisplayFriendsActivity.getSaveButton();
        addFriendtoDisplayFriendsActivity.runOnUiThread(new Runnable() {
            public void run() {
                saveFriendButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        Iterator<Friend> friendIterator = friendsActivity.getFriends().iterator();
        while (friendIterator.hasNext()) {
            assertEquals(friendIterator.next().getName(), "test friend");
        }

        list = friendsActivity.getFriendsinFriendsList();
        friendsActivity.runOnUiThread(new Runnable() {
            public void run() {
                View firstItem = list.getChildAt(0);
                list.performItemClick(firstItem, 0, firstItem.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        EditFriendsListActivity nextReceiverDelActivity = (EditFriendsListActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", nextReceiverDelActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                EditFriendsListActivity.class, nextReceiverDelActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);
        final Button deleteButton = nextReceiverDelActivity.getDeleteButton();
        friendsActivity.runOnUiThread(new Runnable() {
            public void run() {
                deleteButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        assertNull(list.getChildAt(0));
    }

    // Test method for removing a friend from your friends list
    public void testRemoveFriend() {
        FriendsList friendsList = new FriendsList();
        Friend friend = new Friend();
        friendsList.add(friend);
        assertTrue(friendsList.contains(friend));
        friendsList.remove(friend);
        assertFalse(friendsList.contains(friend));
    }

    // Test method for checking if a user has a profile
    public void testHasProfile() {
        User user = new User();
        UserProfile userProfile = new UserProfile();
        user.setProfile(userProfile);
        assertEquals(user.getProfile(), userProfile);
    }

    //test method for checking if a friend has a profile
    public void testFriendHasProfile(){
        User user = new User();
        FriendsList friendsList = new FriendsList();
        Friend friend = new Friend();
        friendsList.add(friend);
        //assertTrue(friend.);
    }

    // Test method for checking if profile has contact information stored
    public void testContactInformation() {
        UserProfile userProfile = new UserProfile();
        ContactInfo contactInfo = new ContactInfo();
        assertNull(userProfile.getContactInfo());
        userProfile.setContactInfo(contactInfo);
        assertNotNull(userProfile.getContactInfo());
    }

    // Test method for checking if profile has city information stored
    public void testCityInformation() {
        UserProfile userProfile = new UserProfile();
        assertNull(userProfile.getCity());
        userProfile.setCity("Edmonton");
        assertEquals(userProfile.getCity(), "Edmonton");
    }

    /* Test method for checking if user removes contact info from profile
    public void testRemoveContactInformation() {
        UserProfile userProfile = new UserProfile();
        ContactInfo contactInfo = new ContactInfo();
        userProfile.setContactInfo();
        assertTrue(myProfile.hasContactInfo);
        myProfile.removeContactInfo(contactInfo);
        assertFalse(myProfile.hasContactInfo);
    }*/

    // Test method for checking if user removes city info from profile
    public void testRemoveCityInformation() {
        UserProfile userProfile = new UserProfile();
        userProfile.setCity("Edmonton");
        assertEquals(userProfile.getCity(), "Edmonton");
        userProfile.setCity(null);
        assertNull(userProfile.getCity());
    }

    //Test method for checking if  user has a tracked friends list
    public void testHasTrackedFriendsList() {
        User user = new User();
        TrackedFriendsList trackedFriendsList = new TrackedFriendsList();
        assertEquals(user.getTrackedFriends(), trackedFriendsList);
    }

    // Test if tracked friends list has a friend in it
    public void testHasTrackedFriends() {
        TrackedFriendsList trackedFriendsList = new TrackedFriendsList();
        Friend friend = new Friend();
        assertFalse(trackedFriendsList.contains(friend));
        trackedFriendsList.add(friend);
        assertTrue(trackedFriendsList.contains(friend));
    }

    // Test method to test if the tracked friends list is empty
    public void testNoTrackedFriends() {
        TrackedFriendsList trackedFriendsList = new TrackedFriendsList();
        assertTrue(trackedFriendsList.isEmpty());
        Friend friend = new Friend();
        trackedFriendsList.add(friend);
        assertFalse(trackedFriendsList.isEmpty());
    }

    // Test method for checking how many tracked friends a user has
    public void testNumberOfTrackedFriends() {
        TrackedFriendsList trackedFriendsList = new TrackedFriendsList();
        Friend friend1 = new Friend();
        trackedFriendsList.add(friend1);
        assertEquals(trackedFriendsList.size(), 1);
        Friend friend2 = new Friend();
        trackedFriendsList.add(friend2);
        assertEquals(trackedFriendsList.size(), 1);
        Friend friend3 = new Friend();
        trackedFriendsList.add(friend3);
        assertEquals(trackedFriendsList.size(), 3);
        Friend friend4 = new Friend();
        trackedFriendsList.add(friend4);
        assertEquals(trackedFriendsList.size(), 4);
        trackedFriendsList.remove(friend1);
        assertEquals(trackedFriendsList.size(), 3);
        trackedFriendsList.remove(friend2);
        assertEquals(trackedFriendsList.size(), 2);
    }

    // Test method for adding a friend to your friends list
    public void testAddTrackedFriend() {
        TrackedFriendsList trackedFriendsList = new TrackedFriendsList();
        Friend friend = new Friend();
        trackedFriendsList.add(friend);
        assertTrue(trackedFriendsList.contains(friend));
    }

    // Test method for removing a friend from your friends list
    public void testRemoveTrackedFriend() {
        TrackedFriendsList trackedFriendsList = new TrackedFriendsList();
        Friend friend = new Friend();
        trackedFriendsList.add(friend);
        assertTrue(trackedFriendsList.contains(friend));
        trackedFriendsList.remove(friend);
        assertFalse(trackedFriendsList.contains(friend));
    }
}
