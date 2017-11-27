package com.company.sticksnsushi.infrastructure;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

import com.company.sticksnsushi.BuildConfig;
import com.company.sticksnsushi.R;
import com.company.sticksnsushi.library.NetworkStatus;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Khurram Saeed Malik on 09/10/2017.
 */

public class SticksnSushiApplication extends Application {
    private static final String TAG = "SticksnSushiApplication";

    public static SticksnSushiApplication instance;
    public static SharedPreferences prefs;
    public static ConnectivityManager connectivityManager;
    public static String versionName = BuildConfig.VERSION_NAME;
    public static Handler mainThread;
    public static Resources res;
    public static NetworkStatus network;

    private Auth auth;
    private User user;
    public static ArrayList<Categories> dataCategories;
    public static ArrayList<Item> dataStarters;
    public static ArrayList<Item> dataKids;
    public static ArrayList<Item> dataMenuer;

    @Override
    public void onCreate() {

        Log.d(TAG, "onCreate: Auth(context), User(), retrieveListView() called");
        super.onCreate();
        instance = this;
        //mainThread = new Handler();
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        res = SticksnSushiApplication.instance.getResources();
        network = new NetworkStatus();

        auth = new Auth(this);
        user = new User();
        dataCategories = new ArrayList<>();
        dataStarters = new ArrayList<>();
        dataKids = new ArrayList<>();
        dataMenuer = new ArrayList<>();

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
            InputStream inputStream = getResources().openRawResource(R.raw.data_backend);

            byte bytes[] = new byte[inputStream.available()]; // kun små filer
            inputStream.read(bytes);
            String data = new String(bytes, "UTF-8");

            JSONObject json = new JSONObject(data);


            JSONArray categories = json.getJSONArray("categories");
            JSONArray starters = json.getJSONArray("starters");
            JSONArray kids = json.getJSONArray("kids");
            JSONArray menuer = json.getJSONArray("menuer");

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

                dataCategories.add(new Categories(title, itemImage));

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

                dataStarters.add(new Item(id, price, title, PCS, description, category, itemImage));
            }

            //Data for kids
            int numberKids = kids.length();
            for (int i = 0; i < numberKids; i++) {
                JSONObject kid = kids.getJSONObject(i);
                System.err.println("obj = " + kid);

                // Get id, price, title, PCS, description, category and imageName from JSON-file
                int price = kid.getInt("price");
                String title = kid.getString("title");
                String PCS = kid.getString("pcs");
                String description = (String) kid.get("description");
                String imageName = kid.getString("imageName");

                // resId gets image resource with its identifier (image_name)
                int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                // Convert resId to BitMap
                Bitmap itemImage = BitmapFactory.decodeResource(getResources(), resId);
                // TODO: 27-11-2017 Item entity: update constructor id
                dataKids.add(new Item(0, price, title, PCS, description, "", itemImage));
            }

            //Data for menuer
            int numberMenuer = menuer.length();
            for (int i = 0; i < numberMenuer; i++) {
                JSONObject menu = menuer.getJSONObject(i);
                System.err.println("obj = " + menu);

                // Get id, price, title, PCS, description, category and imageName from JSON-file
                int price = menu.getInt("price");
                String title = menu.getString("title");
                String PCS = menu.getString("pcs");
                String description = (String) menu.get("description");
                String imageName = menu.getString("imageName");

                // resId gets image resource with its identifier (image_name)
                int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                // Convert resId to BitMap
                Bitmap itemImage = BitmapFactory.decodeResource(getResources(), resId);
                // TODO: 27-11-2017 Item entity: update constructor id
                dataMenuer.add(new Item(0, price, title, PCS, description, "", itemImage));
            }


        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }

    }
}
