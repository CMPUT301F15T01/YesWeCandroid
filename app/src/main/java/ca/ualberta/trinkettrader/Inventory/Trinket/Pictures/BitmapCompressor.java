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
import android.content.Context;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Utility class used to compress bitmaps and store them in a new file.  Pictures can be attached to a Trinket to show
 * what it looks like to other users.  All pictures must be less or equal to than 65535 bytes large.  To achieve this,
 * the JPEG image that is attached from the phone's camera or gallery is converted to a bitmap image and
 * put through this compressor, which will ensure that the image is of the maximum size or less.
 */
public class BitmapCompressor {

    private Activity activity;
    private Integer MAX_IMAGE_SIZE = 65535;

    /**
     * Creates a new BitmapCompressor with its activity attribute set to the activity that is calling this constructor.
     *
     * @param activity - android activity used to get the application's context
     */
    public BitmapCompressor(Activity activity) {
        this.activity = activity;
    }

    /**
     * Compresses a given bitmap to less than 65536 bytes and stores it in a new file.  The resulting
     * bitmap will be 65535 bytes or less in size.  By specification, a picture must be less than 65536 bytes for it to be
     * attached to a trinket in the app.
     *
     * @param bitmap - bitmap to compress and store
     * @param outfile -  file to place resulting compressed bitmap in
     * @throws IOException
     */
    public void compressBitmap(Bitmap bitmap, File outfile) throws IOException {
        // stephen1706; http://stackoverflow.com/questions/28760941/compress-image-file-from-camera-to-certain-size; 2015-11-23
        if (outfile.exists()) {
            outfile.delete();
        }

        Integer streamLength = MAX_IMAGE_SIZE;
        Integer compressQuality = 105;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (streamLength >= MAX_IMAGE_SIZE && compressQuality > 5) {
            byteArrayOutputStream.flush();  //to avoid out of memory error
            byteArrayOutputStream.reset();
            compressQuality -= 5;
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, byteArrayOutputStream);
            byte[] bmpPicByteArray = byteArrayOutputStream.toByteArray();
            streamLength = bmpPicByteArray.length;

            FileOutputStream fileOutputStream = activity.openFileOutput(outfile.getName(), Context.MODE_PRIVATE);
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
        }
    }
}
