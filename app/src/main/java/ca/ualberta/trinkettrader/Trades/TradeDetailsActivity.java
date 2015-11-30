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

package ca.ualberta.trinkettrader.Trades;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.Inventory.Trinket.Trinket;
import ca.ualberta.trinkettrader.R;


/**
 * Shows details of a single trade.
 * <p/>
 * Screen will display the relevant trade information for a trade (ie. items offered, quanity of
 * items offered).
 * This screen will appear when a trade is clicked in the user's current trades list
 * or past trades list.
 */
public class TradeDetailsActivity extends Activity implements Observer {
    private TextView friendInTradeTextView;
    private TextView statusOfTradeTextView;
    private ListView offeredItemInTradeListView;
    private ListView requestedItemInTradeListView;
    private Trade trade;
    private RadioButton completedRadioButton;
    private TradeDetailsController controller;
    private ArrayAdapter<Trinket> friendTrinketAdapter;
    private ArrayAdapter<Trinket> yourTrinketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_details);
        friendInTradeTextView = (TextView) findViewById(R.id.tradeWithFriendName);
        statusOfTradeTextView = (TextView) findViewById(R.id.tradeStatus);
        //TODO if stuff breaks comment this

        offeredItemInTradeListView = (ListView) findViewById(R.id.offeredItems);
        requestedItemInTradeListView = (ListView) findViewById(R.id.requestedItemsy);

        trade = ApplicationState.getInstance().getClickedTrade();
        controller = new TradeDetailsController(this);
        controller.updateTextViews();
        updateTradeDetailsListView();

        completedRadioButton = (RadioButton) findViewById(R.id.tradeCompletedRadioButton);

        if (trade.getStatus().equals("Accepted")) {
            completedRadioButton.setVisibility(View.VISIBLE);
        } else if (trade.getStatus().equals("Declined")) {
            completedRadioButton.setVisibility(View.VISIBLE);
        } else {
            completedRadioButton.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        updateTradeDetailsListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTradeDetailsListView();
    }

    public void updateTradeDetailsListView() {
        yourTrinketAdapter = new ArrayAdapter<Trinket>(this, android.R.layout.simple_list_item_1, trade.getOfferedTrinkets());
        offeredItemInTradeListView.setAdapter(yourTrinketAdapter);
        friendTrinketAdapter = new ArrayAdapter<Trinket>(this, android.R.layout.simple_list_item_1, trade.getRequestedTrinkets());
        requestedItemInTradeListView.setAdapter(friendTrinketAdapter);
    }


    public TextView getFriendInTradeTextView() {
        return friendInTradeTextView;
    }

    public TextView getStatusOfTradeTextView() {
        return statusOfTradeTextView;
    }

    public RadioButton getCompletedRadioButton() {
        return completedRadioButton;
    }

    public void tradeCompletedRadioButtonOnClick(View v) {
        controller.tradeCompletedRadioButtonOnClick();
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
