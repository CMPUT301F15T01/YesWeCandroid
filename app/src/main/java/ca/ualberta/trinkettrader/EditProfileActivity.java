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
import android.widget.EditText;
import android.widget.ToggleButton;

import java.util.logging.Handler;

public class EditProfileActivity extends AppCompatActivity {


    public EditProfileActivity() {
    }

    private UserProfile userProfile;
    private EditProfileController controller;
    private ToggleButton arePhotosDownloadableButton;
    private Handler handler;

    private Runnable populateEditFieldsWithExistingValues =  new Runnable() {
        @Override
        public void run() {
            EditText name = (EditText) findViewById(R.id.edit_name);
            EditText address = (EditText) findViewById(R.id.edit_address);
            EditText city = (EditText) findViewById(R.id.edit_city);
            EditText postalCode = (EditText) name.findViewById(R.id.edit_postal_code);
            EditText phoneNum = (EditText) name.findViewById(R.id.edit_phone_number);

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

        //Instantiate controller
        controller = new EditProfileController(this, userProfile);

        //Populate the EditText fields with existing values

        //Set onClickListener for
    }

    public ToggleButton getArePhotosDownloadableButton() {
        return arePhotosDownloadableButton;
    }

    public UserProfile getUserProfile() {
        return LoggedInUser.getInstance().getProfile();
    }
}
