package com.company.sticksnsushi.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.infrastructure.MenuCategoryItem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Khurram Saeed Malik on 02/11/2017.
 */

public class TakeAway extends Fragment {
    // For debugging purposes
    private static final String TAG = "TakeAwayFragment";
    private ArrayList<HashMap<String, Object>> data = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sidebar_item_takeaway, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("TAKEAWAY");

        retrieveListView(view);
    }

    /***
     * Gets rows from JSON and puts in ArrayList, HashMap
     * afterwards SimpleAdapter is used to create list item views from data
     * @param view
     */
    private void retrieveListView(View view) {

        // TODO: 19/11/2017 IMPLEMENT DB OR JSON TO GET FILES DYNAMICALLY
        Log.d(TAG, "retrieveListView: Show data in ListView");

        // Local String and int arrays to store values
        String[] hashMapProperties = {"itemName", "itemImage"};
        int[] textViewIds = {R.id.overview_list_item_name, R.id.overview_list_item_image};

        //while(cursor.moveToNext()){
        // Values from the database in column 1 & 2
        data.add(new MenuCategoryItem("Starters", R.drawable.starters_01).toHashMap());
        data.add(new MenuCategoryItem("Maki", R.drawable.maki_01).toHashMap());

        data.add(new MenuCategoryItem("Desserter", R.drawable.dessert_01).toHashMap());
        data.add(new MenuCategoryItem("Maki", R.drawable.maki_01).toHashMap());
        //}
        SimpleAdapter adapter = new SimpleAdapter(getContext(), data, R.layout.list_item_menu_overview, hashMapProperties, textViewIds);

        ListView listView = view.findViewById(R.id.sidebar_takeaway_listView);
        listView.setAdapter(adapter);
    }
}
