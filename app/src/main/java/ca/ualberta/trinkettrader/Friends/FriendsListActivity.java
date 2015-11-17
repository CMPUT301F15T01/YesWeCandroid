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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Observable;
import java.util.Observer;

import ca.ualberta.trinkettrader.User.LoggedInUser;
import ca.ualberta.trinkettrader.R;

public class FriendsListActivity extends AppCompatActivity implements Observer {

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
        setContentView(R.layout.activity_friends);
        friendsListView = (ListView) findViewById(R.id.friendsView);
        findFriendsButton = (Button) findViewById(R.id.findFriendsButton);
        viewTrackedFriendsButton = (Button) findViewById(R.id.viewTrackedFriendsButton);
        findFriendTextField = (EditText) findViewById(R.id.findFriendTextField);
        userFriendList = LoggedInUser.getInstance().getFriendsList();
        controller = new FriendsListController(this);
        controller.setFriendsListViewItemOnClick();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateFriendsListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateFriendsListView();
    }

    /**
     * Returns the find friend button.
     *
     * @return findFriendsButton
     */
    public Button getFindFriendsButton() {
        return findFriendsButton;
    }

    /**
     * Returns view tracked friends button
     *
     * @return viewTrackedFriendsButton
     */
    public Button getViewTrackedFriendsButton() {
        return viewTrackedFriendsButton;
    }

    public EditText getFindFriendTextField() {
        return findFriendTextField;
    }

    public ListView getFriendsListView() {
        return friendsListView;
    }

    /**
     * Sets the friends information so that their profile can be viewed
     *
     * @param v
     */
    public void findFriendsOnClick(View v) {
        controller.findFriendsOnClick();
        friendAdapter.notifyDataSetChanged();
    }

    /**
     * viewTrackedFriendsOnCLick navigates to tracked friends
     *
     * @param v tracked friends button view
     */

    public void viewTrackedFriendsOnClick(View v) {
        controller.viewTrackedFriendsOnClick();
    }

    public void updateFriendsListView() {
        userFriendList = LoggedInUser.getInstance().getFriendsList();
        friendAdapter = new ArrayAdapter<Friend>(this, R.layout.activity_friends_friend, userFriendList);
        friendsListView.setAdapter(friendAdapter);
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
