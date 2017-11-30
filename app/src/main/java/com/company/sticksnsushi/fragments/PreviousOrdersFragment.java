package com.company.sticksnsushi.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.infrastructure.Item;
import com.company.sticksnsushi.infrastructure.PreviousOrdersItem;

import java.util.ArrayList;

/**
 * Created by Khurram Saeed Malik on 02/11/2017.
 */

public class PreviousOrdersFragment extends Fragment {

    public static ArrayList<PreviousOrdersItem> dataPreviousOrders = new ArrayList<>();

    // For debugging purposes
    private static final String TAG = "Previous";

    private RecyclerView recyclerView;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Tidligere ordrer");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.sidebar_item_previous_orders, container, false);
        rootView.setTag(TAG);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewPreviousOrders);

        // setLayoutManager is required in RecyclerView - GridLayout is used with 2 rows.
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        // Intantiating Adapter.
        CustomDataAdapter adapter = new CustomDataAdapter();

        //Dummie data for testing
        dataPreviousOrders.add(new PreviousOrdersItem("Ebi hapsere", "199 kr.", "30/11-2017"));
        dataPreviousOrders.add(new PreviousOrdersItem("Ebi hapsere", "199 kr.", "30/11-2017"));
        dataPreviousOrders.add(new PreviousOrdersItem("Ebi hapsere", "199 kr.", "30/11-2017"));
        dataPreviousOrders.add(new PreviousOrdersItem("Ebi hapsere", "199 kr.", "30/11-2017"));

        // Add dataCategories to my adapter
        // Add dataCategories to my adapter
        for (int i = 0; i < dataPreviousOrders.size(); i++) {
            adapter.addItem(dataPreviousOrders.get(i));
        }

            recyclerView.setAdapter(adapter);

            return rootView;
        }


    /**
     * Custom Adapter
     * @author Khurram Saeed Malik
     */
    public class CustomDataAdapter extends RecyclerView.Adapter<DataListViewHolder> {
        private final ArrayList<PreviousOrdersItem> items;

        public CustomDataAdapter() {
            this.items = new ArrayList<>();
        }

        public void addItem(PreviousOrdersItem item) {
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
            View view = getActivity().getLayoutInflater().inflate(R.layout.sidebar_item_previous_orders_item, parent, false);

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
           PreviousOrdersItem item = dataPreviousOrders.get(position);

            holder.info.setText(item.getInfo());
            holder.price.setText(item.getPrice());
            holder.date.setText(item.getDate());

        }


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
        private TextView info, date, price;

        public DataListViewHolder(View itemView) {
            super(itemView);

            info = itemView.findViewById(R.id.previous_orders_info);
            price = itemView.findViewById(R.id.previous_orders_price);
            date = itemView.findViewById(R.id.previous_orders_date);
        }

    }
}
