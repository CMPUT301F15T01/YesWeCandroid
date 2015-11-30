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

package ca.ualberta.trinkettrader.User.Profile;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ToggleButton;

import java.util.Observable;
import java.util.Observer;

import ca.ualberta.trinkettrader.R;
import ca.ualberta.trinkettrader.User.LoggedInUser;

/**
 * Activity to edit and append to the current user's UserProfile instance.
 * This allows edits to the User's personal information.
 */
public class EditUserProfileActivity extends Activity implements Observer {


    private EditUserProfileController controller;
    private ToggleButton arePhotosDownloadableButton;
    private Handler handler;

    /**
     * This runnable serves to populate the edit profile interface with the
     * current information of the user.
     */
    private Runnable populateEditFieldsWithExistingValuesRunnable = new Runnable() {
        @Override
        public void run() {
            CheckBox photoDownloadCheckbox = (CheckBox) findViewById(R.id.photo_download_checkbox);
            EditText address = (EditText) findViewById(R.id.edit_address);
            EditText city = (EditText) findViewById(R.id.edit_city);
            EditText latitude = (EditText) findViewById(R.id.edit_latitude);
            EditText longitude = (EditText) findViewById(R.id.edit_longitude);
            EditText name = (EditText) findViewById(R.id.edit_name);
            EditText phoneNum = (EditText) findViewById(R.id.edit_phone_number);
            EditText postalCode = (EditText) findViewById(R.id.edit_postal_code);

            address.setText(LoggedInUser.getInstance().getProfile().getContactInfo().getAddress());
            city.setText(LoggedInUser.getInstance().getProfile().getCity());
            latitude.setText(Double.toString(LoggedInUser.getInstance().getProfile().getDefaultLocation().getLatitude()));
            longitude.setText(Double.toString(LoggedInUser.getInstance().getProfile().getDefaultLocation().getLongitude()));
            name.setText(LoggedInUser.getInstance().getProfile().getName());
            phoneNum.setText(LoggedInUser.getInstance().getProfile().getContactInfo().getPhoneNumber());
            photoDownloadCheckbox.setChecked(LoggedInUser.getInstance().getProfile().getArePhotosDownloadable());
            postalCode.setText(LoggedInUser.getInstance().getProfile().getContactInfo().getPostalCode());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //setup handler
        handler = new Handler();

        //Instantiate controller
        controller = new EditUserProfileController(this);

        //Populate the EditText fields with existing values
        handler.post(populateEditFieldsWithExistingValuesRunnable);

        //Set onClickListener for saveButton
        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(controller.getSaveButtonListener());
    }

    /**
     * Returns ToggleButton specifying if photos are downloadable or not. This
     * should be primarily used for testing purposes.
     *
     * @return ToggleButton
     */
    public ToggleButton getArePhotosDownloadableButton() {
        return arePhotosDownloadableButton;
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
