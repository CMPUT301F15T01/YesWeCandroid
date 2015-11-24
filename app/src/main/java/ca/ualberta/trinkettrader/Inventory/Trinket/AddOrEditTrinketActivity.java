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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.meetme.android.horizontallistview.HorizontalListView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.Inventory.Trinket.Pictures.ImageViewArrayAdapter;
import ca.ualberta.trinkettrader.Inventory.Trinket.Pictures.PictureDirectoryManager;
import ca.ualberta.trinkettrader.R;
import ca.ualberta.trinkettrader.Inventory.Trinket.Pictures.Picture;

/**
 * Android activity class for adding a new trinket to the user's activity, or viewing and editing the
 * details of an existing trinket.
 *
 * The layout contains fields for setting the accessibility, description, name, quality, quantity,
 * and category of the trinket, as well as the photos attached to it.  If a new trinket is being
 * created fields will be set to their default values, or empty if that field has no default value.
 * If an existing trinket is being edited, these fields will be populated with the trinket's current
 * values.  A photo can be attached to the trinket to show what the trinket looks like.  This photo
 * can be taken from the phone's camera or gallery.
 */
public class AddOrEditTrinketActivity extends AppCompatActivity implements Observer {

    private AddOrEditTrinketController controller;
    private ArrayAdapter<Picture> adapter;
    private ArrayList<Picture> pictures;
    private Button pictureLibraryButton;
    private Button removePictureButton;
    private Button saveButton;
    private Button takePictureButton;
    private CheckBox trinketAccessibility;
    private EditText trinketDescription;
    private EditText trinketName;
    private EditText trinketQuantity;
    private HorizontalListView gallery;
    private Spinner trinketCategory;
    private Spinner trinketQuality;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int SELECT_PICTURE = 2;
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

        this.gallery = (HorizontalListView) findViewById(R.id.gallery);
        this.saveButton = (Button) findViewById(R.id.save_button);
        this.trinketAccessibility = (CheckBox) findViewById(R.id.accessibility_checkbox);
        this.trinketCategory = (Spinner) findViewById(R.id.category_spinner);
        this.trinketDescription = (EditText) findViewById(R.id.description_text);
        this.trinketName = (EditText) findViewById(R.id.name_text);
        this.trinketQuality = (Spinner) findViewById(R.id.quality_spinner);
        this.trinketQuantity = (EditText) findViewById(R.id.quantity_text);

        this.pictures = new ArrayList<>();

