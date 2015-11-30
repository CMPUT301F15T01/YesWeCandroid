package ca.ualberta.trinkettrader.Trades;

import android.content.Intent;
import android.net.Uri;

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

    /**
     * onClick method for when the "Accept" button is clicked on a trade.  This causes the proposed trade to be
     * accepted.  The trade's status is changed from "Pending" to "Accepted", and both parties in the
     * trade are emailed about the trade being accepted.
     */
    public void acceptTradeButtonOnClick() {
        Trade clickedTrade = ApplicationState.getInstance().getClickedTrade();
        clickedTrade.setStatus("Accepted");
        // TODO push trade back to server ... maybe re-propose the trade


        String senderUsername = clickedTrade.getReceiver().getUsername();
        String receiverUsername = clickedTrade.getSender().getUsername();

        // MKJParekh; http://stackoverflow.com/questions/9097080/intent-extra-email-not-populating-the-to-field; 2015-11-29
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

    /**
     * Method for updating the text in the TradeReceivedActivity that displays the username (email
     * addresses) of the friend you are trading with and the status of the trade.
     */
    public void updateTextViews() {
        //TODO add friend that you will be trading with
        clickedTrade.getReceiver().getUsername();
        activity.getFriendInTradeTextView().setText(clickedTrade.getSender().getUsername());

        activity.getStatusOfTradeTextView().setText(clickedTrade.getStatus());
    }

    /**
     * onClick method for when the "Decline" button is clicked on a trade.  This causes the proposed trade to be
     * declined.  The trade's status is changed from "Pending" to "Declined", but the user is directed to
     * the CounterTradeActivity to propose a counter trade if they so choose.
     */
    public void declineTradeButtonOnClick() {
        Trade clickedTrade = ApplicationState.getInstance().getClickedTrade();
        clickedTrade.setStatus("Declined");
        LoggedInUser.getInstance().getTradeManager().getTradeArchiver().getPastTrades().add(clickedTrade);
        LoggedInUser.getInstance().getTradeManager().getTradeArchiver().getCurrentTrades().remove(clickedTrade);
        Intent intent = new Intent(activity, CounterTradeActivity.class);
        activity.startActivity(intent);
    }

    /**
     * onClick method for when the "Completed" radio button is clicked on a trade.  This causes the proposed trade to be
     * marked as completed.  The trade's status is changed from "Accepted" or "Declined" to "Completed".  This
     * denotes that the trade is closed.  In the case that the trade had been accepted, then this indicates
     * that the trade occurred, and both party's items have been returned.
     */
    public void tradeCompletedRadioButtonOnClick() {
        Trade clickedTrade = ApplicationState.getInstance().getClickedTrade();
        clickedTrade.setStatus("Completed");
        LoggedInUser.getInstance().getTradeManager().getTradeArchiver().getPastTrades().add(clickedTrade);
        LoggedInUser.getInstance().getTradeManager().getTradeArchiver().getCurrentTrades().remove(clickedTrade);
        Intent intent = new Intent(activity, TradesActivity.class);
        activity.startActivity(intent);
    }
}
