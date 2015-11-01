package ca.ualberta.trinkettrader;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Andrea McIntosh on 01/11/2015.
 */
public class HomePageController {

    Activity activity;

    public HomePageController(Activity activity) {
        this.activity = activity;
    }

    public void onInventoryClick() {
        Intent intent = new Intent(this.activity, DisplayInventoryActivity.class);
        activity.startActivity(intent);
    }
}
