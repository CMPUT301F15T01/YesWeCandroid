package ca.ualberta.trinkettrader;

import android.content.Intent;
import android.support.v4.app.NavUtils;

/**
 * Created by anju on 03/11/15.
 */
public class ItemDetailsController {
    ItemDetailsActivity activity;

    public ItemDetailsController(ItemDetailsActivity activity) {
        this.activity = activity;
    }

    public void onDeleteClick(Trinket trinket) {
        LoggedInUser.getInstance().getInventory().remove(trinket);
    }

    public void onEditClick() {
        Intent intent = new Intent(this.activity, AddOrEditItemActivity.class);
        activity.startActivity(intent);
    }
}
