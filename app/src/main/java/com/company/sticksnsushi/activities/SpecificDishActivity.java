package com.company.sticksnsushi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.infrastructure.SticksnSushiApplication;

/**
 * Created by 1234 on 03-01-2018.
 */

public class SpecificDishActivity extends BaseActivity{
    TextView title,price, decription, allergies;
    ImageView image;
    String pcs;
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_specific_dish);
        Intent intent = getIntent();
        int id = getIntent().getIntExtra("ID",0);
        String category = getIntent().getStringExtra("Category");


        SticksnSushiApplication app = SticksnSushiApplication.getInstance();

        title = findViewById(R.id.dishTitle);
        price = findViewById(R.id.dishPrice);
        decription = findViewById(R.id.dishDisciption);
        image = findViewById(R.id.dishImage);
        allergies = findViewById(R.id.dishAllergies);

        if(category.equals("Starters")) {
            title.setText(app.dataStarters.get(id).getItemName());
            pcs = app.dataStarters.get(id).getItemPCS();
            price.setText(Integer.toString(app.dataStarters.get(id).getPrice()) + " kr./ " + pcs);
            decription.setText(app.dataStarters.get(id).getItemDescription());
            allergies.setText(app.dataStarters.get(id).getAllergies());
            image.setImageBitmap(app.dataStarters.get(id).getItemImage());
        }

    }
}
