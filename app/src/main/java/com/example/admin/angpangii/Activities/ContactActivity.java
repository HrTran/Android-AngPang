package com.example.admin.angpangii.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.admin.angpangii.Adapters.User;
import com.example.admin.angpangii.R;
import com.example.admin.angpangii.utils.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 4/26/2016.
 */
public class ContactActivity extends AppCompatActivity {

    private static final String TAG = ContactActivity.class.getSimpleName();
    private String url;
    private ProgressDialog pDialog;
    // temporary string to show the parsed response
    private String jsonResponse;
    private ImageView background;
    private TextView userName;
    private TextView userEmail;
    private TextView userBirthday;
    private TextView userGender;
    private TextView userAddress;
    private TextView userPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setTitle("Profile");
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        background = (ImageView) findViewById(R.id.imgBackground);
        background.setImageResource(R.drawable.ava_background);

        userName = (TextView) findViewById(R.id.username1);
        userEmail = (TextView) findViewById(R.id.email1);
        userBirthday = (TextView) findViewById(R.id.birthday1);
        userGender = (TextView) findViewById(R.id.gender1);
        userAddress = (TextView) findViewById(R.id.address1);
        userPhone = (TextView) findViewById(R.id.phone1);

        url  = "http://10.0.3.2:8000/v1/get/user_info/1";
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        makeJsonObjectRequest();
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
                    String email = response.getString("email");
                    String birthday = response.getString("birthday");
                    String gender = response.getString("sex");
                    String address = response.getString("address");
                    String phone = response.getString("phone");
                    String type = response.getString("type");

                    jsonResponse = "";
                    if (mname.equals("")){
                        jsonResponse += fname + " " + lname + "\0";
                    } else jsonResponse += fname + " " + mname + " " + lname + "\0";

                    userName.setText(jsonResponse);
                    userEmail.setText(email);
                    userBirthday.setText(birthday);
                    if (gender.equals("true")){userGender.setText("Nam"); }
                    else userGender.setText("Nữ");
                    userAddress.setText(address);
                    userPhone.setText(phone);

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

    /**
     * Method to show Progress Dialog
     * */
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    /**
     * Method to hide Progress Dialog
     * */
    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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
