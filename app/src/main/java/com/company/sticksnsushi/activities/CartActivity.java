package com.company.sticksnsushi.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.infrastructure.Item;
import com.company.sticksnsushi.infrastructure.SticksnSushiApplication;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Swagam on 08/01/2018.
 */

public class CartActivity extends BaseActivity {

    SticksnSushiApplication app = SticksnSushiApplication.getInstance();

    private CartAdapter adapter;
    private ArrayList<Item> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedState) {

        super.onCreate(savedState);

        setContentView(R.layout.activity_cart);

        Toolbar toolbar = (Toolbar) findViewById(R.id.include_toolbar);
        setSupportActionBar(toolbar);
        // Back button on Toolbar
        if (getSupportActionBar() != null) {
            toolbar.setTitle("Bestilling");
            toolbar.setNavigationIcon(R.drawable.arrow_left);
        }

        retrieveListView();
    }

    public void startCheckoutActivity(View view) { startActivity(new Intent(this, CheckoutActivity.class));  }
    /**
     * Clears Cart Menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        return true;
    }

    /**
     * Effects back button in current activity
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


    private void retrieveListView(){


        for (int i =0; i < app.getCart().getItems().size(); i++) {
            data.add(app.getCart().getItems().get(i));
        }
        adapter = new CartAdapter(this, data);

        ListView listView = findViewById(R.id.activity_cart_listView);
        listView.setAdapter(adapter);

    }

    public class CartAdapter extends ArrayAdapter<Item> {


        public CartAdapter(@NonNull Context context, ArrayList<Item> itemArrayList) {
            super(context, 0, itemArrayList);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            Item item = getItem(position);
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.activity_cart_items, viewGroup, false);
            }

            TextView itemName = (TextView) view.findViewById(R.id.cart_itemName);
            TextView itemQuantity = (TextView) view.findViewById(R.id.cart_itemAmount);
            TextView pricePrItem = (TextView) view.findViewById(R.id.cart_pricePrItem);
            TextView priceTotal = (TextView)  view.findViewById(R.id.cart_priceTotal);

            ImageView itemImage = (ImageView) view.findViewById(R.id.cart_itemImage);

            itemName.setText(item.getItemName().toString());
            // TODO: 09/01/2018 Mangler Quantity
            itemQuantity.setText(""+item.getId());
            pricePrItem.setText(""+item.getPrice());
            priceTotal.setText(""+item.getId() * item.getPrice());
            itemImage.setImageBitmap(item.getItemImage());


            return view;

        }


    }

}
