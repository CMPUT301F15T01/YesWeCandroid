package ca.ualberta.trinkettrader;

/**
 * Created by Andrea McIntosh on 01/11/2015.
 */
public class AddOrEditItemController {

    private AddOrEditItemActivity activity;
    private Trinket trinket;
    private User user = LoggedInUser.getInstance();

    public AddOrEditItemController(AddOrEditItemActivity act) {
        this.activity = act;
    }

    public void onSaveClick() {
        this.trinket = new Trinket();
        if (this.activity.getAccessibility().isChecked()) {
            this.trinket.setAccessibility("public");
        } else {
            this.trinket.setAccessibility("private");
        }
        this.trinket.setCategory(this.activity.getItemCategory().toString());
        this.trinket.setDescription(this.activity.getItemDescription().getText().toString());
        this.trinket.setName(this.activity.getItemName().getText().toString());
        this.trinket.setQuantity(this.activity.getItemQuantity().getText().toString());
        this.trinket.setQuality(this.activity.getItemQuality().toString());
        this.user.getInventory().add(this.trinket);
    }
}
