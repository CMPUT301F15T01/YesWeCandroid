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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ca.ualberta.trinkettrader.R;

/**
 * This class provides an ArrayAdapter that can work with pictures.
 */
public class ImageViewArrayAdapter extends ArrayAdapter<Picture> {

    private Boolean showSizes;
    private Context context;

    /**
     * Returns a new ImageViewArrayAdapter used to display images.
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a View to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public ImageViewArrayAdapter(Context context, int resource, List<Picture> objects, Boolean showSizes) {
        super(context, resource, objects);
        this.context = context;
        this.showSizes = showSizes;
    }

    /**
     * {@inheritDoc}
     *
     * @param position
     * @param convertView
     * @param parent
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Tanis.7x; http://stackoverflow.com/questions/19454602/android-bitmap-listview; 2015-11-18
        // Raghunandan; http://stackoverflow.com/questions/19454602/android-bitmap-listview; 2015-11-18
        class ViewHolder {
            ImageView imageView;
            TextView imageSize;
        }
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.activity_trinket_details_picture, parent, false);
            holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
            holder.imageSize = (TextView) convertView.findViewById(R.id.size_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Picture picture = this.getItem(position);
        holder.imageView.setImageBitmap(picture.getBitmap());
        if (showSizes) {
            holder.imageSize.setText("Size: " + picture.size());
        }
        return convertView;
    }
}
