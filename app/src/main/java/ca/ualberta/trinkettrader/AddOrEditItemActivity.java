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

    private Button removeImageButton;
    private Button saveButton;
    private CheckBox accessibility;
    private EditText itemDescription;
    private EditText itemName;
    private EditText itemQuantity;
    private Spinner itemCategory;
    private Spinner itemQuality;

    AddOrEditItemController controller;

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
    }

    public CheckBox getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(CheckBox accessibility) {
        this.accessibility = accessibility;
    }

    public Spinner getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(Spinner itemCategory) {
        this.itemCategory = itemCategory;
    }

    public EditText getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(EditText itemDescription) {
        this.itemDescription = itemDescription;
    }

    public EditText getItemName() {
        return itemName;
    }

    public void setItemName(EditText itemName) {
        this.itemName = itemName;
    }

    public Spinner getItemQuality() {
        return itemQuality;
    }

    public void setItemQuality(Spinner itemQuality) {
        this.itemQuality = itemQuality;
    }

    public EditText getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(EditText itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Button getRemoveImageButton() {
        return removeImageButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public void saveClick(View view) {
        controller.onSaveClick();
    }
}
