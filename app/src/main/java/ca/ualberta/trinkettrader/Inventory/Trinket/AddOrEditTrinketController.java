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
import android.location.Location;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.Inventory.InventoryActivity;
import ca.ualberta.trinkettrader.Inventory.Trinket.Pictures.Picture;
import ca.ualberta.trinkettrader.R;
import ca.ualberta.trinkettrader.User.LoggedInUser;
import ca.ualberta.trinkettrader.User.User;

/**
 * Controller for handling interactions from the AddOrEditTrinketActivity.  The controller manages
 * clicks to the "Add Picture", "Remove Picture" , and "Save" buttons in the
 * AddOrEditTrinketActivity's layout.  Clicking the photo buttons attaches a photograph of the
 * trinket to it, the save button either creates a new trinket or updates the details of an existing
 * one in order to complete the user's request.
 */
public class AddOrEditTrinketController {

    private AddOrEditTrinketActivity activity;
    private Trinket trinket = new Trinket();
    private User user = LoggedInUser.getInstance();
    private ArrayList<Picture> pictures = new ArrayList<>();
    private ArrayList<Picture> picturesToDelete = new ArrayList<>();

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
     * Returns the current trinket being edited.
     *
     * @return Trinket currently being edited
     */
    public Trinket getTrinket() {
        return trinket;
    }

    /**
     * Adds a picture to trinket being added/edited.  This picture will then be visible when a
     * user views the trinket's details.
     *
     * @param picture the picture being added
     * @throws IOException
     */
    public void addPicture(Picture picture) throws IOException {
        pictures.add(picture);
    }

    /**
     * Removes a picture from trinket currently being added/edited.
     *
     * @param picture specific picture to remove
     */
    public void removePicture(Picture picture) {
        pictures.remove(picture);
        picturesToDelete.add(picture);
    }

    /**
     * Handles click from the "Save" button in the AddOrEditTrinketActivity in the event that a new
     * trinket should be added (rather than editing an existing trinket).  A new trinket will be
     * created with the details specified in the AddOrEditTrinketActivity and added to the user's
     * inventory.  The user will then be directed back to the InventoryActivity.
     */
    public void onSaveNewClick() {
        for (Picture picture : picturesToDelete) {
            try {
                picture.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        prepareTrinketForSave();
        this.user.getInventory().add(this.trinket);
        try {
            this.user.saveToNetwork();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Picture picture : pictures) {
            picture.deleteObserver(activity);
        }
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
        this.trinket.setPictures(pictures);
        ArrayList<String> pictureFileNames = new ArrayList<>();
        for (Picture picture : pictures) {
            Log.i("picture", picture.getFilename());
            pictureFileNames.add(picture.getFilename());
        }
        this.trinket.setPictureFileNames(pictureFileNames);

        Location location = new Location("this");
        if (this.activity.getTrinketLatitude().getText().toString().length() == 0) {
            location.setLatitude(LoggedInUser.getInstance().getDefaultLocation().getLatitude());
        } else {
            location.setLatitude(Double.valueOf(this.activity.getTrinketLatitude().getText().toString()));
        }
        if (this.activity.getTrinketLongitude().getText().toString().length() == 0) {
            location.setLongitude(LoggedInUser.getInstance().getDefaultLocation().getLongitude());
        } else {
            location.setLongitude(Double.valueOf(this.activity.getTrinketLongitude().getText().toString()));
        }
        this.trinket.setLocation(location);
    }

    /**
     * Handles click from the "Save" button in the AddOrEditTrinketActivity in the event that an
     * existing trinket is being edited (rather than a new one being created).  The existing trinket
     * int he user's inventory will be updated with the details specified in the AddOrEditTrinketActivity.
     * The user will then be directed back to the InventoryActivity.
     */
    public void onSaveEditClick() {
        for (Picture picture : picturesToDelete) {
            try {
                picture.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        prepareTrinketForSave();
        this.user.getInventory().set(this.user.getInventory().indexOf(ApplicationState.getInstance().getClickedTrinket()), this.trinket);
        for (Picture picture : pictures) {
            picture.deleteObserver(activity);
        }
        try {
            this.user.saveToNetwork();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(activity, InventoryActivity.class);
        activity.startActivity(intent);
    }

    public ArrayList<Picture> getPictures() {
        return pictures;
    }
}
