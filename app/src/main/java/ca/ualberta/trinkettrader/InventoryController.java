package ca.ualberta.trinkettrader;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Andrea McIntosh on 01/11/2015.
 */
public class InventoryController {

    private Activity activity;

    public InventoryController(Activity activity) {
        this.activity = activity;
    }

    public void onAddItemClick() {
        Intent intent = new Intent(this.activity, AddOrEditItemActivity.class);
        intent.putExtra("activityName", "add");
        activity.startActivity(intent);
    }
}
