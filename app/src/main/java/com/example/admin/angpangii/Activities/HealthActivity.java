package com.example.admin.angpangii.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.admin.angpangii.Adapters.Health;
import com.example.admin.angpangii.Adapters.HealthAdapter;
import com.example.admin.angpangii.Adapters.User;
import com.example.admin.angpangii.R;
import com.example.admin.angpangii.utils.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 5/7/2016.
 */
public class HealthActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    // Log tag
    private static final String TAG = HealthActivity.class.getSimpleName();
    private String url;
    private static final String[]paths = {"Choose class","Class 1", "Class 2", "Class 3","Class 4","Class 5"};
    //private static String urlString;
    private List<Health> childList = new ArrayList<Health>();
    private HealthAdapter HAdapter;
    private ListView listChild;
    private ProgressDialog pDialog;
    private int childId;

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
        listChild.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listChild.getItemAtPosition(position);
                childId = ((Health) o).getChildId();
                Log.i("Health Activity - ", "Child id to pass: " + childId);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                /* Create an Intent that will start the Status-Activity. */
                        Intent mainIntent = new Intent(HealthActivity.this, SubHealthActivity.class);
                        mainIntent.putExtra("Pass_childId",childId);
                        startActivity(mainIntent);
                    }
                }, 0);

            }
        });

        // Calling async task to get json
        //new ProcessJSON().execute();
    }

    /**
     * Method to handle action with each item on spinner
     * @param position is position of item on spinner
     * */
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        //List<Health> healths = getListData();

        //listChild.setAdapter(new HealthAdapter(HealthActivity.this, healths));

        switch (position) {
            case 0:
                listChild.setVisibility(View.INVISIBLE);
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                listChild.setVisibility(View.VISIBLE);
                // Clear collection..
                childList.clear();
                // Add data to collection..
                url  = "http://10.0.3.2:8000/v1/get/class_list/1";
                new ProcessJSON().execute(url);
                // Refresh your listview..
                HAdapter.notifyDataSetChanged();

                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                listChild.setVisibility(View.VISIBLE);
                // Clear collection..
                childList.clear();
                // Add data to collection..
                url  = "http://10.0.3.2:8000/v1/get/class_list/2";
                new ProcessJSON().execute(url);
                // Refresh your listview..
                HAdapter.notifyDataSetChanged();
                break;
            case 3:
                // Whatever you want to happen when the thrid item gets selected
                listChild.setVisibility(View.VISIBLE);
                // Clear collection..
                childList.clear();
                // Add data to collection..
                url  = "http://10.0.3.2:8000/v1/get/class_list/3";
                new ProcessJSON().execute(url);
                // Refresh your listview..
                HAdapter.notifyDataSetChanged();
                break;
            case 4:
                // Whatever you want to happen when the thrid item gets selected
                listChild.setVisibility(View.VISIBLE);
                // Clear collection..
                childList.clear();
                // Add data to collection..
                url  = "http://10.0.3.2:8000/v1/get/class_list/4";
                new ProcessJSON().execute(url);
                // Refresh your listview..
                HAdapter.notifyDataSetChanged();
                break;
            case 5:
                // Whatever you want to happen when the thrid item gets selected
                listChild.setVisibility(View.VISIBLE);
                // Clear collection..
                childList.clear();
                // Add data to collection..
                url  = "http://10.0.3.2:8000/v1/get/class_list/5";
                new ProcessJSON().execute(url);
                // Refresh your listview..
                HAdapter.notifyDataSetChanged();
                break;
        }


    }

    /**
     * Method to handle action if not choose anyitem on spinner
     *
     * */
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

    /**
     * Method to hide Progress Dialog
     * */
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    /**
     * Method to read JSON array
     *
     * */
    private class ProcessJSON extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... urls){
            url = urls[0];
            JsonArrayRequest childReq = new JsonArrayRequest(url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(TAG, response.toString());

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
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> headers = new HashMap< String, String >();
                    headers.put("Authorization", User.getUser().getBasicAuth());
                    headers.put("Content-Type","application/x-www-form-urlencoded");
                    return headers;
                }
            };

            AppController.getInstance().addToRequestQueue(childReq);
            return null;
        }
    }
}
