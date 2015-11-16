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

import android.content.Intent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.Inventory.InventoryActivity;
import ca.ualberta.trinkettrader.R;
import ca.ualberta.trinkettrader.Inventory.Trinket.Pictures.Picture;
import ca.ualberta.trinkettrader.User.LoggedInUser;
import ca.ualberta.trinkettrader.User.User;

/**
 * Controller for handling interactions from the AddOrEditTrinketActivity.
 */
public class AddOrEditTrinketController {

    private AddOrEditTrinketActivity activity;
    private Trinket trinket = new Trinket();
    private User user = LoggedInUser.getInstance();

    /**
     * Constructs a controller with the activity this constructor is attached to.  Each controller
     * can only be used by one activity.
     *
     * @param activity - The activity this controller is attached to
     */
    public AddOrEditTrinketController(AddOrEditTrinketActivity activity) {
        this.activity = activity;
    }

    /**
     * Adds a picture to trinket being added/edited.  This picture will then be visible when a
     * user views the trinket's details.
     *
     * @param path filepath of the picture being added
     * @throws IOException
     */
    public void addPicture(String path) throws IOException {
        trinket.getPictures().add(new Picture(new File(path)));
    }

    /**
     * Removes a picture from trinket currently being added/edited.
     *
     * @param picture specific picture to remove
     */
    public void removePicture(Picture picture) {
        trinket.getPictures().remove(picture);
    }

    /**
     * Handles click from the "Save" button in the AddOrEditTrinketActivity in the event that a new
     * trinket should be added (rather than editing an existing trinket).  A new trinket will be
     * created with the details specified in the AddOrEditTrinketActivity and added to the user's
     * inventory.  The user will then be directed back to the InventoryActivity.
     */
    public void onSaveNewClick() {
        prepareTrinketForSave();
        this.user.getInventory().add(this.trinket);
        Intent intent = new Intent(activity, InventoryActivity.class);
        activity.startActivity(intent);
    }

    private void prepareTrinketForSave() {
        if (this.activity.getTrinketAccessibility().isChecked()) {
            this.trinket.setAccessibility("public");
        } else {
            this.trinket.setAccessibility("private");
        }
        this.trinket.setCategory(new ArrayList<>(Arrays.asList(this.activity.getResources().getStringArray(R.array.spinner_categories))).get(this.activity.getTrinketCategory().getSelectedItemPosition()));
        this.trinket.setDescription(this.activity.getTrinketDescription().getText().toString());
        this.trinket.setName(this.activity.getTrinketName().getText().toString());
        this.trinket.setQuantity(this.activity.getTrinketQuantity().getText().toString());
        this.trinket.setQuality(new ArrayList<>(Arrays.asList(this.activity.getResources().getStringArray(R.array.spinner_qualities))).get(this.activity.getTrinketQuality().getSelectedItemPosition()));
    }

    /**
     * Handles click from the "Save" button in the AddOrEditTrinketActivity in the event that an
     * existing trinket is being edited (rather than a new one being created).  The existing trinket
     * int he user's inventory will be updated with the details specified in the AddOrEditTrinketActivity.
     * The user will then be directed back to the InventoryActivity.
     */
    public void onSaveEditClick() {
        prepareTrinketForSave();
        this.user.getInventory().set(this.user.getInventory().indexOf(ApplicationState.getInstance().getClickedTrinket()), this.trinket);
        Intent intent = new Intent(activity, InventoryActivity.class);
        activity.startActivity(intent);
    }
}
