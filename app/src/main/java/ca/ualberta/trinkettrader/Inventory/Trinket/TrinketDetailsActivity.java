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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.Inventory.Trinket.Pictures.Picture;
import ca.ualberta.trinkettrader.R;

public class TrinketDetailsActivity extends AppCompatActivity implements Observer {

    private Button deleteButton;
    private Button editButton;
    private ArrayList<ImageView> imageViews;
    private Trinket item;
    private TrinketDetailsController controller;
    private TrinketDetailsActivity activity = this;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        this.item = ApplicationState.getInstance().getClickedTrinket();

        this.deleteButton = (Button) findViewById(R.id.deleteItemButton);
        this.editButton = (Button) findViewById(R.id.edit_button);
        this.controller = new TrinketDetailsController(this);

        imageViews = new ArrayList<>();
        imageViews.add((ImageView) findViewById(R.id.imageView));
        ArrayList<Picture> pictures = item.getPictures();
        if (!pictures.isEmpty()) {
            imageViews.get(0).setImageBitmap(pictures.get(0).getBitmap());
        }

        this.dialog = setupAlertDialog();
    }

    private AlertDialog setupAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete item?")
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

    /**
     * Returns button for deleting item.
     *
     * @return Button
     */
    public Button getDeleteButton() {
        return deleteButton;
    }

    /**
     * Returns button for editing item.
     *
     * @return Button
     */
    public Button getEditButton() {
        return editButton;
    }

    /**
     * Returns image views.
     *
     * @return ArrayList
     */
    public ArrayList<ImageView> getImageViews() {
        return this.imageViews;
    }

    /**
     * Returns trinke currently being viewed.
     *
     * @return Trinket
     */
    public Trinket getItem() {
        return item;
    }

    /**
     * Handles click on delete item button.
     *
     * @param view
     */
    public void deleteClick(View view) {
        dialog.show();
    }

    public AlertDialog getDialog() {
        return dialog;
    }

    public void editClick(View view) {
        controller.onEditClick();
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
