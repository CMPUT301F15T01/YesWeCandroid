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

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

public class CreateTradeActivity extends AppCompatActivity {

    private Button addFriendsItemsButton;
    private Button addYourItemsButton;
    private CreateTradeController controller;
    private FriendsList userFriendList;
    private ArrayAdapter<Friend> friendAdapter;
    private Spinner friendSpinner;
    private Inventory friendTradeTrinkets;
    private ListView friendTradeTrinketListView;
    private ArrayAdapter<Trinket> friendTrinketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trade);

        addFriendsItemsButton = (Button) findViewById(R.id.addFriendsItemsButton);
        addYourItemsButton = (Button) findViewById(R.id.addYourItemsButton);
        userFriendList = LoggedInUser.getInstance().getFriendsList();
        friendSpinner = (Spinner) findViewById(R.id.friends_spinner);
        friendTradeTrinkets = ApplicationState.getInstance().getFriendsTradeTrinkets();
        friendTradeTrinketListView = (ListView) findViewById(R.id.friendsItemsListView);
        controller = new CreateTradeController(this);
        controller.setFriendSpinnerItemOnClick();
        controller.updateClickedFriend();

    }

    @Override
    protected void onStart() {
        super.onStart();
        updateFriendsSpinner();
        updateFriendTradeTrinketListView();
        controller.updateClickedFriend();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateFriendsSpinner();
        updateFriendTradeTrinketListView();
        controller.updateClickedFriend();
    }

    public Button getAddFriendsItemsButton() {
        return addFriendsItemsButton;
    }

    public Button getAddYourItemsButton() {
        return addYourItemsButton;
    }

    public Spinner getFriendSpinner() {
        return friendSpinner;
    }

    public ListView getFriendTradeTrinketListView() {
        return friendTradeTrinketListView;
    }

    public void addFriendsItemsButtonOnClick(View v) {
        controller.addFriendsItemsButtonOnClick();
    }

    public void addYourItemsButtonOnClick(View v) {
        controller.addYourItemsButtonOnClick();
    }

    public void updateFriendsSpinner() {
        userFriendList = LoggedInUser.getInstance().getFriendsList();
        friendAdapter = new ArrayAdapter<Friend>(this, android.R.layout.simple_list_item_1, userFriendList);
        friendAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        friendSpinner.setAdapter(friendAdapter);
    }

    public void updateFriendTradeTrinketListView() {
        friendTradeTrinkets = ApplicationState.getInstance().getFriendsTradeTrinkets();
        friendTrinketAdapter = new ArrayAdapter<Trinket>(this, R.layout.activity_friends_friend, friendTradeTrinkets);
        friendTradeTrinketListView.setAdapter(friendTrinketAdapter);
    }

}
