// Copyright 2015 Andrea McIntosh, Dylan Ashley, Anju Eappen, Jenna Hatchard, Kirsten Svidal, Raghav Vamaraju
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package ca.ualberta.trinkettrader;

import android.test.ActivityInstrumentationTestCase2;

public class TradeWithFriendsTest extends ActivityInstrumentationTestCase2 {

    public TradeWithFriendsTest() {
        super(MainActivity.class);
    }

    // Test method to see if user has a notification
    public void testHasNotification() {
        User user = new User();
        Trade trade = new Trade(user.getInventory(), user.getTradeManager(), user.getInventory(), user.getTradeManager());
        // Send a trade to yourself as a test
        user.getTradeManager().proposeTrade(trade);
        // Test if this trade has triggered a notification
        assertTrue(user.getNotificationManager().hasNotification());
    }

    // Test accepting trade
    public void testAcceptTrade() {
        User user = new User();
        Trade trade = new Trade(user.getInventory(), user.getTradeManager(), user.getInventory(), user.getTradeManager());
        // Send a trade to yourself as a test
        user.getTradeManager().proposeTrade(trade);
        // Test if this trade has triggered a notification
        assertTrue(user.getNotificationManager().hasNotification());
        user.getTradeManager().acceptTrade(trade, "Test accept message");
        assertTrue(trade.getStatus().equals("accepted"));  // TODO need to clarify what status names will be
        assertTrue(user.getTradeManager().getTradeArchiver().getPastTrade(trade).getStatus().equals("Accepted"));
    }

    // Test rejecting a trade without sending a counter offer
    public void testDeclineTradeNoCounter() {
        User user = new User();
        Trade trade = new Trade(user.getInventory(), user.getTradeManager(), user.getInventory(), user.getTradeManager());
        // Send a trade to yourself as a test
        user.getTradeManager().proposeTrade(trade);
        // Test if this trade has triggered a notification
        assertTrue(user.getNotificationManager().hasNotification());
        user.getTradeManager().declineTrade(trade);
        // Send null instead of a counter trade
        trade.sendCounterTrade(null);
        assertTrue(trade.getStatus().equals("declined"));
        // don't think we need the line below?
        assertTrue(user.getTradeManager().getTradeArchiver().getPastTrade(trade).getStatus().equals("rejected"));
    }

    // Test rejecting a trade with sending a counter offer
    public void testDeclineTradeWithCounter() {
        User user = new User();
        // Send a trade to yourself as a test
        Trade trade = new Trade(user.getInventory(), user.getTradeManager(), user.getInventory(), user.getTradeManager());
        user.getTradeManager().proposeTrade(trade);
        // Test if this trade has triggered a notification
        assertTrue(user.getNotificationManager().hasNotification());
        user.getTradeManager().declineTrade(trade);
        // Send a counter trade
        Trade counterTrade = new Trade(user.getInventory(), user.getTradeManager(), user.getInventory(), user.getTradeManager());
        assertFalse(trade.getStatus().equals("closed"));

    }

    // Test that friend has the item that user is proposing in their offered trade
    public void testFriendHasItem() {
        User user = new User();
        User user1 = new User();
        // right now, line below isn't used
        Trade trade = new Trade(user.getInventory(), user.getTradeManager(), user1.getInventory(), user1.getTradeManager());
        Trinket item = new Trinket();
        item.setName("necklace");
        Friend friend = new Friend(user1);
        assertTrue(friend.inventory.hasItem(item));
    }

    //check that the proposed trade shows up in the users current trades
    public void testProposedTrade() {
        User user = new User();
        Trade trade = new Trade(user.getInventory(), user.getTradeManager(), user.getInventory(), user.getTradeManager());
        assertFalse(user.getTradeManager().getTradeArchiver().hasCurrentTrade(trade));
        user.getTradeManager().proposeTrade(trade);
        assertTrue(user.getTradeManager().getTradeArchiver().hasCurrentTrade(trade));
    }

    //check that user can edit a current trade
    public void testEditTrade() {
        User user = new User();
        Trade trade = new Trade();
        trade.setNumberOfItems(1);
        user.sendTradeProposal(user, trade);
        user.trade.setNumberOfItems(2);
        assertEquals(user.trade.getNumberOfItems() == 2);
    }

    //check that user can delete a proposed trade
    public void testDeleteTrade() {
        User user = new User();
        Trade trade = new Trade(user.getInventory(), user.getTradeManager(), user.getInventory(), user.getTradeManager());
        user.getTradeManager().proposeTrade(trade);
        assertTrue(user.getTradeManager().getTradeArchiver().hasCurrentTrade(trade));
        user.getTradeManager().deleteTrade(trade);
        assertFalse(user.getTradeManager().getTradeArchiver().hasCurrentTrade(trade));
    }

    // Test that user has current trades they are involved in
    public void testCurrentTrades() {
        User user = new User();
        Trade trade = new Trade(user.getInventory(), user.getTradeManager(), user.getInventory(), user.getTradeManager());
        Trade trade1 = new Trade(user.getInventory(), user.getTradeManager(), user.getInventory(), user.getTradeManager());
        user.getTradeManager().proposeTrade(trade);
        user.getTradeManager().proposeTrade(trade1);
        assertTrue(user.getTradeManager().getTradeArchiver().hasCurrentTrade(trade));
        assertTrue(user.getTradeManager().getTradeArchiver().hasCurrentTrade(trade1));
    }
    // Test that user can browse past trades that they were involved in
    public void testPastTrades() {
        User user = new User();
        Trade trade = new Trade(user.getInventory(), user.getTradeManager(), user.getInventory(), user.getTradeManager());
        Trade trade1 = new Trade(user.getInventory(), user.getTradeManager(), user.getInventory(), user.getTradeManager());
        user.getTradeManager().proposeTrade(trade);
        user.getTradeManager().proposeTrade(trade1);

        assertTrue(user.getTradeManager().getTradeArchiver().hasCurrentTrade(trade));
        assertTrue(user.getTradeManager().getTradeArchiver().hasCurrentTrade(trade1));

        user.getTradeManager().deleteTrade(trade);
        assertFalse(user.getTradeManager().getTradeArchiver().hasCurrentTrade(trade));
        assertTrue(user.getTradeManager().getTradeArchiver().hasPastTrade(trade));
        assertFalse(user.getTradeManager().getTradeArchiver().hasPastTrade(trade1));
    }
}
