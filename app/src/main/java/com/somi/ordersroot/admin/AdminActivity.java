package com.somi.ordersroot.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.somi.ordersroot.FirestoreDataManager.FirestoreAdminManager;
import com.somi.ordersroot.FirestoreDataManager.FirestoreAdminManagerListener;
import com.somi.ordersroot.R;
import com.somi.ordersroot.admin.license.License;
import com.somi.ordersroot.admin.user.User;
import com.somi.ordersroot.admin.license.LicensesFragment;
import com.somi.ordersroot.admin.user.UsersFragment;
import com.somi.ordersroot.auth.AuthActivity;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity implements PanelFragmentListener, FirestoreAdminManagerListener {



    private PanelFragment panelFragment;
    private UsersFragment usersFragment;
    private LicensesFragment licensesFragment;

    private FirestoreAdminManager firestoreAdminManager;

    private AdminActivityListener listenerForPanel, listenerForUser, listenerForLicenses;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        toggleLoader(false);

        firestoreAdminManager = new FirestoreAdminManager();

        if(firestoreAdminManager.getMAuth().getCurrentUser() != null){
            showPanelFragment();
            showLicensesFragment();

            firestoreAdminManager.setListener(this);
            firestoreAdminManager.fetchAdminData();
            firestoreAdminManager.fetchLicensesData();

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
            firestoreAdminManager.fetchLicensesData();
        }

    }

    @Override
    public void onBackPressed() {

    }

    public void setListenerForPanel(AdminActivityListener listener) {
        listenerForPanel = listener;

    }//setListenerForPanel


    public void setListenerForUser(AdminActivityListener listener) {
        listenerForUser = listener;

    }//setListenerForUser

    public void setListenerForLicenses(AdminActivityListener listener) {
        listenerForLicenses = listener;

    }//setListenerForLicenses


    public void showPanelFragment() {

        panelFragment = new PanelFragment();
        FragmentManager mainFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mainFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_admin_panel, panelFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        panelFragment.setPanelFragmentListener(this);


    }//showPanelFragment

    public void showUsersFragment() {

        usersFragment = new UsersFragment();
        FragmentManager mainFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mainFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_admin_main, usersFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();


    }//showUsersFragment


    public void showLicensesFragment() {

        licensesFragment = new LicensesFragment();
        FragmentManager mainFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mainFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_admin_main, licensesFragment);
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


    @Override
    public void onLogout() {
        toggleLoader(true);
        firestoreAdminManager.getMAuth().signOut();

        Intent intent = new Intent(this, AuthActivity.class);
        this.startActivity(intent);
        this.finish();


    }


    public void onAdminDataRetrieved(Admin admin) {
        if(listenerForPanel != null)listenerForPanel.onAdminDataUpdated(admin);

    }//onAdminDataRetrieved


    public void onUsersDataRetrieved(ArrayList<User> users) {
        if(listenerForUser != null) listenerForUser.onUsersDataUpdated(users);

    }//onUsersDataRetrieved


    public void onLicensesDataRetrieved(ArrayList<License> licenses) {
        if(listenerForLicenses != null) listenerForLicenses.onLicensesDataUpdated(licenses);
    }//onLicensesDataRetrieved


    public void onDataRetrieveError(String error) {

        Toast.makeText(this, error, Toast.LENGTH_LONG).show();

    }//onDataRetrieveError


}//AdminActivity