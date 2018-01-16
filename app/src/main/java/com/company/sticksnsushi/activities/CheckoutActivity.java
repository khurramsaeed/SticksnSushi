package com.company.sticksnsushi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.infrastructure.App;
import com.company.sticksnsushi.infrastructure.Cart;
import com.company.sticksnsushi.infrastructure.StepperAdapter;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CheckoutActivity extends BaseActivity implements StepperLayout.StepperListener{

    private StepperLayout mStepperLayout;

    private App app = App.getInstance();
    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.include_toolbar);
        setSupportActionBar(toolbar);

        // Back button on Toolbar
        if (getSupportActionBar() != null) {
            toolbar.setTitle("Bestilling");
            toolbar.setNavigationIcon(R.drawable.arrow_left);
        }

        if (app.firebaseAuth.getCurrentUser() == null) {
            // TODO: 12-01-2018 data skal udfyldes
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();
        currentUser = app.firebaseAuth.getCurrentUser();

        mStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        mStepperLayout.setAdapter(new StepperAdapter(getSupportFragmentManager(), this));
        mStepperLayout.setListener(this);

    }



    private void completeOrder() {
        app.getCart().setOrderDate(new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date()));
        app.getCart().setTotal(app.total);
        //Resets quantity pr. item in cart after order is completed
        for (int i =0; i <app.getCart().getItems().size(); i++) {
            app.getCart().getItems().get(i).setItemImage(null);
        }
        databaseReference.child("users").child(app.getAuth().getUser().getId()).child("orders").push().setValue(app.getCart());
        app.longToastMessage("Bestilling gemt");
        app.retrieveJSONData();

    }

    @Override
    public void onCompleted(View completeButton) {
        if (!app.network.isOnline()) {
            app.shortToastMessage("Venligst forbinde enheden med nettet!");
            return;
        }
        
        if (currentUser==null) {
            app.shortToastMessage("Du kan ikke bestille uden logind");
            return;
        }

        completeOrder();

        // Reset/Empty cart & order
        app.orders.clear();
        Cart cart = app.getCart();
        for (int i =0; i <cart.getItems().size(); i++) {
            cart.getItems().get(i).resetQuantity();
        }
        cart.emptyCart();

        // Order Confirmation
        startActivity(new Intent(this, ConfirmationActivity.class));
        finish();
    }

    @Override
    public void onError(VerificationError verificationError) {

    }

    @Override
    public void onStepSelected(int newStepPosition) {

    }

    @Override
    public void onReturn() {
        finish();
    }


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


}