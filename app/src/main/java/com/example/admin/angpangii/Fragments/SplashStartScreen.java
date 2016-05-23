package com.example.admin.angpangii.Fragments;

import android.app.Activity;

import com.example.admin.angpangii.Items.User;
import com.example.admin.angpangii.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
                Intent intent;
                SharedPreferences preferences = getSharedPreferences(
                        getString(R.string.preference_file_key), MODE_PRIVATE
                );
                if(preferences.getInt("id", -1) == -1 ){
                    // if id = -1, go to login screen
                    intent = new Intent(SplashStartScreen.this,Login2Activity.class);
                    //intent = new Intent(SplashStartScreen.this,MainActivity.class);
                }else {
                    // else load saved data and go to main screen
                    loadSavedData(preferences);
                    intent = new Intent(SplashStartScreen.this,MainActivity.class);
                }


                SplashStartScreen.this.startActivity(intent);
                SplashStartScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    /**
     * Load user data: id, type, basicAuth saved in Shared preference
     * @param preferences
     */
    private void loadSavedData(SharedPreferences preferences) {
        User user = User.getUser();
        user.setId(preferences.getInt("id", 0));
        user.setType(preferences.getInt("type", 0));
        user.setBasicAuth(preferences.getString("basicAuth", "basic"));
    }
}
