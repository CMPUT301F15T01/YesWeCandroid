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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Observable;
import java.util.Observer;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.User.LoggedInUser;
import ca.ualberta.trinkettrader.R;
import ca.ualberta.trinkettrader.Inventory.Trinket.Trinket;
import ca.ualberta.trinkettrader.Inventory.Trinket.TrinketDetailsActivity;

/**
 * Android activity class for displaying a user's inventory.  From this activity all the trinkets in
 * the user's inventory can be viewed.
 *
 * This activity contains an "Add Trinket" button that connects the user to the
 * AddOrEditTrinketActivity for adding a new trinket to their own inventory.  It also has a "Details"
 * button that connects the user to the InventoryDetailsActivity, which displays information about
 * the user's inventory.  Any trinket in the inventory can be clicked on which connects to the
 * AddOrEditTrinketActivity, from which the user can view and edit the trinket's details.
 */
public class InventoryActivity extends AppCompatActivity implements Observer {

    private ArrayAdapter<Trinket> trinketArrayAdapter;
    private Button addItemButton;
    private Inventory inventory;
    private ListView inventoryItemsList;

    private InventoryController inventoryController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        this.inventoryController = new InventoryController(this);
        this.inventory = LoggedInUser.getInstance().getInventory();
        this.inventoryItemsList = (ListView) findViewById(R.id.displayedTrinkets);
        this.addItemButton = (Button) findViewById(R.id.addItemButton);

        trinketArrayAdapter = new ArrayAdapter<>(this, R.layout.activity_inventory_trinket, inventory);
        inventoryItemsList.setAdapter(trinketArrayAdapter);

        final InventoryActivity activity = this;
        inventoryItemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> v, View view, int position, long id) {
                Trinket clickedTrinket = LoggedInUser.getInstance().getInventory().get(position);
                ApplicationState.getInstance().setClickedTrinket(clickedTrinket);
                Intent intent = new Intent(activity, TrinketDetailsActivity.class);
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
     * Returns the "Add Trinket" button from the activity's user interface that connects the user to
     * the AddOrEditTrinketActivity.
     *
     * @return Android Button - button for adding a new trinket to the user's inventory
     */
    public Button getAddItemButton() {
        return addItemButton;
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

    /**
     * Method called with the "Add Trinket" button is clocked.  Directs the activity's controller
     * to switch the user to the AddOrEditTrinketActivity.
     *
     * @param view - the button that was clicked
     */
    public void clickAdd(View view) {
        inventoryController.onAddItemClick();
    }

    /**
     * Method called with the "Details" button is clocked.  Directs the activity's controller
     * to switch the user to the InventoryDetailsActivity.
     *
     * @param view - the button that was clicked
     */
    public void detailsClick(View view) {
        inventoryController.onDetailsClick();
    }

    /**
     * This method is called if the specified {@code Observable} object's
     * {@code notifyObservers} method is called (because the {@code Observable}
     * object has been updated.
     *
     * @param observable the {@link Observable} object.
     * @param data       the data passed to {@link Observable#notifyObservers(Object)}.
     */
    @Override
    public void update(Observable observable, Object data) {

    }
}
