package com.company.sticksnsushi.activities;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.fragments.PersonalinfoFragment;
import com.company.sticksnsushi.fragments.FavoritesFragment;
import com.company.sticksnsushi.infrastructure.App;
import com.company.sticksnsushi.infrastructure.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Swagam on 20/11/2017.
 */


public class ProfileActivity extends BaseActivity {

    EditText editFullName, editPhone, editAdress, editPostalnr, editCity;

    TextView displayName, initials;

    private static final int STATE_EDITING = 1;
    private static final int STATE_VIEWING = 2;
    private static final String BUNDLE_STATE = "BUNDLE_STATE";

    ActionMode editProfileActionMode;

    private int currentState;

    App app = App.getInstance();

    FirebaseUser currentUser = app.firebaseAuth.getCurrentUser();
    User user = app.getAuth().getUser();

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        setContentView(R.layout.activity_profile);

        editFullName = (EditText) findViewById(R.id.editTextName);
        editPhone = (EditText) findViewById(R.id.editTextPhone);
        editAdress = (EditText) findViewById(R.id.editTextAdress);
        editPostalnr = (EditText) findViewById(R.id.editTextPostalAdress);
        editCity = (EditText) findViewById(R.id.editTextCity);

        displayName = (TextView) findViewById(R.id.displayName);
        initials = (TextView) findViewById(R.id.initials);

        changeState(STATE_VIEWING);

        setTitle("Profile");

        editFullName.setText(user.getDisplayName());
        editPhone.setText(user.getPhone());
        editAdress.setText(user.getAddress());
        editPostalnr.setText(user.getPostalNr());
        editCity.setText(user.getCity());

        displayName.setText(user.getDisplayName());
        initials.setText(getInitials());

        // Screen rotation: Editing fields fix
        if (savedState == null) {
            changeState(STATE_VIEWING);
        } else {
            changeState(savedState.getInt(BUNDLE_STATE));
        }

    }

    private void saveUserDetailsFirebase(){
        String address = editAdress.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();
        String city  = editCity.getText().toString().trim();
        String postalnr = editPostalnr.getText().toString().trim();
        String name = editFullName.getText().toString().trim();
        user.setDisplayName(name);
        user.setDeliveryDetails(address, city, phone, postalnr);
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").child(user.getId()).child("personal_details").setValue(app.getAuth().getUser());
    }

    private String getInitials(){
        String initials = user.getDisplayName();

        return initials.substring(0, 1);
    }

    /**
     * This method is called when you rotate screen
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Saved state is reloaded here
        outState.putInt(BUNDLE_STATE, currentState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if(itemId == R.id.activity_profile_edit){
            changeState(STATE_EDITING);
            return true;
        }

        return false;
    }

    private void changeState(int state){

        if(state == currentState){
            return;
        }

        currentState = state;

        if(state == STATE_VIEWING){
            editFullName.setEnabled(false);
            editPhone.setEnabled(false);
            editAdress.setEnabled(false);
            editPostalnr.setEnabled(false);
            editCity.setEnabled(false);

            if(editProfileActionMode != null){
                editProfileActionMode.finish();
                editProfileActionMode = null;
            }
        }
        else if(state == STATE_EDITING){
            editFullName.setEnabled(true);
            editPhone.setEnabled(true);
            editAdress.setEnabled(true);
            editPostalnr.setEnabled(true);
            editCity.setEnabled(true);

            editProfileActionMode = toolbar.startActionMode(new EditProfileActionCallBack());

        }
        else
            throw  new IllegalArgumentException("Invalid state " + state);
    }

    private class EditProfileActionCallBack implements ActionMode.Callback{


        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            getMenuInflater().inflate(R.menu.action_profile_edit, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            int itemId = menuItem.getItemId();

            if(itemId == R.id.activity_profile_editDone){

                changeState(STATE_VIEWING);

                saveUserDetailsFirebase();

                displayName.setText(user.getDisplayName());
                initials.setText(getInitials());
                app.shortToastMessage("Oplysninger opdateret");
            }

            return true;
        }


        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            if(currentState != STATE_VIEWING){
                changeState(STATE_VIEWING);
            }

        }
    }

}
