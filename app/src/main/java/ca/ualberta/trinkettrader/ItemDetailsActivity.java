package ca.ualberta.trinkettrader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

public class ItemDetailsActivity extends AppCompatActivity {

    private Button deleteButton;
    private Button editButton;
    private ImageView imageView;
    private Trinket item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        this.item = ApplicationState.getInstance().getClickedTrinket();

        imageView = (ImageView) findViewById(R.id.imageView);
        if (item.getPicture() != null) {
            imageView.setImageBitmap(item.getPicture().getBitmap());
        }
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getEditButton() {
        return editButton;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Trinket getItem() {
        return item;
    }
}
