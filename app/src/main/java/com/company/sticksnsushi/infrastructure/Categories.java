package com.company.sticksnsushi.infrastructure;

import android.graphics.Bitmap;

import java.util.HashMap;

/**
 * Created by Khurram Saeed Malik on 19/11/2017.
 */

public class Categories {

    private String itemName;
    private Bitmap itemImage;

    public Categories(String itemName, Bitmap itemImage) {
        this.itemName = itemName;
        this.itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public Bitmap getItemImage() {
        return itemImage;
    }

}
