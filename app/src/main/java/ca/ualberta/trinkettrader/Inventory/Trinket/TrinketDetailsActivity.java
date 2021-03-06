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
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.meetme.android.horizontallistview.HorizontalListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.Inventory.Trinket.Pictures.ImageViewArrayAdapter;
import ca.ualberta.trinkettrader.Inventory.Trinket.Pictures.Picture;
import ca.ualberta.trinkettrader.R;
import ca.ualberta.trinkettrader.User.LoggedInUser;

/**
 * Android activity for displaying the details of a trinket.  Some of the trinkets details are
 * displayed statically, and there are buttons that allow you to delete the trinket or edit its
 * details with the AddOrEditTrinketActivity.  If the trinket has photos attached to it those will
 * be displayed, too.
 */
public class TrinketDetailsActivity extends Activity implements Observer {


    private TextView category;
    private TextView description;
    private TextView latitude;
    private TextView longitude;
    private TextView name;
    private TextView quality;
    private TextView quantity;

    private AlertDialog dialog;
    private Button deleteButton;
    private Button editButton;
    private Button addToTradeButton;
    private HorizontalListView gallery;
    private Trinket item;
    private TrinketDetailsActivity activity = this;
    private TrinketDetailsController controller;
    private ArrayList<Picture> pictures;
    private ArrayAdapter<Picture> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trinket_details);

        this.item = ApplicationState.getInstance().getClickedTrinket();

        this.category = (TextView) findViewById(R.id.trinketCategory);
        this.description = (TextView) findViewById(R.id.trinketDescription);
        this.latitude = (TextView) findViewById(R.id.trinketLatitude);
        this.longitude = (TextView) findViewById(R.id.trinketLongitude);
        this.name = (TextView) findViewById(R.id.trinketName);
        this.quality = (TextView) findViewById(R.id.trinketQuality);
        this.quantity = (TextView) findViewById(R.id.trinketQuantity);

        this.addToTradeButton = (Button) findViewById(R.id.addYourTrinketToTradeButton);
        this.controller = new TrinketDetailsController(this);
        this.deleteButton = (Button) findViewById(R.id.deleteItemButton);
        this.editButton = (Button) findViewById(R.id.edit_button);
        this.gallery = (HorizontalListView) findViewById(R.id.gallery);

        this.pictures = item.getPictures();
        if (LoggedInUser.getInstance().getProfile().getArePhotosDownloadable()) {
            for (Picture picture: pictures) {
                try {
                    picture.loadPicture();
                } catch (IOException | PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        this.adapter = new ImageViewArrayAdapter(this, R.layout.activity_trinket_details_picture, this.pictures, Boolean.FALSE);
        this.gallery.setAdapter(this.adapter);
        this.adapter.notifyDataSetChanged();

        this.gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Picture picture = pictures.get(position);
                try {
                    picture.loadPicture();
                } catch (IOException | PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }
        });

        controller.updateTextViews();
        this.dialog = setupAlertDialog();
    }

    private AlertDialog setupAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete trinket?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        controller.onDeleteClick(item);
                        NavUtils.navigateUpFromSameTask(activity);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }


    public TextView getNameTextView() {
        return name;
    }

    public TextView getDescriptionTextView() {
        return description;
    }

    public TextView getQuantityTextView() {
        return quantity;
    }

    public TextView getQualityTextView() {
        return quality;
    }

    public TextView getCategoryTextView() {
        return category;
    }

    /**
     * Returns the button from the activity's user interface that lets the user delete the trinket.
     *
     * @return Button - button for deleting the trinket being displayed in the activity
     */
    public Button getDeleteButton() {
        return deleteButton;
    }

    /**
     * Returns the button from the activity's user interface that lets the user add the trinket
     * to the user's trade.
     *
     * @return Button - button for adding the trinket to a trade
     */
    public Button getAddToTradeButton() {
        return addToTradeButton;
    }

    /**
     * Returns the button from the activity's user interface that lets the user edit the trinket's
     * details through the AddOrEditTrinketActivity.
     *
     * @return Button - button for editing the trinket's details
     */
    public Button getEditButton() {
        return editButton;
    }

    /**
     * Returns the Gallery displaying all of the photos attached to the trinket.
     *
     * @return Gallery - gallery containing each photo attached to the trinket
     */
    public HorizontalListView getGallery() {
        return this.gallery;
    }

    /**
     * Returns the trinket object whose details are currently being displayed.
     *
     * @return Trinket - trinket currently being displayed by the activity
     */
    public Trinket getItem() {
        return item;
    }

    /**
     * Method called when the "Delete" button is clicked.  This button allows the trinket currently
     * being displayed to be deleted from the user's inventory.  When the button is clicked a dialog
     * is displayed prompting the user to confirm the deletion.
     *
     * @param view - the button that was clicked
     */
    public void deleteClick(View view) {
        dialog.show();
    }

    public AlertDialog getDialog() {
        return dialog;
    }

    /**
     * Method called with the "Edit Trinket" button is clicked.  Directs the activity's controller
     * to switch the user to the AddOrEditTrinketActivity.
     *
     * @param view - the button that was clicked
     */
    public void editClick(View view) {
        controller.onEditClick();
    }

    public void addYourTrinketToTradeButtonOnClick(View v) {
        controller.addYourTrinketToTradeButtonOnClick();
    }

    public void openMapsOnTrinketOnClick(View view) {
        controller.openMapsOnTrinketOnClick();
    }

    public TextView getLatitude() {
        return latitude;
    }

    public TextView getLongitude() {
        return longitude;
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
        adapter.notifyDataSetChanged();
    }
}
