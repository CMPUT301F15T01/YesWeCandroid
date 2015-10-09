package ca.ualberta.trinkettrader;
import android.test.ActivityInstrumentationTestCase2;
/**
 Copyright 2015 Andrea McIntosh, Dylan Ashley, Anju Eappen, Jenna Hatchard, Kirsten Svidal, Raghav Vamaraju

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
public class InventoryTests extends ActivityInstrumentationTestCase2 {

    public InventoryTests() {
        super(MainActivity.class);
    }

    // Test method for checking if inventory contains a certain item
    public void testHasItem() {
        Inventory inventory = new Inventory();
        MyItem myItem = new MyItem();
        assertFalse(inventory.hasItem(myItem));
        inventory.addItem(myItem);
        assertTrue(inventory.hasItem(myItem));
    }

    // Test if a user has an inventory
    public void testHasInventory() {
        User user = new User();
        Inventory inventory = new Inventory();
        user.setInventory(inventory);
        assertTrue(user.hasInventory());
    }

    // Test method for getting the number of items in an inventory
    public void testNumberOfItemsInInventory() {
        Inventory inventory = new Inventory();
        MyItem myItem1 = new MyItem();
        inventory.addItem(myItem1);
        MyItem myItem2 = new MyItem();
        inventory.addItem(myItem2);
        MyItem myItem3 = new MyItem();
        inventory.addItem(myItem3);
        assertTrue(inventory.size().equals(3));

        inventory.removeItem(myItem1);
        inventory.removeItem(myItem2);
        assertTrue(inventory.size().equals(1));
    }

    // Test method to test if an inventory is empty
    public void testIsInventoryEmpty() {
        Inventory inventory = new Inventory();
        assertTrue(inventory.isEmpty());
        MyItem myItem = new MyItem();
        inventory.addItem(myItem);
        assertFalse(inventory.isEmpty());
    }

    // Test method for adding an item to your inventory
    public void testAddItem() {
        Inventory inventory = new Inventory();
        MyItem myItem = new MyItem();
        inventory.addItem(myItem);
        assertTrue(inventory.hasItem(myItem));
    }

    // Test method for removing an item from your inventory
    public void testRemoveItem() {
        Inventory inventory = new Inventory();
        MyItem myItem = new MyItem();
        inventory.addItem(myItem);
        assertTrue(inventory.hasItem(myItem));
        inventory.removeItem(myItem);
        assertFalse(inventory.hasItem(myItem));
    }

    // Test method for changing the share settings of an item
    public void testShareSettings() {
        MyItem myItem = new MyItem();
        // Default share value is "public"
        assertTrue(myItem.getShareStatus().equals("public"));
        // Change the item's share status to private
        myItem.setShareStatus("private");
        assertTrue(myItem.getShareStatus().equals("private"));
    }

    /* Test methods to edit an item's details */
    public void testChangeItemName() {
        MyItem myItem = new MyItem();
        myItem.setName("Test Name");
        assertTrue(myItem.getName().equals("Test Name"));
        myItem.setName("New Test Name");
        assertTrue(myItem.getName().equals("New Test Name"));
    }
    public void testChangeItemQuantity() {
        MyItem myItem = new MyItem();
        myItem.setQuantity(5);
        assertTrue(myItem.getQuantity().equals(5));
        myItem.setQuantity(10);
        assertTrue(myItem.getQuantity().equals(10));
    }
    public void testChangeItemQuality() {
        MyItem myItem = new MyItem();
        myItem.setQuality("poor");
        assertTrue(myItem.getQuantity().equals("poor"));
        myItem.setQuality("good");
        assertTrue(myItem.getQuantity().equals("good"));
    }
    public void testChangeItemDescription() {
        MyItem myItem = new MyItem();
        myItem.setDescription("Test description.");
        assertTrue(myItem.getDescription().equals("Test description."));
        myItem.setDescription("New test description.");
        assertTrue(myItem.getDescription().equals("New test description"));
    }
    // Test for changing item's category
    public void testChangeItemCategory() {
        MyItem myItem = new MyItem();
        myItem.setCategory("Bracelets");
        assertTrue(myItem.getCategory().equals("Bracelets"));
        myItem.setCategory("Rings");
        assertTrue(myItem.getCategory().equals("Rings"));
    }

    // Test method for removing multiple items at once
    public void testBatchItemRemoval() {
        Inventory inventory = new Inventory();
        MyItem myItem1 = new MyItem();
        inventory.addItem(myItem1);
        MyItem myItem2 = new MyItem();
        inventory.addItem(myItem2);
        MyItem myItem3 = new MyItem();
        inventory.addItem(myItem3);
        MyItem myItem4 = new MyItem();
        inventory.addItem(myItem4);
        assertTrue(inventory.hasItem(myItem1));
        assertTrue(inventory.hasItem(myItem2));
        assertTrue(inventory.hasItem(myItem3));
        assertTrue(inventory.hasItem(myItem4));

        // Remove items 1, 3, and 4 from inventory
        inventory.batchRemoveItems(myItem1, myItem3, myItem4);
        assertFalse(inventory.hasItem(myItem1));
        assertTrue(inventory.hasItem(myItem2));
        assertFalse(inventory.hasItem(myItem3));
        assertFalse(inventory.hasItem(myItem4));
    }
}
