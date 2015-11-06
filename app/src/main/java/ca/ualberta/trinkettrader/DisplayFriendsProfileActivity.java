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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class DisplayFriendsProfileActivity extends AppCompatActivity {

    private User friend;
    private Button removeFriendButton;
    private Button backButton;
    private RadioButton trackedRadioButton;
    private TextView usernameTextView;
    private DisplayFriendsProfileController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_friends_profile);
        friend = ApplicationState.getInstance().getClickedFriend();
        removeFriendButton = (Button)findViewById(R.id.removeFriendButton);
        backButton = (Button) findViewById(R.id.backToFriendsListFromProfile);
        trackedRadioButton = (RadioButton)findViewById(R.id.trackedRadioButton);
        controller = new DisplayFriendsProfileController(this);

        usernameTextView = (TextView) findViewById(R.id.usernameText);

        updateFields();
    }

    private void updateFields() {
        this.getUsernameTextView().setText(friend.getProfile().getUsername());
        trackedRadioButton.setChecked(ApplicationState.getInstance().getClickedFriend().isTracked());
    }

    /**
     * Gets button for removing friends.
     * @return Button
     */
    public Button getRemoveFriendButton() {
        return removeFriendButton;
    }

    /**
     * Returns RadioButton that indicates if a friend is tracked or not.
     * @return RadioButton
     */
    public RadioButton getTrackedRadioButton() {
        return trackedRadioButton;
    }

    /**
     * Directs controller to handle Remove Friends button click.
     * @param v
     */
    public void removeFriendButtonOnClick(View v) {
        controller.removeFriendButtonOnClick();
    }

    /**
     * Directs controller to handle track friends radio button click.
     * @param v
     */
    public void trackedRadioButtonOnClick(View v) {
        controller.trackedRadioButtonOnClick();
    }

    /**
     * Directs controller to handle click to return to Friends List activity.
     * @param v
     */
    public void backToFriendsListButtonFromProfileOnClick(View v) {
        controller.backToFriendsListButtonFromProfileOnClick();
    }

    /**
     * Gets button that directs back to friends list activity.
     * @return Button
     */
    public Button getBackButton() {
        return backButton;
    }

    /**
     * Gets textView for displaying friend's username.
     * @return TextView
     */
    public TextView getUsernameTextView() {
        return usernameTextView;
    }






}
