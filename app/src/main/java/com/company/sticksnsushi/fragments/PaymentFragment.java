package com.company.sticksnsushi.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.company.sticksnsushi.R;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

public class PaymentFragment extends BaseFragment implements BlockingStep {

    CheckBox cb, cb1, cb2;

    EditText editCardNumber, editCVC, editDate;

    TextView tv1;


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedState) {
        ViewGroup PaymentView = (ViewGroup) layoutInflater.inflate(R.layout.fragment_payment, container, false);
        setHasOptionsMenu(true);


        return PaymentView;

    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cb = view.findViewById(R.id.checkKredit);
        cb1 = view.findViewById(R.id.checkMobilePay);
        cb2 = view.findViewById(R.id.checkPaypal);

        editCardNumber = view.findViewById(R.id.input_cardNumber);
        editCVC = view.findViewById(R.id.input_cvc);
        editDate = view.findViewById(R.id.input_date);

        tv1 = view.findViewById(R.id.notImplemented);


        editCardNumber.setVisibility(View.GONE);
        editCVC.setVisibility(View.GONE);
        editDate.setVisibility(View.GONE);

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                          @Override
                                          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                                              LinearLayout linearLayout = view.findViewById(R.id.linearKredit);

                                              if (isChecked == true) {

                                                  linearLayout.setBackground(getResources().getDrawable(R.drawable.bg_outline));

                                                  editCardNumber.setVisibility(View.VISIBLE);

                                                  editCVC.setVisibility(View.VISIBLE);

                                                  editDate.setVisibility(View.VISIBLE);
                                                  cb1.setChecked(false);

                                              } else {

                                                  linearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


                                                  editCardNumber.setVisibility(View.GONE);
                                                  editCVC.setVisibility(View.GONE);
                                                  editDate.setVisibility(View.GONE);
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

                                                   tv1.setVisibility(View.VISIBLE);

                                                   cb.setChecked(false);
                                               } else {

                                                   tv1.setVisibility(View.GONE);
                                                   linearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                               }
                                           }
                                       }

        );

        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                           @Override
                                           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                               LinearLayout linearLayout = view.findViewById(R.id.linearPaypal);

                                               if (isChecked == true) {

                                                   linearLayout.setBackground(getResources().getDrawable(R.drawable.bg_outline));

                                                   tv1.setVisibility(View.VISIBLE);

                                                   cb.setChecked(false);
                                                   cb1.setChecked(false);
                                               } else {

                                                   tv1.setVisibility(View.GONE);
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

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {

    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

        String cardNumber = editCardNumber.getText().toString().trim();
        String cvc = editCVC.getText().toString().trim();
        String date = editDate.getText().toString().trim();

        if(TextUtils.isEmpty(cardNumber)){
            editCardNumber.setError("Angiv dit kort nummer");
            editCardNumber.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(cvc)){
            editCVC.setError("Angiv dit CVC nummer");
            editCVC.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(date)){
            editDate.setError("Angiv udløbsdato");
            editDate.requestFocus();
            return;
        }

        callback.complete();

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {

        callback.goToPrevStep();

    }
}

