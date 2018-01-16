package com.company.sticksnsushi.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.activities.NavDrawerActivity;
import com.company.sticksnsushi.infrastructure.App;
import com.company.sticksnsushi.infrastructure.Cart;
import com.company.sticksnsushi.infrastructure.User;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by Khurram Saeed Malik on 02/11/2017.
 */

public class PreviousOrdersFragment extends BaseFragment {
    // For debugging purposes
    private static final String TAG = "Previous";

    private RecyclerView recyclerView;
    private App app = App.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sidebar_item_previous_orders, container, false);
        rootView.setTag(TAG);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewPreviousOrders);

        // setLayoutManager is required in RecyclerView - GridLayout is used with 2 rows.
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        CustomDataAdapter adapter = new CustomDataAdapter();

        if (savedInstanceState !=null) {
            adapter.clearItems();
        }
        // Add dataCategories to my adapter
        for (int i = 0; i < app.orders.size(); i++) {
            adapter.addItem(app.orders.get(i));
        }
        recyclerView.setAdapter(adapter);


            return rootView;
        }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Tidligere ordrer");

        System.out.println("onViewCreated(): Adapter");


    }


    /**
     * Back button override for Fragment
     * Backs up to NavdrawerActivity
     */
    @Override
    public void onResume() {
        super.onResume();
        if(getView() == null){
            return;
        }
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    ((NavDrawerActivity)getActivity()).navigationView.setCheckedItem(R.id.item_takeaway);
                    ((NavDrawerActivity)getActivity()).displaySelectedItem(R.id.item_takeaway);
                    return true;
                }
                return false;
            }
        });
    }



    /**
     * Custom Adapter
     * @author Khurram Saeed Malik
     */
    public class CustomDataAdapter extends RecyclerView.Adapter<DataListViewHolder> {
        private final ArrayList<Cart> orders;

        public CustomDataAdapter() {
            this.orders = new ArrayList<>();
        }

        public void addItem(Cart order) {
            orders.add(order);
            // Sidste element af Array
            notifyItemInserted(orders.size() - 1);
        }

        public void clearItems(){
            orders.clear();
        }

        @Override
        public DataListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.sidebar_item_previous_orders_item, parent, false);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 12/01/2018 LOGIC
                }
            });

            return new DataListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DataListViewHolder holder, int position) {
           Cart order = orders.get(position);

            holder.orderId.setText(order.getOrderDate().toString());
            for (int i=0; i < order.getItems().size(); i++) {
                holder.orderedItems.append(order.getItems().get(i).getQuantity() + " x " + order.getItems().get(i).getItemName() + "\n");
            }
            holder.total.setText(order.getTotal() + " kr.");


        }

        @Override
        public int getItemCount() {
            return orders.size();
        }
    }

    /**
     * ViewHolder er et object som er ansvarlig for indeholder referencer
     * til de enkelte items som vises i RecyclerView
     */
    private class DataListViewHolder extends RecyclerView.ViewHolder {
        private TextView orderId, orderedItems, total;

        public DataListViewHolder(View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.previous_orders_orderId);
            orderedItems = itemView.findViewById(R.id.previous_orders_date);
            total = itemView.findViewById(R.id.previous_orders_price);
        }

    }
}
