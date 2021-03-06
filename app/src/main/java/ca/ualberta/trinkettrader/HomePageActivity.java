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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Observable;
import java.util.Observer;

import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.Inventory.Trinket.Trinket;
import ca.ualberta.trinkettrader.Trades.Trade;
import ca.ualberta.trinkettrader.Trades.TradeManager;
import ca.ualberta.trinkettrader.User.LoggedInUser;

/**
 * Activity serving as the home page of the app. This should be navigated to
 * immediately after having logged in.
 */
public class HomePageActivity extends Activity implements Observer {

    private Button friendsButton;
    private Button inventoryButton;
    private Button profileButton;
    private Button tradeButton;

    private HomePageController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        controller = new HomePageController(this);

        this.friendsButton = (Button) findViewById(R.id.friends_button);
        this.inventoryButton = (Button) findViewById(R.id.inventory_button);
        this.profileButton = (Button) findViewById(R.id.profile_button);
        this.tradeButton = (Button) findViewById(R.id.trades_button);


        Inventory incomingTrinkets = new Inventory();
        Trinket fakeTrinket = new Trinket();
        fakeTrinket.setName("Diamond Earrings");
        fakeTrinket.setCategory("Earrings");
        incomingTrinkets.add(fakeTrinket);
        Inventory desiredTrinkets = new Inventory();
        Trinket fakeTrinket2 = new Trinket();
        fakeTrinket2.setName("Silver Tiara");
        fakeTrinket2.setCategory("Tiara");
        desiredTrinkets.add(fakeTrinket2);
        Trade fakeIncomingTrade = new Trade(incomingTrinkets, LoggedInUser.getInstance().getTradeManager(), desiredTrinkets, new TradeManager("Abram"));
        fakeIncomingTrade.setStatus("Pending Incoming");
        LoggedInUser.getInstance().getTradeManager().getTradeArchiver().getCurrentTrades().add(fakeIncomingTrade);
    }

    /**
     * Returns button directing to friends list.
     *
     * @return Button
     */
    public Button getFriendsButton() {
        return friendsButton;
    }

    /**
     * Returns button directing to inventory.
     *
     * @return Button
     */
    public Button getInventoryButton() {
        return inventoryButton;
    }

    /**
     * Returns button directing to user profile.
     *
     * @return Button
     */
    public Button getProfileButton() {
        return profileButton;
    }

    /**
     * Returns button directing to trades
     *
     * @return Button
     */
    public Button getTradeButton() {
        return tradeButton;
    }

    /**
     * Directs controller to handle click on inventory button.
     *
     * @param view
     */
    public void inventoryClick(View view) {
        controller.onInventoryClick();
    }

    /**
     * Directs controller to handle click on friend button.
     *
     * @param view
     */
    public void friendsClick(View view) {
        controller.onFriendsClick();
    }

    /**
     * Directs controller to handle click on trade button.
     *
     * @param view
     */
    public void tradesClick(View view) {
        controller.onTradesClick();
    }

    /**
     * Directs controller to handle click on profile button.
     *
     * @param view
     */
    public void profileClick(View view) {
        controller.onProfileClick();
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
