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

/**
 * Created by Andrea McIntosh on 01/11/2015.
 */
public class HomePageController {

    Activity activity;

    public HomePageController(Activity activity) {
        this.activity = activity;
    }

    public void onInventoryClick() {
        Intent intent = new Intent(this.activity, DisplayInventoryActivity.class);
        activity.startActivity(intent);
    }

    public void onFriendsClick() {
        Intent intent = new Intent(this.activity, DisplayFriendsActivity.class);
        activity.startActivity(intent);
    }

    public void onTradesClick() {
        Intent intent = new Intent(this.activity, DisplayTradesActivity.class);
        activity.startActivity(intent);
    }

    public void onProfileClick() {
        Intent intent = new Intent(this.activity, DisplayUserProfileActivity.class);
        activity.startActivity(intent);
    }
}
