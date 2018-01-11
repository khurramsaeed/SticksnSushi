package com.company.sticksnsushi.infrastructure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khurram Saeed Malik on 09/01/2018.
 */

public class Cart {
    private int itemId;
    private int quantity = 0;
    private int total = 0;

    ArrayList<Item> items = new ArrayList<>();

    public Cart() {}


    public void addItem(Item item) {
        this.total = (item.getItemTotal() + total);
        System.out.println(toString());
        if (items.contains(item)) {return;}
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

    public ArrayList<Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "quantity=" + quantity +
                ", total=" + total +
                '}';
    }
}
