package ca.ualberta.trinkettrader;
import android.test.ActivityInstrumentationTestCase2;

/**
 Copyright 2015 Andrea McIntosh, Dylan Ashley, Anju Eappen, Jenna Hatchard, Kirsten Svidal, Raghav Vamaraju
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

public class FriendsTests extends ActivityInstrumentationTestCase2{

    public FriendsTests(Class activityClass) {
        super(activityClass);
    }

    // Test method for checking if a user has a friends list
    public void testHasFriendsList() {
        User user = new User();
        FriendsList friendsList = new FriendsList();
        user.setFriendsList(friendsList);
        assertTrue(user.hasFriendsList);
    }

    // Test if friends list has a friend in it
    public void testHasFriends() {
        FriendsList friendsList = new FriendsList();
        Friends friend = new Friends();
        assertFalse(friendsList.hasFriend(friend));
        friendsList.add(friend);
        assertTrue(friendsList.hasFriend(friend));
    }

    // Test method to test if an friends list has no friends in it
    public void testNoFriends() {
        FriendsList friendsList = new FriendsList();
        assertTrue(friendsList.isEmpty());
        Friends friend1 = new Friends();
        friendsList.addFriend(friend1);
        assertFalse(friendsList.isEmpty());
    }

    // Test method for checking how many friends a user has
    public void testNumberOfFriends() {
        FriendsList friendsList = new FriendsList();
        Friends friend1 = new Friends();
        friendsList.addFriend(friend1);
        assertTrue(friendsList.size().equals(1));
        Friends friend2 = new Friends();
        friendsList.addFriend(friend2);
        assertTrue(friendsList.size().equals(2));
        Friends friend3 = new Friends();
        friendsList.addFriend(friend3);
        assertTrue(friendsList.size().equals(3));
        Friends friend4 = new Friends();
        friendsList.addFriend(friend4);
        assertTrue(friendsList.size().equals(4));
        friendsList.removeFriend(friend1);
        assertTrue(friendsList.size().equals(3));
        friendsList.removeFriend(friend2);
        assertTrue(friendsList.size().equals(2));
    }


    // Test method for adding a friend to your friends list
    public void testAddFriend() {
        FriendsList friendsList = new FriendsList();
        Friends friend = new Friends();
        friendsList.addFriend(friend);
        assertTrue(friendsList.hasFriend(friend));
    }

    // Test method for removing a friend from your friends list
    public void testRemoveFriend() {
        FriendsList friendsList = new FriendsList();
        Friends friend = new Friends();
        friendsList.addFriend(friend);
        assertTrue(friendsList.hasFriend(friend));
        friendsList.removeFriend(friend2);
        assertFalse(friendsList.hasFriend(friend));
    }

    // Test method for checking if a user has a profile
    public void testHasProfile() {
        User user = new User();
        MyProfile myProfile = new MyProfile();
        user.setProfile(myProfile);
        assertTrue(user.hasProfile);
    }

    //test method for checking if a friend has a profile
    public void testFriendHasProfile(){
        User user = new User();
        FriendsList friendsList = new FriendsList();
        Friends friend = new Friends();
        friendsList.addFriend(friend);
        assertTrue(friend.hasProfile);
    }

    // Test method for checking if profile has contact information stored
    public void testContactInformation() {
        MyProfile myProfile = new MyProfile();
        ContactInfo contactInfo = new ContactInfo();
        assertFalse(myProfile.hasContactInfo);
        myProfile.addContactInfo(contactInfo);
        assertTrue(myProfile.hasContactInfo);
    }

    // Test method for checking if profile has city information stored
    public void testCityInformation() {
        MyProfile myProfile = new MyProfile();
        CityInfo cityInfo = new CityInfo();
        assertFalse(myProfile.hasCityInfo);
        myProfile.addCityInfo(cityInfo);
        assertTrue(myProfile.hasCityInfo);
    }

    // Test method for checking if user removes contact info from profile
    public void testRemoveContactInformation() {
        MyProfile myProfile = new MyProfile();
        ContactInfo contactInfo = new ContactInfo();
        myProfile.addContactInfo(contactInfo);
        assertTrue(myProfile.hasContactInfo);
        myProfile.removeContactInfo(contactInfo);
        assertFalse(myProfile.hasContactInfo);
    }

    // Test method for checking if user removes city info from profile
    public void testRemoveCityInformation() {
        MyProfile myProfile = new MyProfile();
        CityInfo cityInfo = new CityInfo();
        myProfile.addCityInfo(cityInfo);
        assertTrue(myProfile.hasCityInfo);
        myProfile.removeCityInfo(cityInfo);
        assertFalse(myProfile.hasCityInfo);
    }

    //Test method for checking if  user has a tracked friends list
    public void testHasTrackedFriendsList() {
        User user = new User();
        TrackedFriendsList trackedFriendsList = new TrackedFriendsList();
        user.setTrackedFriendsList(trackedFriendsList);
        assertTrue(user.hasTrackedFriendsList);
    }

    // Test if tracked friends list has a friend in it
    public void testHasTrackedFriends() {
        TrackedFriendsList trackedFriendsList = new TrackedFriendsList();
        Friends friend = new Friends();
        assertFalse(trackedFriendsList.hasTrackedFriend(friend));
        trackedFriendsList.add(friend);
        assertTrue(trackedFriendsList.hasTrackedFriend(friend));
    }

    // Test method to test if the tracked friends list is empty
    public void testNoTrackedFriends() {
        TrackedFriendsList trackedFriendsList = new TrackedFriendsList();
        assertTrue(trackedFriendsList.isEmpty());
        Friends friend = new Friends();
        trackedFriendsList.addTrackedFriend(friend);
        assertFalse(trackedFriendsList.isEmpty());
    }

    // Test method for checking how many tracked friends a user has
    public void testNumberOfTrackedFriends() {
        TrackedFriendsList trackedFriendsList = new TrackedFriendsList();
        Friends friend1 = new Friends();
        trackedFriendsList.addTrackedFriend(friend1);
        assertTrue(trackedFriendsList.size().equals(1));
        Friends friend2 = new Friends();
        trackedFriendsList.addTrackedFriend(friend2);
        assertTrue(trackedFriendsList.size().equals(1));
        Friends friend3 = new Friends();
        trackedFriendsList.addTrackedFriend(friend3);
        assertTrue(trackedFriendsList.size().equals(3));
        Friends friend4 = new Friends();
        trackedFriendsList.addTrackedFriend(friend4);
        assertTrue(trackedFriendsList.size().equals(4));
        trackedFriendsList.removeTrackedFriend(friend1);
        assertTrue(trackedFriendsList.size().equals(3));
        trackFriendsList.removeTrackedFriend(friend2);
        assertTrue(trackedFriendsList.size().equals(2));
    }

    // Test method for adding a friend to your friends list
    public void testAddTrackedFriend() {
        TrackedFriendsList trackedFriendsList = new TrackedFriendsList();
        Friends friend = new Friends();
        friendsList.addFriend(friend);
        assertTrue(friendsList.hasFriend(friend));
    }

    // Test method for removing a friend from your friends list
    public void testRemoveTrackedFriend() {
        TrackedFriendsList trackedFriendsList = new TrackedFriendsList();
        Friends friend = new Friends();
        trackedFriendsList.addTrackedFriend(friend);
        assertTrue(trackedFriendsList.hasTrackedFriend(friend));
        trackedFriendsList.removeTrackedFriend(friend);
        assertFalse(trackedFriendsList.hasTrackedFriend(friend));
    }

}
