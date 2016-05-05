package com.example.admin.angpangii.Fragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.admin.angpangii.R;

/**
 * Created by Admin on 5/4/2016.
 */
public class StatusActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_status);
        getSupportActionBar().setTitle("Trở về trang chủ");
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
    }
}
