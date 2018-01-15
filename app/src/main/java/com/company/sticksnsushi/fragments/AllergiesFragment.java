package com.company.sticksnsushi.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.activities.NavDrawerActivity;
import com.company.sticksnsushi.library.SupportPreferenceFragment;

import java.util.ArrayList;

/**
 * Created by Khurram Saeed Malik on 02/11/2017.
 */

public class AllergiesFragment extends BaseFragment {
    public static ArrayList<String> allergyList = new ArrayList<String>();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sidebar_item_allergies, container, false);

        getActivity().setTitle("Allergener");
        getFragmentManager().beginTransaction().replace(R.id.sidebar_allergies, new AllergyScreen()).commit();

        return view;
    }
    /**
     * Back button override for Fragment
     * Backs up to NavdrawerActivity
     */
    @Override
    public void onResume() {
        super.onResume();
        if(getView() == null){
            return;
        }
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    ((NavDrawerActivity)getActivity()).navigationView.setCheckedItem(R.id.item_takeaway);
                    ((NavDrawerActivity)getActivity()).displaySelectedItem(R.id.item_takeaway);
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Sets preference screen as layout
     */
    public static class AllergyScreen extends SupportPreferenceFragment {
        public  void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.allergies);
        }
    }

    public ArrayList<String> getAllergies(){
        return allergyList;
    }

    public void markAllergies(SharedPreferences sp) {
        allergyList.clear();
        boolean allergiesSkaldyr = sp.getBoolean("allergies_skaldyr", false);
            if (allergiesSkaldyr) {
                allergyList.add("skaldyr");
            }
            boolean allergiMaelk = sp.getBoolean("allergies_maelk", false);
            if (allergiMaelk) {
                allergyList.add("mælk");
            }
            boolean allergiBloeddyr = sp.getBoolean("allergies_bloeddyr", false);
            if (allergiBloeddyr) {
            allergyList.add("bløddyr");
            }
            boolean allergiFisk = sp.getBoolean("allergies_fisk", false);
            if (allergiFisk) {
            allergyList.add("fisk");
            }
            boolean allergiGluten = sp.getBoolean("allergies_kornsorter", false);
            if (allergiGluten) {
            allergyList.add("gluten");
            }
            boolean allergiLupin = sp.getBoolean("allergies_lupin", false);
            if (allergiLupin) {
            allergyList.add("lupin");
            }
            boolean allergiNoedder = sp.getBoolean("allergies_noedder", false);
            if (allergiNoedder) {
            allergyList.add("nødder");
            }
            boolean allergiPeanuts = sp.getBoolean("allergies_peanuts", false);
            if (allergiPeanuts) {
            allergyList.add("peanuts");
            }
            boolean allergiSelleri = sp.getBoolean("allergies_selleri", false);
            if (allergiSelleri) {
            allergyList.add("selleri");
            }
            boolean allergiSennep = sp.getBoolean("allergies_sennep", false);
            if (allergiSennep) {
            allergyList.add("sennep");
            }
            boolean allergiSesam = sp.getBoolean("allergies_sesam", false);
            if (allergiSesam) {
            allergyList.add("sesam");
            }
            boolean allergiSoya = sp.getBoolean("allergies_soya", false);
            if (allergiSoya) {
            allergyList.add("soya");
            }
            boolean allergiSulfitter = sp.getBoolean("allergies_sulfitter", false);
            if (allergiSulfitter) {
            allergyList.add("sulfitter");
            }
            boolean allergiAeg = sp.getBoolean("allergies_aeg", false);
            if (allergiAeg) {
            allergyList.add("æg");
            }

    }

}