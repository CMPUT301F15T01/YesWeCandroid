package ca.ualberta.trinkettrader.Friends;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.Inventory.Trinket.Trinket;

/**
 * Controller for Activity to view the Inventories of all Friends in FriendsList.
 */
public class AllFriendsInventoriesController {

    private AllFriendsInventoriesActivity activity;

    public AllFriendsInventoriesController(AllFriendsInventoriesActivity activity) {
        this.activity = activity;
    }

    /**
     * Response method when user Clicks Filter Button on Inventories Activity. This method gathers
     * any filter conditions like text queries, category and distance from current location and
     * checks all the trinkets on this condition and then updates the ArrayAdapter that displays the
     * trinkets.
     */
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
