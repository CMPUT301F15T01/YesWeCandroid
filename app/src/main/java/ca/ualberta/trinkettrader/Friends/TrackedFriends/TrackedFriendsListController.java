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

package ca.ualberta.trinkettrader.Friends.TrackedFriends;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.Friends.Friend;
import ca.ualberta.trinkettrader.Friends.FriendsListActivity;
import ca.ualberta.trinkettrader.Friends.FriendsProfileActivity;
import ca.ualberta.trinkettrader.User.LoggedInUser;

/**
 * Controller for TrackedFriendsListActivity. Used for selecting items in TrackedFriendsList View
 * and returning to the FriendsList View.
 */
public class TrackedFriendsListController {
    private TrackedFriendsListActivity activity;

    public TrackedFriendsListController(TrackedFriendsListActivity activity) {
        this.activity = activity;
    }

    /**
     * onClick method for the back button in order to return to FriendsListActivity.
     */
    public void backToFriendsListOnClick() {
        activity.onBackPressed();
    }

    /**
     * onClick method for the tracked friends radio button for selecting an item in the
     * FriendsList ListView.
     */
    public void setTrackedFriendsListViewItemOnClick() {
        ListView trackedFriendsListView = activity.getTrackedFriendsListView();
        trackedFriendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> v, View view, int position, long id) {
                Friend clickedFriend = LoggedInUser.getInstance().getTrackedFriendsList().get(position);
                ApplicationState.getInstance().setClickedFriend(clickedFriend);
                Intent intent = new Intent(activity, FriendsProfileActivity.class);
                activity.startActivity(intent);
            }
        });
    }
}
