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

import android.test.ActivityInstrumentationTestCase2;

public class ConfigurationTests extends ActivityInstrumentationTestCase2 {

    public ConfigurationTests(Class activityClass) {
        super(activityClass);
    }

    public void testEditProfile() {
        User user = new User();
        UserProfile profile = user.getUserProfile();
        profile.setName("Name");
        profile.setPostalCode("T6W 1K8");
        assertTrue(profile.getName().equals("Name"));
        assertTrue(profile.getPostalCode().equals("T6W 1K8"));
    }

    public void testEnablePhotoDownload() {
        UserSettings settings = getUserSettings();
        assertTrue(settings.arePhotosDownloadable);
    }

    public void testDisablePhotoDownload() {
        UserSettings settings = getUserSettings();
        assertFalse(settings.arePhotosDownloadable);
    }


}
