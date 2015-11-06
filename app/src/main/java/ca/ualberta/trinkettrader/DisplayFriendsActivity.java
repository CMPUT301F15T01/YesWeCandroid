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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class DisplayFriendsActivity extends AppCompatActivity {

    private Button findFriendsButton;
    private EditText findFriendTextField;
    private ListView friendsListView;
    private FriendsList userFriendList;
    private ArrayAdapter<Friend> friendAdapter;
    private FriendsListController controller;
    private Button viewTrackedFriendsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_friends);
        friendsListView = (ListView)findViewById(R.id.friendsView);
        findFriendsButton = (Button)findViewById(R.id.findFriendsButton);
        viewTrackedFriendsButton = (Button) findViewById(R.id.viewTrackedFriendsButton);
        findFriendTextField = (EditText)findViewById(R.id.findFriendTextField);
        userFriendList = LoggedInUser.getInstance().getFriendsList();
        controller = new FriendsListController(this);
        controller.setFriendsListViewItemOnClick();
    }

    @Override
    protected void onStart() {
        super.onStart();
        friendAdapter = new ArrayAdapter<Friend>(this, R.layout.listview_item, userFriendList);
        friendsListView.setAdapter(friendAdapter);
    }

    public Button getFindFriendsButton() {
        return findFriendsButton;
    }

    public Button getViewTrackedFriendsButton() {
        return viewTrackedFriendsButton;
    }

    public EditText getFindFriendTextField() {
        return findFriendTextField;
    }

    public ListView getFriendsListView() {
        return friendsListView;
    }

    public void findFriendsOnClick(View v) {
        controller.findFriendsOnClick();
        friendAdapter.notifyDataSetChanged();
    }

    public void viewTrackedFriendsOnClick(View v){
        controller.viewTrackedFriendsOnClick();
    }
}
