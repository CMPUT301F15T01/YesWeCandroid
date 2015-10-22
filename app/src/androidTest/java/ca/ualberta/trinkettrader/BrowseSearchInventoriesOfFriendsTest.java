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

import java.util.Iterator;

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
        Trinket trinket1 = new Trinket();
        trinket1.setAccessibility("public");
        user2Inventory.add(trinket1);
        Trinket trinket2 = new Trinket();
        trinket2.setAccessibility("private");
        user2Inventory.add(trinket2);

        User user3 = new User();
        Trinket trinket3 = new Trinket();
        trinket3.setAccessibility("public");
        user2Inventory.add(trinket3);

        user1.getFriendsList().add(user2);
        user2.getFriendsList().add(user1);

        Inventory inventory = user1.getInventory();
        Iterator iterator = inventory.iterator();
        while (iterator.hasNext()) {
            assertTrue(iterator.next() == trinket1);
        }
    }

    public void testSearchInventoriesOfFriendsByCategory() {
        User user1 = new User();

        User user2 = new User();
        Inventory user2Inventory = new Inventory();
        Trinket trinket1 = new Trinket();
        trinket1.setAccessibility("public");
        trinket1.setCategory("necklace");
        user2Inventory.add(trinket1);
        Trinket trinket2 = new Trinket();
        trinket2.setAccessibility("public");
        trinket2.setCategory("ring");
        user2Inventory.add(trinket2);

        User user3 = new User();
        Trinket trinket3 = new Trinket();
        trinket3.setAccessibility("public");
        trinket3.setAccessibility("necklace");
        user2Inventory.add(trinket3);

        user1.getFriendsList().add(user2);
        user2.getFriendsList().add(user1);

        Inventory inventory = Searcher.searchInventoryByCategory(user1, "necklace");
        Iterator iterator = inventory.iterator();
        while (iterator.hasNext()) {
            assertTrue(iterator.next() == trinket1);
        }
    }

    public void testSearchInventoriesOfFriendsByTextualQuery() {
        User user1 = new User();

        User user2 = new User();
        Inventory user2Inventory = new Inventory();
        Trinket trinket1 = new Trinket();
        trinket1.setAccessibility("public");
        trinket1.setDescription("rose gold ring with a garnet");
        user2Inventory.add(trinket1);
        Trinket trinket2 = new Trinket();
        trinket2.setDescription("rose gold ring with a ruby");
        user2Inventory.add(trinket2);

        User user3 = new User();
        Trinket trinket3 = new Trinket();
        trinket3.setAccessibility("public");
        user2Inventory.add(trinket3);

        user1.getFriendsList().add(user2);
        user2.getFriendsList().add(user1);

        Inventory inventory = Searcher.searchInventoryByDescription(user1, "garnet");
        Iterator iterator = inventory.iterator();
        while (iterator.hasNext()) {
            assertTrue(iterator.next() == trinket1);
        }
    }

    public void testSharedtrinketsAreSearchableByFriends() {
        User user1 = new User();

        User user2 = new User();
        Inventory user2Inventory = new Inventory();
        Trinket trinket1 = new Trinket();
        trinket1.setAccessibility("public");
        user2Inventory.add(trinket1);
        Trinket trinket2 = new Trinket();
        trinket2.setAccessibility("private");
        user2Inventory.add(trinket2);

        User user3 = new User();
        Trinket trinket3 = new Trinket();
        trinket3.setAccessibility("public");
        user2Inventory.add(trinket3);

        user1.getFriendsList().add(user2);
        user2.getFriendsList().add(user1);

        Inventory inventory = user1.getInventory();
        Iterator iterator = inventory.iterator();
        while (iterator.hasNext()) {
            assertTrue(iterator.next() == trinket1);
        }
    }
}
