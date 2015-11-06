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
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import java.util.Observable;
import java.util.Observer;

public class EditProfileActivity extends AppCompatActivity implements Observer {


    public EditProfileActivity() {
    }

    private EditProfileController controller;
    private ToggleButton arePhotosDownloadableButton;
    private Handler handler;

    private Runnable populateEditFieldsWithExistingValuesRunnable =  new Runnable() {
        @Override
        public void run() {
            EditText name = (EditText) findViewById(R.id.edit_name);
            EditText address = (EditText) findViewById(R.id.edit_address);
            EditText city = (EditText) findViewById(R.id.edit_city);
            EditText postalCode = (EditText) findViewById(R.id.edit_postal_code);
            EditText phoneNum = (EditText) findViewById(R.id.edit_phone_number);

            name.setText(getUserProfile().getName());
            address.setText(getUserProfile().getContactInfo().getAddress());
            city.setText(getUserProfile().getCity());
            postalCode.setText(getUserProfile().getContactInfo().getPostalCode());
            phoneNum.setText(getUserProfile().getContactInfo().getPhoneNumber());

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //setup handler
        handler = new Handler();

        //Instantiate controller
        controller = new EditProfileController(this);

        //Populate the EditText fields with existing values
        handler.post(populateEditFieldsWithExistingValuesRunnable);

        //Set onClickListener for saveButton
        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(controller.getSaveButtonListener());
    }

    /**
     * Returns ToggleButton specifying if photos are downloadable or not.
     * @return ToggleButton
     */
    public ToggleButton getArePhotosDownloadableButton() {
        return arePhotosDownloadableButton;
    }

    /**
     * Returns profile of current LoggedInUser.
     * @return UserProfile
     */
    public UserProfile getUserProfile() {
        return LoggedInUser.getInstance().getProfile();
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
