package com.company.sticksnsushi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.sticksnsushi.R;

/**
 * Created by Khurram Saeed Malik on 26/10/2017.
 */

public class StartersFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedState) {
        ViewGroup starterView = (ViewGroup) layoutInflater.inflate(R.layout.fragment_starters, container, false);

        return starterView;
    }
}
