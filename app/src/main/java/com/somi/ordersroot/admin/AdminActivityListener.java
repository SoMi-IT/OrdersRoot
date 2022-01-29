package com.somi.ordersroot.admin;

import com.somi.ordersroot.admin.license.License;
import com.somi.ordersroot.admin.user.User;

import java.util.ArrayList;

public interface AdminActivityListener {

    void onAdminDataUpdated(Admin admin);
    void onLicensesDataUpdated(ArrayList<License> licenses);
    void onUsersDataUpdated(ArrayList<User> users);

}//AdminActivityListener
