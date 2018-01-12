package com.company.sticksnsushi.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.company.sticksnsushi.R;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

public class PaymentFragment extends Fragment implements Step {


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedState) {
        ViewGroup PaymentView = (ViewGroup) layoutInflater.inflate(R.layout.fragment_payment, container, false);
        setHasOptionsMenu(true);


        return PaymentView;

    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final CheckBox cb = view.findViewById(R.id.checkKredit);
        final CheckBox cb1 = view.findViewById(R.id.checkMobilePay);


        final TextView tv = view.findViewById(R.id.cardnr);
        final TextView tv1 = view.findViewById(R.id.cvc);
        final TextView tv2 = view.findViewById(R.id.date);


        final EditText et = view.findViewById(R.id.editCardNumber);
        final EditText et1 = view.findViewById(R.id.editCvc);
        final EditText et2 = view.findViewById(R.id.editDate);

        tv.setVisibility(View.GONE);
        tv1.setVisibility(View.GONE);
        tv2.setVisibility(View.GONE);

        et.setVisibility(View.GONE);
        et1.setVisibility(View.GONE);
        et2.setVisibility(View.GONE);

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                          @Override
                                          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                              LinearLayout linearLayout = view.findViewById(R.id.linearKredit);

                                              if (isChecked == true) {

                                                  linearLayout.setBackground(getResources().getDrawable(R.drawable.bg_outline));

                                                  tv.setVisibility(View.VISIBLE);
                                                  et.setVisibility(View.VISIBLE);
                                                  tv1.setVisibility(View.VISIBLE);
                                                  et1.setVisibility(View.VISIBLE);
                                                  tv2.setVisibility(View.VISIBLE);
                                                  et2.setVisibility(View.VISIBLE);
                                                  cb1.setChecked(false);

                                              } else {

                                                  linearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                                                  tv.setVisibility(View.GONE);
                                                  tv1.setVisibility(View.GONE);
                                                  tv2.setVisibility(View.GONE);

                                                  et.setVisibility(View.GONE);
                                                  et1.setVisibility(View.GONE);
                                                  et2.setVisibility(View.GONE);
                                              }
                                          }
                                      }
        );

        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                           @Override
                                           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                               LinearLayout linearLayout = view.findViewById(R.id.linearMobile);

                                               if (isChecked == true) {

                                                   linearLayout.setBackground(getResources().getDrawable(R.drawable.bg_outline));
                                                   cb.setChecked(false);
                                               } else {

                                                   linearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                               }
                                           }
                                       }

        );
    }


    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}

