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

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.infrastructure.App;
import com.company.sticksnsushi.infrastructure.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;


public class InformationFragment extends BaseFragment implements BlockingStep {

    private static final int STATE_EDITING = 1;
    private static final int STATE_VIEWING = 2;
    private static final String BUNDLE_STATE = "BUNDLE_STATE";

    private int currentState;



    private static final String TAG = "INFORMATION: ";
    EditText editFullName, editPhone, editAdress, editPostalnr, editCity;
    App app = App.getInstance();
    FirebaseUser currentUser = app.firebaseAuth.getCurrentUser();
    User user = app.getAuth().getUser();
    CheckBox userInfo;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_information, container, false);
        editFullName = (EditText) view.findViewById(R.id.editTextName);
        editPhone = (EditText) view.findViewById(R.id.editTextPhone);
        editAdress = (EditText) view.findViewById(R.id.editTextAdress);
        editPostalnr = (EditText) view.findViewById(R.id.editTextPostalAdress);
        editCity = (EditText) view.findViewById(R.id.editTextCity);
        userInfo = (CheckBox) view.findViewById(R.id.userInfo);


        userInfo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(userInfo.isChecked()){
                    editFullName.setText(user.getDisplayName());
                    editPhone.setText(user.getPhone());
                    editAdress.setText(user.getAddress());
                    editPostalnr.setText(user.getPostalNr());
                    editCity.setText(user.getCity());
                }
                else {
                    editFullName.setText("");
                    editPhone.setText("");
                    editAdress.setText("");
                    editPostalnr.setText("");
                    editCity.setText("");
                }
            }
        });

        changeState(STATE_VIEWING);

        if (savedInstanceState == null) {
            changeState(STATE_VIEWING);
        } else {
            changeState(savedInstanceState.getInt(BUNDLE_STATE));
        }

        getActivity().setTitle("Bestilling");

        return view;
    }

    /**
     * Saves logged in users data in Firebase by its ID
     */
    private void saveUserDetailsFirebase(){
        String name = editFullName.getText().toString().trim();
        String address = editAdress.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();
        String city  = editCity.getText().toString().trim();
        String postalnr = editPostalnr.getText().toString().trim();
        user.setDeliveryDetails(address, city, phone, postalnr);
        user.setDisplayName(name);
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").child(app.getAuth().getUser().getId()).child("personal_details").setValue(app.getAuth().getUser());
    }

    private void changeState(int state){

        if(state == currentState){
            return;
        }

        currentState = state;

    }

    /**
     * This method is called when you rotate screen
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Saved state is reloaded here
        outState.putInt(BUNDLE_STATE, currentState);
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

        final String fullName = editFullName.getText().toString().trim();
        final String phone = editPhone.getText().toString().trim();
        final String adress = editAdress.getText().toString().trim();
        final String postalnr = editPostalnr.getText().toString().trim();
        final String city = editCity.getText().toString().trim();

        if (TextUtils.isEmpty(fullName) ) {
            editFullName.setError("Du skal indtaste dit fulde navn");
            editFullName.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(phone)){
            editPhone.setError("Du skal indtaste dit telefon nummer");
            editPhone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(adress)) {
            editAdress.setError("Du skal indtaste din adresse");
            editAdress.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(postalnr)) {
            editPostalnr.setError("Du skal indtaste dit post nummer");
            editPostalnr.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(city)) {
            editCity.setError("Du skal indtaste din by");
            editCity.requestFocus();
            return;
        }

        if(currentUser != null){
            // TODO: 15-01-2018 Run commented method
            saveUserDetailsFirebase();
        }

        callback.goToNextStep();

    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {

        callback.goToPrevStep();

    }
}

