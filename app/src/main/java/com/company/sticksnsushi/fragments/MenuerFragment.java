package com.company.sticksnsushi.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.infrastructure.Item;

import java.util.ArrayList;

import static com.company.sticksnsushi.infrastructure.SticksnSushiApplication.dataMenuer;

/**
 * Created by Nikolaj on 27-11-2017.
 */

public class MenuerFragment extends BaseFragment {

    // For debugging purposes
    private static final String TAG = "MenuerFragment";

    private RecyclerView recyclerView;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("MENUER");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_menuer, container, false);
        rootView.setTag(TAG);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView1);

        // setLayoutManager is required in RecyclerView - GridLayout is used with 1 row.
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        // Intantiating Adapter.
        CustomDataAdapter adapter = new CustomDataAdapter();

        // Add dataCategories to my adapter
        for (int i = 0; i < dataMenuer.size(); i++) {
            adapter.addItem(dataMenuer.get(i));
        }
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    /**
     * Custom Adapter
     * @author Khurram Saeed Malik
     */
    public class CustomDataAdapter extends RecyclerView.Adapter<DataListViewHolder> {
        private final ArrayList<Item> items;

        public CustomDataAdapter() {
            this.items = new ArrayList<>();
        }

        public void addItem(Item item) {
            items.add(item);
            // Sidste element af Array
            notifyItemInserted(items.size() - 1);
        }

        // If we want to remove item
        public void removeItem(Item item) {
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
            View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_menuer_item, parent, false);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
                }
            });

            return new DataListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DataListViewHolder holder, int position) {
            Item item = dataMenuer.get(position);

            holder.title.setText(item.getItemName());
            holder.price.setText(item.getPrice() + " kr.");
            holder.pcs.setText("/"+item.getItemPCS());
            holder.image.setImageBitmap(item.getItemImage());

        }

//        @Override
//        public void onBindViewHolder(TakeAwayFragment.DataListViewHolder holder, int position) {
//            Categories item = SticksnSushiApplication.dataCategories.get(position);
//
//            holder.title.setText(item.getItemName());
//            holder.image.setImageBitmap(item.getItemImage());
//
//        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    /**
     * ViewHolder er et object som er ansvarlig for indeholder referencer
     * til de enkelte items som vises i RecyclerView
     */
    private class DataListViewHolder extends RecyclerView.ViewHolder {
        private TextView title, pcs, price;
        private ImageView image;

        public DataListViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.menuer_item_name);
            pcs = itemView.findViewById(R.id.menuer_item_pcs);
            price = itemView.findViewById(R.id.menuer_item_price);
            image = itemView.findViewById(R.id.menuer_item_image);
        }

    }
}
