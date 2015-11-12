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

import ca.ualberta.trinkettrader.Friends.FriendsListActivity;
import ca.ualberta.trinkettrader.Inventory.InventoryActivity;
import ca.ualberta.trinkettrader.Trades.TradesActivity;
import ca.ualberta.trinkettrader.User.Profile.UserProfileActivity;

public class HomePageController {

    Activity activity;

    public HomePageController(Activity activity) {
        this.activity = activity;
    }

    /**
     * Directs to InventoryActivity.
     */
    public void onInventoryClick() {
        Intent intent = new Intent(this.activity, InventoryActivity.class);
        activity.startActivity(intent);
    }

    /**
     * Direct to DisplayFriendsActivity.
     */
    public void onFriendsClick() {
        Intent intent = new Intent(this.activity, FriendsListActivity.class);
        activity.startActivity(intent);
    }

    /**
     * Directs to TradesActivity.
     */
    public void onTradesClick() {
        Intent intent = new Intent(this.activity, TradesActivity.class);
        activity.startActivity(intent);
    }

    /**
     * Directs to UserProfileActivity.
     */
    public void onProfileClick() {
        Intent intent = new Intent(this.activity, UserProfileActivity.class);
        activity.startActivity(intent);
    }
}
