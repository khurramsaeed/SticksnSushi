package com.company.sticksnsushi.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.infrastructure.SticksnSushiApplication;

/**
 * Created by 1234 on 03-01-2018.
 */

public class SpecificDishActivity extends BaseActivity implements View.OnClickListener {
    private SticksnSushiApplication app = SticksnSushiApplication.getInstance();

    private TextView itemName, itemPrice, itemDesc, allergies;
    private String pcs;
    private ImageView itemImage;
    private Button addToBasket;
    private String category;
    private int i;

    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_specific_dish);
        i = getIntent().getIntExtra("ID",0);
        category = getIntent().getStringExtra("Category");

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

        itemName = findViewById(R.id.dishTitle);
        itemPrice = findViewById(R.id.dishPrice);
        itemDesc = findViewById(R.id.dishDisciption);
        itemImage = findViewById(R.id.dishImage);
        allergies = findViewById(R.id.dishAllergies);

        if(category.equals("Starters")) {
            itemName.setText(app.dataStarters.get(i).getItemName());
            pcs = app.dataStarters.get(i).getItemPCS();
            itemPrice.setText(Integer.toString(app.dataStarters.get(i).getPrice()) + " kr./ " + pcs);
            itemDesc.setText(app.dataStarters.get(i).getItemDescription());
            allergies.setText(app.dataStarters.get(i).getAllergies());
            itemImage.setImageBitmap(app.dataStarters.get(i).getItemImage());
        }
        else if (category.equals("Menuer")){
            itemName.setText(app.dataMenuer.get(i).getItemName());
            pcs = app.dataMenuer.get(i).getItemPCS();
            itemPrice.setText(Integer.toString(app.dataMenuer.get(i).getPrice()) + " kr./ " + pcs);
            itemDesc.setText(app.dataMenuer.get(i).getItemDescription());
            allergies.setText(app.dataMenuer.get(i).getAllergies());
            itemImage.setImageBitmap(app.dataMenuer.get(i).getItemImage());
        }

        else if (category.equals("Kids")){
            itemName.setText(app.dataKids.get(i).getItemName());
            pcs = app.dataKids.get(i).getItemPCS();
            itemPrice.setText(Integer.toString(app.dataKids.get(i).getPrice()) + " kr./ " + pcs);
            itemDesc.setText(app.dataKids.get(i).getItemDescription());
            allergies.setText(app.dataKids.get(i).getAllergies());
            itemImage.setImageBitmap(app.dataKids.get(i).getItemImage());
        }

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
            Toast.makeText(this, "Tilføjet til kurven", Toast.LENGTH_SHORT).show();
            if(category.equals("Starters")) {app.getCart().addItem(app.dataStarters.get(i));}
            else if (category.equals("Menuer")){app.getCart().addItem(app.dataMenuer.get(i));}
            else if (category.equals("Kids")){ app.getCart().addItem(app.dataKids.get(i));
            }
        }
    }

}
