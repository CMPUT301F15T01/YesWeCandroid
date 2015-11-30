package ca.ualberta.trinkettrader.Trades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Observable;
import java.util.Observer;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.Inventory.Trinket.Trinket;
import ca.ualberta.trinkettrader.Inventory.Trinket.TrinketDetailsActivity;
import ca.ualberta.trinkettrader.R;
import ca.ualberta.trinkettrader.User.LoggedInUser;

public class InventoryTradeActivity extends Activity implements Observer {

    private ArrayAdapter<Trinket> trinketArrayAdapter;
    private Inventory inventory;
    private ListView inventoryItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_trade);

        this.inventory = LoggedInUser.getInstance().getInventory();
        this.inventoryItemsList = (ListView) findViewById(R.id.inventoryTradeDisplayedTrinkets);

        trinketArrayAdapter = new ArrayAdapter<>(this, R.layout.activity_inventory_trinket, inventory);
        inventoryItemsList.setAdapter(trinketArrayAdapter);

        final InventoryTradeActivity activity = this;
        // When a trinket in the ListView is clicked, user is directed to its TrinketDetailsActivity
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
