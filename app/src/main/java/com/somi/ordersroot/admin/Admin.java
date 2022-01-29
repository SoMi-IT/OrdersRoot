package com.somi.ordersroot.admin;

import android.graphics.Bitmap;

import com.somi.ordersroot.admin.user.User;

import java.util.ArrayList;

public class Admin {




    private String id;
    private String address;
    private Bitmap image;
    private ArrayList<User> users;
    private String email;
    private String title;
    private String city;

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public Bitmap getImage() {
        return image;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public String getCity() {
        return city;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
