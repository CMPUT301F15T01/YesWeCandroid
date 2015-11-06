package ca.ualberta.trinkettrader;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by anju on 03/11/15.
 */
public class EditProfileController {

    private EditProfileActivity activity;
    public EditProfileController(EditProfileActivity activity) {
        this.activity = activity;
    }

    public View.OnClickListener getSaveButtonListener() {
        return saveButtonListener;
    }

    private View.OnClickListener saveButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO: save the changed data remotely - Part 5

            //NOW: Controller takes data from activity, updates UserProfile in LoggedInUser 
            //UserProfile (observable) updates its observer (DisplayProfileActivity) 

            EditText name = (EditText) activity.findViewById(R.id.edit_name);
            EditText address = (EditText) activity.findViewById(R.id.edit_address);
            EditText city = (EditText) activity.findViewById(R.id.edit_city);
            EditText postalCode = (EditText) activity.findViewById(R.id.edit_postal_code);
            EditText phoneNum = (EditText) activity.findViewById(R.id.edit_phone_number);
            CheckBox photoDownload = (CheckBox) activity.findViewById(R.id.photo_download_checkbox);

            String _name = name.getText().toString();
            String _address = address.getText().toString();
            String _city = city.getText().toString();
            String _postalCode = postalCode.getText().toString();
            String _phoneNum = phoneNum.getText().toString();
            Boolean photoDownloadEnabled = photoDownload.isChecked();

            //Set the LoggedInUser with new data
            activity.getUserProfile().setName(_name);
            activity.getUserProfile().getContactInfo().setAddress(_address);
            activity.getUserProfile().setCity(_city);
            activity.getUserProfile().setPostalCode(_postalCode);
            activity.getUserProfile().getContactInfo().setPhoneNumber(_phoneNum);
            activity.getUserProfile().setArePhotosDownloadable(photoDownloadEnabled);

            LoggedInUser.getInstance().saveInFile(activity.getBaseContext());
            //exit the activity
            activity.finish();

        }
    };
    
    


}
