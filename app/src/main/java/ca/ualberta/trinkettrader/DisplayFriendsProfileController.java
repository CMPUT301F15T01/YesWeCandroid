package ca.ualberta.trinkettrader;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

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

    public void trackedRadioButtonOnClick() {
        RadioButton tracked = activity.getTrackedRadioButton();
        Friend clickedFriend = ApplicationState.getInstance().getClickedFriend();
        Boolean wasChecked = clickedFriend.isTracked();
        tracked.setChecked(!wasChecked);
        clickedFriend.setIsTracked(!wasChecked);
        if(wasChecked) {
            LoggedInUser.getInstance().getTrackedFriends().remove(clickedFriend);
        } else {
            LoggedInUser.getInstance().getTrackedFriends().add(clickedFriend);
        }
    }

    public void backToFriendsListButtonFromProfileOnClick() {
        NavUtils.navigateUpFromSameTask(activity);
    }
}
