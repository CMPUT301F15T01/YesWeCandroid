package ca.ualberta.trinkettrader;

import android.content.Intent;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Andrea McIntosh on 01/11/2015.
 */
public class AddOrEditItemController {

    private AddOrEditItemActivity activity;
    private Trinket trinket = new Trinket();
    private User user = LoggedInUser.getInstance();

    public AddOrEditItemController(AddOrEditItemActivity activity) {
        this.activity = activity;
    }

    // http://developer.android.com/training/camera/photobasics.html; 2015-11-04
    // dfserrano; https://github.com/joshua2ua/BogoPicLab/blob/master/BogoPicGen/src/es/softwareprocess/bogopicgen/BogoPicGenActivity.java; 2015-11-04
    public void addPicture(String path) throws IOException {
        trinket.getPictures().add(new Picture(new File(path)));
    }

    public void removePicture() {
        trinket.setPictures(new ArrayList<Picture>());
    }

    public void onSaveNewClick() {
        if (this.activity.getAccessibility().isChecked()) {
            this.trinket.setAccessibility("public");
        } else {
            this.trinket.setAccessibility("private");
        }
        this.trinket.setCategory(new ArrayList<>(Arrays.asList(this.activity.getResources().getStringArray(R.array.spinner_categories))).get(this.activity.getItemCategory().getSelectedItemPosition()));
        this.trinket.setDescription(this.activity.getItemDescription().getText().toString());
        this.trinket.setName(this.activity.getItemName().getText().toString());
        this.trinket.setQuantity(this.activity.getItemQuantity().getText().toString());
        this.trinket.setQuality(new ArrayList<>(Arrays.asList(this.activity.getResources().getStringArray(R.array.spinner_qualities))).get(this.activity.getItemQuality().getSelectedItemPosition()));
        this.user.getInventory().add(this.trinket);

        Intent intent = new Intent(activity, DisplayInventoryActivity.class);
        activity.startActivity(intent);
    }

    public void onSaveEditClick() {
        if (this.activity.getAccessibility().isChecked()) {
            this.trinket.setAccessibility("public");
        } else {
            this.trinket.setAccessibility("private");
        }
        this.trinket.setCategory(new ArrayList<>(Arrays.asList(this.activity.getResources().getStringArray(R.array.spinner_categories))).get(this.activity.getItemCategory().getSelectedItemPosition()));
        this.trinket.setDescription(this.activity.getItemDescription().getText().toString());
        this.trinket.setName(this.activity.getItemName().getText().toString());
        this.trinket.setQuantity(this.activity.getItemQuantity().getText().toString());
        this.trinket.setQuality(new ArrayList<>(Arrays.asList(this.activity.getResources().getStringArray(R.array.spinner_qualities))).get(this.activity.getItemQuality().getSelectedItemPosition()));
        this.user.getInventory().set(this.user.getInventory().indexOf(ApplicationState.getInstance().getClickedTrinket()), this.trinket);

        Intent intent = new Intent(activity, DisplayInventoryActivity.class);
        activity.startActivity(intent);
    }
}
