package ca.ualberta.trinkettrader;

import android.content.Intent;
import android.view.View;

/**
 * Created by anju on 03/11/15.
 */
public class DisplayUserProfileController {

    private DisplayUserProfileActivity activity;

    public DisplayUserProfileController(DisplayUserProfileActivity activity) {
        this.activity = activity;
    }

    public View.OnClickListener getEditButtonListener() {
        return editButtonListener;
    }

    private View.OnClickListener editButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(activity.getBaseContext(), EditProfileActivity.class);
            activity.startActivity(i);
        }
    };
}
