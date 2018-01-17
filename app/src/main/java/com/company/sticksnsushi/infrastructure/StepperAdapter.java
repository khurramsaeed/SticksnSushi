package com.company.sticksnsushi.infrastructure;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.company.sticksnsushi.fragments.CheckoutTimeFragment;
import com.company.sticksnsushi.fragments.InformationFragment;
import com.company.sticksnsushi.fragments.PaymentFragment;
import com.company.sticksnsushi.fragments.StepperFragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

/**
 * Created by Swagam on 12/01/2018.
 */
public class StepperAdapter extends AbstractFragmentStepAdapter{

    public StepperAdapter(FragmentManager fm, Context context){
        super(fm, context);
    }


    @Override
    public Step createStep(int position) {

        switch(position) {
            case 0:
                CheckoutTimeFragment step1 = new CheckoutTimeFragment();
                Bundle b = new Bundle();
                b.putInt("1", position);
                step1.setArguments(b);
                return step1;

            case 1:
                InformationFragment step2 = new InformationFragment();
                Bundle b2 = new Bundle();
                b2.putInt("2", position);
                step2.setArguments(b2);
                return step2;

            case 2:
                PaymentFragment step3 = new PaymentFragment();
                Bundle b3 = new Bundle();
                b3.putInt("3", position);
                step3.setArguments(b3);
                return step3;

        }

        return null;

    }


    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position){

        return new StepViewModel.Builder(context)
                .setTitle("STEPSTEPSTEP")
                .create();
    }
}