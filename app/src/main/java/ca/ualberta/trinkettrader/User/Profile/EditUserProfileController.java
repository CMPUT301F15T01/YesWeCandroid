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

import android.location.Location;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.IOException;

import ca.ualberta.trinkettrader.InternetConnection;
import ca.ualberta.trinkettrader.R;
import ca.ualberta.trinkettrader.User.LoggedInUser;
import ca.ualberta.trinkettrader.User.User;

/**
 * Controller for EditUserProfileActivity.
 */
public class EditUserProfileController {

    private EditUserProfileActivity activity;

    /**
     * This listener encapsulates the act of saving all the information in the
     * user's profile. It can be accessed through the relevant getter.
     */
    private View.OnClickListener saveButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CheckBox photoDownload = (CheckBox) activity.findViewById(R.id.photo_download_checkbox);
            EditText address = (EditText) activity.findViewById(R.id.edit_address);
            EditText city = (EditText) activity.findViewById(R.id.edit_city);
            EditText latitude = (EditText) activity.findViewById(R.id.edit_latitude);
            EditText longitude = (EditText) activity.findViewById(R.id.edit_longitude);
            EditText name = (EditText) activity.findViewById(R.id.edit_name);
            EditText phoneNum = (EditText) activity.findViewById(R.id.edit_phone_number);
            EditText postalCode = (EditText) activity.findViewById(R.id.edit_postal_code);

            Double _latitude;
            if (latitude.getText().toString().length() > 0) {
                _latitude = Double.valueOf(latitude.getText().toString());
            } else {
                _latitude = 0.0;
            }
            Double _longitude;
            if (longitude.getText().toString().length() > 0) {
                _longitude = Double.valueOf(longitude.getText().toString());
            } else {
                _longitude = 0.0;
            }

            Boolean photoDownloadEnabled = photoDownload.isChecked();
            String _address = address.getText().toString();
            String _city = city.getText().toString();
            String _name = name.getText().toString();
            String _phoneNum = phoneNum.getText().toString();
            String _postalCode = postalCode.getText().toString();

            UserProfile userProfile = LoggedInUser.getInstance().getProfile();
            userProfile.getContactInfo().setAddress(_address);
            userProfile.getContactInfo().setPhoneNumber(_phoneNum);
            userProfile.setArePhotosDownloadable(photoDownloadEnabled);
            userProfile.setCity(_city);
            userProfile.setName(_name);
            userProfile.setPostalCode(_postalCode);

            Location location = new Location("this");
            location.setLatitude(_latitude);
            location.setLongitude(_longitude);
            userProfile.setDefaultLocation(location);

            LoggedInUser.getInstance().saveInFile(activity.getBaseContext());
            Boolean cnxn = InternetConnection.getInstance().internetConnectionAvailable(activity.getBaseContext());
            if (cnxn) {
                try {
                    User user = LoggedInUser.getInstance();
                    user.saveToNetwork();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            activity.finish();
        }
    };

    public EditUserProfileController(EditUserProfileActivity activity) {
        this.activity = activity;
    }

    /**
     * Returns onClickListener for saving edits to user profile.
     *
     * @return onCLickListener
     */
    public View.OnClickListener getSaveButtonListener() {
        return saveButtonListener;
    }
}
