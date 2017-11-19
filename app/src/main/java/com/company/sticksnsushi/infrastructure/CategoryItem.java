package com.company.sticksnsushi.infrastructure;

import java.util.HashMap;

/**
 * Created by Khurram Saeed Malik on 19/11/2017.
 */

public class CategoryItem {

    private final String itemName;
    private final Object itemImage;

    public CategoryItem(String itemName, Object itemImage) {
        this.itemName = itemName;
        this.itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public Object getItemImage() {
        return itemImage;
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> returnValue = new HashMap<>();
        returnValue.put("itemImage", getItemImage());
        returnValue.put("itemName", getItemName());
        return returnValue;
    }
}
