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

    ArrayList<Item> items = new ArrayList<>();

    public Cart() {}

    public void addItem(Item item) {
        this.total = (item.getItemTotal() + total);
        System.out.println(toString());
        if (items.contains(item)) {return;}
        this.items.add(item);

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

    public ArrayList<Item> getItems() {
        return items;
    }

}
