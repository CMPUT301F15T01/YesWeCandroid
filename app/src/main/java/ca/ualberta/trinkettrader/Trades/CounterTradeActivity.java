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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.Friends.Friend;
import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.Inventory.Trinket.Trinket;
import ca.ualberta.trinkettrader.R;


/**
 * Activity for proposing a counter trade. This activity is triggered when a user declines a trade that
 * was proposed to them.
 */
public class CounterTradeActivity extends Activity {

    private Button addFriendsItemsButton;
    private Button addYourItemsButton;
    private Button proposeTradeButton;
    private Button cancelButton;
    private CounterTradeController controller;
    private ArrayAdapter<Friend> friendAdapter;
    private Inventory friendTradeTrinkets;
    private Inventory yourTradeTrinkets;
    private ListView friendTradeTrinketListView;
    private ListView yourTradeTrinketListView;
    private ArrayAdapter<Trinket> friendTrinketAdapter;
    private ArrayAdapter<Trinket> yourTrinketAdapter;
    private TextView friendNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_trade);

        ApplicationState.getInstance().setInCounterTrade(true);
        addFriendsItemsButton = (Button) findViewById(R.id.addFriendsItemsCounterTradeButton);
        addYourItemsButton = (Button) findViewById(R.id.addYourItemsCounterTradeButton);
        proposeTradeButton = (Button) findViewById(R.id.proposeCounterTradeButton);
        cancelButton = (Button) findViewById(R.id.cancelCounterTradeButton);
        friendTradeTrinkets = ApplicationState.getInstance().getFriendsTradeTrinkets();
        friendTradeTrinketListView = (ListView) findViewById(R.id.friendsItemsCounterTradeListView);
        yourTradeTrinkets = ApplicationState.getInstance().getYourTradeTrinkets();
        yourTradeTrinketListView = (ListView) findViewById(R.id.yourItemsCounterTradeListView);
        friendNameTextView = (TextView) findViewById(R.id.friendNameCounterTradeTextView);
        controller = new CounterTradeController(this);
        controller.updateTextViews();

    }

    @Override
    protected void onStart() {
        super.onStart();
        updateFriendTradeTrinketListView();
        updateYourTradeTrinketListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateFriendTradeTrinketListView();
        updateYourTradeTrinketListView();
    }

    /**
     * Update the list of trinkets that the other user is offering in the trade.  The user may
     * request these be changed in the counter-trade offer.
     */
    public void updateFriendTradeTrinketListView() {
        friendTradeTrinkets = ApplicationState.getInstance().getFriendsTradeTrinkets();
        friendTrinketAdapter = new ArrayAdapter<Trinket>(this, R.layout.activity_friends_friend, friendTradeTrinkets);
        friendTradeTrinketListView.setAdapter(friendTrinketAdapter);
    }

    /**
     * Update the list of trinkets that the user is offering in the trade.  The user may
     * request these be changed in the counter-trade offer.
     */
    public void updateYourTradeTrinketListView() {
        yourTradeTrinkets = ApplicationState.getInstance().getYourTradeTrinkets();
        yourTrinketAdapter = new ArrayAdapter<Trinket>(this, R.layout.activity_friends_friend, yourTradeTrinkets);
        yourTradeTrinketListView.setAdapter(yourTrinketAdapter);
    }

    public void clearFriendTradeTrinketListView() {
        friendTradeTrinkets = ApplicationState.getInstance().getFriendsTradeTrinkets();
        friendTradeTrinkets.clear();
        friendTrinketAdapter = new ArrayAdapter<Trinket>(this, R.layout.activity_friends_friend, friendTradeTrinkets);
        friendTradeTrinketListView.setAdapter(friendTrinketAdapter);
    }

    public void clearYourTradeTrinketListView() {
        yourTradeTrinkets = ApplicationState.getInstance().getYourTradeTrinkets();
        yourTradeTrinkets.clear();
        yourTrinketAdapter = new ArrayAdapter<Trinket>(this, R.layout.activity_friends_friend, yourTradeTrinkets);
        yourTradeTrinketListView.setAdapter(yourTrinketAdapter);
    }

    public Button getAddFriendsItemsButton() {
        return addFriendsItemsButton;
    }

    /**
     * @return Button Cancel button
     */
    public Button getCancelButton() {
        return cancelButton;
    }

    public Button getAddYourItemsButton() {
        return addYourItemsButton;
    }

    /**
     * Returns
     *
     * @return Button Propose Trade button
     */
    public Button getProposeTradeButton() {
        return proposeTradeButton;
    }

    /**
     * Get the text view that displays the email address of the user you are trading with
     *
     * @return TextView - text view displaying the friend you are trading with's email
     */
    public TextView getFriendNameTextView() {
        return friendNameTextView;
    }

    /**
     * Returns
     *
     * @return ListView
     */
    public ListView getFriendTradeTrinketListView() {
        return friendTradeTrinketListView;
    }

    /**
     * onClick method for button that allows you to add your friend's items to the trade
     *
     * @param v - the button that was clicked
     */
    public void addFriendsItemsButtonOnClick(View v) {
        controller.addFriendsItemsButtonOnClick();
    }

    /**
     * onClick method for button that allows you to add your own items to the trade
     *
     * @param v - the button that was clicked
     */
    public void addYourItemsButtonOnClick(View v) {
        controller.addYourItemsButtonOnClick();
    }

    /**
     * onClick method for button that allows you to propose the counter trade you have created
     *
     * @param v - the button that was clicked
     */
    public void proposeCounterTradeButtonOnClick(View v) {
        controller.proposeCounterTradeButtonOnClick();
    }

    /**
     * onClick method for button that allows you to cancel the counter trade, if the user just wants
     * to decline the counter trade
     *
     * @param v - the button that was clicked
     */
    public void cancelCounterTradeButtonOnClick(View v) {
        controller.cancelCounterTradeButtonOnClick();
    }

}
