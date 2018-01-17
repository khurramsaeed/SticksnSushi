package com.company.sticksnsushi.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.infrastructure.App;

import java.util.Random;

/**
 * Created by Gruppe 23 on 03-01-2018.
 */

public class SpecificDishActivity extends BaseActivity implements View.OnClickListener {
    private App app = App.getInstance();

    private TextView recomItemName1, recomItemName2, recomItemName3;
    private ImageView recomItemImage1, recomItemImage2, recomItemImage3;
    private ImageView allergyAlertButton;
    private Button addToBasket;

    private String category, allergyAlert;
    private int recomID1, recomID2, recomID3;
    private int quantityStarters;
    private int quantityKids;
    private int quantityMenuer;
    private int indexKids;
    private int indexStarters;
    private int indexMenuer;


    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_specific_dish);

        indexKids = getIntent().getIntExtra("kidsID", 0);
        indexStarters = getIntent().getIntExtra("startersID", 0);
        indexMenuer = getIntent().getIntExtra("menuerID", 0);

        category = getIntent().getStringExtra("Category");
        boolean containsAlllergies = getIntent().getBooleanExtra("AllergiesBoolean", false);
        if(containsAlllergies){
            allergyAlert = getIntent().getStringExtra("AllergiesAlert");
            allergyAlertButton = findViewById(R.id.allergyAlert);
            allergyAlertButton.setVisibility(View.VISIBLE);
            allergyAlertButton.setOnClickListener(this);
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.include_toolbar);
        setSupportActionBar(toolbar);
        // Back button on Toolbar
        if (getSupportActionBar() != null){
            // TODO: 08/01/2018 GET ITEM NAME HERE
            toolbar.setTitle(category);
            toolbar.setNavigationIcon(R.drawable.arrow_left);
        }


        addToBasket = (Button) findViewById(R.id.addToBasket);
        addToBasket.setOnClickListener(this);

        TextView itemName = findViewById(R.id.dishTitle);
        TextView itemPrice = findViewById(R.id.dishPrice);
        TextView itemDesc = findViewById(R.id.dishDisciption);
        ImageView itemImage = findViewById(R.id.dishImage);
        TextView allergies = findViewById(R.id.dishAllergies);

        recomItemName1 = findViewById(R.id.recom_name1);
        recomItemImage1 = findViewById(R.id.recom_image1);
        recomItemImage1.setOnClickListener(this);

        recomItemName2 = findViewById(R.id.recom_name2);
        recomItemImage2 = findViewById(R.id.recom_image2);
        recomItemImage2.setOnClickListener(this);

        recomItemName3 = findViewById(R.id.recom_name3);
        recomItemImage3 = findViewById(R.id.recom_image3);
        recomItemImage3.setOnClickListener(this);


        String pcs;
        if(category.equals("Starters")) {
            itemName.setText(app.dataStarters.get(indexStarters).getItemName());
            pcs = app.dataStarters.get(indexStarters).getItemPCS();
            itemPrice.setText(Integer.toString(app.dataStarters.get(indexStarters).getPrice()) + " kr./ " + pcs);
            itemDesc.setText(app.dataStarters.get(indexStarters).getItemDescription());
            allergies.setText(app.dataStarters.get(indexStarters).getAllergies());
            itemImage.setImageBitmap(app.dataStarters.get(indexStarters).getItemImage());
        }
        else if (category.equals("Menuer")){
            itemName.setText(app.dataMenuer.get(indexMenuer).getItemName());
            pcs = app.dataMenuer.get(indexMenuer).getItemPCS();
            itemPrice.setText(Integer.toString(app.dataMenuer.get(indexMenuer).getPrice()) + " kr./ " + pcs);
            itemDesc.setText(app.dataMenuer.get(indexMenuer).getItemDescription());
            allergies.setText(app.dataMenuer.get(indexMenuer).getAllergies());
            itemImage.setImageBitmap(app.dataMenuer.get(indexMenuer).getItemImage());
        }

        else if (category.equals("Kids")){
            itemName.setText(app.dataKids.get(indexKids).getItemName());
            pcs = app.dataKids.get(indexKids).getItemPCS();
            itemPrice.setText(Integer.toString(app.dataKids.get(indexKids).getPrice()) + " kr./ " + pcs);
            itemDesc.setText(app.dataKids.get(indexKids).getItemDescription());
            allergies.setText(app.dataKids.get(indexKids).getAllergies());
            itemImage.setImageBitmap(app.dataKids.get(indexKids).getItemImage());
        }

        addRecom();
    }

    @Override
    public void onResume() {
        super.onResume();
        quantityStarters = app.dataStarters.get(indexStarters).getQuantity();
        quantityKids = app.dataKids.get(indexKids).getQuantity();
        quantityMenuer = app.dataMenuer.get(indexMenuer).getQuantity();
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


    @Override
    public void onClick(View view) {
        if (view == addToBasket) {
            if(category.equals("Starters")) {
                app.getCart().addItem(app.dataStarters.get(indexStarters));
                quantityStarters++;
                app.dataStarters.get(indexStarters).setQuantity(quantityStarters);
            }
            else if (category.equals("Menuer")){
                app.getCart().addItem(app.dataMenuer.get(indexMenuer));
                quantityMenuer++;
                app.dataMenuer.get(indexMenuer).setQuantity(quantityMenuer);
            }
            else if (category.equals("Kids")){
                app.getCart().addItem(app.dataKids.get(indexKids));
                quantityKids++;
                app.dataKids.get(indexKids).setQuantity(quantityKids);
            }
            app.shortToastMessage("Tilføjet til kurv");
            //Calculates new total
            app.cartTotal();
            invalidateOptionsMenu();

        }
        if (view==allergyAlertButton){
            showAlert();
        }
        if(view==recomItemImage1){
            String category = app.dataStarters.get(recomID1).getCategory();
            Intent myintent=new Intent(this, SpecificDishActivity.class);
            myintent.putExtra("Category", category);
            myintent.putExtra("ID", recomID1);
            myintent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(myintent);
        }
        if(view==recomItemImage2){
            String category = app.dataStarters.get(recomID2).getCategory();
            Intent myintent=new Intent(this, SpecificDishActivity.class);
            myintent.putExtra("Category", category);
            myintent.putExtra("ID", recomID2);
            myintent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(myintent);
        }
        if(view==recomItemImage3){
            String category = app.dataStarters.get(recomID3).getCategory();
            Intent myintent=new Intent(this, SpecificDishActivity.class);
            myintent.putExtra("Category", category);
            myintent.putExtra("ID", recomID3);
            myintent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(myintent);
        }
    }

    private void showAlert() {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        myAlert.setMessage(allergyAlert)
                .setPositiveButton("Forstået", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setTitle("Bemærk")
                .create();
        myAlert.show();
    }

    public void addRecom() {
        Random rand = new Random();
        int nextRec = 0;
        while (nextRec <=3) {
            if (nextRec==1) {
                recomID1 = rand.nextInt(5);
                while (recomID1 == indexStarters){
                    recomID1 = rand.nextInt(5);
                }
                recomItemName1.setText(app.dataStarters.get(recomID1).getItemName());
                recomItemImage1.setImageBitmap(app.dataStarters.get(recomID1).getItemImage());
            }
            else if (nextRec==2){
                recomID2=rand.nextInt(6)+ 6;
                while (recomID2 == indexStarters){
                    recomID2 = rand.nextInt(6)+ 6;
                }
                recomItemName2.setText(app.dataStarters.get(recomID2).getItemName());
                recomItemImage2.setImageBitmap(app.dataStarters.get(recomID2).getItemImage());
            }
            else if (nextRec==3){
                recomID3=rand.nextInt(4)+13;
                while (recomID3 == indexStarters){
                    recomID3 = rand.nextInt(4)+13;
                }
                recomItemName3.setText(app.dataStarters.get(recomID3).getItemName());
                recomItemImage3.setImageBitmap(app.dataStarters.get(recomID3).getItemImage());
            }

            nextRec++;
        }
    }

}
