package com.example.admin.angpangii.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.admin.angpangii.Items.CustomStatusAdapter;
import com.example.admin.angpangii.Items.MenuAdapter;
import com.example.admin.angpangii.Items.MenuList;
import com.example.admin.angpangii.Items.Status;
import com.example.admin.angpangii.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 4/26/2016.
 */
public class MenuActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().setTitle("Menu");
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        List<MenuList> menu_list = getListData();
        final ListView listView = (ListView) findViewById(R.id.listM);
        listView.setAdapter(new MenuAdapter(MenuActivity.this, menu_list));
        listView.setFastScrollEnabled(true);
        listView.setScrollingCacheEnabled(false);

        /*// When user click in items in listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                *//* Create an Intent that will start the Status-Activity. *//*
                        Intent mainIntent = new Intent(MenuActivity.this, StatusActivity.class);
                        startActivity(mainIntent);

                    }
                }, 0);

            }
        });*/
    }

    /**
     * Method to make data for listview
     */
    private List<MenuList> getListData() {
        List<MenuList> list = new ArrayList<MenuList>();
        MenuList stt1 = new MenuList("f5", "Monday");
        MenuList stt2 = new MenuList("f5", "Tuesday");
        MenuList stt3 = new MenuList("f5", "Wesnesday");
        MenuList stt4 = new MenuList("f5", "Thursday");
        MenuList stt5 = new MenuList("f5", "Friday");
        MenuList stt6 = new MenuList("f5", "Saturday");
        MenuList stt7 = new MenuList("f5", "Sunday");


        list.add(stt1);
        list.add(stt2);
        list.add(stt3);
        list.add(stt4);
        list.add(stt5);
        list.add(stt6);
        list.add(stt7);

        return list;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
