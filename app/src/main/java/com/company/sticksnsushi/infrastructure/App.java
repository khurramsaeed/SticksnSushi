package com.company.sticksnsushi.infrastructure;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.company.sticksnsushi.BuildConfig;
import com.company.sticksnsushi.R;
import com.company.sticksnsushi.library.NetworkStatus;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Khurram Saeed Malik on 09/10/2017.
 */

public class App extends Application {
    private static final String TAG = "App";

    private static App instance;

    public static SharedPreferences prefs;
    public static ConnectivityManager connectivityManager;
    public static String versionName = BuildConfig.VERSION_NAME;
    public static Handler foregroundThread;
    public static Resources res;
    public static FirebaseAuth firebaseAuth;
    public static FirebaseUser currentUser;
    public static ArrayList<Cart> orders = new ArrayList<>();

    public static NetworkStatus network;

    private Auth auth;
    private Cart cart;
    public static int total = 0;

    public ArrayList<Categories> dataCategories = new ArrayList<>();
    public ArrayList<Categories> dataMakiCategories = new ArrayList<>();
    public ArrayList<Item> dataStarters = new ArrayList<>();
    public ArrayList<Item> dataUramaki = new ArrayList<>();
    public ArrayList<Item> dataFutomaki = new ArrayList<>();
    public ArrayList<Item> dataHosomaki = new ArrayList<>();
    public ArrayList<Item> dataKaburimaki = new ArrayList<>();
    public ArrayList<Item> dataKids = new ArrayList<>();
    public ArrayList<Item> dataMenuer = new ArrayList<>();

