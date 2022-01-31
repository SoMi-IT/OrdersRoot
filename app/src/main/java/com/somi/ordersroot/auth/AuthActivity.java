package com.somi.ordersroot.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.Document;
import com.somi.ordersroot.R;
import com.somi.ordersroot.admin.AdminActivity;
import com.somi.ordersroot.admin.user.User;

import java.util.ArrayList;
import java.util.List;

public class AuthActivity extends AppCompatActivity implements AuthFragmentListener, FirebaseAuthManagerListener {


    private FirebaseAuthManager firebaseAuthManager;

    private AuthFragment authFragment;

    private FirebaseAuth mAuth;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        toggleLoader(false);
        showAuthFragment();

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(this, AdminActivity.class);
            this.startActivity(intent);
            this.finish();
        }



    }//onCreate


    protected void onResume() {
        super.onResume();
        firebaseAuthManager = new FirebaseAuthManager();
        firebaseAuthManager.setListener(this);

    }//onResume


    protected void onPause() {
        super.onPause();
        if(firebaseAuthManager != null)firebaseAuthManager.setListener(null);
    }//onPause


    protected void onStop() {
        super.onStop();
        if(firebaseAuthManager != null)firebaseAuthManager.setListener(null);
    }//onStop


    protected void onDestroy() {
        super.onDestroy();
        if(firebaseAuthManager != null)firebaseAuthManager.setListener(null);
    }//onDestroy


    public void showAuthFragment() {

        authFragment = new AuthFragment();

        FragmentManager mainFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mainFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_auth, authFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        authFragment.setListener(this);

    }//showAuthFragment

    private void toggleLoader(boolean visible){

        View v_authLoader = findViewById(R.id.v_auth);
        ProgressBar pb_authLoader = findViewById(R.id.pb_auth);

        if(visible) {
            v_authLoader.setVisibility(View.VISIBLE);
            pb_authLoader.setVisibility(View.VISIBLE);

        }else {
            v_authLoader.setVisibility(View.GONE);
            pb_authLoader.setVisibility(View.GONE);
        }

    }//toggleLoader

    public void onAuthStartRequested(String email, String password) {

        toggleLoader(true);

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getWindow().getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(getWindow().getCurrentFocus().getApplicationWindowToken(), 0);

        if (email != null && password != null) {
            if(firebaseAuthManager != null) firebaseAuthManager.logInAsAdmin(email, password);

        } else if (email != null && password == null) {
            if(firebaseAuthManager != null) firebaseAuthManager.logInAsUser(email, getDeviceId(this));

        }
    }


    public static String getDeviceId(Context context) {

        String deviceId;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            if (mTelephony.getMeid() != null) deviceId = mTelephony.getMeid();
            else deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        }

        return deviceId;

    }//getDeviceId

    public void onAdminLogged() {
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
        finish();

    }//onAdminLogged


    public void onUserLogged() {
        //QUA DEVE CARICARE ACTIVITY USER
    }//onUserLogged


    public void onAuthError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        toggleLoader(false);

    }//onAuthError


}//AuthActivity