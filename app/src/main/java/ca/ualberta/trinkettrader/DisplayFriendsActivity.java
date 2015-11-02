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
import android.widget.Button;
import android.widget.ListView;

public class DisplayFriendsActivity extends AppCompatActivity {

    private FriendsList friends;
    private Button addFriendButton;
    private ListView friendsinFriendsList;

    public ListView getFriendsListView() {
        return friendsListView;
    }

    private ListView friendsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_friends);
        friendsListView = (ListView)findViewById(R.id.friendsView);
    }

    public FriendsList getFriends() {
        return friends;
    }

    public void setFriends(FriendsList friends) {
        this.friends = friends;
    }

    public Button getAddFriendButton() {
        return addFriendButton;
    }

    public ListView getFriendsinFriendsList() {
        return friendsinFriendsList;
    }
}
