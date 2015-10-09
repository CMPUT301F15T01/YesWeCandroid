package ca.ualberta.trinkettrader;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by anju on 09/10/15.
 */
public class PhotographsOfItemsTests extends ActivityInstrumentationTestCase2 {
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
