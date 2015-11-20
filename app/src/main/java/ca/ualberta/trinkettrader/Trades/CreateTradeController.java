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

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.Friends.Friend;
import ca.ualberta.trinkettrader.Friends.FriendsInventoryActivity;
import ca.ualberta.trinkettrader.Friends.FriendsList;
import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.Inventory.InventoryActivity;
import ca.ualberta.trinkettrader.User.LoggedInUser;

public class CreateTradeController {
    private CreateTradeActivity activity;

    public CreateTradeController(CreateTradeActivity activity) {
        this.activity = activity;
    }

    public void addFriendsItemsButtonOnClick() {
        Intent intent = new Intent(activity, FriendsInventoryActivity.class);
        activity.startActivity(intent);
    }

    public void addYourItemsButtonOnClick() {
        Intent intent = new Intent(activity, InventoryActivity.class);
        activity.startActivity(intent);
    }

    public void updateClickedFriend() {
        Spinner friendSpinner = activity.getFriendSpinner();
        Friend clickedFriend = ApplicationState.getInstance().getClickedFriend();

        if (clickedFriend == null) {
            Friend selectedFriend = (Friend) friendSpinner.getSelectedItem();
            ApplicationState.getInstance().setClickedFriend(selectedFriend);
        }

        else {
            FriendsList userFriendList = LoggedInUser.getInstance().getFriendsList();
            Integer friendIndex = userFriendList.getFriendIndexByUsername(clickedFriend.getActualFriend().getProfile().getEmail());
            friendSpinner.setSelection(friendIndex);
        }

        Friend selectedFriend = (Friend) friendSpinner.getSelectedItem();
        ApplicationState.getInstance().setClickedFriend(selectedFriend);
    }

    public void setFriendSpinnerItemOnClick() {
        Spinner friendSpinner = activity.getFriendSpinner();
        friendSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> spinner, View view, int pos, long id) {
                Friend selectedFriend = (Friend) spinner.getItemAtPosition(pos);
                if(!selectedFriend.getActualFriend().getProfile().getEmail().equals(ApplicationState.getInstance().getClickedFriend().getActualFriend().getProfile().getEmail())){
                    ApplicationState.getInstance().setFriendsTradeTrinkets(new Inventory());
                }
                ApplicationState.getInstance().setClickedFriend(selectedFriend);
                activity.updateFriendTradeTrinketListView();
            }

            public void onNothingSelected(AdapterView<?> spinner) {
            }
        });
    }


}