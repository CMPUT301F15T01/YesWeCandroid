package ca.ualberta.trinkettrader;

import android.provider.ContactsContract.CommonDataKinds.Photo;

import java.util.ArrayList;

/**
 * Created by dashley on 2015-10-21.
 */
public class Trinket {

    private ArrayList<Photo> photos;
    private Integer quantity;
    private String accessibility;
    private String category;
    private String description;
    private String name;

    public Trinket() {
    }

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
