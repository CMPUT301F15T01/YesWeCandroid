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
        Photograph photograph = new Photograph('<path/to/photo>');
        ItemProfile profile = new Item().getProfile();
        profile.attatchPhoto(photograph);
        assertTrue(profile.hasPhotograph('<path/to/photo>'));
    }

    public void testDeletePhotoGraph(){
        Photograph photograph = new Photograph('<path/to/photo>');
        ItemProfile profile = new Item().getProfile();
        profile.deletePhoto(photograph);
        assertFalse(profile.hasPhotograph('<path/to/photo>'));
    }

    public void testConstrainPhotographSize(){
        ItemProfile profile = new Item().getProfile();
        Photograph photo = profile.getPhotographs('1');
        assertTrue(photo.getSize()<= 65536);
    }


    public void testViewPhotograph(){
        ItemProfile profile = new Item().getProfile();
        Photograph photo = profile.getPhotographs('1');
        assertTrue(photo.isVisible());
    }


    public void testEnablePhotoDownload(){
        TTSettings settings = getTTSettings();
        assertTrue(settings.getArePhotosDownloadable);
    }

    public void testDisablePhotoDownload(){
        TTSettings settings = getTTSettings();
        assertFalse(settings.getArePhotosDownloadable);
    }
}
