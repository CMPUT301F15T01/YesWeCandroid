package ca.ualberta.trinkettrader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayFriendsProfileActivity extends AppCompatActivity {

    private User friend;
    private Button removeFriendButton;
    private Button trackFriendButton;
    private DisplayFriendsProfileController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_friends_profile);
        friend = ApplicationState.getInstance().getClickedFriend();
        removeFriendButton = (Button)findViewById(R.id.removeFriendButton);
        trackFriendButton = (Button)findViewById(R.id.trackFriendButton);
        controller = new DisplayFriendsProfileController(this);

        updateFields();
    }

    private void updateFields() {
        TextView usernameText = (TextView)findViewById(R.id.usernameText);
        usernameText.setText(friend.getProfile().getUsername());

    }

    public Button getRemoveFriendButton() {
        return removeFriendButton;
    }

    public Button getTrackFriendButton() {
        return trackFriendButton;
    }

    public void removeFriendButtonOnClick(View v) {
        controller.removeFriendButtonOnClick();
    }

    public void trackFriendButtonOnClick(View v) {

    }







}
