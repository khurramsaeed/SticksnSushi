package com.company.sticksnsushi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.sticksnsushi.R;

/**
 * Created by Nikolaj on 30-10-2017.
 */

    public class MakiFragment extends BaseFragment {

        @Override
        public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedState) {
            ViewGroup MakiView = (ViewGroup) layoutInflater.inflate(R.layout.fragment_maki, container, false);

            return MakiView;
        }
}
