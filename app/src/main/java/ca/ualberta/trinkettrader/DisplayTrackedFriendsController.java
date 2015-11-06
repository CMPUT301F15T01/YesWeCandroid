package ca.ualberta.trinkettrader;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;

/**
 * Created by RV on 11/5/2015.
 */
public class DisplayTrackedFriendsController {
    private DisplayTrackedFriendsActivity activity;

    public DisplayTrackedFriendsController(DisplayTrackedFriendsActivity activity) {
        this.activity = activity;
    }

    public void backToFriendsListOnClick() {
        Intent intent = new Intent(this.activity, DisplayFriendsActivity.class);
        activity.startActivity(intent);
    }

    public void setTrackedFriendsListViewItemOnClick() {
        ListView trackedFriendsListView = activity.getTrackedFriendsListView();
        trackedFriendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> v, View view, int position, long id) {
                Friend clickedFriend = LoggedInUser.getInstance().getFriendsList().get(position);
                ApplicationState.getInstance().setClickedFriend(clickedFriend);
                Intent intent = new Intent(activity, DisplayFriendsProfileActivity.class);
                activity.startActivity(intent);
            }
        });
    }
}
