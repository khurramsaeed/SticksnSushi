package com.company.sticksnsushi.infrastructure;

import android.content.Context;

/**
 * Created by Khurram Saeed Malik on 09/10/2017.
 */

public class Auth {

    private final Context context;
    private User user;

    public Auth(Context context) {
        this.context = context;
        user = new User();
    }

    public User getUser() {
        return user;
    }
}
