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

    public void onFriendsClick() {
        Intent intent = new Intent(this.activity, DisplayFriendsActivity.class);
        activity.startActivity(intent);
    }

    public void onTradesClick() {
        Intent intent = new Intent(this.activity, DisplayTradesActivity.class);
        activity.startActivity(intent);
    }

    public void onProfileClick() {
        Intent intent = new Intent(this.activity, DisplayUserProfileActivity.class);
        activity.startActivity(intent);
    }
}
