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

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class DisplayTrackedFriendsActivity extends AppCompatActivity {

    private TrackedFriendsList trackedFriendsList;
    private ArrayAdapter<Friend> trackedFriendAdapter;
    private ListView trackedFriendsListView;
    private Button backToFriendsListButton;
    private DisplayTrackedFriendsController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_tracked_friends);

        trackedFriendsList = LoggedInUser.getInstance().getTrackedFriends();
        trackedFriendsListView = (ListView) findViewById(R.id.trackedFriendsListView);
        backToFriendsListButton = (Button) findViewById(R.id.backToFriendsListButton);
        controller = new DisplayTrackedFriendsController(this);
        controller.setTrackedFriendsListViewItemOnClick();
    }

    @Override
    protected void onStart() {
        super.onStart();
        trackedFriendAdapter = new ArrayAdapter<Friend>(this, R.layout.listview_item, trackedFriendsList);
        trackedFriendsListView.setAdapter(trackedFriendAdapter);
    }

    public void backToFriendsListButtonOnClick(View v) {
        controller.backToFriendsListOnClick();
    }

    /** Returns the back button to go back to the friends list page.
     *
     * @return back button
     */
    public Button getBackToFriendsListButton() {
        return backToFriendsListButton;
    }

    public ListView getTrackedFriendsListView() {
        return trackedFriendsListView;
    }

}
