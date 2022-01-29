package com.somi.ordersroot.FirestoreDataManager;

import com.somi.ordersroot.admin.Admin;
import com.somi.ordersroot.admin.license.License;
import com.somi.ordersroot.admin.user.User;

import java.util.ArrayList;

public interface FirestoreAdminManagerListener {


    void onAdminDataRetrieved(Admin admin);
    void onLicensesDataRetrieved(ArrayList<License> licenses);
    void onUsersDataRetrieved(ArrayList<User> users);
    void onDataRetrieveError(String error);

}//FirestoreUserManagerListener
