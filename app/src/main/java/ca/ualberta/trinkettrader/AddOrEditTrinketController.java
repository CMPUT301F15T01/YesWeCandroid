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

import android.content.Intent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AddOrEditTrinketController {

    private AddOrEditTrinketActivity activity;
    private Trinket trinket = new Trinket();
    private User user = LoggedInUser.getInstance();

    public AddOrEditTrinketController(AddOrEditTrinketActivity activity) {
        this.activity = activity;
    }

    /**
     * Adds picture to trinket being added/edited.
     *
     * @param path filepath of the picture being added
     * @throws IOException
     */
    public void addPicture(String path) throws IOException {
        trinket.getPictures().add(new Picture(new File(path)));
    }

    /**
     * Removes a picture from trinket being added/edited.
     *
     * @param picture specific picture to remove
     */
    public void removePicture(Picture picture) {
        trinket.getPictures().remove(picture);
    }

    /**
     * onClick behaviour for when a new item is being saved.
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
     * onClick behaviour for when an existing item is being saved.
     */
    public void onSaveEditClick() {
        prepareTrinketForSave();
        this.user.getInventory().set(this.user.getInventory().indexOf(ApplicationState.getInstance().getClickedTrinket()), this.trinket);
        Intent intent = new Intent(activity, InventoryActivity.class);
        activity.startActivity(intent);
    }
}
