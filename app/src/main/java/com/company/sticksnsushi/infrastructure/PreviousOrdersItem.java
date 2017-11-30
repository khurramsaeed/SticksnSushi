package com.company.sticksnsushi.infrastructure;

/**
 * Created by Nikolaj on 30-11-2017.
 */

public class PreviousOrdersItem {
    private String info;
    private String date;
    private String price;

    public PreviousOrdersItem(String info, String date, String price) {
        this.info = info;
        this.date = date;
        this.price= price;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
