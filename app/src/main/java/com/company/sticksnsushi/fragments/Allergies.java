package com.company.sticksnsushi.fragments;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.sticksnsushi.R;

/**
 * Created by Khurram Saeed Malik on 02/11/2017.
 */

public class Allergies extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sidebar_item_allergies, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Allergies");

        getFragmentManager().beginTransaction().replace(R.id.sidebar_allergies, new AllergyScreen()).commit();
    }

    public static class AllergyScreen extends com.company.sticksnsushi.fragments.PreferenceFragment{
        public  void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.allergies);
        }
    }
}