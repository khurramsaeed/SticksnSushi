package com.company.sticksnsushi.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.library.SupportPreferenceFragment;

import java.util.ArrayList;

/**
 * Created by Khurram Saeed Malik on 02/11/2017.
 */

public class AllergiesFragment extends Fragment {
    public static ArrayList<String> allergyList = new ArrayList<String>();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sidebar_item_allergies, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Allergener");
        getFragmentManager().beginTransaction().replace(R.id.sidebar_allergies, new AllergyScreen()).commit();
        //markAllergies();



    }


    public static class AllergyScreen extends SupportPreferenceFragment {
        public  void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.allergies);

        }
    }

    public ArrayList<String> getAllergies(){
        return allergyList;
    }

    public void markAllergies(SharedPreferences sp){
        //sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean allergiBloeddyr = sp.getBoolean("allergies_skaldyr", false);
        allergyList.clear();
        if (allergiBloeddyr){
            allergyList.add("skaldyr");
            System.out.println(getAllergies());
        }
        boolean allergiMaelk = sp.getBoolean("allergies_maelk", false);
        if (allergiMaelk){
            allergyList.add("mælk");
            System.out.println(getAllergies());
        }
    }
}