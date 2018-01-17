package com.company.sticksnsushi.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.fragments.PersonalinfoFragment;
import com.company.sticksnsushi.fragments.FavoritesFragment;

/**
 * Created by Swagam on 20/11/2017.
 */


public class ProfileActivity extends BaseActivity {

    EditText editFullName, editPhone, editAdress, editPostalnr, editCity;

    private static final int STATE_EDITING = 1;

    private static final int STATE_VIEWING = 2;

    ActionMode editProfileActionMode;

    private int currentState;


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        setContentView(R.layout.activity_profile);

        editFullName = (EditText) findViewById(R.id.editTextName);
        editPhone = (EditText) findViewById(R.id.editTextPhone);
        editAdress = (EditText) findViewById(R.id.editTextAdress);
        editPostalnr = (EditText) findViewById(R.id.editTextPostalAdress);
        editCity = (EditText) findViewById(R.id.editTextName);

        changeState(STATE_VIEWING);

        setTitle("Profile");

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
