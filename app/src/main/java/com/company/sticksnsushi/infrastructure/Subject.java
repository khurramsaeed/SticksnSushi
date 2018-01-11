package com.company.sticksnsushi.infrastructure;

import android.content.Context;

/**
 * Created by Khurram Saeed Malik on 11/01/2018.
 */

public interface Subject {
    void register(Context context);
    void unregister(Context status);
    void notifyObservers();
}
