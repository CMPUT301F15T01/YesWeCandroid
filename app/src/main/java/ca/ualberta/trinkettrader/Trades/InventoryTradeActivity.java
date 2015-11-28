package ca.ualberta.trinkettrader.Trades;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import ca.ualberta.trinkettrader.R;

public class InventoryTradeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_trade);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
