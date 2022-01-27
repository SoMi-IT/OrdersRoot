package com.somi.ordersroot.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.somi.ordersroot.FirestoreDataManager.FirestoreAdminManager;
import com.somi.ordersroot.FirestoreDataManager.FirestoreAdminManagerListener;
import com.somi.ordersroot.R;
import com.somi.ordersroot.admin.data.User;
import com.somi.ordersroot.auth.AuthActivity;
import com.somi.ordersroot.auth.AuthFragment;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity implements PanelFragmentListener, FirestoreAdminManagerListener {



    private PanelFragment panelFragment;
    private AdminFragment adminFragment;

    private FirebaseAuth mAuth;
    private FirestoreAdminManager firestoreAdminManager;

    private AdminActivityListener listenerForPanel, listenerForMain;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        toggleLoader(false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            showPanelFragment();
            showAdminFragment();

            firestoreAdminManager = new FirestoreAdminManager();
            firestoreAdminManager.setListener(this);
            firestoreAdminManager.fetchAdminData();
            firestoreAdminManager.fetchUsersData();

        }else {
            Intent intent = new Intent(this, AuthActivity.class);
            this.startActivity(intent);
            this.finish();
        }



    }//onCreate

    public void setListenerForPanel(AdminActivityListener listener) {

        listenerForPanel = listener;

    }//setListenerForPanel


    public void setListenerForMain(AdminActivityListener listener) {

        listenerForMain = listener;

    }//setListenerForMain


    public void showPanelFragment() {

        panelFragment = new PanelFragment();

        FragmentManager mainFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mainFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_admin_panel, panelFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        panelFragment.setPanelFragmentListener(this);


    }//showPanelFragment

    public void showAdminFragment() {

        adminFragment = new AdminFragment();

        FragmentManager mainFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mainFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_admin_main, adminFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();


    }//showAdminFragment


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
        mAuth.signOut();

        Intent intent = new Intent(this, AuthActivity.class);
        this.startActivity(intent);
        this.finish();


    }


    public void onAdminDataRetrieved(Admin admin) {
        if(listenerForPanel != null)listenerForPanel.onAdminDataUpdated(admin);

    }//onAdminDataRetrieved


    public void onUsersDataRetrieved(ArrayList<User> users) {
        if(listenerForMain != null) listenerForMain.onUsersDataUpdated(users);

    }//onUsersDataRetrieved


    public void onDataRetrieveError(String error) {

        Toast.makeText(this, error, Toast.LENGTH_LONG).show();

    }//onDataRetrieveError


}//AdminActivity