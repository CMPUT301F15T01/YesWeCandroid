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
import android.widget.EditText;
import android.widget.ListView;

import ca.ualberta.trinkettrader.Friends.Friend;
import ca.ualberta.trinkettrader.Friends.FriendsListActivity;
import ca.ualberta.trinkettrader.Friends.FriendsProfileActivity;
import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.Trades.Trade;
import ca.ualberta.trinkettrader.Trades.TradesActivity;
import ca.ualberta.trinkettrader.Inventory.Trinket.Trinket;
import ca.ualberta.trinkettrader.User.LoggedInUser;
import ca.ualberta.trinkettrader.User.User;

public class TradeWithFriendsTest extends ActivityInstrumentationTestCase2 {
    AutoCompleteTextView loginEmailTextView;
    Button loginButton;
    ListView friendsList;
    Button friendsButton;
    Button findFriendsButton;
    EditText findFriendTextField;
    Button tradeButton;


    public TradeWithFriendsTest() {
        super(LoginActivity.class);
    }
    // TODO I think that all of the Users invloved in trades have to be made friends.
    // TODO ask about how to incorporate FriendsListController

    // TODO : User Case - Be notified of a proposed trade
    // Test method to see if user has a notification
    public void testHasNotification() {
        User user = LoggedInUser.getInstance();
        Trade trade = new Trade(user.getInventory(), user.getTradeManager(), user.getInventory(), user.getTradeManager());
        // Send a trade to yourself as a test
        user.getTradeManager().proposeTrade(trade);
        // Test if this trade has triggered a notification
        assertTrue(user.getNotificationManager().hasNotification());
        /*
        *
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
        assertTrue(trade.getStatus().equals("accepted"));  // TODO need to clarify what status names will be
        assertTrue(user.getTradeManager().getTradeArchiver().getPastTrade(trade).getStatus().equals("Accepted"));

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
        assertTrue(user.getTradeManager().getTradeArchiver().getPastTrade(trade).getStatus().equals("rejected"));
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
        trade.setNumberOfItems(1);  // TODO should this instead be a count of how many items included in the trade?
        user.getTradeManager().proposeTrade(trade);
        trade.setNumberOfItems(2);
        assertEquals((Integer) 2, trade.getNumberOfItems());

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

    // TODO is this redundant?
    // Test that user has current trades they are involved in
    public void testViewCurrentTrades() {
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

        Instrumentation.ActivityMonitor displayTradesActivityMonitor = getInstrumentation().addMonitor(TradesActivity.class.getName(), null, false);

        // Hardcode in trades for user
        LoggedInUser currentUser = LoggedInUser.getInstance();

        // create inventories of items to trade
        Inventory borrowerInventory = new Inventory();
        Inventory ownerInventory = new Inventory();

        Trinket necklace = new Trinket();
        necklace.setName("Amulet of Fire");
        Trinket ring = new Trinket();
        ring.setName("Ring of Swirling Mist");
        borrowerInventory.add(necklace);
        ownerInventory.add(ring);

        // trade with self
        Trade trade = new Trade(borrowerInventory, currentUser.getTradeManager(), ownerInventory, currentUser.getTradeManager());
        currentUser.getTradeManager().proposeTrade(trade);
        currentUser.getTradeManager().getTradeArchiver().addTrade(trade);
        assertTrue(currentUser.getTradeManager().getTradeArchiver().hasCurrentTrade(trade));

        // TODO will implement like Friends List

        // Click the trades button to proceed to the current trades page.
        tradeButton = homePageActivity.getTradeButton();
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                tradeButton.performClick();
            }
        });

        // Test that the TradesActivity started correctly after the clicking the login button.
        //Instrumentation.ActivityMonitor displayTradesActivityMonitor = getInstrumentation().addMonitor(TradesActivity.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        TradesActivity displayTradesActivity = (TradesActivity) displayTradesActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("TradesActivity is null", displayTradesActivity);
        assertEquals("Monitor for TradesActivity has not been called", 1, displayTradesActivityMonitor.getHits());
        assertEquals("Activity is of wrong type; expected TradesActivity", TradesActivity.class, displayTradesActivity.getClass());
        getInstrumentation().removeMonitor(displayTradesActivityMonitor);



        // finish activities
        loginActivity.finish();
        homePageActivity.finish();
        displayTradesActivity.finish();
        assertNotNull(null);
    }



    // TODO User Case: owner/ borrower can browse all past trade involving them
    // TODO: need to test different statuses? owner/borrower cases?
    // Test that user can browse past trades that they were involved in
    public void testPastTrades() {
        User user = LoggedInUser.getInstance();
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

    // TODO: User Case : Notify parties of accepted trade - email; could possibly be bundled with acceptTrade test
    public void testNotifyPartiesOfAcceptedTrade() {
        assertNotNull(null);
    }
}
