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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class AddOrEditItemActivity extends AppCompatActivity {

    private Button addPictureButton;
    private Button removePictureButton;
    private Button saveButton;
    private CheckBox accessibility;
    private EditText itemDescription;
    private EditText itemName;
    private EditText itemQuantity;
    private Spinner itemCategory;
    private Spinner itemQuality;

    private AddOrEditItemController controller;

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
    }

    public CheckBox getAccessibility() {
        return accessibility;
    }

    public Button getAddPictureButton() {
        return addPictureButton;
    }

    public AddOrEditItemController getController() {
        return controller;
    }

    public Spinner getItemCategory() {
        return itemCategory;
    }

    public EditText getItemDescription() {
        return itemDescription;
    }

    public EditText getItemName() {
        return itemName;
    }

    public Spinner getItemQuality() {
        return itemQuality;
    }

    public EditText getItemQuantity() {
        return itemQuantity;
    }

    public Button getRemovePictureButton() {
        return removePictureButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public void addPictureClick(View view) {
        controller.onAddPictureClick();
    }

    public void removePictureClick(View view) {
        controller.onRemovePictureClick();
    }

    public void saveClick(View view) {
        controller.onSaveClick();
    }
}