        // Lalit Poptani; http://stackoverflow.com/questions/8119526/android-get-previous-activity; 2015-11-06
        Intent intent = getIntent();
        String prevActivity = intent.getStringExtra("activityName");
        if (prevActivity.equals("edit")) {
            Trinket edited = ApplicationState.getInstance().getClickedTrinket();

            this.trinketAccessibility.setChecked(edited.getTrinketAccessibility().equals("public"));
            this.trinketCategory.setSelection(new ArrayList<>(Arrays.asList(this.getResources().getStringArray(R.array.spinner_categories))).indexOf(edited.getCategory()));
            this.trinketDescription.setText(edited.getDescription());
            this.trinketName.setText(edited.getName());
            this.trinketQuality.setSelection(new ArrayList<>(Arrays.asList(this.getResources().getStringArray(R.array.spinner_qualities))).indexOf(edited.getQuality()));
            this.trinketQuantity.setText(edited.getQuantity());
            this.controller.getTrinket().setPictures(edited.getPictures());

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    controller.onSaveEditClick();
                }
            });
        } else {
            this.trinketAccessibility.setChecked(true);
            this.trinketQuantity.setText("1");

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    controller.onSaveNewClick();
                }
            });
        }

        this.adapter = new ImageViewArrayAdapter(this, R.layout.activity_trinket_details_picture, this.pictures, Boolean.TRUE);
        this.gallery.setAdapter(this.adapter);
        updatePictures();

        this.gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                deletePictureDialog(position);
            }
        });
    }

    private void deletePictureDialog(final Integer position) {
        final Activity activity = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete picture?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        controller.removePicture(pictures.get(position));
                        updatePictures();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    /**
     * Click method for the "Take Photo" button that handles taking a picture to attach to a trinket.
     * Creates a unique filename for the photo and sets the directory it will be saved to, then
     * starts the phone's camera app to take the photo.
     *
     * @param view - button that was clicked
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
     * Click method for the "Photo Library" button that selects a picture from the android gallery.
     * Open's phone's picture gallery so that a photo can be selected.
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
     * Method to be called after user has taken or selected a picture.
     *
     * @param requestCode type of request that was issued
     * @param resultCode state of the request's execution
     * @param data resulting data of the request
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            PictureDirectoryManager directoryManager = new PictureDirectoryManager(this);
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                try {
                    // malclocke; http://stackoverflow.com/questions/8017374/how-to-pass-a-uri-to-an-intent; 2015-11-04
                    this.addPicture(new Picture(new File(uri.getPath()), directoryManager));
                } catch (IOException | PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            } else if (requestCode == SELECT_PICTURE) {
                try {
                    this.addPicture(new Picture(new File(getPath(data.getData())), directoryManager));
                } catch (IOException | PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void addPicture(Picture newPicture) {
        try {
            this.controller.addPicture(newPicture);
            this.updatePictures();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void updatePictures() {
        pictures.clear();
        pictures.addAll(controller.getPictures());
        adapter.notifyDataSetChanged();
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
     * Returns checkbox from the activity's user interface indicating if trinket is public or private.
     *
     * @return CheckBox - checkbox indicating it's accessibility.  The box being checked indicates
     * that the item is public, while uncheck means the item is private.
     */
    public CheckBox getTrinketAccessibility() {
        return trinketAccessibility;
    }

    /**
     * Returns spinner from the activity's user interface indicating the trinket's category.
     *
     * @return Spinner - spinner used for selecting the trinket's category from one of 10 categories.
     */
    public Spinner getTrinketCategory() {
        return trinketCategory;
    }

    /**
     * Returns the text field from the activity's user interface for writing trinket's description.
     *
     * @return EditText - editable text field where the trinket's description can be added or edited.
     */
    public EditText getTrinketDescription() {
        return trinketDescription;
    }

    /**
     * Returns the text field from the activity's user interface for writing trinket's name.
     *
     * @return EditText - editable text field where the trinket's name can be added or edited.
     */
    public EditText getTrinketName() {
        return trinketName;
    }

    /**
     * Returns spinner from the activity's user interface indicating the trinket's quality.
     *
     * @return Spinner - spinner used for selecting the trinket's quality as either "good", "average"
     * or "poor".
     */
    public Spinner getTrinketQuality() {
        return trinketQuality;
    }

    /**
     * Returns the text field from the activity's user interface for setting the trinket's quantity.
     * The trinket's default quantity is 1.
     *
     * @return EditText - editable text field where the trinket's quantity can be added or edited.
     */
    public EditText getTrinketQuantity() {
        return trinketQuantity;
    }

    /**
     * Returns the "Picture Library" button from the activity's user interface that will allow a
     * user to select a picture from the library and attach it to the item.
     *
     * @return Button - button for selecting a photo from the phone's gallery to attach to the trinket
     */
    public Button getPictureLibraryButton() {
        return pictureLibraryButton;
    }

    /**
     * Returns the button from the activity's user interface that will remove an image attached to the item.
     *
     * @return Button - button for removing a photo that has been attached to the trinket
     */
    public Button getRemovePictureButton() {
        return removePictureButton;
    }

    /**
     * Returns the button from the activity's user interface that will save the trinket to the user's
     * inventory.
     *
     * @return Button - button for saving the new or edited trinket
     */
    public Button getSaveButton() {
        return saveButton;
    }

    /**
     * Returns the button from the activity's user interface that will allow a user to take a new
     * picture and attach it to the item.
     *
     * @return Button - button for taking a new photo to attach to the trinket
     */
    public Button getTakePictureButton() {
        return takePictureButton;
    }
}
