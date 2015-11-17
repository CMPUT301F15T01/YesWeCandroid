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

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import ca.ualberta.trinkettrader.R;
import ca.ualberta.trinkettrader.Trades.TradesActivity;
import ca.ualberta.trinkettrader.User.LoggedInUser;

public class UserProfileActivity extends AppCompatActivity implements Observer {

    private Button editUserProfileButton;
    private Button tradesButton;
    private UserProfileController controller;
    private Handler handler;
    private Runnable populateTextFieldsWithExistingValuesRunnable = new Runnable() {
        @Override
        public void run() {
            TextView name = (TextView) findViewById(R.id.name);
            TextView address = (TextView) findViewById(R.id.address);
            TextView city = (TextView) findViewById(R.id.city);
            TextView postalCode = (TextView) findViewById(R.id.postal_code);
            TextView phoneNum = (TextView) findViewById(R.id.phone_number);
            TextView photoDownloadEnabled = (TextView) findViewById(R.id.photo_download_setting);

            name.setText(getUserProfile().getName());
            address.setText(getUserProfile().getContactInfo().getAddress());
            city.setText(getUserProfile().getCity());
            postalCode.setText(getUserProfile().getContactInfo().getPostalCode());
            phoneNum.setText(getUserProfile().getContactInfo().getPhoneNumber());
            Boolean isPhotoDownloadEnabled = getUserProfile().getArePhotosDownloadable();
            if (isPhotoDownloadEnabled) {
                photoDownloadEnabled.setText("Photo Download: Enabled");
            } else {
                photoDownloadEnabled.setText("Photo Download: Disabled");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        controller = new UserProfileController(this);

        handler = new Handler();
        handler.post(populateTextFieldsWithExistingValuesRunnable);

        editUserProfileButton = (Button) findViewById(R.id.edit_button);
        editUserProfileButton.setOnClickListener(controller.getEditButtonListener());
        tradesButton = (Button) findViewById(R.id.trade_button_user_profile);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        handler.post(populateTextFieldsWithExistingValuesRunnable);
    }

    /**
     * Returns profile object of current LoggedInUser.
     *
     * @return UserProfile
     */
    public UserProfile getUserProfile() {
        return LoggedInUser.getInstance().getProfile();
    }

    /**
     * Returns button directing to edit user profile activity.
     *
     * @return Button
     */
    public Button getEditUserProfileButton() {
        return editUserProfileButton;
    }

    /**
     * Returns button directing to list of user's current(active) trades.
     *
     * @return Button
     */
    public Button getTradesButton(){ return tradesButton; }

    /**
     * Links to Activity which displays list of user's current trades.
     * @param view
     */
    public void openCurrentTrades(View view){
        Intent intent = new Intent(this, TradesActivity.class);
        startActivity(intent);
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
