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

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int SELECT_PICTURE = 2;
    private AddOrEditTrinketController controller;
    private Button pictureLibraryButton;
    private Button removePictureButton;
    private Button saveButton;
    private Button takePictureButton;
    private CheckBox trinketAccessibility;
    private EditText trinketDescription;
    private EditText trinketName;
    private EditText trinketQuantity;
    private Spinner trinketCategory;
    private Spinner trinketQuality;
    private Uri uri;

    /**
     * Gets REQUEST_IMAGE_CAPTURE value. This should only be used for testing purposes.
     *
     * @return REQUEST_IMAGE_CAPTURE
     */
    public static int getRequestImageCapture() {
        return REQUEST_IMAGE_CAPTURE;
    }

    /**
     * Gets REQUEST_IMAGE_CAPTURE value. This should only be used for testing purposes.
     *
     * @return SELECT_PICTURE
     */
    public static int getSelectPicture() {
        return SELECT_PICTURE;
    }

    // hcpl; http://stackoverflow.com/questions/2169649/get-pick-an-image-from-androids-built-in-gallery-app-programmatically; 2015-11-05
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_trinket);

        this.controller = new AddOrEditTrinketController(this);

        this.saveButton = (Button) findViewById(R.id.save_button);
        this.trinketAccessibility = (CheckBox) findViewById(R.id.accessibility_checkbox);
        this.trinketCategory = (Spinner) findViewById(R.id.category_spinner);
        this.trinketDescription = (EditText) findViewById(R.id.description_text);
        this.trinketName = (EditText) findViewById(R.id.name_text);
        this.trinketQuality = (Spinner) findViewById(R.id.quality_spinner);
        this.trinketQuantity = (EditText) findViewById(R.id.quantity_text);

        // Lalit Poptani; http://stackoverflow.com/questions/8119526/android-get-previous-activity; 2015-11-06
        Intent intent = getIntent();
        String prevActivity = intent.getStringExtra("activityName");
        if (prevActivity.equals("edit")) {
            Trinket edited = ApplicationState.getInstance().getClickedTrinket();

            this.trinketAccessibility.setChecked(edited.getAccessibility().equals("public"));
            this.trinketCategory.setSelection(new ArrayList<>(Arrays.asList(this.getResources().getStringArray(R.array.spinner_categories))).indexOf(edited.getCategory()));
            this.trinketDescription.setText(edited.getDescription());
            this.trinketName.setText(edited.getName());
            this.trinketQuality.setSelection(new ArrayList<>(Arrays.asList(this.getResources().getStringArray(R.array.spinner_qualities))).indexOf(edited.getQuality()));
            this.trinketQuantity.setText(edited.getQuantity());

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
     * Click method for taking a picture to attach to an item.
     *
     * @param view current view
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

    /**
     * Click method for selecting a picture from the android gallery.
     *
     * @param view current view
     */
    public void pictureLibraryClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    /**
     * Method to be called after use has taken or selected a picture.
     *
     * @param requestCode type of request that was issued
     * @param resultCode state of the request's execution
     * @param data resulting data of the request
     */
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
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else return null;
    }

    /**
     * Click method directing controller to remove attached image.
     *
     * @param view current view
     */
    public void removePictureClick(View view) {
        try {
            controller.removePicture(new Picture(new File("/")));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    /**
     * Gets Uri. This should only be used for testing purposes.
     *
     * @return Uri
     */
    public Uri getUri() {
        return uri;
    }

    /**
     * Sets Uri. This should only be used for testing purposes.
     *
     * @param uri uri to set value to
     */
    public void setUri(Uri uri) {
        this.uri = uri;
    }

    /**
     * Gets checkbox indicating if item is public or private.
     *
     * @return CheckBox
     */
    public CheckBox getTrinketAccessibility() {
        return trinketAccessibility;
    }

    /**
     * Gets spinner indicating items category.
     *
     * @return Spinner
     */
    public Spinner getTrinketCategory() {
        return trinketCategory;
    }

    /**
     * Gets EditText for item's description.
     *
     * @return EditText
     */
    public EditText getTrinketDescription() {
        return trinketDescription;
    }

    /**
     * Gets EditText field for item's name
     *
     * @return EditText
     */
    public EditText getTrinketName() {
        return trinketName;
    }

    /**
     * Gets Spinner indicating item's quality.
     *
     * @return Spinner
     */
    public Spinner getTrinketQuality() {
        return trinketQuality;
    }

    /**
     * Gets EditText field for item's quantity.
     *
     * @return EditText
     */
    public EditText getTrinketQuantity() {
        return trinketQuantity;
    }

    /**
     * Gets the button that will allow a user to select a picture from the library and attach it to the item.
     *
     * @return Button
     */
    public Button getPictureLibraryButton() {
        return pictureLibraryButton;
    }

    /**
     * Gets the button that will remove an image attached to the item.
     *
     * @return Button
     */
    public Button getRemovePictureButton() {
        return removePictureButton;
    }

    /**
     * Gets the button that will save the item.
     *
     * @return Button
     */
    public Button getSaveButton() {
        return saveButton;
    }

    /**
     * Gets the button that will allow a user to take a new picture and attach it to the item.
     *
     * @return Button
     */
    public Button getTakePictureButton() {
        return takePictureButton;
    }
}
