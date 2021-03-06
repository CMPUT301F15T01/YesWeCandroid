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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.R;
import ca.ualberta.trinkettrader.User.User;

/**
 * Android activity for displaying a user's friends profile. Displays the friends details,
 * such as their username, name, etc. It also displays whether the friend is currently tracked,
 */

public class FriendsProfileActivity extends Activity implements Observer {

    private User friend;
    private Button removeFriendButton;
    private Button backButton;
    private RadioButton trackedRadioButton;
    private TextView usernameTextView;
    private TextView nameTextView;
    private FriendsProfileController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_profile);
        friend = ApplicationState.getInstance().getClickedFriend().getActualFriend();
        removeFriendButton = (Button) findViewById(R.id.removeFriendButton);
        backButton = (Button) findViewById(R.id.backToFriendsListFromProfile);
        trackedRadioButton = (RadioButton) findViewById(R.id.trackedRadioButton);
        controller = new FriendsProfileController(this);

        usernameTextView = (TextView) findViewById(R.id.usernameText);
        nameTextView = (TextView) findViewById(R.id.nameText);

        updateFields();
    }

    private void updateFields() {
        this.getUsernameTextView().setText(friend.getProfile().getUsername());
        trackedRadioButton.setChecked(ApplicationState.getInstance().getClickedFriend().isTracked());
    }

    /**
     * Gets textView for displaying friend's username.
     *
     * @return TextView
     */
    public TextView getUsernameTextView() {
        return usernameTextView;
    }

    /**
     * Gets textView for displaying friend's name.
     *
     * @return TextView
     */
    public TextView getNameTextView() {
        return nameTextView;
    }

    /**
     * Gets button for removing friends.
     *
     * @return Button
     */
    public Button getRemoveFriendButton() {
        return removeFriendButton;
    }

    /**
     * Returns RadioButton that indicates if a friend is tracked or not.
     *
     * @return RadioButton
     */
    public RadioButton getTrackedRadioButton() {
        return trackedRadioButton;
    }

    /**
     * Directs controller to handle Remove Friends button click.
     *
     * @param v remove friends button
     */
    public void removeFriendButtonOnClick(View v) {
        controller.removeFriendButtonOnClick();
    }

    /**
     * On-click method for the View Inventory button on the Friend's Profile page.
     * Directs controller to handle the View Inventory button click.
     *
     * @param v the View Inventory button in the FriendsProfileActivity
     */
    public void viewInventoryButtonOnClick(View v) {
        controller.viewInventoryButtonOnClick();
    }

    /**
     * Directs controller to handle track friends radio button click.
     *
     * @param v track friends radio button
     */
    public void trackedRadioButtonOnClick(View v) {
        controller.trackedRadioButtonOnClick();
    }

    /**
     * Directs controller to handle click to return to Friends List activity.
     *
     * @param v back to friends list button
     */
    public void backToFriendsListButtonFromProfileOnClick(View v) {
        controller.backToFriendsListButtonFromProfileOnClick();
    }

    /**
     * Gets button that directs back to friends list activity.
     *
     * @return Button
     */
    public Button getBackButton() {
        return backButton;
    }

    /**
     * This method is called if the specified {@code Observable} object's
     * {@code notifyObservers} method is called (because the {@code Observable}
     * object has been updated.
     *
     * @param observable the {@link Observable} object.
     * @param data       the data passed to {@link Observable#notifyObservers(Object)}.
     */
    @Override
    public void update(Observable observable, Object data) {

    }
}
