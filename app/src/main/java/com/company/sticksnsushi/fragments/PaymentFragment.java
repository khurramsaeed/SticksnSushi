package com.company.sticksnsushi.fragments;



import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.company.sticksnsushi.R;

public class PaymentFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedState) {
        ViewGroup PaymentView = (ViewGroup) layoutInflater.inflate(R.layout.fragment_payment, container, false);
        setHasOptionsMenu(true);

        return PaymentView;

    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }
}

