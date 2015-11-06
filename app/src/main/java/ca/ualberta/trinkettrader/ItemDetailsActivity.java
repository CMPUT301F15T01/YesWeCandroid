package ca.ualberta.trinkettrader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class ItemDetailsActivity extends AppCompatActivity {

    private Button deleteButton;
    private Button editButton;
    private ArrayList<ImageView> imageViews;
    private Trinket item;
    private ItemDetailsController controller;
    private ItemDetailsActivity activity = this;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        this.item = ApplicationState.getInstance().getClickedTrinket();

        this.deleteButton = (Button) findViewById(R.id.deleteItemButton);
        this.editButton = (Button) findViewById(R.id.edit_button);
        this.controller = new ItemDetailsController(this);

        imageViews = new ArrayList<>();
        imageViews.add((ImageView) findViewById(R.id.imageView));
        ArrayList<Picture> pictures = item.getPictures();
        if (!pictures.isEmpty()) {
            imageViews.get(0).setImageBitmap(pictures.get(0).getBitmap());
        }

        this.dialog = setupAlertDialog();
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getEditButton() {
        return editButton;
    }

    public ArrayList<ImageView> getImageViews() {
        return this.imageViews;
    }

    public Trinket getItem() {
        return item;
    }

    public void deleteClick(View view) {
        dialog.show();
    }

    public AlertDialog getDialog() {
        return dialog;
    }

    private AlertDialog setupAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete item?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        controller.onDeleteClick(item);
                        NavUtils.navigateUpFromSameTask(activity);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

    public void editClick(View view) {
        controller.onEditClick();
    }
}
