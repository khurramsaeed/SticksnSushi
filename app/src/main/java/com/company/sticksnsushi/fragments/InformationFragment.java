package com.company.sticksnsushi.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.company.sticksnsushi.R;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;


public class InformationFragment extends Fragment implements BlockingStep {

    EditText fullName, phone, adress, postalnr, city;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_information, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fullName = (EditText) view.findViewById(R.id.editTextName);
        phone = (EditText) view.findViewById(R.id.editTextPhone);
        adress = (EditText) view.findViewById(R.id.editTextAdress);
        postalnr = (EditText) view.findViewById(R.id.editTextPostalAdress);
        city = (EditText) view.findViewById(R.id.editTextCity);

        getActivity().setTitle("Bestilling");

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

        if (fullName.getText().toString().trim().length() == 0) {
            fullName.setError("Du skal indtaste dit navn");
        } else {
            callback.goToNextStep();
        }

        if (phone.getText().toString().trim().length() == 0) {
            phone.setError("Du skal indtaste dit telefonnummer");
        } else {
            callback.goToNextStep();
        }

        if (adress.getText().toString().trim().length() == 0) {
            adress.setError("Du skal indtaste din adresse");
        } else {
            callback.goToNextStep();
        }

        if (postalnr.getText().toString().trim().length() == 0) {
            postalnr.setError("Du skal indtaste dit post nummer");
        } else {
            callback.goToNextStep();
        }

        if (city.getText().toString().trim().length() == 0) {
            city.setError("Du skal indtaste din by");
        } else {
            callback.goToNextStep();
        }

    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {

    }
}

