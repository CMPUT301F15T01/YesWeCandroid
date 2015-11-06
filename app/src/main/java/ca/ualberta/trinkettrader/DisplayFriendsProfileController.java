package ca.ualberta.trinkettrader;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by Me on 2015-11-05.
 */
public class DisplayFriendsProfileController {
    private DisplayFriendsProfileActivity activity;

    public DisplayFriendsProfileController(DisplayFriendsProfileActivity activity) {
        this.activity = activity;
    }

    public void removeFriendButtonOnClick() {
        User exFriend = ApplicationState.getInstance().getClickedFriend();
        LoggedInUser.getInstance().getFriendsList().remove(exFriend);
        Intent intent = new Intent(this.activity, DisplayFriendsActivity.class);
        activity.startActivity(intent);
    }

    public void setFriendsListViewItemOnClick() {

    }
}
