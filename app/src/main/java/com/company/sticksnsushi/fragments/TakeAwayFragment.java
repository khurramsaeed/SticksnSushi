package com.company.sticksnsushi.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.company.sticksnsushi.infrastructure.Categories;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Khurram Saeed Malik on 02/11/2017.
 */

public class TakeAwayFragment extends Fragment {

    // For debugging purposes
    private static final String TAG = "TakeAwayFragment";

    private RecyclerView recyclerView;
    private ArrayList<Categories> data = new ArrayList<>();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("TAKEAWAY");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Get data from JSON
        retrieveListView();

        View rootView = inflater.inflate(R.layout.sidebar_item_takeaway, container, false);
        rootView.setTag(TAG);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        // setLayoutManager is required in RecyclerView - GridLayout is used with 2 rows.
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Intantiating custom Adapter.
        CustomDataAdapter adapter = new CustomDataAdapter();
        recyclerView.setAdapter(adapter);

        // Add data to my adapter
        for (int i = 0; i < data.size(); i++) {
            adapter.addItem(data.get(i));
        }

        return rootView;
    }

    /**
     * Custom Adapter
     * @author Khurram Saeed Malik
     */
    public class CustomDataAdapter extends RecyclerView.Adapter<DataListViewHolder> {
        private final ArrayList<Categories> items;

        public CustomDataAdapter() {
            this.items = new ArrayList<>();
        }

        public void addItem(Categories item) {
            items.add(item);
            // Sidste element af Array
            notifyItemInserted(items.size() - 1);
        }

        // If we want to remove item
        public void removeItem(Categories item) {
            int position = items.indexOf(item);
            if (position == -1) {
                return;
            }
            items.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, items.size() );
        }

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
            Categories item = data.get(position);

            holder.title.setText(item.getItemName());
            holder.image.setImageBitmap(item.getItemImage());

        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    /***
     * Gets rows from JSON and puts in ArrayList, HashMap
     * afterwards SimpleAdapter is used to create list item views from item
     */
    private void retrieveListView() {

        try {
            InputStream is = getResources().openRawResource(R.raw.data_categories);

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
                // Convert resId to BitMap
                Bitmap itemImage = BitmapFactory.decodeResource(getResources(), resId);

                data.add(new Categories(title, itemImage));

            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }

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
