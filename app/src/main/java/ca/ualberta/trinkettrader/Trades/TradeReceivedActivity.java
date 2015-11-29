package ca.ualberta.trinkettrader.Trades;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.Inventory.Trinket.Trinket;
import ca.ualberta.trinkettrader.R;

public class TradeReceivedActivity extends Activity {
    private TextView friendInTradeTextView;
    private TextView statusOfTradeTextView;
    private ListView offeredItemInTradeListView;
    private ListView requestedItemInTradeListView;
    private Trade trade;
    private ArrayAdapter<Trinket> friendTrinketAdapter;
    private ArrayAdapter<Trinket> yourTrinketAdapter;

    private Button acceptTradeButton;
    private Button declineTradeButton;
    private TradeReceivedController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_received);
        acceptTradeButton = (Button) findViewById(R.id.acceptTradeButton);
        declineTradeButton = (Button) findViewById(R.id.declineTradeButton);
        friendInTradeTextView = (TextView) findViewById(R.id.tradeReceivedWithFriendName);
        statusOfTradeTextView = (TextView) findViewById(R.id.tradeReceivedStatus);
        //TODO if stuff breaks comment this

        offeredItemInTradeListView = (ListView) findViewById(R.id.offeredReceivedItems);
        requestedItemInTradeListView = (ListView) findViewById(R.id.requestedReceivedItemsy);

        trade = ApplicationState.getInstance().getClickedTrade();
        controller = new TradeReceivedController(this);
        controller.updateTextViews();
        updateTradeDetailsListView();


    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTradeDetailsListView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateTradeDetailsListView();
    }


    public void updateTradeDetailsListView() {
        yourTrinketAdapter = new ArrayAdapter<Trinket>(this, android.R.layout.simple_list_item_1, trade.getOfferedTrinkets());
        offeredItemInTradeListView.setAdapter(yourTrinketAdapter);
        friendTrinketAdapter = new ArrayAdapter<Trinket>(this, android.R.layout.simple_list_item_1, trade.getRequestedTrinkets());
        requestedItemInTradeListView.setAdapter(friendTrinketAdapter);
    }




    public TextView getFriendInTradeTextView() {
        return friendInTradeTextView;
    }

    public TextView getStatusOfTradeTextView() {
        return statusOfTradeTextView;
    }



    public void acceptTradeButtonOnClick(View v) {
        controller.acceptTradeButtonOnClick();
    }

    public void declineTradeButtonOnClick(View v) {
        controller.declineTradeButtonOnClick();
    }

    public Button getDeclineTradeButton() {
        return declineTradeButton;
    }

    public Button getAcceptTradeButton() {
        return acceptTradeButton;
    }

}
