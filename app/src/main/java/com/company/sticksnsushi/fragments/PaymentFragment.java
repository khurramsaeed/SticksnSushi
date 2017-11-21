package com.company.sticksnsushi.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.company.sticksnsushi.R;
import com.craftman.cardform.CardForm;

/**
 * Created by Khurram Saeed Malik on 20/11/2017.
 */

public class PaymentFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedState) {
        ViewGroup PaymentView = (ViewGroup) layoutInflater.inflate(R.layout.fragment_payment, container, false);

        return PaymentView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardForm cardForm = (CardForm) view.findViewById(R.id.cardform);
        TextView txtDes = (TextView) view.findViewById(R.id.payment_amount);
        Button btnPay = (Button) view.findViewById(R.id.btn_pay);

        btnPay.setText("KØB");
        txtDes.setText("Beløb");

    }

}

