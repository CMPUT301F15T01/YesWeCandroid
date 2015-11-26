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
 * Controls the directory on the device's memory used to store pictures in this app.
 */
public class PictureDirectoryManager {

    private Activity activity;

    /**
     * Creates a new PictureDirectoryManager from an android activity.
     *
     * @param activity android activity used to get the application's context
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
        return compressPicture(infile.getName(), pictureByteArray);
    }

    /**
     * Compresses a picture to under 65536 bytes and stores it in the application's pictures directory.
     *
     * @param filename         name of the file to store the picture in
     * @param pictureByteArray byte array containing the picture
     * @return file containing the compressed picture
     * @throws IOException
     * @throws PackageManager.NameNotFoundException
     */
    public File compressPicture(String filename, byte[] pictureByteArray) throws IOException, PackageManager.NameNotFoundException {
        Bitmap bitmap = BitmapFactory.decodeByteArray(pictureByteArray, 0, pictureByteArray.length);
        return compressPicture(filename, bitmap);
    }

    /**
     * Compresses a picture to under 65536 bytes and stores it in the application's pictures directory.
     *
     * @param filename name of the file to store the picture in
     * @param bitmap   bitmap to compress and store
     * @return file containing the compressed picture
     * @throws IOException
     * @throws PackageManager.NameNotFoundException
     */
    public File compressPicture(String filename, Bitmap bitmap) throws IOException, PackageManager.NameNotFoundException {
        File outfile = new File(getPictureDirectory() + "/files/" + filename);
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
