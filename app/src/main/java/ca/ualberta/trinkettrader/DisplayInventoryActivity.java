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

public class DisplayInventoryActivity extends AppCompatActivity implements Observer {

    private ArrayAdapter<Trinket> trinketArrayAdapter;
    private Button addItemButton;
    private Inventory inventory;
    private ListView inventoryItemsList;

    private InventoryController inventoryController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_inventory);

        this.inventoryController = new InventoryController(this);
        this.inventory = LoggedInUser.getInstance().getInventory();
        this.inventoryItemsList = (ListView) findViewById(R.id.displayedTrinkets);
        this.addItemButton = (Button) findViewById(R.id.addItemButton);

        trinketArrayAdapter = new ArrayAdapter<>(this, R.layout.listview_item, inventory);
        inventoryItemsList.setAdapter(trinketArrayAdapter);

        final DisplayInventoryActivity activity = this;
        inventoryItemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> v, View view, int position, long id) {
                Trinket clickedTrinket = LoggedInUser.getInstance().getInventory().get(position);
                ApplicationState.getInstance().setClickedTrinket(clickedTrinket);
                Intent intent = new Intent(activity, ItemDetailsActivity.class);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        trinketArrayAdapter.notifyDataSetChanged();
    }

    public Button getAddItemButton() {
        return addItemButton;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public ListView getInventoryItemsList() {
        return inventoryItemsList;
    }

    public void clickAdd(View view) {
        inventoryController.onAddItemClick();
    }

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
