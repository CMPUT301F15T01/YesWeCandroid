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

public class HomePageController {

    Activity activity;

    public HomePageController(Activity activity) {
        this.activity = activity;
    }

    /**
     * Directs to DisplayInventoryActivity.
     */
    public void onInventoryClick() {
        Intent intent = new Intent(this.activity, InventoryActivity.class);
        activity.startActivity(intent);
    }

    /**
     * Direct to DisplayFriendsActivity.
     */
    public void onFriendsClick() {
        Intent intent = new Intent(this.activity, FriendsActivity.class);
        activity.startActivity(intent);
    }

    /**
     * Directs to DisplayTradesActivity.
     */
    public void onTradesClick() {
        Intent intent = new Intent(this.activity, TradesActivity.class);
        activity.startActivity(intent);
    }

    /**
     * Directs to DisplayUserProfileActivity.
     */
    public void onProfileClick() {
        Intent intent = new Intent(this.activity, UserProfileActivity.class);
        activity.startActivity(intent);
    }
}
