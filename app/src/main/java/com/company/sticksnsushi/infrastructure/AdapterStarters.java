package com.company.sticksnsushi.infrastructure;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.sticksnsushi.R;

/**
 * Created by Nikolaj on 25-11-2017.
 */

public class AdapterStarters extends RecyclerView.Adapter<AdapterStarters.ViewHolder> {
    private static final String TAG = "AdapterStarters";

    private String[] title;
    private String[] description;
    private String[] pcs;
    private String [] price;
    private Object[] image;


    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName, textViewDescription, textViewPCS, textViewPrice;
        private final ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            textViewName = v.findViewById(R.id.starters_item_name);
            textViewDescription = v.findViewById(R.id.starters_item_description);
            textViewPCS = v.findViewById(R.id.starters_item_pcs);
            textViewPrice = v.findViewById(R.id.starters_item_price);
            imageView = (ImageView) v.findViewById(R.id.starters_item_image);
        }

        public TextView getTextViewName() {
            return textViewName;
        }
        public TextView getTextViewDescription() {
            return textViewDescription;
        }
        public TextView getTextViewPCS() {
            return textViewPCS;
        }
        public TextView getTextViewPrice() {
            return textViewPrice;
        }

        public ImageView getImageView() { return imageView; }
    }
    // END_INCLUDE(recyclerViewSampleViewHolder)

    /**
     * Initialize the dataset of the Adapter.
     * @param title
     * @param description
     * @param pcs
     * @param price
     * @param image
     */
    public AdapterStarters(String[] title, String[] description, String[] pcs, String[] price, Object[] image) {
        this.title= title;
        this.description= description;
        this.pcs= pcs;
        this.price= price;
        this.image = image;

    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_starters_item, viewGroup, false);

        return new ViewHolder(v);
    }

    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.getTextViewName().setText(title[position]);
        viewHolder.getTextViewDescription().setText(description[position]);
        viewHolder.getTextViewPCS().setText(pcs[position]);
        viewHolder.getTextViewPrice().setText(price[position]);
        viewHolder.getImageView().setImageBitmap((Bitmap) image[position]);
    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return title.length;
    }
}

