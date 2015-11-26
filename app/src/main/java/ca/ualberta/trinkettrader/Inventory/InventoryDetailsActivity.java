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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import ca.ualberta.trinkettrader.R;
import ca.ualberta.trinkettrader.User.LoggedInUser;

/**
 * Android activity for displaying the details of a user's inventory.  Some of the inventory's details
 * are displayed statically, such as the number of items in it.
 */
public class InventoryDetailsActivity extends AppCompatActivity implements Observer {

    TextView numItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_details);

        numItems = (TextView) findViewById(R.id.numItemsText);
        numItems.setText("Number of items: " + LoggedInUser.getInstance().getInventory().size());
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
