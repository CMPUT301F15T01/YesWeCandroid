package ca.ualberta.trinkettrader;

/**
 * Created by dashley on 2015-10-21.
 */
public class Trade {

    private Inventory offeredItems;
    private Inventory requestedItems;
    private String status;
    private User receiver;
    private User sender;

    public Trade(Inventory offeredItems, User receiver, Inventory requestedItems, User sender) {
        this.offeredItems = offeredItems;
        this.receiver = receiver;
        this.requestedItems = requestedItems;
        this.sender = sender;
        this.status = "pending";
    }

    public Inventory getOfferedItems() {
        return offeredItems;
    }

    public User getReceiver() {
        return receiver;
    }

    public Inventory getRequestedItems() {
        return requestedItems;
    }

    public User getSender() {
        return sender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
