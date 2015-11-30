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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.Inventory.Trinket.Trinket;

/**
 * Controller for handling interactions from the FriendsInventoryActivity.  The controller manages clicks
 * on the trinkets, which connects the user to the TrinketDetailsActivity.
 */

public class FriendsInventoryController {
    private FriendsInventoryActivity activity;

    /**
     * Constructs a controller with the activity this constructor is attached to.  Each controller
     * can only be used by one activity.
     *
     * @param activity - The activity this controller is attached to
     */
    public FriendsInventoryController(FriendsInventoryActivity activity) {
        this.activity = activity;
    }

    /**
     * Sets click listener for the items in the friends list ListView.  Will direct to the friend's profile activity.
     */
    public void friendsInventoryItemOnClick() {
        ListView friendsInventoryListView = activity.getInventoryItemsList();
        friendsInventoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> v, View view, int position, long id) {
                Trinket clickedTrinket = ApplicationState.getInstance().getClickedFriend().getActualFriend().getInventory().get(position);
                ApplicationState.getInstance().setClickedTrinket(clickedTrinket);
                Intent intent = new Intent(activity, FriendsTrinketDetailsActivity.class);
                activity.startActivity(intent);
            }
        });
    }

    public void friendsFilterButtonOnClick() {
        String category = activity.getCategorySpinner().getSelectedItem().toString();
        String textQuery = activity.getSearchBox().getText().toString();
        Inventory empty = new Inventory();
        activity.setInventory(empty);

        for (Trinket t : activity.getCompleteInventory()) {
            if ((t.getCategory() == category) && (t.getDescription().contains(textQuery) | t.getName().contains(textQuery))) {
                empty.add(t);
            }
        }
        activity.getTrinketArrayAdapter().notifyDataSetChanged();
    }

}
