package com.example.admin.angpangii.Fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.admin.angpangii.Items.Health;
import com.example.admin.angpangii.Items.HealthAdapter;
import com.example.admin.angpangii.R;
import com.example.admin.angpangii.utils.AppController;
import com.example.admin.angpangii.utils.HTTPDataHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Admin on 5/7/2016.
 */
public class HealthActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String url = "http://10.0.3.2:8000/v1/get/class_list/1";
    private static final String[]paths = {"Class","Class A1", "Class A2", "Class A3"};
    //private static String urlString;
    private List<Health> childList = new ArrayList<Health>();
    private HealthAdapter HAdapter;
    private ListView listChild;
    private ProgressDialog pDialog;
    /*// contacts JSONArray
    JSONArray childName = new JSONArray();
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> childList;*/

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        getSupportActionBar().setTitle("Notice");
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner = (Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(HealthActivity.this,
                R.layout.layout_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //childList = new ArrayList<HashMap<String, String>>();

        listChild = (ListView) findViewById(R.id.listH);
        listChild.setFastScrollEnabled(true);
        listChild.setScrollingCacheEnabled(false);
        HAdapter = new HealthAdapter(this, childList);
        listChild.setAdapter(HAdapter);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        // Creating volley request obj
        JsonArrayRequest childReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Health child = new Health();
                                child.setChildId(obj.getInt("id"));
                                String child_fname = obj.getString("fname");
                                String child_lname = obj.getString("lname");
                                child.setChildFName(child_fname);
                                child.setChildLName(child_lname);
                                childList.add(child);
                                Log.i("Info: ", "id: " + child.getChildId() + ", fname: " + child.getChildFName()
                                         + ", lname: " + child.getChildLName() );
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        HAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(childReq);
        // Calling async task to get json
        //new ProcessJSON().execute();
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        //List<Health> healths = getListData();

        //listChild.setAdapter(new HealthAdapter(HealthActivity.this, healths));

        switch (position) {
            case 0:
                listChild.setVisibility(View.INVISIBLE);
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                /*urlString = "http://10.0.3.2:8000/v1/get/class_list/1";
                new ProcessJSON().execute(urlString);*/
                listChild.setVisibility(View.VISIBLE);

                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                listChild.setVisibility(View.INVISIBLE);
                //urlString = "http://localhost:8000/v1/get/class_list/2";
                break;
            case 3:
                // Whatever you want to happen when the thrid item gets selected
                listChild.setVisibility(View.INVISIBLE);
                //urlString = "http://localhost:8000/v1/get/class_list/3";
                break;
        }


    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        final ListView listChild = (ListView) findViewById(R.id.listH);
        listChild.setVisibility(View.INVISIBLE);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
    /*private class ProcessJSON extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(HealthActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(String... strings){
            String stream = null;
            String urlString = strings[0];

            HTTPDataHandler hh = new HTTPDataHandler();
            stream = hh.GetHTTPData(urlString);

            // Return the data from specified url
            *//*
                Important in JSON DATA
                -------------------------
                * Square bracket ([) represents a JSON array
                * Curly bracket ({) represents a JSON object
                * JSON object contains key/value pairs
                * Each key is a String and value may be different data types
             *//*

            //..........Process JSON DATA................
            if(stream !=null){
                try{
                    // looping through All
                    for (int i = 0; i < childName.length(); i++) {
                        JSONObject c = childName.getJSONObject(i);
                        String id = c.getString("id");
                        String fname = c.getString("fname");
                        String lname = c.getString("lname");

                        // tmp hashmap for single contact
                        HashMap<String, String> childFullName = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        childFullName.put("id", id);
                        childFullName.put("lname", lname);
                        childFullName.put("fname", fname);

                        // adding contact to contact list
                        childList.add(childFullName);
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }

            } // if statement end
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            *//**
             * Updating parsed JSON data into ListView
             * *//*
            ListAdapter adapter = new SimpleAdapter(
                    HealthActivity.this, childList,
                    R.layout.layout_health, new String[] {"fname", "lname"}, new int[] { R.id.childFName,
                    R.id.childLName });

            listChild.setAdapter(adapter);
        } // onPostExecute() end
    } // ProcessJSON class end*/


    /*private List<Health> getListData() {
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
    }*/



}
