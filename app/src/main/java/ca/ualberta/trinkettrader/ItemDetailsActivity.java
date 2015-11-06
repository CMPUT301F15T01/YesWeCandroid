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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ItemDetailsActivity extends AppCompatActivity {

    private Button deleteButton;
    private Button editButton;
    private ImageView imageView;
    private Trinket item;
    private ItemDetailsController controller;
    private ItemDetailsActivity activity = this;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        this.item = ApplicationState.getInstance().getClickedTrinket();

        this.deleteButton = (Button) findViewById(R.id.deleteItemButton);
        this.editButton = (Button) findViewById(R.id.edit_button);
        this.controller = new ItemDetailsController(this);

        imageView = (ImageView) findViewById(R.id.imageView);
        if (item.getPicture() != null) {
            imageView.setImageBitmap(item.getPicture().getBitmap());
        }

        this.dialog = setupAlertDialog();
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getEditButton() {
        return editButton;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Trinket getItem() {
        return item;
    }

    public void deleteClick(View view) {
        dialog.show();
    }

    public AlertDialog getDialog() {
        return dialog;
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

    public void editClick(View view) {
        controller.onEditClick();
    }
}
