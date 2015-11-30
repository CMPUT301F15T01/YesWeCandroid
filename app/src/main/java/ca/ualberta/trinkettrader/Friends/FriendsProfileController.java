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

package ca.ualberta.trinkettrader.Friends;

import android.content.Intent;
import android.widget.RadioButton;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.User.LoggedInUser;

/**
 * Controller for handling interactions from the FriendsProfileActivity.  The controller manages clicks to
 * the "Remove Friend", "Back" and "Track Friend" buttons in the FriendsProfileActivity's layout.
 * Clicking the remove button will remove the friend whose profile was being viewed in the
 * FriendsProfileActivity from the user's friend's list, the track button toggles whether the user
 * is tracking that friend or not, and the back button returns the user back to the previous
 * FriendsListActivity.
 */
public class FriendsProfileController {
    private FriendsProfileActivity activity;

    /**
     * Constructs a controller with the activity this constructor is attached to.  Each controller
     * can only be used by one activity.
     *
     * @param activity - The activity this controller is attached to
     */
    public FriendsProfileController(FriendsProfileActivity activity) {
        this.activity = activity;
    }

    /**
     * Handles removing the friend displayed in the FriendsProfileActivity from the user's
     * friends list after the user clicks the "Delete Friend" button.  The system uses a state
     * variable that remembers which friend in the user's friends list was clicked on last to know
     * which friend to remove.
     * If the friend is being tracked, remove the friend from the tracked friends list as well.
     */
    public void removeFriendButtonOnClick() {
        Friend exFriend = ApplicationState.getInstance().getClickedFriend();
        LoggedInUser.getInstance().getFriendsList().remove(exFriend);

        if (exFriend.isTracked()) {
            LoggedInUser.getInstance().getTrackedFriendsList().remove(exFriend);
        }

        ApplicationState.getInstance().setClickedFriend(null);

        activity.onBackPressed();
    }

    /**
     * Handles the "View Inventory" button click in the FriendsProfileActivity.
     * Sends the user to the FriendsInventoryActivity.
     */
    public void viewInventoryButtonOnClick() {
        Intent intent = new Intent(activity, FriendsInventoryActivity.class);
        activity.startActivity(intent);
    }

    /**
     * Handles click for the "Track Friends" radio button in the FriendsProfileActivity.  This
     * button toggles whether a friend is tracked by the user or not.  If the friend was untracked
     * by the user before the click, they will become tracked.  If the friend was tracked before the
     * click, they will be removed from the user's tacked friends list and will become untracked.
     * The system uses an state variable that remembers which friend in the user's friends list was
     * clicked on last to know which friend to track/untrack.
     */
    public void trackedRadioButtonOnClick() {
        RadioButton tracked = activity.getTrackedRadioButton();
        Friend clickedFriend = ApplicationState.getInstance().getClickedFriend();
        Boolean wasChecked = clickedFriend.isTracked();
        tracked.setChecked(!wasChecked);
        clickedFriend.setIsTracked(!wasChecked);
        if (wasChecked) {
            LoggedInUser.getInstance().getTrackedFriendsList().remove(clickedFriend);
        } else {
            LoggedInUser.getInstance().getTrackedFriendsList().add(clickedFriend);
        }
    }

    /**
     * Handles click to the "Back To Friends List" button, which returns the user to their to
     * FriendsListActivity.  Any changes made to the friends list, such as untracking the friend,
     * will be shown in the FriendsListActivity they return to.
     */
    public void backToFriendsListButtonFromProfileOnClick() {
        activity.onBackPressed();
    }

    /**
     * Sets the information text views for the Friend's profile data. Activity, on create, will
     * get the Friend's data from the network, if the friend made any changes to their profile.
     */
    public void updateFields() {
        activity.getUsernameTextView().setText(activity.getFriend().getProfile().getUsername());
        activity.getNameTextView().setText(activity.getFriend().getProfile().getName());
        activity.getEmailTextView().setText(activity.getFriend().getProfile().getEmail());
        activity.getCityInfoTextView().setText(activity.getFriend().getProfile().getCity());
        activity.getPhoneTextView().setText(activity.getFriend().getProfile().getContactInfo().getPhoneNumber());
        activity.getPostalCodeInfoTextView().setText(activity.getFriend().getProfile().getPostalCode());
        activity.getTrackedRadioButton().setChecked(ApplicationState.getInstance().getClickedFriend().isTracked());
    }
}
