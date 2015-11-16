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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.User.LoggedInUser;

/**
 * This class is responsible for switching to the trade details page for a trade when a trade
 * is clicked in the active trades list.
 */
public class PastTradesController {
    private PastTradesActivity activity;

    public PastTradesController(PastTradesActivity activity) { this.activity = activity; }

    public void setPastTradesListViewItemOnClick() {
        ListView pastTradesListView = activity.getPastTradesListView();
        pastTradesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> v, View view, int position, long id) {
                ArrayList<Trade> userPastTradesList = LoggedInUser.getInstance().getTradeManager().getTradeArchiver().getPastTrades();
                Trade clickedTrade = userPastTradesList.get(position);
                ApplicationState.getInstance().setClickedTrade(clickedTrade);
                Intent intent = new Intent(activity, TradeDetailsActivity.class);  //TODO set parent to previous activity-DisplayPastTradesActivity
                activity.startActivity(intent);
            }
        });
    }

        /*
    * For trades displayed on current and past trades pages
    * Trade box format:
    * Trade id
    * Other user invloved in trade (not yourself)
     * status of trade
     * category of items involved
    * */
}