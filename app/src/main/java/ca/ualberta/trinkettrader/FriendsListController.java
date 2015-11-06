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
