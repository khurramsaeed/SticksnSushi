package com.company.sticksnsushi.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.infrastructure.App;
import com.company.sticksnsushi.infrastructure.Categories;

import java.util.ArrayList;

/**
 * Created by Nikolaj on 30-10-2017.
 */

    public class MakiFragment extends BaseFragment {

    // For debugging purposes
    private static final String TAG = "MakiFragment";

    App app = App.getInstance();
    private RecyclerView recyclerView;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("MAKI");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_maki, container, false);
        rootView.setTag(TAG);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewMaki);

        // setLayoutManager is required in RecyclerView - GridLayout is used with 2 rows.
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Intantiating Adapter.
        CustomDataAdapter adapter = new CustomDataAdapter();

        // Add dataCategories to my adapter
        for (int i = 0; i < app.dataMakiCategories.size(); i++) {
            adapter.addItem(app.dataMakiCategories.get(i));
        }
        recyclerView.setAdapter(adapter);


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
        public DataListViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_maki_item, parent, false);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = recyclerView.getChildLayoutPosition(view);
                    if(id==0) {
                        FragmentManager fm = getChildFragmentManager();
                        Fragment newFragment = new UramakiFragment();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_container_maki, newFragment).addToBackStack("Maki").commit();
                    }

                    else if(id==1) {
                        FragmentManager fm = getChildFragmentManager();
                        Fragment newFragment = new KaburimakiFragment();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_container_maki, newFragment).addToBackStack("Maki").commit();
                    }

                    else if(id==2) {
                        FragmentManager fm = getChildFragmentManager();
                        Fragment newFragment = new FutomakiFragment();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_container_maki, newFragment).addToBackStack("Maki").commit();
                    }
                    else if(id==3) {
                        FragmentManager fm = getChildFragmentManager();
                        Fragment newFragment = new HosomakiFragment();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_container_maki, newFragment).addToBackStack("Maki").commit();
                    }

                }
            });

            return new DataListViewHolder(view);
        }


        @Override
        public void onBindViewHolder(DataListViewHolder holder, int position) {
            Categories item = app.dataMakiCategories.get(position);

            holder.title.setText(item.getItemName());
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
        private TextView title;
        private ImageView image;

        public DataListViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.maki_item_name);
            image = itemView.findViewById(R.id.maki_item_image);
        }

    }

}

