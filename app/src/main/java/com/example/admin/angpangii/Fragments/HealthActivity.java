package com.example.admin.angpangii.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.admin.angpangii.Items.Health;
import com.example.admin.angpangii.Items.HealthAdapter;
import com.example.admin.angpangii.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 5/7/2016.
 */
public class HealthActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private static final String[]paths = {"Class","Class A1", "Class A2", "Class A3"};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        spinner = (Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(HealthActivity.this,
                R.layout.layout_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);





    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        List<Health> healths = getListData();
        final ListView listChild = (ListView) findViewById(R.id.listH);
        listChild.setAdapter(new HealthAdapter(HealthActivity.this, healths));
        listChild.setFastScrollEnabled(true);
        listChild.setScrollingCacheEnabled(false);
        switch (position) {
            case 0:
                listChild.setVisibility(View.INVISIBLE);
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                listChild.setVisibility(View.VISIBLE);
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                listChild.setVisibility(View.INVISIBLE);
                break;
            case 3:
                // Whatever you want to happen when the thrid item gets selected
                listChild.setVisibility(View.INVISIBLE);
                break;
        }

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        final ListView listChild = (ListView) findViewById(R.id.listH);
        listChild.setVisibility(View.INVISIBLE);
    }

    private List<Health> getListData() {
        List<Health> list = new ArrayList<Health>();
        Health not1 = new Health("Trần Tất Huy", "f5");
        Health not2 = new Health("Trần Quang Linh", "f5");
        Health not3 = new Health("Trần Quang Huy", "f5");
        Health not4 = new Health("Trần Tất Linh", "f5");
        Health not5 = new Health("Nguyễn Thành Long", "f5");
        Health not6 = new Health("Nguyễn Văn Lương", "f5");
        Health not7 = new Health("Nguyễn Thành Lương", "f5");
        Health not8 = new Health("Nguyễn Văn Long", "f5");

        list.add(not1);
        list.add(not2);
        list.add(not3);
        list.add(not4);
        list.add(not5);
        list.add(not6);
        list.add(not7);
        list.add(not8);

        return list;
    }

}
