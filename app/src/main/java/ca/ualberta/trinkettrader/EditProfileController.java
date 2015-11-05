package ca.ualberta.trinkettrader;

import android.content.Intent;
import android.view.View;

/**
 * Created by anju on 03/11/15.
 */
public class EditProfileController {

    private EditProfileActivity activity;
    public EditProfileController(EditProfileActivity activity) {
        this.activity = activity;
    }

    private View.OnClickListener saveButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO: save the changed data remotely

            Intent i = new Intent(activity.getBaseContext(), EditProfileActivity.class);
            activity.startActivity(i);
        }
    };

    //Must be in runnable


}
