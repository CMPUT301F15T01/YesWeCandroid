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

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;

import ca.ualberta.trinkettrader.Friends.Friend;
import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.Inventory.Trinket.Trinket;
import ca.ualberta.trinkettrader.Trades.PastTradesActivity;
import ca.ualberta.trinkettrader.Trades.Trade;
import ca.ualberta.trinkettrader.Trades.TradeDetailsActivity;
import ca.ualberta.trinkettrader.Trades.TradeReceivedActivity;
import ca.ualberta.trinkettrader.Trades.TradesActivity;
import ca.ualberta.trinkettrader.User.LoggedInUser;
import ca.ualberta.trinkettrader.User.Profile.UserProfileActivity;
import ca.ualberta.trinkettrader.User.User;

public class TradeWithFriendsTest extends ActivityInstrumentationTestCase2 {
    AutoCompleteTextView loginEmailTextView;
    Button loginButton;


    public TradeWithFriendsTest() {
        super(LoginActivity.class);
    }
    // TODO I think that all of the Users invloved in trades have to be made friends.
    // TODO ask about how to incorporate FriendsListController


    /*
    * Use Case: Accept a Proposed Trade
    *
    * MUST BE LOGGED INTO EMAIL FOR TEST TO PASS
    * */
    public void testAcceptTrade() {

        // Get the current activity
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** HomePageActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor homePageActivityMonitor = getInstrumentation().addMonitor(HomePageActivity.class.getName(), null, false);

        // actions which lead to next activity
        //final String test_email = loginActivity.getResources().getString(R.string.test_email);
        final String test_email = "trex@iceage.ca";
        final AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                emailTextView.setText(test_email);
            }
        });
        final Button homePageButton = loginActivity.getLoginButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                homePageButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        /****Hardcode in trades to click****/
        LoggedInUser currentUser = LoggedInUser.getInstance();
        currentUser.getTradeManager().setUsername("trex@iceage.ca");

        Friend friend = new Friend("burger@ihop.ca");
        friend.getActualFriend().getTradeManager().setUsername("burger@ihop.ca");
        // create inventories of trinkets to trade
        Inventory borrowerInventory = new Inventory();
        Inventory ownerInventory = new Inventory();
        Inventory secondInventory = new Inventory();

        Trinket necklace = new Trinket();
        necklace.setName("Amulet of Fire");
        Trinket ring = new Trinket();
        ring.setName("Ring of Swirling Mist");
        borrowerInventory.add(necklace);
        ownerInventory.add(ring);

        // hardcoded trades - with alternating borrowers and owners
        final Trade trade = new Trade(borrowerInventory, currentUser.getTradeManager(), ownerInventory, friend.getActualFriend().getTradeManager());
        //currentUser.getTradeManager().proposeTrade(trade); //TODO not implemented. just hardcoding into user's currentTrades ArrayList
        // trade offered to user from friend
        currentUser.getTradeManager().getTradeArchiver().addTrade(trade);
        trade.setStatus("Pending Incoming");
        assertTrue(currentUser.getTradeManager().getTradeArchiver().hasCurrentTrade(trade));

        // make one more trade
        Trinket bracelet = new Trinket();
        bracelet.setName("Bronze bracer");
        secondInventory.add(bracelet);

        // swapped borrower and receiver
        // trade offered to friend from user
        final Trade trade1 = new Trade(borrowerInventory, friend.getActualFriend().getTradeManager(), secondInventory, currentUser.getTradeManager());
        //currentUser.getTradeManager().proposeTrade(trade1); //TODO not implemented. just hardcoding into user's currentTrades ArrayList
        currentUser.getTradeManager().getTradeArchiver().addTrade(trade1);
        assertTrue(currentUser.getTradeManager().getTradeArchiver().hasCurrentTrade(trade1));


        // Validate that ReceiverActivity is started
        HomePageActivity homePageActivity = (HomePageActivity) homePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", homePageActivity);
        assertEquals("Monitor for ReceiverActivity has not been called", 1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type", HomePageActivity.class, homePageActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(homePageActivityMonitor);

        /****UserProfileActivity****/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor profilePageActivityMonitor = getInstrumentation().addMonitor(UserProfileActivity.class.getName(), null, false);

        // actions which lead to next activity
        final Button homePageProfileButton = homePageActivity.getProfileButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                homePageProfileButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        UserProfileActivity userProfileActivity = (UserProfileActivity) profilePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", userProfileActivity);
        assertEquals("Monitor for ReceiverActivity has not been called", 1, profilePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type", UserProfileActivity.class, userProfileActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(profilePageActivityMonitor);

        /****ActiveTradesActivity****/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor currentTradesActivityMonitor = getInstrumentation().addMonitor(TradesActivity.class.getName(), null, false);

        // actions which lead to next activity
        final Button tradesProfilePageButton = userProfileActivity.getTradesButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                tradesProfilePageButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        TradesActivity currentTradesActivity = (TradesActivity) currentTradesActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", currentTradesActivity);
        assertEquals("Monitor for ReceiverActivity has not been called", 1, currentTradesActivityMonitor.getHits());
        assertEquals("Activity is of wrong type", TradesActivity.class, currentTradesActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(currentTradesActivityMonitor);

        /****TradeDetailsActivity****/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor tradeReceivedActivityMonitor = getInstrumentation().addMonitor(TradeReceivedActivity.class.getName(), null, false);

        // trade at 1st index should be the first trade that was added. let's click it
        // actions which lead to next activity
        final ListView currentTradesList = currentTradesActivity.getCurrentTradesListView();
        currentTradesActivity.runOnUiThread(new Runnable() {
            public void run() {
                View tradeBox1 = currentTradesList.getChildAt(1);
                currentTradesList.performItemClick(tradeBox1, 1, tradeBox1.getId()); // test failing here. null pointer exception. trades not in list
                // check that we clicked the correct trade
                assertTrue((trade.toString()).equals(ApplicationState.getInstance().getClickedTrade().toString()));
            }
        });
        getInstrumentation().waitForIdleSync();

        // regular activity check stuff
        // Validate that ReceiverActivity is started
        TradeReceivedActivity tradeReceivedActivity = (TradeReceivedActivity) tradeReceivedActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", tradeReceivedActivity);
        assertEquals("Monitor for ReceiverActivity has not been called", 1, tradeReceivedActivityMonitor.getHits());
        assertEquals("Activity is of wrong type", TradeReceivedActivity.class, tradeReceivedActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(tradeReceivedActivityMonitor);

        // Click Accept Trade
        // actions which lead to next activity
        final Button acceptTradeButton = tradeReceivedActivity.getAcceptTradeButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                acceptTradeButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // trade should not be in current trades list. trade should be in past trades list
        //assertFalse(currentUser.getTradeManager().getTradeArchiver().hasCurrentTrade(trade));
        //assertTrue(currentUser.getTradeManager().getTradeArchiver().hasPastTrade(trade));

        assertTrue(trade.getStatus().equals("Accepted"));

        // finish activities
        loginActivity.finish();
        homePageActivity.finish();
        userProfileActivity.finish();
        currentTradesActivity.finish();
        tradeReceivedActivity.finish();

        try{
            currentUser.deleteFromNetwork(); // delete logged in user
        }catch(IOException e){

        }

    }
    /*
    * Case: Decline a Proposed Trade
    * Test rejecting a trade without sending a counter offer
    * */
    public void testDeclineTradeNoCounter() {
        // Get the current activity
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** HomePageActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor homePageActivityMonitor = getInstrumentation().addMonitor(HomePageActivity.class.getName(), null, false);

        // actions which lead to next activity
        //final String test_email = loginActivity.getResources().getString(R.string.test_email);
        final String test_email = "trex@iceage.ca";
        final AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                emailTextView.setText(test_email);
            }
        });
        final Button homePageButton = loginActivity.getLoginButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                homePageButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        /****Hardcode in trades to click****/
        LoggedInUser currentUser = LoggedInUser.getInstance();
        currentUser.getTradeManager().setUsername("trex@iceage.ca");

        Friend friend = new Friend("burger@ihop.ca");
        friend.getActualFriend().getTradeManager().setUsername("burger@ihop.ca");
        // create inventories of trinkets to trade
        Inventory borrowerInventory = new Inventory();
        Inventory ownerInventory = new Inventory();
        Inventory secondInventory = new Inventory();

        Trinket necklace = new Trinket();
        necklace.setName("Amulet of Fire");
        Trinket ring = new Trinket();
        ring.setName("Ring of Swirling Mist");
        borrowerInventory.add(necklace);
        ownerInventory.add(ring);

        // hardcoded trades - with alternating borrowers and owners
        final Trade trade = new Trade(borrowerInventory, currentUser.getTradeManager(), ownerInventory, friend.getActualFriend().getTradeManager());
        //currentUser.getTradeManager().proposeTrade(trade); //TODO not implemented. just hardcoding into user's currentTrades ArrayList
        // trade offered to user from friend
        currentUser.getTradeManager().getTradeArchiver().addTrade(trade);
        trade.setStatus("Pending Incoming");
        assertTrue(currentUser.getTradeManager().getTradeArchiver().hasCurrentTrade(trade));

        // make one more trade
        Trinket bracelet = new Trinket();
        bracelet.setName("Bronze bracer");
        secondInventory.add(bracelet);

        // swapped borrower and receiver
        // trade offered to friend from user
        final Trade trade1 = new Trade(borrowerInventory, friend.getActualFriend().getTradeManager(), secondInventory, currentUser.getTradeManager());
        //currentUser.getTradeManager().proposeTrade(trade1); //TODO not implemented. just hardcoding into user's currentTrades ArrayList
        currentUser.getTradeManager().getTradeArchiver().addTrade(trade1);
        assertTrue(currentUser.getTradeManager().getTradeArchiver().hasCurrentTrade(trade1));


        // Validate that ReceiverActivity is started
        HomePageActivity homePageActivity = (HomePageActivity) homePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", homePageActivity);
        assertEquals("Monitor for ReceiverActivity has not been called", 1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type", HomePageActivity.class, homePageActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(homePageActivityMonitor);

        /****UserProfileActivity****/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor profilePageActivityMonitor = getInstrumentation().addMonitor(UserProfileActivity.class.getName(), null, false);

        // actions which lead to next activity
        final Button homePageProfileButton = homePageActivity.getProfileButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                homePageProfileButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        UserProfileActivity userProfileActivity = (UserProfileActivity) profilePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", userProfileActivity);
        assertEquals("Monitor for ReceiverActivity has not been called", 1, profilePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type", UserProfileActivity.class, userProfileActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(profilePageActivityMonitor);

        /****ActiveTradesActivity****/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor currentTradesActivityMonitor = getInstrumentation().addMonitor(TradesActivity.class.getName(), null, false);

        // actions which lead to next activity
        final Button tradesProfilePageButton = userProfileActivity.getTradesButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                tradesProfilePageButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        TradesActivity currentTradesActivity = (TradesActivity) currentTradesActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", currentTradesActivity);
        assertEquals("Monitor for ReceiverActivity has not been called", 1, currentTradesActivityMonitor.getHits());
        assertEquals("Activity is of wrong type", TradesActivity.class, currentTradesActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(currentTradesActivityMonitor);

        /****TradeDetailsActivity****/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor tradeReceivedActivityMonitor = getInstrumentation().addMonitor(TradeReceivedActivity.class.getName(), null, false);

        // trade at 1st index should be the first trade that was added. let's click it
        // actions which lead to next activity
        final ListView currentTradesList = currentTradesActivity.getCurrentTradesListView();
        currentTradesActivity.runOnUiThread(new Runnable() {
            public void run() {
                View tradeBox1 = currentTradesList.getChildAt(1);
                currentTradesList.performItemClick(tradeBox1, 1, tradeBox1.getId()); // test failing here. null pointer exception. trades not in list
                // check that we clicked the correct trade
                assertTrue((trade.toString()).equals(ApplicationState.getInstance().getClickedTrade().toString()));
            }
        });
        getInstrumentation().waitForIdleSync();

        // regular activity check stuff
        // Validate that ReceiverActivity is started
        TradeReceivedActivity tradeReceivedActivity = (TradeReceivedActivity) tradeReceivedActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", tradeReceivedActivity);
        assertEquals("Monitor for ReceiverActivity has not been called", 1, tradeReceivedActivityMonitor.getHits());
        assertEquals("Activity is of wrong type", TradeReceivedActivity.class, tradeReceivedActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(tradeReceivedActivityMonitor);

        // Click Accept Trade
        // actions which lead to next activity
        final Button declineTradeButton = tradeReceivedActivity.getDeclineTradeButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                declineTradeButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // trade should not be in current trades list. trade should be in past trades list
        //assertFalse(currentUser.getTradeManager().getTradeArchiver().hasCurrentTrade(trade));
        //assertTrue(currentUser.getTradeManager().getTradeArchiver().hasPastTrade(trade));

        assertTrue(trade.getStatus().equals("Declined"));

        // finish activities
        loginActivity.finish();
        homePageActivity.finish();
        userProfileActivity.finish();
        currentTradesActivity.finish();
        tradeReceivedActivity.finish();

        try{
            currentUser.deleteFromNetwork(); // delete logged in user
        }catch(IOException e){

        }

    }


    // Test that friend has the item that user is proposing in their offered trade
    // TODO this seems out of place?
    public void testFriendHasItem() {
        User user = LoggedInUser.getInstance();
        User user1 = LoggedInUser.getInstance();
        // right now, line below isn't used
        Trade trade = new Trade(user.getInventory(), user.getTradeManager(), user1.getInventory(), user1.getTradeManager());
        Trinket item = new Trinket();
        item.setName("necklace");
        Friend friend = new Friend();
        friend.setIsTracked(Boolean.TRUE);
        //assertTrue(friend.inventory.hasItem(item)); TODO
        assertNotNull(null);

        /*
        *
        * */
        // Start the UI test from the login page (beginning of the app).
        LoginActivity loginActivity = (LoginActivity) getActivity();

        Instrumentation.ActivityMonitor homePageActivityMonitor = getInstrumentation().addMonitor(HomePageActivity.class.getName(), null, false);

        // On the login page: click the email input box and write an arbitrary email.
        // Test that the text was successfully written.
        loginEmailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                loginEmailTextView.performClick();
                loginEmailTextView.setText("user@gmail.com");
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(loginEmailTextView.getText().toString().equals("user@gmail.com"));

        // Click the login button to proceed to the home page.
        loginButton = loginActivity.getLoginButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                loginButton.performClick();
            }
        });

        // Test that the HomePageActivity started correctly after the clicking the login button.
        //Instrumentation.ActivityMonitor homePageActivityMonitor = getInstrumentation().addMonitor(HomePageActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        HomePageActivity homePageActivity = (HomePageActivity) homePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("HomePageActivity is null", homePageActivity);
        assertEquals("Monitor for HomePageActivity has not been called", 1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected HomePageActivity", HomePageActivity.class, homePageActivity.getClass());
        getInstrumentation().removeMonitor(homePageActivityMonitor);

        // finish activities
        loginActivity.finish();
        homePageActivity.finish();
    }

   /*
   * Use Case: Offer a trade
   * */
    public void testProposeTrade(){
        assertNotNull(null);
    }

    /*
    * Use Case: Edit a proposed trade
    * User edits a trade they proposed.
    * */
    public void testEditProposedTrade(){
        assertNotNull(null);
    }

    /*
    * Use Case: Delete a proposed trade
    * User deletes a trade they proposed.
    * */
    public void deleteProposedTrade(){
        assertNotNull(null);
    }
    // TODO USer Case: Edit a proposed trade
    //check that user can edit a current trade
    public void testEditTrade() {
        User user = LoggedInUser.getInstance();
        Trade trade = new Trade(user.getInventory(), user.getTradeManager(), user.getInventory(), user.getTradeManager());
        trade.setNumberOfTrinkets(1);  // TODO should this instead be a count of how many trinkets included in the trade?
        user.getTradeManager().proposeTrade(trade);
        trade.setNumberOfTrinkets(2);
        assertEquals((Integer) 2, trade.getNumberOfTrinkets());

        /*
        *
        * */
        // Start the UI test from the login page (beginning of the app).
        LoginActivity loginActivity = (LoginActivity) getActivity();

        Instrumentation.ActivityMonitor homePageActivityMonitor = getInstrumentation().addMonitor(HomePageActivity.class.getName(), null, false);

        // On the login page: click the email input box and write an arbitrary email.
        // Test that the text was successfully written.
        loginEmailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                loginEmailTextView.performClick();
                loginEmailTextView.setText("user@gmail.com");
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(loginEmailTextView.getText().toString().equals("user@gmail.com"));

        // Click the login button to proceed to the home page.
        loginButton = loginActivity.getLoginButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                loginButton.performClick();
            }
        });

        // Test that the HomePageActivity started correctly after the clicking the login button.
        //Instrumentation.ActivityMonitor homePageActivityMonitor = getInstrumentation().addMonitor(HomePageActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        HomePageActivity homePageActivity = (HomePageActivity) homePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("HomePageActivity is null", homePageActivity);
        assertEquals("Monitor for HomePageActivity has not been called", 1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected HomePageActivity", HomePageActivity.class, homePageActivity.getClass());
        getInstrumentation().removeMonitor(homePageActivityMonitor);


        // finish activities
        loginActivity.finish();
        homePageActivity.finish();
        assertNotNull(null);
    }

    // TODO User Case: delete a proposed trade
    //check that user can delete a proposed trade
    public void testDeleteTrade() {
        User user = LoggedInUser.getInstance();
        Trade trade = new Trade(user.getInventory(), user.getTradeManager(), user.getInventory(), user.getTradeManager());
        user.getTradeManager().proposeTrade(trade);
        assertTrue(user.getTradeManager().getTradeArchiver().hasCurrentTrade(trade));
        user.getTradeManager().deleteTrade(trade);
        assertFalse(user.getTradeManager().getTradeArchiver().hasCurrentTrade(trade));

        /*
        *
        * */

        // Start the UI test from the login page (beginning of the app).
        LoginActivity loginActivity = (LoginActivity) getActivity();

        Instrumentation.ActivityMonitor homePageActivityMonitor = getInstrumentation().addMonitor(HomePageActivity.class.getName(), null, false);

        // On the login page: click the email input box and write an arbitrary email.
        // Test that the text was successfully written.
        loginEmailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                loginEmailTextView.performClick();
                loginEmailTextView.setText("user@gmail.com");
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue(loginEmailTextView.getText().toString().equals("user@gmail.com"));

        // Click the login button to proceed to the home page.
        loginButton = loginActivity.getLoginButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                loginButton.performClick();
            }
        });

        // Test that the HomePageActivity started correctly after the clicking the login button.
        //Instrumentation.ActivityMonitor homePageActivityMonitor = getInstrumentation().addMonitor(HomePageActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        HomePageActivity homePageActivity = (HomePageActivity) homePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("HomePageActivity is null", homePageActivity);
        assertEquals("Monitor for HomePageActivity has not been called", 1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected HomePageActivity", HomePageActivity.class, homePageActivity.getClass());
        getInstrumentation().removeMonitor(homePageActivityMonitor);

        // finish activities
        loginActivity.finish();
        homePageActivity.finish();
    }


    // TODO is there a way to view the current trades lists of the other person involved in the trade. not currently
    /*
    * Please note: This test only is concerned with a trade being in the currentTrades list, NOT
    * viewing the TradeDetailsActivity.  TradeDetailsActivity will not be implemented for this prototype.
    * TradeDetailsActivity just included for completeness.
    *
    *  Use Case - Owner/Borrower can browse all current trades involving them
    *
    *  Use Case: Owner/Borrower can browse all current trades involving them as either the borrower or owner
    *
    *  Use Case - Be notified of a proposed trade
    /* Tests that user is notified when offered a new trade.
     * Notification will be in the form of the trade being displayed with NEW! in the current trades list */
    public void testViewCurrentTrades() {

        // Get the current activity
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** HomePageActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor homePageActivityMonitor = getInstrumentation().addMonitor(HomePageActivity.class.getName(), null, false);

        // actions which lead to next activity
        //final String test_email = loginActivity.getResources().getString(R.string.test_email);
        final String test_email = "trex@iceage.ca";
        final AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                emailTextView.setText(test_email);
            }
        });
        final Button homePageButton = loginActivity.getLoginButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                homePageButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        /****Hardcode in trades to click****/
        LoggedInUser currentUser = LoggedInUser.getInstance();
        currentUser.getTradeManager().setUsername("trex@iceage.ca");

        Friend friend = new Friend("burger@ihop.ca");
        friend.getActualFriend().getTradeManager().setUsername("burger@ihop.ca");
        // create inventories of trinkets to trade
        Inventory borrowerInventory = new Inventory();
        Inventory ownerInventory = new Inventory();
        Inventory secondInventory = new Inventory();

        Trinket necklace = new Trinket();
        necklace.setName("Amulet of Fire");
        Trinket ring = new Trinket();
        ring.setName("Ring of Swirling Mist");
        borrowerInventory.add(necklace);
        ownerInventory.add(ring);

        // hardcoded trades - with alternating borrowers and owners
        final Trade trade = new Trade(borrowerInventory, currentUser.getTradeManager(), ownerInventory, friend.getActualFriend().getTradeManager());
        //currentUser.getTradeManager().proposeTrade(trade); //TODO not implemented. just hardcoding into user's currentTrades ArrayList
        currentUser.getTradeManager().getTradeArchiver().addTrade(trade);
        assertTrue(currentUser.getTradeManager().getTradeArchiver().hasCurrentTrade(trade));

        // make one more trade
        Trinket bracelet = new Trinket();
        bracelet.setName("Bronze bracer");
        secondInventory.add(bracelet);

        // swapped borrower and receiver
        final Trade trade1 = new Trade(borrowerInventory, friend.getActualFriend().getTradeManager(), secondInventory, currentUser.getTradeManager());
        //currentUser.getTradeManager().proposeTrade(trade1); //TODO not implemented. just hardcoding into user's currentTrades ArrayList
        currentUser.getTradeManager().getTradeArchiver().addTrade(trade1);
        assertTrue(currentUser.getTradeManager().getTradeArchiver().hasCurrentTrade(trade1));


        // Validate that ReceiverActivity is started
        HomePageActivity homePageActivity = (HomePageActivity) homePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", homePageActivity);
        assertEquals("Monitor for ReceiverActivity has not been called", 1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type", HomePageActivity.class, homePageActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(homePageActivityMonitor);

        /****UserProfileActivity****/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor profilePageActivityMonitor = getInstrumentation().addMonitor(UserProfileActivity.class.getName(), null, false);

        // actions which lead to next activity
        final Button homePageProfileButton = homePageActivity.getProfileButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                homePageProfileButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        UserProfileActivity userProfileActivity = (UserProfileActivity) profilePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", userProfileActivity);
        assertEquals("Monitor for ReceiverActivity has not been called", 1, profilePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type", UserProfileActivity.class, userProfileActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(profilePageActivityMonitor);

        /****ActiveTradesActivity****/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor currentTradesActivityMonitor = getInstrumentation().addMonitor(TradesActivity.class.getName(), null, false);

        // actions which lead to next activity
        final Button tradesProfilePageButton = userProfileActivity.getTradesButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                tradesProfilePageButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        TradesActivity currentTradesActivity = (TradesActivity) currentTradesActivityMonitor.waitForActivityWithTimeout(10000);
        assertNotNull("ReceiverActivity is null", currentTradesActivity);
        assertEquals("Monitor for ReceiverActivity has not been called", 1, currentTradesActivityMonitor.getHits());
        assertEquals("Activity is of wrong type", TradesActivity.class, currentTradesActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(currentTradesActivityMonitor);

        /****TradeDetailsActivity****/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor tradeDetailsActivityMonitor = getInstrumentation().addMonitor(TradeDetailsActivity.class.getName(), null, false);

        // trade at 1st index should be the first trade that was added. let's click it
        // trade that user proposed
        // actions which lead to next activity
        final ListView currentTradesList = currentTradesActivity.getCurrentTradesListView();
        currentTradesActivity.runOnUiThread(new Runnable() {
            public void run() {
                View tradeBox1 = currentTradesList.getChildAt(1);
                currentTradesList.performItemClick(tradeBox1, 1, tradeBox1.getId()); // test failing here. null pointer exception. trades not in list
                // check that we clicked the correct trade
                assertTrue((trade.toString()).equals(ApplicationState.getInstance().getClickedTrade().toString()));
            }
        });
        getInstrumentation().waitForIdleSync();

        // regular activity check stuff
        // Validate that ReceiverActivity is started
        TradeDetailsActivity tradeDetailsActivity = (TradeDetailsActivity) tradeDetailsActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", tradeDetailsActivity);
        assertEquals("Monitor for ReceiverActivity has not been called", 1, tradeDetailsActivityMonitor.getHits());
        assertEquals("Activity is of wrong type", TradeDetailsActivity.class, tradeDetailsActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(tradeDetailsActivityMonitor);

        // finish activities
        loginActivity.finish();
        homePageActivity.finish();
        userProfileActivity.finish();
        currentTradesActivity.finish();
        tradeDetailsActivity.finish();

        try{
            currentUser.deleteFromNetwork(); // delete logged in user
        }catch(IOException e){

        }
    }


    // TODO User Case: owner/ borrower can browse all past trade involving them
    // TODO: need to test different statuses? owner/borrower cases?
    /*
    * Please note: This test only is concerned with a trade being in the pastTrades list, NOT
    * viewing the TradeDetailsActivity.  TradeDetailsActivity will not be implemented for this prototype.
    * TradeDetailsActivity just included for completeness.
    *
    *  Use Case: Owner/Borrower can browse all past trades involving them
    *
    *  Use Case: Owner/Borrower can browse all past trades involving them as the borrower or owner
    * */
    public void testViewPastTrades() {

        // Get the current activity
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** HomePageActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor homePageActivityMonitor = getInstrumentation().addMonitor(HomePageActivity.class.getName(), null, false);

        // actions which lead to next activity
        final String test_email = "trex@iceage.ca";
        final AutoCompleteTextView emailTextView = loginActivity.getEmailTextView();
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                emailTextView.setText(test_email);
            }
        });
        final Button homePageButton = loginActivity.getLoginButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                homePageButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        /****Hardcode in trades to click****/
        LoggedInUser currentUser = LoggedInUser.getInstance();
        currentUser.getTradeManager().setUsername("trex@iceage.ca");

        Friend friend = new Friend("burger@ihop.ca");
        friend.getActualFriend().getTradeManager().setUsername("burger@ihop.ca");
        // create inventories of trinkets to trade
        Inventory borrowerInventory = new Inventory();
        Inventory ownerInventory = new Inventory();
        Inventory secondInventory = new Inventory();

        Trinket necklace = new Trinket();
        necklace.setName("Amulet of Fire");
        Trinket ring = new Trinket();
        ring.setName("Ring of Swirling Mist");
        borrowerInventory.add(necklace);
        ownerInventory.add(ring);

        // hardcoded trades - with alternating borrowers and owners
        final Trade trade = new Trade(borrowerInventory, currentUser.getTradeManager(), ownerInventory, friend.getActualFriend().getTradeManager());
        //currentUser.getTradeManager().proposeTrade(trade); //TODO not implemented. just hardcoding into user's currentTrades ArrayList
        currentUser.getTradeManager().getTradeArchiver().archiveTrade(trade, "declined");
        assertTrue(currentUser.getTradeManager().getTradeArchiver().hasPastTrade(trade));

        // make one more trade
        Trinket bracelet = new Trinket();
        bracelet.setName("Bronze bracer");
        secondInventory.add(bracelet);

        // swapped borrower and receiver
        final Trade trade1 = new Trade(borrowerInventory, friend.getActualFriend().getTradeManager(), secondInventory, currentUser.getTradeManager());
        //currentUser.getTradeManager().proposeTrade(trade1); //TODO not implemented. just hardcoding into user's currentTrades ArrayList
        currentUser.getTradeManager().getTradeArchiver().archiveTrade(trade1, "declined");
        assertTrue(currentUser.getTradeManager().getTradeArchiver().hasPastTrade(trade1));



        // Validate that ReceiverActivity is started
        HomePageActivity homePageActivity = (HomePageActivity) homePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", homePageActivity);
        assertEquals("Monitor for ReceiverActivity has not been called", 1, homePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type", HomePageActivity.class, homePageActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(homePageActivityMonitor);

        /****UserProfileActivity****/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor profilePageActivityMonitor = getInstrumentation().addMonitor(UserProfileActivity.class.getName(), null, false);

        // actions which lead to next activity
        final Button homePageProfileButton = homePageActivity.getProfileButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                homePageProfileButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        UserProfileActivity userProfileActivity = (UserProfileActivity) profilePageActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", userProfileActivity);
        assertEquals("Monitor for ReceiverActivity has not been called", 1, profilePageActivityMonitor.getHits());
        assertEquals("Activity is of wrong type", UserProfileActivity.class, userProfileActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(profilePageActivityMonitor);

        /****ActiveTradesActivity****/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor currentTradesActivityMonitor = getInstrumentation().addMonitor(TradesActivity.class.getName(), null, false);

        // actions which lead to next activity
        final Button tradesProfilePageButton = userProfileActivity.getTradesButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                tradesProfilePageButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        TradesActivity currentTradesActivity = (TradesActivity) currentTradesActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", currentTradesActivity);
        assertEquals("Monitor for ReceiverActivity has not been called", 1, currentTradesActivityMonitor.getHits());
        assertEquals("Activity is of wrong type", TradesActivity.class, currentTradesActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(currentTradesActivityMonitor);

        /****PastTradesActivity****/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor pastTradesActivityMonitor = getInstrumentation().addMonitor(PastTradesActivity.class.getName(), null, false);

        // actions which lead to next activity
        final Button pastTradesButton = currentTradesActivity.getPastTradesButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                pastTradesButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        PastTradesActivity pastTradesActivity = (PastTradesActivity) pastTradesActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", pastTradesActivity);
        assertEquals("Monitor for ReceiverActivity has not been called", 1, pastTradesActivityMonitor.getHits());
        assertEquals("Activity is of wrong type", PastTradesActivity.class, pastTradesActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(pastTradesActivityMonitor);

        /****TradeDetailsActivity****/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor tradeDetailsActivityMonitor = getInstrumentation().addMonitor(TradeDetailsActivity.class.getName(), null, false);

        // trade at 1st index should be the first trade that was added. let's click it
        // actions which lead to next activity
        final ListView pastTradesList = pastTradesActivity.getPastTradesListView();
        currentTradesActivity.runOnUiThread(new Runnable() {
            public void run() {
                View tradeBox1 = pastTradesList.getChildAt(1);
                pastTradesList.performItemClick(tradeBox1, 1, tradeBox1.getId());
                // check that we clicked the correct trade
                assertTrue((trade.toString()).equals(ApplicationState.getInstance().getClickedTrade().toString()));
            }
        });
        getInstrumentation().waitForIdleSync();

        // regular activity check stuff
        // Validate that ReceiverActivity is started
        TradeDetailsActivity tradeDetailsActivity = (TradeDetailsActivity) tradeDetailsActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", tradeDetailsActivity);
        assertEquals("Monitor for ReceiverActivity has not been called", 1, tradeDetailsActivityMonitor.getHits());
        assertEquals("Activity is of wrong type", TradeDetailsActivity.class, tradeDetailsActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(tradeDetailsActivityMonitor);

        // finish activities
        loginActivity.finish();
        homePageActivity.finish();
        userProfileActivity.finish();
        currentTradesActivity.finish();
        pastTradesActivity.finish();
        tradeDetailsActivity.finish();

        try{
            currentUser.deleteFromNetwork(); // delete LoggedInUser from network
        }catch(IOException e){

        }
    }

    /*
    * User Case : Notify parties of accepted trade - email; could possibly be bundled with acceptTrade test
    * THIS TEST IS TIED TO THE testAcceptTrade().  AN EMAIL IS AUTOMATICALLY SENT WHEN "Accept Trade" is clicked.
    * THIS TEST WAS VERIFIED BY CHECKING THE EMAIL ACCOUNT LOGGED INTO ON THE EMULATOR.  THE EMAIL DRAFT WAS
    * CREATED WITH CORRECT INFORMATION INCLUDED.
    * */
    public void testNotifyPartiesOfAcceptedTrade() {
        
    }
}
