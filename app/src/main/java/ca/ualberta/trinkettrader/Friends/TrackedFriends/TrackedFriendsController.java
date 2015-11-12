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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.Friends.Friend;
import ca.ualberta.trinkettrader.Friends.FriendsActivity;
import ca.ualberta.trinkettrader.Friends.FriendsProfileActivity;
import ca.ualberta.trinkettrader.User.LoggedInUser;

/**
 * Created by RV on 11/5/2015.
 */
public class TrackedFriendsController {
    private TrackedFriendsActivity activity;

    public TrackedFriendsController(TrackedFriendsActivity activity) {
        this.activity = activity;
    }

    /**
     * onClick method to return to FriendsListActivity.
     */
    public void backToFriendsListOnClick() {
        Intent intent = new Intent(this.activity, FriendsActivity.class);
        activity.startActivity(intent);
    }

    /**
     * onClick method for clicking an item in the FriendsList ListView.
     */
    public void setTrackedFriendsListViewItemOnClick() {
        ListView trackedFriendsListView = activity.getTrackedFriendsListView();
        trackedFriendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> v, View view, int position, long id) {
                Friend clickedFriend = LoggedInUser.getInstance().getFriendsList().get(position);
                ApplicationState.getInstance().setClickedFriend(clickedFriend);
                Intent intent = new Intent(activity, FriendsProfileActivity.class);
                activity.startActivity(intent);
            }
        });
    }
}
