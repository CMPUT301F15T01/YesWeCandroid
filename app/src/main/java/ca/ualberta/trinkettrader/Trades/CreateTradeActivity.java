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
import android.widget.Spinner;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.Friends.Friend;
import ca.ualberta.trinkettrader.Friends.FriendsList;
import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.Inventory.Trinket.Trinket;
import ca.ualberta.trinkettrader.R;
import ca.ualberta.trinkettrader.User.LoggedInUser;

/**
 * To create a trade
 * Select a friend to offer the trade to.
 * Add items from the friend's inventory.
 * Add items from your own inventory
 * Once satisfied, a user can click <i>Propose Trade</i> to send the trade
 * offer.
 * Clicking the <i>Create Trade</i> button in the
 */
public class CreateTradeActivity extends Activity {

    private Button addFriendsItemsButton;
    private Button addYourItemsButton;
    private Button proposeTradeButton;
    private CreateTradeController controller;
    private FriendsList userFriendList;
    private ArrayAdapter<Friend> friendAdapter;
    private Spinner friendSpinner;
    private Inventory friendTradeTrinkets;
    private Inventory yourTradeTrinkets;
    private ListView friendTradeTrinketListView;


    private ListView yourTradeTrinketListView;
    private ArrayAdapter<Trinket> friendTrinketAdapter;
    private ArrayAdapter<Trinket> yourTrinketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trade);

        addFriendsItemsButton = (Button) findViewById(R.id.addFriendsItemsTradeButton);
        addYourItemsButton = (Button) findViewById(R.id.addYourItemsTradeButton);
        proposeTradeButton = (Button) findViewById(R.id.proposeTradeButton);
        userFriendList = LoggedInUser.getInstance().getFriendsList();
        friendSpinner = (Spinner) findViewById(R.id.friends_spinner);
        friendTradeTrinkets = ApplicationState.getInstance().getFriendsTradeTrinkets();
        friendTradeTrinketListView = (ListView) findViewById(R.id.friendsItemsTradeListView);
        yourTradeTrinkets = ApplicationState.getInstance().getYourTradeTrinkets();
        yourTradeTrinketListView = (ListView) findViewById(R.id.yourItemsTradeListView);
        controller = new CreateTradeController(this);
        controller.setFriendSpinnerItemOnClick();
        controller.updateClickedFriend();
        controller.setYourTradeTrinketListViewItemOnClick();
        controller.setFriendTradeTrinketListViewItemOnClick();
        updateFriendsSpinner();

    }

    @Override
    protected void onStart() {
        super.onStart();
        updateFriendsSpinner();
        updateFriendTradeTrinketListView();
        updateYourTradeTrinketListView();
        controller.updateClickedFriend();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateFriendsSpinner();
        updateFriendTradeTrinketListView();
        updateYourTradeTrinketListView();
        controller.updateClickedFriend();
    }

    /**
     *
     */
    public void updateFriendTradeTrinketListView() {
        friendTradeTrinkets = ApplicationState.getInstance().getFriendsTradeTrinkets();
        friendTrinketAdapter = new ArrayAdapter<Trinket>(this, R.layout.activity_friends_friend, friendTradeTrinkets);
        friendTradeTrinketListView.setAdapter(friendTrinketAdapter);
    }

    /**
     *
     */
    public void updateYourTradeTrinketListView() {
        yourTradeTrinkets = ApplicationState.getInstance().getYourTradeTrinkets();
        yourTrinketAdapter = new ArrayAdapter<Trinket>(this, R.layout.activity_friends_friend, yourTradeTrinkets);
        yourTradeTrinketListView.setAdapter(yourTrinketAdapter);
    }

    /**
     *
     */
    public void updateFriendsSpinner() {
        userFriendList = LoggedInUser.getInstance().getFriendsList();
        friendAdapter = new ArrayAdapter<Friend>(this, android.R.layout.simple_list_item_1, userFriendList);
        friendAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        friendSpinner.setAdapter(friendAdapter);
        if (userFriendList.size() > 0) {
            friendSpinner.setSelection(ApplicationState.getInstance().getFriendSpinnerPosition());
        }
    }

    /**
     *
     */
    public void clearFriendTradeTrinketListView() {
        friendTradeTrinkets = ApplicationState.getInstance().getFriendsTradeTrinkets();
        friendTradeTrinkets.clear();
        friendTrinketAdapter = new ArrayAdapter<Trinket>(this, R.layout.activity_friends_friend, friendTradeTrinkets);
        friendTradeTrinketListView.setAdapter(friendTrinketAdapter);
    }

    /**
     *
     */
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
     * @return
     */
    public Button getAddYourItemsButton() {
        return addYourItemsButton;
    }

    /**
     * @return
     */
    public Button getProposeTradeButton() {
        return proposeTradeButton;
    }

    /**
     * @return
     */
    public Spinner getFriendSpinner() {
        return friendSpinner;
    }

    /**
     * @return
     */
    public ListView getFriendTradeTrinketListView() {
        return friendTradeTrinketListView;
    }

    public ListView getYourTradeTrinketListView() {
        return yourTradeTrinketListView;
    }

    /**
     * @param v
     */
    public void addFriendsItemsButtonOnClick(View v) {
        controller.addFriendsItemsButtonOnClick();
    }

    /**
     * @param v
     */
    public void addYourItemsButtonOnClick(View v) {
        controller.addYourItemsButtonOnClick();
    }

    /**
     * @param v
     */
    public void proposeTradeButtonOnClick(View v) {
        controller.proposeTradeButtonOnClick();
    }

}
