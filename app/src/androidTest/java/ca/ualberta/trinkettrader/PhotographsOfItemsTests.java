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

public class PhotographsOfItemsTests extends ActivityInstrumentationTestCase2 {

    public PhotographsOfItemsTests(Class activityClass) {
        super(activityClass);
    }

    public void testAttatchPhotoGraph(){
        Photograph photograph = new Photograph("<path/to/photo>");
        Trinket trinket = new Trinket();
        trinket.attatchPhoto(photograph);
        assertTrue(trinket.photos.contains(photograph));
    }

    public void testDeletePhotoGraph(){
        Photograph photograph = new Photograph("<path/to/photo>");
        Trinket trinket = new Trinket();
        trinket.attatchPhoto(photograph);
        assertTrue(trinket.photos.contains(photograph));
        trinket.deletePhoto(photograph);
        assertFalse(trinket.photos.contains(photograph));
    }

    public void testConstrainPhotographSize(){
        /**
         * How do we validly test this? This use case is specific to a sysadmin.
         * Should a user extend that?
         */
        Photograph photograph = new Photograph("<path/to/photo>");
        /*How to check that photograph is within 65536? */
        Trinket profile = new Trinket();
        trinket.attatchPhoto(photograph);
        assertTrue(trinket.photos.contains(photograph));
        assertTrue(photograph.getSize()<= 65536);
    }


    public void testViewPhotograph(){
        /*This looks like it has to be an activity test...do all use cases have to be in
        * 'model' tests when their functionality is better suited for the view? */
        Trinket profile = new Trinket();
        Photograph photo = profile.getPhotographs("1");
        assertTrue(photo.isVisible());
    }


    public void testManuallyChoosePhotosToDownloadIfPhotoDownloadDisabled(){
        /**
         * This is another test that looks like it should be an activity test.
         * Changes will be made once those tests are underway.
          */

     /*Assert that default photo download is disabled*/
        User user = new User();
        UserSettings settings = user.getUserSettings();
        assertFalse(settings.arePhotosDownloadable);

        /*Select photos to download*/


    }

    public void testDisablePhotoDownload(){
     /*Assert that default photo download is disabled*/
        User user = new User();
        UserSettings settings = user.getUserSettings();
        assertFalse(settings.arePhotosDownloadable);
    }
}
