package com.company.sticksnsushi.infrastructure;

/**
 * Created by Khurram Saeed Malik on 09/10/2017.
 * DTO class object
 */

public class User {
    private String id;
    private String displayName, address, city, phone, postalNr;
    private boolean isLoggedIn;
    private boolean hasPassword;
    private String email;

    public void setPersonalDetails(String id, String displayName, String email) {
        this.id = id;
        this.displayName = displayName;
        this.email = email;
    }

    public void setDeliveryDetails(String address, String city, String phone, String postalNr) {
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.postalNr = postalNr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostalNr() {
        return postalNr;
    }

    public void setPostalNr(String postalNr) {
        this.postalNr = postalNr;
    }

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
    
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void resetUser(){
        setPersonalDetails(null,"","");
        setDeliveryDetails("","","","");

    }


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", displayName='" + displayName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + phone + '\'' +
                ", postalNr='" + postalNr + '\'' +
                ", isLoggedIn=" + isLoggedIn +
                ", hasPassword=" + hasPassword +
                ", email='" + email + '\'' +
                '}';
    }
}
