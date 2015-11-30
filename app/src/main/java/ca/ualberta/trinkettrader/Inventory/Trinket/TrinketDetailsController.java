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

package ca.ualberta.trinkettrader.Inventory.Trinket;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.Trades.CounterTradeActivity;
import ca.ualberta.trinkettrader.Trades.CreateTradeActivity;
import ca.ualberta.trinkettrader.User.LoggedInUser;

/**
 * Controller for handling interactions from the TrinketDetailsActivity.  The controller manages
 * clicks to the "Delete" and "Edit" buttons in the InventoryActivity's layout.  Clicking the delete
 * button generates an AlertDialog to confirm the deletion of the trinket, and the edit button
 * navigates the user to a new activity in order to complete the user's request.
 */
public class TrinketDetailsController {

    private TrinketDetailsActivity activity;
    private Trinket clickedTrinket = ApplicationState.getInstance().getClickedTrinket();

    /**
     * Constructs a controller with the activity this constructor is attached to.  Each controller
     * can only be used by one activity.
     *
     * @param activity - The activity this controller is attached to
     */
    public TrinketDetailsController(TrinketDetailsActivity activity) {
        this.activity = activity;
    }

    /**
     * Handles removing the trinket displayed in the TrinketDetailsActivity from the user's
     * inventory after the user clicks the "Delete Trinket" button and then clicks the "Confirm"
     * button on the ensuing deletion confirmation dialog.
     *
     * @param trinket - trinket to remove from the user's inventory
     */
    public void onDeleteClick(Trinket trinket) {
        LoggedInUser.getInstance().getInventory().remove(trinket);
    }

    /**
     * Handles click form the "Edit Trinket" button in the TrinketDetailsActivity.  Starts the
     * AddOrEditTrinketActivity with its trinket editing functionality (rather than its adding
     * functionality).
     */
    public void onEditClick() {
        Intent intent = new Intent(this.activity, AddOrEditTrinketActivity.class);
        intent.putExtra("activityName", "edit");
        activity.startActivity(intent);

    }

    public void addYourTrinketToTradeButtonOnClick() {

        if (LoggedInUser.getInstance().getFriendsList().isEmpty()) {
            // David Hedlund; http://stackoverflow.com/questions/2115758/how-to-display-alert-dialog-in-android; 2015-11-29
            new AlertDialog.Builder(activity)
                    .setTitle("No Friends :(")
                    .setMessage("You do not have any friends to add to a trade with!")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

            return;
        }

        Trinket clickedTrinket = ApplicationState.getInstance().getClickedTrinket();
        ApplicationState.getInstance().getYourTradeTrinkets().add(clickedTrinket);

        if (ApplicationState.getInstance().getInCounterTrade()) {
            Intent intent = new Intent(activity, CounterTradeActivity.class);
            activity.startActivity(intent);
        } else {
            Intent intent = new Intent(activity, CreateTradeActivity.class);
            activity.startActivity(intent);
        }
    }

    public void openMapsOnTrinketOnClick() {
    }

    public void updateTextViews() {
        activity.getCategoryTextView().setText(clickedTrinket.getCategory());
        activity.getDescriptionTextView().setText(clickedTrinket.getDescription());
        activity.getLatitude().setText(String.format("%.4f", clickedTrinket.getLocation().getLatitude()));
        activity.getLongitude().setText(String.format("%.4f", clickedTrinket.getLocation().getLongitude()));
        activity.getNameTextView().setText(clickedTrinket.getName());
        activity.getQualityTextView().setText(clickedTrinket.getQuality());
        activity.getQuantityTextView().setText(clickedTrinket.getQuantity());
    }
}
