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

    public void findFriendsOnClick() {
        EditText textField = activity.getFindFriendTextField();
        Friend newFriend = new Friend();
        String username = textField.getText().toString();
        // TODO testing for offline purposes only - will redo once web service intact
        newFriend.getProfile().setUsername(username);
        LoggedInUser.getInstance().friendsList.add(newFriend);
    }

    public void setFriendsListViewItemOnClick() {
        ListView friendsListView = activity.getFriendsListView();
        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> v, View view, int position, long id) {
                Intent intent = new Intent(activity, DisplayFriendsProfileActivity.class);
                activity.startActivity(intent);
            }
        });
    }
}

