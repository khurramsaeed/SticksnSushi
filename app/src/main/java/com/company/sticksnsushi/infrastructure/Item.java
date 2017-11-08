package com.company.sticksnsushi.infrastructure;

/**
 * Created by ksm on 08/11/2017.
 */

public class Item {
    private int id;
    private int price;
    private String itemName;
    private String itemDescription;
    private String category;

    public Item(int id, int price, String itemName, String itemDescription, String category) {
        this.id = id;
        this.price = price;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.category = category;
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
}
