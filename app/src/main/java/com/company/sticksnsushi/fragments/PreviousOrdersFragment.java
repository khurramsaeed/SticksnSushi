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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khurram Saeed Malik on 02/11/2017.
 */

public class PreviousOrdersFragment extends BaseFragment {
    // For debugging purposes
    private static final String TAG = "Previous";

    private RecyclerView recyclerView;
    private App app = App.getInstance();
    private DatabaseReference databaseReference;
    private List<Cart> orders = new ArrayList<>();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Tidligere ordrer");

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sidebar_item_previous_orders, container, false);
        rootView.setTag(TAG);
        FirebaseUser user = app.firebaseAuth.getCurrentUser();
        assert user != null;
        //databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewPreviousOrders);

        // setLayoutManager is required in RecyclerView - GridLayout is used with 2 rows.
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

   /*    databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // TODO: 12/01/2018 IMPLEMENET LOGIC
                //orders.add(dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }); */

        CustomDataAdapter adapter = new CustomDataAdapter();
        // Add dataCategories to my adapter
        for (int i = 0; i < orders.size(); i++) {
            adapter.addItem(orders.get(i));
        }
            recyclerView.setAdapter(adapter);

            return rootView;
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

        // If we want to remove item
        public void removeItem(Cart order) {
            int position = orders.indexOf(order);
            if (position == -1) {
                return;
            }
            orders.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, orders.size() );
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

            holder.total.setText(order.getTotal());
            holder.orderDate.setText(order.getOrderDate());
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
        private TextView orderId, orderDate, total;

        public DataListViewHolder(View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.previous_orders_orderId);
            orderDate = itemView.findViewById(R.id.previous_orders_date);
            total = itemView.findViewById(R.id.previous_orders_price);
        }

    }
}
