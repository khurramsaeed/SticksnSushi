package com.company.sticksnsushi.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment{
    public abstract View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedState);
}
