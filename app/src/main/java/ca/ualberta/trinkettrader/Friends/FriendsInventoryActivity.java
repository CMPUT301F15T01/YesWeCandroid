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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.Inventory.Trinket.Trinket;
import ca.ualberta.trinkettrader.R;
import ca.ualberta.trinkettrader.User.LoggedInUser;

/**
 * Activity to display all publicly shared trinkets in a friend's inventory.
 * The activity fills the screen with trinkets from the current clickedFriend in ApplicationState.
 * Items in the friend's inventory can be clicked to view details of the item. This will link
 * to another activity.
 */
public class FriendsInventoryActivity extends AppCompatActivity {

    private ArrayAdapter<Trinket> trinketArrayAdapter;
    private Inventory inventory;
    private ListView inventoryItemsList;

    private FriendsInventoryController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_inventory);

        this.controller = new FriendsInventoryController(this);
        this.inventory = ApplicationState.getInstance().getClickedFriend().getActualFriend().getInventory();
        this.inventoryItemsList = (ListView) findViewById(R.id.friendsDisplayedTrinkets);

        trinketArrayAdapter = new ArrayAdapter<>(this, R.layout.activity_inventory_trinket, inventory);
        inventoryItemsList.setAdapter(trinketArrayAdapter);

        final FriendsInventoryActivity activity = this;
        // When a trinket in the ListView is clicked, user is directed to its TrinketDetailsActivity
        inventoryItemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> v, View view, int position, long id) {
                Friend clickedFriend = ApplicationState.getInstance().getClickedFriend();
                String friendsUsername = clickedFriend.getActualFriend().getProfile().getUsername();
                Trinket clickedTrinket = LoggedInUser.getInstance().getFriendsList().getFriendByUsername(friendsUsername).getActualFriend().getInventory().get(position);
                ApplicationState.getInstance().setClickedTrinket(clickedTrinket);
                Intent intent = new Intent(FriendsInventoryActivity.this, FriendsTrinketDetailsActivity.class);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        trinketArrayAdapter.notifyDataSetChanged();
    }

    /**
     * Returns the current user's inventory, which is being displayed in this activity.
     *
     * @return Inventory - the current user's inventory, which is being displayed by this activity
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Returns the ListView element displaying the current user's inventory.
     *
     * @return Android ListView - the layout element responsible for displaying the user's inventory
     * in this activity
     */
    public ListView getInventoryItemsList() {
        return inventoryItemsList;
    }

}
