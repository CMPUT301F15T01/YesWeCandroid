package ca.ualberta.trinkettrader.Trades;

import ca.ualberta.trinkettrader.ApplicationState;

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

}


