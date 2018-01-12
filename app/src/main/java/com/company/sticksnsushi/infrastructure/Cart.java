package com.company.sticksnsushi.infrastructure;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Khurram Saeed Malik on 09/01/2018.
 */

public class Cart {
    private int orderId;
    private String orderDate;
    private int total = 0;
    private ArrayList<Item> items = new ArrayList<>();

    public Cart() {}

    public void addItem(Item item) {
        if (items.contains(item)) {return;}
        items.add(item);
    }

    public void removeItem(Item item) {
        int itemTotal = item.getItemTotal();
        total = total - itemTotal;
        if (items.contains(item)) {
            items.remove(item);}
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    public int getTotal() {
        return total;
    }

    public void setTotal(int price) {
        total = total + price;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

}