    @Override
    public void onCreate() {

        Log.d(TAG, "onCreate: Auth(context), Cart(), User(), retrieveJSONData() called");
        super.onCreate();
        CrashlyticsCore core = new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build();
        Fabric.with(this, new Crashlytics.Builder().core(core).build());
        instance = this;

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        res = getInstance().getResources();
        foregroundThread = new Handler();
        network = new NetworkStatus();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        auth = new Auth(this);
        cart = new Cart();

        registerReceiver(network, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        network.onReceive(this, null); // Update network status

        retrieveJSONData();
    }

    public static App getInstance() {
        return instance;
    }

    public Cart getCart() {
        return cart;
    }

    public Auth getAuth() {
        return auth;
    }

    public void cartTotal() {
        total=0;
        for (int i=0; i < getCart().getItems().size(); i++) {
            total = total + getCart().getItems().get(i).getItemTotal();
        }
    }


    public void getPreviousOrders(DatabaseReference reference) {
        if (getAuth().getUser().getId() == null) {return;}
        reference.child("users").child(getAuth().getUser().getId()).child("orders").getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Cart cart = snapshot.getValue(Cart.class);
                    Log.d(TAG, "Value is: " + cart.toString());
                    orders.add(cart);
                    System.out.println("Number of orders: "+orders.size());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());
                shortToastMessage("Der opstod en fejl i hentning af ordrer");
            }
        });
    }

    /***
     * Gets rows from JSON and puts in ArrayList, HashMap
     * afterwards SimpleAdapter is used to create list item views from item
     */
    public void retrieveJSONData() {
        dataMenuer.clear();
        dataCategories.clear();
        dataKids.clear();
        dataStarters.clear();
        dataMakiCategories.clear();
        dataUramaki.clear();
        dataFutomaki.clear();
        dataHosomaki.clear();
        dataKaburimaki.clear();

        try {
            InputStream inputStream = getResources().openRawResource(R.raw.data_backend);

            byte bytes[] = new byte[inputStream.available()];
            inputStream.read(bytes);
            String data = new String(bytes, "UTF-8");

            JSONObject json = new JSONObject(data);

            // JSONArray variables
            JSONArray categories = json.getJSONArray("categories");
            JSONArray makiCategories = json.getJSONArray("makiCategories");
            JSONArray starters = json.getJSONArray("starters");
            JSONArray kids = json.getJSONArray("kids");
            JSONArray menuer = json.getJSONArray("menuer");
            JSONArray uramakis = json.getJSONArray("uramaki");
            JSONArray kaburimakis = json.getJSONArray("kaburimaki");
            JSONArray futomakis = json.getJSONArray("futomaki");
            JSONArray hosomakis = json.getJSONArray("hosomaki");

            //Data for categories
            int number = categories.length();
            for (int i = 0; i < number; i++) {
                JSONObject category = categories.getJSONObject(i);

                // Get title and imageName from JSON-file
                String title = category.getString("title");
                String imageName = category.getString("imageName");
                int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());// resId gets image resource with its identifier (image_name)
                Bitmap itemImage = BitmapFactory.decodeResource(getResources(), resId);// Convert resId to BitMap

                dataCategories.add(new Categories(title, itemImage));
            }

            //Data for makiCategories
            int numberMaki = makiCategories.length();
            for (int i = 0; i < numberMaki; i++) {
                JSONObject makiCategory = makiCategories.getJSONObject(i);

                // Get title and imageName from JSON-file
                String title = makiCategory.getString("title");
                String imageName = makiCategory.getString("imageName");
                int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                Bitmap itemImage = BitmapFactory.decodeResource(getResources(), resId);

                dataMakiCategories.add(new Categories(title, itemImage));
            }

            //Data for starters
            int numberStarters = starters.length();
            for (int i = 0; i < numberStarters; i++) {
                JSONObject starter = starters.getJSONObject(i);

                // Get id, price, title, PCS, description, category and imageName from JSON-file
                int id = starter.getInt("id");
                int price = starter.getInt("price");
                String title = starter.getString("title");
                String PCS = starter.getString("pcs");
                String description = (String) starter.get("description");
                String category = starter.getString("category");
                String imageName = starter.getString("imageName");

                String allergies=starter.getJSONArray("allergies").toString();
                allergies = allergies.replace("[", "");
                allergies = allergies.replace("]", "");
                allergies = allergies.replace("\"", "");
                allergies = allergies.replace(",", ", ");

                int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                Bitmap itemImage = BitmapFactory.decodeResource(getResources(), resId);

                dataStarters.add(new Item(id, price, title, PCS, description, category, allergies, itemImage));
            }

            //Data for kids
            int numberKids = kids.length();
            for (int i = 0; i < numberKids; i++) {
                JSONObject kid = kids.getJSONObject(i);

                // Get id, price, title, PCS, description, category and imageName from JSON-file
                int price = kid.getInt("price");
                String title = kid.getString("title");
                String PCS = kid.getString("pcs");
                String description = (String) kid.get("description");
                String category = kid.getString("category");
                String imageName = kid.getString("imageName");

                String allergies=kid.getJSONArray("allergies").toString();
                allergies = allergies.replace("[", "");
                allergies = allergies.replace("]", "");
                allergies = allergies.replace("\"", "");
                allergies = allergies.replace(",", ", ");

                int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                Bitmap itemImage = BitmapFactory.decodeResource(getResources(), resId);
                // TODO: 27-11-2017 Item entity: update constructor id
                dataKids.add(new Item(0, price, title, PCS, description, category,allergies, itemImage));
            }

            //Data for menuer
            int numberMenuer = menuer.length();
            for (int i = 0; i < numberMenuer; i++) {
                JSONObject menu = menuer.getJSONObject(i);

                // Get id, price, title, PCS, description, category and imageName from JSON-file
                int price = menu.getInt("price");
                String title = menu.getString("title");
                String PCS = menu.getString("pcs");
                String category = menu.getString("category");
                String description = (String) menu.get("description");
                String imageName = menu.getString("imageName");

                String allergies=menu.getJSONArray("allergies").toString();
                allergies = allergies.replace("[", "");
                allergies = allergies.replace("]", "");
                allergies = allergies.replace("\"", "");
                allergies = allergies.replace(",", ", ");

                int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                Bitmap itemImage = BitmapFactory.decodeResource(getResources(), resId);
                // TODO: 27-11-2017 Item entity: update constructor id
                dataMenuer.add(new Item(0, price, title, PCS, description, category, allergies, itemImage));
            }

            //Data for uramaki
            int numberUramaki = uramakis.length();
            for (int i = 0; i < numberUramaki; i++) {
                JSONObject uramaki = uramakis.getJSONObject(i);

                // Get id, price, title, PCS, description, category and imageName from JSON-file
                //int id = uramaki.getInt("id");
                int price = uramaki.getInt("price");
                String title = uramaki.getString("title");
                String PCS = uramaki.getString("pcs");
                String description = (String) uramaki.get("description");
                String category = uramaki.getString("category");
                String imageName = uramaki.getString("imageName");

                String allergies=uramaki.getJSONArray("allergies").toString();
                allergies = allergies.replace("[", "");
                allergies = allergies.replace("]", "");
                allergies = allergies.replace("\"", "");
                allergies = allergies.replace(",", ", ");

                int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                Bitmap itemImage = BitmapFactory.decodeResource(getResources(), resId);

                dataUramaki.add(new Item(0, price, title, PCS, description, category, allergies, itemImage));
            }

            //Data for kaburimaki
            int numberKaburimaki = kaburimakis.length();
            for (int i = 0; i < numberKaburimaki; i++) {
                JSONObject kaburimaki = kaburimakis.getJSONObject(i);

                // Get id, price, title, PCS, description, category and imageName from JSON-file
                //int id = kaburimaki.getInt("id");
                int price = kaburimaki.getInt("price");
                String title = kaburimaki.getString("title");
                String PCS = kaburimaki.getString("pcs");
                String description = (String) kaburimaki.get("description");
                String category = kaburimaki.getString("category");
                String imageName = kaburimaki.getString("imageName");

                String allergies=kaburimaki.getJSONArray("allergies").toString();
                allergies = allergies.replace("[", "");
                allergies = allergies.replace("]", "");
                allergies = allergies.replace("\"", "");
                allergies = allergies.replace(",", ", ");

                int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                Bitmap itemImage = BitmapFactory.decodeResource(getResources(), resId);

                dataKaburimaki.add(new Item(0, price, title, PCS, description, category, allergies, itemImage));
            }

            //Data for futomaki
            int numberFutomaki = futomakis.length();
            for (int i = 0; i < numberFutomaki; i++) {
                JSONObject futomaki = futomakis.getJSONObject(i);

                // Get id, price, title, PCS, description, category and imageName from JSON-file
                //int id = futomaki.getInt("id");
                int price = futomaki.getInt("price");
                String title = futomaki.getString("title");
                String PCS = futomaki.getString("pcs");
                String description = (String) futomaki.get("description");
                String category = futomaki.getString("category");
                String imageName = futomaki.getString("imageName");

                String allergies=futomaki.getJSONArray("allergies").toString();
                allergies = allergies.replace("[", "");
                allergies = allergies.replace("]", "");
                allergies = allergies.replace("\"", "");
                allergies = allergies.replace(",", ", ");

                int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                Bitmap itemImage = BitmapFactory.decodeResource(getResources(), resId);

                dataFutomaki.add(new Item(0, price, title, PCS, description, category, allergies, itemImage));
            }

            //Data for hosomaki
            int numberHosomaki = hosomakis.length();
            for (int i = 0; i < numberHosomaki; i++) {
                JSONObject hosomaki = hosomakis.getJSONObject(i);

                // Get id, price, title, PCS, description, category and imageName from JSON-file
                //int id = hosomaki.getInt("id");
                int price = hosomaki.getInt("price");
                String title = hosomaki.getString("title");
                String PCS = hosomaki.getString("pcs");
                String description = (String) hosomaki.get("description");
                String category = hosomaki.getString("category");
                String imageName = hosomaki.getString("imageName");

                String allergies=hosomaki.getJSONArray("allergies").toString();
                allergies = allergies.replace("[", "");
                allergies = allergies.replace("]", "");
                allergies = allergies.replace("\"", "");
                allergies = allergies.replace(",", ", ");

                int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                Bitmap itemImage = BitmapFactory.decodeResource(getResources(), resId);

                dataHosomaki.add(new Item(0, price, title, PCS, description, category, allergies, itemImage));
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }

    }


    /**
     * toastMessage to make toast short
     * @param message - String resource
     */
    public void shortToastMessage(final String message){
        foregroundThread.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getInstance(), message, Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void longToastMessage(final String message){
        foregroundThread.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getInstance(), message, Toast.LENGTH_LONG).show();
            }
        });

    }



}
