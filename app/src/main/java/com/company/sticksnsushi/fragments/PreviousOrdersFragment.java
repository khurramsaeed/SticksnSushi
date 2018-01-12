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

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.infrastructure.Cart;
import com.company.sticksnsushi.infrastructure.PreviousOrdersItem;
import com.company.sticksnsushi.infrastructure.SticksnSushiApplication;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khurram Saeed Malik on 02/11/2017.
 */

public class PreviousOrdersFragment extends Fragment {

    public static ArrayList<PreviousOrdersItem> dataPreviousOrders = new ArrayList<>();

    // For debugging purposes
    private static final String TAG = "Previous";

    private RecyclerView recyclerView;
    private SticksnSushiApplication app = SticksnSushiApplication.getInstance();
    private DatabaseReference databaseReference;
    List<Cart> cartItems = new ArrayList<>();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Tidligere ordrer");
        FirebaseUser user = app.firebaseAuth.getCurrentUser();
        assert user != null;
        databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid());
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


        //Dummie data for testing
//        dataPreviousOrders.add(new PreviousOrdersItem("Ebi hapsere", "30/11-2017", "199 kr. (3 emner)"));


        CustomDataAdapter adapter = new CustomDataAdapter();
        // Add dataCategories to my adapter
        for (int i = 0; i < cartItems.size(); i++) {
            adapter.addItem(cartItems.get(i));
        }

            recyclerView.setAdapter(adapter);

            return rootView;
        }

    @Override
    public void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cartItems.clear();
                GenericTypeIndicator<ArrayList<Cart>> t = new GenericTypeIndicator<ArrayList<Cart>>() {};
                cartItems = dataSnapshot.getValue(t);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Custom Adapter
     * @author Khurram Saeed Malik
     */
    public class CustomDataAdapter extends RecyclerView.Adapter<DataListViewHolder> {
        private final ArrayList<Cart> items;

        public CustomDataAdapter() {
            this.items = new ArrayList<>();
        }

        public void addItem(Cart cart) {
            items.add(cart);
            // Sidste element af Array
            notifyItemInserted(items.size() - 1);
        }

        // If we want to remove item
        public void removeItem(Cart item) {
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
                    // TODO: 12/01/2018 LOGIC
                }
            });

            return new DataListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DataListViewHolder holder, int position) {
           Cart order = items.get(position);

            holder.info.setText(order.getOrderId());
            holder.price.setText(order.getTotal());
            holder.date.setText(order.getOrderDate());
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
            info = itemView.findViewById(R.id.previous_orders_orderId);
            price = itemView.findViewById(R.id.previous_orders_price);
            date = itemView.findViewById(R.id.previous_orders_date);
        }

    }
}
