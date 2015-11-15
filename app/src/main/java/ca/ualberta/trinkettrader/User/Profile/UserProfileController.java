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
import android.view.View;

public class UserProfileController {

    private static final String RESOURCE_URL = "";

    private UserProfileActivity activity;
    private View.OnClickListener editButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(activity.getBaseContext(), EditUserProfileActivity.class);
            activity.startActivity(i);
        }
    };

    public UserProfileController(UserProfileActivity activity) {
        this.activity = activity;
    }

    /**
     * Sets onClickListener for Edit Profile button.
     *
     * @return onClickListener
     */
    public View.OnClickListener getEditButtonListener() {
        return editButtonListener;
    }
}
