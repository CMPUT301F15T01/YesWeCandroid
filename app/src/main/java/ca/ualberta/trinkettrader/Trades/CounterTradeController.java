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

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.Friends.FriendsInventoryActivity;
import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.User.LoggedInUser;


/**
 * Controller for proposing a counter trade. The activity is triggered when a user declines a trade that
 * was proposed to them.
 */
public class CounterTradeController {
    private CounterTradeActivity activity;


    public CounterTradeController(CounterTradeActivity activity) {
        this.activity = activity;
    }

        /**
         * onClick method for button that allows you to add your friend's items to the trade
         */
        public void addFriendsItemsButtonOnClick() {
            Intent intent = new Intent(activity, FriendsInventoryActivity.class);
            activity.startActivity(intent);
        }

    /**
     * onClick method for button that allows you to add your own items to the trade
     */
    public void addYourItemsButtonOnClick() {
        Intent intent = new Intent(activity, InventoryTradeActivity.class);
        activity.startActivity(intent);
    }

    /**
     * onClick method for button that allows you to propose the counter trade you have created
     */
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

    /**
     * onClick method for button that allows you to cancel the counter trade, if the user just wants
     * to decline the counter trade
     */
    public void cancelCounterTradeButtonOnClick() {
        ApplicationState.getInstance().setInCounterTrade(false);
        //TODO move to past trades
        //TODO send declined notification
        Intent intent = new Intent(activity, TradesActivity.class);
        activity.startActivity(intent);

    }

    /**
     * Update all the textviews on the CounterTradeActivity
     */
    public void updateTextViews() {
        Trade clickedTrade = ApplicationState.getInstance().getClickedTrade();
        activity.getFriendNameTextView().setText(clickedTrade.getReceiver().getUsername());
    }
}


