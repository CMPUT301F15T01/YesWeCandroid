package ca.ualberta.trinkettrader.Inventory.Trinket.Pictures;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by dashley on 15-11-23.
 */
public class PictureDirectoryManager {

    private Activity activity;

    /**
     * Constructor.
     *
     * @param activity android activity
     */
    public PictureDirectoryManager(Activity activity) {
        this.activity = activity;
    }

    /**
     * Compresses a picture to under 65536 bytes and stores it in the application's pictures directory.
     *
     * @param infile file containing the picture to compress
     * @return file containing the compressed picture
     */
    public File compressPicture(File infile) throws IOException, PackageManager.NameNotFoundException {
        // \u00d3scar L\u00f3pez; http://stackoverflow.com/questions/8721262/how-to-get-file-size-in-java; 2015-11-04
        byte[] pictureByteArray = new byte[(int) infile.length()];
        FileInputStream fileInputStream = new FileInputStream(infile);
        fileInputStream.read(pictureByteArray);
        fileInputStream.close();

        Bitmap bitmap = BitmapFactory.decodeByteArray(pictureByteArray, 0, pictureByteArray.length);
        File outfile = new File(getPictureDirectory() + "/files/" + infile.getName());

        BitmapCompressor compressor = new BitmapCompressor(activity);
        compressor.compressBitmap(bitmap, outfile);

        return outfile;
    }

    private String getPictureDirectory() throws PackageManager.NameNotFoundException {
        // Philip Sheard; http://stackoverflow.com/questions/5527764/get-application-directory; 2015-11-23
        PackageManager packageManager = activity.getPackageManager();
        String packageName = activity.getPackageName();
        PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
        return packageInfo.applicationInfo.dataDir;
    }

    /**
     * Loads a picture from the application's directory and returns it.
     *
     * @param name name of the picture to load
     * @return picture loaded from the application's directory
     * @throws IOException
     * @throws PackageManager.NameNotFoundException
     */
    public Picture loadPicture(String name) throws IOException, PackageManager.NameNotFoundException {
        return new Picture(new File(getPictureDirectory() + "/" + name), new PictureDirectoryManager(activity));
    }
}
