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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Observable;

public class Picture extends Observable {

    private byte[] image;
    private volatile File file;

    /**
     *
     * @param file
     * @throws IOException
     */
    public Picture(File file) throws IOException {
        this.file = file;
        FileInputStream fileInputStream = new FileInputStream(file);
        // \u00d3scar L\u00f3pez; http://stackoverflow.com/questions/8721262/how-to-get-file-size-in-java; 2015-11-04
        image = new byte[(int) file.length()];
        fileInputStream.read(image);
        fileInputStream.close();
    }

    public void delete() {
        this.file.delete();
    }

    /**
     * Returns bitmap of the picture.
     * @return Bitmap
     */
    public Bitmap getBitmap() {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    /**
     * Returns size of the picture.
     * @return Long
     */
    public Long size() {
        return file.length();
    }
}
