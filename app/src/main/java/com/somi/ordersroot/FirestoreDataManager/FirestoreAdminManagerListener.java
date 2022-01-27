package com.somi.ordersroot.FirestoreDataManager;

import com.somi.ordersroot.admin.Admin;
import com.somi.ordersroot.admin.data.User;

import java.util.ArrayList;

public interface FirestoreAdminManagerListener {


    void onAdminDataRetrieved(Admin admin);
    void onUsersDataRetrieved(ArrayList<User> users);
    void onDataRetrieveError(String error);

}//FirestoreUserManagerListener
