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
import android.widget.Spinner;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.Friends.Friend;
import ca.ualberta.trinkettrader.Friends.FriendsInventoryActivity;
import ca.ualberta.trinkettrader.Friends.FriendsList;
import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.Inventory.InventoryActivity;
import ca.ualberta.trinkettrader.User.LoggedInUser;


/**
 *
 */
public class CounterTradeController {
    private CounterTradeActivity activity;


    public CounterTradeController(CounterTradeActivity activity) {
        this.activity = activity;
    }

    public void addFriendsItemsButtonOnClick() {
        Intent intent = new Intent(activity, FriendsInventoryActivity.class);
        activity.startActivity(intent);
    }

    public void addYourItemsButtonOnClick() {
        Intent intent = new Intent(activity, InventoryTradeActivity.class);
        activity.startActivity(intent);
    }

    public void proposeCounterTradeButtonOnClick() {
        ApplicationState.getInstance().setInCounterTrade(false);
        Inventory yourTradeTrinkets = ApplicationState.getInstance().getYourTradeTrinkets();
        Inventory friendsTradeTrinkets = ApplicationState.getInstance().getFriendsTradeTrinkets();
        TradeManager receiverTradeManager = ApplicationState.getInstance().getClickedFriend().getActualFriend().getTradeManager();
        TradeManager senderTradeManager = LoggedInUser.getInstance().getTradeManager();

        Trade proposedTrade = new Trade(yourTradeTrinkets, receiverTradeManager, friendsTradeTrinkets, senderTradeManager);
        LoggedInUser.getInstance().getTradeManager().proposeTrade(proposedTrade);

        senderTradeManager.getTradeArchiver().getCurrentTrades().add(proposedTrade);
        receiverTradeManager.getTradeArchiver().getCurrentTrades().add(proposedTrade);
        // activity.clearFriendTradeTrinketListView();
        //activity.clearYourTradeTrinketListView();

        Intent intent = new Intent(activity, TradesActivity.class);
        activity.startActivity(intent);
    }


    public void cancelCounterTradeButtonOnClick() {
        ApplicationState.getInstance().setInCounterTrade(false);
        //TODO move to past trades
        //TODO send declined notification
        Intent intent = new Intent(activity, TradesActivity.class);
        activity.startActivity(intent);

    }

    public void updateTextViews() {
        Trade clickedTrade = ApplicationState.getInstance().getClickedTrade();
        activity.getFriendNameTextView().setText(clickedTrade.getReceiver().getUsername());
    }
}


