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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ca.ualberta.trinkettrader.R;
import ca.ualberta.trinkettrader.User.LoggedInUser;

// TODO clarify trade statuses.
// TODO clarify what should appear in tradeBox.
/**
 * Shows list of user's <i>active trades</i>. Active trades are trades with the status
 * "pending" (user's current trades).  If a trade has been offered to the user, it will appear in
 * the list on this screen. Clicking a trade in the list will open up the trade details page,
 * and show the relevant trade information for that trade (ie. items offered, quanity of
 * items offered).
 *
 * Trades which have been accepted or declined are considered <i>past</i> trades and will
 * not be shown in the displayed list.  To view past trades, the <b>Past Trades</b> button
 * on the screen can be clicked.
 */
public class TradesActivity extends AppCompatActivity implements Observer {

    Button pastTradesButton;
    ListView currentTradesListView;
    private ActiveTradesController controller;
    private ArrayAdapter<Trade> currentTradesAdapter;
    private ArrayList<Trade> userCurrentTradesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trades);
        currentTradesListView = (ListView)findViewById(R.id.currentTradesList);
        pastTradesButton = (Button)findViewById(R.id.past_trades_button);
        userCurrentTradesList = LoggedInUser.getInstance().getTradeManager().getTradeArchiver().getCurrentTrades();
        controller = new ActiveTradesController(this);
        controller.setCurrentTradesListViewItemOnClick();
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentTradesAdapter = new ArrayAdapter<Trade>(this, R.layout.activity_trades_trade_box, userCurrentTradesList);
        currentTradesListView.setAdapter(currentTradesAdapter);
    }


    /**
     * onClick method for button that directs to past trades.
     *
     * @param view
     */
    public void openPastTrades(View view) {
        Intent intent = new Intent(this, PastTradesActivity.class);
        startActivity(intent);
    }

    /**
     * Returns Past Trades button.
     * @return Button
     */
    public Button getPastTradesButton(){return pastTradesButton; }

    /**
     * Returns currentTradesListView.  This method is used by the ActiveTradesController
     * to get the clicked trade on the screen.
     * @return ListView
     */
    public ListView getCurrentTradesListView() { return currentTradesListView; }

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
