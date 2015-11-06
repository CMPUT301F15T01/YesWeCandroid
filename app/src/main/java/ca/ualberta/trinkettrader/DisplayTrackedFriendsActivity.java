package ca.ualberta.trinkettrader;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class DisplayTrackedFriendsActivity extends AppCompatActivity {

    private TrackedFriendsList trackedFriendsList;
    private ArrayAdapter<Friend> trackedFriendAdapter;
    private ListView trackedFriendsListView;
    private Button backToFriendsListButton;
    private DisplayTrackedFriendsController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_tracked_friends);

        trackedFriendsList = LoggedInUser.getInstance().getTrackedFriends();
        trackedFriendsListView = (ListView) findViewById(R.id.trackedFriendsListView);
        backToFriendsListButton = (Button) findViewById(R.id.backToFriendsListButton);
        controller = new DisplayTrackedFriendsController(this);
        controller.setTrackedFriendsListViewItemOnClick();
    }

    @Override
    protected void onStart() {
        super.onStart();
        trackedFriendAdapter = new ArrayAdapter<Friend>(this, R.layout.listview_item, trackedFriendsList);
        trackedFriendsListView.setAdapter(trackedFriendAdapter);
    }

    public void backToFriendsListButtonOnClick(View v) {
        controller.backToFriendsListOnClick();
    }

    /** Returns the back button to go back to the friends list page.
     *
     * @return back button
     */
    public Button getBackToFriendsListButton() {
        return backToFriendsListButton;
    }

    public ListView getTrackedFriendsListView() {
        return trackedFriendsListView;
    }

}
