package ca.ualberta.trinkettrader.Trades;

import android.content.Intent;

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
