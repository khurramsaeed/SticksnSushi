package com.company.sticksnsushi.activities;

import android.content.Intent;
import android.os.Bundle;
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
    TextView title,price, decription, allergies;
    ImageView image;
    private Button addToBasket;
    String pcs;
    SticksnSushiApplication app = SticksnSushiApplication.getInstance();
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_specific_dish);
        Intent intent = getIntent();
        int id = getIntent().getIntExtra("ID",0);
        String category = getIntent().getStringExtra("Category");


        addToBasket = (Button) findViewById(R.id.addToBasket);
        addToBasket.setOnClickListener(this);

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

    @Override
    public void onClick(View view) {
        if (view == addToBasket) {
            // TODO: 08/01/2018 add item in basket
        }
    }
}
