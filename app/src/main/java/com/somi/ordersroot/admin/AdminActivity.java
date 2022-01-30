package com.somi.ordersroot.admin;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.somi.ordersroot.FirestoreDataManager.FirestoreAdminManager;
import com.somi.ordersroot.FirestoreDataManager.FirestoreAdminManagerListener;
import com.somi.ordersroot.R;
import com.somi.ordersroot.admin.license.License;
import com.somi.ordersroot.admin.license.LicenseEditDialogListener;
import com.somi.ordersroot.admin.license.LicensesFragmentListener;
import com.somi.ordersroot.admin.user.User;
import com.somi.ordersroot.admin.license.LicensesFragment;
import com.somi.ordersroot.admin.user.UserEditDialogListener;
import com.somi.ordersroot.admin.user.UsersFragment;
import com.somi.ordersroot.auth.AuthActivity;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity implements PanelFragmentListener, FirestoreAdminManagerListener, LicensesFragmentListener, UserEditDialogListener {



    private static final int STATE_LICENSES = 0;
    private static final int STATE_USERS = 1;

    private PanelFragment panelFragment;
    private UsersFragment usersFragment;
    private LicensesFragment licensesFragment;

    private FirestoreAdminManager firestoreAdminManager;

    private int currentState;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        toggleLoader(false);

        firestoreAdminManager = new FirestoreAdminManager();

        if(firestoreAdminManager.getMAuth().getCurrentUser() != null){
            showPanelFragment();
            currentState = STATE_LICENSES;
            showMainFragment();

        }else {
            Intent intent = new Intent(this, AuthActivity.class);
            this.startActivity(intent);
            this.finish();
        }



    }//onCreate


    @Override
    protected void onStop() {
        super.onStop();
        if(firestoreAdminManager != null)firestoreAdminManager.setListener(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(firestoreAdminManager != null)firestoreAdminManager.setListener(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(firestoreAdminManager != null) {
            firestoreAdminManager.setListener(this);
            firestoreAdminManager.fetchAdminData();

            if(currentState == STATE_LICENSES) {
                firestoreAdminManager.fetchLicensesData();

            }else if(currentState == STATE_USERS) {
                firestoreAdminManager.fetchUsersData();

            }

        }

    }

    @Override
    public void onBackPressed() {

    }


    public void showPanelFragment() {

        panelFragment = new PanelFragment();
        FragmentManager mainFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mainFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_admin_panel, panelFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        panelFragment.setPanelFragmentListener(this);


    }//showPanelFragment


    public void showMainFragment() {

        FragmentManager mainFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mainFragmentManager.beginTransaction();

        if(currentState == STATE_LICENSES) {
            licensesFragment = new LicensesFragment();
            licensesFragment.setListener(this);
            fragmentTransaction.replace(R.id.fl_admin_main, licensesFragment);
            firestoreAdminManager.fetchLicensesData();

        }else if(currentState == STATE_USERS) {
            usersFragment = new UsersFragment();
            usersFragment.setListener(this);
            fragmentTransaction.replace(R.id.fl_admin_main, usersFragment);
            firestoreAdminManager.fetchUsersData();

        }

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

    }//showLicensesFragment


    private void toggleLoader(boolean visible){

        View v_authLoader = findViewById(R.id.v_admin);
        ProgressBar pb_authLoader = findViewById(R.id.pb_admin);

        if(visible) {
            v_authLoader.setVisibility(View.VISIBLE);
            pb_authLoader.setVisibility(View.VISIBLE);

        }else {
            v_authLoader.setVisibility(View.GONE);
            pb_authLoader.setVisibility(View.GONE);
        }

    }//toggleLoader


    public void onLogout() {
        toggleLoader(true);
        firestoreAdminManager.getMAuth().signOut();

        Intent intent = new Intent(this, AuthActivity.class);
        this.startActivity(intent);
        this.finish();

    }//onLogout


    public void onManageLicenses() {
        currentState = STATE_LICENSES;
        showMainFragment();

    }//onManageLicenses


    public void onManageUsers() {
        currentState = STATE_USERS;
        showMainFragment();

    }//onManageUsers


    //-------------------FireStoreAdminListener
    public void onAdminDataRetrieved(Admin admin) {
        if(panelFragment != null)panelFragment.adminDataUpdated(admin);

    }//onAdminDataRetrieved


    public void onUsersDataRetrieved(ArrayList<User> users) {
        if(usersFragment != null && currentState == STATE_USERS) usersFragment.usersDataUpdated(users);

    }//onUsersDataRetrieved


    public void onLicensesDataRetrieved(ArrayList<License> licenses) {
        if(licensesFragment != null && currentState == STATE_LICENSES) licensesFragment.licensesDataUpdated(licenses);
    }//onLicensesDataRetrieved


    public void onDataRetrieveError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();

    }//onDataRetrieveError

    //-------------------LicenseFragmentListener


    @Override
    public void onLicenseDataChanged(License license) {
        firestoreAdminManager.updateLicenseData(license);
    }

    @Override
    public void onUserEdited(User user) {

    }
}//AdminActivity