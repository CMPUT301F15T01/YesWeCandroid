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

import android.widget.ArrayAdapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 *
 *
 *
 */

public class DisplayPastTradesActivity extends AppCompatActivity implements Observer {
    ListView pastTradesListView;
    private PastTradesController controller;
    private ArrayAdapter<Trade> pastTradesAdapter;
    private ArrayList<Trade> userPastTradesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_past_trades);

        pastTradesListView = (ListView)findViewById(R.id.pastTradesList);
        // add trade to test. TODO empty list functionality. or create an 'empty trade' which is around and displayed if no current trades
        userPastTradesList = LoggedInUser.getInstance().getTradeManager().getTradeArchiver().getPastTrades();
        controller = new PastTradesController(this);
        controller.setPastTradesListViewItemOnClick();
    }

    @Override
    protected void onStart() {
        super.onStart();
        pastTradesAdapter = new ArrayAdapter<Trade>(this, R.layout.listview_item, userPastTradesList);
        pastTradesListView.setAdapter(pastTradesAdapter);
    }

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
