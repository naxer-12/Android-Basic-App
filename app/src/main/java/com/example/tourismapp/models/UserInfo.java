package com.example.tourismapp.models;

import java.util.ArrayList;

class UserInfo {
    private String userName;
    private String password;
    private boolean isLoggedIn;
    private boolean rememberMe;
    private ArrayList<Rating> ratingValue;

    UserInfo(String userName, String password, boolean isLoggedIn, boolean rememberMe, ArrayList<Rating> ratingValue) {
        this.userName = userName;
        this.password = password;
        this.isLoggedIn = isLoggedIn;
        this.rememberMe = rememberMe;
        this.ratingValue = ratingValue;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public ArrayList<Rating> getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(ArrayList<Rating> ratingValue) {
        this.ratingValue = ratingValue;
    }
}