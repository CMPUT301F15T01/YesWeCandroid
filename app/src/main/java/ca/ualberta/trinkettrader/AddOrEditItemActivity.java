package ca.ualberta.trinkettrader;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddOrEditItemActivity extends ActionBarActivity {

    private EditText itemName;
    private Spinner itemCategory;
    private Spinner itemQuality;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_item);
    }

    public Spinner getItemCategory() {
        return itemCategory;
    }

    public EditText getItemName() {
        return itemName;
    }

    public Spinner getItemQuality() {
        return itemQuality;
    }

    public void setItemCategory(Spinner itemCategory) {
        this.itemCategory = itemCategory;
    }

    public void setItemName(EditText itemName) {
        this.itemName = itemName;
    }

    public void setItemQuality(Spinner quality) {
        this.itemQuality = quality;
    }

    public Button getSaveButton() {
        return saveButton;
    }
}
