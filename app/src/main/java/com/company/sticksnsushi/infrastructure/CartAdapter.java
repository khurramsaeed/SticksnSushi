package com.company.sticksnsushi.infrastructure;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Swagam on 09/01/2018.
 */

public class CartAdapter extends ArrayAdapter<Item> {

    ArrayList<Item> itemArrayList;

    public CartAdapter(@NonNull Context context, ArrayList<Item> itemArrayList) {
        super(context, 0, itemArrayList);
        this.itemArrayList = itemArrayList;
    }

    @Override
    public int getCount() {

        return itemArrayList.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        Item item = getItem(position);

        if(view == null){

        }





        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

}

