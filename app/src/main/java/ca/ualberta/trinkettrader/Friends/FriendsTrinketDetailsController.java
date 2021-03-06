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

package ca.ualberta.trinkettrader.Friends;

import android.content.Intent;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.Inventory.Trinket.Trinket;
import ca.ualberta.trinkettrader.Trades.CounterTradeActivity;
import ca.ualberta.trinkettrader.Trades.CreateTradeActivity;

public class FriendsTrinketDetailsController {

    private FriendsTrinketDetailsActivity activity;
    private Trinket clickedTrinket = ApplicationState.getInstance().getClickedTrinket();


    public FriendsTrinketDetailsController(FriendsTrinketDetailsActivity activity) {
        this.activity = activity;

    }

    public void addToTradeButtonOnClick() {
        Trinket clickedTrinket = ApplicationState.getInstance().getClickedTrinket();
        ApplicationState.getInstance().getFriendsTradeTrinkets().add(clickedTrinket);

        if (ApplicationState.getInstance().getInCounterTrade()) {
            Intent intent = new Intent(activity, CounterTradeActivity.class);
            activity.startActivity(intent);
        } else {
            Intent intent = new Intent(activity, CreateTradeActivity.class);
            activity.startActivity(intent);
        }

    }

    public void updateTextViews() {
        activity.getNameTextView().setText(clickedTrinket.getName());
        activity.getDescriptionTextView().setText(clickedTrinket.getDescription());
        activity.getQuantityTextView().setText(clickedTrinket.getQuantity());
        activity.getQualityTextView().setText(clickedTrinket.getQuality());
        activity.getCategoryTextView().setText(clickedTrinket.getCategory());
    }

}
