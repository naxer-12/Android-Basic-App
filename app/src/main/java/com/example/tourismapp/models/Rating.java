package com.example.tourismapp.models;

class Rating {
    private String locationName;
    private int locationCode;
    private double ratingValue;


    Rating(String locationName, int locationCode, double ratingValue) {
        this.locationName = locationName;
        this.locationCode = locationCode;
        this.ratingValue = ratingValue;
    }

    public double getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(double ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(int locationCode) {
        this.locationCode = locationCode;
    }
}