package com.company.sticksnsushi.fragments;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.activities.MenuOverviewActivity;
import com.company.sticksnsushi.infrastructure.MenuCategoryItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Khurram Saeed Malik on 02/11/2017.
 */

public class TakeAwayFragment extends Fragment {

    // For debugging purposes
    private static final String TAG = "TakeAwayFragment";

    private RecyclerView recyclerView;
    private ArrayList<HashMap<String, Object>> data = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.sidebar_item_takeaway, container, false);
        rootView.setTag(TAG);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        // setLayoutManager is required in RecyclerView - GridLayout is used with 2 rows.
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        // Intantiating custom Adapter.
        CustomDataAdapter adapter = new CustomDataAdapter();
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    /**
     * Custom Adapter
     * @author Khurram Saeed Malik
     */
    public class CustomDataAdapter extends RecyclerView.Adapter<DataListViewHolder> {

        @Override
        public DataListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_takeaway_item, parent, false);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getContext(), MenuOverviewActivity.class));
                }
            });

            return new DataListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DataListViewHolder holder, int position) {
            for (int i =0; i <= position; i++) {
                holder.image.setImageResource(R.drawable.maki_01);
                holder.title.setText("Title her");
            }

        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("TAKEAWAY");

        retrieveListView();
    }

    /***
     * Gets rows from JSON and puts in ArrayList, HashMap
     * afterwards SimpleAdapter is used to create list item views from data
     */
    private void retrieveListView() {

        try {
            InputStream is = getResources().openRawResource(R.raw.data_categories);
            //InputStream is = new URL("http://javabog.dk/eksempel.json").openStream();

            byte b[] = new byte[is.available()]; // kun små filer
            is.read(b);
            String str = new String(b, "UTF-8");

            JSONObject json = new JSONObject(str);

            JSONArray categories = json.getJSONArray("categories");

            int number = categories.length();
            for (int i = 0; i < number; i++) {
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

      /*  SimpleAdapter adapter = new SimpleAdapter(getContext(), data, R.layout.fragment_takeaway_item, hashMapProperties, textViewIds);

        ListView listView = view.findViewById(R.id.sidebar_takeaway_listView);
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter); */
    }

    /**
     * ViewHolder er et object som er ansvarlig for indeholder referencer
     * til de enkelte items som vises i RecyclerView
     */
    private class DataListViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView image;

        public DataListViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.takeaway_item_name);
            image = (ImageView) itemView.findViewById(R.id.takeaway_item_image);
        }

    }
}
