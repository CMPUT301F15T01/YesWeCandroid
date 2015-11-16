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

import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.NotificationManager;
import ca.ualberta.trinkettrader.Friends.TrackedFriends.TrackedFriendsList;
import ca.ualberta.trinkettrader.Trades.TradeManager;
import ca.ualberta.trinkettrader.User.User;
import ca.ualberta.trinkettrader.User.Profile.UserProfile;

public class Friend extends User {

    private Boolean isTracked;

    public Friend() {
        super();
        this.isTracked = Boolean.FALSE;
    }

    public Friend(FriendsList friendsList, Inventory inventory, NotificationManager notificationManager, UserProfile profile, TrackedFriendsList trackedFriends, TradeManager tradeManager, Boolean isTracked) {
        super(friendsList, inventory, notificationManager, profile, trackedFriends, tradeManager);
        this.isTracked = isTracked;
    }

    public Friend(String username) {
        // this constructor will probably be unnecessary and removed once the webservice is working
        super();
        this.getProfile().setUsername(username);
        this.isTracked = Boolean.FALSE;
    }

    /**
     * Returns boolean indicating whether a friend is tracked or not.
     *
     * @return Boolean
     */
    public Boolean isTracked() {
        return isTracked;
    }

    /**
     * Sets if friend is tracked or not. To be called when adding a new friend to current User's
     * TrackedFriendList
     *
     * @param isTracked
     */
    public void setIsTracked(Boolean isTracked) {
        this.isTracked = isTracked;
    }

    @Override
    public String toString() {
        return this.getProfile().getUsername();
    }
}
