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
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

public class ActiveTradesController {
    private DisplayTradesActivity activity;

    public ActiveTradesController(DisplayTradesActivity activity) {
        this.activity = activity;
    }


    public void setCurrentTradesListViewItemOnClick() {
        ListView currentTradesListView = activity.getCurrentTradesListView();
        currentTradesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> v, View view, int position, long id) {
                Trade clickedTrade = LoggedInUser.getInstance().getFriendsList().get(position);
                ApplicationState.getInstance().setClickedFriend(clickedTrade);
                Intent intent = new Intent(activity, ViewTradeDetailsActivity.class);
                activity.startActivity(intent);
            }
        });
    }

    public void setCurrentTradesListViewItemOnClick() {
        ListView currentTradesListView = activity.getCurrentTradesListView();
        currentTradesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> v, View view, int position, long id) {
                Trade clickedTrade = LoggedInUser.getInstance().getFriendsList().get(position);
                ApplicationState.getInstance().setClickedFriend(clickedTrade);
                Intent intent = new Intent(activity, ViewTradeDetailsActivity.class);
                activity.startActivity(intent);
            }
        });
    }
}
