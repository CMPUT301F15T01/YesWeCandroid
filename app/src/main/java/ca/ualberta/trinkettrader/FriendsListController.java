package ca.ualberta.trinkettrader;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Me on 2015-11-02.
 */
public class FriendsListController {
    private DisplayFriendsActivity activity;

    public FriendsListController(DisplayFriendsActivity activity) {
        this.activity = activity;
    }

    /**
     * onClick method for searching and adding friends.
     */
    public void findFriendsOnClick() {
        EditText textField = activity.getFindFriendTextField();
        Friend newFriend = new Friend();
        String username = textField.getText().toString();

        // TODO testing for offline purposes only - will redo once web service intact
        newFriend.getProfile().setUsername(username);
        LoggedInUser.getInstance().getFriendsList().add(newFriend);
    }

    /**
     * Sets click listener for the items in the friends list ListView.  Will direct to the friend's profile activity.
     */
    public void setFriendsListViewItemOnClick() {
        ListView friendsListView = activity.getFriendsListView();
        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> v, View view, int position, long id) {
                Friend clickedFriend = LoggedInUser.getInstance().getFriendsList().get(position);
                ApplicationState.getInstance().setClickedFriend(clickedFriend);
                Intent intent = new Intent(activity, DisplayFriendsProfileActivity.class);
                activity.startActivity(intent);
            }
        });
    }

    /**
     * onClick method for the view tracked friends button.  Navigates to Tracked Friends List activity.
     */
    public void viewTrackedFriendsOnClick() {
        Intent intent = new Intent(activity, DisplayTrackedFriendsActivity.class);
        activity.startActivity(intent);
    }
}
