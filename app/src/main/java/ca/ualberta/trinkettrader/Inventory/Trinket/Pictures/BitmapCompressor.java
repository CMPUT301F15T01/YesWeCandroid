package ca.ualberta.trinkettrader.Inventory.Trinket.Pictures;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dashley on 15-11-23.
 */
public class BitmapCompressor {

    private Activity activity;
    private Integer MAX_IMAGE_SIZE = 65535;

    public BitmapCompressor(Activity activity) {
        this.activity = activity;
    }

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
