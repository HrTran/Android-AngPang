package com.example.admin.angpangii.Fragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.admin.angpangii.R;

/**
 * Created by Admin on 5/3/2016.
 */
public class SettingActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);
        getSupportActionBar().setTitle("Cài đặt");
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
    }
}