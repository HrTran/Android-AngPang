package com.example.admin.angpangii.Fragments;

import android.app.Activity;
import com.example.admin.angpangii.R;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Admin on 4/24/2016.
 */
public class SplashStartScreen extends AppCompatActivity{

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Main-Activity. */
                Intent mainIntent = new Intent(SplashStartScreen.this,LoginActivity.class);
                SplashStartScreen.this.startActivity(mainIntent);
                SplashStartScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}