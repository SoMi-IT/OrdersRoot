package com.somi.ordersroot.auth;

import com.somi.ordersroot.admin.Admin;
import com.somi.ordersroot.admin.license.License;
import com.somi.ordersroot.admin.user.User;

import java.util.ArrayList;

public interface FirebaseAuthManagerListener {

    void onAdminLogged();
    void onUserLogged();
    void onAuthError(String error);

}//FirebaseAuthManagerListener
