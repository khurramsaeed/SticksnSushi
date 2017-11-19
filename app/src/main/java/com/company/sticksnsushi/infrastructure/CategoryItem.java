package com.company.sticksnsushi.infrastructure;

/**
 * Created by Khurram Saeed Malik on 19/11/2017.
 */

public class CategoryItem {

    private String itemName;
    private String imageURL;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "CategoryItem{ \n" +
                "itemName='" + itemName + '\n' +
                ", imageURL='" + imageURL + '\n' +
                '}';
    }
}
