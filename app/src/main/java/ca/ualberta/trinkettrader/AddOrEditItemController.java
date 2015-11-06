package ca.ualberta.trinkettrader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        trinket.setPicture(new Picture(new File(path)));
    }

    public void removePicture() {
        trinket.setPicture(null);
    }

    public void onSaveClick() {
        if (this.activity.getAccessibility().isChecked()) {
            this.trinket.setAccessibility("public");
        } else {
            this.trinket.setAccessibility("private");
        }
        this.trinket.setCategory(this.activity.getItemCategory().toString());
        this.trinket.setDescription(this.activity.getItemDescription().getText().toString());
        this.trinket.setName(this.activity.getItemName().getText().toString());
        this.trinket.setQuantity(this.activity.getItemQuantity().getText().toString());
        this.trinket.setQuality(this.activity.getItemQuality().toString());
        this.user.getInventory().add(this.trinket);

        Intent intent = new Intent(activity, DisplayInventoryActivity.class);
        activity.startActivity(intent);
    }
}
