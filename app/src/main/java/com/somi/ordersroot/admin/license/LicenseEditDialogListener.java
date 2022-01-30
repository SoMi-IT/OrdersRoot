package com.somi.ordersroot.admin.license;

import com.somi.ordersroot.admin.Admin;
import com.somi.ordersroot.admin.user.User;

import java.util.ArrayList;

public interface LicenseEditDialogListener {

    void onQrScanRequest();
    void onLicenseEdited(License license);

}//LicenseEditDialogListener
