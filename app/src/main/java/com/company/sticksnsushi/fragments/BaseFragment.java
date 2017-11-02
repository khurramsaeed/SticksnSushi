package com.company.sticksnsushi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment {
    public abstract View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedState);
}
