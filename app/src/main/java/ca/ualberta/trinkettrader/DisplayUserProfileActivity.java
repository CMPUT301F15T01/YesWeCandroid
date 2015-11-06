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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class DisplayUserProfileActivity extends AppCompatActivity {

    private Button editUserProfileButton;
    private DisplayUserProfileController controller;
    private Handler handler;
    private Runnable populateTextFieldsWithExistingValuesRunnable =  new Runnable() {
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
            if(isPhotoDownloadEnabled){
                photoDownloadEnabled.setText("Photo Download: Enabled");
            }else{
                photoDownloadEnabled.setText("Photo Download: Disabled");
            }
        }
    };

    @Override
    protected void onPostResume() {
        super.onPostResume();
        handler.post(populateTextFieldsWithExistingValuesRunnable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user_profile);

        controller = new DisplayUserProfileController(this);

        handler = new Handler();
        handler.post(populateTextFieldsWithExistingValuesRunnable);

        editUserProfileButton = (Button) findViewById(R.id.edit_button);
        editUserProfileButton.setOnClickListener(controller.getEditButtonListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public UserProfile getUserProfile(){return LoggedInUser.getInstance().getProfile();}
    public Button getEditUserProfileButton() {
        return editUserProfileButton;
    }

}
