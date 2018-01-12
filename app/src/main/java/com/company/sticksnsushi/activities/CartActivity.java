package com.company.sticksnsushi.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.infrastructure.Item;
import com.company.sticksnsushi.infrastructure.SticksnSushiApplication;
import com.company.sticksnsushi.library.NetworkStatus;

import java.util.ArrayList;

/**
 * Created by Swagam on 08/01/2018.
 */

public class CartActivity extends BaseActivity {

    SticksnSushiApplication app = SticksnSushiApplication.getInstance();
    private CartAdapter adapter;
    TextView priceTotal;

    @Override
    protected void onCreate(Bundle savedState) {

        super.onCreate(savedState);
        setContentView(R.layout.activity_cart);

        priceTotal = (TextView) findViewById(R.id.priceTotal);
        priceTotal.setText(""+ app.getCart().getTotal());

        Toolbar toolbar = (Toolbar) findViewById(R.id.include_toolbar);
        setSupportActionBar(toolbar);
        // Back button on Toolbar
        if (getSupportActionBar() != null) {
            toolbar.setTitle("Bestilling");
            toolbar.setNavigationIcon(R.drawable.arrow_left);
        }

        retrieveListView();
    }

    public void startCheckoutActivity(View view) {
        startActivity(new Intent(this, CheckoutActivity.class));
    }

    /**
     * Clears Cart Menu
     *
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
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void retrieveListView() {

        adapter = new CartAdapter(this, app.getCart().getItems());

        ListView listView = findViewById(R.id.activity_cart_listView);
        listView.setAdapter(adapter);

    }

    public class CartAdapter extends ArrayAdapter<Item> {


        public CartAdapter(@NonNull Context context, ArrayList<Item> itemArrayList) {
            super(context, 0, itemArrayList);
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            final Item item = getItem(position);
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.activity_cart_items, viewGroup, false);
            }

            TextView itemName = (TextView) view.findViewById(R.id.cart_itemName);
            TextView itemQuantity = (TextView) view.findViewById(R.id.cart_itemAmount);
            TextView pricePrItem = (TextView) view.findViewById(R.id.cart_pricePrItem);
            TextView itemTotal = (TextView) view.findViewById(R.id.cart_itemTotal);

            ImageView itemImage = (ImageView) view.findViewById(R.id.cart_itemImage);

            ImageButton plusQuantity = (ImageButton) view.findViewById(R.id.plus);
            final ImageButton minusQuantity = (ImageButton) view.findViewById(R.id.minus);


            itemName.setText(item.getItemName().toString());
            itemQuantity.setText("" + item.getQuantity());
            pricePrItem.setText(item.getPrice() + "kr./stk.");
            itemTotal.setText(item.getItemTotal() + "kr.");
            itemImage.setImageBitmap(item.getItemImage());
            priceTotal.setText(app.getCart().getTotal() + "");
            System.out.println(app.getCart().getTotal() + "");

            plusQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    item.setQuantity(item.getQuantity() + 1);
                    priceTotal.setText(""+app.getCart().getTotal());
                    notifyDataSetChanged();

                }
            });

            minusQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (item.getQuantity() > 1) {
                        item.setQuantity(item.getQuantity() - 1);
                        priceTotal.setText(""+app.getCart().getTotal());
                        notifyDataSetChanged();

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!isFinishing()) {
                                    new AlertDialog.Builder(CartActivity.this)
                                            .setTitle("Du er ved fjerne "+item.getItemName())
                                            .setMessage("Ønsker du at fjerne retten?")
                                            .setCancelable(true)
                                            .setNegativeButton("Nej", null)
                                            .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    app.getCart().getItems().remove(item);
                                                    notifyDataSetChanged();
                                                }
                                            }).show();
                                }
                            }
                        });
                    }
                }
            });
            return view;

        }


    }

}
