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

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import ca.ualberta.trinkettrader.R;

public class FriendsTrinketDetailsActivity extends ActionBarActivity {

    private TextView name;
    private TextView description;
    private TextView quantity;
    private TextView quality;
    private TextView category;

    FriendsTrinketDetailsController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_trinket_details);

        name = (TextView) findViewById(R.id.friendsTrinketName);
        description = (TextView) findViewById(R.id.friendsTrinketDescription);
        quantity = (TextView) findViewById(R.id.friendsTrinketQuantity);
        quality = (TextView) findViewById(R.id.friendsTrinketQuality);
        category = (TextView) findViewById(R.id.friendsTrinketCategory);
        controller = new FriendsTrinketDetailsController(this);
        controller.updateTextViews();
    }

    public TextView getNameTextView() {
        return name;
    }

    public TextView getDescriptionTextView() {
        return description;
    }

    public TextView getQuantityTextView() {
        return quantity;
    }

    public TextView getQualityTextView() {
        return quality;
    }

    public TextView getCategoryTextView() {
        return category;
    }




}
