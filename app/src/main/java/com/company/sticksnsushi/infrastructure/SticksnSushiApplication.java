package com.company.sticksnsushi.infrastructure;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.company.sticksnsushi.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Khurram Saeed Malik on 09/10/2017.
 */

public class SticksnSushiApplication extends Application {
    private static final String TAG = "SticksnSushiApplication";
    private Auth auth;
    private User user;
    public static ArrayList<Categories> data;
    public static ArrayList<Item> dataItem;

    @Override
    public void onCreate() {

        Log.d(TAG, "onCreate: Auth(context), User(), retrieveListView() called");
        super.onCreate();
        auth = new Auth(this);
        user = new User();
        data = new ArrayList<>();
        dataItem = new ArrayList<>();

        retrieveListView();
    }

    public Auth getAuth() {
        return auth;
    }

    /***
     * Gets rows from JSON and puts in ArrayList, HashMap
     * afterwards SimpleAdapter is used to create list item views from item
     */
    private void retrieveListView() {

        try {
            InputStream is = getResources().openRawResource(R.raw.data_categories);

            byte b[] = new byte[is.available()]; // kun små filer
            is.read(b);
            String str = new String(b, "UTF-8");

            InputStream ss = getResources().openRawResource(R.raw.data_starters);
            byte bs[] = new byte[ss.available()]; // kun små filer
            ss.read(bs);
            String st = new String(bs, "UTF-8");


            JSONObject json = new JSONObject(str);
            JSONObject jsonStart = new JSONObject(st);

            JSONArray categories = json.getJSONArray("categories");
            JSONArray starters = jsonStart.getJSONArray("starters");

            //Data for categories
            int number = categories.length();
            for (int i = 0; i < number; i++) {
                JSONObject category = categories.getJSONObject(i);
                System.err.println("obj = " + category);

                // Get title and imageName from JSON-file
                String title = category.getString("title");
                String imageName = category.getString("imageName");

                // resId gets image resource with its identifier (image_name)
                int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                // Convert resId to BitMap
                Bitmap itemImage = BitmapFactory.decodeResource(getResources(), resId);

                data.add(new Categories(title, itemImage));

            }

            //Data for starters
            int numberStarters = starters.length();
            for (int i = 0; i < numberStarters; i++) {
                JSONObject starter = starters.getJSONObject(i);
                System.err.println("obj = " + starter);

                // Get id, price, title, PCS, description, category and imageName from JSON-file
                int id = starter.getInt("id");
                int price = starter.getInt("price");
                String title = starter.getString("title");
                String PCS = starter.getString("pcs");
                String description = (String) starter.get("description");
                String category = starter.getString("category");
                String imageName = starter.getString("imageName");

                // resId gets image resource with its identifier (image_name)
                int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                // Convert resId to BitMap
                Bitmap itemImage = BitmapFactory.decodeResource(getResources(), resId);

                dataItem.add(new Item(id, price, title, PCS, description, category, itemImage));
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }

    }
}
