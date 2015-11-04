package ca.ualberta.trinkettrader;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by anju on 03/11/15.
 */
public class EditProfileController {
    private EditProfileActivity activity;
    private UserProfile userProfile;

    public EditProfileController(EditProfileActivity activity, UserProfile profile) {
        this.activity = activity;
        this.userProfile = profile;
    }

    public UserProfile getUserProfile() {
        return userProfile;
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
    public void populateEditFieldsWithExistingValues(){
        EditText name = (EditText) activity.findViewById(R.id.edit_name);
        EditText address = (EditText) activity.findViewById(R.id.edit_address);
        EditText city = (EditText) activity.findViewById(R.id.edit_city);
        EditText postalCode = (EditText) activity.findViewById(R.id.edit_postal_code);
        EditText phoneNum = (EditText) activity.findViewById(R.id.edit_phone_number);

        name.setText(this.userProfile.getName());
        address.setText(this.userProfile.getContactInfo().getAddress());
        city.setText(this.userProfile.getCity());
        postalCode.setText(this.userProfile.getContactInfo().getPostalCode());
        phoneNum.setText(this.userProfile.getContactInfo().getPhoneNumber());

    }

}
