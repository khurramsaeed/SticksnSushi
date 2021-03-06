package com.company.sticksnsushi.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.activities.SpecificDishActivity;
import com.company.sticksnsushi.infrastructure.App;
import com.company.sticksnsushi.infrastructure.Item;

import java.util.ArrayList;

/**
 * Created by Khurram Saeed Malik on 26/10/2017.
 */

public class FutomakiFragment extends BaseFragment {

    // For debugging purposes
    private static final String TAG = "FutomakiFragment";

    App app = App.getInstance();
    private RecyclerView recyclerView;
    AllergiesFragment allergies = new AllergiesFragment();
    String allergyAlert;
    boolean containsAllergies = false;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("FUTOMAKI");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_futomaki, container, false);
        rootView.setTag(TAG);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewFutomaki);

        // Different layout configurations for landscape/portrait mode
        if (isTablet && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        } else if (isTablet && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        } else if (!isTablet && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }

        // Intantiating Adapter.
        CustomDataAdapter adapter = new CustomDataAdapter();

        // Add dataCategories to my adapter
        for (int i = 0; i < app.dataFutomaki.size(); i++) {
            adapter.addItem(app.dataFutomaki.get(i));
        }
        recyclerView.setAdapter(adapter);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        allergies.markAllergies(sp);

        return rootView;
    }

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
                    getFragmentManager().popBackStack();
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
        public DataListViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_futomaki_item, parent, false);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = recyclerView.getChildLayoutPosition(view);
                    String category = app.dataFutomaki.get(id).getCategory();
                    checkForAllergies(view);
                    Intent myintent=new Intent(getContext(), SpecificDishActivity.class);
                    myintent.putExtra("Category", category);
                    myintent.putExtra("futomakiID", id);
                    myintent.putExtra("AllergiesBoolean", containsAllergies);
                    if(containsAllergies){
                        myintent.putExtra("AllergiesAlert", allergyAlert);
                    }


                    startActivity(myintent);
                }
            });

            return new DataListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DataListViewHolder holder, int position) {
            Item item = app.dataFutomaki.get(position);

            holder.title.setText(item.getItemName());
            holder.price.setText(item.getPrice() + " kr.");
            holder.pcs.setText("/"+item.getItemPCS());
            holder.image.setImageBitmap(item.getItemImage());

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
        private TextView title, pcs, price;
        private ImageView image;
        private ImageButton addToBasket;
        private LinearLayout addToBasketFrame;


        public DataListViewHolder(final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.futomaki_item_name);
            pcs = itemView.findViewById(R.id.futomaki_item_pcs);
            price = itemView.findViewById(R.id.futomaki_item_price);
            image = itemView.findViewById(R.id.futomaki_item_image);
            addToBasket = itemView.findViewById(R.id.futomaki_item_addToBasket);
            addToBasketFrame = itemView.findViewById(R.id.futomaki_item_addToBasketFrame);

            addToBasket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int quantity = app.dataFutomaki.get(getAdapterPosition()).getQuantity();
                    int index = getAdapterPosition();
                    app.getCart().addItem(app.dataFutomaki.get(index));
                    quantity++;
                    app.dataFutomaki.get(index).setQuantity(quantity);
                    app.shortToastMessage("Tilføjet til kurv");
                    getActivity().invalidateOptionsMenu();

                    //Calculates new total
                    app.cartTotal();
                }
            });

            addToBasketFrame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int quantity = app.dataFutomaki.get(getAdapterPosition()).getQuantity();
                    int index = getAdapterPosition();
                    app.getCart().addItem(app.dataFutomaki.get(index));
                    quantity++;
                    app.dataFutomaki.get(index).setQuantity(quantity);
                    app.shortToastMessage("Tilføjet til kurv");
                    getActivity().invalidateOptionsMenu();

                    //Calculates new total
                    app.cartTotal();
                }
            });
        }

    }

    public void checkForAllergies(View view) {
        int id = recyclerView.getChildLayoutPosition(view);
        String matchedAllergies ="";
        String inputStr;
        int i = 0;
        while(i < allergies.getAllergies().size()) {
            try {
                String checkedAllergy = allergies.getAllergies().get(i);
                App app = App.getInstance();
                inputStr = app.dataFutomaki.get(id).getAllergies().toLowerCase();
                if (inputStr.contains(checkedAllergy)) {
                    if(matchedAllergies.equals("")){
                        matchedAllergies = matchedAllergies +   "     - "+checkedAllergy;
                    }
                    else if (matchedAllergies.length()>1){
                        matchedAllergies = matchedAllergies + "\n" + "     - "+ checkedAllergy;
                    }

                }
            }
            catch (IndexOutOfBoundsException e)  // CS0168
            {

            }
            i++;
        }
        containsAllergies=false;
        if(matchedAllergies.length()>0) {
            allergyAlert ="Denne ret indeholder:\n" +matchedAllergies;
            containsAllergies=true;
        }
    }
}
