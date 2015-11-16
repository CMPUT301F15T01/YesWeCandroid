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

package ca.ualberta.trinkettrader.Inventory;

import android.app.Activity;
import android.content.Intent;

import ca.ualberta.trinkettrader.Inventory.Trinket.AddOrEditTrinketActivity;

/**
 * Controller for handling interactions from the InventoryActivity.
 */
public class InventoryController {

    private Activity activity;

    /**
     * Constructs a controller with the activity this constructor is attached to.  Each controller
     * can only be used by one activity.
     *
     * @param activity - The activity this controller is attached to
     */
    public InventoryController(Activity activity) {
        this.activity = activity;
    }

    /**
     * Handles click from the "Add Trinket" button in the InventoryActivity.  Starts the
     * AddOrEditTrinketActivity with its trinket adding functionality (rather than its editing
     * functionality).
     */
    public void onAddItemClick() {
        Intent intent = new Intent(this.activity, AddOrEditTrinketActivity.class);
        intent.putExtra("activityName", "add");
        activity.startActivity(intent);
    }

    /**
     * Handles click from the "Inventory Details" button in the InventoryActivity.  Starts the
     * InventoryDetailsActivity.
     */
    public void onDetailsClick() {
        Intent intent = new Intent(this.activity, InventoryDetailsActivity.class);
        activity.startActivity(intent);
    }
}
