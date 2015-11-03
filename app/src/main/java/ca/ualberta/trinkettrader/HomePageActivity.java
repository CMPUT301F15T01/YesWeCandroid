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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HomePageActivity extends AppCompatActivity {

    private Button friendsButton;
    private Button trackedFriendsButton;
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
        //this.trackedFriendsButton = (Button) findViewById(R.id.) NEED TO FIGURE OUT WHERE THIS IS GOING TO GO
        this.inventoryButton = (Button) findViewById(R.id.inventory_button);
        this.profileButton = (Button) findViewById(R.id.profile_button);
        this.tradeButton = (Button) findViewById(R.id.trades_button);
    }

    public Button getFriendsButton() {
        return friendsButton;
    }

    public Button getInventoryButton() {
        return inventoryButton;
    }

    public Button getProfileButton() {
        return profileButton;
    }

    public Button getTradeButton() {
        return tradeButton;
    }

    public void inventoryClick(View view) {
        controller.onInventoryClick();
    }

    public void friendsClick(View view) {
        controller.onFriendsClick();
    }

    public void tradesClick(View view) {
        controller.onTradesClick();
    }

    public void profileClick(View view) {
        controller.onProfileClick();
    }
}
