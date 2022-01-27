package com.somi.ordersroot.admin;

import com.somi.ordersroot.admin.data.User;

import java.util.ArrayList;

public interface AdminActivityListener {

    void onAdminDataUpdated(Admin admin);
    void onUsersDataUpdated(ArrayList<User> users);

}//AdminActivityListener
