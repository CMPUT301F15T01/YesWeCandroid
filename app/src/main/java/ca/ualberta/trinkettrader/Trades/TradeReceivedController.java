package ca.ualberta.trinkettrader.Trades;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.User.LoggedInUser;

/**
 * Created by Me on 2015-11-28.
 */
public class TradeReceivedController {
    private TradeReceivedActivity activity;
    private Trade clickedTrade = ApplicationState.getInstance().getClickedTrade();

    public TradeReceivedController(TradeReceivedActivity activity) {
        this.activity = activity;
    }

    public void acceptTradeButtonOnClick(){
        Trade clickedTrade = ApplicationState.getInstance().getClickedTrade();
        clickedTrade.setStatus("Accepted");
        // TODO push trade back to server ... maybe re-propose the trade


        String senderUsername = clickedTrade.getReceiver().getUsername();
        String receiverUsername = clickedTrade.getSender().getUsername();
        String emailList[] = { senderUsername,receiverUsername };

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "New Trade!!");
        emailIntent.putExtra(Intent.EXTRA_TEXT, clickedTrade.toEmailString());
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{senderUsername, receiverUsername});
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(emailIntent);


        Intent intent = new Intent(activity, TradesActivity.class);
        activity.startActivity(intent);
    }

    public void updateTextViews() {
        //TODO add friend that you will be trading with
        clickedTrade.getReceiver().getUsername();
        activity.getFriendInTradeTextView().setText(clickedTrade.getSender().getUsername());

        activity.getStatusOfTradeTextView().setText(clickedTrade.getStatus());
    }

    public void declineTradeButtonOnClick() {
        Trade clickedTrade = ApplicationState.getInstance().getClickedTrade();
        clickedTrade.setStatus("Declined");
        LoggedInUser.getInstance().getTradeManager().getTradeArchiver().getPastTrades().add(clickedTrade);
        LoggedInUser.getInstance().getTradeManager().getTradeArchiver().getCurrentTrades().remove(clickedTrade);
        Intent intent = new Intent(activity, CounterTradeActivity.class);
        activity.startActivity(intent);
    }

    public void tradeCompletedRadioButtonOnClick() {
        Trade clickedTrade = ApplicationState.getInstance().getClickedTrade();
        clickedTrade.setStatus("Completed");
        LoggedInUser.getInstance().getTradeManager().getTradeArchiver().getPastTrades().add(clickedTrade);
        LoggedInUser.getInstance().getTradeManager().getTradeArchiver().getCurrentTrades().remove(clickedTrade);
        Intent intent = new Intent(activity, TradesActivity.class);
        activity.startActivity(intent);
    }
}
