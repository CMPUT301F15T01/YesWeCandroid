package ca.ualberta.trinkettrader;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;

public class HomePageActivity extends ActionBarActivity {

    private Button inventoryButton;
    private Button tradeButton;
    private Button friendsButton;
    private Button profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    public Button getFriendsButton() {
        return friendsButton;
    }

    public Button getInventoryButton() {
        return inventoryButton;
    }

    public Button getProfileButton() {
        return profileButton;
    }

    public Button getTradeButton() {
        return tradeButton;
    }
}
