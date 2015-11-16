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
import java.util.ArrayList;

import java.util.Observable;
import java.util.Observer;

import ca.ualberta.trinkettrader.R;
import ca.ualberta.trinkettrader.User.LoggedInUser;

// TODO clarify trade statuses.
/**
 * Shows list of user's <i>past trades</i>. Past trades are trades with the status
 * "accepted" or "declined" (inactive trades).  Trades that are no longer active will appear in
 * the list on this screen. Clicking a trade in the list will open up the trade details page,
 * and show the relevant trade information for that trade (ie. items offered, quanity of
 * items offered).
 */
public class PastTradesActivity extends AppCompatActivity implements Observer {
    ListView pastTradesListView;
    private PastTradesController controller;
    private ArrayAdapter<Trade> pastTradesAdapter;
    private ArrayList<Trade> userPastTradesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_trades);
        pastTradesListView = (ListView)findViewById(R.id.pastTradesList);
        userPastTradesList = LoggedInUser.getInstance().getTradeManager().getTradeArchiver().getPastTrades();
        controller = new PastTradesController(this);
        controller.setPastTradesListViewItemOnClick();
    }

    @Override
    protected void onStart() {
        super.onStart();
        pastTradesAdapter = new ArrayAdapter<Trade>(this, R.layout.activity_trades_trade_box, userPastTradesList);
        pastTradesListView.setAdapter(pastTradesAdapter);
    }

    /**
     * Returns pastTradesListView.  This method is used by the PastTradesController
     * to get the clicked trade on the screen.
     * @return ListView
     */
    public ListView getPastTradesListView(){ return pastTradesListView; }


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
