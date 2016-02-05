package com.acetechapps.sql;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by bhargavsarvepalli on 05/02/16.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        callDisplayActivity();
    }

    private void callDisplayActivity() {

        final Runnable splashScreenRunnable = new Runnable() {
            public void run() {
                Intent i;
                i = new Intent(SplashActivity.this, NewConnectionActivity.class);
                startActivity(i);
                finish();
            }
        };

        Handler handler = new Handler();
        handler.postDelayed(splashScreenRunnable, 1000);
    }

}
