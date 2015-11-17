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

package ca.ualberta.trinkettrader.Friends;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.widget.RadioButton;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.User.LoggedInUser;
import ca.ualberta.trinkettrader.User.User;

/**
 * Created by Me on 2015-11-05.
 */
public class FriendsProfileController {
    private FriendsProfileActivity activity;

    public FriendsProfileController(FriendsProfileActivity activity) {
        this.activity = activity;
    }

    /**
     * onClick method for removing friends.
     */
    public void removeFriendButtonOnClick() {
        Friend exFriend = ApplicationState.getInstance().getClickedFriend();
        LoggedInUser.getInstance().getFriendsList().remove(exFriend);

        if (exFriend.isTracked()) {
            LoggedInUser.getInstance().getTrackedFriendsList().remove(exFriend);
        }

        activity.onBackPressed();
    }

    /**
     * onCLick method for tracking/untracking friends.
     */
    public void trackedRadioButtonOnClick() {
        RadioButton tracked = activity.getTrackedRadioButton();
        Friend clickedFriend = ApplicationState.getInstance().getClickedFriend();
        Boolean wasChecked = clickedFriend.isTracked();
        tracked.setChecked(!wasChecked);
        clickedFriend.setIsTracked(!wasChecked);
        if (wasChecked) {
            LoggedInUser.getInstance().getTrackedFriendsList().remove(clickedFriend);
        } else {
            LoggedInUser.getInstance().getTrackedFriendsList().add(clickedFriend);
        }
    }

    /**
     * onClick method for returning to Friends List activity.
     */
    public void backToFriendsListButtonFromProfileOnClick() {
        activity.onBackPressed();
    }
}
