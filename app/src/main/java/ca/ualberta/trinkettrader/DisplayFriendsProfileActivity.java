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

    public Button getRemoveFriendButton() {
        return removeFriendButton;
    }

    public RadioButton getTrackedRadioButton() {
        return trackedRadioButton;
    }

    public void removeFriendButtonOnClick(View v) {
        controller.removeFriendButtonOnClick();
    }

    public void trackedRadioButtonOnClick(View v) {
        controller.trackedRadioButtonOnClick();
    }

    public void backToFriendsListButtonFromProfileOnClick(View v) {
        controller.backToFriendsListButtonFromProfileOnClick();
    }

    public Button getBackButton() {
        return backButton;
    }

    public TextView getUsernameTextView() {
        return usernameTextView;
    }






}
