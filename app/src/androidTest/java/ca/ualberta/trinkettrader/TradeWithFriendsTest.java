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
        Trade trade = new Trade();
        // Send a trade to yourself as a test
        user.sendTradeProposal(user, trade);
        // Test if this trade has triggered a notification
        assertTrue(user.hasNotification());
    }

    // Test accepting trade
    public void testAcceptTrade() {
        User user = new User();
        Trade trade = new Trade();
        // Send a trade to yourself as a test
        user.sendTradeProposal(user, trade);
        // Test if this trade has triggered a notification
        assertTrue(user.hasNotification());
        user.acceptTrade(trade).setAcceptDetails("Test accept message");
        assertTrue(trade.isClosed());
        assertTrue(user.getPastTrade(trade).wasAccepted());
    }

    // Test rejecting a trade without sending a counter offer
    public void testDeclineTradeNoCounter() {
        User user = new User();
        Trade trade = new Trade();
        // Send a trade to yourself as a test
        user.sendTradeProposal(user, trade);
        // Test if this trade has triggered a notification
        assertTrue(user.hasNotification());
        user.declineTrade(trade);
        // Send null instead of a counter trade
        trade.sendCounterTrade(null);
        assertTrue(trade.isClosed());
        assertTrue(user.getPastTrade(trade).wasRejected());
    }

    // Test rejecting a trade with sending a counter offer
    public void testDeclineTradeWithCounter() {
        User user = new User();
        Trade trade = new Trade();
        // Send a trade to yourself as a test
        user.sendTradeProposal(user, trade);
        // Test if this trade has triggered a notification
        assertTrue(user.hasNotification());
        user.declineTrade(trade);
        // Send a counter trade
        Trade newTrade = new Trade();
        trade.sendCounterTrade(newTrade);
        assertFalse(trade.isClosed());

    }

    // Test that friend has the item that user is proposing in their offered trade
    public void testFriendHasItem() {
        User user = new User();
        Trade trade = new Trade();
        Item item = new Item();
        Friend friend = new Friend();
        assertTrue(friend.inventory.hasItem(item));
    }

    //check that the proposed trade shows up in the users current trades
    public void testProposedTrade() {
        User user = new User();
        Trade trade = new Trade();
        assertFalse(user.currentTrades(trade));
        user.sendTradeProposal(user, trade);
        assertTrue(user.currentTrades.hasTrade(trade));
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
        Trade trade = new Trade();
        user.sendTradeProposal(user, trade);
        assertTrue(user.currentTrades.hasTrade(trade));
        user.deleteTradeProposal(user, trade);
        assertFalse(user.currentTrades.hasTrade(trade));
    }

    // Test that user has current trades they are involved in
    public void testCurrentTrades() {
        User user = new User();
        Trade trade = new Trade();
        Trade trade1 = new Trade();
        user.sendTradeProposal(user, trade);
        user.sendTradeProposal(user, trade);
        assertTrue(user.currentTrades.hasTrade(trade));
        assertTrue(user.currentTrades.hasTrade(trade1));
    }
    // Test that user can browse past trades that they were involved in
    public void testPastTrades() {
        User user = new User();
        Trade trade = new Trade();
        Trade trade1 = new Trade();
        user.sendTradeProposal(user, trade);
        user.sendTradeProposal(user, trade);
        assertTrue(user.currentTrades.hasTrade(trade));
        assertTrue(user.currentTrades.hasTrade(trade1));
        user.deleteTradeProposal(user, trade);
        assertFalse(user.currentTrades.hasTrade(trade));
        assertTrue(user.pastTrades.hasTrade(trade));
        assertFalse(user.pastTrades.hasTrade(trade1));
    }
}
