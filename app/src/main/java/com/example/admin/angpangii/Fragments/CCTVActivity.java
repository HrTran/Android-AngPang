package com.example.admin.angpangii.Fragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.admin.angpangii.R;

/**
 * Created by Admin on 4/26/2016.
 */
public class CCTVActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv);
        getSupportActionBar().setTitle("CCTV");
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
    }
}
