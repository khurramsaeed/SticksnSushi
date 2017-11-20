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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
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

        Log.d(TAG, "retrieveListView: Show data in ListView");

        // Local String and int arrays to store values
        String[] hashMapProperties = {"itemName", "itemImage"};
        int[] textViewIds = {R.id.overview_list_item_name, R.id.overview_list_item_image};

        try {
            InputStream is = getResources().openRawResource(R.raw.data_categories);
            //InputStream is = new URL("http://javabog.dk/eksempel.json").openStream();

            byte b[] = new byte[is.available()]; // kun små filer
            is.read(b);
            String str = new String(b, "UTF-8");

            JSONObject json = new JSONObject(str);

            JSONArray categories = json.getJSONArray("categories");

            int antal = categories.length();
            for (int i = 0; i < antal; i++) {
                JSONObject category = categories.getJSONObject(i);
                System.err.println("obj = " + category);

                // Get title and imageName from JSON-file
                String title = category.getString("title");
                String imageName = category.getString("imageName");

                // resId gets image resource with its identifier (image_name)
                int resId = getResources().getIdentifier(imageName, "drawable", getActivity().getPackageName());
                data.add(new MenuCategoryItem(title, resId).toHashMap());
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }

        SimpleAdapter adapter = new SimpleAdapter(getContext(), data, R.layout.list_item_menu_overview, hashMapProperties, textViewIds);

        ListView listView = view.findViewById(R.id.sidebar_takeaway_listView);
        listView.setAdapter(adapter);
    }
}
