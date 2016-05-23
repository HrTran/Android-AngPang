package com.example.admin.angpangii.Fragments;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.LruCache;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.angpangii.Items.User;
import com.example.admin.angpangii.R;
import com.example.admin.angpangii.utils.AppController;
import com.example.admin.angpangii.utils.CustomVolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 5/20/2016.
 */
public class SubHealthActivity extends HealthActivity {

    private static final String TAG = SubHealthActivity.class.getSimpleName();
    private String url;
    private String AvaUrl = "http://10.0.3.2:8000/v1/get/child_picture/3";
    private ProgressDialog pDialog;
    // temporary string to show the parsed response
    private String jsonResponse, jsonImage;
    private ImageView childAva;
    private TextView mood;
    private TextView health;
    private TextView temp;
    private TextView sleep;
    private TextView food;
    private TextView childName;
    private int childID;
    //private ImageLoader imageLoader;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_subhealth);
        getSupportActionBar().setTitle("Back to Notice");
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //get child id from health activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            childID = extras.getInt("Pass_childId");
        }
        childAva = (ImageView) findViewById(R.id.img_childAva);
        childName = (TextView) findViewById(R.id.txt_childName);
        mood = (TextView) findViewById(R.id.comM);
        health = (TextView) findViewById(R.id.comH);
        temp = (TextView) findViewById(R.id.comT);
        sleep = (TextView) findViewById(R.id.comS);
        food = (TextView) findViewById(R.id.comF);

        /*jsonImage = "http://10.0.3.2:8000/v1/get/child_picture/3";
        imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext()).getImageLoader();
        imageLoader.get(jsonImage,
                ImageLoader.getImageListener(childAva, R.drawable.f5, android.R.drawable.ic_dialog_alert));

        childAva.setImageUrl(jsonImage, imageLoader);*/
        // Retrieves an image specified by the URL, displays it in the UI.



        url = "http://10.0.3.2:8000/v1/get/child_info/" + childID;
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        ChildAvaRequest();
        makeJsonObjectRequest();
    }

    /**
     * Method to load ava of children
     */
    public void ChildAvaRequest() {
        String url = "http://10.0.3.2:8000/v1/get/child_picture/3";
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        childAva.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.d("test image:", error.toString());
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
        AppController.getInstance().addToRequestQueue(request);

    }


    /**
     * Method to make json object request where json response starts wtih {
     * */
    private void makeJsonObjectRequest() {

        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, (String)null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    String id = response.getString("id");
                    String fname = response.getString("fname");
                    String mname = response.getString("mname");
                    String lname = response.getString("lname");
                    String birthday = response.getString("birthday");
                    String gender = response.getString("sex");
                    String address = response.getString("address");
                    String child_mood = response.getString("mood");
                    String child_health = response.getString("health");
                    String child_temp = response.getString("temperature");
                    String child_sleep = response.getString("sleep");
                    String child_food = response.getString("food");
                    String child_idClass = response.getString("id_class");

                    jsonResponse = "";
                    if (mname.equals("")){
                        jsonResponse += fname + " " + lname + "\0";
                    } else jsonResponse += fname + " " + mname + " " + lname + "\0";


                    childName.setText(jsonResponse);
                    mood.setText(child_mood);
                    health.setText(child_health);
                    temp.setText(child_temp);
                    sleep.setText(child_sleep);
                    food.setText(child_food);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
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

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}