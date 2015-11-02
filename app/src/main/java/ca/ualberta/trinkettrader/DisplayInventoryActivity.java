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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class DisplayInventoryActivity extends AppCompatActivity {

    private Button addItemButton;
    private Inventory inventory;
    private ListView inventoryItemsList;
    private Button deleteAll;

    private InventoryController inventoryController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_inventory);

        /*inventoryItemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: open the DisplayItemDetails for the Trinket (list item) that was clicked
            }
        });
        */
        this.inventoryController = new InventoryController(this);
        this.inventory = new Inventory();
        this.inventoryItemsList = (ListView) findViewById(R.id.displayedTrinkets);
        this.addItemButton = (Button) findViewById(R.id.addItemButton);
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

    public Button getDeleteAll() {
        return deleteAll;
    }

    public void clickAdd(View view) {
        inventoryController.onAddItemClick();
    }
}
