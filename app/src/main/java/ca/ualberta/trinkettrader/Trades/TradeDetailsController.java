package ca.ualberta.trinkettrader.Trades;

import android.content.Intent;

import ca.ualberta.trinkettrader.ApplicationState;
import ca.ualberta.trinkettrader.User.LoggedInUser;

/**
 * Created by Me on 2015-11-27.
 */
public class TradeDetailsController {
    private TradeDetailsActivity activity;
    private Trade clickedTrade = ApplicationState.getInstance().getClickedTrade();

    public TradeDetailsController(TradeDetailsActivity activity) {
        this.activity = activity;
    }

    public void updateTextViews() {
        //TODO add friend that you will be trading with
        activity.getFriendInTradeTextView().setText(clickedTrade.getReceiver().getUsername());

        activity.getStatusOfTradeTextView().setText(clickedTrade.getStatus());
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


