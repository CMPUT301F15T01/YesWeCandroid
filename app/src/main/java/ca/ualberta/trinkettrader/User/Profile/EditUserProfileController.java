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

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.IOException;

import ca.ualberta.trinkettrader.InternetConnection;
import ca.ualberta.trinkettrader.R;
import ca.ualberta.trinkettrader.User.LoggedInUser;

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
            //TODO: save the changed data remotely - Part 5

            //NOW: Controller takes data from activity, updates UserProfile in LoggedInUser
            //UserProfile (observable) updates its observer (DisplayProfileActivity)

            EditText name = (EditText) activity.findViewById(R.id.edit_name);
            EditText address = (EditText) activity.findViewById(R.id.edit_address);
            EditText city = (EditText) activity.findViewById(R.id.edit_city);
            EditText postalCode = (EditText) activity.findViewById(R.id.edit_postal_code);
            EditText phoneNum = (EditText) activity.findViewById(R.id.edit_phone_number);
            CheckBox photoDownload = (CheckBox) activity.findViewById(R.id.photo_download_checkbox);

            String _name = name.getText().toString();
            String _address = address.getText().toString();
            String _city = city.getText().toString();
            String _postalCode = postalCode.getText().toString();
            String _phoneNum = phoneNum.getText().toString();
            Boolean photoDownloadEnabled = photoDownload.isChecked();

            //Set the LoggedInUser with new data
            UserProfile userProfile = LoggedInUser.getInstance().getProfile();
            userProfile.setName(_name);
            userProfile.getContactInfo().setAddress(_address);
            userProfile.setCity(_city);
            userProfile.setPostalCode(_postalCode);
            userProfile.getContactInfo().setPhoneNumber(_phoneNum);
            userProfile.setArePhotosDownloadable(photoDownloadEnabled);

            LoggedInUser.getInstance().saveInFile(activity.getBaseContext());

            if (InternetConnection.getInstance().internetConnectionAvailable(activity.getBaseContext())) {
                try {
                    LoggedInUser.getInstance().saveToNetwork();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //exit the activity
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
