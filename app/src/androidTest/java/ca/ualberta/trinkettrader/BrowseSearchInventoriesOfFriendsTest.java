//Copyright 2015 Andrea McIntosh, Dylan Ashley, Anju Eappen, Jenna Hatchard, Kirsten Svidal, Raghav Vamaraju
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.

package ca.ualberta.trinkettrader;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Testing file for "Browse Search Inventories Of Friends" use cases.
 */
public class BrowseSearchInventoriesOfFriendsTest extends ActivityInstrumentationTestCase2 {

    public BrowseSearchInventoriesOfFriendsTest(Class activityClass) {
        super(activityClass);
    }

    public void testSearchInventoriesOfFriends() {
        User user1 = new User();

        User user2 = new User();
        Inventory user2Inventory = new Inventory();
        Item item1 = new Item();
        item1.setShareProperty("friend");
        user2Inventory.addItem(item1);
        Item item2 = new Item();
        item2.setShareProperty("private");
        user2Inventory.addItem(item2);

        User user3 = new User();
        Item item3 = new Item();
        item3.setShareProperty("friend");
        user2Inventory.addItem(item3);

        user1.addFriend(user2);
        user2.addFriend(user1);

        Search search = new Search(user1, "friend");
        Inventory inventory = search.getAllItems();
        for (Item item: inventory) {
            assertTrue(item == item1);
        }
    }

    public void testSearchInventoriesOfFriendsByCategory() {
        User user1 = new User();

        User user2 = new User();
        Inventory user2Inventory = new Inventory();
        Item item1 = new Item();
        item1.setShareProperty("friend");
        item2.setCategory("necklace");
        user2Inventory.addItem(item1);
        Item item2 = new Item();
        item2.setShareProperty("friend");
        item2.setCategory("ring");
        user2Inventory.addItem(item2);

        User user3 = new User();
        Item item3 = new Item();
        item3.setShareProperty("friend");
        item3.setShareProperty("necklace");
        user2Inventory.addItem(item3);

        user1.addFriend(user2);
        user2.addFriend(user1);

        Search search = new Search(user1, "friend", "necklace");
        Inventory inventory = search.getAllItems();
        for (Item item: inventory) {
            assertTrue(item == item1);
        }
    }

    public void testSearchInventoriesOfFriendsByTextualQuery() {
        User user1 = new User();

        User user2 = new User();
        Inventory user2Inventory = new Inventory();
        Item item1 = new Item();
        item1.setShareProperty("friend");
        item1.setDescription("rose gold ring with a garnet");
        user2Inventory.addItem(item1);
        Item item2 = new Item();
        item2.setDescription("rose gold ring with a ruby");
        user2Inventory.addItem(item2);

        User user3 = new User();
        Item item3 = new Item();
        item3.setShareProperty("friend");
        user2Inventory.addItem(item3);

        user1.addFriend(user2);
        user2.addFriend(user1);

        Search search = new Search(user1, "friend");
        search.searchByQuery("garnet");
        Inventory inventory = search.getAllItems();
        for (Item item: inventory) {
            assertTrue(item == item1);
        }
    }

    public void testSharedItemsAreSearchableByFriends() {
        User user1 = new User();

        User user2 = new User();
        Inventory user2Inventory = new Inventory();
        Item item1 = new Item();
        item1.setShareProperty("friend");
        user2Inventory.addItem(item1);
        Item item2 = new Item();
        item2.setShareProperty("private");
        user2Inventory.addItem(item2);

        User user3 = new User();
        Item item3 = new Item();
        item3.setShareProperty("friend");
        user2Inventory.addItem(item3);

        user1.addFriend(user2);
        user2.addFriend(user1);

        Search search = new Search(user1, "friend");
        Inventory inventory = search.getAllItems();
        for (Item item: inventory) {
            assertTrue(item == item1);
        }
    }
}
