package ca.ualberta.trinkettrader.Friends;

import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.Inventory.Trinket.Trinket;

/**
 * Created by anju on 29/11/15.
 */
public class AllFriendsInventoriesController {

    private AllFriendsInventoriesActivity activity;

    public AllFriendsInventoriesController(AllFriendsInventoriesActivity activity) {
        this.activity = activity;
    }

    public void friendsFilterButtonOnClick(){
        String category = activity.getCategorySpinner().getSelectedItem().toString();
        String textQuery = activity.getSearchBox().getText().toString();
        Inventory empty = new Inventory();
        activity.setInventory(empty);

        for(Trinket t: activity.getCompleteInventory()){
            if((t.getCategory() == category) && (t.getDescription().contains(textQuery) | t.getName().contains(textQuery))){
                empty.add(t);
            }
        }
        activity.getTrinketArrayAdapter().notifyDataSetChanged();
    }
}
