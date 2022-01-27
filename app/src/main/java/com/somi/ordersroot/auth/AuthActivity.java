package com.somi.ordersroot.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.somi.ordersroot.R;
import com.somi.ordersroot.admin.AdminActivity;

public class AuthActivity extends AppCompatActivity implements AuthFragmentListener {

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

        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        if(getWindow().getCurrentFocus() != null)inputMethodManager.hideSoftInputFromWindow(getWindow().getCurrentFocus().getApplicationWindowToken(),0);

        if(email != null && password != null) {

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            Intent intent = new Intent(this, AdminActivity.class);
                            this.startActivity(intent);
                            this.finish();

                        } else {
                            Toast.makeText(this, "Authentication failed : " + task.getException(), Toast.LENGTH_SHORT).show();
                            toggleLoader(false);
                        }
                    });

        }


    }//onAuthStarted


}//AuthActivity