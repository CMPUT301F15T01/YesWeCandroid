package ca.ualberta.trinkettrader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Andrea McIntosh on 01/11/2015.
 */
public class AddOrEditItemController {

    private AddOrEditItemActivity activity;
    private Trinket trinket;
    private User user = LoggedInUser.getInstance();

    public AddOrEditItemController(AddOrEditItemActivity activity) {
        this.activity = activity;
    }

    // http://developer.android.com/training/camera/photobasics.html; 2015-11-04
    public void onAddPictureClick(Bitmap picture) throws IOException {
        String mCurrentPhotoPath;

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();

        trinket.getPictures().add(new Picture(image));
    }

    public void onRemovePicturesClick() {
        for (Picture picture: trinket.getPictures()) {
            picture.delete();
        }
        trinket.setPictures(new ArrayList<Picture>());
    }

    public void onSaveClick() {
        this.trinket = new Trinket();
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
