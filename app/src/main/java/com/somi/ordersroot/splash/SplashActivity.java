package com.somi.ordersroot.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.somi.ordersroot.R;
import com.somi.ordersroot.auth.AuthActivity;

public class SplashActivity extends AppCompatActivity {


    private final int SPLASH_DISPLAY_LENGTH = 800;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {

            Intent mainIntent = new Intent(this, AuthActivity.class);
            this.startActivity(mainIntent);
            this.finish();

        }, SPLASH_DISPLAY_LENGTH);

    }//onCreate

}//SplashActivity