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

import ca.ualberta.trinkettrader.Friends.Friend;
import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.Inventory.Trinket.Trinket;
import ca.ualberta.trinkettrader.Trades.PastTradesActivity;
import ca.ualberta.trinkettrader.Trades.Trade;
import ca.ualberta.trinkettrader.Trades.TradeDetailsActivity;
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



    // TODO User Case: Accept a proposed trade
    // Test accepting trade
    public void testAcceptTrade() {
        User user = LoggedInUser.getInstance();
        Trade trade = new Trade(user.getInventory(), user.getTradeManager(), user.getInventory(), user.getTradeManager());
        // Send a trade to yourself as a test
        user.getTradeManager().proposeTrade(trade);
        // Test if this trade has triggered a notification
        assertTrue(user.getNotificationManager().hasNotification());
        user.getTradeManager().acceptTrade(trade, "Test accept message");
        //assertTrue(trade.getStatus().equals("accepted"));  // TODO need to clarify what status names will be
        //assertTrue(user.getTradeManager().getTradeArchiver().getPastTrade(trade).getStatus().equals("Accepted"));

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

    // TODO User Case: Decline a proposed trade
    // Test rejecting a trade without sending a counter offer
    public void testDeclineTradeNoCounter() {
        User user = LoggedInUser.getInstance();
        Trade trade = new Trade(user.getInventory(), user.getTradeManager(), user.getInventory(), user.getTradeManager());
        // Send a trade to yourself as a test
        user.getTradeManager().proposeTrade(trade);
        // Test if this trade has triggered a notification
        assertTrue(user.getNotificationManager().hasNotification());
        user.getTradeManager().declineTrade(trade);
        // Send null instead of a counter trade
        // trade.sendCounterTrade(null); TODO
        assertTrue(trade.getStatus().equals("declined"));
        // don't think we need the line below?
        //assertTrue(user.getTradeManager().getTradeArchiver().getPastTrade(trade).getStatus().equals("rejected"));
        assertNotNull(null);

        /*
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

    // TODO User Case: Propose a counter-trade
    // Test rejecting a trade with sending a counter offer
    public void testDeclineTradeWithCounter() {
        User user = LoggedInUser.getInstance();
        // Send a trade to yourself as a test
        Trade trade = new Trade(user.getInventory(), user.getTradeManager(), user.getInventory(), user.getTradeManager());
        user.getTradeManager().proposeTrade(trade);
        // Test if this trade has triggered a notification
        assertTrue(user.getNotificationManager().hasNotification());
        user.getTradeManager().declineTrade(trade);
        // Send a counter trade
        Trade counterTrade = new Trade(user.getInventory(), user.getTradeManager(), user.getInventory(), user.getTradeManager());
        assertFalse(trade.getStatus().equals("closed"));

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

    // TODO User Case: owner/borrower can browse all current trades involving them
    // TODO Make one for someone who is not owner proposing a trade?
    //check that the proposed trade shows up in the users current trades
    public void testProposedTradeAppearsInCurrentTrades() {
        User user = LoggedInUser.getInstance();
        Trade trade = new Trade(user.getInventory(), user.getTradeManager(), user.getInventory(), user.getTradeManager());
        assertFalse(user.getTradeManager().getTradeArchiver().hasCurrentTrade(trade));
        user.getTradeManager().proposeTrade(trade);
        assertTrue(user.getTradeManager().getTradeArchiver().hasCurrentTrade(trade));

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

        /****Hardcode in trades to click****/
        LoggedInUser currentUser = LoggedInUser.getInstance();

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

        // trade with self
        final Trade trade = new Trade(borrowerInventory, currentUser.getTradeManager(), ownerInventory, currentUser.getTradeManager());
        //currentUser.getTradeManager().proposeTrade(trade); //TODO not implemented. just hardcoding into user's currentTrades ArrayList
        currentUser.getTradeManager().getTradeArchiver().addTrade(trade);
        assertTrue(currentUser.getTradeManager().getTradeArchiver().hasCurrentTrade(trade));

        // make one more trade
        Trinket bracelet = new Trinket();
        bracelet.setName("Bronze bracer");
        secondInventory.add(bracelet);

        final Trade trade1 = new Trade(borrowerInventory, currentUser.getTradeManager(), secondInventory, currentUser.getTradeManager());
        //currentUser.getTradeManager().proposeTrade(trade1); //TODO not implemented. just hardcoding into user's currentTrades ArrayList
        currentUser.getTradeManager().getTradeArchiver().addTrade(trade1);
        assertTrue(currentUser.getTradeManager().getTradeArchiver().hasCurrentTrade(trade1));


        // Get the current activity
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** HomePageActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor homePageActivityMonitor = getInstrumentation().addMonitor(HomePageActivity.class.getName(), null, false);

        // actions which lead to next activity
        final String test_email = loginActivity.getResources().getString(R.string.test_email);
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
        Instrumentation.ActivityMonitor tradeDetailsActivityMonitor = getInstrumentation().addMonitor(TradeDetailsActivity.class.getName(), null, false);

        // trade at 1st index should be the first trade that was added. let's click it
        // actions which lead to next activity
        final ListView currentTradesList = currentTradesActivity.getCurrentTradesListView();
        currentTradesActivity.runOnUiThread(new Runnable() {
            public void run() {
                View tradeBox1 = currentTradesList.getChildAt(1);
                currentTradesList.performItemClick(tradeBox1, 1, tradeBox1.getId());
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
        /****Hardcode in trades to click****/
        LoggedInUser currentUser = LoggedInUser.getInstance();

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

        // trade with self
        final Trade trade = new Trade(borrowerInventory, currentUser.getTradeManager(), ownerInventory, currentUser.getTradeManager());
        //currentUser.getTradeManager().proposeTrade(trade); //TODO not implemented. just hardcoding into user's currentTrades ArrayList
        currentUser.getTradeManager().getTradeArchiver().archiveTrade(trade, "declined");
        assertTrue(currentUser.getTradeManager().getTradeArchiver().hasPastTrade(trade));

        // make one more trade
        Trinket bracelet = new Trinket();
        bracelet.setName("Bronze bracer");
        secondInventory.add(bracelet);

        final Trade trade1 = new Trade(borrowerInventory, currentUser.getTradeManager(), secondInventory, currentUser.getTradeManager());
        //currentUser.getTradeManager().proposeTrade(trade1); //TODO not implemented. just hardcoding into user's currentTrades ArrayList
        currentUser.getTradeManager().getTradeArchiver().archiveTrade(trade1, "accepted");
        assertTrue(currentUser.getTradeManager().getTradeArchiver().hasPastTrade(trade1));


        // Get the current activity
        LoginActivity loginActivity = (LoginActivity) getActivity();

        /******** HomePageActivity ********/
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor homePageActivityMonitor = getInstrumentation().addMonitor(HomePageActivity.class.getName(), null, false);

        // actions which lead to next activity
        final String test_email = loginActivity.getResources().getString(R.string.test_email);
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

    }

    // TODO: User Case : Notify parties of accepted trade - email; could possibly be bundled with acceptTrade test
    /**/
    public void testNotifyPartiesOfAcceptedTrade() {
        assertNotNull(null);
    }
}
