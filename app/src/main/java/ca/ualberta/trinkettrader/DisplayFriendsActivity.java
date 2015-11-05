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
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayFriendsActivity extends AppCompatActivity {

    private Button findFriendsButton;
    private EditText findFriendTextField;
    private ListView friendsListView;
    private FriendsList userFriendList;
    private ArrayAdapter<User> friendAdapter;
    private FriendsListController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_friends);
        friendsListView = (ListView)findViewById(R.id.friendsView);
        findFriendsButton = (Button)findViewById(R.id.findFriendsButton);
        findFriendTextField = (EditText)findViewById(R.id.findFriendTextField);
        userFriendList = LoggedInUser.getInstance().getFriendsList();
        controller = new FriendsListController(this);

        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> v, View view, int position, long id) {
                Log.d("XXXXXXXXXX1", "about to start intent");
                Intent intent = new Intent(DisplayFriendsActivity.this, DisplayFriendsProfileActivity.class);
                Log.d("XXXXXXXXXX1", "made intent");
                startActivity(intent);
                Log.d("XXXXXXXXXX1", "done");
            }
        });
  }

    @Override
    protected void onStart() {
        super.onStart();
        friendAdapter = new ArrayAdapter<User>(this, R.layout.listview_item, userFriendList);
        friendsListView.setAdapter(friendAdapter);
    }

    public Button getFindFriendsButton() {
        return findFriendsButton;
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
}
