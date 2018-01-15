package com.company.sticksnsushi.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.activities.SpecificDishActivity;
import com.company.sticksnsushi.infrastructure.App;
import com.company.sticksnsushi.infrastructure.Item;

import java.util.ArrayList;

/**
 * Created by Nikolaj on 27-11-2017.
 */

public class MenuerFragment extends BaseFragment {

    // For debugging purposes
    private static final String TAG = "MenuerFragment";

    private App app = App.getInstance();
    private RecyclerView recyclerView;
    private Item item;
    private AllergiesFragment allergies = new AllergiesFragment();
    private String allergyAlert;
    boolean containsAllergies = false;
    private ImageButton addToBasket;

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

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewMenuer);

        // Different layout configurations for landscape/portrait mode
        if (isTablet && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        } else if (isTablet && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else if (!isTablet && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        }

        // Intantiating Adapter.
        CustomDataAdapter adapter = new CustomDataAdapter();

        // Add dataCategories to my adapter
        for (int i = 0; i < app.dataMenuer.size(); i++) {
            adapter.addItem(app.dataMenuer.get(i));
        }
        recyclerView.setAdapter(adapter);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        allergies.markAllergies(sp);

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
                    int id = recyclerView.getChildLayoutPosition(view);
                    String category = app.dataMenuer.get(id).getCategory();
                    checkForAllergies(view);
                    Intent menuerIntent=new Intent(getContext(), SpecificDishActivity.class);
                    menuerIntent.putExtra("Category", category);
                    menuerIntent.putExtra("ID", id);
                    menuerIntent.putExtra("AllergiesBoolean", containsAllergies);
                    if(containsAllergies){
                        menuerIntent.putExtra("AllergiesAlert", allergyAlert);
                    }

                    startActivity(menuerIntent);
                }
            });

            return new DataListViewHolder(view);
        }



        @Override
        public void onBindViewHolder(DataListViewHolder holder, int position) {
            item = app.dataMenuer.get(position);

            holder.title.setText(item.getItemName());
            holder.price.setText(item.getPrice() + " kr.");
            holder.pcs.setText("/"+item.getItemPCS());
            holder.image.setImageBitmap(item.getItemImage());

        }

//        @Override
//        public void onBindViewHolder(TakeAwayFragment.DataListViewHolder holder, int position) {
//            Categories item = App.dataCategories.get(position);
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
            addToBasket = itemView.findViewById(R.id.menuer_item_addToBasket);

            addToBasket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int quantity=app.dataMenuer.get(getAdapterPosition()).getQuantity();
                    int index = getAdapterPosition();
                    app.getCart().addItem(app.dataMenuer.get(index));
                    quantity++;
                    app.dataMenuer.get(index).setQuantity(quantity);

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
                inputStr = app.dataMenuer.get(id).getAllergies().toLowerCase();
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

