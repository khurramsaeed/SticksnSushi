package com.company.sticksnsushi.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.activities.NavDrawerActivity;
import com.company.sticksnsushi.infrastructure.App;
import com.company.sticksnsushi.infrastructure.Cart;
import com.company.sticksnsushi.infrastructure.Item;
import com.company.sticksnsushi.infrastructure.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Khurram Saeed Malik on 02/11/2017.
 */

public class PreviousOrdersFragment extends BaseFragment {
    private static final String TAG = "PreviousOrders: ";
    private App app = App.getInstance();
    private User user = app.getAuth().getUser();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private FirebaseUser currentUser = app.firebaseAuth.getCurrentUser();
    private TextView info;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sidebar_item_previous_orders, container, false);
        setHasOptionsMenu(true);
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewPreviousOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final CustomDataAdapter adapter = new CustomDataAdapter();
        if (currentUser == null) {
            info = rootView.findViewById(R.id.previous_orders_info);
            info.setText("Du er ikke logget ind!");
            return rootView;
        }

        if (!app.network.isOnline()) {
            app.longToastMessage("Du skal have internet for at kunne se dine tidligere ordrer!");
            return rootView;
        }
        databaseReference.child("users").child(user.getId()).child("orders").getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recyclerView.setAdapter(adapter);
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Cart order = snapshot.getValue(Cart.class);
                    Log.d(TAG, "Value is: " + order.toString());
                    adapter.addItem(order);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());
                app.shortToastMessage("Der opstod en fejl i hentning af ordrer");
            }
        });

        return rootView;
        }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

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



    /**
     * Custom Adapter
     * @author Khurram Saeed Malik
     */
    public class CustomDataAdapter extends RecyclerView.Adapter<DataListViewHolder> {
        public final ArrayList<Cart> orders;

        public CustomDataAdapter() {
            this.orders = new ArrayList<>();
        }

        public void addItem(Cart order) {
            orders.add(order);
            // Sidste element af Array
            notifyItemInserted(orders.size() - 1);
        }

        @Override
        public DataListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.sidebar_item_previous_orders_item, parent, false);


            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Du er ved at genbestille")
                            .setMessage("Er du sikker på du vil genbestille igen?")
                            .setCancelable(true)
                            .setNegativeButton("Nej", null)
                            .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    app.shortToastMessage("Ikke implementeret");
                                    notifyDataSetChanged();
                                }
                            }).show();



                    return false;
                }
            });

            return new DataListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DataListViewHolder holder, int position) {
           Cart order = orders.get(position);
           ArrayList<Item> items = order.getItems();

           holder.orderId.setText(order.getOrderDate().toString());
            holder.orderedItems.setText("");
           for (int i=0; i < items.size(); i++) {
               holder.orderedItems.append(items.get(i).getQuantity() + " x " + items.get(i).getItemName() + "\n");
           }
           String emne;
           if (items.size() ==1) {emne="emne";} else {emne="emner";}
           holder.total.setText(order.getTotal() + " kr. (" + items.size() +" "+ emne + ")");

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
