package ca.ualberta.trinkettrader.Inventory.Trinket.Pictures;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import ca.ualberta.trinkettrader.R;

/**
 * This class provides an ArrayAdapter that can work with bitmaps.
 */
public class ImageViewArrayAdapter extends ArrayAdapter<Bitmap> {

    private Context context;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a View to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public ImageViewArrayAdapter(Context context, int resource, List<Bitmap> objects) {
        super(context, resource, objects);
        this.context = context;
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
        }
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.activity_trinket_details_picture, parent, false);
            holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imageView.setImageBitmap(this.getItem(position));
        return convertView;
    }
}
