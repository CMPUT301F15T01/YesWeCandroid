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
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class AddOrEditTrinketActivity extends AppCompatActivity implements Observer {

    private AddOrEditItemController controller;
    private Button pictureLibraryButton;
    private Button removePictureButton;
    private Button saveButton;
    private Button takePictureButton;
    private CheckBox accessibility;
    private EditText itemDescription;
    private EditText itemName;
    private EditText itemQuantity;
    private Spinner itemCategory;
    private Spinner itemQuality;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int SELECT_PICTURE = 2;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_item);

        this.controller = new AddOrEditItemController(this);

        this.itemName = (EditText) findViewById(R.id.itemNameText);
        this.itemCategory = (Spinner) findViewById(R.id.itemCategorySpinner);
        this.itemQuality = (Spinner) findViewById(R.id.itemQualitySpinner);
        this.itemQuantity = (EditText) findViewById(R.id.itemQuantityText);
        this.accessibility = (CheckBox) findViewById(R.id.accessibilityCheckbox);
        this.saveButton = (Button) findViewById(R.id.saveItemButton);
        this.itemDescription = (EditText) findViewById(R.id.itemDescriptionText);

        // Lalit Poptani, http://stackoverflow.com/questions/8119526/android-get-previous-activity, 2015-11-06
        Intent intent = getIntent();
        String prevActivity = intent.getStringExtra("activityName");
        if (prevActivity.equals("edit")) {
            Trinket edited = ApplicationState.getInstance().getClickedTrinket();
            this.itemName.setText(edited.getName());
            this.itemCategory.setSelection(new ArrayList<>(Arrays.asList(this.getResources().getStringArray(R.array.spinner_categories))).indexOf(edited.getCategory()));
            this.itemQuality.setSelection(new ArrayList<>(Arrays.asList(this.getResources().getStringArray(R.array.spinner_qualities))).indexOf(edited.getQuality()));
            this.itemQuantity.setText(edited.getQuantity());
            this.accessibility.setChecked(edited.getAccessibility().equals("public"));
            this.itemDescription.setText(edited.getDescription());

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    controller.onSaveEditClick();
                }
            });
        } else {
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    controller.onSaveNewClick();
                }
            });
        }
    }

    /**
     * Gets checkbox indicating if item is public or private.
     * @return CheckBox
     */
    public CheckBox getAccessibility() {
        return accessibility;
    }

    /**
     * Returns spinner indicating items category.
     * @return Spinner
     */
    public Spinner getItemCategory() {
        return itemCategory;
    }

    /**
     * Returns EditText for item's description.
     * @return EditText
     */
    public EditText getItemDescription() {
        return itemDescription;
    }

    /**
     * Returns EditText field for item's name
     * @return EditText
     */
    public EditText getItemName() {
        return itemName;
    }

    /**
     * Returns Spinner indicating item's quality.
     * @return Spinner
     */
    public Spinner getItemQuality() {
        return itemQuality;
    }

    /**
     * Returns EditText field for item's quantity.
     * @return EditText
     */
    public EditText getItemQuantity() {
        return itemQuantity;
    }

    public Button getPictureLibraryButton() {
        return pictureLibraryButton;
    }

    /**
     * Returns Button that will remove an image attached to the item.
     * @return Button
     */
    public Button getRemovePictureButton() {
        return removePictureButton;
    }

    /**
     * Return Button that will save the item.
     * @return Button
     */
    public Button getSaveButton() {
        return saveButton;
    }

    public Button getTakePictureButton() {
        return takePictureButton;
    }

    // http://developer.android.com/training/camera/photobasics.html; 2015-11-04

    /**
     * Click method for taking a picture to attach to an item.
     * @param view
     */
    public void takePictureClick(View view) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        uri = Uri.fromFile(image);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    // hcpl; http://stackoverflow.com/questions/2169649/get-pick-an-image-from-androids-built-in-gallery-app-programmatically; 2015-11-05

    /**
     * Click method for selecting a picture from the android gallery.
     * @param view
     */
    public void pictureLibraryClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    // http://developer.android.com/training/camera/photobasics.html; 2015-11-04
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                // malclocke; http://stackoverflow.com/questions/8017374/how-to-pass-a-uri-to-an-intent; 2015-11-04
                controller.addPicture(uri.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            try {
                controller.addPicture(getPath(data.getData()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // mad; http://stackoverflow.com/questions/2169649/get-pick-an-image-from-androids-built-in-gallery-app-programmatically; 2015-11-05
    private String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if(cursor!=null)
        {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        else return null;
    }

    /**
     * Click method directing controller to remove attached image.
     * @param view
     */
    public void removePictureClick(View view) {
        controller.removePicture();
    }

    /**
     * Returns REQUEST_IMAGE_CAPTURE value.
     * @return in
     */
    public static int getRequestImageCapture() {
        return REQUEST_IMAGE_CAPTURE;
    }

    public static int getSelectPicture() {
        return SELECT_PICTURE;
    }

    /**
     * Returns Uri.
     * @return Uri
     */
    public Uri getUri() {
        return uri;
    }

    /**
     * Set Uri.
     * @param uri
     */
    public void setUri(Uri uri) {
        this.uri = uri;
    }

    /**
     * This method is called if the specified {@code Observable} object's
     * {@code notifyObservers} method is called (because the {@code Observable}
     * object has been updated.
     *
     * @param observable the {@link Observable} object.
     * @param data       the data passed to {@link Observable#notifyObservers(Object)}.
     */
    @Override
    public void update(Observable observable, Object data) {

    }
}
