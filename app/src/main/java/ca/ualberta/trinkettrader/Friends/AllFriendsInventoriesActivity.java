package ca.ualberta.trinkettrader.Friends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.HashMap;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.Inventory.Trinket.Trinket;
import ca.ualberta.trinkettrader.R;
import ca.ualberta.trinkettrader.User.LoggedInUser;

public class AllFriendsInventoriesActivity extends Activity {

    private ArrayAdapter<Trinket> trinketArrayAdapter;
    private Inventory inventory;
    private ListView inventoryItemsListView;
    private AllFriendsInventoriesController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_friends_inventories);

        this.controller = new AllFriendsInventoriesController(this);
        this.inventory = new Inventory();
        FriendsList friends = LoggedInUser.getInstance().getFriendsList();
        final HashMap<Trinket, Friend> trinketToUserMap = new HashMap<>();
        for (Friend f : friends) {
            for (Trinket t : f.getActualFriend().getInventory()) {
                this.inventory.add(t);
                trinketToUserMap.put(t, f);
            }
            this.inventory.addAll(f.getActualFriend().getInventory());
        }
        this.inventoryItemsListView = (ListView) findViewById(R.id.allFriendsDisplayedTrinkets);

        trinketArrayAdapter = new ArrayAdapter<>(this, R.layout.activity_inventory_trinket, inventory);
        inventoryItemsListView.setAdapter(trinketArrayAdapter);

        final AllFriendsInventoriesActivity activity = this;
        // When a trinket in the ListView is clicked, user is directed to its TrinketDetailsActivity
        inventoryItemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> v, View view, int position, long id) {
                Trinket selectedTrinket = inventory.get(position);
                Friend owner = trinketToUserMap.get(selectedTrinket);

                ApplicationState.getInstance().setClickedTrinket(selectedTrinket);
                ApplicationState.getInstance().setClickedFriend(owner);

                Intent intent = new Intent(AllFriendsInventoriesActivity.this, FriendsTrinketDetailsActivity.class);
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
    public ListView getInventoryItemsListView() {
        return inventoryItemsListView;
    }

}
