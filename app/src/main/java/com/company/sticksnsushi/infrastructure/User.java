package com.company.sticksnsushi.infrastructure;

/**
 * Created by Khurram Saeed Malik on 09/10/2017.
 * DTO class object
 */

public class User {
    private int id;
    private String userName;
    private String displayName;
    private boolean isLoggedIn;
    private boolean hasPassword;
    private String email;

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public boolean isHasPassword() {
        return hasPassword;
    }

    public void setHasPassword(boolean hasPassword) {
        this.hasPassword = hasPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
//    public String getDisplayName() {
//        return displayName;
//    }

//    public void setDisplayName(String displayName) {
//        this.displayName = displayName;
//    }

    public String getUserName() {return userName;}

    public void setInitials(){}

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
