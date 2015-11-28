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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
public class TradeDetailsActivity extends AppCompatActivity implements Observer {
    private TextView friendInTradeTextView;
    private TextView statusOfTradeTextView;
    private ListView offeredItemInTradeListView;
    private ListView requestedItemInTradeListView;
    private Trade trade;
    private TradeDetailsController controller;
    private ArrayAdapter<Trinket> trinketArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_details);
       // friendInTradeTextView = (TextView) findViewById(R.id.tradeWithFriendName);
        statusOfTradeTextView = (TextView) findViewById(R.id.tradeStatus);
       // offeredItemInTradeListView = (ListView) findViewById(R.id.offeredItems);
        //requestedItemInTradeListView = (ListView) findViewById(R.id.requestedItemsy);

        trade = ApplicationState.getInstance().getClickedTrade();
        controller = new TradeDetailsController(this);
        controller.updateTextViews();

        ///trinketArrayAdapter = new ArrayAdapter<>(this, R.layout.activity_trades, trade);
        //offeredItemInTradeListView.setAdapter(trinketArrayAdapter);

    }



    public TextView getFriendInTradeTextView() {
        return friendInTradeTextView;
    }

    public TextView getStatusOfTradeTextView() {
        return statusOfTradeTextView;
    }

    public ListView getOfferedItemInTradeListView() {
        return offeredItemInTradeListView;
    }

    public ListView getRequestedItemInTradeListView() {
        return requestedItemInTradeListView;
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
