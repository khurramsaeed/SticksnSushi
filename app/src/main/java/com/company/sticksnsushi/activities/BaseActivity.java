package com.company.sticksnsushi.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.infrastructure.App;
import com.company.sticksnsushi.infrastructure.BadgeDrawable;
import com.company.sticksnsushi.infrastructure.Item;
import com.company.sticksnsushi.infrastructure.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.company.sticksnsushi.infrastructure.App.firebaseAuth;

public abstract class BaseActivity extends AppCompatActivity implements Runnable {
    private static final String TAG = "Base";
    private MenuItem item;
    private ListView listView;
    private App app = App.getInstance();
    protected Toolbar toolbar;
    protected PopupCartAdapter adapter;
    protected boolean isTablet;
    public int itemsInCart;
    private FirebaseUser currentUser = app.firebaseAuth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        // This gets information about device screen size
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        isTablet = (metrics.widthPixels / metrics.density) >= 600;

        if (app.network.isOnline()) {getDetails();}
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        toolbar = (Toolbar) findViewById(R.id.include_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.menu);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
//        if(app.getAuth().getUser().isLoggedIn()){
            System.out.println("Bruger logget ind: " + currentUser);
            System.out.println("Bruger med email: " + currentUser.getEmail());
        } else {
            System.out.println("Bruger ikke logget ind");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cart_pop_up, menu);
        item = menu.findItem(R.id.cartPopUp);
        LayerDrawable icon = (LayerDrawable) item.getIcon();
        updateBadgeCount();
        setBadgeCount(getApplicationContext(), icon, "" + itemsInCart);
        return true;
    }

    @Override
    public void onResume() {
        invalidateOptionsMenu();
        super.onResume();
    }

    /**
     * Checks for boolean value becomes true
     * Then enables button input
     */
    public void updateBadgeCount() {
        int temp = 0;
        // Vi er for tidligt på den og Menuen er ikke oprettet endnu - der kommer et kald i forbindelse ned oprettelse af menuen
        if (item == null) return;
        LayerDrawable icon = (LayerDrawable) item.getIcon();
        itemsInCart = 0;
        if (temp != app.getCart().getItems().size()) {
            temp = app.getCart().getItems().size();

            //Iterate in list to get the correct item amount in Cart
            for (int i = 0; i < app.getCart().getItems().size(); i++) {
                itemsInCart = itemsInCart + app.getCart().getItems().get(i).getQuantity();
            }
            app.shortToastMessage(itemsInCart + "");
            setBadgeCount(getApplicationContext(), icon, "" + itemsInCart);
        }
    }

    private static void setBadgeCount(Context context, LayerDrawable icon, String count) {
        BadgeDrawable badge;
        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.cartPopUp:
                View menuItemView = findViewById(R.id.cartPopUp);
                PopupWindow popupwindow_obj = popupDisplay();
                popupwindow_obj.showAsDropDown(menuItemView, -40, 18);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * PopupWindow for Cart items
     * Shows Items in Cart as ListView
     *
     * @return PopUp
     */
    public PopupWindow popupDisplay() {

        final PopupWindow popupWindow = new PopupWindow(this);
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_cart, null);


        TextView priceTotal = view.findViewById(R.id.popup_cart_totalPrice);
        app.cartTotal();
        priceTotal.setText(app.total + " kr.");

        //Add cart data to listview
        listView = view.findViewById(R.id.popup_cart_listView);
        adapter = new PopupCartAdapter(this, app.getCart().getItems());
        listView.setAdapter(adapter);

        popupWindow.setFocusable(true);

        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);

        Button popupPayButton = view.findViewById(R.id.popupPayButton);
        popupPayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
                //Close popup window
                popupWindow.dismiss();
            }
        });
        return popupWindow;
    }


    /**
     * PopupCart Adapter - takes Item as ArrayAdapter
     * Shows Cart items & Updates if item are removed
     */
    public class PopupCartAdapter extends ArrayAdapter<Item> {

        public PopupCartAdapter(@NonNull Context context, ArrayList<Item> itemArrayList) {
            super(context, 0, itemArrayList);
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            Item item = getItem(position);
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.popup_cart_item, viewGroup, false);
            }

            TextView itemName = (TextView) view.findViewById(R.id.popup_itemName);
            TextView itemQuantity = (TextView) view.findViewById(R.id.popup_itemQuantity);

            itemName.setText(item.getItemName().toString());
            itemQuantity.setText(" x " + item.getQuantity());

            return view;

        }
    }

    @Override
    public void run() {

    }

    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    public void getDetails() {
        if (currentUser==null) {return;}
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid()).child("personal_details");
        // Read from 1the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                System.out.println("DATASNAPSHOT "+user.toString());
                app.getAuth().getUser().setDeliveryDetails(user.getAddress(), user.getCity(), user.getPhone(), user.getPostalNr());
                app.getAuth().getUser().setPersonalDetails(user.getId(), user.getDisplayName(), user.getEmail());
            }
            @Override
            public void onCancelled(DatabaseError error) {
            // Failed to read value
            Log.w(TAG, "Failed to read value.", error.toException());
            }
            });
    }

}
