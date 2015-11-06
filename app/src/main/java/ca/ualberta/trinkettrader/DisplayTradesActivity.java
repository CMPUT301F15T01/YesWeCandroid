package ca.ualberta.trinkettrader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

/**
 *
 *
 *
 */
public class DisplayTradesActivity extends AppCompatActivity {
    Button pastTradesButton;
    ListView currentTradesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_trades);
        currentTradesListView = (ListView)findViewById(R.id.friendsView);
        pastTradesButton = (Button)findViewById(R.id.past_trades_button);
        // equivalent of user friend's list
        // controller
        // set controller
    }

    // PastTrades Button click
    public void openPastTrades(View view){
        Intent intent = new Intent(this, DisplayPastTradesActivity.class);
        startActivity(intent);
    }

    public Button getPastTradesButton(){return pastTradesButton; }
}
