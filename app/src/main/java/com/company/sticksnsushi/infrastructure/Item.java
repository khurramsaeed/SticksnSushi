package com.company.sticksnsushi.infrastructure;

import android.graphics.Bitmap;

/**
 * Created by ksm on 08/11/2017.
 */

public class Item {
    private int id;
    private int price;
    private String itemName;
    private String itemPCS;
    private String itemDescription;
    private String category;
    private Bitmap itemImage;

    public Item(int id, int price, String itemName, String itemPCS , String itemDescription, String category, Bitmap itemImage) {
        this.id = id;
        this.price = price;
        this.itemName = itemName;
        this.itemPCS= itemPCS;
        this.itemDescription = itemDescription;
        this.category = category;
        this.itemImage = itemImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String  getItemPCS() {return itemPCS; }

    public void setItemPCS(String itemPCS) {this.itemPCS = itemPCS; }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Bitmap getItemImage() {
        return itemImage;
    }

    public void setItemImage(Bitmap itemImage) {
        this.itemImage = itemImage;
    }
}
