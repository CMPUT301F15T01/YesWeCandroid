package ca.ualberta.trinkettrader;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by anju on 09/10/15.
 */
public class ConfigurationTests extends ActivityInstrumentationTestCase2 {
    public void testEditProfile(){
        User user = new User();
        Profile profile = user.getProfile();
        profile.setName('Name');
        profile.setPostalCode('T6w 1K8');
        assertTrue(profile.getName().equals('Name'));
        assertTrue(profile.getPostalCode().equals('T6w 1K8'));
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
