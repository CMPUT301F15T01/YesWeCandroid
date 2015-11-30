package ca.ualberta.trinkettrader.Friends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.HashMap;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.Inventory.Trinket.Trinket;
import ca.ualberta.trinkettrader.R;
import ca.ualberta.trinkettrader.User.LoggedInUser;

public class AllFriendsInventoriesActivity extends Activity {

    private ArrayAdapter<Trinket> trinketArrayAdapter;
    private Inventory inventory;
    private Inventory completeInventory;
    private ListView inventoryItemsListView;
    private AllFriendsInventoriesController controller;
    private Button filterButton;
    private EditText searchBox;
    private Spinner categorySpinner;
    private ArrayAdapter<Trinket> autocompleteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_friends_inventories);

        this.controller = new AllFriendsInventoriesController(this);
        this.inventory = new Inventory();
        this.completeInventory = new Inventory();
        this.inventoryItemsListView = (ListView) findViewById(R.id.friendsDisplayedTrinkets);
        this.searchBox = (EditText) findViewById(R.id.search_box_all_friends);

        FriendsList friends = LoggedInUser.getInstance().getFriendsList();
        final HashMap<Trinket, Friend> trinketToUserMap = new HashMap<>();
        for (Friend f : friends) {
            for (Trinket t : f.getActualFriend().getInventory()) {
                this.inventory.add(t);
                this.completeInventory.add(t);
                trinketToUserMap.put(t, f);
            }
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
        //Dhawal Sodha Parmar; http://stackoverflow.com/questions/15804805/android-action-bar-searchview-as-autocomplete; 2015-29-11
        autocompleteAdapter = new ArrayAdapter<Trinket>(this, android.R.layout.simple_dropdown_item_1line, completeInventory);

        filterButton = (Button)findViewById(R.id.allFriendsFilterButtton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.friendsFilterButtonOnClick();
            }
        });
        categorySpinner = (Spinner)findViewById(R.id.allFriendsAccessibilitySpinner);
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

    public ArrayAdapter<Trinket> getTrinketArrayAdapter() {
        return trinketArrayAdapter;
    }

    public Inventory getCompleteInventory() {
        return completeInventory;
    }

    public AllFriendsInventoriesController getController() {
        return controller;
    }

    public Button getFilterButton() {
        return filterButton;
    }

    public EditText getSearchBox() {
        return searchBox;
    }

    public Spinner getCategorySpinner() {
        return categorySpinner;
    }

    public ArrayAdapter<Trinket> getAutocompleteAdapter() {
        return autocompleteAdapter;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
