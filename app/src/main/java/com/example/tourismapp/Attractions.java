package com.example.tourismapp;

import java.io.Serializable;

public class Attractions implements Serializable {
    String name;
    String address;
    String description;
    int price;
    String website;
    String phone;
    String images[];

    public Attractions(String name, String address, String description, int price, String website, String phone,String[] images) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.price = price;
        this.website = website;
        this.phone = phone;
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }
    public String[] getImages() {
        return images;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Attractions{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", website='" + website + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
