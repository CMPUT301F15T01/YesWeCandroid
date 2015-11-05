package ca.ualberta.trinkettrader;

import android.app.Activity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Me on 2015-11-02.
 */
public class FriendsListController {
    private Activity activity;

    public FriendsListController(Activity activity) {
        this.activity = activity;
    }

    public void findFriendsOnClick() {
        Log.d("XXXXXXXXXX1", "entered");
        Friend newFriend = new Friend();
        Log.d("XXXXXXXXXX2", "before activtity stuff");
        EditText view = (EditText) activity.findViewById(R.id.findFriendTextField);
        Log.d("XXXXXXXXXX3", "after");
        String username = view.getText().toString();
        // TODO testing for offline purposes only - will redo once web service intact
        newFriend.getProfile().setName(username);
        LoggedInUser.getInstance().friendsList.add(newFriend);

    }
}

