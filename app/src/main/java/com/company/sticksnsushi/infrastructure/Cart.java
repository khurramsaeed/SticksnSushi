package com.company.sticksnsushi.infrastructure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khurram Saeed Malik on 09/01/2018.
 */

public class Cart {
    private int itemId;
    private int quantity = 1;
    private int total;

    ArrayList<Item> items = new ArrayList<>();

    public Cart() {}


    public void addItem(Item item) {
        this.items.add(item);
    }
    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

}
