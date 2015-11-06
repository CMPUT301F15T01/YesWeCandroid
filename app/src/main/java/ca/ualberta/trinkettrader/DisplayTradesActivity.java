package ca.ualberta.trinkettrader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

/**
 *
 *
 *
 */
public class DisplayTradesActivity extends AppCompatActivity {
    Button pastTradesButton;
    ListView currentTradesListView;
    private ActiveTradesController controller;
    private ArrayAdapter<Trade> currentTradesAdapter;
    private ArrayList<Trade> userCurrentTradesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_trades);
        currentTradesListView = (ListView)findViewById(R.id.friendsView);
        pastTradesButton = (Button)findViewById(R.id.past_trades_button);
        //userCurrentTradesList = LoggedInUser.getInstance().getTradeManager().getTradeArchiver().getCurrentTrades();
        controller = new ActiveTradesController(this);
        controller.setCurrentTradesListViewItemOnClick();
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentTradesAdapter = new ArrayAdapter<Trade>(this, R.layout.listview_item, userCurrentTradesList);
        currentTradesListView.setAdapter(currentTradesAdapter);
    }

    // PastTrades Button click
    public void openPastTrades(View view){
        Intent intent = new Intent(this, DisplayPastTradesActivity.class);
            startActivity(intent);
        }

    public Button getPastTradesButton(){return pastTradesButton; }

    public ListView getCurrentTradesListView() {
        return currentTradesListView;
    }

}
