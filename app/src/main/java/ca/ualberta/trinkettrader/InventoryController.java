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

public class InventoryController {

    private Activity activity;

    public InventoryController(Activity activity) {
        this.activity = activity;
    }

    /**
     * Handles click to add an item to inventory.
     */
    public void onAddItemClick() {
        Intent intent = new Intent(this.activity, AddOrEditTrinketActivity.class);
        intent.putExtra("activityName", "add");
        activity.startActivity(intent);
    }

    /**
     * Handles click to on an inventory ListView item to view item's details.
     */
    public void onDetailsClick() {
        Intent intent = new Intent(this.activity, InventoryDetailsActivity.class);
        activity.startActivity(intent);
    }
}
