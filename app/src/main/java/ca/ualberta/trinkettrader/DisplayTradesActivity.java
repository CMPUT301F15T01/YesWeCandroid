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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

import java.util.Observable;
import java.util.Observer;

/**
 *
 *
 *
 */
public class DisplayTradesActivity extends AppCompatActivity implements Observer {
    Button pastTradesButton;
    ListView currentTradesListView;
    private ActiveTradesController controller;
    private ArrayAdapter<Trade> currentTradesAdapter;
    private ArrayList<Trade> userCurrentTradesList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_trades);
        currentTradesListView = (ListView)findViewById(R.id.currentTradesList);
        pastTradesButton = (Button)findViewById(R.id.past_trades_button);
        // add trade to test. TODO empty list functionality. or create an 'empty trade' which is around and displayed if no current trades

        userCurrentTradesList = LoggedInUser.getInstance().getTradeManager().getTradeArchiver().getCurrentTrades();
        controller = new ActiveTradesController(this);
        controller.setCurrentTradesListViewItemOnClick();
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentTradesAdapter = new ArrayAdapter<Trade>(this, R.layout.listview_item, userCurrentTradesList);
        currentTradesListView.setAdapter(currentTradesAdapter);
    }


    /**
     * onClick method for button that directs to past trades.
     * @param view
     */
    public void openPastTrades(View view){
        Intent intent = new Intent(this, DisplayPastTradesActivity.class);
            startActivity(intent);
        }

    public Button getPastTradesButton(){return pastTradesButton; }

    public ListView getCurrentTradesListView() {
        return currentTradesListView;
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
