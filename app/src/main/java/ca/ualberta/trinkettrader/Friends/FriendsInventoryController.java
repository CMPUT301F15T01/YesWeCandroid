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

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.Inventory.Trinket.Trinket;
import ca.ualberta.trinkettrader.R;

/**
 * Controller for handling interactions from the FriendsInventoryActivity.  The controller manages clicks
 * on the trinkets, which connects the user to the TrinketDetailsActivity.
 */

public class FriendsInventoryController {
    private FriendsInventoryActivity activity;

    /**
     * Constructs a controller with the activity this constructor is attached to.  Each controller
     * can only be used by one activity.
     *
     * @param activity - The activity this controller is attached to
     */
    public FriendsInventoryController(FriendsInventoryActivity activity) {
        this.activity = activity;
    }

    /**
     * Sets click listener for the items in the friends list ListView.  Will direct to the friend's profile activity.
     */
    public void friendsInventoryItemOnClick() {
        ListView friendsInventoryListView = activity.getInventoryItemsList();
        friendsInventoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> v, View view, int position, long id) {
                Trinket clickedTrinket = ApplicationState.getInstance().getClickedFriend().getActualFriend().getInventory().get(position);
                ApplicationState.getInstance().setClickedTrinket(clickedTrinket);
                Intent intent = new Intent(activity, FriendsTrinketDetailsActivity.class);
                activity.startActivity(intent);
            }
        });
    }

    public void friendsFilterButtonOnClick(){
        String category = activity.getCategorySpinner().getSelectedItem().toString();
        Integer distanceTo = distanceValue(activity.getLocationSpinner().getSelectedItem().toString());
        String textQuery = activity.getSearchBox().getText().toString();
        Inventory empty = new Inventory();
        activity.setInventory(empty);

        for(Trinket t: activity.getCompleteInventory()){
            if((category != "None" && t.getCategory() == category) && (distanceTo != -1 && trinketInRange(t, distanceTo)) &&(textQuery != "" && t.getDescription().contains(textQuery) | t.getName().contains(textQuery))){
                empty.add(t);
            }
        }
        activity.getTrinketArrayAdapter().notifyDataSetChanged();
    }

    private Boolean trinketInRange(Trinket t, Integer kmDistance) {
        // Olegas; http://stackoverflow.com/questions/6100967/gps-android-getting-latitude-and-longitude; 2015-11-27
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //fix;http://stackoverflow.com/questions/22063842/check-if-a-latitude-and-longitude-is-within-a-circle; 2015-11-30
        if (location != null) {
            float distanceInM = location.distanceTo(t.getLocation());
            if(distanceInM < kmDistance*1000){
                return true;
            }
            return false;
        }
        return false;
    }

    private Integer distanceValue(String distance){
        switch (distance){
            case "5 km": return 5;
            case "10 km": return 10;
            case "20 km": return 20;
            case "50 km": return 50;
            default: return -1;
        }
    }
}
