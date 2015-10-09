package ca.ualberta.trinkettrader;
import android.test.ActivityInstrumentationTestCase2;
/**
 * Created by Andrea McIntosh on 09/10/2015.
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
